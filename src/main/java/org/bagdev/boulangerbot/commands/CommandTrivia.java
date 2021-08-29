package org.bagdev.boulangerbot.commands;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bagdev.boulangerbot.utils.StringUtils;
import org.bagdev.boulangerbot.utils.commands.Command;
import org.bagdev.boulangerbot.utils.commands.CommandExecutor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CommandTrivia implements CommandExecutor {

    private String category;
    private String question;
    private String correctAnswer;
    private String difficulty;
    private String reponse = "";

    private int secondPassed = 0;


    @Override
    public void run(MessageCreateEvent event, Command command, String[] args) throws ExecutionException, InterruptedException {

        if(secondPassed != 0) { // Vérifie qu'une question n'est pas déjà en cours
            event.getChannel().sendMessage("Une question est déjà en cours fdp");
        } else {
            makeTriviaRequest(); // Récupère les données de la question

            EmbedBuilder embed = createEmbed(30); // Initialise l'embed

            decompte(event, embed); // Démarre le timer

            //event.getChannel().sendMessage(embed);
            //event.getMessage().addReaction("\uD83D\uDC4D");

            event.getApi().addMessageCreateListener(replyEvent -> {
                reponse = replyEvent.getMessageContent(); // Récupère le contenu du message avec la réponse

                if (reponse.equalsIgnoreCase("Trivial : " + getCorrectAnswer())) {
                    event.getChannel().sendMessage("Bien joué bg");
                } else if (reponse.equalsIgnoreCase("Trivial : " + getIncorrectAnswer())) {
                    event.getChannel().sendMessage("T'es nul, la bonne réponse était " + getCorrectAnswer());
                }

            }).removeAfter(30, TimeUnit.SECONDS);
        }
    }

    private void decompte(MessageCreateEvent event, EmbedBuilder embed) {
        event.getChannel().sendMessage(embed).thenAccept(msg -> {
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    if(secondPassed >= 30) { // Si le nombre de seconde passée est à 30sec
                        timer.cancel(); // Ferme le timer
                        secondPassed = 0; // Réinitialise le nombre de secondes
                    } else {
                        secondPassed++; // Incrémente le nombre de secondes
                        EmbedBuilder newEmbed = createEmbed(30 - secondPassed); // Crée un nouvel embed avec le nouveau temps restant
                        msg.edit(newEmbed); // Modifie le message avec l'embed
                    }
                }
            }, 1000, 1000); // Répète la méthode toutes les 1000ms (1sec)
        });

    }

    private EmbedBuilder createEmbed(int second) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Question")
                .setDescription(getQuestion())
                .addField("Catégorie", getCategory())
                .addField("Difficulté", getDifficulty())
                .addField("Comment répondre ?", "Ecrire ``Trivial : `` suivis de ``True`` ou de ``False``")
                .addField("Temps restant", second+"sec");

        if(getDifficulty().equals("Facile")) {
            embed.setColor(Color.GREEN);
        } else if (getDifficulty().equals("Moyen")) {
            embed.setColor(Color.ORANGE);
        } else {
            embed.setColor(Color.RED);
        }

        return embed;
    }

    private void makeTriviaRequest() {
        try {
            URL url = new URL("https://opentdb.com/api.php?amount=1&type=boolean");

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

            for(Object triviaObj : nestedArray) {
                JsonObject currentTriviaObj = (JsonObject) triviaObj;

                this.category = StringUtils.unescapeHTML(currentTriviaObj.get("category").getAsString());

                this.question = StringUtils.unescapeHTML(currentTriviaObj.get("question").getAsString());

                this.correctAnswer = currentTriviaObj.get("correct_answer").getAsString();

                this.difficulty = translateDifficulty(currentTriviaObj.get("difficulty").getAsString());
            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String translateDifficulty(String txt) {
        if(txt.equals("easy")) {
            return "Facile";
        } else if (txt.equals("medium")) {
            return "Moyen";
        } else {
            return "Difficile";
        }
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getIncorrectAnswer() {
        String lowerCase = this.correctAnswer.toLowerCase();
        boolean value = Boolean.parseBoolean(lowerCase);
        return Boolean.toString(!value);
    }
}
