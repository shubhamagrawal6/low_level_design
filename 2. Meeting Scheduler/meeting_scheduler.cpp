#include<bits/stdc++.h>
using namespace std;

class Room;
class Meeting;
class Scheduler;

typedef unordered_map<int, vector<Meeting>> Calender;

class Room{
    string name;
    Calender calender;

    public:
    Room(string);
    string getName();
    bool book(int,int,int);
};

class Meeting{
    int start; 
    int end;
    Room room;

    public:
    Meeting(int,int,Room);
    int getStart();
    int getEnd();
};

class Scheduler{
    vector<Room> rooms;

    public:
    Scheduler(vector<Room>);
    string book(int,int,int);
};

Room::Room(string name){
    this->name = name;
}

string Room::getName() {
    return this->name;
}

bool Room::book(int day, int start, int end){
    for(Meeting m: calender[day]){
        if(m.getStart() < end && start < m.getEnd()){
            return false;
        }
    }
    Meeting meeting(start, end, *this);
    calender[day].push_back(meeting);
    return true;
}

Meeting::Meeting(int start, int end, Room room) : room(room) {
    this->start = start;
    this->end = end;
}

int Meeting::getStart(){
    return this->start;
}

int Meeting::getEnd(){
    return this->end;
}

Scheduler::Scheduler(vector<Room> rooms){
    this->rooms = rooms;
}

string Scheduler::book(int day, int start, int end){
    for(Room& room: rooms){
        bool flag = room.book(day,start,end);
        if(flag){
            return room.getName();
        }
    }
    return "No rooms available";
}

int main(){
    Room room1("Room 1");
    Room room2("Room 2");
    Room room3("Room 3");

    vector<Room> roomVec;
    roomVec.push_back(room1);
    roomVec.push_back(room2);
    roomVec.push_back(room3);

    Scheduler scheduler(roomVec);

    cout<<scheduler.book(15,2,5)<<endl;
    cout<<scheduler.book(15,5,8)<<endl;
    cout<<scheduler.book(15,4,8)<<endl;
    cout<<scheduler.book(15,3,6)<<endl;
    cout<<scheduler.book(15,7,8)<<endl;
    cout<<scheduler.book(16,6,9)<<endl;

    return 0;
}