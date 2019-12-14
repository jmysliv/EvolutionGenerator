package agh.World;

import agh.JsonConfig.JsonParser;
import agh.MapElements.Animal;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Simulator {
    private Map map;
    private GrassPlanter grassPlanter;
    private int day;
    private JsonParser config;

    public Simulator(){
        this.config = new JsonParser();
        this.map = new Map(config.getWidth(), config.getHeight(), config.getMoveEnergy());
        this.grassPlanter = new GrassPlanter(map, config.getJungleRatio(), config.getPlantEnergy());
        this.createAnimal(config.getNumberOfAnimals(), map, config.getStartEnergy());
        for(int i=0; i<config.getNumberOfGrass(); i++) grassPlanter.plantGrass();
        this.day = 0;
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
        this.day ++;
        map.run();
        map.feedAnimal();
        map.reproduce();
        grassPlanter.plantGrass();
        map.removeDeadAnimal(day);
    }

    public void showAnimalStats(){
        Scanner scan = new Scanner(System.in);
        List<Animal> history = this.map.getHistory(this.day);
        int max = 0;
        for(int i=1; i<8; i++){
            if(this.map.genePrportion()[i] > this.map.genePrportion()[max]) max = i;
        }
        final int dominant = max;
        List<Animal> dominantGene = history.stream().filter(animal -> animal.getAnimalGene().getDominantGene() == dominant).collect(Collectors.toList());
        while(true){
            System.out.println("Poznaj historie zwierząt! Liczba symulowanych dni: " + this.day + " \n" +
                    "Wybierz numer zwierzącia: 0 - " + history.size()
                    + "\nWybierz g by wyświetlic wszytski z dominującym genem\n" +
                    "Wybierz s by zapisać statystyki do pliku i zakończyć\n" +
                    "Wybierz q by zakończyc");
            String choice = scan.nextLine();
            switch (choice){
                case "g":
                    dominantGene.forEach(animal -> {
                        System.out.println("Data urodzin: " + (animal.getDeathTime() - animal.getAge()) + " Wiek: " + animal.getAge() + " Data śmierci: " + animal.getDeathTime()
                                + "\nLiczba dzieci: " + animal.getChilds() + " liczba potomków: " + animal.getScion() + "\nGenom: " + animal.getAnimalGene().toString());
                    });
                    System.out.println("Dominujący gen: " + dominant + " Liczba zwierzat: " + dominantGene.size());
                    break;
                case "q":
                    return;
                case "s":
                    config.writeToJson(this.map);
                    return;
                default:
                    int i = Integer.parseInt(choice);
                    if(i>=0 && i<history.size()){
                        Animal animal = history.get(i);
                        System.out.println( i + " Data urodzin: " + (animal.getDeathTime() - animal.getAge()) + " Wiek: " + animal.getAge() + " Data śmierci: " + animal.getDeathTime()
                                + "\nLiczba dzieci: " + animal.getChilds() + " liczba potomków: " + animal.getScion() + "\nGenom: " + animal.getAnimalGene().toString());
                    }
                    break;
            }
        }


    }


}
