/**
 *  This class is purposed to hold the inventory of a simple car wash, disregarding the number of stands.
 */
package car.Management;

import automobiles.*;
import java.util.*;

/**
 * <p>
 * <h3> Class description </h3>
 * This class is purposed to hold the inventory of a simple car wash, disregarding the number of stands.
 * <p>
 * @author Razvan Radu
 */
public class CarWash {
	
	private ArrayList<Car> carStand = new ArrayList<Car>();
	private int lastWashedIndex = 0;
	private boolean shopClosed;
	
		
	/**
	 * Default constructor, creating an empty instance.
	 */
	public CarWash(){
		this.shopClosed = true;
	}
	
	/**
	 * In case the car wash is closed, the method initializes the database to null.
	 * In case the car wash is already open with a previous call to the openShop method, a warning message is printed and the database is not reset.
	 */
	public void openShop(){
		if (shopClosed) {
			this.carStand.clear();
			this.lastWashedIndex = 0;
			shopClosed = false;
		}
		else {
			System.out.println("Car wash already open");
		}
	}
	
	/**
	 * In case the car wash is open, the method adds the first car in the queue to the list of clients (cars that have been washed).
	 * <p>
	 * In case the car wash is closed, an error message is printed, without stopping the execution.
	 */
	public void washCar(){
		if (!shopClosed) {
			this.lastWashedIndex ++;
		}
		else {
			System.out.println("Car wash is closed, you can only add a new car to the queue with the standInLine(Car) method");
			System.out.println("Or you can open the car wash with the openShop() method");
		}
	}
	
	/**
	 * The standInLine method adds a car type to the washing queue.
	 * <p>
	 * The permitted types are listed below. In case an illegal type is passed, an error message is printed.
	 * <p>
	 * @param car A type of {@link Car} ({@link Logan}, {@link Duster}, {@link Astra}, {@link Insignia})
	 */
	public void standInLine(Car car){
		if ((!this.carStand.contains(car)) && ((car instanceof Logan) || (car instanceof Duster) || (car instanceof Astra) || (car instanceof Insignia))) {		
			this.carStand.add(car);
		}
		else { 
			System.out.println("Invalid car type " + car + " not added to list");
		}
	}
	
	/**
	 * The closeShop method produces the freezing of the database.
	 */
	public void closeShop(){
		shopClosed = true;
	}
	
	/**
	 * The getTodaysClients method returns an unique {@link List} of {@link Car} ({@link Logan}, {@link Duster}, {@link Astra}, {@link Insignia}) objects that have been marked as washed.
	 * <p>
	 * The method can be called even if a closeShop method was not prior called. 
	 */
	public List<Car> getTodaysClients(){
		
		// In case all cars have been washed, return the queue.
		if(lastWashedIndex == this.carStand.size()-1){
			List todayList = this.carStand;
			return todayList;
		}
		// In case no cars have been washed, return a null list.
		else if (lastWashedIndex == 0) {
			List todayList = new ArrayList();
			return todayList;
		}
		// Default situation : a number of cars have been washed, iterating through the queue.
		else {
			ArrayList<Car> newlist = new ArrayList<Car>();
			for(int i = 0 ; i < lastWashedIndex ; i ++){
				newlist.add(this.carStand.get(i));
			}
			Set todaysClients = new HashSet(newlist);
			List todayList = new ArrayList(todaysClients);
			return todayList;
		}
		
	}
	
	/**
	 * The getPostponedClients method returns an unique list of {@link List} of {@link Car} ({@link Logan}, {@link Duster}, {@link Astra}, {@link Insignia}) objects that have not yet been marked as washed.
	 * <p>
	 * The method can be called even if a closeShop method was not prior called. 
	 */
	public List<Car> getPostponedClients(){
		
		// In case all cars have been washed, return a null list.
		if(lastWashedIndex == this.carStand.size()-1){
			List postponedList = new ArrayList();
			postponedList.add(null);
			return postponedList;
		}
		// In case no car has been washed, return the queue.
		else if (lastWashedIndex == 0){
			List postponedList = this.carStand;
			return postponedList;
		}
		// Default situation : a number of cars have been washed, iterating through the queue.
		else {
			ArrayList<Car> newlist = new ArrayList<Car>();
			for(int i = lastWashedIndex ; i < this.carStand.size() ; i ++){
				newlist.add(this.carStand.get(i));
			}
			Set postponedClients = new HashSet(newlist);
			List postponedList = new ArrayList(postponedClients);
			return postponedList;
		}
		
		
	}
	
	public static void main(String[] args) {
		CarWash carWash = new CarWash();
		carWash.openShop();
		carWash.standInLine(new Logan(20 , "ohgi6853hb")); // car stands in line to get washed.
		carWash.standInLine(new Astra(25 , "436g753hb")); // car2 stands in line to get washed.
		carWash.washCar(); // will wash the first car standing in line, which is car1
		carWash.standInLine(new Astra(25 , "436g753hb"));
		carWash.washCar(); // will wash the next car standing in line, which is car2

		carWash.standInLine(new Logan(30 , "ohgi6853hb"));

		carWash.washCar();

		carWash.standInLine(new Astra(25 , "436g753hb"));

		carWash.standInLine(new Duster(50, "Wet6b9"));

		carWash.standInLine(new Duster(50, "Wet6b9"));//new Duster(35, "dsbi7543gb9"));

		carWash.closeShop();
		List<Car> todaysClients = carWash.getTodaysClients(); // returns all clients that have visited the shop today. If car was washed twice, it should be returned once.
		List<Car> postponedClients = carWash.getPostponedClients(); // returns a list of clients who did not get to have their car washed. The shop closed while they were standing in line.
		System.out.println("Washed today : " + todaysClients);
		System.out.println("Not washed today : " + postponedClients);
	}

}
