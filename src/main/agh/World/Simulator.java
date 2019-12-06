package agh.World;

import agh.JsonConfig.JsonParser;
import agh.MapElements.Animal;

public class Simulator {

    public Simulator(){}

    public void simulate(int numberOfDays){
        JsonParser config = new JsonParser();
        Map map = new Map(config.getWidth(), config.getHeight(), config.getMoveEnergy());
        GrassPlanter grassPlanter = new GrassPlanter(map, config.getJungleRatio(), config.getPlantEnergy());
        this.createAnimal(config.getNumberOfAnimals(), map, config.getStartEnergy());
        for(int i=0; i<config.getNumberOfGrass(); i++) grassPlanter.plantGrass();
        for(int i=0; i<numberOfDays; i++) cycle(map, grassPlanter);
    }

    public void createAnimal(int number, Map map, int startEnergy){
        for(int i=0; i<number; i++){
            Animal animal = new Animal(map, startEnergy);
            map.place(animal);
        }
    }

    public void cycle(Map map, GrassPlanter grassPlanter){
        map.run();
        map.feedAnimal();
        map.reproduce();
        grassPlanter.plantGrass();
        map.removeDeadAnimal();
        System.out.println(map);
        System.out.println("animals: " + map.numberOfAnimals());
        System.out.println("grasses: " + map.numberOfGrasses());
    }


}
