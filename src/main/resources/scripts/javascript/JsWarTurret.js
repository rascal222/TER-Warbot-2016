var JsWarTurret = JsWarAgent.extend({

	getTurret : function () {
		var tur = edu.warbot.agents.agents.WarTurret;
		return tur;
	},

	angleOfView : function () {
		return this.getTurret().ANGLE_OF_VIEW;
	},

	bagSize : function () {
		return this.getTurret().BAG_SIZE;
	},

	cost : function () {
		return this.getTurret().COST;
	},

	distanceOfView : function () {
		return this.getTurret().DISTANCE_OF_VIEW;
	},

	maxHealth : function () {
		return this.getTurret().MAX_HEALTH;
	},

	ticksToReload : function () {
		return this.getTurret().TICKS_TO_RELOAD;
	},

	eat : function () {
		return this.getTurret().ACTION_EAT;
	},

	fire : function () {	
		return this.getTurret().ACTION_FIRE;
	},

	give : function () {
		return this.getTurret().ACTION_GIVE;
	},

	idle : function () {
		return this.getTurret().ACTION_IDLE;
	},

	reloadWeapon : function () {
		return this.getTurret().ACTION_RELOAD;
	},

	maxDistanceGive : function () {
		return this.getTurret().MAX_DISTANCE_GIVE;
	},

	isBlocked : function () {
		return false;
	},

	getSpeed : function () {
		return 0;
	},

	isReloaded : function () {
		return this.getTurret().isReloaded();
	},

	isReloading : function () {
		return this.getTurret().isReloading();
	}

});