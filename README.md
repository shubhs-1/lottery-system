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


## Database Design
I have created 3 tables for the lottery system design.

1. user_details - Holds participant details
2. lottery_details - Holds lottery information
3. lottery_ballot - Holds ballot information for lotteries

Below is the structure of tables,

**user_details**
- id
- username
- firstname
- lastname

**lottery_details**
- id
- lottery_name
- start_date
- end_date
- lottery_winner

**lottery_ballot**
- id
- lottery_id
- username
- lottery_number
- date

![img_1](https://user-images.githubusercontent.com/30280454/197953412-32549947-beaf-491f-a6fb-2b29d6ceff34.png)


## API Responses using Postman
- Successful participant registration
![img_2](https://user-images.githubusercontent.com/30280454/197953466-04b9221a-8116-4cb7-904b-d2a5c38ba9d7.png)

- Username already exists exception
![img_3](https://user-images.githubusercontent.com/30280454/197953518-9ec0a469-da5f-416e-8e04-4e00e34a2719.png)

- Username constraint violation
![img_4](https://user-images.githubusercontent.com/30280454/197953674-95511290-5b56-4bb1-84e3-85a87d5ec2dd.png)

- Start lottery by name
![img_5](https://user-images.githubusercontent.com/30280454/197953693-6af8aa3e-ac5a-443f-bed1-3f1c3479a1da.png)

- Lottery name already exists exception
![img_6](https://user-images.githubusercontent.com/30280454/197953720-6ade798c-b7a8-4d21-87a0-35d47b1c4e21.png)

- Submit lottery by id and username
![img_7](https://user-images.githubusercontent.com/30280454/197953731-bbf1af3e-b1cb-404f-a517-c49c6851abd6.png)

- Username not found exception
![img_9](https://user-images.githubusercontent.com/30280454/197954085-f59b7e72-08b5-4823-a9ef-9e7a9a3f079c.png)

- Lottery not found exception
![img_10](https://user-images.githubusercontent.com/30280454/197954092-47df7dbf-a379-49c8-a2f3-a3a8efa19072.png)

- Fetch all active lotteries
![img_11](https://user-images.githubusercontent.com/30280454/197954130-ac656776-904b-44c6-9665-e14f04d5cf10.png)

- End lottery by id
![img_12](https://user-images.githubusercontent.com/30280454/197954149-c1e28bcd-2575-4fef-97e3-b73d51cf19fa.png)

- Lottery not found to End
![img_13](https://user-images.githubusercontent.com/30280454/197954167-70cffb12-870a-4754-a0b8-ac62f216ebc8.png)

- Fetch lottery result by id and date
![img_14](https://user-images.githubusercontent.com/30280454/197954181-828b5088-3df9-4dab-8eb3-2589a50dc78e.png)

- Winning ballot not found for specified date
![img_16](https://user-images.githubusercontent.com/30280454/197954233-90788ba0-797a-46ca-a202-dfc5b4bd7604.png)

- End all active lotteries
![img_17](https://user-images.githubusercontent.com/30280454/197954261-72a65882-2014-4efd-a702-ca51cbc7e047.png)

