var JsWarKamikaze = JsWarAgent.extend({

	getKami : function () {
		var rl = edu.warbot.agents.agents.WarRocketLauncher;
		return rl;
	},

	angleOfView : function () {
		return this.getKami().ANGLE_OF_VIEW;
	},

	bagSize : function () {
		return this.getKami().BAG_SIZE;
	},

	cost : function () {
		return this.getKami().COST;
	},

	distanceOfView : function () {
		return this.getKami().DISTANCE_OF_VIEW;
	},

	maxHealth : function () {
		return this.getKami().MAX_HEALTH;
	},

	speed : function () {
		return this.getKami().SPEED;
	},

	eat : function () {
		return this.getKami().ACTION_EAT;
	},

	fire : function () {	
		return this.getKami().ACTION_FIRE;
	},

	give : function () {
		return this.getKami().ACTION_GIVE;
	},

	idle : function () {
		return this.getKami().ACTION_IDLE;
	},

	move : function () {
		return this.getKami().ACTION_MOVE;
	},

	reloadWeapon : function () {
		return this.getKami().ACTION_RELOAD;
	},

	take : function () {
		return this.getKami().ACTION_TAKE;
	},

	maxDistanceGive : function () {
		return this.getKami().MAX_DISTANCE_GIVE;
	},

	isReloaded : function () {
		return this.getKami().isReloaded();
	},

	isReloading : function () {
		return this.getKami().isReloading();
	}

	
});