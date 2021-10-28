import java.io.*;
import java.util.*;

enum RideStatus{
    IDLE, CREATED, WITHDRAWN, COMPLETED;
}

class Ride{
    private int id;
	private int origin;
	private int dest;
	private int seats;
	private RideStatus rideStatus = RideStatus.IDLE;

    public static final int AMT_PER_KM = 20;
	
    public Ride(){
        this.id = 0;
        this.origin = 0;
        this.dest = 0;
        this.seats = 0;
        this.rideStatus = RideStatus.IDLE;
    }
	
    public int calcFare(boolean isPriorityRider){
        int dist = this.dest - this.origin;
        if(this.seats < 2){
            return (int) (dist * this.AMT_PER_KM * (isPriorityRider ? 0.75 : 1));
        }
        
        return (int) (dist * this.seats * this.AMT_PER_KM * (isPriorityRider ? 0.5 : 0.75));
    }
	
    public int getId(){
        return this.id;
    }

	public void setId(int id){
        this.id = id;
    }

	public void setOrigin(int origin){
        this.origin = origin;
    }

	public void setDest(int dest){
        this.dest = dest;
    }

	public void setSeats(int seats){
        this.seats = seats;
    }

	public RideStatus getRideStatus(){
        return this.rideStatus;
    }

	public void setRideStatus(RideStatus rideStatus){
        this.rideStatus = rideStatus;
    }
}

class Person{
    public String name;
}

class Driver extends Person{
    public Driver(String name){
        this.name = name;
    }
}

class Rider extends Person{
    private int id;
    private ArrayList<Ride> allRides = new ArrayList<Ride>();

    public Rider(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public void createRide(int id, int origin, int dest, int seats){
        try{
            if(origin >= dest){
                throw new ArithmeticException();
            }
        } 
        catch (Exception e) {
            System.out.println("Wrong values of Origin and Destination provided. Can't create ride");
        }

        Ride currentRide = new Ride();
        currentRide.setId(id);
        currentRide.setOrigin(origin);
        currentRide.setDest(dest);
        currentRide.setSeats(seats);
        currentRide.setRideStatus(RideStatus.CREATED);
        allRides.add(currentRide);
    }

    public void updateRide(int id, int origin, int dest, int seats){
        int n = allRides.size();
        for(int i=n-1; i>=0; i--){
            if(allRides.get(i).getId() == id){
                try{
                    if(allRides.get(i).getRideStatus() != RideStatus.CREATED){
                        throw new ArithmeticException();
                    }
                } 
                catch(Exception e){
                    System.out.println("Can't update ride. Ride was not in progress");
                    return;
                }

                allRides.get(i).setOrigin(origin);
                allRides.get(i).setDest(dest);
                allRides.get(i).setSeats(seats);
                return;
            }
        }
    }

    public void withdrawRide(int id){
        int n = allRides.size();
        for(int i=n-1; i>=0; i--){
            if(allRides.get(i).getId() == id){
                if(allRides.get(i).getRideStatus() != RideStatus.CREATED){
                    System.out.println("Can't withdraw ride. Ride was not in progress");
                    return;
                }
                allRides.get(i).setRideStatus(RideStatus.WITHDRAWN);
                allRides.remove(i);
                return;
            }
        }
    }

    public int closeRide(int id){
        int n = allRides.size();
        for(int i=n-1; i>=0; i--){
            if(allRides.get(i).getId() == id){
                try{
                    if(allRides.get(i).getRideStatus() != RideStatus.CREATED){
                        throw new ArithmeticException();
                    }
                } 
                catch(Exception e){
                    System.out.println("Can't close ride. Ride was not in progress");
                    return 0;
                }
        
                allRides.get(i).setRideStatus(RideStatus.COMPLETED);
                return allRides.get(i).calcFare(allRides.size() >= 10);
            }
        }

        return 0;
    }
}

class Sys{
    private int drivers;
    private ArrayList<Rider> riders = new ArrayList<Rider>();

    public Sys(int drivers, ArrayList<Rider> riders){
        if(drivers < 2 || riders.size() < 2){
            System.out.println("Not enough drivers or riders");
            return;
        }

        this.drivers = drivers;
        this.riders = riders;
    }

	public void createRide(int riderId, int rideId, int origin, int dest, int seats){
        if(drivers == 0){
            System.out.println("No drivers around. Can't create ride");
            return;
        }

        for(Rider rider: riders){
            if(rider.getId() == rideId){
                rider.createRide(rideId, origin, dest, seats);
                this.drivers--;
                return;
            }
        }

        System.out.println("Rider is not registered.");
    }

	public void updateRide(int riderId, int rideId, int origin, int dest, int seats){
        int n = riders.size();
        for(int i=n-1; i>=0; i--){
            if(riders.get(i).getId() == riderId){
                riders.get(i).updateRide(rideId, origin, dest, seats);
                return;
            }
        }

        System.out.println("Rider is not registered.");
    }

	public void withdrawRide(int riderId, int rideId){
        int n = riders.size();
        for(int i=n-1; i>=0; i--){
            if(riders.get(i).getId() == riderId){
                riders.get(i).withdrawRide(rideId);
                this.drivers++;
                return;
            }
        }

        System.out.println("Rider is not registered.");
    }

	public int closeRide(int riderId, int rideId){
        int n = riders.size();
        for(int i=n-1; i>=0; i--){
            if(riders.get(i).getId() == riderId){
                this.drivers++;
                return riders.get(i).closeRide(rideId);
            }
        }

        System.out.println("Rider is not registered.");
        return 0;
    }
}

public class ride_sharing_app{
    public static void main(String[] args){
        Rider rider1 = new Rider(1, "Rider 1");
        Rider rider2 = new Rider(2, "Rider 2");
        Rider rider3 = new Rider(3, "Rider 3");

        Driver driver = new Driver("Driver 1");

        ArrayList<Rider> riders = new ArrayList<Rider>();
        riders.add(rider1);
        riders.add(rider2);
        riders.add(rider3);
        Sys sys = new Sys(3, riders);

        rider1.createRide(1, 50, 60, 1);
        System.out.println(rider1.closeRide(1));
        rider1.updateRide(1, 50, 60, 2);
        System.out.println(rider1.closeRide(1));

        System.out.println("*****************************************************************");

        sys.createRide(1, 1, 50, 60, 1);
        sys.withdrawRide(1, 1);
        sys.updateRide(1, 1, 50, 60, 2);
        System.out.println(sys.closeRide(1, 1));

        System.out.println("*****************************************************************");

        sys.createRide(1, 1, 50, 60, 1);
        sys.updateRide(1, 1, 50, 60, 2);
        System.out.println(sys.closeRide(1, 1));
    }
}