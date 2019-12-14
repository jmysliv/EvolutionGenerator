package agh.MapElements;

import java.util.Arrays;
import java.util.Random;

public class Genes {
    private int[] genesTable = new int[32];
    private int[] geneQuantity = new int[8];

    public Genes(){
        for(int i=0; i<8; i++){
            geneQuantity[i] = 0;
        }
        createRandomGenes();
        Arrays.sort(genesTable);
    }

    public Genes(Genes motherGene, Genes fatherGene){
        for(int i=0; i<8; i++){
            geneQuantity[i] = 0;
        }
        createGenesFromOthers(motherGene, fatherGene);
        Arrays.sort(genesTable);
    }

    public void createRandomGenes(){
        Random rand = new Random();
        for(int i=0; i<32; i++){
            genesTable[i] = rand.nextInt(8);
            geneQuantity[genesTable[i]] ++;
        }
        while(!isBalanced());
    }

    public String toString(){
        String result = "";
        for(int i=0; i<32; i++){
            result = result + genesTable[i] + ", ";
        }
        for (int i : geneQuantity) {
            System.out.println(i);
        }
        return result;
    }

    public void createGenesFromOthers(Genes motherGene, Genes fatherGene){
        Random rand = new Random();
        int x = rand.nextInt(30);
        int y = x + 1 + rand.nextInt(30 - x);
        for(int i=0; i<=x; i++){
            this.genesTable[i] = motherGene.getGenesTable()[i];
            this.geneQuantity[genesTable[i]] ++;
        }
        for(int i=x+1; i<=y; i++){
            this.genesTable[i] = fatherGene.getGenesTable()[i];
            this.geneQuantity[genesTable[i]] ++;
        }
        for(int i=y+1; i<=31; i++){
            this.genesTable[i] = motherGene.getGenesTable()[i];
            this.geneQuantity[genesTable[i]] ++;
        }
        while(!isBalanced());
    }

    public boolean isBalanced(){
        for (int i=0; i<8; i++) {
            if(geneQuantity[i]==0){
                Random rand = new Random();
                int x = rand.nextInt(32);
                geneQuantity[genesTable[x]] --;
                geneQuantity[i]++;
                genesTable[x] = i;
                return false;
            }
        }
        return true;
    }

    public int chooseOne(){
        Random rand = new Random();
        int x = rand.nextInt(32);
        return genesTable[x];
    }

    public int[] getGenesTable() {
        return genesTable;
    }

    public int[] getGeneQuantity(){return geneQuantity;}

    public int getDominantGene(){
        int max = 0;
        for(int i=1; i<8; i++){
            if(geneQuantity[i] > geneQuantity[max]) max = i;
        }
        return max;
    }
}
