import java.io.*;
import java.util.*;

class Room{
    private String name;
    private HashMap<Integer, ArrayList<Meeting>> calender = new HashMap<Integer, ArrayList<Meeting>>() ;

    public Room(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public boolean book(int day, int start, int end){
        if((calender.containsKey(day)) == false){
            calender.put(day, new ArrayList<Meeting>());
        }
        for(Meeting m: calender.get(day)){
            if((m.getStart() < end) && (start < m.getEnd())){
                return false;
            }
        }
        Meeting meeting = new Meeting(start, end, this);
        if(calender.containsKey(day)){
            calender.get(day).add(meeting);
        }
        else{
            calender.put(day, new ArrayList<Meeting>());
            calender.get(day).add(meeting);
        }
        
        return true;
    }
}

class Meeting{
    private int start;
    private int end;
    private Room room;

    public Meeting(int start, int end, Room room){
        this.start = start;
        this.end = end;
        this.room = room;
    }

    public int getStart(){
        return this.start;
    }

    public int getEnd(){
        return this.end;
    }
}

class Scheduler{
    private ArrayList<Room> rooms;

    public Scheduler(ArrayList<Room> rooms){
        this.rooms = rooms;
    }

    public String book(int day, int start, int end){
        for(Room room: rooms){
            boolean flag = room.book(day, start, end);
            if(flag){
                return room.getName();
            }
        }
        return "No Rooms Available";
    }
}

public class meeting_scheduler{
    public static void main(String[] args){
        Room room1 = new Room("Room 1");
        Room room2 = new Room("Room 2");
        Room room3 = new Room("Room 3");

        ArrayList<Room> roomList = new ArrayList<Room>();
        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);

        Scheduler scheduler = new Scheduler(roomList);

        System.out.println(scheduler.book(15,2,5));
        System.out.println(scheduler.book(15,5,8));
        System.out.println(scheduler.book(15,4,8));
        System.out.println(scheduler.book(15,3,6));
        System.out.println(scheduler.book(15,7,8));
        System.out.println(scheduler.book(16,6,9));
    }
}