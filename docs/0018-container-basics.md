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

## Creating a container image

## Make it configurable with environment variables

## Publish image on docker hub or github registry or something else (ECR)
