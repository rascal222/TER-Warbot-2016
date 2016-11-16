from java.lang import String
from edu.warbot.agents.agents import WarExplorer as ExplorerAction

class PyWarExplorer(PyWarAgent):

	def __init__(self):
		pass

	def angleOfView(self):
		"""
		Permet de savoir l'angle de vision du champ de vision de notre Explorer
		
		return : double angle de vision
		"""
		return ExplorerAction.ANGLE_OF_VIEW;

	def bagSize(self):
		"""
		cf : methode identique in PyWarAgent
		"""
		return ExplorerAction.BAG_SIZE;

	def cost(self):
		"""
		Permet de savoir le coup de création de notre Explorer

		return : int le cout
		"""
		return ExplorerAction.COST;

	def distanceOfView(self):
		"""
		Permet de savoir la distance max du champ de vision de notre Explorer

		return : double la distance max
		"""
		return ExplorerAction.DISTANCE_OF_VIEW;

	def maxHealth(self):
		"""
		cf : methode identique in PyWarAgent
		"""
		return ExplorerAction.MAX_HEALTH;

	def speed(self):
		"""
		cf : methode identique in PyWarAgent
		"""
		return ExplorerAction.SPEED;

	def eat(self):
		"""
		Permet de realiser l'action de manger de la nourriture

		return : String le nom de l'action
		"""
		return ExplorerAction.ACTION_EAT;

	def give(self):
		"""
		Permet de realiser l'action de donner de la nourriture

		return : String le nom de l'action
		"""
		return ExplorerAction.ACTION_GIVE;

	def idle(self):	
		"""
		Permet de realiser l'action de pas bouger de sa position

		return : String le nom de l'action
		"""
		return ExplorerAction.ACTION_IDLE;

	def move(self):
		"""
		Permet de realiser l'action de se deplacer de sa position

		return : String le nom de l'action
		"""
		return ExplorerAction.ACTION_MOVE;

	def take(self):
		"""
		Permet de realiser l'action de donner de la nourriture

		return : String le nom de l'action
		"""
		return ExplorerAction.ACTION_TAKE;

	def maxDistanceGive(self):
		"""
		cf : methode identique in PyWarAgent
		"""
		return ExplorerAction.MAX_DISTANCE_GIVE;
