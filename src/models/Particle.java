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

    private double visual;  //campo visual

    private double radio;
    private double maxV;
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

    public double getDistanceTo(Particle p){
        return position.getDistanceTo(p.getPosition());
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
            //todo: cambiar campo visual y demas
        }
    }


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
    //devulve true si era humano y se tranformo a zombie
    public boolean next(List<Particle> humans,  List<Particle> zombie, double dt){
        boolean toReturn = false;
        List<Particle> contactZ =  detectContact(zombie);
        List<Particle> contactH = detectContact(humans);

        //maneja las conversion a zombie
        if(isConverted(contactZ, dt)){
            convertToZombie();
            toReturn = true;
        }

        //aplica la heuristica
        Vector target = heuristic.getTarget(this ,humans, zombie);

        //segun si esta en conacto con otras particuls es el CPM que aplica
        if(contactH.isEmpty() && contactZ.isEmpty()){
            CPM.noContact(this, target, dt);
        }else{
            //dejo todas las particulas con las que esta en contacto en unsa sola lista
            contactH.addAll(contactZ);
            CPM.Contact(this, target,contactH.get(1), dt);
        }
        return toReturn;
    }


    private boolean isConverted(List<Particle> contactZ, double dt){
        if(isZombie){return false;}
        if(zombieContactTime >= CONVERTER_TIME ){return true;}
        if(zombieContactTime > 0){zombieContactTime += dt;}
        return false;
    }




}
