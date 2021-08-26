package demo;

import base.Highway;
import base.Truck;
import java.util.ArrayList;

class HighwayDemo extends Highway {

	public HighwayDemo() {
		trucks = new ArrayList<Truck>();
	}

	@Override
	public boolean hasCapacity() {
		return trucks.size() < this.getCapacity();
	}

	@Override
	synchronized public boolean add(Truck truck) {
		if(trucks.size() < this.getCapacity()) {
			trucks.add(truck);
			return true;
		}
		return false;
	}

	@Override
	synchronized public void remove(Truck truck) {
		trucks.remove(truck);
	}

	private ArrayList<Truck> trucks;


}
