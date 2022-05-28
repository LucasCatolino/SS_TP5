package core;

import models.Particle;

import java.util.*;

public class Algorithm {
    //constant
    private static final double dt = 0.01; //seg
    private static final double MAX_SIMULATION_TIME = 20; //seg
    private static final double Z_VISUAL_FIELD = 4; //m
    private static final double H_VISUAL_FIELD = 22; //m

    //variables del sistema
    private List<Particle> particles;
   // private List<Particle> zombies;
    private int spaceRadio;

    //variable auxiliares
    private int personNumber;
    private int zombieNumber;
    private int totalNumber;

    public Algorithm(String staticFile, String dynamicFile) {
        fileReader(staticFile, dynamicFile);
    }

    public void run(){

        double currentTime = 0;
        while(!endCondition(currentTime)){

            List<Particle> newPosition = new ArrayList<>();

            personNumber = 0;
            zombieNumber = 0;

            //recorro todas la particulas
            for ( Particle currentP : particles ) {

                Set<Particle> nearerZombies = new TreeSet<>(createComparator(currentP));  // zombies dentro del campo de vision de currentP
                Set<Particle> contactZombies = new TreeSet<>(createComparator(currentP)); // zombies que estan tocando a currentP
                Set<Particle> nearerHumans = new TreeSet<>(createComparator(currentP));   // humanos dentro del campo de vision de currentP
                Set<Particle> contactHumans = new TreeSet<>(createComparator(currentP));  // humanos que estan tocando a currentP

                //obtengo la particulas dentro de su campo visual y oredenas de mas cercanas a lejanas
                if(currentP.isZombie()){
                    getNearerParticles(currentP, Z_VISUAL_FIELD, nearerZombies, contactZombies, nearerHumans, contactHumans);
                }else{
                    getNearerParticles(currentP, H_VISUAL_FIELD, nearerZombies, contactZombies, nearerHumans, contactHumans);
                }

                //creo una nueva particula con los parmetro con paso temporal despues
                Particle newP = currentP.next(nearerZombies, contactZombies, nearerHumans, contactHumans, dt);


                //metricas de la corrida actual
                if(newP.isZombie())
                    zombieNumber++;
                else
                    personNumber++;

                //lo guardo en el nuevo espacio
                newPosition.add(newP);

                //todo:crear funcion que pasa este vector al archivo de salida
            }

            //todo: ver si esto funciona bien
            particles = newPosition;
        }
    }

    private void getNearerParticles(Particle currentP, double visualField, Set<Particle> nearerZombies,
                                    Set<Particle> contactZombies, Set<Particle> nearerHumans, Set<Particle> contactHumans){
        for ( Particle p: particles ) {
            if(!currentP.equals(p) && currentP.getDistanceTo(p) <= visualField){
                if(currentP.getDistanceTo(p) <= currentP.getRadio()) {
                    //se estan tocando
                    if(p.isZombie()) {
                        contactZombies.add(p);
                    }else{
                        contactHumans.add(p);
                    }
                }else{
                    //esta en el campo visual y no se están tocando
                    if(p.isZombie()) {
                        nearerZombies.add(p);
                    }else{
                        nearerHumans.add(p);
                    }
                }
            }
        }
    }

    //ordena de las que estan mas cerca de p a mas lejos
    private static Comparator<Particle> createComparator(Particle p) {
        final Particle finalP = new Particle(p);
        return new Comparator<Particle>() {
            @Override
            public int compare(Particle p0, Particle p1) {
                double ds0 = p0.getDistanceTo(finalP);
                double ds1 = p1.getDistanceTo(finalP);
                return Double.compare(ds0, ds1);
            }
        };
    }

    private boolean endCondition(double currentTime){
        if(personNumber == 0 || currentTime >= MAX_SIMULATION_TIME){
            return true;
        }
        return false;
    }

    private void fileReader(String staticFile, String dynamicFile){
        //todo:hacer
        //mientras tanto:
        spaceRadio = 11;
        personNumber = 10;
        zombieNumber = 1;
        totalNumber =11;
        particles = new ArrayList<>();
        //zombies = new ArrayList<>();
    }


}
