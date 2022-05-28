package core;

import models.Particle;
import models.Vector;

import java.util.List;
import java.util.Set;

public abstract class Heuristic {
	
	public abstract Vector getTarget(Particle p, Set<Particle> nearerZombies, Set<Particle> contactZombies,
									 Set<Particle> nearerHumans, Set<Particle> contactHumans);

}
