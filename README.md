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
* Ride Amount if ``` no. of seats >= 2 ```: No. of Kilometers * No. of Seats * 0.75 * Cost per Kilometer
* Ride Amount if ``` no. of seats = 1 ```: No. of Kilometers * Cost per Kilometer

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
