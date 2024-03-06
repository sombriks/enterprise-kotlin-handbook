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

While spring is well designed and pays very nice with JEE projects (good old
days of [spring web app configuration][0514]), it is ultimately limited by it.

In JEE, there is a separation between the application itself and the server
provisioned to run it.

[Spring Boot][0515] comes to solve this question by making possible to deliver a
enterprise-grade solution to replace both applications and application servers.

[For the time][0516] it was groundbreaking, no one used to dispute usefulness of
having an application server managing resources and the application only
borrowing them.

Nowadays all the thing looks like a distant misdirection. Well,
[not that distant][0517] but we'll not discuss this now.

Spring Boot is a concrete example of get a complex issue, then solve a simple
one that delivers the same result and therefore get the complex one solved.

## Understanding a Spring Boot project

Unlike all we did until now, manually create the project isn't the way on Spring
Boot land. Instead, you go to <https://start.spring.io/> and it renders to you a
project ready to use. Sure, you still needs to manually add some dependencies on
pom.xml/build.grade but it's a faster start, skipping several chores.

In order to take advantage of spring-boot goodies, you need to add
[starters][0519] as dependencies. We'll discuss them in a moment.

You can check a basic spring-boot project produced with the _initializr_
[here][0518].

## The `spring-boot-starter-web`

The most common Spring Boot starter is for sure the [web one][0520]. Everyone is
still doing JSON APIs and this starter eases the job pretty much.

You can select it when using the [initializr][0521] or add it to you spring-boot
project manually as a dependency.

For gradle:

```kotlin
implementation('org.springframework.boot:spring-boot-starter-web')
```

For maven:

```xml
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

And that's it, next time you run your spring project it will spin up a server on
port 8080 an you will be able to create [RestControllers][0522] like this one:

```kotlin
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/my")
class MyController {

  @GetMapping
  fun hello() = "hello world"

}
```

And that would be accessible via <http://localhost:8080/my> in your browser or
curl or wget.

As **exercise**, add starter web in previous sample project and then make this
controller work.

## Notable spring stereotypes annotations

In Spring, there are several ways configure and _wire_ beans. When doing that
via annotations + classpath scanner, You can use distinct annotations to declare
distinct roles (or _stereotypes_) for each bean.

When using Spring Boot, these are the holy trinity:

- [@RestController][0523] (used to be just [@Controller][0524])
- [@Service][0525]
- [@Repository][0526]

They're all [@Component][0527] descendants but they serve to split mental models
in the big [MVC layers][0528] idea or the [Single Responsibility][0529] thing,
the "S" in SOLID.

## Notable spring configuration annotations

## Spring profiles and environment variables

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
[0514]: https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet.html
[0515]: https://spring.io/projects/spring-boot
[0516]: https://spring.io/blog/2014/04/01/spring-boot-1-0-ga-released
[0517]: https://kubernetes.io/docs/home/
[0518]: ../samples/project-011-spring-boot-example/README.md
[0519]: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using.build-systems.starters
[0520]: https://docs.spring.io/spring-boot/docs/current/reference/html/web.html
[0521]: https://start.spring.io
[0522]: https://spring.io/guides/tutorials/rest#_http_is_the_platform
[0523]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html
[0524]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html
[0525]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Service.html
[0526]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Repository.html
[0527]: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html
[0528]: https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller
[0529]: https://en.wikipedia.org/wiki/Single_responsibility_principle
