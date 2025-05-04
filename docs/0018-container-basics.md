# Docker, Why?

In our [previous chapter][0901], our [sample project][0902] used [docker][0903]
to provision a database.

This is no surprise that spin up a container rather a bare metal database is a
simpler procedure, given the current state of modern container technology.

Containers became the de-facto standard way to package and deliver enterprise
software. Public and private registries can support the software distribution
and attend different hardware architectures as well.

They also hold a great advantage over full virtual machines: containers consumes
less resources, since they still share most of the underlying operating system
management.

## Running containers

With docker [properly installed][0904], a simple command line is enough to run a
container:

```bash
docker run hello-world
```

This command runs the `hello-world` [container image][0905] pulling it from the
[docker hub registry][0906] if it's not present on your system already.

With the command `docker ps -a` is possible to list all containers, running or
not.

With `docker image list` is possible to see all images currently present.

Finally, `docker image prune` removes all unused docker images.

### Proper use of a container image

It is paramount to read the documentation of an image to understand how to use
it correctly.

For example, for the [official postgres image][0907]:

```bash
docker run --rm -d \
  --name pg-database -p 5432:5432 \
  -e POSTGRES_PASSWORD=enterprise \
  -e POSTGRES_USER=enterprise \
  -e POSTGRES_DB=products \
  postgres:16-alpine
```

Some extra parameters are used:

1. `--rm` is used to create an [ephemeral container][0908].
1. `-d` spawns the container as a daemon.
1. `--name` defines a name for the image instead of a random name.
1. `-p` [binds container to host ports][0909], the specified port in this case
   is the port used by postgres.
1. `-e` arguments creates environment variables used by this specific docker
   image, as described in the docs.

## Creating a container image

It is important to know how to create a docker image to package enterprise
software. As mentioned before, container images are the current standard for
software distribution.

To create images using docker, it is important to understand how to write
[Dockerfiles][0910]:

```dockerfile
FROM gradle:jdk21
ADD src /app/src/
ADD build.gradle.kts settings.gradle.kts /app/
WORKDIR /app
RUN gradle build
ENTRYPOINT gradle bootRun
```

The dockerfile above is intended to dwell at the project root folder. It expects
a spring/kotlin gradle project, builds it and runs it. It's by no means properly
optimized.

The command to build this image follows:

```bash
docker build -t my-enterprise-app .
```

The `-t` parameter **tags** the image and the `.` is the [build context][0911]
(i.e. rom where files needed to the image will be copied).

Then you can run the created image:

```bash
docker run --rm my-enterprise-app
```

Try it in [this sample project][0912] to see it in action.

### Use multi-stage builds

Container images doesn't need to provide a complete development environment.
Instead, a minimal runtime is recommended to optimize resources consumption and
provide better overall performance.

One way to achieve it is using [multi-stage builds][0913]:

```dockerfile
FROM gradle:jdk21 as builder
ADD src /app/src/
ADD build.gradle.kts settings.gradle.kts /app/
WORKDIR /app
RUN gradle build

FROM eclipse-temurin:21-jre-alpine
COPY --from=builder /app/build/libs/project-011-spring-boot-example-0.0.1-SNAPSHOT.jar /app/boot.jar
WORKDIR /app
ENTRYPOINT java -jar boot.jar
```

That way the resulting container image will be much smaller.

### Make it configurable with environment variables

Another key aspect to take in consideration is how to configure the application
at runtime. Makes little sense, for example, build a new image whenever database
credentials change.

## Publish image on docker hub or github registry or something else (ECR)
