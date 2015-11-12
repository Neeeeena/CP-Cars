
class Bridge{
	int k = 9;
	int count = 0; 

	
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

