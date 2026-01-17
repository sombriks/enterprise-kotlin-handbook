# Infrastructure as Code: Provisioning infrastructure

Once you got your enterprise solution properly wrapped into a docker image, it's
time to run it in your infrastructure.

## Back in time

Long before docker, configure a server for development, testing and production
used to be an crafting art. Many details would need extensive documentation and
all the magic would happen in many distinct ways on each scenario: ip addresses
and names, folder paths, software versions and credentials, all fine-tuned by
hand.

Enterprise java introduced the [Application Server][1001] concept and
specification, making the configuration surface smaller, abstracting many
resources into its concepts.

In a way, if you think about docker images, they are like the war and ear files
from the application server middleware era, and the infrastructure code to be
presented here as the middlewares itself.

## Abstracting the infrastructure

In a similar way that app servers abstracted the environment details on a JEE
application, IaC scripts describe the needed environment without exactly saying
the physical details about it.

You don't install and configure a database. Instead, you *declare* what kind of
database you need. The details will be provided by the container runtime and one
of the tools bellow.  

## Docker compose

Compose is the first, straightforward way to provision infrastructure for your
application. It comes bundled with any modern docker installation, and is a
better alternative from bare-hand command lines or shell scripts.

You usually start by creating a file called `docker-compose.yml` and describing
the services you intend to run:

```yml
---
# sample hello world but this time as docker-compose
services:
  hello-world:
    image: hello-world:latest

```

By performing the `docker compose up` command, you'll get an output like this:

```
sombriks@barbatos hello-world $ docker compose up
[+] up 0/2
[+] up 4/4ello-world:latest [⠀] Pulling                                                                                                    3.4s 
 ✔ Image hello-world:latest            Pulled                                                                                              3.4s 
   ✔ 17eec7bbc9d7                      Pull complete                                                                                       0.8s 
 ✔ Network hello-world_default         Created                                                                                             0.1s 
 ✔ Container hello-world-hello-world-1 Created                                                                                             0.2s 
Attaching to hello-world-1
hello-world-1  | 
hello-world-1  | Hello from Docker!
hello-world-1  | This message shows that your installation appears to be working correctly.
hello-world-1  | 
hello-world-1  | To generate this message, Docker took the following steps:
hello-world-1  |  1. The Docker client contacted the Docker daemon.
hello-world-1  |  2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
hello-world-1  |     (amd64)
hello-world-1  |  3. The Docker daemon created a new container from that image which runs the
hello-world-1  |     executable that produces the output you are currently reading.
hello-world-1  |  4. The Docker daemon streamed that output to the Docker client, which sent it
hello-world-1  |     to your terminal.
hello-world-1  | 
hello-world-1  | To try something more ambitious, you can run an Ubuntu container with:
hello-world-1  |  $ docker run -it ubuntu bash
hello-world-1  | 
hello-world-1  | Share images, automate workflows, and more with a free Docker ID:
hello-world-1  |  https://hub.docker.com/
hello-world-1  | 
hello-world-1  | For more examples and ideas, visit:
hello-world-1  |  https://docs.docker.com/get-started/
hello-world-1  | 
hello-world-1 exited with code 0
sombriks@barbatos hello-world $ 
```

A lot is happening just from this simple configuration:

- Docker image fetch from the default registry
- Network creation for the service or services
- Container instance or instances creation
- Unified logs for the compose environment

### Executable documentation

One cool thing about declare infrastructure instead of doing it in the bare
metal is that, in practice, the config documentation **are** the IaC artifacts.

Therefore, you can maintain distinct compose files for distinct environments.

For example, the following compose can provision a database for development:

```yml
---
# set a name for this compose project
name: dev-env
services:
  database:
    image: postgres:18-alpine
    restart: always
    # see more supported variables at https://hub.docker.com/_/postgres
    environment:
      POSTGRES_PASSWORD: postgres
    ports: # expose port so it can be contacted by the host
      - "5432:5432"
    healthcheck: # other services can wait by a healthcheck before start
      test: ["CMD-SHELL", "pg_isready -u postgres"]
      start_period: 10s
      interval: 10s
      timeout: 5s
      retries: 5
```

### Docker swarm

## Kubernetes with k3s

### The most common manifest files for docker deployment

#### config-map.yml

#### deployment.yml

#### service.yml

#### ingress.yml

[1001]: https://payara.fish/blog/what-is-an-application-server-jakarta-ee/
