package agh.World;

import agh.JsonConfig.JsonParser;
import agh.MapElements.Animal;

public class Simulator {
    private Map map;
    private GrassPlanter grassPlanter;

    public Simulator(){
        JsonParser config = new JsonParser();
        this.map = new Map(config.getWidth(), config.getHeight(), config.getMoveEnergy());
        this.grassPlanter = new GrassPlanter(map, config.getJungleRatio(), config.getPlantEnergy());
        this.createAnimal(config.getNumberOfAnimals(), map, config.getStartEnergy());
        for(int i=0; i<config.getNumberOfGrass(); i++) grassPlanter.plantGrass();
    }

    public Map getMap(){
        return this.map;
    }

    public void simulate(int numberOfDays){
        for(int i=0; i<numberOfDays; i++) cycle();
    }

    public void createAnimal(int number, Map map, int startEnergy){
        for(int i=0; i<number; i++){
            Animal animal = new Animal(map, startEnergy);
            map.place(animal);
        }
    }

    public void cycle(){
        map.run();
        map.feedAnimal();
        map.reproduce();
        grassPlanter.plantGrass();
        map.removeDeadAnimal();
    }


}
