class Alley{
	//TODO 
	int nClockwise = 0;
	int nCounterwise = 0;
	
	public synchronized void enterCounterwise() throws InterruptedException{
		while(nClockwise>0){
			System.out.println("nC "+nClockwise + "nCC "+nCounterwise);
			wait();
		}nCounterwise++;
	}
	public synchronized void enterClockwise() throws InterruptedException{
		while(nCounterwise>0){
			System.out.println("nC "+nClockwise + "nCC "+nCounterwise);
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

