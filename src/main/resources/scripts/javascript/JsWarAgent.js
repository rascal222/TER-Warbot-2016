//importPackage( Packages. );

importPackage( Packages.java.lang );
importPackage( Packages.edu.warbot.agents.agents );
importPackage( Packages.edu.warbot.agents );
importPackage( Packages.edu.warbot.agents.enums );
importPackage( Packages.edu.warbot.agents.percepts );
importPackage( Packages.edu.warbot.agents.resources );
importPackage( Packages.edu.warbot.brains );
importPackage( Packages.edu.warbot.scriptcore.wrapper );
importPackage( Packages.madkit.kernel ); // TODO verif si ok
importPackage( Packages.java.awt );
importPackage( Packages.java.util );
importPackage( Packages.edu.warbot.communications );
importPackage( Packages.edu.warbot.tools.geometry );

var JsWarAgent = Class.extend({

	initialize : function() {
		this.agentJava = "agent";
    	},

   	link : function(t) { 
		this.agentJava = JsWrapper(t);
   	},

	getAgent : function() {
		return this.agentJava.get();
	},

	sendMessage : function(idAgent, messageAgent, content) {
		return this.getAgent().sendMessage(idAgent, messageAgent, content);
	},

	broadcastMessageToAll : function (messageAgent, content) {
		this.getAgent().broadcastMessageToAll(messageAgent, content);
	},

	broadcastMessageToAgentType : function (agentType, messageAgent, content) {
		return this.getAgent().broadcastMessageToAgentType(agentType, messageAgent, content);
	},

	broadcastMessage : function (groupName, roleName, messageAgent, content) {
		return this.getAgent().broadcastMessage(groupName, roleName, messageAgent, content);
	},

	reply : function (warMessage,  messageAgent, content) {
		return this.getAgent().reply(warMessage, messageAgent, content);
	},

	getMessages : function () {
		return this.getAgent().getMessages();
	},

	setIdNextAgentToGive : function (idNextAgentToGive) {
		this.getAgent().setIdNextAgentToGive(idNextAgentToGive);
	},

	getBagSize : function () {
		return this.getAgent().getBagSize();
	},

	getNbElementsInBag : function () {
		return this.getAgent().getNbElementsInBag();
	},

	isBagEmpty : function () {
		return this.getAgent().isBagEmpty();
	},

	isBagFull : function () {
		return this.getAgent().isBagFull();
	},

	setViewDirection : function (viewDirection) {
		this.getAgent().setViewDirection(viewDirection);
	},	

	getHealth : function () {
		return this.getAgent().getHealth();
	},

	getPerceptsAllies : function () {
		return this.getAgent().getPerceptsAllies();
	},

	getPerceptsEnemies : function () {
		return this.getAgent().getPerceptsEnemies();
	},

	getPerceptsResources : function () {
		return this.getAgent.getPerceptsResources();
	},

	getPerceptsAlliesByType : function (agentType) {
		return this.getAgent().getPerceptsAlliesByType(agentType);
	},

	getPerceptsEnemiesByType : function (agentType) {
		return this.getAgent().getPerceptsEnemiesByType(agentType);
	},

	getPercepts : function () {
		return this.getAgent().getPercepts();
	},

	getDebugString : function () {
		return this.getAgent().getDebugString();
	},

	setDebugString : function (debug) {
		this.getAgent().setDebugString(debug);
	},

	getDebugStringColor : function () {
		return this.getAgent().getDebugStringColor();
	},

	setDebugStringColor : function (color) {
		this.getAgent().setDebugStringColor(color);
	},

	getAveragePositionOfUnitInPercept : function (agentType, ally) {
		return this.getAgent().getAveragePositionOfUnitInPercept(agentType, ally);
	},

	getIndirectPositionOfAgentWithMessage : function (messageAgent) {
		return this.getAgent().getIndirectPositionOfAgentWithMessage(messageAgent);
	},

	getTargetedAgentPosition : function (angleToAlly, distanceFromAlly, angleFromAllyToTarget, distanceBetweenAllyAndTarget) {
		return this.getAgent().getTargetedAgentPosition(angleToAlly, distanceFromAlly, angleFromAllyToTarget, distanceBetweenAllyAndTarget);
	},

	getViewDirection : function () {
		return this.getAgent().getViewDirection();
	},

	getMaxHealth : function () {
		return this.getAgent().getMaxHealth();
	},

	isBlocked : function() {
		return this.getAgent().isBlocked();
	},

	getSpeed : function () {
		return this.getAgent().getSpeed();
	},

	getHeading : function () {
		return this.getAgent().getHeading();
	},

	setHeading : function (angle) {
		this.getAgent().setHeading(angle);
	},

	randomHeading : function() {
		this.getAgent().setRandomHeading();
	},
	
	setRandomHeading : function (rangeHeading) {
		this.getAgent().setRandomHeading(rangeHeading);
	},

	getTeamName : function () {
		return this.getAgent().getTeamName();
	},

	isEnemy : function (percept) {
		return this.getAgent().isEnemy(percept);
	},

	getID : function () {
		return this.getAgent().getID();
	},

	requestRole : function (group, role) {
		return this.getAgent().requestRole(group, role);
	},

	leaveRole : function (group, role) {
		return this.getAgent().leaveRole(group, role);
	},

	leaveGroup : function (group) {
		return this.getAgent().leaveGroup(group);
	},

	numberOfAgentsInRole : function (group, role) {
		return this.getAgent().numberOfAgentsInRole(group, role);
	},

	getMaxDistanceTakeFood : function () {
		var take = edu.warbot.agents.resources.WarFood.MAX_DISTANCE_TAKE;
		return take;
	},

	getFoodHealthGiven : function () {
		var heal = edu.warbot.agents.resources.WarFood.HEALTH_GIVEN;
		return heal;
	},

	getFoodType : function () {
		//var food = edu.warbot.agents.enums.WarFood.toString();
		return "WarFood";
	}
});