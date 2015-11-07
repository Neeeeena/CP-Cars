import java.lang.reflect.Array;
import java.util.ArrayList;


class Barrier {
	int count = 9;
	boolean on = false;
	CarControl carControl;
	Pos critpos[] = new Pos[9];
	int carsFurther = 0;
	boolean shutdown = false;
	
	public Barrier(CarControl carC){
		carControl = carC;
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
	
	public synchronized void sync(){
		if(on){
			count++;

			while(count!=carControl.drivingCars()){
				try{wait();}catch(InterruptedException e ){}
			}

			carsFurther++;
			System.out.println("carsFurther" + carsFurther);
			System.out.println("driving cars: " + carControl.drivingCars());
			if(carsFurther != carControl.drivingCars()){
				notify();
			}else{
				count = 0;
				carsFurther=0;
				if(shutdown){
					on = false;
				}
			}
			//notifyAll();
			
		}
	}
	
	public synchronized void shutdown(){
		shutdown = true;
		
	}
	
	public synchronized void on(){
		on = true;
		count = 0;
		carsFurther = 0;
		shutdown = false; 
	}
	
	public synchronized void off(){
		on = false;
		count = carControl.drivingCars();
		notifyAll();
	}
		
	
}