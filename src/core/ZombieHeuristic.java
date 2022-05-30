package core;

import models.Particle;
import models.Vector;
import java.util.Set;
import java.util.TreeSet;

public class ZombieHeuristic extends Heuristic {

	public ZombieHeuristic(double spaceRadio) {
		super(spaceRadio);
	}

	@Override
	public Vector getTarget(Particle p, Set<Particle> nearerZombies, Set<Particle> contactZombies,
							Set<Particle> nearerHumans, Set<Particle> contactHumans) {

		if(nearerHumans.isEmpty() && contactHumans.isEmpty()){
			return randomWalk();
		}

		return closestParticle(p, (TreeSet<Particle>) nearerHumans, (TreeSet<Particle>) contactHumans);
	}

	//----------
	private Vector closestParticle(Particle p, TreeSet<Particle> nearerHumans, TreeSet<Particle> contactHumans){
		if(!contactHumans.isEmpty()){
			return contactHumans.first().getPosition();
		}
		return nearerHumans.first().getPosition();
	}


	//todo: necesitaria las dienciones del espacio
	private Vector randomWalk(){
		return  null;
	}




}
