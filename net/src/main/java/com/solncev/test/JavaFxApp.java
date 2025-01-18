package com.solncev.test;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class JavaFxApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene main = new Scene(root, 600, 600);
        stage.setTitle("This is my first javafx app");
        stage.setScene(main);

        Line line = new Line();
        line.setStartX(100);
        line.setStartY(100);
        line.setEndX(200);
        line.setEndY(200);

        Text text = new Text();
        text.setText("Hello World");
        text.setX(300);
        text.setY(300);

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Form");
        GridPane grid = new GridPane();
        ButtonType connect = new ButtonType("Connect", ButtonBar.ButtonData.OK_DONE);
        TextField username = new TextField();
        username.setPromptText("Username");
        grid.add(username, 1, 0);

        dialog.getDialogPane().getButtonTypes().addAll(connect);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(button -> {
                    if (connect.equals(button)) {
                        return username.getText();
                    }
                    return null;
                }
        );

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(System.out::println);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText("Content");

        Button click = new Button("Click");
        click.setOnAction(event -> alert.show());

        ObservableList children = root.getChildren();
        children.addAll(line, text, click);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
