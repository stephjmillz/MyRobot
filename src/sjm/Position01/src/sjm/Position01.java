package sjm;

import java.awt.Color;

import robocode.Condition;
import robocode.CustomEvent;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Position01 extends Robot {
	int trigger;
	
	public void run() {
		
	
		
		setBodyColor(Color.red);
		setGunColor(Color.black);
		setRadarColor(Color.yellow);
		setBulletColor(Color.green);
		setScanColor(Color.green); 
		
		trigger = 80;
		// Add a custom event named "trigger hit",
		addCustomEvent(new Condition("triggerhit") {
			public boolean test() {
				return (getEnergy() <= trigger);
			}
		});
	
		
		
		while(true) {
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
	}
	
	
	
	private void addCustomEvent(Condition condition) {
		// TODO Auto-generated method stub
		
	}



	public void onScannedRobot(ScannedRobotEvent e) {
		fire(25);
	}
	
	public void onHitByBullet (HitByBulletEvent e) {
		back(3);
	}
	
	public void onHitWall(HitWallEvent e) {
		back (10);
	}
	
	
	public void onCustomEvent(CustomEvent e) {
		// If our custom event "triggerhit" went off,
		if (e.getCondition().getName().equals("triggerhit")) {
			// Adjust the trigger value, or
			// else the event will fire again and again and again...
			trigger -= 20;
			out.println("Ouch, down to " + (int) (getEnergy() + .5) + " energy.");
			// move around a bit.
			turnLeft(65);
			ahead(100);
		}
	}
}

