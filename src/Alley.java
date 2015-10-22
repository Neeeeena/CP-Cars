public class Alley{
	Semaphore sAlley = new Semaphore(1);
	int clockWise = 0;
	int counterWise = 0;
	Semaphore first = new Semaphore(1);

	
	
	public void enter(int no) throws InterruptedException{
		// 0 = clockwise
		if(no == 0){
			first.P();
			if(clockWise == 0){
				sAlley.P();
				clockWise++;
			} else clockWise ++; 
			first.V();
		} else{
			if(counterWise == 0){
				sAlley.P();
				counterWise++;
			} else counterWise ++;
		}
	}
	

	public void leave(int no) throws InterruptedException{
		if(no == 0){
			if(clockWise == 1){
				sAlley.V();
				clockWise--;
			} else clockWise --;
		} else{
			if(counterWise == 1){
				sAlley.V();
				counterWise--;
			} else counterWise --;
		}
	}
}