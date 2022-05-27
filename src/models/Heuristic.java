package models;

import java.util.List;

public abstract class Heuristic {
	
	public abstract Vector getTarget(List<Particle> people, List<Particle> zombies);

}
