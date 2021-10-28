# Low Level Object Oriented Design

### 1) ride_sharing_app:
Design a Ride Sharing Application where Drivers can offer rides(origin, destination, number of seats), any Rider can request rides(origin, destination, number of seats). 

The program must have the following functionalities:
* Add a Driver
* Add a Rider
* Create a Ride
* Withdraw a Ride
* Update a Ride's information
* Calculate the amount charged for the ride

The program should calculate the amount charged to the Rider after closing the Ride based on the following guidelines:
* Ride Amount if ``` No. of seats >= 2 ```: No. of Kilometers * No. of Seats * 0.75 * Cost per Kilometer
* Ride Amount if ``` No. of seats = 1 ```: No. of Kilometers * Cost per Kilometer

If a Rider takes more than 10 rides then update to Preferred Rider status and follow these guidelines:
* Ride Amount if ``` No. of seats >= 2 ``` and Preferred rider: No. of Kilometers * No. of Seats * 0.5 * Cost per Kilometer
* Ride Amount if ``` No. of seats = 1 ``` and Preferred rider: No. of Kilometers * 0.75 * Cost per Kilometer

### 2) meeting_scheduler:
Design meeting scheduler to book a meeting and return the name of the room booked and also keep the history of all the bookings done.

The program must have the following functionalities:
* Add new Room
* Accept the start time and end time of the meeting, create a booking and return a room for the meeting.
* Show error if all the rooms are booked for the current timeslot.

### 3) task_planner:
Design a Task Planner to create Tasks and Sprints, the User must also be able to add a Task to a Sprint.

The program must have the following functionalities:
* User should be able to create a Task of type Story, Feature, Bug. Each can have their own status.
* Stories can have further subtracts.
* Should be able to change the status of any Task.
* User should be able to create any Sprint. Should be able to add any Task to Sprint and remove from it.

The User must also be able to print:
* Delayed Tasks.
* Sprint details.
* Tasks assigned to the User.

### 4) bowling_system:
Design a system to manage a Bowling game. The system must be able to display and maintain the scores as well as declare a winner at the end of the game.

The program must have the following functionalities:
* Game can be played by multiple players.
* The game consists of 10 frames and each frame has 2 chances to knock off the pins.
* If there is a spare the player gets 5 bonus points and for a strike the player gets 10 bonus points.
* In the final set, a player who rolls a spare or a strike is allowed an extra roll to complete the set. However, only a maximum of 3 balls can be rolled in the final set
* Multiple games can be played in parallel on multiple free lanes.

### 5) snake_ladders:
Design a system to manage a Snakes and Ladders game. The system must show the winner of the game when it ends and also roll a dice for the game to proceed.

The program must have the following functionalities:
* The game can have multiple players.
* Snakes, Ladders and number of players are decided before the game begins and cannot be changed once the game begins.
* The players participate in a round-robin manner and follow their subsequent turns.
* The dice value must be between 1 - 6.
* If a player land on a ladder or a snake then its new position must be updated automatically.
* The game ends when a players reached 100.
* After the game ends, the system should display the final values of each player and also declare the winner.

### 6) notepad:
Design a system to manage a Text Editor Application. The system must be able to display, insert and delete lines from the text editor input.

The program must have the following functionalities:
* Display the entire content
* Display the specified number lines from a given line number.
* Insert a text at given line number.
* Delete text at a given line number.
* Delete all lines between 2 given line numbers.
* Copy all the content between 2 given line numbers.
* Paste the copied content at a given line number.
* Undo the last command.
* Redo the last command.

### 7) employee_manage:
Design a system for Employee Management with an employee structure where each Employee has an ID, name and Manager.

The program must have the following functionalities:
* Add an Employee.
* Add subordinates to a given Employee.
* Register a Manager in the system.
* Return the details for a given Employee ID.
* List all the subordinates of the given Employee for a given ID/name.
* Given a name, search with the prefix search property.

### 8) book_catalog:
Design a Book Catalog system where each Book has name, author, publisher, publish year, category, price and number of copies sold as attributes.

The program must have the following functionalities:
* Add a Book to the catalog.
* Search a book by prefix of the Book name.
* Get all the book written by an author from given author name.
* Get a given number of most sold Books by a given author name.
* Get a given number of most sold Books by a given category. 
