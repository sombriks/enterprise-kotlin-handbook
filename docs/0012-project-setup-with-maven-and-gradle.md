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
tool. For mac, use [brew][0317]: **[brew install maven][0314]**

For linux, use [sdkman][0316]: **[sdk install maven][0315]**

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

## Wrapper scripts

## Popular plugins

## Using java libraries and code from kotlin code

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
