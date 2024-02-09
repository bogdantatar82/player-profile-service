# player-profile-service
The purpose of service is to update a player profile with the current campaign if that matches.

The service will get the full profile of a player from the database and then match the profile of
the player with the current campaign settings and will determine if the current player profile matches
any of the campaign conditions, which are part of the `matchers` field.

If the requirements are met, then the player profile will be updated by adding the current campaign name
to player profile's `active campaign` field and returned it to the user.

## Development
Player profile service is a Java project, built with Spring Boot and Maven

Unit and component tests are executed with a JUnit 5 runner, and they can be run within an IDE (e.g. IntelliJ) or as part of the Maven lifecycle

### Prerequisites
* Java 17
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/get-started/)

### How to generate database JOOQ stub

For generating database stub run `./generate-jooq-model.sh`. This starts an application container and uses the database schema to generate the stub.


### Build
 `mvn clean package docker:build -Drevision=latest`

### Run
`SERVICE_IMAGE=hunus/player-profile-service:latest docker-compose up -d`

### Unit tests
Run: `mvn clean verify`

### Local tests
Import `player-profile-service.postman_collection.json` in Postman and run the following actions:
* `get current campaign`    -> gives the details about the current campaign
* `save player profile `     -> adds a new player profile and return its id
* `match player profile`    -> matches player profile with the current campaign

### Docker
Connect to postgresql docker database using
`docker exec -it container_id psql -U playerprofileservice -d playerprofileservicedb --password`

## Infrastructure
* **Dependencies** The direct dependencies for this project are:

| component         | expected address       | repository                                 |
|-------------------|------------------------|--------------------------------------------|
| campaigns-service | campaigns-service:8080 | https://github.com/hunus/campaigns-service |

## Monitoring
* **Health check**:
`http://localhost:8888/rest/actuator/health`
