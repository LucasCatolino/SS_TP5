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
		// TODO Auto-generated method stub
		return null;
	}

}
