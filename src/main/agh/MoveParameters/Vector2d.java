package agh.MoveParameters;

public class Vector2d {

    public final int x, y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return String.format("(%s, %s)", x, y);
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other){
        if(other==null) return this;
        return new Vector2d(Math.max(x, other.x), Math.max(y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        if(other==null) return this;
        return new Vector2d(Math.min(x, other.x), Math.min(y, other.y));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d substract(Vector2d other){
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Vector2d)) return false;

        Vector2d v = (Vector2d)other;
        return x == v.x && y == v.y;
    }

    public Vector2d oposite(){
        return new Vector2d( - this.x, - this.y);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}
