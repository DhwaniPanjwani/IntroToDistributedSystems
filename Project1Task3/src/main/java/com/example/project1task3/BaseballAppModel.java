package com.example.project1task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Gson used for handling json from API
//Reference: ChatGPT: Prompt - example code to learn to use Gson for handling JSON
import com.google.gson.*;
//Jsoup used for webscraping
//Reference: ChatGPT: Prompt - example code to learn to use Jsoup for web scraping
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document.OutputSettings;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/* This is the business model for the Baseball App which has the logic of all the web-scraping
happening in the app. It contains the several functions which are called by the Servlet.
 */
public class BaseballAppModel {
    //function to scrape the TeamStats from https://www.cbssports.com/mlb/news/2023-mlb-playoff-picture-baseball-standings-postseason-projections-tiebreakers-magic-numbers/
    public String scrapeMLBPTeamStats(String leagueName) throws IOException {
        String result = null;
        Document doc = Jsoup.connect("https://www.cbssports.com/mlb/news/2023-mlb-playoff-picture-baseball-standings-postseason-projections-tiebreakers-magic-numbers/").get();


        // Create an HTML StringBuilder to build the table
        StringBuilder tableHtml = new StringBuilder();
        /*Syntax Reference: ChatGPT Prompt: How to build a table html from web scraped data
         which can be directly output to the jsp

         */

        String finalHtml = null;

        //for scraping the 3 tables for every league
        Element table = null;
        Element table1 = null;
        Element table2 = null;

        System.out.println(leagueName);

        //for web scraping the image logos
        /*
        Website 1: https://www.cbssports.com/mlb/standings/
        Website 2: "https://news.sportslogos.net/2013/05/16/mlb-updates-both-al-and-nl-league-logos/baseball/"
         */

        //functions to fetch the logos
        String logoURL = fetchLeagueLogo(leagueName);
        String logoURL2 = fetchLeagueLogo2(leagueName);


        //output the respective tables - based on the league name AL or NL
        if (leagueName.equalsIgnoreCase("AL")) {
            table = doc.select("table").get(0);
            table1 = doc.select("table").get(1);
            table2 = doc.select("table").get(2);

        } else if (leagueName.equalsIgnoreCase("NL")) {
            table = doc.select("table").get(3);
            table1 = doc.select("table").get(4);
            table2 = doc.select("table").get(5);

            // handling the edge case of no results found
        } else {
            result = "not found";
        }
        //appending the tables to the tableHtml StringBuilder
        tableHtml.append("<table>");
        //Looping through the tables of the website and fetching the rows
        Elements rows = table.select("tr");
        for (Element row : rows) {
            Elements cells = row.select("td,th");
            tableHtml.append("<tr>");
            for (Element cell : cells) {
                tableHtml.append("<td>").append(cell.text()).append("</td>");
            }
            tableHtml.append("</tr>");
        }
        tableHtml.append("<p></p>");
        Elements rows1 = table1.select("tr");
        for (Element row : rows1) {
            Elements cells = row.select("td,th");
            tableHtml.append("<tr>");
            for (Element cell : cells) {
                tableHtml.append("<td>").append(cell.text()).append("</td>");
            }
            tableHtml.append("</tr>");
        }
        tableHtml.append("<p></p>");

        Elements rows2 = table2.select("tr");
        for (Element row : rows2) {
            Elements cells = row.select("td,th");
            tableHtml.append("<tr>");
            for (Element cell : cells) {
                tableHtml.append("<td>").append(cell.text()).append("</td>");
            }
            tableHtml.append("</tr>");
        }
        tableHtml.append("</table>");

        // Generate the final HTML page with the table
        finalHtml = "<html><head></head><body> <img src= \" " + logoURL +" \" width=\"200\" height=\"200\"</img> <img src= \"  "+ logoURL2 +" \" width=\"200\" height=\"200\"</img>" + tableHtml.toString() +" </body></html>";
        //pretty printing the table and returning the html to the servlet
        Document finalDoc = Jsoup.parse(finalHtml);
        OutputSettings settings = new OutputSettings();
        settings.prettyPrint(true);
        finalDoc.outputSettings(settings);
        return finalDoc.html();
    }

    //function to fetch the first logo
    //Reference: https://www.w3schools.com/html/html_images.asp
    private String fetchLeagueLogo(String leagueName) throws IOException {
        Document doc_cbs = Jsoup.connect("https://www.cbssports.com/mlb/standings/").get();
        //Fetching all the image tags
        Elements images_cbs = doc_cbs.select("img");
        Element image_cbs = null;
        //Looping through all the images and finding the one which conatins the league name in the URL
        for(Element i:images_cbs)
        {
            String a = i.attr("data-lazy");
            if(a.contains(leagueName))
            {
                image_cbs = i;
                //once found, ending the loop
                break;
            }

        }


        //returning the image URL
        return image_cbs.attr("data-lazy");
    }
    //function to fetch the second logo
    //Reference: https://www.w3schools.com/html/html_images.asp
    private String fetchLeagueLogo2(String leagueName) throws IOException {

        Document doc_mlb = Jsoup.connect("https://news.sportslogos.net/2013/05/16/mlb-updates-both-al-and-nl-league-logos/baseball/").get();
        //Fetching all the image tags
        Elements images_mlb = doc_mlb.select("img");

        Element image_mlb = null;
        //Looping through all the images and finding the one which conatins the league name in the URL
        for(Element i:images_mlb)
        {
            String a = i.attr("src");
            //finding the image which contains the correct substring
            if(leagueName.equalsIgnoreCase("AL"))
            {
                if(a.contains(leagueName) && !(a.contains("NL")))
                {
                    image_mlb = i;
                    break;
                }
            }
            else if(leagueName.equalsIgnoreCase("NL"))
            {
                if(a.contains(leagueName) && !(a.contains("AL")))
                {
                    image_mlb = i;
                    //once found, ending the loop
                    break;
                }
            }


        }


        //returning the image URL
        return image_mlb.attr("src");
    }

    //function to scrape the player statistics from https://legacy.baseballprospectus.com/pitchfx/leaderboards/
    //Reference: ChatGPT Prompt: Example code to scrape table data and form a result string from the row using Jsoup
    public String scrapeMLBPlayerStats(String player) throws IOException {
        Document doc = Jsoup.connect("https://legacy.baseballprospectus.com/pitchfx/leaderboards/").get();

        // Initialize a variable to store the result stats
        String resultStats = "";

        // Find the table containing player stats (you may need to inspect the HTML to find the correct table)
        Element table = doc.select("table").get(0);

        // Check if the table exists
        if (table != null) {
            // Iterate through rows in the table (skip the header row)
            Elements rows = table.select("tr");

            for (int i = 3; i < rows.size(); i++) {
                Element row = rows.get(i);

                // Extract the player name from the second cell (assuming it's the second column)
                String playerName = row.select("td").get(1).text(); // Use .get(1) to access the second <td> element

                // Check if the player name matches the desired player
                if (playerName.equalsIgnoreCase(player)) {
                    // Extract the entire row as stats
                    resultStats = row.text();

                    break; // Exit the loop once the player's row is found
                }
            }
        } else {
            //handling the case of no results found
            resultStats = "Table not found on the webpage.";
        }
        //returning the stats to the servlet
        return resultStats;

    }

    //Function for the API call
    /*
    This API finds the news for all players from a particular start date to an end date.
    Here, given any player input, it iterates through the JSON and fetches the news related to
    that particular player
     */
    //API: http://lookup-service-prod.mlb.com/
    //Function Reference: ChatGPT prompt- using JSON Array in Gson to iterate over JSON fields
    public String getNewsData(String playerName) throws IOException {
        StringBuilder response = null;
        String note=null;
        // Build the URL with URI parameters
        URL url = new URL("http://lookup-service-prod.mlb.com/json/named.transaction_all.bam?sport_code='mlb'&start_date='20171201'&end_date='20171231'");
        // Create a HttpURLConnection and set the request method to GET
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Set request headers (if needed)
        connection.setRequestProperty("Content-Type", "application/json");

        // Get the response code
        int responseCode = connection.getResponseCode();

        // Check if the request was successful (HTTP status 200)
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Create a BufferedReader to read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuilder();
            String line;

            // Read the response line by line
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Close the reader and the connection
            reader.close();
            connection.disconnect();

            //creating the Gson library object
            Gson gson = new Gson();

            //preprocessing to clean up the Json
            JsonObject transaction = gson.fromJson(response.toString(), JsonObject.class);
            JsonObject queryResults = gson.fromJson(transaction.get("transaction_all").toString(), JsonObject.class);
            JsonObject query = gson.fromJson(queryResults.get("queryResults").toString(), JsonObject.class);
            JsonArray rowResults = query.get("row").getAsJsonArray();

            //iterating over the JsonArray to fetch the player's name and the news
            for (int i = 0; i < rowResults.size(); i++) {
                String player = String.valueOf(rowResults.get(i).getAsJsonObject().get("player"));
                player = player.replace("\"", "");

                //matching the input player with the player in the JSON
                if (player.equalsIgnoreCase(playerName)) {
                    note = String.valueOf((rowResults.get(i).getAsJsonObject().get("note")));
                    note = note.replace("\"", "");

                }

            }


        }

            //returning the note to the servlet
            return note;
        }
    }
