package agh.World;

import agh.MapElements.Animal;
import agh.MapElements.Grass;
import agh.MoveParameters.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public class Map implements IWorldMap {
    private Vector2d leftCorner = new Vector2d(0, 0);
    private Vector2d rightCorner;
    private int moveCost;
    private HashMap<Vector2d, List<Animal>>  animals= new HashMap<>();
    private HashMap<Vector2d, Grass>  grasses= new HashMap<>();

    public Map(int width, int height, int moveCost){
        this.moveCost = moveCost;
        rightCorner = new Vector2d(width, height);
    }

    public String toString(){
        MapVisualizer map = new MapVisualizer(this);
        return map.draw(leftCorner, rightCorner);
    }
    public Vector2d getSize(){
        return new Vector2d(rightCorner.x, rightCorner.y);
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(leftCorner) && position.precedes(rightCorner);
    }

    @Override
    public boolean place(Animal animal) {
        if(canMoveTo(animal.getPosition())){
            if(!animals.containsKey(animal.getPosition())){
                animals.put(animal.getPosition(),new ArrayList<>());
            }
            animals.get(animal.getPosition()).add(animal);
            return true;
        }
        return false;
    }

    public boolean plantGrass(Grass grass){
        if(this.isOccupied(grass.getPosition())) return false;
        grasses.put(grass.getPosition(), grass);
        return true;
    }

    @Override
    public void run() {
        List<Animal> animalsToRun = animals.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
        Random rand = new Random();
        animalsToRun.forEach(element->element.turn());
        animalsToRun.forEach(element ->element.move(moveCost));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(animals.get(position) != null) return true;
        return grasses.get(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(animals.get(position) != null){
            return animals.get(position).get(0);
        }
        return grasses.get(position);
    }

    public int numberOfAnimals(){
        List<Animal> animalsToCount = animals.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
        return animalsToCount.size();
    }
    public int numberOfGrasses(){
       return grasses.size();
    }

    public int[] genePrportion(){
        int[] geneQuantity = new int[8];
        for(int i=0; i<8; i++) geneQuantity[i] = 0;
        List<Animal> animalsToGenesCount = animals.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
        animalsToGenesCount.forEach(element->{
            for(int i=0; i<8; i++){
                geneQuantity[i] += element.getAnimalGene().getGeneQuantity()[i];
            }
        });
        return geneQuantity;
    }

    public void positionChanged(Vector2d oldPosition, Animal animal){
        animals.get(oldPosition).remove(animal);
        if(animals.get(oldPosition).isEmpty()) animals.remove(oldPosition);
        this.place(animal);
    }

    public void feedAnimal(){
        List<Grass> grassesToEat = this.grasses.values().stream().collect(Collectors.toList());
        grassesToEat.forEach(element->{
            if(this.animals.containsKey(element.getPosition())){
                this.animals.get(element.getPosition()).sort(Comparator.comparing(Animal::getEnergy));
                this.animals.get(element.getPosition()).get(0).eatGrass(element);
                this.grasses.remove(element.getPosition());
            }
        });
    }

    public void removeDeadAnimal(){
        List<Animal> animalsToDie = animals.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
        animalsToDie.forEach(element->{
            if(element.getEnergy()<=0 && animals.containsKey(element.getPosition())){
                this.animals.get(element.getPosition()).remove(element);
                if(this.animals.get(element.getPosition()).isEmpty()) this.animals.remove(element.getPosition());
            }
        });
    }

    public void reproduce(){
        List<Animal> newBornAnimals = new ArrayList<>();
        this.animals.forEach((key, element) ->{
            if(element.size()>1){
                element.sort(Comparator.comparing(Animal::getEnergy));
                if(element.get(0).isAbleToReproduce() && element.get(1).isAbleToReproduce()){
                    Animal child = new Animal(element.get(0), element.get(1), this);
                    newBornAnimals.add(child);
                }
            }
        });
        newBornAnimals.forEach(element ->{
            place(element);
        });
    }
}
