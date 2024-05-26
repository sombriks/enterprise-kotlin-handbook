package project013

import java.sql.Connection
import java.sql.DriverManager

fun main(args: Array<String>) {
    if (args.size < 2) return usage()
    var db = "todos"
    val (runtime, operation) = args
    if (args.size > 2) db = args[2]
    QueryDb(db)
        .using(runtime)
        .perform(operation)
}

fun usage() = println(
    """
        Usage: java -jar db-manage.jar <runtime> <operation> [custom-db-name]
        
        Available runtime:
            - sqlite
            - h2
        
        Available operation:
            - insert
            - list
            - remove
            - update
    """.trimIndent()
)


class QueryDb(private val name: String) {

    private lateinit var connection: Connection

    fun using(runtime: String): QueryDb {
        when (runtime) {
            "sqlite" -> {
                Class.forName("org.sqlite.JDBC")
                connection = DriverManager.getConnection("jdbc:sqlite:./$name.sqlite3.db")
                initDb("id integer primary key autoincrement")
            }

            "h2" -> {
                Class.forName("org.h2.Driver")
                connection = DriverManager.getConnection("jdbc:h2:./$name.h2.db")
                initDb("id identity primary key")
            }

            else -> {
                println("Invalid driver $runtime, must be either h2 or sqlite")
                usage()
            }
        }
        return this
    }

    fun perform(operation: String) {
        when (operation) {
            "insert" -> insert()
            "list" -> list()
            "remove" -> remove()
            "update" -> update()
            else -> {
                println("Invalid operation: $operation.")
                usage()
            }
        }
    }

    /**
     * each RDBMS implements a 'dialect', so the SQL can be slightly different
     * There is a standard, that should not happen, but it is what it is.
     */
    fun initDb(idDefinition: String) {
        connection.prepareStatement(
            """
            create table if not exists todos (
                $idDefinition,
                description text not null,
                done boolean default false,
                updated timestamp not null default current_timestamp
            );
        """.trimIndent()
        ).use {
            it.executeUpdate();
        }
    }

    fun insert() {
        println("Provide description:")
        val description = readln()
        println("Is it done? (true/false):")
        val done = readln().toBoolean()
        connection.prepareStatement(
            """
            insert into todos(description,done) values (?,?)
        """.trimIndent()
        ).use {
            it.setString(1, description)
            it.setBoolean(2, done)
            it.executeUpdate()
            println("Saved!")
        }
    }

    fun list() {
        println("|#\t|description\t|done\t|updated\t")
        connection.prepareStatement(
            """
            select * from todos
        """.trimIndent()
        ).use {
            val rs = it.executeQuery()
            while (rs.next()) {
                println(
                    "|${rs.getLong("id")}\t" +
                    "|${rs.getString("description")}\t" +
                    "|${rs.getBoolean("done")}\t" +
                    "|${rs.getTimestamp("updated")}"
                )
            }
        }
    }

    fun remove() {
        println("Provide todo id to remove:")
        val id = readln().toInt()
        connection.prepareStatement("delete from todos where id = ?").use {
            it.setInt(1, id)
            val removed = it.executeUpdate()
            println("$removed Removed!")
        }
    }

    fun update() {
        println("Provide todo id to update:")
        val id = readln().toInt()
        println("Provide description:")
        val description = readln()
        println("Is it done? (true/false):")
        val done = readln().toBoolean()
        connection.prepareStatement(
            """
            update todos 
            set description = ?, done = ?, updated = current_timestamp
            where id = ?
        """.trimIndent()
        ).use {
            it.setString(1, description)
            it.setBoolean(2, done)
            it.setInt(3, id)
            it.executeUpdate()
            println("Saved!")
        }
    }
}
