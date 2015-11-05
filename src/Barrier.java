import java.lang.reflect.Array;
import java.util.ArrayList;


class Barrier {
	
	private boolean barrier = false;
	Pos critpos[] = new Pos[9];
	Semaphore mutex = new Semaphore(1);
	int count = 0;
	Semaphore bar = new Semaphore(0);
	CarControl carControl;
	
	public Barrier(CarControl cc){
		carControl = cc;
		critpos[0] = new Pos(5,3);
		critpos[1] = new Pos(5,4);
		critpos[2] = new Pos(5,5);
		critpos[3] = new Pos(5,6);
		critpos[4] = new Pos(5,7);
		critpos[5] = new Pos(4,8);
		critpos[6] = new Pos(4,9);
		critpos[7] = new Pos(4,10);
		critpos[8] = new Pos(4,11);
		
	}

   public void sync() throws InterruptedException { 
	   System.out.println("1bar: "+bar);
	   if(barrier){
		   mutex.P();
		   count++;
		   mutex.V();
		   
		   int noOfCars = carControl.drivingCars();
		   System.out.println("count: "+count+" noOfCars: "+noOfCars);
		   System.out.println("bar: "+bar);
		   if(count==noOfCars){
			   System.out.println("come on!");
			   for(int i=0;i<noOfCars-1;i++){
				   bar.V();
			   }
			   System.out.println("bar: "+bar);
			   count=0;
		   }else{bar.P();System.out.println("bar: "+bar);}

	   }
	   
   }  // Wait for others to arrive (if barrier active)

   public void on() {  
	   barrier = true;
   }    // Activate barrier

   public void off() { 
	   barrier = false;
	   for(int i=0;i<count;i++){
		   bar.V();
	   }
   }   // Deactivate barrier 

}