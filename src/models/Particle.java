package models;


import core.Heuristic;
import core.PersonHeuristic;
import core.ZombieHeuristic;
import core.CPM;

import java.util.ArrayList;
import java.util.List;

public class Particle{

    static private final double CONVERTER_TIME = 7;
    private double zombieContactTime = 0; //si se pasa de 7seg se convierte

    private double radio;
    private double maxV;

    private double visual;  //campo visual

    private Vector position;
    private Vector velocity;

    private boolean isZombie;
    private Heuristic heuristic;

    public Particle(Particle particle){
        position = new Vector(particle.position.getX(), particle.position.getY());
        velocity = new Vector(particle.velocity.getX(), particle.velocity.getY());
        if (isZombie) {
			heuristic= new ZombieHeuristic();
		} else {
			heuristic= new PersonHeuristic();
		}
    }

    public Particle(Vector position, Vector velocity, double radio, boolean isZombie) {
        this.position = position;
        this.velocity = velocity;
        this.radio = radio;
        if (isZombie) {
            heuristic= new ZombieHeuristic();
        } else {
            heuristic= new PersonHeuristic();
        }
    }

    //-------------------------------------------------

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(double velocityX, double velocityY){
        velocity.setX(velocityX);
        velocity.setY(velocityY);
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public double getMaxV() {
        return maxV;
    }

    public void setMaxV(double maxV) {
        this.maxV = maxV;
    }

   public boolean isZombie(){
        return  isZombie;
    }

    public void convertToZombie(){
        if (!isZombie) {
            isZombie = true;
            heuristic= new ZombieHeuristic();
        }
    }

    //-------

    public List<Particle> detectContact(List<Particle> particles){
        List<Particle> toReturn = new ArrayList<>();
        for (Particle currentP : particles) {
            if(position.getDistanceTo(currentP.getPosition()) <= radio){
                toReturn.add(currentP);
            }
        }
        return toReturn;
    }


    //------------------------------------------

    //si no esta en contacto con ninguna particula contactP = null

    public void next(List<Particle> humans,  List<Particle> zombie, double dt){

        List<Particle> contactZ =  detectContact(zombie);
        List<Particle> contactH = detectContact(humans);

        if(isConverted(contactZ)){
            convertToZombie();
        }

        Vector target = heuristic.getTarget(humans, zombie);

        if(contactH.isEmpty() && contactZ.isEmpty()){
            CPM.noContact(this, target, dt);
        }else{
            //dejo todas la particulas en contacto en una misma lista

            contactH.addAll(contactZ);
            CPM.Contact(this, target,contactH.get(1), dt);
        }

    }


    private boolean isConverted(List<Particle> contactZ){
        if(isZombie){
            return false;
        }
        if(zombieContactTime >= CONVERTER_TIME ){
            return true;
        }
        if(contactZ.isEmpty()){
            zombieContactTime = 0;

        }
        return false;
    }




}
