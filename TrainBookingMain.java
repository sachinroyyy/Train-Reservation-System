// train booking
package Lecture06_Synchronisation;

public class TrainBookingMain {
    public static void main(String[] args) throws InterruptedException {
        TrainReservation tr = new TrainReservation();
        tr.displayAvailableSeats();


        Thread u1 = new Thread(()->{
            tr.bookSeats("1AC",3);
        });
        Thread u2 = new Thread(()->{
            tr.bookSeats("1AC",3);
        });
        Thread u3 = new Thread(()->{
            tr.bookSeats("2AC",5);
        });
        Thread u4 = new Thread(()->{
            tr.bookSeats("2AC",5);
        });
        Thread u5 = new Thread(()->{
            tr.bookSeats("Sleeper",50);
        });

        u1.start();
        u2.start();
        u3.start();
        u4.start();
        u5.start();

        u1.join();
        u2.join();
        u3.join();
        u4.join();
        u5.join();

        tr.displayAvailableSeats();
    }
}

//==================================================================Train reservation=========================================================================

package Lecture06_Synchronisation;

//one train will have multiple seat types
// 1AC - 100 ,2AC - 11 , 3AC - 300, Sleeper-500
// U1 - 1AC 5 seats
// U2 - 2 AC 3 seats U3 - 2AC 10 seats


import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TrainReservation {
    HashMap<String,Integer> availableSeats;
    HashMap<String, ReentrantLock> seatLocks;

    TrainReservation(){
        availableSeats = new HashMap<>();
        availableSeats.put("2AC",10);
        availableSeats.put("3AC",20);
        availableSeats.put("Sleeper",40);
        availableSeats.put("1AC",5);

        //init the lock hashmap
        seatLocks = new HashMap<>();
        for(String key: availableSeats.keySet()){
            seatLocks.put(key,new ReentrantLock());
        }
    }

    void bookSeats(String seatType, int qty){
            ReentrantLock lock = seatLocks.get(seatType);
            lock.lock();
            if(availableSeats.get(seatType)>=qty){
                int currentAvailable = availableSeats.get(seatType);
                availableSeats.put(seatType,currentAvailable-qty);
            }
            else{
                System.out.println("Enough seats not available");
            }
            lock.unlock();
    }

    void displayAvailableSeats(){
        System.out.println(availableSeats);
    }
}
