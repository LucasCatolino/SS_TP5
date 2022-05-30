package core;

import core.Heuristic;
import models.Particle;
import models.Vector;

import java.util.List;
import java.util.Set;

public class PersonHeuristic extends Heuristic {

	public PersonHeuristic(double spaceRadio) {
		super(spaceRadio);
	}

	@Override
	public Vector getTarget(Particle p, Set<Particle> nearerZombies, Set<Particle> contactZombies,
							Set<Particle> nearerHumans, Set<Particle> contactHumans) {

		if(nearerZombies.isEmpty() || !contactZombies.isEmpty()) {
			return null;
		}
		Vector bestDir = new Vector(0,0);
		for( Particle z : nearerZombies){
			bestDir.add(z.getPosition().sub(p.getPosition()).opposite().getVersor().multiply(1.0 / z.getDistanceTo(p)));
		}
		return Vector.add(bestDir,p.getPosition());
	}



}
