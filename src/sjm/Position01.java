package sjm;

import java.awt.Color;

import robocode.Condition;
import robocode.CustomEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Position01 extends Robot {
	
	int trigger;
	boolean peek; // Don't turn if there's a robot there
	double moveAmount; // How much to move
	int turnDirection = 1;
	
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
	
		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		// Initialize peek to false
		peek = false;

		// turnLeft to face a wall.
		// getHeading() % 90 means the remainder of
		// getHeading() divided by 90.
		turnLeft(getHeading() % 90);
		ahead(moveAmount);
		// Turn the gun to turn right 90 degrees.
		peek = true;
		turnGunRight(90);
		turnRight(90);

		while (true) {
			// Look before we turn when ahead() completes.
			peek = true;
			// Move up the wall
			ahead(moveAmount);
			// Don't look now
			peek = false;
			// Turn to the next wall
			turnRight(90);
			
			while (true) {
				turnRight(5 * turnDirection);
			}
		}
		}
	
		
	
	
	
	
	private void addCustomEvent(Condition condition) {
		// TODO Auto-generated method stub
		
	}

	


	public void onScannedRobot(ScannedRobotEvent e) {
		// Calculate exact location of the robot
				double absoluteBearing = getHeading() + e.getBearing();
				double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

				// If it's close enough, fire!
				if (Math.abs(bearingFromGun) <= 3) {
					turnGunRight(bearingFromGun);
					// We check gun heat here, because calling fire()
					// uses a turn, which could cause us to lose track
					// of the other robot.
					if (getGunHeat() == 0) {
						fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
					}
				} // otherwise just set the gun to turn.
				// Note:  This will have no effect until we call scan()
				else {
					turnGunRight(bearingFromGun);
				}
				// Generates another scan event if we see a robot.
				// We only need to call this if the gun (and therefore radar)
				// are not turning.  Otherwise, scan is called automatically.
				if (bearingFromGun == 0) {
					scan();
					
					if (e.getBearing() >= 0) {
						turnDirection = 1;
					} else {
						turnDirection = -1;
					}

					turnRight(e.getBearing());
					ahead(e.getDistance() + 5);
					scan(); // Might want to move ahead again!
				}
			}
	
	private double normalRelativeAngleDegrees(double d) {
		// TODO Auto-generated method stub
		return 0;
	}





	public void onHitByBullet (HitByBulletEvent e) {
		back(3);
	}
	
	public void onHitWall(HitWallEvent e) {
		back (10);
	}
	
	public void onHitRobot(HitRobotEvent e) {
		if (e.getBearing() >= 0) {
			turnDirection = 1;
		} else {
			turnDirection = -1;
		}
		turnRight(e.getBearing());

		// Determine a shot that won't kill the robot...
		// We want to ram him instead for bonus points
		if (e.getEnergy() > 16) {
			fire(3);
		} else if (e.getEnergy() > 10) {
			fire(2);
		} else if (e.getEnergy() > 4) {
			fire(1);
		} else if (e.getEnergy() > 2) {
			fire(.5);
		} else if (e.getEnergy() > .4) {
			fire(.1);
		}
		ahead(40); // Ram him again!
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

