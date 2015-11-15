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
	   
	   if(barrier){
		   mutex.P();
		   count++;
		   mutex.V();

		   if(count==carControl.drivingCars()){

			   for(int i=0;i<carControl.drivingCars()-1;i++){
				   bar.V();
			   }

			   count=0;
		   }else{bar.P();}

	   }
	   
   } 
   
   public boolean check(){
	   return (count==carControl.drivingCars());
   }
   
   public void freeCars(){
	   for(int i=0;i<carControl.drivingCars();i++){
		   bar.V();
	   }
	   count=0;
   }

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