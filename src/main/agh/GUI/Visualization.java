package agh.GUI;

import agh.JsonConfig.JsonParser;
import agh.MapElements.Animal;
import agh.MapElements.Grass;
import agh.MoveParameters.Vector2d;
import agh.World.GrassPlanter;
import agh.World.Map;
import agh.World.Simulator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class Visualization extends Application {
    private Pane root = new Pane();
    final int WINDOW_SIZE = 150;
    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss:SSSSS");
    private ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                    new PieChart.Data("0", 0),
                    new PieChart.Data("1"  , 0),
                    new PieChart.Data("2" , 0),
                    new PieChart.Data("3" , 0),
                    new PieChart.Data("4" , 0),
                    new PieChart.Data("5" , 0),
                    new PieChart.Data("6" , 0),
                    new PieChart.Data("7" , 0));
    @Override
    public void start(Stage stage) {

       Simulator simulator = new Simulator();
       long animationTimeStep = 1_000_000;
       long chartTimeStep = 200_000_000;

        //Creating a scene object
        Scene scene = new Scene(root, 10*simulator.getMap().getSize().x, 10*simulator.getMap().getSize().y);
        //Pie chart
        final PieChart geneQuantity = new PieChart(pieChartData);
        Scene scene3 = new Scene(geneQuantity, 600, 600);

        //line chart
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time/s");
        xAxis.setAnimated(false);
        yAxis.setLabel("Number of elements");
        yAxis.setAnimated(false);

        //creating the line chart with two axis created above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);



        //defining a series to display data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series.setName("Animal number");
        series2.setName("Grass number");

        // add series to chart
        lineChart.getData().add(series);
        lineChart.getData().add(series2);
        Scene scene2 = new Scene(lineChart, 1500, 800);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0 ;
            private long lastUpdate2 = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= animationTimeStep) {
                    update(simulator);
                    lastUpdate = now ;
                }
                if(now-lastUpdate2 >= chartTimeStep){
                   lastUpdate2 = now;
                    series2.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), simulator.getMap().numberOfGrasses()));
                    series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), simulator.getMap().numberOfAnimals()));

                    if (series.getData().size() > WINDOW_SIZE)
                        series.getData().remove(0);
                    if (series2.getData().size() > WINDOW_SIZE)
                        series2.getData().remove(0);
                    updatePieChart(simulator);
                }

            }
        };

        timer.start();

        //Adding scenes to the stages
        stage.setScene(scene);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        Stage stage3 = new Stage();
        stage3.setScene(scene3);

        //Displaying the contents of the stages
        stage.show();
        stage2.show();
        stage3.show();
    }

    private void update( Simulator simulator) {
        root.getChildren().removeIf(n -> {
            return true;
        });
        simulator.cycle();
        draw(simulator.getMap());
    }

   public void draw(Map map){

       for(int i=0; i<=map.getSize().x; i++){
           for(int j=0; j<=map.getSize().y; j++){
               Vector2d tmp = new Vector2d(i, j);
               if(map.isOccupied(tmp)){
                   if(map.objectAt(tmp) instanceof Animal){
                       Rectangle rectangle = new Rectangle(10, 10);
                       if(((Animal) map.objectAt(tmp)).getEnergy() > ((Animal) map.objectAt(tmp)).getStartEnergy()) {
                           rectangle.setFill(Color.BLUE);
                       } else  if(((Animal) map.objectAt(tmp)).getEnergy() > ((Animal) map.objectAt(tmp)).getStartEnergy() / 2) {
                           rectangle.setFill(Color.LIGHTBLUE);
                       }else  if(((Animal) map.objectAt(tmp)).getEnergy() > ((Animal) map.objectAt(tmp)).getStartEnergy() / 3) {
                           rectangle.setFill(Color.PINK);
                       } else {
                           rectangle.setFill(Color.RED);
                       }
                       rectangle.setX(10*i);
                       rectangle.setY(10*j);
                       root.getChildren().add(rectangle);
                   }
                   else if(map.objectAt(tmp) instanceof Grass){
                       Rectangle rectangle = new Rectangle(10, 10, Color.GREEN);
                       rectangle.setX(10*i);
                       rectangle.setY(10*j);
                       root.getChildren().add(rectangle);
                   }
               }
           }
       }
   }

   public void updatePieChart( Simulator simulator){
        int[] genes = simulator.getMap().genePrportion();
        for(int i=0; i<8; i++){
            for(PieChart.Data d : pieChartData)
            {
                if(d.getName().equals(Integer.toString(i)))
                {
                    d.setPieValue(genes[i]);
                }
            }
        }

   }


    public static void main(String args[]){
        launch(args);
    }
}