# Backgammon

This project is a client-server application that allows playing a backgammon game both on the network and against a bot.

# Technologies:

 - <b>TCP-multithreading</b> for managing an unlimited number of clients and game rooms
 - <b>Processing</b> library for creating an intuitive and friendly GUI
 - <b>JDBC</b> with <b>MariaDB</b> for storing clients accounts

# Server
The server has the following tasks:
 - ensure good management of all game rooms 
 - ensure constant and correct communication between clients in the same room
 - query the database to ensure successful authentication

# Client
The client is based on 3 main screens:
 - The Login Screen ensures the creation of new accounts and allows access to the application only to users who will log in with a valid account.
 - The Menu Screen gives the customer the opportunity to decide what kind of game he wants to start (vs Robot, vs Real Player). To be able to play against a real user, you need to create a game room or join an already created one with a unique code.
 - The Game Screen contains game boards and offers all the specifications of the backgammon game: roll the dice, move pieces, eat pieces, remove pieces, etc.

Also, the client provides an <b>Algorithm</b> in order to let the robot decide which is the best move at a moment. The robot is able to:
 - take enemy pieces if it is not very risky for him
 - covers its exposed pieces that can be taken by the enemy
 - make a 'gate' (triangle in the house with 2 pieces) from 2 different positions when the dice allow
 - make moves so as not to leave positions with a single piece
 - etc.

<img src="https://github.com/andreihaivas6/Tables/blob/main/Table/q1.png">


