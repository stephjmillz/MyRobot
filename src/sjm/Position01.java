package sjm;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Position01 extends Robot {

	public void run() {
		
		setBodyColor(Color.red);
		setGunColor(Color.black);
		setRadarColor(Color.yellow);
		setBulletColor(Color.green);
		setScanColor(Color.green); 
		
		
		while(true) {
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}
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
}
