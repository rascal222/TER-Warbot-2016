from java.lang import String
from edu.warbot.agents.agents import WarKamikaze as KamikazeAction

class PyWarKamikaze(PyWarAgent):
	
	def __init__(self):
		pass

	def angleOfView(self):
		return KamikazeAction.ANGLE_OF_VIEW;

	def bagSize(self):
		return KamikazeAction.BAG_SIZE;

	def cost(self):
		return KamikazeAction.COST;

	def distanceOfView(self):
		return KamikazeAction.DISTANCE_OF_VIEW;

	def maxHealth(self):
		return KamikazeAction.MAX_HEALTH;

	def speed(self):
		return KamikazeAction.SPEED;

	def eat(self):
		return KamikazeAction.ACTION_EAT;

	def fire(self):
		return KamikazeAction.ACTION_FIRE;

	def give(self):
		return KamikazeAction.ACTION_GIVE;

	def idle(self):	
		return KamikazeAction.ACTION_IDLE;

	def move(self):
		return KamikazeAction.ACTION_MOVE;

	def reloadWeapon(self):
		return KamikazeAction.ACTION_RELOAD;

	def take(self):
		return KamikazeAction.ACTION_TAKE;

	def maxDistanceGive(self):
		return KamikazeAction.MAX_DISTANCE_GIVE;

	def isReloaded(self):
		return self.getRetAgent().isReloaded();

	def isReloading(self):
		return self.getRetAgent().isReloading();

