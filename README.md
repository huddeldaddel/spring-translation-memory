# Spring Translation Memory

This is a small sample application based on Kotlin and React utilizing the Spring Framework, MongoDB and ElasticSearch. That means you might have to install some additional tools:

* Java 11
* Apache Maven
* Docker

Please make sure they're in your `$PATH` as well.

## Running the application

Open a terminal and `cd` into the project directory. Then run:

* `sudo docker-compose up --build -d`
* `cd ./server`
* `mvn package`
* `java -jar target/translation-challenge-0.0.1-SNAPSHOT.jar &`
* `cd ../client/`
* `npm start`

This will start the server at `http://localhost:8080` and the client at `http://localhost:3000/`.
