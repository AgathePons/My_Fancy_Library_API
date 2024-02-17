# Getting Started

### TEST with docker-compose.yml

```
docker-compose -p testbot-pg-app up -d
```

### Create Spring app Docker image

```
docker build -t myorg/myapp .
```

### Run the docker container

```
docker run --name myapp -p 4040:4444 myorg/myapp
```

### Access Ash shell (sort of Bash) command line of the app image

If the container is not currentliy running

```
docker run -ti --entrypoint /bin/sh myorg/myapp
```

If the container is already running

```
docker exec -ti myapp /bin/sh
```