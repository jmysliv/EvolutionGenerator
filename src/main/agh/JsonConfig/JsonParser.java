package agh.JsonConfig;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
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
