package agh.MapElements;

import agh.MoveParameters.Vector2d;

public class Grass {
    private Vector2d position;
    private int energy;

    public Grass(Vector2d position, int energy){
        this.position = position;
        this.energy = energy;
    }

    public String toString(){
        return "*";
    }

    public Vector2d getPosition(){
        return new Vector2d(this.position.x, this.position.y);
    }

    public int getEnergy(){
        return this.energy;
    }
}
