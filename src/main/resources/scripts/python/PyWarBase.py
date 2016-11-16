from java.lang import String
from edu.warbot.agents.agents import WarBase as BaseAction

class PyWarBase(PyWarAgent):

	def __init__(self):
		pass
	
	def angleOfView(self):
		return BaseAction.ANGLE_OF_VIEW;

	def bagSize(self):
		return BaseAction.BAG_SIZE;

	def cost(self):
		return BaseAction.COST;

	def distanceOfView(self):
		return BaseAction.DISTANCE_OF_VIEW;

	def maxHealth(self):
		return BaseAction.MAX_HEALTH;
	
	def create(self):
		return BaseAction.ACTION_CREATE;

	def eat(self):
		return BaseAction.ACTION_EAT;

	def give(self):
		"""
		Permet de realiser l'action de donner de la nourriture

		return : String le nom de l'action
		"""
		return BaseAction.ACTION_GIVE;

	def idle(self):	
		"""
		Permet de realiser l'action de pas bouger de sa position

		return : String le nom de l'action
		"""
		return BaseAction.ACTION_IDLE;

	def maxDistanceGive(self):
		return BaseAction.MAX_DISTANCE_GIVE;

	# MovableWarAgentAdapter

	def isBlocked(self):
		return False

	def getSpeed(self):
		return 0

	# CreatorWarAgentAdapter

	def setNextAgentToCreate(self, agent):
		"""
		Permet de dire le prochain agent que la base veut créer

		return : void
		"""

		self.getRetAgent().setNextAgentToCreate(agent)

	def getNextAgentToCreate(self):
		"""
		Permet de savoir le prochain agent que la base va créer
	
		return : WarAgentType le type d'agent qui va être créé
		"""
		return self.getRetAgent().getNextAgentToCreate()

	def isAbleToCreate(self, agent):
		"""
		Permet de savoir si la base peut créer un agent à ce moment la

		return : boolean true si peut créer un agent false sinon
		"""
		return self.getRetAgent().isAbleToCreate(agent)
	