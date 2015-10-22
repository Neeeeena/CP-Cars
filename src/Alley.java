public class Alley{
	Semaphore sAlley = new Semaphore(1);
	int clockWise = 0;
	int counterWise = 0;
	
	public void enter(int no) throws InterruptedException{
		// 0 = clockwise
		if(no == 0){
			if(clockWise == 0){
				sAlley.P();
				clockWise++;
			} else clockWise ++;
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