package com.solncev.chat.view;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ChatBotView extends BaseView {

    private static final String WEATHER_API_KEY = "ec6ae61f58c44f36d3bd7d4f99c9993a";
    private static final String EXCHANGE_API_KEY = "02c622f203fe44bbb17a3c09fa686187";

    private AnchorPane pane;
    private TextArea conversation;
    private TextArea input;

    @Override
    public Parent getView() {
        if (pane == null) {
            createView();
        }
        return pane;
    }

    public void appendMessage(String message) {
        if (message != null) {
            conversation.appendText(message + "\n");
        }
    }

    private void createView() {
        pane = new AnchorPane();

        conversation = new TextArea();
        conversation.setEditable(false);
        conversation.setWrapText(true);

        AnchorPane.setLeftAnchor(conversation, 10.0);
        AnchorPane.setRightAnchor(conversation, 10.0);
        AnchorPane.setTopAnchor(conversation, 10.0);

        input = new TextArea();
        input.setMaxHeight(50);

        AnchorPane.setLeftAnchor(input, 10.0);
        AnchorPane.setRightAnchor(input, 10.0);
        AnchorPane.setBottomAnchor(input, 10.0);

        input.addEventHandler(KeyEvent.KEY_PRESSED, (EventHandler<KeyEvent>) keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String userInput = input.getText().trim();
                processCommand(userInput);
                input.clear();
                keyEvent.consume();
            }
        });
        pane.getChildren().addAll(input, conversation);
    }

    private void processCommand(String command) {
        if (command.equalsIgnoreCase("list")) {
            appendMessage("Available commands:\n- list: Show this list of commands\n- weather: Show weather in a specified city\n- exchange: Show currency exchange rate to RUB\n- quit: Close the application");
        } else if (command.toLowerCase().startsWith("weather")) {
            String[] parts = command.split(" ", 2);
            if (parts.length < 2) {
                appendMessage("Please specify a city. Example: weather London");
            } else {
                String city = parts[1];
                fetchWeather(city);
            }
        } else if (command.toLowerCase().startsWith("exchange")) {
            String[] parts = command.split(" ", 2);
            if (parts.length < 2) {
                appendMessage("Please specify a currency. Example: exchange USD");
            } else {
                String currency = parts[1];
                fetchExchangeRate(currency);
            }
        } else if (command.equalsIgnoreCase("quit")) {
            System.exit(0);
        } else {
            appendMessage("Unknown command. Type 'list' to see available commands.");
        }
    }

    private void fetchWeather(String city) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + WEATHER_API_KEY + "&units=metric";

        new Thread(() -> {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                    scanner.close();

                    parseAndDisplayWeather(response.toString(), city);
                } else {
                    appendMessage("Failed to fetch weather. Please check the city name and try again.");
                }
                connection.disconnect();
            } catch (IOException e) {
                appendMessage("Error fetching weather: " + e.getMessage());
            }
        }).start();
    }

    private void parseAndDisplayWeather(String response, String city) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject main = jsonResponse.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        String weatherDescription = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");

        appendMessage("Weather in " + city + ":\n" +
                "Temperature: " + temperature + "°C\n" +
                "Humidity: " + humidity + "%\n" +
                "Description: " + weatherDescription);
    }

    private void fetchExchangeRate(String currency) {
        String apiUrl = "https://openexchangerates.org/api/latest.json?app_id=" + EXCHANGE_API_KEY + "&symbols=RUB," + currency.toUpperCase();

        new Thread(() -> {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Scanner scanner = new Scanner(connection.getInputStream());
                    StringBuilder response = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        response.append(scanner.nextLine());
                    }
                    scanner.close();

                    parseAndDisplayExchangeRate(response.toString(), currency);
                } else {
                    appendMessage("Failed to fetch exchange rate. Please check the currency code and try again.");
                }
                connection.disconnect();
            } catch (IOException e) {
                appendMessage("Error fetching exchange rate: " + e.getMessage());
            }
        }).start();
    }

    private void parseAndDisplayExchangeRate(String response, String currency) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONObject rates = jsonResponse.getJSONObject("rates");
        if (rates.has(currency.toUpperCase()) && rates.has("RUB")) {
            double rate = rates.getDouble("RUB") / rates.getDouble(currency.toUpperCase());
            appendMessage("Exchange rate " + currency.toUpperCase() + " to RUB: " + rate);
        } else {
            appendMessage("Currency " + currency.toUpperCase() + " is not supported.");
        }
    }
}
