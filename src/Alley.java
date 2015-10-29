public class Alley{
	Semaphore sAlley = new Semaphore(1);
	int clockWise = 0;
	int counterWise = 0;
	Semaphore firstc = new Semaphore(1);
	Semaphore firstcc = new Semaphore(1);
	Semaphore protectcw = new Semaphore(1);
	Semaphore protectccw = new Semaphore(1);

	
	
	public void enter(int no) throws InterruptedException{
		System.out.println("sAlley: "+sAlley);
		// 0 = clockwise
		if(no == 0){
			firstc.P();
			if(clockWise == 0){
				sAlley.P();
				clockWise++;
			} else clockWise ++; 
			firstc.V();
		} else{
			firstcc.P();
			if(counterWise == 0){
				sAlley.P();
				counterWise++;
			} else counterWise ++;
			firstcc.V();
		}
	}
	
	public void leave(int no) throws InterruptedException{
		if(no == 0){
			if(clockWise == 1){
				sAlley.V();
			}
			protectcw.P();
			clockWise--;
			protectcw.V();
		} else{
			if(counterWise == 1){
				sAlley.V();
			}
			protectccw.P();
			clockWise--;
			protectccw.V();
		}
	}
}