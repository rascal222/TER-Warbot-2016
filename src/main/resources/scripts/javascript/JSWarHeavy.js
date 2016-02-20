var JsWarHeavy = JsWarAgent.extend({

	getHeavy : function () {
		var rl = edu.warbot.agents.agents.WarHeavy;
		return rl;
	},

	angleOfView : function () {
		return this.getHeavy().ANGLE_OF_VIEW;
	},

	bagSize : function () {
		return this.getHeavy().BAG_SIZE;
	},

	cost : function () {
		return this.getHeavy().COST;
	},

	distanceOfView : function () {
		return this.getHeavy().DISTANCE_OF_VIEW;
	},

	maxHealth : function () {
		return this.getHeavy().MAX_HEALTH;
	},

	speed : function () {
		return this.getHeavy().SPEED;
	},

	ticksToReload : function () {
		return this.getHeavy().TICKS_TO_RELOAD;
	},

	eat : function () {
		return this.getHeavy().ACTION_EAT;
	},

	fire : function () {	
		return this.getHeavy().ACTION_FIRE;
	},

	give : function () {
		return this.getHeavy().ACTION_GIVE;
	},

	idle : function () {
		return this.getHeavy().ACTION_IDLE;
	},

	move : function () {
		return this.getHeavy().ACTION_MOVE;
	},

	reloadWeapon : function () {
		return this.getHeavy().ACTION_RELOAD;
	},

	take : function () {
		return this.getHeavy().ACTION_TAKE;
	},

	maxDistanceGive : function () {
		return this.getHeavy().MAX_DISTANCE_GIVE;
	},

	isReloaded : function () {
		return this.getHeavy().isReloaded();
	},

	isReloading : function () {
		return this.getHeavy().isReloading();
	}

	
});