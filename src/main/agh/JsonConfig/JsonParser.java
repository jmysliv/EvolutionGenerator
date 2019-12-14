package agh.JsonConfig;

import agh.World.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class JsonParser {
    private int width, height, moveEnergy, plantEnergy, startEnergy, numberOfAnimals, numberOfGrass;
    private double jungleRatio;

    public JsonParser(){
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("parameters.json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            System.out.println(jsonObject);
            this.width = ((Number) jsonObject.get("width")).intValue();
            this.height = ((Number) jsonObject.get("height")).intValue();
            this.moveEnergy = ((Number) jsonObject.get("moveEnergy")).intValue();
            this.plantEnergy = ((Number) jsonObject.get("plantEnergy")).intValue();
            this.startEnergy = ((Number) jsonObject.get("startEnergy")).intValue();
            this.numberOfAnimals = ((Number) jsonObject.get("numberOfAnimals")).intValue();
            this.jungleRatio = ((Number) jsonObject.get("jungleRatio")).doubleValue();
            this.numberOfGrass = ((Number) jsonObject.get("numberOfGrassInJungle")).intValue();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void writeToJson(Map map){
        JSONObject mapStats = new JSONObject();
        mapStats.put("number of Animals: ", map.numberOfAnimals());
        mapStats.put("number of Grasses: ", map.numberOfGrasses());
        mapStats.put("Liczba genu 0: ", map.genePrportion()[0]);
        mapStats.put("Liczba genu 1: ", map.genePrportion()[1]);
        mapStats.put("Liczba genu 2: ", map.genePrportion()[2]);
        mapStats.put("Liczba genu 3: ", map.genePrportion()[3]);
        mapStats.put("Liczba genu 4: ", map.genePrportion()[4]);
        mapStats.put("Liczba genu 5: ", map.genePrportion()[5]);
        mapStats.put("Liczba genu 6: ", map.genePrportion()[6]);
        mapStats.put("Liczba genu 7: ", map.genePrportion()[7]);
        mapStats.put("średni poziom energi: ", map.averageEnergy());
        mapStats.put("średnia liczba dzieci: ", map.averageChildsNumber());
        mapStats.put("średni wiek: ", map.averageAge());


        //Write JSON file
        try (FileWriter file = new FileWriter("stats.json")) {

            file.write(mapStats.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMoveEnergy() {
        return moveEnergy;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getStartEnergy() {
        return startEnergy;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public int getNumberOfAnimals() {
        return numberOfAnimals;
    }

    public int getNumberOfGrass() {
        return numberOfGrass;
    }
}
