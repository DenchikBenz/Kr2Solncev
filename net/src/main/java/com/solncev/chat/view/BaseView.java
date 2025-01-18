package com.solncev.chat.view;
import com.solncev.chat.ChatApplication;
import com.solncev.chat.ChatBotApplication;
import javafx.scene.Parent;

public abstract class BaseView {

    private static ChatApplication application;
    private static ChatBotApplication chatBotApplication;
    public static ChatBotApplication getChatBotApplication() {
        if (chatBotApplication != null) {
            return chatBotApplication;
        }
        throw new RuntimeException("chatBotApplication is null");
    }
    public static void setChatBotApplication(ChatBotApplication application) {
        BaseView.chatBotApplication = application;
    }

    public static ChatApplication getApplication() {
        if (application != null) {
            return application;
        }
        throw new RuntimeException("application is null");
    }

    public static void setApplication(ChatApplication application) {
        BaseView.application = application;
    }


    public abstract Parent getView();
}
