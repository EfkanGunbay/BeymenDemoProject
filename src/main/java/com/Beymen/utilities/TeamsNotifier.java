package com.Beymen.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamsNotifier {
    public static int totalFailed = 0;

    public static List<String> getCucumberReportStatuses() throws FileNotFoundException {
        JsonParser parser = new JsonParser();
        JsonArray originalJSONArray = parser.parse(new FileReader("target/cucumber.json")).getAsJsonArray();
        JsonArray jsonArray = removeDuplicateScenarios(originalJSONArray);

        int scenarioCount = 0;
        int passedScenarioCount = 0;
        int totalSteps = 0;
        int totalPassed = 0;
        int totalSkipped = 0;
        int failedScenarioCount = 0;

        try {
            for (JsonElement element : jsonArray) {
                JsonObject lines = element.getAsJsonObject();
                JsonArray elements = lines.getAsJsonArray("elements");

                for (JsonElement el : elements) {
                    JsonObject scenarios = el.getAsJsonObject();
                    String elementType = scenarios.get("type").getAsString();

                    if ("scenario".equals(elementType)) {
                        scenarioCount++;

                        JsonArray stepResults = scenarios.getAsJsonArray("steps");
                        boolean isScenarioPassed = true;

                        for (JsonElement step : stepResults) {
                            JsonObject result = step.getAsJsonObject().getAsJsonObject("result");
                            String status = result.get("status").getAsString();
                            totalSteps += 1;
                            if ("passed".equals(status)) {
                                totalPassed++;
                            } else if ("failed".equals(status)) {
                                totalFailed++;
                                isScenarioPassed = false;
                            } else if ("skipped".equals(status)) {
                                totalSkipped++;
                                isScenarioPassed = false;
                            }
                        }

                        if (isScenarioPassed) {
                            passedScenarioCount++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        failedScenarioCount = scenarioCount - passedScenarioCount;

        List<String> reportElements = new ArrayList<>();
        reportElements.add("Automation Test Report: ");
        reportElements.add("" + scenarioCount);
        reportElements.add("" + passedScenarioCount);
        reportElements.add("" + totalSteps);
        reportElements.add("" + totalPassed);
        reportElements.add("" + failedScenarioCount);
        reportElements.add("" + totalSkipped);
        return reportElements;
    }

    public static JsonArray removeDuplicateScenarios(JsonArray jsonArray) {
        JsonArray cleanedArray = new JsonArray();
        Set<String> processedScenarios = new HashSet<>();

        for (JsonElement element : jsonArray) {
            JsonObject feature = element.getAsJsonObject();
            JsonArray elements = feature.getAsJsonArray("elements");
            JsonArray cleanedElements = new JsonArray();

            for (JsonElement el : elements) {
                JsonObject scenario = el.getAsJsonObject();
                String scenarioName = scenario.get("name").getAsString();

                if (!processedScenarios.contains(scenarioName)) {
                    cleanedElements.add(el);
                    processedScenarios.add(scenarioName);
                }
            }

            feature.add("elements", cleanedElements);
            cleanedArray.add(feature);
        }

        return cleanedArray;
    }

    private static final String WEBHOOK_URL = "";

    public void sendReport() throws Exception {
        String report = createReport();
        URL url = new URL(WEBHOOK_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonPayload = "{\"text\": \"" + report + "\"}";

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode);
    }

    private String createReport() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        String sendTime = currentDate.format(formatter);

        List<String> statuses = null;
        try {
            statuses = getCucumberReportStatuses();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return "<strong>Phrase Test Results</strong>" + " \n\n" + sendTime + "\n\n" +
                "Total Scenarios: " + statuses.get(1) + "\n\n\n" +
                "\u2705 Passed Scenarios: " + statuses.get(2) + "\n\n" +
                "\u274C Failed Scenarios: " + statuses.get(5) + "\n\n" +
                "\uD83C\uDF00 Passed Steps: " + statuses.get(4) + "\n\n" +
                "\uD83C\uDF00 Skipped Steps: " + statuses.get(6) + "\n\n" +
                "Report Link: " + "https://reports.cucumber.io/report-collections/93b7b7a0-8227-45de-bf43-9caef1f103bb";
    }
}
