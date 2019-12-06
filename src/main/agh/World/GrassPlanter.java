package agh.World;



import agh.MapElements.Grass;
import agh.MoveParameters.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassPlanter {
    private Map map;
    Vector2d jungleRightCorner, jungleLeftCorner;
    private int grassEnergy;

    public GrassPlanter(Map map, double jungleRatio, int grassEnergy){
        this.grassEnergy = grassEnergy;
        this.map = map;
        jungleLeftCorner = new Vector2d((int)((map.getSize().x/2) - (jungleRatio * map.getSize().x/2)), (int)((map.getSize().y/2) - (jungleRatio * map.getSize().y/2)) );
        jungleRightCorner = new Vector2d((int)((map.getSize().x/2) + (jungleRatio * map.getSize().x/2)), (int)((map.getSize().y/2) + (jungleRatio * map.getSize().y/2)) );
    }

    public void plantGrass(){
        List<Vector2d> jungle = new ArrayList<>();
        List<Vector2d> savanna = new ArrayList<>();
        for (int i=0; i<map.getSize().x; i++){
            for(int j=0; j<map.getSize().y; j++){
                Vector2d tmp = new Vector2d(i, j);
                if(!map.isOccupied(tmp)){
                    if(tmp.follows(jungleLeftCorner) && tmp.precedes(jungleRightCorner)){
                        jungle.add(tmp);
                    }else{
                        savanna.add(tmp);
                    }
                }
            }
        }
        Random rand = new Random();
        if(jungle.size()>0){
            int junglePosition = rand.nextInt(jungle.size());
            Grass jungleGrass = new Grass(jungle.get(junglePosition), this.grassEnergy);
            map.plantGrass(jungleGrass);
        }
       if(savanna.size()>0){
           int savannaPosition = rand.nextInt(savanna.size());
           Grass savannaGrass = new Grass(savanna.get(savannaPosition), this.grassEnergy);
           map.plantGrass(savannaGrass);
       }

    }
}
