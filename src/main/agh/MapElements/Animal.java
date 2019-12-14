package agh.MapElements;


import agh.MoveParameters.MapDirection;
import agh.MoveParameters.Vector2d;
import agh.World.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal{
    private Vector2d position;
    private MapDirection direction;
    private int energy;
    private Genes animalGene;
    private Map map;
    private int startEnergy;
    private int age;
    private int childs;
    private int scion;
    private int deathTime;
    private List<Animal> ancestors = new ArrayList<>();


    public Animal(Map map, int startEnergy){
        this.map = map;
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
        Random rand = new Random();
        int x = rand.nextInt(map.getSize().x);
        int y = rand.nextInt(map.getSize().y);
        this.direction = MapDirection.NORTH;
        this.position = new Vector2d(x, y);
        this.animalGene = new Genes();
        this.age = 0;
        this.childs = 0;
        this.scion = 0;
    }

    public Animal(Animal mother, Animal father, Map map){
        this.map = map;
        this.energy = mother.getEnergy()/4 + father.getEnergy()/4;
        mother.reproduce(); father.reproduce();
        this.direction = MapDirection.NORTH;
        this.animalGene = new Genes(mother.getAnimalGene(), father.getAnimalGene());
        this.startEnergy = mother.getStartEnergy();
        this.position = mother.getPosition();
        this.turn();
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        Vector2d size = map.getSize();
        newPosition = new Vector2d((newPosition.x + size.x + 1)%(size.x + 1), (newPosition.y + 1 + size.y)%(size.y + 1));
        this.position = newPosition;
        this.age = 0;
        this.childs = 0;
        this.scion = 0;
        this.ancestors.addAll(father.getAncestors());
        this.ancestors.add(father);
        boolean flag = true;
        for(Animal animal : ancestors) {
            if(animal.equals(mother)) {
                flag = false;
                break;
            }
        }
        if(flag) ancestors.add(mother);
        for(Animal motherAncestor: mother.getAncestors()){
            flag = true;
            for( Animal animal : ancestors){
                if(motherAncestor.equals(animal)){
                    flag = false;
                    break;
                }
            }
            if(flag) ancestors.add(motherAncestor);
        }
        this.ancestors.forEach(ancestor ->{
            ancestor.addNewScion();
        });
    }

    public int getAge(){
        return this.age;
    }

    public int getChilds() {
        return childs;
    }

    public boolean isAbleToReproduce(){
        return this.energy >= this.startEnergy/2;
    }

    public void reproduce(){
        this.energy -= (int) this.energy/4;
        this.childs ++;
    }


    public void addNewScion(){
        this.scion ++;
    }


    public String toLongString(){
        return String.format("Position: %s; Direction: %s", this.position.toString(), this.direction.toString());
    }

    public String toString(){
        return this.direction.toString();
    }


    public MapDirection getDirection(){
        return this.direction;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public Genes getAnimalGene() {
        return animalGene;
    }

    public int getStartEnergy(){
        return this.startEnergy;
    }

    public void turn(){
        for(int i=0; i<this.animalGene.chooseOne(); i++){
            this.direction = this.direction.next();
        }
    }

    public void move(int moveCost){
        Vector2d newPosition = this.position.add(this.direction.toUnitVector());
        Vector2d size = map.getSize();
        newPosition = new Vector2d((newPosition.x + size.x + 1)%(size.x + 1), (newPosition.y + 1 + size.y)%(size.y + 1));
        this.energy -= moveCost;
        this.age ++;
        Vector2d oldPosition = this.position;
        this.position = newPosition;
        map.positionChanged(oldPosition, this);
    }

    public int getEnergy(){
        return this.energy;
    }

    public void eatGrass(Grass grass){
        this.energy += grass.getEnergy();
    }


    public List<Animal> getAncestors() {
        return ancestors;
    }

    public int getScion() {
        return scion;
    }

    public int getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(int deathTime) {
        this.deathTime = deathTime;
    }
}
