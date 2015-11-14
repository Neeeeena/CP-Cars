class Alley{
	//TODO 
	int nClockwise = 0;
	int nCounterwise = 0;
	
	public synchronized void enterCounterwise() throws InterruptedException{
		while(nClockwise>0){
			wait();
		}nCounterwise++;
	}
	public synchronized void enterClockwise() throws InterruptedException{
		while(nCounterwise>0){
			wait();
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

