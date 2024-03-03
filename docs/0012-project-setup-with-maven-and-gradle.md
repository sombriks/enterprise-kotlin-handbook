# The very foundations of enterprise ~~java~~ kotlin projects

In the [previous chapter][0300] we discussed the language and how it`s  very
dependent on the java/jvm ecosystem.

Now we go through the tools to manage more complex projects which make use of
third party libraries, but first a bit of history.

## The history the way i remember it

Classic software development always needed tools to save us from the tedious
work of compile code in the proper way.

For C there was [Make][0301], then [Automake][0302], then other obscure ways of
indirection.

For java, as the legends says, people where unable to understand how make works
then they created [Ant][0303]. That made easier to build and package complex
projects, yet it still was up to the programmer to carefully pick the right
version of each third party libraries by hand and put in a "lib" folder.

Taking inspiration from tools like [apt][0304], [Maven][0305] born and took the
java development ergonomics even further by making the dependency management an
automatic process, all you needed to provide was the
[artifact coordinates][0306].

The maven registry become [insanely huge][0307] and the adoption of it as the
default way of create a java project skyrocket.

Several new tools appeared over the time trying to replace maven because
[xml wasn't sexy anymore][0308] and the most prominent tool on that field is
[gradle][0309].

All alternative tools still make use of maven registry and it's coordinate
system of dependencies.

IDE support for both tools is prime nowadays, with [intellij][0310],
[eclipse][0311], [netbeans][0312], [vscode][0313] and others offering support to
maven and gradle out of the box or via plugin.

## Maven setup

In order to manage a project with maven, you need to install the command line
tool. For mac, use [brew][0317]:

**[brew install maven][0314]**

For linux, use [sdkman][0316]:

**[sdk install maven][0315]**

The you can use the following command to create a maven project:

```bash
mvn archetype:generate \
  -DgroupId=com.mycompany.app \
  -DartifactId=my-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.4 -DinteractiveMode=false
```

This _hideous_ command does the following:

- create a folder called my-app
- create a [standard maven folder structure][0318] to organize your code
- create a [pom.xml file][0319] to save project configurations

### Notes on standard folder structure

In the initial [sample projects][0320] we provided, all you needed to do to have
a project was a folder and an _idea_. Little by little, we started to organize
things (i.e. a src folder, build output folder). If we kept that pace, we might
ended up reinventing the current maven folder layout.

The maven project folders became a standard on java (and kotlin by inheritance)
projects even on non-maven projects. So, **get used to it**.

### Notes on pom.xml

The pom.xml generated is, by the time this material was written, _outdated_. I
mean **extremely outdated**. It suggests the use of [java 1.7][0321]. You must
manually update the java version once the project is created.

Also remember, maven born to java projects, other languages supported by jvm are
available for maven projects via [maven plugins][0322].

In the end, instead of use the command line directly, the common practice to
create maven projects, kotlin or not, is to keep a [sample pom.xml][0324] file
already configured and just rename a few things, and create the standard maven
directories by hand.

See the [sample maven project][0323] for further details.

## Gradle setup

In order to provision gradle projects, you start by installing it.

On linux (using [sdkman][0316]):

```bash
sdk install gradle
```

On mac (using [homebrew][0317]):

```bash
brew install gradle@8
```

On windows:

_good luck!_

The you can create a project using the following command (it needs at least
[gradle 8.6][0325] to work properly):

```bash
mkdir project-008-sample-gradle
cd project-008-sample-gradle
echo "no"| gradle init \
  --type kotlin-application \
  --dsl kotlin \
  --test-framework kotlintest \
  --package project008 \
  --project-name project-008-sample-gradle \
  --no-split-project  \
  --java-version 17
```

This _extremely hideous_ command will generate to you a [kotlin-ready][0327]
project managed with gradle.

Unlike the maven version, no need to manual changes in the [config file][0328]
after project creation except for new dependencies.

The resulting project has a slightly distinct folder structure:

```bash
project-008-sample-gradle
├── app
│   ├── build.gradle.kts
│   └── src
│       ├── main
│       │   ├── kotlin
│       │   │   └── project008
│       │   │       └── App.kt
│       │   └── resources
│       └── test
│           ├── kotlin
│           │   └── project008
│           │       └── AppTest.kt
│           └── resources
├── gradle
│   ├── libs.versions.toml
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
```

The equivalent (more or less) of pom.xml in gradle projects is the build.gradle
(or build.gradle.kts, if you specify --dsl as kotlin instead of groovy) file. It
has a dependencies section very similar to what maven has, mut uses some exotic
ways to represent the libraries coordinates.

## Wrapper scripts

Wrapper scripts allow you to distribute a [reproducible build][0326] by making
sure not only of the dependencies versions but also the maven or gradle runtime
versions.

They are useful because the gradle or maven setup on local machine can be tricky
so it's a welcome way to work with those kind of projects.

In [gradle example][0327] you might already notice the wrapper script: it's the
[gradlew][0329] (or `gradlew.bat` if you're on windows) file.

When it does not exist, there is a [gradle task to create the wrapper][0330].

[Maven has its own task as well][0331].

So, when you have the wrapper properly configured, instead of do:

```bash
gradle build
```

You do:

```bash
./gradlew build
```

Or, with maven:

```bash
./mvnw clean compile package
```

## Popular plugins

We already saw some of them on those projects, but list them properly with
links, so it gets easier to check tem out when the time to use them comes:

| purpose                            | gradle       | maven        |
|------------------------------------|--------------|--------------|
| kotlin support                     | [link][0332] | [link][0322] |
| repackage with dependencies        | [link][0334] | [link][0333] |
| ease code execution in development | [link][0335] | [link][0336] |
| test coverage                      | [link][0337] | [link][0338] |
| database migrations                | [link][0339] | [link][0340] |
| spring-boot support                | [link][0341] | [link][0342] |

## Multi module projects

Using maven or gradle also makes possible to declare not only external
dependencies but also local modules.

You can read more about this practice [here][0343] for gradle and [here][0344]
for maven.

One interesting side effect of multi modules projects is that, as long as the
output of each module remains compatible with the jvm, they can be written in
any language. it makes possible, for example, to feature java, kotlin, groovy
and more in one single codebase.

## Using java libraries and code from kotlin code and vice versa

This benefit can be achieved using multi modules but also in the same project.
The module itself has distinct source sets for each language, for example
[this project][0345]. 

## Further reading

This is the minimum to know to handle projects using those tools. 

What? Which one is better?

Hard question.

- Gradle has lots of attention due jetbrains _dedication_ to it.
- Maven is the first one, who created the coordinate system for dependencies.
- Gradle eats more memory, spawns a daemon for _faster incremental builds_.
- Maven is faster for single builds.
- Gradle is buggier than Maven.

In the end, there is no way to enter enterprise JVM-based development without
know both, because all projects out there are using that.

[0300]: ./0011-kotlin-basics.md
[0301]: https://web.stanford.edu/class/archive/cs/cs107/cs107.1174/guide_make.html
[0302]: https://earthly.dev/blog/autoconf/
[0303]: https://ant.apache.org/
[0304]: https://en.wikipedia.org/wiki/APT_(software)
[0305]: https://maven.apache.org/
[0306]: https://maven.apache.org/guides/mini/guide-naming-conventions.html
[0307]: https://central.sonatype.com/
[0308]: https://harmful.cat-v.org/software/xml/
[0309]: https://gradle.org/
[0310]: https://www.jetbrains.com/pt-br/idea/
[0311]: https://www.eclipse.org/downloads/
[0312]: https://netbeans.apache.org/front/main/download/index.html
[0313]: https://code.visualstudio.com/download
[0314]: https://formulae.brew.sh/formula/maven
[0315]: https://sdkman.io/sdks#maven
[0316]: https://sdkman.io/
[0317]: https://brew.sh/
[0318]: https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html
[0319]: https://maven.apache.org/guides/introduction/introduction-to-the-pom.html
[0320]: ../samples/README.md
[0321]: https://en.wikipedia.org/wiki/Java_version_history#Java_7
[0322]: https://kotlinlang.org/docs/maven.html
[0323]: ../samples/project-007-sample-maven/README.md
[0324]: ../samples/project-007-sample-maven/pom.xml
[0325]: https://docs.gradle.org/8.6/userguide/build_init_plugin.html#sec:sample_usage
[0326]: https://en.wikipedia.org/wiki/Reproducible_builds
[0327]: ../samples/project-008-sample-gradle/README.md
[0328]: ../samples/project-008-sample-gradle/app/build.gradle.kts
[0329]: ../samples/project-008-sample-gradle/gradlew
[0330]: https://docs.gradle.org/current/userguide/gradle_wrapper.html
[0331]: https://maven.apache.org/wrapper
[0332]: https://kotlinlang.org/docs/gradle-configure-project.html#targeting-the-jvm
[0333]: https://maven.apache.org/plugins/maven-assembly-plugin/usage.html
[0334]: https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow
[0335]: https://docs.gradle.org/current/userguide/application_plugin.html
[0336]: https://www.mojohaus.org/exec-maven-plugin/usage.html#java-goal
[0337]: https://docs.gradle.org/current/userguide/jacoco_plugin.html
[0338]: https://www.eclemma.org/jacoco/trunk/doc/maven.html
[0339]: https://contribute.liquibase.com/extensions-integrations/directory/integration-docs/gradle/
[0340]: https://docs.liquibase.com/tools-integrations/maven/home.html
[0341]: https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
[0342]: https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/
[0343]: https://docs.gradle.org/current/userguide/intro_multi_project_builds.html
[0344]: https://books.sonatype.com/mvnex-book/reference/multimodule-sect-simple-parent.html
[0345]: https://github.com/sombriks/sample-htmx-javalin/tree/main/src/test
