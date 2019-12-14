package agh.GUI;

import agh.MapElements.Animal;
import agh.MapElements.Grass;
import agh.MoveParameters.Vector2d;
import agh.World.Map;
import agh.World.Simulator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;


public class Visualization extends Application {
    private FlowPane root = new FlowPane();
    private Pane mapChart = new Pane();
    private final int WINDOW_SIZE = 80;
    private long animationTimeStep = 1_000_000;
    private long chartTimeStep = 400_000_000;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ss:SSSSS");
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
    private boolean stopSimulation = false;

    @Override
    public void start(Stage stage) {

       Simulator simulator = new Simulator();


        //Pie chart
        final PieChart geneQuantity = new PieChart(pieChartData);

        //button
        VBox vb = new VBox();
        vb.setPadding(new Insets(10, 50, 50, 50));
        vb.setSpacing(10);
        Button button = new Button("Stop Simulation");
        vb.getChildren().add(button);
        button.setOnAction(value ->  {
            this.stopSimulation = !this.stopSimulation;
        });
        //line charts - energy, age, childs
        final LineChart<String, Number> Energy = this.setUp("time", "Energy");
        Energy.setMaxWidth(300);
        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Average Energy");
        Energy.getData().add(series3);
        final  LineChart<String, Number> Age = this.setUp("time", "age");
        Age.setMaxWidth(300);
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
        series4.setName("Average Age");
        Age.getData().add(series4);
        final  LineChart<String, Number> Childs = this.setUp("time", "Number of Childs");
        Childs.setMaxWidth(300);
        XYChart.Series<String, Number> series5 = new XYChart.Series<>();
        series5.setName("Average number of childs");
        Childs.getData().add(series5);

        //line chart - grass and animals
        final LineChart<String, Number> lineChart = this.setUp("time", "number of elements");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series.setName("Animal number");
        series2.setName("Grass number");
        // add series to chart
        lineChart.getData().add(series);
        lineChart.getData().add(series2);

        root.getChildren().addAll( geneQuantity, lineChart, mapChart, Energy, Age, Childs, vb);
        Scene scene2 = new Scene(root, 1500, 900);

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0 ;
            private long lastUpdate2 = 0;
            @Override
            public void handle(long now) {
                if(!stopSimulation){
                    if (now - lastUpdate >= animationTimeStep) {
                        update(simulator);
                        lastUpdate = now ;
                    }
                    if(now-lastUpdate2 >= chartTimeStep){
                        lastUpdate2 = now;
                        series2.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), simulator.getMap().numberOfGrasses()));
                        series.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), simulator.getMap().numberOfAnimals()));
                        series3.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), simulator.getMap().averageEnergy()));
                        series4.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), simulator.getMap().averageAge()));
                        series5.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), simulator.getMap().averageChildsNumber()));

                        if (series.getData().size() > WINDOW_SIZE)
                            series.getData().remove(0);
                        if (series2.getData().size() > WINDOW_SIZE)
                            series2.getData().remove(0);
                        if (series3.getData().size() > WINDOW_SIZE)
                            series3.getData().remove(0);
                        if (series4.getData().size() > WINDOW_SIZE)
                            series4.getData().remove(0);
                        if (series5.getData().size() > WINDOW_SIZE)
                            series5.getData().remove(0);
                        updatePieChart(simulator);
                    }
                }
            }
        };

        timer.start();

        //Adding scenes to the stages
        stage.setScene(scene2);
        stage.setTitle("map 1");


        //Displaying the contents of the stages
        stage.show();
        stage.setOnCloseRequest(e -> {
            stage.close();
            simulator.showAnimalStats();
        });

    }

    private LineChart setUp(String xName, String yName){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xName);
        yAxis.setLabel(yName);
        return  new LineChart<>(xAxis, yAxis);

    }

    private void update( Simulator simulator) {
        mapChart.getChildren().removeIf(n -> {
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
                       Rectangle rectangle = new Rectangle(5, 5);
                       if(((Animal) map.objectAt(tmp)).getEnergy() > ((Animal) map.objectAt(tmp)).getStartEnergy()) {
                           rectangle.setFill(Color.INDIGO);
                       } else  if(((Animal) map.objectAt(tmp)).getEnergy() > ((Animal) map.objectAt(tmp)).getStartEnergy() / 2) {
                           rectangle.setFill(Color.BLUE);
                       }else  if(((Animal) map.objectAt(tmp)).getEnergy() > ((Animal) map.objectAt(tmp)).getStartEnergy() / 3) {
                           rectangle.setFill(Color.YELLOW);
                       } else {
                           rectangle.setFill(Color.RED);
                       }
                       rectangle.setX(5*i);
                       rectangle.setY(5*j);
                       mapChart.getChildren().add(rectangle);
                   }
                   else if(map.objectAt(tmp) instanceof Grass){
                       Rectangle rectangle = new Rectangle(5, 5, Color.GREEN);
                       rectangle.setX(5*i);
                       rectangle.setY(5*j);
                       mapChart.getChildren().add(rectangle);
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