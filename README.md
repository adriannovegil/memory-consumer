# Memory Consumer

Those who have already run a Java application inside Docker have probably come across the problem of the JVM incorrectly detecting the available memory when running inside of the container. 

The JVM sees the available memory of the machine instead of the memory available only to the Docker container. 

This can lead to cases where applications running inside the container is killed when tries to use more memory beyond the [limits of the Docker container](https://docs.docker.com/engine/admin/resource_constraints/#limit-a-containers-access-to-memory).

## Start Minikube

Start the cluster

```bash
minikube start --driver=virtualbox
```

Then, run a kubectl command to verify it looks good:

```bash
→ kubectl get nodes
NAME           STATUS   ROLES                  AGE   VERSION
minikube       Ready    control-plane,master   51s   v1.22.2
```

Launch the Kubernetes dashboard

```bash
minikube dashboard
```

## Build the Docker images and push into the docker registry

When using a container or VM driver (all drivers except none), you can reuse the Docker daemon inside minikube cluster. 

This means you don’t have to build on your host machine and push the image into a docker registry. You can just build inside the same docker daemon as minikube which speeds up local experiments.

To point your terminal to use the docker daemon inside minikube run this:

```bash
eval $(minikube docker-env)
```

Now any `docker` command you run in this current terminal will run against the docker inside minikube cluster.

So if you do the following commands, it will show you the containers inside the minikube, inside minikube’s VM or Container.

```bash
docker ps
```

No it's time to build the docker image, Make sure to tag the image as same as in the deployment yaml file.

```bash
docker build -t memory_consumer -f ./docker/Dockerfile.jdk7 .
docker build -t memory_consumer -f ./docker/Dockerfile.jdk8 .
docker build -t memory_consumer -f ./docker/Dockerfile.jdk9 .
docker build -t memory_consumer -f ./docker/Dockerfile.jdk11 .
```

We have to set `ImagePullPolicy` to Never in order to use local docker images with the deployment.

We can unset the environment variables with this command 

```bash
eval $(minikube docker-env -u)
```

## Setting things up in Kubernetes

Let’s install the application in a separate namespace `memory-consumer`.

```bash
kubectl create namespace memory-consumer
kubectl config set-context --current --namespace=memory-consumer
```

Create the Kubernetes pods that will be use validate the experiment:

```bash
sh deploy-to-k8s.sh
```

## Delete the Pod's

Delete all the pods in the namespace

```bash
kubectl delete --all pods --namespace=memory-consumer
```

## References

- https://dzone.com/articles/why-my-java-application-is-oomkilled
- https://docs.docker.com/config/containers/resource_constraints/
- https://github.com/msepehr/memory-consumer
- https://github.com/kubernetes/minikube/issues/12842
- https://docs.docker.com/engine/install/linux-postinstall/#your-kernel-does-not-support-cgroup-swap-limit-capabilities
