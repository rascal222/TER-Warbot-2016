from java.lang import String
from edu.warbot.agents.agents import WarExplorer as AgentAction
from edu.warbot.agents import *
from edu.warbot.agents.enums import WarAgentType
from edu.warbot.agents.enums import WarAgentCategory
from edu.warbot.agents.percepts import *
from edu.warbot.agents.resources import WarFood
from edu.warbot.brains import *
from edu.warbot.scriptcore.scriptagent import ScriptAgent
from edu.warbot.scriptcore.wrapper import PyWrapper
from madkit.kernel.AbstractAgent import ReturnCode
from java.awt import Color
from java.util import ArrayList
from edu.warbot.communications import WarMessage
from edu.warbot.tools.geometry import PolarCoordinates
from edu.warbot.agents.agents import WarEngineer as EngineerAction
from edu.warbot.agents.agents import WarBase as BaseAction
from edu.warbot.brains.capacities import Builder

global WA;

class PyWarAgent(ScriptAgent):
	
		""" Permet de stocker l'agent java correspondant à notre agent python"""
		__ret = None

		def __init__(self):
			global WA
			WA = self;

		def link(self,t):
			""" Permet d'associer notre agent java à notre agent python

			Normalement uitlisé dans les classes java pour passer le type de
			l'agent

			return : void
			"""
			self.__ret = PyWrapper(t)

		def getRetAgent(self):
			"""
			Permet d'avoir l'agent java correspondant à notre agent python
			ce qui permet d'appeler les méthodes java de cette agent

			return : WarAgentAdaptater cela le type de l'agent qui l'utilise
			"""

			return self.__ret.get()

		# Methode contenu dans la class ControllableWarAgentAdapter

		def sendMessage(self, idAgent, message, content):
			"""
			Permet d'envoyer un message à une autre agent qui peut lire des messages

			idAgent -- int -- l'ID de l'agent qui oit reçevoir notre message
			message -- String -- le titre de notre message
			content -- String -- le contenu de notre message
			ou
			content -- String[] -- les contenus de notre message

			return : ReturnCode permet de savoir si le message est bien reçu par l'agent
			"""

			return self.getRetAgent().sendMessage(idAgent, message, content)

		def broadcastMessageToAll(self, message, content=""):
			"""
			Permet d'envoyer un message à tout les agents qui peuvent lirenr des messages

			message -- String -- le titre de notre message
			content -- String -- le contenu de notre message
			ou
			content -- String[] -- les contenus de notre message

			return : void
			"""

			self.getRetAgent().broadcastMessageToAll(message, content)

		def broadcastMessageToAgentType(self, agentType, message, content) :
			"""
			Permet d'envoyer un message à une type d'agent en particulier

			agentType -- WarAgentType -- le type des agents voulu
			message -- String -- le titre de notre message
			content -- String -- le contenu de notre message
			ou
			content -- String[] -- les contenus de notre message

			return : ReturnCode permet de savoir si le message est bien reçu par l'agent
			"""
			return self.getRetAgent().broadcastMessageToAgentType(agentType, message, content)

		def broadcastMessage(self, groupName,  roleName,  message,   content) :
			"""
			Permet d'envoyer un message à un autre d'agent en particulier
			et qui ont un role défini

			groupName -- String -- le groupe d'agents
			roleName -- String -- le role de ces agent
			message -- String -- le titre de notre message
			content -- String -- le contenu de notre message
			ou
			content -- String[] -- les contenus de notre message

			return : ReturnCode permet de savoir si le message est bien reçu par l'agent
			"""

			return self.getRetAgent().broadcastMessage(groupName, roleName, message, content)

		def broadcastMessageToGroup(groupName,  message,   content) :
			"""
			Permet d'envoyer un message à un group d'agent particulier

			groupName -- String -- le groupe d'agents
			message -- String -- le titre du message
			content -- String -- le contenu du message
			ou
			content -- String[] -- le contenu de message sous plusieures chaînes de caractères
			"""
			return self.getRetAgent().broadcastMessageToGroup(groupName, message, content)

		def reply(self, warMessage,  message, content):
			"""
			Permet de répondre à un message reçu par l'agent

			warMessage -- WarMessage -- le message reçu
			message -- String -- le titre de notre message
			content -- String -- le contenu de notre message
			ou
			content -- String[] -- les contenus de notre message

			return : ReturnCode permet de savoir si le message est bien reçu par l'agent
			"""

			return self.getRetAgent().reply(warMessage, message, content)


			"""
			Permet de savoir la liste des messages que notre agent reçoit
			attention car l'appele de cette méthode efface la liste des messages après
			utilisation, donc ne l'appeler qu'une fois par tick

			return : List<WarMessage> la liste des messages
			"""

		def getMessages(self):

			__message = self.getRetAgent().getMessages()

			messagePython = []

			for i in range (0, __message.size()):
				messagePython.append(__message.get(i))
			return messagePython

		def setIdNextAgentToGive(self, idNextAgentToGive):
			"""
			Permet de dire qu'elle sera le prochain agent qui aura le give de ressource
			de notre agent

			idNextAgentToGive -- int -- ID de l'agent à qui on va donner notre ressource

			return : void
			"""

			self.getRetAgent().setIdNextAgentToGive(idNextAgentToGive)

		def getBagSize(self):
			"""
			Permet d'avoir la taille de sac d'un agent
			Le sac dans lequelle il stocke les ressources

			return : int la taille du sac
			"""

			return self.getRetAgent().getBagSize()

		def getNbElementsInBag(self):
			"""
			Permet d'avoir le nombre de ressource dans le sac d'un agent
			Le sac dans lequelle il stocke les ressources

			return : int le nombre d'élément dans le sac de l'agent
			"""

			return self.getRetAgent().getNbElementsInBag()

		def isBagEmpty(self):
			"""
			Permet de savoir si le sac d'un agent est vide
			Le sac dans lequelle il stocke les ressources

			return : boolean true si le sac de l'agent est vide false sinon
			"""

			return self.getRetAgent().isBagEmpty()

		def isBagFull(self):
			"""
			Permet de savoir si le sac d'un agent est plein
			Le sac dans lequelle il stocke les ressources

			return : boolean true si le sac de l'agent est plein false sinon
			"""

			return self.getRetAgent().isBagFull()

		def setViewDirection(self, viewDirection):
			"""
			Permet de modifier la direction du champ de vision d'un agent
			pour quelle soit différente de celle de ça direction

			viewDirection -- double -- le nouvelle angle de direction de la vue de l'agent

			return : void
			"""

			self.getRetAgent().setViewDirection(viewDirection)

		def getHealth(self):
			"""
			Permet de savoir la vie courante de notre agent

			return : int la vie de l'agent
			"""

			return self.getRetAgent().getHealth()

		def getPerceptsAllies(self):
			"""
			Permet de savoir la liste des agents qui sont dans notre inGameTeams
			que notre agent perçoit dans sont champ de vision

			return : List<WarAgentPercept> la liste des agents perçu
			"""
			__ally = self.getRetAgent().getPerceptsAllies()
			perceptAlly = []
			for i in range (0, __ally.size()):
				perceptAlly.append(__ally.get(i))
			return perceptAlly;
			#return self.getRetAgent().getPerceptsAllies()

		def getPerceptsEnemies(self):
			"""
			Permet de savoir la liste des agents qui sont dans la inGameTeams adverse
			que notre agent perçoit dans sont champ de vision

			return : List<WarAgentPercept> la liste des agents perçu
			"""
			__Enem = self.getRetAgent().getPerceptsEnemies()
			perceptEnem = []
			for i in range (0, __Enem.size()):
				perceptEnem.append(__Enem.get(i))
			return perceptEnem;
			#return self.getRetAgent().getPerceptsEnemies()

		def getPerceptsResources(self):
			"""
			Permet de savoir la liste des ressources que notre agent
			perçoit dans sont champ de vision

			return : List<WarAgentPercept> la liste des ressources perçu
			"""
			__Ress = self.getRetAgent().getPerceptsResources()
			perceptRess = []
			for i in range (0, __Ress.size()):
				perceptRess.append(__Ress.get(i))
			return perceptRess;
			#return self.getRetAgent().getPerceptsResources()

		def getPerceptsAlliesByType(self, agentType):
			"""
			Permet de savoir la liste des agents de notre inGameTeams d'un type d'agent particulier
			que notre agent perçoit dans sont champ de vision

			agentType -- WarAgentType -- le type d'agent que l'on chercher

			return : List<WarAgentPercept> la liste des agents perçu
			"""
			__ally = self.getRetAgent().getPerceptsAlliesByType(agentType)
			perceptAlly = []
			for i in range (0, __ally.size()):
				perceptAlly.append(__ally.get(i))
			return perceptAlly;
			#return self.getRetAgent().getPerceptsAlliesByType(agentType)

		def getPerceptsEnemiesByType(self, agentType):
			"""
			Permet de savoir la liste des agents de la inGameTeams adverse d'un type d'agent particulier
			que notre agent perçoit dans sont champ de vision

			agentType -- WarAgentType -- le type d'agent que l'on chercher

			return : List<WarAgentPercept> la liste des agents perçu
			"""
			__Enem = self.getRetAgent().getPerceptsEnemiesByType(agentType)
			perceptEnem = []
			for i in range (0, __Enem.size()):
				perceptEnem.append(__Enem.get(i))
			return perceptEnem;
			#return self.getRetAgent().getPerceptsEnemiesByType(agentType)

		def getPercepts(self):
			"""
			Permet de savoir la liste des agents que notre agent
			perçoit dans sont champ de vision

			return : List<WarAgentPercept> la liste des agents perçu
			"""
			__percept = self.getRetAgent().getPercepts()
			percept = []
			for i in range (0, __percept.size()):
				percept.append(__percept.get(i))
			return percept;
			#return self.getRetAgent().getPercepts()

		def getDebugString(self):
			"""
			Permet de savoir le message de debug pour le même type d'agent
			que notre agent qui appele cette méthode

			return : String le message de debug
			"""

			return self.getRetAgent().getDebugString()

		def setDebugString(self, debug):
			"""
			Permet de modifier le contenu du message de debug pour le même type d'agent
			que notre agent qui appele cette méthode

			debug -- String -- le nouveau message de debug

			return : void
			"""
			self.getRetAgent().setDebugString(debug)

		def getDebugStringColor(self):
			"""
			Permet de savoir la couleur du message de debug pour le même type d'agent
			que notre agent qui appele cette méthode

			return : Color la couleur du message de debug
			"""
			return self.getRetAgent().getDebugStringColor()

		def setDebugStringColor(self, color):
			"""
			Permet de modifier la couleur du message de debug pour le même type d'agent
			que notre agent qui appele cette méthode

			color -- Color -- la nouvelle couleur du message de debug

			return : void
			"""

			self.getRetAgent().setDebugStringColor(color)

		def getAveragePositionOfUnitInPercept(self, agentType, ally):
			return self.getRetAgent().getAveragePositionOfUnitInPercept(agentType, ally)

		def getIndirectPositionOfAgentWithMessage(self, message):
			return self.getRetAgent().getIndirectPositionOfAgentWithMessage(message)

		def getTargetedAgentPosition(self, angleToAlly, distanceFromAlly, angleFromAllyToTarget, distanceBetweenAllyAndTarget):
			return self.getRetAgent().getTargetedAgentPosition(angleToAlly, distanceFromAlly, angleFromAllyToTarget, distanceBetweenAllyAndTarget)

		def getViewDirection(self):
			"""
			Permet de savoir l'angle de la direction du champ de vision de notre agent

			return : double l'angle du champ de vision
			"""

			return self.getRetAgent().getViewDirection()

		def getMaxHealth(self):
			"""
			Permet de savoir la vie maximum que peut avoir un agent
			Pour l'instant cette vie max de change jamais

			return : int la vie max de l'agent
			"""

			return self.getRetAgent().getMaxHealth()


		# Methode contenu dans la class MovableWarAgentAdapter

		def isBlocked(self):
			"""
			Permet de savoir si un agent est bloqué

			return : boolean true si l'agent est bloqué false sinon
			"""
			return self.getRetAgent().isBlocked()

		def getSpeed(self):
			"""
			Permet d'avoir la vitesse d'un agent
			Cette vitesse est constante quel que soit la situation

			return : double la vitesse de l'agent
			"""
			return self.getRetAgent().getSpeed()

		# Methode contenu dans la class WarAgentAdapter

		def getHeading(self):
			"""
			Permet de savoir l'angle de la direction vers lequel l'agent se trouve

			return : double l'angle de la direction
			"""

			return self.getRetAgent().getHeading()

		def setHeading(self, angle):
			"""
			Permet de modifier l'angle de la direction vers lequel l'agent se trouve

			angle -- double -- la nouvelle valeur de l'angle

			return : void
			"""
			self.getRetAgent().setHeading(angle)

		def RandomHeading(self):
			"""
			Permet de modifier l'angle de la direction vers lequel l'agent se trouve
			par une valeur aléatoire compris entre 0 et 360

			return : void
			"""
			self.getRetAgent().setRandomHeading()

		def setRandomHeading(self, rangeHeading):
			"""
			Permet de modifier l'angle de la direction vers lequel l'agent se trouve
			par une valeur aléatoire compris entre 0 et le range

			rangeHeading -- int -- la valeur pour le range du random

			return : void
			"""
			self.getRetAgent().setRandomHeading(rangeHeading)

		def getTeamName(self):
			"""
			Permet de savoir à quel type appartient notre agent

			return : String le nom de la inGameTeams de notre agent
			"""
			return self.getRetAgent().getTeamName()

		def isEnemy(self, percept):
			"""
			Permet de savoir si l'agent perçu est un ennemi ou nn

			percept -- WarAgentPercept -- l'agent perçu

			return : boolean true si l'agent perçu false sinon
			"""

			return self.getRetAgent().isEnemy(percept)

		def getID(self):
			"""
			Permet de savoir le ID de notre agent
			Ce ID est unique pour chaque agent

			return : int le ID de notre agent
			"""
			return self.getRetAgent().getID()

		def requestRole(self, group, role):
			"""
			Permet de rajouter un role à un group d'agent et de nous affecter ce role
			Si le group n'existe pas,  cela créer ce nouveau groupe

			group -- String -- le group d'agent
			role -- String -- le nouveaux role des agents

			return AbstractAgent.ReturnCode permet de savoir si le rajout c'est bien passé
			"""

			return self.getRetAgent().requestRole(group, role)

		def leaveRole(self, group, role):
			"""
			Permet de quitter le role de notre agent

			group -- String -- le group d'agent
			role -- String -- le nouveaux role des agents

			return AbstractAgent.ReturnCode permet de savoir si le rajout c'est bien passé
			"""
			return self.getRetAgent().leaveRole(group, role)

		def leaveGroup(self, group):
			"""
			Permet de quitter le group dans lequelle notre agent ce trouve

			group -- String -- le group d'agent

			return AbstractAgent.ReturnCode permet de savoir si le rajout c'est bien passé
			"""

			return self.getRetAgent().leaveGroup(group)

		def myGroups(self):
			return self.getRetAgent().myGroups()

		def myRolesIn(self,group):
			return self.getRetAgent().myRolesIn(group)

		def numberOfAgentsInRole(self, group, role):
			"""
			Permet de savoir combien d'agent d'un groupe d'agent on ce role

			group -- String -- le group d'agent
			role -- String -- le role des agents

			return : int le nombre d'agent
			"""

			return self.getRetAgent().numberOfAgentsInRole(group, role)

		# Methode contenu dans la class WarFood

		def getMaxDistanceTakeFood(self):
			"""
			Permet de savoir la distance maximum pour que un agent puisse donner de  la
			nourriture à un autre agent

			return : double la dictance max
			"""

			return WarFood.MAX_DISTANCE_TAKE

		def getFoodHealthGiven(self):
			"""
			Permet de savoir de combien de points de vie, nous restaure la nourriture

			return : int nombre de points de vie restauré
			"""
			return WarFood.HEALTH_GIVEN
		
		def setTargetDistance(self, targetDistance):
			self.getRetAgent().setTargetDistance(targetDistance)

# === FONCTIONS ===

def sendMessage(idAgent, message, content=""):
	return WA.sendMessage(idAgent, message, content)

def broadcastAll(message, content):
	WA.broadcastMessageToAll(message,content)

def broadcastMessageToAll(message, content):
	WA.broadcastMessageToAll(message, content)

def broadcastMessageToAgentType(agentType, message, content) :
	return WA.broadcastMessageToAgentType(agentType, message, content)

def broadcastMessage(groupName,  roleName,  message,   content) :
	return WA.broadcastMessage(groupName, roleName, message, content)

def broadcastMessageToGroup(groupName,  message,   content) :
	return WA.broadcastMessageToGroup(groupName, message, content)

def reply(warMessage,  message, content):
	return WA.reply(warMessage, message, content)

def getMessages():
	return WA.getMessages()

def setIdNextAgentToGive(idNextAgentToGive):
	WA.setIdNextAgentToGive(idNextAgentToGive)

def getBagSize():
	return WA.getBagSize()

def getNbElementsInBag():
	return WA.getNbElementsInBag()

def isBagEmpty():
	return WA.isBagEmpty()

def isBagFull():
	return WA.isBagFull()

def setViewDirection(viewDirection):
	WA.setViewDirection(viewDirection)

def getHealth():
	return WA.getHealth()

def getPerceptsAllies():
	return WA.getPerceptsAllies()

def getPerceptsEnemies():
	return WA.getPerceptsEnemies()

def getPerceptsResources():
	return WA.getPerceptsResources()

def getPerceptsAlliesByType(agentType):
	return WA.getPerceptsAlliesByType(agentType)

def getPerceptsEnemiesByType(agentType):
	return WA.getPerceptsEnemiesByType(agentType)

def getPercepts():
	return WA.getPercepts()

def getDebugString():
	return WA.getDebugString()

def setDebugString(debug):
	WA.setDebugString(debug)

def getDebugStringColor():
	return WA.getDebugStringColor()

def setDebugStringColor(color):
	WA.setDebugStringColor(color)

def getAveragePositionOfUnitInPercept(agentType, ally):
	return WA.getAveragePositionOfUnitInPercept(agentType, ally)

def getIndirectPositionOfAgentWithMessage(message):
	return WA.getIndirectPositionOfAgentWithMessage(message)

def getTargetedAgentPosition(angleToAlly, distanceFromAlly, angleFromAllyToTarget, distanceBetweenAllyAndTarget):
	return WA.getTargetedAgentPosition(angleToAlly, distanceFromAlly, angleFromAllyToTarget, distanceBetweenAllyAndTarget)

def getViewDirection():
	return WA.getViewDirection()

def getMaxHealth():
	return WA.getMaxHealth()

def isBlocked():
	return WA.isBlocked()

def getSpeed():
	return WA.getSpeed()

def getHeading():
	return WA.getHeading()

def setHeading(angle):
	WA.setHeading(angle)

def RandomHeading():
	WA.RandomHeading()

def setRandomHeading(rangeHeading):
	WA.setRandomHeading(rangeHeading)

def getTeamName():
	return WA.getTeamName()

def isEnemy(percept):
	return WA.isEnemy(percept)

def getID():
	return WA.getID()

def requestRole(group, role):
	return WA.requestRole(group, role)

def leaveRole(group, role):
	return WA.leaveRole(group, role)

def leaveGroup(group):
	return WA.leaveGroup(group)

def numberOfAgentsInRole(group, role):
	return WA.numberOfAgentsInRole(group, role)

def getMaxDistanceTakeFood():
	return WarFood.MAX_DISTANCE_TAKE

def getFoodHealthGiven():
	return WarFood.HEALTH_GIVEN

def angleOfView():
	return WA.angleOfView();

def bagSize():
	return WA.bagSize();

def cost():
	return WA.cost();

def distanceOfView():
	return WA.distanceOfView();

def maxHealth():
	return WA.maxHealth();

def speed():
	return WA.speed();

def create():
	return WA.create();
	
def build():
	return WA.build();

def eat():
	return WA.eat();

def give():
	return WA.give();

def idle():
	return WA.idle();

def move():
	return WA.move();

def take():
	return WA.take();

def maxDistanceGive():
	return WA.maxDistanceGive();

def setNextAgentToCreate(nextAgentToCreate):
	WA.setNextAgentToCreate(nextAgentToCreate)

def setNextBuildingToBuild(nextBuild):
	WA.setNextBuildingToBuild(nextBuild)

def getNextAgentToCreate():
	return WA.getNextAgentToCreate()

def getNextBuildingToBuild():
	return WA.getNextBuildingToBuild()

def getIdNextBuildingToRepair():
	return WA.getIdNextBuildingToRepair()

def setIdNextBuildingToRepair(idBuild):
	return WA.setIdNextBuildingToRepair(idBuild)

def isAbleToCreate(agent):
	return WA.isAbleToCreate(agent)

def isAbleToBuild(agent):
	return WA.isAbleToBuild(agent)

def ticksToReload():
	return WA.ticksToReload();

def fire():
	return WA.fire();

def reloadWeapon():
	return WA.reloadWeapon();

def isReloaded():
	return WA.isReloaded();

def isReloading():
	return WA.isReloading();

def printDevelopper():
	print "Lopez Jimmy"

def getPerceptsFood():
	__percept = WA.getPercepts();
	__food = [];
	for percept in __percept:
		if (percept.getType().equals(WarAgentType.WarFood)):
			__food.append(percept)
	return __food;

def getPerceptsWarBase():
	__percept = WA.getPercepts()
	__base = []
	for percept in __percept:
		if (percept.getType().equals(WarAgentType.WarBase)):
			__base.append(percept)
	return __base;

def getPerceptsWarEngineer():
	__percept = WA.getPercepts()
	__engi = []
	for percept in __percept:
		if (percept.getType().equals(WarAgentType.WarEngineer)):
			__engi.append(percept)
	return __engi;

def getPerceptsWarExplorer():
	__percept = WA.getPercepts()
	__explo = []
	for percept in __percept:
		if (percept.getType().equals(WarAgentType.WarExplorer)):
			__explo.append(percept)
	return __explo;

def getPerceptsWarKamikaze():
	__percept = WA.getPercepts()
	__kami = []
	for percept in __percept:
		if (percept.getType().equals(WarAgentType.WarKamikaze)):
			__kami.append(percept)
	return __kami;

def getPerceptsWarBase():
	__percept = WA.getPercepts()
	__base = []
	for percept in __percept:
		if (percept.getType().equals(WarAgentType.WarBase)):
			__base.append(percept)
	return __base;

def getPerceptsWarRocketLauncher():
	__percept = WA.getPercepts()
	__rockLaun = []
	for percept in __percept:
		if (percept.getType().equals(WarAgentType.WarRocketLauncher)):
			__rockLaun.append(percept)
	return __rockLaun;

def getPerceptsWarTurret():
	__percept = WA.getPercepts()
	__turr = []
	for percept in __percept:
		if (percept.getType().equals(WarAgentType.WarTurret)):
			__turr.append(percept)
	return __turr;


def getPerceptsAlliesWarBase():
	return getPerceptsAlliesByType(WarAgentType.WarBase);

def getPerceptsAlliesWarEngineer():
	return getPerceptsAlliesByType(WarAgentType.WarEngineer);

def getPerceptsAlliesWarExplorer():
	return getPerceptsAlliesByType(WarAgentType.WarExplorer);

def getPerceptsAlliesWarKamikaze():
	return getPerceptsAlliesByType(WarAgentType.WarKamikaze);

def getPerceptsAlliesWarRocketLauncher():
	return getPerceptsAlliesByType(WarAgentType.WarRocketLauncher);

def getPerceptsAlliesWarTurret():
	return getPerceptsAlliesByType(WarAgentType.WarTurret);

def getPerceptsEnemiesWarBase():
	return getPerceptsEnemiesByType(WarAgentType.WarBase);

def getPerceptsEnemiesWarEngineer():
	return getPerceptsEnemiesByType(WarAgentType.WarEngineer);

def getPerceptsEnemiesWarExplorer():
	return getPerceptsEnemiesByType(WarAgentType.WarExplorer);

def getPerceptsEnemiesWarKamikaze():
	return getPerceptsEnemiesByType(WarAgentType.WarKamikaze);

def getPerceptsEnemiesWarRocketLauncher():
	return getPerceptsEnemiesByType(WarAgentType.WarRocketLauncher);

def getPerceptsEnemiesWarTurret():
	return getPerceptsEnemiesByType(WarAgentType.WarTurret);

def followTarget(percept):
	setHeading(percept.getAngle());

def haveTarget():
	__percept = WA.getPercepts();
	return len(__percept) > 0;

def haveTargets(percept):
	return len(percept) > 0;

def haveNoTarget():
	__percept = WA.getPercepts();
	return len(__percept) == 0;

def haveNoTargets(percept):
	return len(percept) == 0;

def isBase(percept):
	return percept.getType().equals(WarAgentType.WarBase);

def isEngineer(percept):
	return percept.getType().equals(WarAgentType.WarEngineer);

def isExplorer(percept):
	return percept.getType().equals(WarAgentType.WarExplorer);

def isKamikaze(percept):
	return percept.getType().equals(WarAgentType.WarKamikaze);

def isRocketLauncher(percept):
	return percept.getType().equals(WarAgentType.WarRocketLauncher);

def isTurret(percept):
	return percept.getType().equals(WarAgentType.WarTurret);

def isFood(percept):
	return percept.getType().equals(WarAgentType.WarFood);

def isAlly(percept):
	return not isEnemy(percept);

def shootTarget():
	if (isReloaded()):
		return fire()
	else :
		return reloadWeapon()

def pickableFood(percept):
	if(isFood(percept)):
		return percept.getDistance() < getMaxDistanceTakeFood();
	return False;

def isNotBagFull():
	return not WA.isBagFull();

def isMessageOfWarBase(message):
	return message.getSenderType() == WarAgentType.WarBase;

def isMessageOfWarEngineer(message):
	return message.getSenderType() == WarAgentType.WarEngineer;

def isMessageOfWarExplorer(message):
	return message.getSenderType() == WarAgentType.WarExplorer;

def isMessageOfWarKamikaze(message):
	return message.getSenderType() == WarAgentType.WarKamikaze;

def isMessageOfWarRocketLauncher(message):
	return message.getSenderType() == WarAgentType.WarRocketLauncher;

def isMessageOfWarTurret(message):
	return message.getSenderType() == WarAgentType.WarTurret;

def sendMessageToBases(message, content):
	return broadcastMessageToAgentType(WarAgentType.WarBase, message, content);

def sendMessageToEngineers(message, content):
	return broadcastMessageToAgentType(WarAgentType.WarEngineer, message, content);

def sendMessageToExplorers(message, content):
	return broadcastMessageToAgentType(WarAgentType.WarExplorer, message, content);

def sendMessageToKamikazes(message, content):
	return broadcastMessageToAgentType(WarAgentType.WarKamikaze, message, content);

def sendMessageToRocketLaunchers(message, content):
	return broadcastMessageToAgentType(WarAgentType.WarRocketLauncher, message, content);

def sendMessageToTurrets(message, content):
	return broadcastMessageToAgentType(WarAgentType.WarTurret, message, content);

def isPossibleToGiveFood(percept):
	return percept.getDistance() < maxDistanceGive();

def giveToTarget(percept) :
	setIdNextAgentToGive(percept.getID());
	return WA.give()

def createEngineer():
	setNextAgentToCreate(WarAgentType.WarEngineer)
	return WA.create()

def createExplorer():
	setNextAgentToCreate(WarAgentType.WarExplorer)
	return WA.create()

def createKamikaze():
	setNextAgentToCreate(WarAgentType.WarKamikaze)
	return WA.create()

def createRocketLauncher():
	setNextAgentToCreate(WarAgentType.WarRocketLauncher)
	return WA.create()

def createTurret():
	setNextAgentToCreate(WarAgentType.WarTurret)
	return WA.create()

def createWall():
	setNextBuildingToBuild(WarAgentType.Wall);
	return WA.build()

def face(percept):
	setHeading(percept.getAngle())

def getGroups():
	return WA.getGroups()

def getRolesIn(group):
	return WA.getRolesIn(group)

def myGroups():
	return WA.myGroups()

def myRolesIn(group):
	return WA.myROlesIn(group)

def setTargetDistance(targetDistance):
	WA.setTargetDistance(targetDistance)
