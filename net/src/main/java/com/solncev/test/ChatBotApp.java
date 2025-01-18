package com.solncev.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatBotApp extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        TextField inputField = new TextField();
        Button sendButton = new Button("Send");

        sendButton.setOnAction(event -> {
            String userInput = inputField.getText().trim();
            if (!userInput.isEmpty()) {
                chatArea.appendText("You: " + userInput + "\n");
                processCommand(userInput, chatArea);
                inputField.clear();
            }
        });

        root.getChildren().addAll(chatArea, inputField, sendButton);

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Chat Bot App");
        stage.setScene(scene);
        stage.show();
    }

    private void processCommand(String command, TextArea chatArea) {
        switch (command.toLowerCase()) {
            case "list":
                chatArea.appendText("Bot: Available commands: list, weather <city>, exchange <currency>, quit\n");
                break;
            case "quit":
                chatArea.appendText("Bot: Returning to the main page...\n");
                // Логика возврата на главную страницу
                break;
            default:
                chatArea.appendText("Bot: Unknown command. Type 'list' for available commands.\n");
                break;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

