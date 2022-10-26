# lottery-system

## Description
This is a Lottery System application which exposes APIs or web-services that allows users to participant in a lottery. Lottery participants will be able to submit as many lottery ballots for any lottery that is active.
Moreover, at midnight every day the lottery event will be considered finished and random lottery winner will be selected from all the registered participants of that lottery for that day. The participants will be able to check the winning ballot for any specific date.


## Requirements
- Java v1.8
- SpringBoot
- Postgres DB
- Gradle v7.5
- Unix environment


## How to Build / Test / Run
- Build and Run UnitTests
    ```
    cd PROJECT_DIRECTORY
    ./gradlew clean build
    ```
- Run/Deploy
    ```
    cd PROJECT_DIRECTORY
    java -jar build/libs/lotterysystem-0.0.1-SNAPSHOT.jar
    ```


## Implementation Key Points
- **KISS Principle:** I have tried my best to keep the code very simple, modular and testable to enable people to play around with the APIs or webservices using Postman tool.
- **Java Documentation:** One of the most essential part when implementing webservices or APIs which are user interactive is Documentation. I have added JavaDoc for all interfaces, classes and methods which will improve the readability of the code.
- **Integration Tests:** I have provided integrated unit tests for API endpoints to mimic the web environment using MockMvc. This will allow everyone of understand various scenarios during application execution


## Points omitted from the assignment
- I have tried to cover all the instructions and requirements mentioned in the assignment PDF. If there is anything missed, I would be happy to discuss.

