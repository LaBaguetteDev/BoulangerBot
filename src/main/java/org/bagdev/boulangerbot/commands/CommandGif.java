package org.bagdev.boulangerbot.commands;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bagdev.boulangerbot.utils.StringUtils;
import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.event.message.MessageCreateEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class CommandGif implements CommandExecutor {

    final String API_KEY = "LL9VVSCXSZ2M";
    String url = "";

    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) throws ExecutionException, InterruptedException {

        if(event.getMessageContent().length() >= 5) {
            String search = event.getMessageContent().substring(5);

            getSearchResult(search);

            event.getChannel().sendMessage(url);
        } else if (event.getMessageContent().substring(4).isBlank() || event.getMessageContent().substring(4).isEmpty()){
            getSearchResult("baguette");
            event.getChannel().sendMessage(url);
        } else {
            getSearchResult("baguette");
            event.getChannel().sendMessage(url);
        }
    }

    private void getSearchResult(String searchTerm) {
        final String urlStr = String.format("https://g.tenor.com/v1/search?q=%1$s&key=%2$s&limit=%3$s",
                searchTerm, API_KEY, 30);

        try {
            URL url = new URL(urlStr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder bufferContent = new StringBuilder();

            while((inputLine = br.readLine()) != null) {
                bufferContent.append(inputLine);
            }
            br.close();

            Gson gson = new Gson();
            JsonObject returnObj = gson.fromJson(String.valueOf(bufferContent), JsonObject.class);
            JsonArray nestedArray = returnObj.get("results").getAsJsonArray();

            Random rd = new Random();
            JsonObject json = nestedArray.get(rd.nextInt(30)).getAsJsonObject();

            this.url = json.get("itemurl").getAsString();

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
