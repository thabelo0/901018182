package com.example.demo4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        String[] imagePaths = {
                "file:/C:/Users/Administrator/IdeaProjects/demo4/target/Statue_of_God.png",
                "file:/C:/Users/Administrator/IdeaProjects/demo4/target/dbXcFfXqn4d68danptipjF-1200-80.jpg",
                "file:/C:/Users/Administrator/IdeaProjects/demo4/target/art1.jpg",
                "file:/C:/Users/Administrator/IdeaProjects/demo4/target/Comp 1_00016.jpg",
                "file:/C:/Users/Administrator/IdeaProjects/demo4/target/GBDKbX0W4AAv--O.jpg"};

        String[] imageNames = {"Statue of God", "Nature", "Art", "Logos", "Mountains"};

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color:#003a47");

        Label header = new Label("Gallery");
        header.setStyle("-fx-font-size: 35px; -fx-font-style: Monospace; -fx-text-fill: white; -fx-padding: 20 0 10 0;");
        header.setAlignment(Pos.CENTER);
        root.setTop(header);
        BorderPane.setAlignment(header, Pos.CENTER);

        TextField searchField = new TextField();
        searchField.setPromptText("Search");
        searchField.setMaxWidth(300);
        HBox searchBox = new HBox(searchField);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(0, 0, 20, 0));

        GridPane grid = new GridPane();
        grid.setHgap(100);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(searchBox, grid);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setSpacing(10);
        centerBox.setPadding(new Insets(0, 20, 20, 20));
        root.setCenter(centerBox);

        Scene mainScene = new Scene(root,1380,700);
        stage.setScene(mainScene);
        stage.setTitle("Media Gallery");
        stage.show();

        Runnable populateGrid = () -> {
            grid.getChildren().clear();
            String filter = searchField.getText().toLowerCase();
            int cols = 2, imageWidth = 400, imageHeight = 200;
            int index = 0;
            for (int i = 0; i < imagePaths.length; i++) {
                if (imageNames[i].toLowerCase().contains(filter)) {
                    Image img = new Image(imagePaths[i], imageWidth, imageHeight, true, true);
                    ImageView thumb = new ImageView(img);

                    thumb.setEffect(new DropShadow(8, Color.BLACK));
                    thumb.setOnMouseEntered(e -> {
                        thumb.setScaleX(1.1);
                        thumb.setScaleY(1.1);
                        thumb.setEffect(new DropShadow(15, Color.WHITE));
                    });
                    thumb.setOnMouseExited(e -> {
                        thumb.setScaleX(1.0);
                        thumb.setScaleY(1.0);
                        thumb.setEffect(new DropShadow(8, Color.BLACK));
                    });

                    Label nameLabel = new Label(imageNames[i]);
                    nameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                    nameLabel.setAlignment(Pos.CENTER);

                    VBox vbox = new VBox(thumb, nameLabel);
                    vbox.setAlignment(Pos.CENTER);
                    vbox.setSpacing(5);

                    int col = index % cols;
                    int row = index / cols;
                    grid.add(vbox, col, row);
                    index++;

                    int finalI = i;
                    vbox.setOnMouseClicked(event -> {
                        BorderPane detailRoot = new BorderPane();
                        detailRoot.setStyle("-fx-background-color:#003a47");

                        ImageView largeImage = new ImageView(new Image(imagePaths[finalI], 800, 600, true, true));
                        largeImage.setPreserveRatio(true);
                        largeImage.setSmooth(true);
                        detailRoot.setCenter(largeImage);

                        Label title = new Label(imageNames[finalI]);
                        title.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
                        BorderPane.setAlignment(title, Pos.CENTER);
                        detailRoot.setTop(title);
                        BorderPane.setMargin(title, new Insets(20, 0, 0, 0));

                        Button back = new Button("Back");
                        back.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-padding: 8 16 8 16; -fx-font-size: 18px; -fx-background-radius: 50;");
                        back.setOnAction(e -> stage.setScene(mainScene));

                        HBox bottomBox = new HBox(back);
                        bottomBox.setAlignment(Pos.CENTER);
                        bottomBox.setPadding(new Insets(20));
                        detailRoot.setBottom(bottomBox);

                        Scene detailScene = new Scene(detailRoot, 1400, 700);
                        stage.setScene(detailScene);
                    });
                }}};

        populateGrid.run();
        searchField.textProperty().addListener((obs, oldVal, newVal) -> populateGrid.run());
    }

    public static void main(String[] args) {
        launch();
    }
}