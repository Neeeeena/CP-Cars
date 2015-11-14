//Prototype implementation of Car Test class
//Mandatory assignment
//Course 02158 Concurrent Programming, DTU, Fall 2015

//Hans Henrik Løvengreen    Oct 6,  2015

public class CarTest extends Thread {

    CarTestingI cars;
    int testno;

    public CarTest(CarTestingI ct, int no) {
        cars = ct;
        testno = no;
    }

    public void run() {
        try {
            switch (testno) { 
            case 0:
                // Demonstration of startAll/stopAll.
                // Should let the cars go one round (unless very fast)
                cars.startAll();
                sleep(3000);
                cars.stopAll();
                break;
            case 1: 
            	// Demonstration of startCar/stopCar
            	// Should let the cars go one round (unless very fast)
            	
            	// Clockwise, Counterclockwise and 0
            	cars.startCar(1);
            	cars.startCar(5);
            	cars.startCar(0);
            	sleep(3000);
            	cars.stopCar(1);
            	cars.stopCar(5);
            	cars.stopCar(0);            	
            	break; 
            case 2: 
            	// Demonstration of barrierOn/barrierOff
            	
            	// Demonstration of barrierOn/barrierOff when stopping and starting a car
            	// Demonstration of barrierShutDown 
            	// Demonstration of barrierShutDown when stopping and starting a car
            	// Demonstration of removeCar/restoreCar
            	// Demonstration of bridge - setLimit 1, 2, 3, 4, 5, 6
            
            	

            case 19:
                // Demonstration of speed setting.
                // Change speed to double of default values
                cars.println("Doubling speeds");
                for (int i = 1; i < 9; i++) {
                    cars.setSpeed(i,50);
                };
                break;

            default:
                cars.println("Test " + testno + " not available");
            }

            cars.println("Test ended");

        } catch (Exception e) {
            System.err.println("Exception in test: "+e);
        }
    }

}



