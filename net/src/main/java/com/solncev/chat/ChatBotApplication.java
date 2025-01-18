package com.solncev.chat;
import com.solncev.chat.view.BaseView;
import com.solncev.chat.view.ChatBotView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatBotApplication extends Application {

    private ChatBotView chatBotView;
    private BorderPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ChatBot");
        primaryStage.setOnCloseRequest(e -> System.exit(0));

        BaseView.setChatBotApplication(this);

        chatBotView = new ChatBotView();
        root = new BorderPane();

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        setView(chatBotView);
    }


    public void setView(BaseView view) {
        root.setCenter(view.getView());
    }
}
