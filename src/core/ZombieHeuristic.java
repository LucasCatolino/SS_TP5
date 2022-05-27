package core;

import core.Heuristic;
import models.Particle;
import models.Vector;

import javax.swing.*;
import java.util.List;

public class ZombieHeuristic extends Heuristic {

	@Override
	public Vector getTarget(Particle p, List<Particle> people, List<Particle> zombies) {
		if(people.isEmpty()){
			return randomWalk();
		}
		
		return closestParticle(p, people);
	}

	private Vector closestParticle(Particle p, List<Particle> particles){
		double minDistance = Double.MAX_VALUE;
		Vector toReturn = null;
		for (Particle currentP : particles) {
			double aux = p.getDistanceTo(currentP);
			if(aux < minDistance){
				minDistance = aux;
				toReturn = currentP.getPosition();
			}
		}
		return toReturn;
	}


	//todo: necesitaria las dienciones del espacio
	private Vector randomWalk(){
		return  null;
	}




}
