Multiplayer Battle Ship Game
==============================
Java base Battleship game. It support playing against the computer, or against another player on a different computer.

How to build the package
-
1. Install Maven 3
2. Clone the repo and run maven command:
```bash
mvn clean package
```
3. You will get two executable jar "battle-ship-server.jar" and "battle-ship-client.jar".

In order to run the application for playing with computer do the following: <br/>
-
1. Open new terminal navigate to clone directory and run command.
```bash
java -jar battle-ship-client.jar
```

In order to run the application for playing against another player on a different computer do the following: <br/>
-
1. Open new terminal navigate to clone directory.
```bash
java -jar battle-ship-server.jar
```
2. Open new terminal tab and Run first client using command.
```bash
java -jar battle-ship-client.jar
```

3. Open new terminal tab and Run second client using command.
```bash
java -jar battle-ship-client.jar
```