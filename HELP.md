# Getting Started

### Create Spring app Docker image

Build the image

```
docker build -t agatheatsohga/spring_app_test .
```

Push the image on Docker Hub

```
docker push agatheatsohga/spring_app_test:latest
```

### Launch the container

```
docker-compose -p testbot-pg-app up -d
```

### Create Spring app Docker image

- Active docker profile
- Maven clean package
- Build the image

```
docker build -t agatheatsohga/spring_app_test .
```

- Push the image on Docker Hub

```
docker push agatheatsohga/spring_app_test:latest
```


### Access Ash shell (sort of Bash) command line of the app image

If the container is not currentliy running

```
docker run -ti --entrypoint /bin/sh agatheatsohga/spring_app_test
```

If the container is already running

```
docker exec -ti agatheatsohga/spring_app_test /bin/sh
```

*ou ???*

```
docker exec -ti spring_app_test /bin/sh
```
