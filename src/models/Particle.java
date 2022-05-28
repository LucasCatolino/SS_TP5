package models;


import core.Heuristic;
import core.PersonHeuristic;
import core.ZombieHeuristic;
import core.CPM;

import java.util.*;

public class Particle{

    static private int nextId = 0;
    static private final double CONVERTER_TIME = 7;
    static private  final double Z_INACTIVE_VELOCITY = 0.3; // m/s
    static private  final double Z_MAX_VELOCITY = 4; // m/s
    static private  final double H_MAX_VELOCITY = 5; //todo:no se cual se

    private double zombieContactTime = 0; //si se pasa de 7seg se convierte

    private double radio;
    private double maxV;
    private Vector position;
    private Vector velocity;
    private boolean isZombie;
    private Heuristic heuristic;
    private int id;

    public Particle(Particle particle){
        position = new Vector(particle.position.getX(), particle.position.getY());
        velocity = new Vector(particle.velocity.getX(), particle.velocity.getY());
        this.id = particle.id;
        if (isZombie) {
            maxV = Z_MAX_VELOCITY;
			heuristic= new ZombieHeuristic();
		} else {

            maxV = H_MAX_VELOCITY;
			heuristic= new PersonHeuristic();
		}
    }

    public Particle(Vector position, Vector velocity, double radio, boolean isZombie) {
        this.position = position;
        this.velocity = velocity;
        this.radio = radio;
        this.id = getNextId();
        if (isZombie) {
            maxV = Z_MAX_VELOCITY;
            heuristic= new ZombieHeuristic();
        } else {
            maxV = H_MAX_VELOCITY;
            heuristic= new PersonHeuristic();
        }
    }

    static private int getNextId(){
        int toReturn = nextId;
        nextId++;
        return toReturn;
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
            maxV = Z_MAX_VELOCITY;
        }
    }

    //------------------------------------------

    //devuelve una particula con la nueva posicion;
    public Particle next(Set<Particle> nearerZombies, Set<Particle> contactZombies,
                         Set<Particle> nearerHumans, Set<Particle> contactHumans, double dt){

        Particle newParticle = new Particle(this);

        //maneja la conversión a zombie
        if(isConverted(contactZombies, dt)){
            newParticle.convertToZombie();
        }

        //aplica la heurística
        Vector target = heuristic.getTarget(newParticle , nearerZombies, contactZombies, nearerHumans, contactHumans);

        //si es zombie y no tiene humanos cerca cambia la velocidad
        if(isZombie){
            if(nearerHumans.isEmpty() && contactHumans.isEmpty()){
                maxV = Z_INACTIVE_VELOCITY;
            }else{
                maxV = Z_MAX_VELOCITY;
            }
        }

        //dejo todas las particulas con las que esta en contacto en una sola lista
        contactZombies.addAll(contactHumans);
        CPM.apply(newParticle, target, dt, (TreeSet<Particle>) contactZombies);

        //retorno la nueva particula con la nueva posiciones
        return newParticle;
    }


    private boolean isConverted(Set<Particle> contactZombies, double dt){
        if(isZombie){return false;}
        if(zombieContactTime >= CONVERTER_TIME ){return true;}
        if(!contactZombies.isEmpty() || zombieContactTime != 0){
            zombieContactTime += dt;
        }
        return false;
    }

    /*//todo:borrar
    private Particle detectContact(Set<Particle> particles, double dt){
        Particle toReturn = null;
        for (Particle currentP : particles) {
            //particle esta ordena, si una esta mas lejos del radio las demas tmabien
            if(this.getDistanceTo(currentP) > radio)
                return toReturn;
            //aca es que se estan tocando
            //si es zombie
            if(!this.isZombie && currentP.isZombie)
                zombieContactTime += dt;
            //solo me interesa el mas cercano al centro
            if(toReturn == null)
                toReturn = currentP;
        }
        return toReturn;

    }*/

    //-------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
