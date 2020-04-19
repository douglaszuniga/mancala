# Mancala

Mancala game app is a Spring Boot application exposing a couple of rest endpoints.

A simple front-end app can be found here: https://github.com/douglaszuniga/mancala-fe

## Requirements
 
### Board Setup
Each of the two players has his six pits in front of him. To the right of the six pits,
each player has a larger pit. At the start of the game, there are six stones in each
of the six round pits .

### Rules

#### Game Play
The player who begins with the first move picks up all the stones in any of his
own six pits, and sows the stones on to the right, one in each of the following
pits, including his own big pit. No stones are put in the opponents' big pit. If the
player's last stone lands in his own big pit, he gets another turn. This can be
repeated several times before it's the other player's turn.
#### Capturing Stones
During the game the pits are emptied on both sides. Always when the last stone
lands in an own empty pit, the player captures his own stone and all stones in the
opposite pit (the other player’s pit) and puts them in his own (big or little?) pit.
#### The Game Ends
The game is over as soon as one of the sides runs out of stones. The player who
still has stones in his pits keeps them and puts them in his big pit. The winner of
the game is the player who has the most stones in his big pit.

## Basic Design
This web application exposes a REST interface with two endpoints:
1. One endpoint to generate a new game
2. One endpoint to apply a player's move

The application is `stateless` meaning that is not using a repository, or it is not persisting the game and actions. 
It needs the state of the world in every move request.

## Assumptions

- before ending a turn the web app will try to check and apply all the rules at once
- both players will share the same browser page
- only two players wil play, without an AI

## Endpoint Responses

- on `POST <host>:8080/v1/mancala/game`:
    - `HTTP 201 Created` => success request
- on `PUT <host>:8080/v1/mancala/game/move`
    - `HTTP 400 Bad Request` => when request body is invalid or when player tries to do an invalid move
    - `HTTP 202 Accepted` => move was successfully applied
 
## API Documentation

The application integrates a web-ui page where it is possible to check and interact with the endpoints, visit [swagger-ui](http://localhost:8080/swagger-ui.html#)
    
## Possible Improvements

### Functional

- provide the possibility to keep playing later or restore game
- multiple type of boards and number of pebbles
- real time play

### Non-Functional

- use a repository to save the games and actions
- add resiliency capabilities (circuit-breakers, retries)
- add a cache
- create dockerfile to create images
- use reactive programming
- use `Records` instead of data classes
- regression and load testing

## Implementation details

The game follows these structure:

- The board is using an array of integers, size 14
- it is divided in two sections, one for each player
- Each section has 5 pits and one mancala
- Player One Section goes from index 0 to 6, being index 6 the mancala
- Player Two Section goes from index 7 to 13, being index 13 the mancala
- at the end of each turn a list of events will be included
- each player move must contain the state of the board

The workflow of `POST <host>:8080/v1/mancala/game` is:

- first receives a request from user
- using a `supplier`, the app generates a new fresh game state
- returns the next Turn information
    - assume that player one is the first playing

The workflow of `PUT <host>:8080/v1/mancala/game/move` is:

- first validate the move, 
- validations: 
    - is inside the board, is in player section, pit has pebbles, is not a mancala
- then a function will start dropping pebbles in each pit
- finally, a set of rules are validated, each rule has a `condition` and a `consequence`, this last one can change the state of the board 
- rules:
    - extra turn rule
    - capture pebbles rule
    - game end rule    


### Tech Stack

- Java 14
- Spring Boot 2.2.6
- Spring Web Starter
- Lombok, help to remove some verbosity
- Apache Commons, mostly used for `StringUtils`
- JUnit 5
- Mockito 3
- Maven 

### How to set up lombok in the IDE

In order to use lombok in the IDE follow the instructions found in these links:

- [Intellij](https://projectlombok.org/setup/intellij)
- [Eclipse](https://projectlombok.org/setup/eclipse)

## How to build

compile:

```shell script
mvn clean compile
```

run tests:

```shell script
mvn verify
```

package:

```shell script
mvn package
```
 
run the application

```shell script
mvn spring-boot:run
```

or 

```shell script
java -jar target/mancala-1.0.0-RELEASE.jar
```

# Author

Douglas Zuniga