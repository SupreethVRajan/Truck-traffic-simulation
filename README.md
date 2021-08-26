# Truck Traffic Simulation

This is a simple project where the trucks enter a hub from a departure point, travel on the highway  through different hubs to reach the hub that is nearest to the destination and reaches the destination point. It is implemented in java.

## How to run

This project can be run using the following command:  
**$ java DemoDriver**

After that command, inputs are given regarding the hubs, trucks and highways on the prompt in the following format:

animationTimeStep displayTimeStep  
numberofHubs  
hub1_x hub1_y hub1_capacity  
hub1_x hub1_y hub1_capacity  
.  
.  
numberofHighways  
hw1_starthubindex hw1_endhubindex hw1_capacity hw1_maxspeed  
hw2_starthubindex hw2_endhubindex hw2_capacity hw2_maxspeed  
.  
.  
numberofTrucks  
trk1_src_x trk1_dst_y trk1_src_x trk1_dst_y trk1_start_time  
trk1_src_x trk1_dst_y trk1_src_x trk1_dst_y trk1_start_time  
.  
.  

A sample input is given in sample.txt file.