from java.lang import String
from edu.warbot.agents.agents import WarRocketLauncher as RocketLauncherAction

class PyWarRocketLauncher(PyWarAgent):
	
	def __init__(self):
		pass

	def angleOfView(self):
		return RocketLauncherAction.ANGLE_OF_VIEW;

	def bagSize(self):
		return RocketLauncherAction.BAG_SIZE;

	def cost(self):
		return RocketLauncherAction.COST;

	def distanceOfView(self):
		return RocketLauncherAction.DISTANCE_OF_VIEW;

	def maxHealth(self):
		return RocketLauncherAction.MAX_HEALTH;

	def speed(self):
		return RocketLauncherAction.SPEED;

	def ticksToReload(self):
		"""
		Permet de savoir le nombre de tick qua besoin un agent pour recharger son arme

		return : int nombre de tick
		"""
		return RocketLauncherAction.TICKS_TO_RELOAD;

	def eat(self):
		return RocketLauncherAction.ACTION_EAT;

	def fire(self):
		"""
		Permet de realiser l'action de se tirer avec son arme

		return : String le nom de l'action
		"""
		return RocketLauncherAction.ACTION_FIRE;

	def give(self):
		return RocketLauncherAction.ACTION_GIVE;

	def idle(self):	
		return RocketLauncherAction.ACTION_IDLE;

	def move(self):
		return RocketLauncherAction.ACTION_MOVE;

	def reloadWeapon(self):
		"""
		Permet de realiser l'action de recharger son arme

		return : String le nom de l'action
		"""
		return RocketLauncherAction.ACTION_RELOAD;

	def take(self):
		return RocketLauncherAction.ACTION_TAKE;

	def maxDistanceGive(self):
		return RocketLauncherAction.MAX_DISTANCE_GIVE;

	def isReloaded(self):
		"""
		Permet de savoir si notre arme est rechargé

		return : boolean true si l'arme est disponible pour tirer false sinon
		"""
		
		return self.getRetAgent().isReloaded();

	def isReloading(self):
		"""
		Permet de savoir si notre arme est en train de se recharger

		return : boolean true si l'arme est indisponible pour tirer false sinon
		"""

		return self.getRetAgent().isReloading();

	def setTargetDistance(self, targetDistance):
		"""
		Permet de viser une zone

		return : void
		"""

		self.getRetAgent().setTargetDistance(targetDistance);
