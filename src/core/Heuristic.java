package core;

import models.Particle;
import models.Vector;

import java.util.List;

public abstract class Heuristic {
	
	public abstract Vector getTarget(Particle p, List<Particle> people, List<Particle> zombies);

}
