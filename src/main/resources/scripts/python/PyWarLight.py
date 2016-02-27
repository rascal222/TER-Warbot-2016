from java.lang import String
from edu.warbot.agents.agents import WarLight as LightAction

class PyWarLight(PyWarAgent):
	
	def __init__(self):
		pass

	def angleOfView(self):
		return LightAction.ANGLE_OF_VIEW;

	def bagSize(self):
		return LightAction.BAG_SIZE;

	def cost(self):
		return LightAction.COST;

	def distanceOfView(self):
		return LightAction.DISTANCE_OF_VIEW;

	def maxHealth(self):
		return LightAction.MAX_HEALTH;

	def speed(self):
		return LightAction.SPEED;

	def ticksToReload(self):
		"""
		Permet de savoir le nombre de tick qua besoin un agent pour recharger son arme

		return : int nombre de tick
		"""
		return LightAction.TICKS_TO_RELOAD;

	def eat(self):
		return LightAction.ACTION_EAT;

	def fire(self):
		"""
		Permet de realiser l'action de se tirer avec son arme

		return : String le nom de l'action
		"""
		return LightAction.ACTION_FIRE;

	def give(self):
		return LightAction.ACTION_GIVE;

	def idle(self):	
		return LightAction.ACTION_IDLE;

	def move(self):
		return LightAction.ACTION_MOVE;

	def reloadWeapon(self):
		"""
		Permet de realiser l'action de recharger son arme

		return : String le nom de l'action
		"""
		return LightAction.ACTION_RELOAD;

	def take(self):
		return LightAction.ACTION_TAKE;

	def maxDistanceGive(self):
		return LightAction.MAX_DISTANCE_GIVE;

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
