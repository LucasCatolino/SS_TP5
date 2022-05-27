package core;

import models.Particle;
import models.Vector;

public class CPM {
    private static final double MAX_RADIO = 4;
    private static final double MIN_RADIO = 0;
    private static final double TOU = 0.5; //seg
    //private static final double BETA = 1;

    static public void noContact(Particle p, Vector target, double dt ){

        //update speed
        double newSpeed = p.getMaxV()*( (p.getRadio() - MIN_RADIO)/(MAX_RADIO - MIN_RADIO));

        //update Velocity
        Vector eTarget = Vector.sub(p.getPosition(), target).getVersor();
        p.setVelocity(Vector.multiply(eTarget, newSpeed));

        //update vector position
        p.getPosition().add(Vector.multiply(p.getVelocity(), dt));

        //update radio
        if(p.getRadio() < MAX_RADIO){
            p.setRadio(p.getRadio() + (MAX_RADIO /(TOU/dt)) );
        }

    }

    static public Vector getEscapeVerse(Particle p, Particle contactP){
        Vector toReturn = Vector.sub(p.getPosition(), contactP.getPosition());
        toReturn.opposite();
        return toReturn.getVersor();
    }

    //todo: contactP deberia ser una lisata
    static public void Contact(Particle p, Vector target, Particle contactP, double dt) {

        //get escape verse
        Vector escapeVerse = getEscapeVerse(p, contactP);

        //update speed
        double newSpeed = p.getMaxV();

        //update Velocity
        p.setVelocity(Vector.multiply(escapeVerse, newSpeed));

        //update vector position
        p.getPosition().add(Vector.multiply(p.getVelocity(), dt));

        ///update radio
        p.setRadio(MIN_RADIO);

    }






}
