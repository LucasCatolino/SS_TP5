package core;

import models.Particle;
import models.Vector;

import java.util.Set;
import java.util.TreeSet;

public class CPM {
    private static final double MAX_RADIO = 0.32;
    private static final double MIN_RADIO = 0.15;
    private static final double TOU = 0.5; //seg
    //private static final double BETA = 1;

    //todo: contactP deberia ser una lisata
    static public void apply(Particle p, Vector target, double dt, TreeSet<Particle> contactP){


        if(checkPosition(p) <= 0){
            //get escape verse
            Vector escapeVerse = getCenterEscape(p);

            //update speed
            double newSpeed = p.getMaxV();

            //update Velocity
            p.setVelocity(Vector.multiply(escapeVerse, newSpeed));

            //update vector position
            p.getPosition().add(Vector.multiply(p.getVelocity(), dt));

            ///update radio
            p.setRadio(MIN_RADIO);
        }

        if(!contactP.isEmpty()){
            //get escape verse
            Vector escapeVerse = getEscapeVerse(p, contactP.first()); //todo:cambiar para que acepte mas de uno

            //update speed
            double newSpeed = p.getMaxV();

            //update Velocity
            p.setVelocity(Vector.multiply(escapeVerse, newSpeed));

            //update vector position
            p.getPosition().add(Vector.multiply(p.getVelocity(), dt));

            ///update radio
            p.setRadio(MIN_RADIO);

        }else {
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
            if(p.getRadio() > MAX_RADIO){
                p.setRadio(MAX_RADIO);
            }
        }
    }

    static private Vector getEscapeVerse(Particle p, Particle contactP){
        Vector toReturn = Vector.sub(p.getPosition(), contactP.getPosition());
        toReturn.opposite();
        return toReturn.getVersor();
    }

    //retorna 1 si esta dentro de la circunferencia, 0 si esta en el borde, -1 si esta afuera
    static private int checkPosition(Particle p){
        double distance = Math.sqrt(Math.pow(p.getPosition().getX() - p.getSpaceRadio(), 2) + Math.pow(p.getPosition().getY() - p.getSpaceRadio(), 2));
        if(distance + p.getRadio() >  p.getSpaceRadio()){
            return -1;
        }else if(distance + p.getRadio() ==  p.getSpaceRadio()){
            return 0;
        }
        return  1;
    }


    static private Vector getCenterEscape(Particle p){
        Vector aux = new Vector(p.getSpaceRadio(), p.getSpaceRadio());
        Vector toReturn = Vector.sub(p.getPosition(), aux);
        toReturn.opposite();
        return toReturn.getVersor();

    }












}
