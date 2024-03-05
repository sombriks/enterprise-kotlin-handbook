# Spring(Boot), the _de-facto_ enterprise java (and more!) standard

In this chapter we will discuss [spring boot][0500] and a little of spring
itself, but mostly spring boot.

## History the way i remember

In the beginning, java development and the [dot-com bubble][0502] demanded more
and more specialized libraries so the [J2EE specification][0502] was born and
with that [several][0501] [new][0503] [implementations][0504].

The specification idea was (is) good, but it was a disaster in reality.
Implementations where incompatible with each other, demanding special
configuration files not present in the original spec. They were also incomplete,
with most implementations covering the servlet specification well but lacking
important bits like [ejb][0506] or admin tools.

In that chaotic context [spring is born][0507] and on top of a few J2EE specs it
delivered an astounding developer experience (for the standards of the time),
code portability and much more solid foundation to build enterprise solutions.

Over the time, Both platforms - Spring and JEE (it changed name a few times)
evolved taking lessons from each other, but if you ask any java developer he
will tell you that spring is in better shape these days. [Jakarta EE][0508] is
not dead and keeps evolving, but the scenario and challenges are
[quite different nowadays][0509].

## Before we start - The "D" in SOLID

Spring philosophy makes it the first choice for enterprise java projects because
it's very pragmatic regarding reuse not only of the code, but the ideas as well.

You can say that spring isn't just a framework but an implementation of industry
standards and best practices. Given enough time, one would end up reinventing
spring.

Take the [SOLID principles][0510] for example, modern spring enforces the
dependency inversion since the beginning, thanks to it's classpath scanning and
[stereotype annotations][0511].

Thanks to it, several chores needed to produce safe, testable and somewhat
performant code are done automatically and the developer can lay down a couple
of annotations in the code and the go home early.

Under the hood, Spring container uses [reflection][0512] to scan the classpath,
instantiate objects, lookup for dependencies and inject every piece of code in
the appropriate way.

See [this example project][0513] for better understanding.

## Difference between spring framework and spring boot

## Initialize a spring boot project

## The `spring-boot-starter-web`

## Notable spring stereotypes annotations

## Spring profiles and environment variables

## Notable spring configuration annotations

[0500]: https://spring.io/projects/spring-boot
[0501]: https://tomcat.apache.org/tomcat-4.1-doc/index.html
[0502]: https://en.wikipedia.org/wiki/Dot-com_bubble
[0503]: https://en.wikipedia.org/wiki/Jetty_(web_server)
[0504]: https://en.wikipedia.org/wiki/IBM_WebSphere_Application_Server
[0506]: https://en.wikipedia.org/wiki/Jakarta_Enterprise_Beans
[0507]: https://en.wikipedia.org/wiki/Spring_Framework
[0508]: https://jakarta.ee/
[0509]: https://developers.redhat.com/blog/2018/06/28/why-kubernetes-is-the-new-application-server#empowering_your_application
[0510]: https://en.wikipedia.org/wiki/SOLID
[0511]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ClassPathBeanDefinitionScanner.html
[0512]: https://www.oracle.com/technical-resources/articles/java/javareflection.html
[0513]: ../samples/project-010-spring-example/README.md
