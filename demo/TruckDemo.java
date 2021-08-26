package demo;


import base.Highway;
import base.Hub;
import base.Truck;
import base.Location;
import base.Network;
import java.lang.Math;

class TruckDemo extends Truck {

	public TruckDemo() {
		lastHub = null;
		onhigh = null;
		nowHub = null;
		reached = false;
		totalTime = 0;
		truckno++;
		// name = "Truck19087_" + String.valueOf(truckno);
		name = "Sup_" + String.valueOf(truckno);
	}

	@Override
	synchronized protected void update(int deltaT) {
		totalTime += deltaT;
		if(totalTime > this.getStartTime() && !reached) {

			if(onhigh != null) {
			System.out.println(this.getTruckName() + " travelling.");
				Location src = this.getLoc();
				Location des = onhigh.getEnd().getLoc();
				double dist = (double) onhigh.getMaxSpeed()*deltaT/500;
				// System.out.println(this.getTruckName() + "  " +src.getX() + ", "+src.getY() + "  >  " + des.getX() + ", " + des.getY());
				if(src.getX() == des.getX()) {

					if(Math.abs(des.getY() - src.getY()) > dist) {
						if(src.getY() > des.getY()) {
							this.setLoc(new Location(this.getLoc().getX(), this.getLoc().getY() - (int)Math.round(dist)));
						}
						else {
							this.setLoc(new Location(this.getLoc().getX(), this.getLoc().getY() + (int)Math.round(dist)));
						}
					}
					else {
						if(onhigh.getEnd().add(this)) {
							this.nowHub = onhigh.getEnd();
							this.setLoc(new Location(nowHub.getLoc().getX(), nowHub.getLoc().getY()));
							this.onhigh.remove(this);
							this.onhigh = null;
						}
					}
				}

				else {
					double theta = Math.atan(Math.abs((double) (des.getY() - src.getY()) / (des.getX() - src.getX())));
					double disttohub = (double) Math.sqrt((src.getX() - des.getX())*(src.getX() - des.getX()) + (src.getY() - des.getY())*(src.getY() - des.getY()));
                    // System.out.println(this.getTruckName() + " " + dist + " " + disttohub );
					if(dist < disttohub) {
						int xdis = (int) Math.round(Math.cos(theta) * dist);
						int ydis = (int) Math.round(Math.sin(theta) * dist);
						// System.out.println(this.getTruckName() + "a " + Math.sin(theta) + "b " + Math.cos(theta));
						if(Math.abs(xdis) < 1 && Math.abs(ydis) < 1) {
							xdis = 1;
							ydis = 1;
						}
						if(src.getX() > des.getX()) {
							xdis = -Math.abs(xdis);
						}
						else {
							xdis = Math.abs(xdis);
						}
						if(src.getY() > des.getY()) {
							ydis = -Math.abs(ydis);
						}
						else {
							ydis = Math.abs(ydis);
						}
						// System.out.println(this.getLoc().getX() + ", " + this.getLoc().getY() +"  >  " + xdis + "  " + ydis);
						this.setLoc(new Location(this.getLoc().getX() + xdis, this.getLoc().getY() + ydis));
					}
					else {
						if(onhigh.getEnd().add(this)) {					
							this.nowHub = onhigh.getEnd();
							this.setLoc(new Location(nowHub.getLoc().getX(), nowHub.getLoc().getY()));
							this.onhigh.remove(this);
							this.onhigh = null;
							System.out.println(this.getTruckName() + " entered (" + nowHub.getLoc().getX() + ", " + nowHub.getLoc().getY() + ")");		
						}
					}
				}
			}

			if(nowHub == null && lastHub == null ) {
				Hub startHub = Network.getNearestHub(this.getSource());
				if(startHub.add(this)) {
					this.setLoc(new Location(startHub.getLoc().getX(), startHub.getLoc().getY()));
					nowHub = startHub;
				}
			}
		}
	}

	@Override
	public Hub getLastHub() {
		return lastHub;
	}

	@Override
	public String getTruckName() {
		return name;
	}

	@Override
	public void enter(Highway hwy) {
		System.out.println(this.getTruckName() + " entered hwy");
		if(hwy == null) {
			this.setLoc(new Location(this.getDest().getX(), this.getDest().getY()));
			this.lastHub = this.nowHub;
			this.nowHub = null;
			reached = true;
		}
		else {
		lastHub = nowHub;
		nowHub = null;
		hwy.add(this);
		onhigh = hwy;
		}
	}

	private Hub lastHub;
	private Highway onhigh; 
	private Hub nowHub;
	private boolean reached;
	private static int truckno = 0;
	private int totalTime;
	private String name;
}
