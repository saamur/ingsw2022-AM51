# Software Engineering final project 2022 
<img src="https://github.com/saamur/ingsw2022-AM51/blob/6af32eef33b6b3f73380fdf564e193114849fa67/src/main/resources/png/logo_eriantys.png" width=280px height=280px align="right" />
Eriantys is the final project of Software Engineering course held
at Politecnico di Milano. (2021/2022)


**Teacher**: Alessandro Margara

## Project specification
The project consists of a Java version of the board game *Eriantys*, made by Cranio Creation

<br>
<br>


## Authors [Gruppo AM51]
- ### 10667290 [Giulia Cornetta](https://github.com/giuCornetta)<br>giulia.cornetta@mail.polimi.it
- ### 10706257 [Federica Del Beato](https://github.com/FedericaDelBeato)<br>federica.delbeato@mail.polimi.it
- ### 10730188 [Samuele Delpero](https://github.com/saamur)<br> samuele.delpero@mail.polimi.it
<br>




## Implemented Functionalities
| Functionality                    | State  |
|:---------------------------------|:------:|
| Basic rules                      |   🟢  |
| Complete rules                   |   🟢  |
| Socket                           |   🟢  |
| GUI                              |   🟢  |
| CLI                              |   🟢  |
| Multiple games                   |   🟢  |
| Persistence                      |   🟢  |
| CharacterCards                   |   🟢  |
| 4-player games                   |   🔴  |
| resilience to disconnections     |   🔴  |


#### Legend
🔴 Not implemented<br>
🟡 Implementing<br>
🟢 Implemented<br>

## Coverage
| Element          | Class % | Method % | Line % | 
|------------------|---------|----------|--------|
| model            |   100%  |    91%   |   83%  | 
|   &nbsp; charactercards |   100%  |    96%   |   91%  |
|   &nbsp; clouds         |   100%  |   100%   |  100%  | 
|   &nbsp; islands        |   100%  |    96%   |   96%  | 
|   &nbsp; player         |   100%  |   100%   |  100%  |

## Requirements
For the implementation of this game, Java 17 was used.
Server and both CLI and GUI Client can run on all operative systems

# How to play using the JAR
There is a single file, both for client and for server.
<br>
## LINUX or WINDOWS
### Server
To start the server, open a terminal and go to the directory in which you have saved the jar file. After doing it, run this command on the Command Prompt:<br>
```sh
java -jar Eriantys-Linux-Windows.jar server
```
After that you can leave the default port, 5555, or select a new one.
The server is now online.
<br>
### Client
To start the client, open a terminal and go to the directory in which you have saved the jar file. After that, run this command on the Command Prompt:<br>
```sh
java -jar Eriantys-Linux-Windows.jar
```
followed by "cli" if you want to play with the CLI, and "gui" or no parameter at all if you prefer to play with the GUI.
<br>

## MAC
### Server 
To start the server, open a terminal and go to the directory in which you have saved the jar file. After doing it, run this command on the Command Prompt:<br>
```sh
java -jar Eriantys-Mac.jar server
```
After that you can leave the default port, 5555, or select a new one.
The server is now online.
<br>
### Client
To start the client, open a terminal and go to the directory in which you have saved the jar file. After that, run this command on the Command Prompt:<br>
```sh
java -jar Eriantys-Mac.jar
```
followed by "cli" if you want to play with the CLI, and "gui" or no parameter at all if you prefer to play with the GUI.
<br>
<br>
<br>
<br>

It is also possible to run the GUI by clicking twice on the jar file.<br>
It is possible to play a game with a minimum of two players and a maximum of three.<br>
The game will create two folders in which it will save the unfinished games that can be reopened in the future.
## Eriantys' rules 
You can find the rules of the game [here](https://craniointernational.com/2021/wp-content/uploads/2021/06/Eriantys_rules_small.pdf).

# Tools
* Astah UML - UML Diagrams
* Maven - Dependency Management
* Intellij - IDE
* JavaFX - GUI

## License
This project is developed in collaboration with [Politecnico di Milano](https://www.polimi.it/) and [Cranio Creations](https://www.craniocreations.it/)

