package edu.warbot.brains.implementations;

import edu.warbot.brains.capacities.Targeter;

public class WarTargeterBrainImplementation extends WarBrainImplementation implements Targeter{

	@Override
	public void setTargetDistance(double targetDistance) {
		((Targeter) getAgent()).setTargetDistance(targetDistance);
	}

}
