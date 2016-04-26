package teams.presentation_relic;

public abstract class UtilTir {
	/* Calcule l'angle optimal pour faire mouche sur une cible (en supposant que la vitesse du projectile est supérieur à celle de la cible)
	 * 
	 * vitesseCible : vitesse de la cible au moment du tir (cf. fichier de config)
	 * vitesseProjectile : vitesse du projectile tiré par le tireur au moment du tir (WarAgentType.AgentType.)
	 * distanceCibleTireur : getDistance() du percept.
	 * headingCible : getHeading() du percept
	 * angleCible : getAngle() du percept 
	 * 
	 * Sources : 
	 * http://howlingmoonsoftware.com/wordpress/leading-a-target/
	 * http://gamedev.stackexchange.com/questions/74282/aim-at-moving-target-or-predicting-targets-position-at-time-it-takes-for-proje?lq=1
	 */
	static double angleToShoot(double vitesseCible, 
			double vitesseProjectile, 
			double distanceCibleTireur, 
			double headingCible,
			double angleCible) {
		//System.out.println("="+);
		System.out.println("angleCible="+angleCible);
		double angleCibleTrans = angleCible;
		
		double angleCibleRelativeTireur = headingCible - angleCibleTrans;
		if (angleCibleRelativeTireur < 0) {
			angleCibleRelativeTireur += 360;
		}
		
		
		
		System.out.println("Calcul Tir");

		System.out.println("angleCibleRelativeTireur="+angleCibleRelativeTireur);
		double directionCibleRelativeTireur = Math.toRadians(angleCibleRelativeTireur);
		System.out.println("Math.sin(directionCibleRelativeTireur)="+Math.sin(directionCibleRelativeTireur));
		double vitesseAngulaireCible = vitesseCible * Math.abs(Math.sin(directionCibleRelativeTireur));
		System.out.println("vitesseAngulaireCible="+vitesseAngulaireCible);
		double deltaPos = distanceCibleTireur;
		double deltaVitesse = vitesseProjectile - vitesseAngulaireCible;
		System.out.println("distance="+distanceCibleTireur);
		double tempsAvantCollision = deltaPos/deltaVitesse;
		System.out.println("tempsAvantCollision="+tempsAvantCollision);
		double distanceParcourueCible = vitesseCible * tempsAvantCollision;
		double distanceParcourueProjectile = vitesseProjectile * tempsAvantCollision;
		
		double numerateurCosAngleDeTir = - (distanceParcourueCible * distanceParcourueCible)
				+ (distanceCibleTireur * distanceCibleTireur)
				+ (distanceParcourueProjectile * distanceParcourueProjectile);
		double denominateurCosAngleDeTir = 2.0 * distanceCibleTireur * distanceParcourueProjectile;
		double cosAngleDeTir = numerateurCosAngleDeTir / denominateurCosAngleDeTir;
		System.out.println("cosAngleDeTir="+cosAngleDeTir);
		
		double angleDeTir;
		if (headingCible<90 && headingCible>270) {
			// Cible dirigé vers la gauche
			angleDeTir = angleCible + Math.toDegrees(Math.acos(cosAngleDeTir));
		} else {
			angleDeTir = angleCible - Math.toDegrees(Math.acos(cosAngleDeTir));
		}
		
		
		System.out.println("angleDeTir="+angleDeTir);
		//return angleDeTir;	
		return angleDeTir;
	}
}
