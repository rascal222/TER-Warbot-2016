var JsWarRocketLauncher = JsWarAgent.extend({

	getRockLaunch : function () {
		var rl = edu.warbot.agents.agents.WarRocketLauncher;
		return rl;
	},

	angleOfView : function () {
		return this.getRockLaunch().ANGLE_OF_VIEW;
	},

	bagSize : function () {
		return this.getRockLaunch().BAG_SIZE;
	},

	cost : function () {
		return this.getRockLaunch().COST;
	},

	distanceOfView : function () {
		return this.getRockLaunch().DISTANCE_OF_VIEW;
	},

	maxHealth : function () {
		return this.getRockLaunch().MAX_HEALTH;
	},

	speed : function () {
		return this.getRockLaunch().SPEED;
	},

	ticksToReload : function () {
		return this.getRockLaunch().TICKS_TO_RELOAD;
	},

	eat : function () {
		return this.getRockLaunch().ACTION_EAT;
	},

	fire : function () {	
		return this.getRockLaunch().ACTION_FIRE;
	},

	give : function () {
		return this.getRockLaunch().ACTION_GIVE;
	},

	idle : function () {
		return this.getRockLaunch().ACTION_IDLE;
	},

	move : function () {
		return this.getRockLaunch().ACTION_MOVE;
	},

	reloadWeapon : function () {
		return this.getRockLaunch().ACTION_RELOAD;
	},

	take : function () {
		return this.getRockLaunch().ACTION_TAKE;
	},

	maxDistanceGive : function () {
		return this.getRockLaunch().MAX_DISTANCE_GIVE;
	},

	isReloaded : function () {
		return this.getRockLaunch().isReloaded();
	},

	isReloading : function () {
		return this.getRockLaunch().isReloading();
	},
	
	setTargetDistance : function (targetDistance) {
		this.getRockLaunch().setTargetDistance(targetDistance);
	}

	
});