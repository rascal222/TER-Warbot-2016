from java.lang import String
from edu.warbot.agents.agents import WarTurret as TurretAction

class PyWarTurret(PyWarAgent):

	def __init__(self):
		pass

	def angleOfView(self):
		return TurretAction.ANGLE_OF_VIEW;

	def bagSize(self):
		return TurretAction.BAG_SIZE;

	def cost(self):
		return TurretAction.COST;

	def distanceOfView(self):
		return TurretAction.DISTANCE_OF_VIEW;

	def maxHealth(self):
		return TurretAction.MAX_HEALTH;

	def ticksToReload(self):
		return TurretAction.TICKS_TO_RELOAD;

	def eat(self):
		return TurretAction.ACTION_EAT;

	def fire(self):
		return TurretAction.ACTION_FIRE;

	def give(self):
		return TurretAction.ACTION_GIVE;

	def idle(self):	
		return TurretAction.ACTION_IDLE;

	def reloadWeapon(self):
		return TurretAction.ACTION_RELOAD;

	def maxDistanceGive(self):
		return TurretAction.MAX_DISTANCE_GIVE;

	# MovableWarAgentAdapter

	def isBlocked(self):
		return False

	def getSpeed(self):
		return 0

	def isReloaded(self):
		return self.getRetAgent().isReloaded();

	def isReloading(self):
		return self.getRetAgent().isReloading();
