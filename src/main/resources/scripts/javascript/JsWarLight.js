var JsWarLight = JsWarAgent.extend({

	getLight : function () {
		var rl = edu.warbot.agents.agents.WarLight;
		return rl;
	},

	angleOfView : function () {
		return this.getLight().ANGLE_OF_VIEW;
	},

	bagSize : function () {
		return this.getLight().BAG_SIZE;
	},

	cost : function () {
		return this.getLight().COST;
	},

	distanceOfView : function () {
		return this.getLight().DISTANCE_OF_VIEW;
	},

	maxHealth : function () {
		return this.getLight().MAX_HEALTH;
	},

	speed : function () {
		return this.getLight().SPEED;
	},

	ticksToReload : function () {
		return this.getLight().TICKS_TO_RELOAD;
	},

	eat : function () {
		return this.getLight().ACTION_EAT;
	},

	fire : function () {	
		return this.getLight().ACTION_FIRE;
	},

	give : function () {
		return this.getLight().ACTION_GIVE;
	},

	idle : function () {
		return this.getLight().ACTION_IDLE;
	},

	move : function () {
		return this.getLight().ACTION_MOVE;
	},

	reloadWeapon : function () {
		return this.getLight().ACTION_RELOAD;
	},

	take : function () {
		return this.getLight().ACTION_TAKE;
	},

	maxDistanceGive : function () {
		return this.getLight().MAX_DISTANCE_GIVE;
	},

	isReloaded : function () {
		return this.getLight().isReloaded();
	},

	isReloading : function () {
		return this.getLight().isReloading();
	}

	
});