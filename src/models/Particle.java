package models;

public class Particle{
	
    private Vector position;
    private Vector velocity;
    private boolean zombie;
    private Heuristic heuristic;

    public Particle(Particle particle){
        position = new Vector(particle.position.getX(), particle.position.getY());
        velocity = new Vector(particle.velocity.getX(), particle.velocity.getY());
        if (zombie) {
			heuristic= new ZombieHeuristic();
		} else {
			heuristic= new PersonHeuristic();
		}
    }

    public Particle(Vector position, Vector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double velocityX, double velocityY){
        velocity.setX(velocityX);
        velocity.setY(velocityY);
    }

}
