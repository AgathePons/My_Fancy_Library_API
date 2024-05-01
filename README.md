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
## Update the app and the docker

### 1. Package the .jar

- Active Maven docker profile
- Maven clean package

### 2. Create Spring app Docker image & push it on Docker Hub

Build the image

```
docker build -t agatheatsohga/spring_app_test .
```

Push the image on Docker Hub

```
docker push agatheatsohga/spring_app_test:latest
```

### 3. Re-launch the container

```
docker-compose -p testbot-pg-app up -d
```

## Tips

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

# Infos about this project

## docker-compose

`docker-compose.yml` file describes the docker container with its volumes, services and images. It is possible to use 
a `.env` file to modify the value of the variables, for example :

```
  guistorage:
    name: ${GUI_DATA_VOLUME:-pg-gui-data}
```

or

```
    ports:
      - ${DB_PORT:-5433}:5432
```

The container is composed by 

### 3 volumes : 

- `guistorage` for the web version of pgAdmin
- `dbstorage` for the postgreSQL DB
- `appstorage` for the application itself

### 3 services, each has its own image, that can communicate

- `gui` uses an image of pgAdmin from DockerHub : [dpage pgAdmin4](https://hub.docker.com/r/dpage/pgadmin4).
It is just the web version of pgAdmin that allows to administrate the postgreSQL database


- `db` is the database service. It is a custom image of a postgreSQL database, that comes with a `docker-entrypoint-initdb.d`
in which 2 sql scripts are copied (1 for DDL, 1 for DML), so when the database is initiated for the first time,
tables are created, and data are inserted in tables. This side project I made is available on GitHub 
([sandbox_custom_postgres_image](https://github.com/AgathePons/sandbox_custom_postgres_image)) and the image can be found
on my dockerhub ([sandbox_custom_postgres_image](https://hub.docker.com/repository/docker/agatheatsohga/sandbox_custom_postgres_image/)).


- `myapp`  is the Java Spring boot application. It is a very simple API I made to have a functional base to work on.

## dockerfile

`dockerfile` file is the entry point when the docker container is launched. It copies the `.jar`of the application and
launch it, so the application is already running when the `docker-compose up` command line is launched.

Finally, the pgAdmin, database, tables and data, and application, all are build and launched by one single docker command :

```
docker-compose -p testbot-pg-app up -d
```

## Maven

This project has 2 Maven profiles set :

- **dev** for local developpment

- **docker** for docker launch

Each profile has its own `application-*.properties` file, so it is possible to configure ports, context path, connexion
to the database and so on, specifically for each profile.

For now, the database is sharing for local dev and docker, so the container has to be launched with the database service
running to run the application locally. But it is possible to build a local database and modify the `application-dev.properties`
to connect the local app to the local db.

## Data folder

The data folder is just here to keep the DDL and DML sql scripts that can build the database, but these 2 files are not
used. Like it is specified in the docker-compose part, the database with its tables and data is created from my
[custom postgreSQL image](https://github.com/AgathePons/sandbox_custom_postgres_image) where the 2 sql scripts can be
found too. There are these 2 script which are used, so if DML or DDL has to be modified, it must be done in this project.