## DOCKER SETUP

##### 1. GET IMAGE 

 - **From docker hub** 
    - Because I want to make setting up the application as easy as possible
     I already pushed the image on docker hub so `docker-compose up` should work by default.
     It can be found under name : `nejcr/backend-task-nr0811`
  
  - **Manual build** 
     - You can also make your own build of an image by running :   `gradlew bootBuildImage --imageName=nejcr/backend-task-nr0811:latest  -Dorg.gradle.java.home="{path_to_jdk}"` 
     
 
##### 2. Run IT WITH DOCKER-COMPOSE
   
  - Navigate to [source](../backend-task/)
  - Run `docker-compose up`
  - Go to [OPENAPI-SPEC](http://localhost:8042/api/openapi.html) to explore the API


## MANUAL SETUP
You need jdk 14 installed on your machine. The easiest way to do that is just by using IntelliJ Idea and download it from there.
You would also a postgres database running on port 15433
Change [application-properites](../backend-task/src/main/resources/application.properties) accordingly
The easiest way to do that is by running :  

``docker run --name test-data-db -e POSTGRES_DB=test-data-db -e POSTGRES_USER=cund -e POSTGRES_PASSWORD=cund -d  -p 15433:5432 postgres``  

Application has these exact properties already under resources so by executing   ``gradlew bootRun``  application should be up and running. 




