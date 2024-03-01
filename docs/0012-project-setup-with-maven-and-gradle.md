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

## Maven setup

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
