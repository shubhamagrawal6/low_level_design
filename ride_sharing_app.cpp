#include<bits/stdc++.h>
using namespace std;

class Person;
class Driver;
class Rider;
class Ride;
class System;

enum RideStatus {
	IDLE,
	CREATED,
	WITHDRAWN,
	COMPLETED
};

class Person {
	public:
	string name;
};

class Driver : private Person {
	public:
	Driver(string);
};

class Rider : private Person {
	int id;
	vector<Ride> allRides;

	public:
	Rider(int, string);
	int getId();
	void createRide(int,int,int,int);
	void updateRide(int,int,int,int);
	void withdrawRide(int);
	int closeRide(int);
};

class Ride {
	int id;
	int origin;
	int dest;
	int seats;
	RideStatus rideStatus;

	public:
	static const int AMT_PER_KM = 20;
	Ride();
	int calcFare(bool);
	int getId();
	void setId(int);
	void setOrigin(int);
	void setDest(int);
	void setSeats(int);
	RideStatus getRideStatus();
	void setRideStatus(RideStatus);
};

class System{
	int drivers;
	vector<Rider> riders;

	public:
	System(int, vector<Rider>&);
	void createRide(int,int,int,int,int);
	void updateRide(int,int,int,int,int);
	void withdrawRide(int,int);
	int closeRide(int,int);
};

Driver::Driver(string name) {
	this->name = name;
}

Rider::Rider(int id, string name){
	this->id = id;
	this->name = name;
}

int Rider::getId(){
	return this->id;
}

void Rider::createRide(int id, int origin, int dest, int seats){
	try{
		if(origin >= dest){
			throw 1;
		}
	}
	catch(...){
		cout<<"Wrong values of Origin and Destination provided. Can't create ride\n";
		return;
	}
	Ride currentRide;
	currentRide.setId(id);
	currentRide.setOrigin(origin);
	currentRide.setDest(dest);
	currentRide.setSeats(seats);
	currentRide.setRideStatus(RideStatus::CREATED);
	allRides.push_back(currentRide);
}

void Rider::updateRide(int id, int origin, int dest, int seats){
	auto iter = allRides.rbegin();
	while(iter != allRides.rend()){
		if(iter->getId() == id){
			break;
		}
		iter++;
	}
	try{
		if(iter->getRideStatus() != RideStatus::CREATED){
			throw 1;
		}
	}
	catch(int x){
		if(x == 1){
			cout<<"Can't update ride. Ride was not in progress\n";
		}
		return;
	}
	iter->setOrigin(origin);
	iter->setDest(dest);
	iter->setSeats(seats);
}

void Rider::withdrawRide(int id){
	auto iter = allRides.rbegin();
	while(iter != allRides.rend()){
		if(iter->getId() == id){
			break;
		}
		iter++;
	}
	try{
		if(iter->getRideStatus() != RideStatus::CREATED){
			throw 1;
		}
	}
	catch(int x){
		if(x == 1){
			cout<<"Can't withdraw ride. Ride was not in progress\n";
		}
		return;
	}
	iter->setRideStatus(RideStatus::WITHDRAWN);
	allRides.erase((iter+1).base());
}

int Rider::closeRide(int id){
	auto iter = allRides.rbegin();
	while(iter != allRides.rend()){
		if(iter->getId() == id){
			break;
		}
		iter++;
	}
	try{
		if(iter->getRideStatus() != RideStatus::CREATED){
			throw 1;
		}
	}
	catch(int x){
		if(x == 1){
			cout<<"Ride was not in progress. Can't close ride\n";
		}
		return 0;
	}
	iter->setRideStatus(RideStatus::COMPLETED);
	return iter->calcFare(allRides.size() >= 10);
}

Ride::Ride(){
	id = origin = dest = seats = 0;
	rideStatus = RideStatus::IDLE;
}

int Ride::calcFare(bool isPriorityRider){
	int dist = dest - origin;
	if(seats < 2){
		return dist * AMT_PER_KM * (isPriorityRider ? 0.75 : 1);
	}
	return dist * seats * AMT_PER_KM * (isPriorityRider ? 0.5 : 0.75);
}

int Ride::getId() {
	return this->id;
}

void Ride::setId(int id) {
	this->id = id;
}

void Ride::setDest(int dest) {
	this->dest = dest;
}

void Ride::setOrigin(int origin) {
	this->origin = origin;
}

void Ride::setSeats(int seats) {
	this->seats = seats;
}

RideStatus Ride::getRideStatus() {
	return this->rideStatus;
}

void Ride::setRideStatus(RideStatus rideStatus) {
	this->rideStatus = rideStatus;
}

System::System(int drivers, vector<Rider>& riders){
	if(drivers < 2 || riders.size() < 2){
		cout<<"Not enough drivers or riders\n";
	}
	this->drivers = drivers;
	this->riders = riders;
}

void System::createRide(int riderId, int rideId, int origin, int dest, int seats){
	if(drivers == 0){
		cout<<"No drivers around. Can't create ride\n";
		return;
	}
	for(Rider& rider: riders){
		if(rider.getId() == riderId){
			rider.createRide(rideId, origin, dest, seats);
			drivers--;
			return;
		}
	}
	cout<<"Rider is not registered."<<endl;
}

void System::updateRide(int riderId, int rideId, int origin, int dest, int seats){
	for(Rider& rider: riders){
		if(rider.getId() == riderId){
			rider.updateRide(rideId, origin, dest, seats);
			return;
		}
	}
	cout<<"Rider is not registered."<<endl;
}

void System::withdrawRide(int riderId, int rideId){
	for(Rider& rider: riders){
		if(rider.getId() == riderId){
			rider.withdrawRide(rideId);
			drivers++;
			return;
		}
	}
	cout<<"Rider is not registered."<<endl;
}

int System::closeRide(int riderId, int rideId){
	for(Rider& rider: riders){
		if(rider.getId() == riderId){
			drivers++;
			return rider.closeRide(rideId);
		}
	}
	cout<<"Rider is not registered."<<endl;
	return 0;
}

int main(){
	Rider rider1(1, "Rider 1");
	Rider rider2(2, "Rider 2");
	Rider rider3(3, "Rider 3");

	Driver driver("Driver 1");

	vector<Rider> riders;
	riders.push_back(rider1);
	riders.push_back(rider2);
	riders.push_back(rider3);
	System system(3, riders);

	rider1.createRide(1, 50, 60, 1);
	cout<<rider1.closeRide(1)<<endl;
	rider1.updateRide(1, 50, 60, 2);
	cout<<rider1.closeRide(1)<<endl;

	cout<<"*****************************************************************"<<endl;

	system.createRide(1, 1, 50, 60, 1);
	system.withdrawRide(1, 1);
	system.updateRide(1, 1, 50, 60, 2);
	cout<<system.closeRide(1, 1)<<endl;

	cout<<"*****************************************************************"<<endl;

	system.createRide(1, 1, 50, 60, 1);
	system.updateRide(1, 1, 50, 60, 2);
	cout<<system.closeRide(1, 1)<<endl;
	return 0;
}