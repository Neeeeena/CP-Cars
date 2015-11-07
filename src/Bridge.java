
class Bridge{
	int k = 9;
	int count = 0; 
	//boolean on = false;
	//CarControl carControl;
	//Pos critpos[] = new Pos[9];
	//int carsFurther = 0;
	//boolean shutdown = false;
	
   public synchronized void enter(){
	   while(k<count){
		   try{wait();}catch(InterruptedException e ){}
	   }
	   
	   count++; 
   }

   public synchronized void leave(){
	 count--;
	 notifyAll(); 
   }

   public synchronized void setLimit(int k){
	   this.k = k; 
	   notifyAll(); 
   }
}

