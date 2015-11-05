class Alley{
	//TODO 
	int nClockwise = 0;
	int nCounterwise = 0;
	
	public synchronized void enterCounterwise(){
		while(nClockwise>0){
			try{wait();}catch(InterruptedException e ){}
		}nCounterwise++;
	}
	public synchronized void enterClockwise(){
		while(nCounterwise>0){
			try{wait();}catch(InterruptedException e ){}
		}nClockwise++;
	}
	
	public synchronized void leaveCounterwise(){
		nCounterwise--;
		if(nCounterwise==0){
			notifyAll();
		}
	}
	public synchronized void leaveClockwise(){
		nClockwise--;
		if(nClockwise==0){
			notifyAll();
		}
	}
	
	
}

