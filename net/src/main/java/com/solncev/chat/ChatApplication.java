package com.solncev.chat;

import com.solncev.chat.client.ChatClient;
import com.solncev.chat.model.UserConfig;
import com.solncev.chat.view.BaseView;
import com.solncev.chat.view.ChatView;
import com.solncev.chat.view.UserConfigView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatApplication extends Application {

    private UserConfig userConfig;
    private UserConfigView userConfigView;
    private ChatView chatView;
    private BorderPane root;
    private ChatClient chatClient;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        BaseView.setApplication(this);

        userConfigView = new UserConfigView();
        chatView = new ChatView();
        chatClient = new ChatClient(this);
        root = new BorderPane();

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        setView(userConfigView);
    }

    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    public void startChat() {
        chatClient.start();
    }

    public UserConfigView getUserConfigView() {
        return userConfigView;
    }

    public ChatView getChatView() {
        return chatView;
    }

    public void setView(BaseView view) {
        root.setCenter(view.getView());
    }

    public UserConfig getUserConfig() {
        return userConfig;
    }

    public void appendMessage(String message) {
        chatView.appendMessage(message);
    }

    public ChatClient getChatClient() {
        return chatClient;
    }
}