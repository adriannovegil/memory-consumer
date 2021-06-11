# Memory Consumer

Launch Minikube

```
minikube start
```

We need to set the environment variables with 

```
eval $(minikube docker-env)
```

We need to build the docker image after we set the environment variables above and make sure to tag the image as same as in the deployment yaml file.

```
docker build -t memory_consumer .
```

We have to set ImagePullPolicy to Never in order to use local docker images with the deployment.

We can unset the environment variables with this command 

```
eval $(minikube docker-env -u)
```


kubectl apply -f memory-consumer.ko.yaml
kubectl apply -f memory-consumer.ok.yaml