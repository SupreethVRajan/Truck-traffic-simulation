package demo;

import java.util.HashSet;

import base.Display;
import base.Highway;
import base.Hub;
import base.Location;
import base.Network;
import base.Truck;

public class NetworkDemo extends Network {

    public NetworkDemo() {
        trucks = new HashSet<Truck>();
        hubs = new HashSet<Hub>();
        highways = new HashSet<Highway>();
    }

    @Override
    public void add(Hub hub) {
        hubs.add(hub);
    }

    @Override
    public void add(Highway hwy) {
        highways.add(hwy);
    }

    @Override
    public void add(Truck truck) {
        trucks.add(truck);
    }

    @Override
    public void start() {
        for(Truck t: trucks) {
            t.start();
        }
        for(Hub h: hubs) {
            h.start();
        }

    }

    @Override
    public void redisplay(Display disp) {
        for(Hub h: hubs) {
            h.draw(disp);
        }

        for(Highway h: highways) {
            h.draw(disp);
        }

        for(Truck h: trucks) {
            h.draw(disp);
        }
    }

    @Override
    protected Hub findNearestHubForLoc(Location loc) {
        int x1 = loc.getX();
        int y1 = loc.getY();
        int mindis = Integer.MAX_VALUE;
        Hub minhub = null;
        for(Hub h: hubs) {
            int x2 = h.getLoc().getX();
            int y2 = h.getLoc().getY();
            int dist = (int) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
            if(dist < mindis) {
                mindis = dist;
                minhub = h;
            }
        }
        return minhub;
    }

    private HashSet<Hub> hubs;
    private HashSet<Highway> highways;
    private HashSet<Truck> trucks;
    
}
