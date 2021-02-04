## My TopRankScore Application

The module contains how I implemented for 2 days as much as I can.
Specifically, it contains the following features:
* Create score for a Player
* Delete score for a Player
* Get all scores for all Players

TODO: More Unit Tests

### Requirements
Software Requirements

* Java 1.8 or higher
* Gradle 6 or above
* Spring Boot 2.4.2 or higher
* Spring Boot Admin TODO
* Oracle SQL Database
* JUnit 5


### Getting Started
Please follow below steps.
Prerequisite:

1. Clone from github
2. Download ojdbc7-12.1.0.1.jar and modify path of ojdbc7 artifact in pom.xml
3. Ensure port 9091 is not used anywhere
4. Update application.yml
	- Change user/password/jdbcUrl in hikari.datasource.rdg

In Eclipse/IntelliJ
1. Run "gradle cleanEclipse"
2. Run "gradle eclipse"
3. Run "gradle build"
4. Run application

In Swagger:
1. http://localhost:9091/rdg/swagger-ui.html
2. In top-score-controller : Top Score Controller
	Endpoints:
		1. /topscore/create
		2. /topscore/deleteScore
		3. /topscore/getAllScores
		4. /topscore/getPlayersHistory
		5. /topscore/getScore


### GitHub




### Change Log
Please refer to change log below.
0.0.1-SNAPSHOT First implementation
