package car.Management;

import automobiles.*;
import java.util.*;
import java.util.HashMap;

/**
 * <p>
 * <h3> Class description </h3>
 * This class is purposed to hold the inventory of a list of {@link Car} objects ({@link Logan}, {@link Duster}, {@link Astra}, {@link Insignia}).
 * <p>
 * @author Razvan Radu
 */
public class CarInventory {
	int hashCode;
	private HashMap<Integer , Car> carInventory = new HashMap<>();
	
	/**
	 * Default constructor, creating an empty instance.
	 */
	public CarInventory() {
		carInventory.clear();
	}

	/**
	 * The addCar method adds a {@link Car} object into the database.
	 * <p>
	 * The permitted types are listed below. In case an illegal type is passed, an error message is printed.
	 * <p>
	 * @param car A type of {@link Car} ({@link Logan}, {@link Duster}, {@link Astra}, {@link Insignia})
	 */
	public void addCar(Car car) {
		if ((car instanceof Logan) || (car instanceof Duster) || (car instanceof Astra) || (car instanceof Insignia)) {
			Integer code = car.hashCode();
			carInventory.put(code, car);
		}
		else { 
			System.out.println("Invalid car type " + car + " not added to list");
		}
	}
	
	/**
	 * The addCar method searches for and removes a specific {@link Car} object from the database.
	 * <p>
	 * If the object is not found, an error message is printed.
	 * <p>
	 * @param chassisNumber {@link String} representing a chassis number which is used to identify a Car object
	 */
	public void removeCar(String chassisNumber) {
		Car toRemove = this.findCar(chassisNumber);
		if ((toRemove != null) && (!new Logan(1,"Not found").equals(toRemove))) {
			Integer removable = toRemove.hashCode();
			this.carInventory.remove(removable);
		}
		else {
			System.out.println("Car not found in the database, therefore nothing to delete.");
		}
	}

	/**
	 * The findCar method searches for a specific {@link Car} object from the database.
	 * <p>
	 * If the object is not found, an {@link Logan} object with the chassis number "Not found" is returned.
	 * <p>
	 * If the object is found, the corresponding {@link Car} object is returned.
	 * <p>
	 * @param chassis {@link String} representing a chassis number which is used to identify a Car object
	 */
	public Car findCar(String chassis) {
		Integer result = 0;
		
		Set<Integer> keySet = this.carInventory.keySet();
		Iterator<Integer> keySetIterator = keySet.iterator();
		while (keySetIterator.hasNext()) {
		    Integer key = keySetIterator.next();
		    if (this.carInventory.get(key).chassisNumber.equalsIgnoreCase(chassis)){
		    	result = key;
		    }
		}
		if (result != 0) {
			return this.carInventory.get(result);
		}
		else {
			return new Logan(1,"Not found");
		}
	}
	
	/**
	 * The getMostFuelEfficientCars method searches the database for a minimum consumption and returns a {@link List} of {@link Car} ({@link Logan}, {@link Duster}, {@link Astra}, {@link Insignia}) objects meeting the criteria.
	 * <p>
	 * It uses the {@link getConsumptionPerGear} method available for each {@link Car} object to calculate the consumption.
	 * <p>
	 * The consumption is calculated in the first gear per 100 kilometers.
	 * <p>
	 * If the object is not found, an  object with the chassis number "Not found" is returned.
	 * @param chassis {@link String} representing a chassis number which is used to identify a Car object.
	 */
	public List<Car> getMostFuelEfficientCars(){
		float minimumConsumption = 100.0f;
				
		// First determine the minimum consumption of the list, by using the consumption in the first gear
		ArrayList<Car> newlist = new ArrayList<Car>();
		
		Set<Integer> keySet = this.carInventory.keySet();
		Iterator<Integer> keySetIterator = keySet.iterator();
		
		while (keySetIterator.hasNext()) {
		    Integer key = keySetIterator.next();
		    if (this.carInventory.get(key).getConsumptionPerGear(1,100) < minimumConsumption){
		    	minimumConsumption = this.carInventory.get(key).getConsumptionPerGear(1,100);
		    }
		}
		keySetIterator.remove();
		// Searching for the cars which hold the minimum consumption
		Iterator<Integer> keySetIterator2 = keySet.iterator();
		while (keySetIterator2.hasNext()) {
		    Integer key = keySetIterator2.next();
		    if (this.carInventory.get(key).getConsumptionPerGear(1,100) == minimumConsumption){
		    	newlist.add(this.carInventory.get(key));
		    }
		}
		
		return newlist;
	}
	
	
	public static void main(String[] args) {
		String chassisNumber;
		
		chassisNumber="4h9g3ga";
		CarInventory ci = new CarInventory();
		Logan car1 = new Logan(27 , "905ghjga");
		ci.addCar(car1);
		Astra car2 = new Astra(31 , "WOLOnjuge759");
		ci.addCar(car2);
		Insignia car3 = new Insignia(44 , "4h9g3ga");
		ci.addCar(car3);
		Duster car4 = new Duster(28 , "Whwh6jugehr");
		ci.addCar(car4);
		Astra car5 = new Astra(30 , "4hgdsaobia");
		ci.addCar(car5);
		// ci.printList();
		//Car car2 = ci.findCar(chassisNumber);
		ci.removeCar(chassisNumber);
		// ci.printList();
		List<Car> cars = ci.getMostFuelEfficientCars();
		System.out.println("Most fuel efficient cars : ");
		for (Car car : cars){
			System.out.println(cars.indexOf(car) + ", " + car);
		}
		
	}

}
