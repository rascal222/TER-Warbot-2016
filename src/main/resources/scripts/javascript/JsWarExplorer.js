var JsWarExplorer = JsWarAgent.extend({

	getExplorer : function () {
		var explo = edu.warbot.agents.agents.WarExplorer;
		return explo;
	},

	angleOfView : function () {
		return this.getExplorer().ANGLE_OF_VIEW;
	},

	bagSize : function () {
		return this.getExplorer().BAG_SIZE;
	},

	cost : function () {
		return this.getExplorer().COST;
	},

	distanceOfView : function () {
		return this.getExplorer().DISTANCE_OF_VIEW;
	},

	maxHealth : function () {
		return this.getExplorer().MAX_HEALTH;
	},

	speed : function () {
		return this.getExplorer().SPEED;
	},

	eat : function () {
		return this.getExplorer().ACTION_EAT;
	},

	give : function () {
		return this.getExplorer().ACTION_GIVE;
	},

	idle : function () {
		return this.getExplorer().ACTION_IDLE;
	},

	move : function () {
		return this.getExplorer().ACTION_MOVE;
	},

	take : function () {
		return this.getExplorer().ACTION_TAKE;
	},

	maxDistanceGive : function () {
		return this.getExplorer().MAX_DISTANCE_GIVE;
	}
});