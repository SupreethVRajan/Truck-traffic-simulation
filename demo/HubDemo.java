package demo;

import base.Highway;
import base.Hub;
import base.Location;
import base.Network;
import base.Truck;
import java.util.ArrayList;
import java.util.HashMap;

public class HubDemo extends Hub {

	public HubDemo(Location loc) {
		super(loc);
		trucks = new ArrayList<Truck>();
	}

	@Override
	synchronized public boolean add(Truck truck) {
	System.out.println("R => (" + this.getLoc().getX() + ", " + this.getLoc().getY() + ")  +  " + truck.getTruckName());
	try {
		if(truck == null) {
			System.out.println("NULL is trying to add to list");
			throw(new NullPointerException());
		}
		else if(trucks.size() < this.getCapacity()) {
			trucks.add(truck);
			return true;
		}
		return false;
	}
	catch (Exception e) {
		System.out.println("add > " + e);
	}
	return false;
	}

	@Override
	synchronized public void remove(Truck truck) {
		try {
		trucks.remove(truck);
		}
		catch (Exception e) {
			System.out.println("remove > " + e);
		}
	}

	private Hub getminDist(Iterable<Hub> priorityqueue, HashMap<Hub, Double> distances) {
		Hub min = null;
		double mdist = Double.MAX_VALUE;
		for(Hub h: priorityqueue) {
			Double d = this.getShortest(distances, h);
			if(d < mdist) {
				mdist = d;
				min = h;
			}
		}
		return min;
	}

	private Double getShortest(HashMap<Hub, Double> distances, Hub target) {
		Double d = distances.get(target);
		if(d == null) {
			return Double.MAX_VALUE;
		}
		else {
			return d;
		}
	}

	private double distlocs(Location a, Location b) {
		double m = (a.getX() - b.getX())*(a.getX() - b.getX()) + (a.getY() - b.getY())*(a.getY() - b.getY());
		return Math.sqrt(m);
	}

	public HashMap<Hub, Double> DijkDist(Hub src) {
		HashMap<Hub, Double> distances = new HashMap<>();
		try {
		ArrayList<Hub> priorityqueue = new ArrayList<>();
		ArrayList<Hub> visited = new ArrayList<>();
		HashMap<Hub, Hub> predecessors = new HashMap<>();

		priorityqueue.add(src);
		distances.put(src, new Double(0.0));

		
		while(priorityqueue.size() > 0) {
			Hub node = getminDist(priorityqueue, distances);
			visited.add(node);
			priorityqueue.remove(node);
			for(Highway high: node.getHighways()) {
				Hub target = high.getEnd();
				if(!visited.contains(target)) {
					if(getShortest(distances, target) > getShortest(distances, node) + distlocs(high.getStart().getLoc(), high.getEnd().getLoc())) {
						distances.put(target, getShortest(distances, node) + distlocs(high.getStart().getLoc(), high.getEnd().getLoc()));
						predecessors.put(target, node);
						priorityqueue.add(target);
					}
				}
			}
		}}
		catch(Exception e){
			System.out.println("dijk > " + e);
		}

		return distances;
	}

	@Override
	public Highway getNextHighway(Hub from, Hub dest) {
		if(this.equals(dest) ) {
			return null;
		}
		Highway ans = null;

		double min = Double.MAX_VALUE;

		try {

		for(Highway h: this.getHighways()) {
			Hub hub = h.getEnd();
			if(!hub.equals(from)) {
				HashMap<Hub, Double> distances = DijkDist(hub);
				if(distances.get(dest) != null) {
					if(distances.get(dest) < min) {
						min = distances.get(dest);
						ans = h;
					}
				}
			}
		}}
		catch(Exception e){
			System.out.println("gethigh > " + e);
		}

		return ans;
	}

	@Override
	synchronized protected void processQ(int deltaT) {
	try {

		// System.out.println("S => (" + this.getLoc().getX() + ", " + this.getLoc().getY() + ")  >  " + this.trucks.size());
		ArrayList<Truck> delList = new ArrayList<Truck>();
		for(Truck t:trucks) {
			if(t == null) {
				System.out.println("NULL in trucklist!!!");
				trucks.remove(t);
				throw(new NullPointerException());
			}
			Hub last = t.getLastHub();
			Hub dest = Network.getNearestHub(t.getDest());
			Highway h = getNextHighway(last, dest);
			if(h == null) {
				t.enter(h);
				delList.add(t);
			}
			else if(h.hasCapacity()) {
				t.enter(h);
				delList.add(t);
			}
			else {
				System.out.println(t.getTruckName() + " on (" + this.getLoc().getX() + ", " + this.getLoc().getY() + ").");

			}
		}
		for(Truck t: delList) {
			this.remove(t);
		}
	}
		
	catch (Exception e) {
		System.out.println("prq > " + e);
		e.printStackTrace();
	}
	}

	private ArrayList<Truck> trucks = new ArrayList<Truck>();

	
}
