from java.lang import String
from edu.warbot.agents.agents import WarHeavy as HeavyAction

class PyWarHeavy(PyWarAgent):
	
	def __init__(self):
		pass

	def angleOfView(self):
		return HeavyAction.ANGLE_OF_VIEW;

	def bagSize(self):
		return HeavyAction.BAG_SIZE;

	def cost(self):
		return HeavyAction.COST;

	def distanceOfView(self):
		return HeavyAction.DISTANCE_OF_VIEW;

	def maxHealth(self):
		return HeavyAction.MAX_HEALTH;

	def speed(self):
		return HeavyAction.SPEED;

	def ticksToReload(self):
		"""
		Permet de savoir le nombre de tick qua besoin un agent pour recharger son arme

		return : int nombre de tick
		"""
		return HeavyAction.TICKS_TO_RELOAD;

	def eat(self):
		return HeavyAction.ACTION_EAT;

	def fire(self):
		"""
		Permet de realiser l'action de se tirer avec son arme

		return : String le nom de l'action
		"""
		return HeavyAction.ACTION_FIRE;

	def give(self):
		return HeavyAction.ACTION_GIVE;

	def idle(self):	
		return HeavyAction.ACTION_IDLE;

	def move(self):
		return HeavyAction.ACTION_MOVE;

	def reloadWeapon(self):
		"""
		Permet de realiser l'action de recharger son arme

		return : String le nom de l'action
		"""
		return HeavyAction.ACTION_RELOAD;

	def take(self):
		return HeavyAction.ACTION_TAKE;

	def maxDistanceGive(self):
		return HeavyAction.MAX_DISTANCE_GIVE;

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
