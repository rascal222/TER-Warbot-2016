
var JsWarBase = JsWarAgent.extend({

	getBase : function () {
		var base = edu.warbot.agents.agents.WarBase;
		return base;
	},

	angleOfView : function () {

		return this.getBase().ANGLE_OF_VIEW;
	},

	bagSize : function () {
		return this.getBase().BAG_SIZE;
	},

	cost : function () {
		return this.getBase().COST;
	},

	distanceOfView : function () {
		return this.getBase().DISTANCE_OF_VIEW;
	},

	maxHealth : function () {
		return this.getBase().MAX_HEALTH;
	},

	create : function () {
		return this.getBase().ACTION_CREATE;
	},

	eat : function () {
		return this.getBase().ACTION_EAT;
	},

	give : function () {
		return this.getBase().ACTION_GIVE;
	},

	idle : function () {
		return this.getBase().ACTION_IDLE;
	},

	maxDistanceGive : function () {
		return this.getBase().MAX_DISTANCE_GIVE;
	},

	isBlocked : function () {
		return false;
	},

	getSpeed : function () {
		return 0;
	},

	setNextAgentToCreate : function (agent) {
		this.getAgent().setNextAgentToCreate(agent);
	},

	getNextAgentToCreate : function () {
		return this.getAgent().getNextAgentToCreate();
	},

	isAbleToCreate : function (agent) {
		return this.getAgent().isAbleToCreate(agent);

	}
});