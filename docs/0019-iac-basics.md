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

### Environment-aware configuration

The same way your spring application can be configured with profiles, properties
and environment variables overrides, it is possible to do the same with compose
yaml files:

```yml
---
services:
  app: # `app` will be the hostname for this container
    image: my-app # assuming you built an image and tagged it as `my-app`
    environment:
      PORT: "${PORT:-8080}"
      SPRING_PROFILES_ACTIVE: "${SPRING_PROFILES_ACTIVE:-dev}"
    ports:
      - "${PORT}:${PORT}"
```

The syntax `${VARIABLE}` replaces the term with a value defined in the host
running the compose.

The syntax `${VARIABLE:-value}` ensures that a default value will be used when
the host does not have the environment variable set.

### Docker swarm

If you think of compose as a fundamentally singe node tool, you can think of
[docker swarm][1002] as a first step on load balancing your infrastructure.

It is quite similar to compose, but adds replication over nodes features and all
the advantages and complications related to it.

Unlike compose, which works out of the box on any docker installation, you first
setup your swarm cluster.

See the [swarm tutorial][1003] for details.

## Kubernetes

Another option, and more popular one, is to declare configuration as kubernetes
manifest files.

Kubernetes is more flexible than swarm, offers more features and extra tools for
monitoring, healthcheck and general management. it also offers more runtime
options to manage the actual servers and abstract the infrastructure to serve
enterprise applications.

A common setup is to offer compose manifests for local development and testing
but manage staging and production manifests for kubernetes.

### k3s example

The easiest way to develop for a kubernetes environment is
~~[pay for an overpriced EKS instance at AWS][1004]~~ install [k3s][1005], a
lightweight kubernetes instance.

On a linux machine, all you need to get k3s up and running is to run the script
provided in the k3s project:

```bash
curl -sfL https://get.k3s.io | sh - 
# Check for Ready node, takes ~30 seconds 
sudo k3s kubectl get node 
```

After that, for a local dev environment, tweak the firewall rules:

```bash
sudo firewall-cmd --permanent --add-port=6443/tcp #apiserver
sudo firewall-cmd --permanent --zone=trusted --add-source=10.42.0.0/16 #pods
sudo firewall-cmd --permanent --zone=trusted --add-source=10.43.0.0/16 #services
sudo firewall-cmd --reload
```

Finally, check your installation with the following command:

```bash
sudo kubectl \
  --kubeconfig /etc/rancher/k3s/k3s.yaml \
  get pods \
  --all-namespaces
```

Specifically with k3s, another common practice is to chmod the `kubeconfig`
configuration file, so the regular user can check the *cluster* without sudo:

```bash
sudo chmod 644 /etc/rancher/k3s/k3s.yaml
mkdir .kube
# as a bonus, symlink the kubeconfig for the local user as well
ln -s /etc/rancher/k3s/k3s.yaml ~/.kube/config
```

Now you can issue commands in a much more ergonomic way:

```bash
kubectl get pods --all-namespaces
```

The [kubectl][1006] command is the official tool to manage daily tasks in any
kubernetes cluster.

You always need a kubeconfig file to interact with a cluster.

Some ides, like intellij and vscode, offers plugins to interact with kubernetes
clusters

And a really cool tool to deal with cluster is the [k9s][1007] cli:

```bash
# available on several linux distros, including fedora:
sudo dnf install k9s
```

Now you have a minimum kubernetes environment to prepare and configure all your
enterprise solutions.

### The most common manifest files for kubernetes deployment

#### config-map.yml

#### deployment.yml

#### service.yml

#### ingress.yml

[1001]: https://payara.fish/blog/what-is-an-application-server-jakarta-ee/
[1002]: https://docs.docker.com/engine/swarm/
[1003]: https://docs.docker.com/engine/swarm/swarm-tutorial/
[1004]: https://aws.amazon.com/eks/
[1005]: https://k3s.io/
[1006]: https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands
[1007]: https://github.com/derailed/k9s
