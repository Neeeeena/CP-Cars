//Prototype implementation of Car Control
//Mandatory assignment
//Course 02158 Concurrent Programming, DTU, Fall 2015

//Hans Henrik Løvengreen    Oct 6,  2015

//EOOOW
import java.awt.Color;
import java.util.ArrayList;

class Gate {

    Semaphore g = new Semaphore(0);
    Semaphore e = new Semaphore(1);
    boolean isopen = false;

    public void pass() throws InterruptedException {
        g.P(); 
        g.V();
    }

    public void open() {
        try { e.P(); } catch (InterruptedException e) {}
        if (!isopen) { g.V();  isopen = true; }
        e.V();
    }

    public void close() {
        try { e.P(); } catch (InterruptedException e) {}
        if (isopen) { 
            try { g.P(); } catch (InterruptedException e) {}
            isopen = false;
        }
        e.V();
    }

}

class Car extends Thread {

    int basespeed = 60;             // Rather: degree of slowness
    int variation =  50;             // Percentage of base speed

    CarDisplayI cd;                  // GUI part

    int no;                          // Car number
    Pos startpos;                    // Startpositon (provided by GUI)
    Pos barpos;                      // Barrierpositon (provided by GUI)
    Color col;                       // Car  color
    Gate mygate;                     // Gate at startposition

    int speed;                       // Current car speed
    Pos curpos;                      // Current position 
    Pos newpos;                      // New position to go to
    
    PosSemaphore ps;
    Alley alley;
    Barrier barrier;

    public Car(int no, CarDisplayI cd, Gate g, PosSemaphore ps, Alley alley, Barrier barrier) {
    	
    	this.ps = ps;
    	this.alley = alley;
        this.no = no;
        this.cd = cd;
        this.barrier = barrier;
        mygate = g;
        startpos = cd.getStartPos(no);
        barpos = cd.getBarrierPos(no);  // For later use
        
        col = chooseColor();

        // do not change the special settings for car no. 0
        if (no==0) {
            basespeed = 0;  
            variation = 0; 
            setPriority(Thread.MAX_PRIORITY); 
        }
    }

    public synchronized void setSpeed(int speed) { 
        if (no != 0 && speed >= 0) {
            basespeed = speed;
        }
        else
            cd.println("Illegal speed settings");
    }

    public synchronized void setVariation(int var) { 
        if (no != 0 && 0 <= var && var <= 100) {
            variation = var;
        }
        else
            cd.println("Illegal variation settings");
    }

    synchronized int chooseSpeed() { 
        double factor = (1.0D+(Math.random()-0.5D)*2*variation/100);
        return (int)Math.round(factor*basespeed);
    }

    private int speed() {
        // Slow down if requested
        final int slowfactor = 3;  
        return speed * (cd.isSlow(curpos)? slowfactor : 1);
    }

    Color chooseColor() { 
        return Color.blue; // You can get any color, as longs as it's blue 
    }

    Pos nextPos(Pos pos) {
        // Get my track from display
        return cd.nextPos(no,pos);
    }

    boolean atGate(Pos pos) {
        return pos.equals(startpos);
    }

   public void run() {
        try {

            speed = chooseSpeed();
            curpos = startpos;
            cd.mark(curpos,col,no);

            while (true) { 
                sleep(speed());

                if (atGate(curpos)) { 
                    mygate.pass(); 
                    speed = chooseSpeed();
                }

                newpos = nextPos(curpos);
                //alleyCtrl(no, newpos); 
                enterAlley(no,newpos);
                if(newpos.col == barrier.critpos[no].col && newpos.row==barrier.critpos[no].row){
                	barrier.sync();
                }
                           
                ps.s[newpos.row][newpos.col].P();
                
                
                
                //  Move to new position 
                cd.clear(curpos);
                cd.mark(curpos,newpos,col,no);
                sleep(speed());
                cd.clear(curpos,newpos);
                cd.mark(newpos,col,no);
                leaveAlley(no,curpos);
                ps.s[curpos.row][curpos.col].V();
                
                curpos = newpos;
                
            }

        } catch (Exception e) {
            cd.println("Exception in Car no. " + no);
            System.err.println("Exception in Car no. " + no + ":" + e);
            e.printStackTrace();
        }
    }
   
   public void enterAlley(int no, Pos pos) throws InterruptedException{
	   if( no < 5 && ((pos.col == 1 && pos.row == 8) || (pos.col == 2 && pos.row == 9))){
		   alley.enter(0);
	   }

	   if ( no >= 5 && pos.col == 0 && pos.row == 1) {
		   alley.enter(1); 
	   }
	    
   }
   
   public void leaveAlley(int no, Pos pos) throws InterruptedException{
	   if( no < 5 && pos.col == 2 && (pos.row == 1)){
		   alley.leave(0);
	   }
	   else if ( no >= 5 && pos.col == 2 && pos.row == 10) {
		   alley.leave(1); 
	   }   
   }

}
class PosSemaphore{
	
	public Semaphore[][] s;
	
	public PosSemaphore(int n, int m){
		s = new Semaphore[n][m];
		for(int i=0;i<n;i++){
			for(int j=0;j<m;j++){
				s[i][j] = new Semaphore(1);
			}
		}
	}
}

public class CarControl implements CarControlI{

    CarDisplayI cd;           // Reference to GUI
    Car[]  car;               // Cars
    Gate[] gate;              // Gates
    PosSemaphore ps;
    Alley alley; 
    Barrier barrier;

    public CarControl(CarDisplayI cd) {
        this.cd = cd;
        car  = new  Car[9];
        gate = new Gate[9];
        ps = new PosSemaphore(11,12);
        alley = new Alley();
        barrier = new Barrier(this);

        for (int no = 0; no < 9; no++) {
            gate[no] = new Gate();
            car[no] = new Car(no,cd,gate[no],ps, alley, barrier);
            car[no].start();
        } 
    }
    
    public int drivingCars(){
    	int carsCounter = 0;
    	for(int i=0;i<9;i++)
    	{
    		if(gate[i].isopen){
    			carsCounter++;
    		}
    	}return carsCounter;
    	}

   public void startCar(int no) {
        gate[no].open();
        
    }

    public void stopCar(int no) {
        gate[no].close();
        
    }
    

    public void barrierOn() { 
        barrier.on();
    }

    public void barrierOff() { 
        barrier.off();
    }

    public void barrierShutDown() { 
        cd.println("Barrier shut down not implemented in this version");
        // This sleep is for illustrating how blocking affects the GUI
        // Remove when shutdown is implemented.
        try { Thread.sleep(3000); } catch (InterruptedException e) { }
        // Recommendation: 
        //   If not implemented call barrier.off() instead to make graphics consistent
    }

    public void setLimit(int k) { 
        cd.println("Setting of bridge limit not implemented in this version");
    }

    public void removeCar(int no) { 
        cd.println("Remove Car not implemented in this version");
    }

    public void restoreCar(int no) { 
        cd.println("Restore Car not implemented in this version");
    }

    /* Speed settings for testing purposes */

    public void setSpeed(int no, int speed) { 
        car[no].setSpeed(speed);
    }

    public void setVariation(int no, int var) { 
        car[no].setVariation(var);
    }

}





