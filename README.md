# WebFlux and MongoDB services demo

## Goal
   Develop a microservice in Spring Boot with WebFlux, functional endpoints and their unit tests, following the 
conventions of a REST service.
It will have two endpoints, one to save objects and another to list all objects. The service should use DTOs for 
communication with the outside world.
   The microservice will save the data in MongoDB, using Docker. We will have two objects, the first will have id and 
name (nodeRoot) and the second will have these same attributes and, in addition, a description (nodeDesc). We want both 
objects to be stored in the same collection called “node”.

## Structure
   After build docker-compose create two services - containers with internal link "mongodb" into. 
   The first one service - "flux-demo" with connection port: 8780, and the second one - "mongodb" with connection 
port: 27017. 

## WebFlux module
   Outside port:8780. 
   Used endpoints: 
#### For controllers - "http://localhost:8780/node/controller"

   GET request - respond list of all Nodes in type of Flux<@NodeDto>:
   
   POST request - get JSON object with field:  
```json
 {
    "id": "1",                        - *Optional parameter
    "name": "some name",              - *Mandatory parameter
    "description": "description text" - *Optional parameter
 }  
```                                             
                                                
####               For functional interfaces - "http://localhost:8780/node/functional"
   GET request - respond list of all Nodes in type of Mono<@NodeDto>:
   
   POST request - get JSON object with field:  
```json
 {
    "id": "1",                        - *Optional parameter
    "name": "some name",              - *Mandatory parameter
    "description": "description text" - *Optional parameter
 }  
``` 
      
####   MongoDb 
   MongoDb connection: by link "mongodb"
   
## Create and Run
- for clean, build project, create and start docker image by docker-compose.yaml file print in console command:
````
./gradlew clean build && sudo docker-compose up --build

````
- for remove containers from compose:
```
$ sudo docker-compose down
```

## Author
With best regards, Mykola Afanasiev!

## For information
## How install Docker
   For installing Docker Community Edition (CE) on Ubuntu.
   
   1. Update the apt package index and install packages to allow apt to use a repository over HTTPS.
```
   $ sudo apt-get update
```
```
 $ sudo apt install apt-transport-https ca-certificates curl software-properties-common
```  
   2. Add Docker’s official GPG key.
```
   $ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```  
   3. Add the Docker repository to APT sources.
```
   $ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
```   
   4. update the package database with the Docker packages from the newly added repo.
```
   $ sudo apt update
```  
   5. Finally, install Docker.
```
   $ sudo apt install docker-ce
```   
   6. Verify if the Docker is running.
```
   $ sudo systemctl status docker
```   
   This figure shows the status of docker.
   