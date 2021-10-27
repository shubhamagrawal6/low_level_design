# Low Level Object Oriented Design

## 1) ride_sharing_app:
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
* Ride Amount if ``` no. of seats >= 2 ``` and Preferred rider: No. of Kilometers * No. of Seats * 0.5 * Cost per Kilometer
* Ride Amount if ``` no. of seats = 1 ```: No. of Kilometers * 0.75 * Cost per Kilometer
