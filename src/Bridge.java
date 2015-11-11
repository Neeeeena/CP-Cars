
class Bridge{
	int k = 9;
	int count = 0; 
	int ccw = 0;
	int cw = 0;
	//boolean on = false;
	//CarControl carControl;
	//Pos critpos[] = new Pos[9];
	//int carsFurther = 0;
	//boolean shutdown = false;
	
   public synchronized void ccwenter(){
	   while(count>k || ccw>k-1){
		   try{wait();}catch(InterruptedException e ){}
	   }
	   
	   count++; 
	   ccw ++;
   }
   
   public synchronized void cwenter(){
	   while(count>k || cw>k-1){
		   try{wait();}catch(InterruptedException e ){}
	   }
	   
	   count++; 
	   cw ++;
   }
   

   public synchronized void ccwleave(){
	 count--;
	 ccw--;
	 notifyAll(); 
   }
   
   public synchronized void cwleave(){
	 count--;
	 cw--;
	 notifyAll(); 
   }

   public synchronized void setLimit(int k){
	   this.k = k; 
	   notifyAll(); 
   }
}

