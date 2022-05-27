package models;

import java.util.Objects;
public class Vector {
    private double x;
    private double y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector( Vector v) {
        x = v.x;
        y = v.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistanceTo(Vector v){
        return getDistanceTo(v.x, v.y);
    }

    public double getDistanceTo(double x, double y){
        return Math.sqrt(Math.pow((this.x - x),2) + Math.pow((this.y - y),2));
    }

    public Vector getVersor(){
        return new Vector(getX()/getModule(), getY()/getModule());
    }

    public double getModule(){
        return Math.sqrt(Math.pow((x),2) + Math.pow((y),2));
    }

    public void add(Vector v){
        this.x += v.x;
        this.y += v.y;
    }

    public void multi(double e){
        x *= e;
        y *= e;
    }

    public Vector sub(Vector v){
        return new Vector(x - v.x, y - v.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x+"\t"+y;
    }
}
