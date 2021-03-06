# Team Service

A proof of concept service to manage a hierarchy of teams

[![CircleCI](https://circleci.com/gh/awconstable/teamservice.svg?style=shield)](https://circleci.com/gh/awconstable/teamservice)
![CodeQL](https://github.com/awconstable/teamservice/workflows/CodeQL/badge.svg)
[![codecov](https://codecov.io/gh/awconstable/teamservice/branch/master/graph/badge.svg)](https://codecov.io/gh/awconstable/teamservice)
[![Libraries.io dependency status for GitHub repo](https://img.shields.io/librariesio/github/awconstable/teamservice.svg)](https://libraries.io/github/awconstable/teamservice)
[![dockerhub](https://img.shields.io/docker/pulls/awconstable/teamservice.svg)](https://cloud.docker.com/repository/docker/awconstable/teamservice)

## Limitations

This application is a proof of concept, it is **not** production ready.

A non-exhaustive list of known limitations:

* No security whatsoever - anonymous users can easily delete or alter all data

## Dependencies

1. MongoDB

## Quick start guide

### Add a team

```bash
 curl -H "Content-Type: application/json" -X POST -d '{"slug": "slug", "entityType":"APPLICATION", "name": "Entity Name"}' http://localhost:8080/v2/data/hierarchy
```

### View and manage teams

<http://localhost:8080/hierarchy_manager/>

---

## Developer guide

### Compile

```bash
./mvnw clean install
```

### Run in development

*Might be **-Drun.arguments** - see: https://stackoverflow.com/questions/23316843/get-command-line-arguments-from-spring-bootrun*

```bash
docker-compose up
```

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.data.mongodb.host=<mongo host>,--spring.data.mongodb.port=<mongo port>,--spring-data.mongodb.database=<mongo db>"
```

### Create Docker image

```bash
./mvnw compile jib:build
```

### Run app as a Docker container

*See https://github.com/docker-library/openjdk/issues/135 as to why spring.boot.mongodb.. env vars don't work*

```bash
docker stop teamservice
docker rm teamservice
docker pull awconstable/teamservice
docker run --name teamservice -d -p 8080:8080 --link <mongo container> -e spring_data_mongodb_host=<mongo host> -e spring_data_mongodb_port=<mongo port> -e spring_data_mongodb_database=<mondo db> awconstable/teamservice:latest
```
