class Alley{
	int nClockwise = 0;
	int nCounterwise = 0;
	int nCounterwise12 = 0;
	
	public synchronized void enterCounterwise(){
		while(nClockwise>0){
			try{wait();}catch(InterruptedException e ){}
		}
		nCounterwise++;
		nCounterwise12++;
	}
	public synchronized void enterClockwise12(){
		while(nCounterwise12>0){
			try{wait();}catch(InterruptedException e ){}
		}nClockwise++;
	}
	public synchronized void enterClockwise(){
		while(nCounterwise>0){
			try{wait();}catch(InterruptedException e){}
		}nClockwise++;
	}
	
	public synchronized void leaveCounterwise12(){
		nCounterwise12--;
		if(nCounterwise12==0){
			notifyAll();
		}
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

