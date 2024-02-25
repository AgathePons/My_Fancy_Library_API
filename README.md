# Getting Started

This is a demo project for a docker container that contains:

- Custom Postgres image with a database running, and initialization SQL scripts to create tables and insert data. 
This custom fork is available on Docker Hub 
([custom postgres image](https://hub.docker.com/repository/docker/agatheatsohga/sandbox_custom_postgres_image/general)) 
and the source files are available on GitHub ([custom postgres image](https://github.com/AgathePons/sandbox_custom_postgres_image))
- PG Admin web application to administrate a postgresql database
- Java Spring Boot API running at [http://localhost:4040/testbot/](http://localhost:4040/testbot/)

For now, this is just a project skeleton for an all-in-one Docker container.

## Docker quick start

### Launch the container

```
docker-compose -p testbot-pg-app up -d
```

## Package the .jar

- Active docker profile
- Maven clean package

### Create Spring app Docker image & push it on Docker Hub

Build the image

```
docker build -t agatheatsohga/spring_app_test .
```

Push the image on Docker Hub

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

*or*

```
docker exec -ti spring_app_test /bin/sh
```
