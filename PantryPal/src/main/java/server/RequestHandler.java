package main.java.server;


import com.mongodb.internal.logging.LogMessage.Entry;
import com.sun.glass.ui.SystemClipboard;
import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.file.*;

import org.bson.Document;

public class RequestHandler implements HttpHandler {
    private final database db;

    public RequestHandler(database db) {
        this.db = db;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String response = "Request received";
        String method = exchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(exchange);
            } else if (method.equals("POST")) {
                response = handlePost(exchange);
            } else if (method.equals("PUT")) {
                response = handlePut(exchange);
            } else if (method.equals("DELETE")) {
                response = handleDelete(exchange);
            } else {
                throw new Exception("Not Valid Request Method");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream out = exchange.getResponseBody();
        out.write(response.getBytes());
        out.close();
    }

    private String handleGet(HttpExchange exchange) throws IOException {
        String response = "";
        URI uri = exchange.getRequestURI();
        String fulluri = uri.toString();
        String query = uri.getRawQuery();
        if (query != null) {
            System.out.println("Query: " + uri);
            String user = fulluri.substring(1, fulluri.indexOf("=")-2);
            String value = query.substring(query.indexOf("=") + 1);
            value = value.replaceAll("%20", " ");
            System.out.println("Getting for User: " + user);
            if (value.equals("ALL")) {
                Iterator<Document> itr = db.getAll(user);
                while (itr.hasNext()) {
                    response += itr.next().getString("title") + "|";
                    System.out.println("Response is: " + response);
                }
            } else {
                recipe out = db.getRecipe(user, value);
                if (out == null) {
                    response = "";
                } else {
                    response = out.getMealType() + "$" +  out.getDetails(); // + "$" + out.getImageURL();
                }
            }
        }
        return (response.equals("")) ? "Invalid GET Request" : response;
    }

    private String handlePost(HttpExchange exchange) throws IOException {
        String user = getPostUser(exchange);
        System.out.println("Handling post request");
        System.out.println(exchange.getRequestHeaders());
        String response = "Invalid POST Request";
        File output = processMultipart(exchange);
        if(output.getName().contains("ingredients")){
            //Invoke GPT-3
            response = getGPT(user);
            recipeHandler handler = new recipeHandler(user);
            handler.addToDB(user);
            //Return recipe name
        }else{
            response = "Meal time received";
        }
        return response;
    }

    private String handlePut(HttpExchange exchange) {
        String response = "Invalid PUT Request";
        InputStream in = exchange.getRequestBody();
        Scanner scanner = new Scanner(in);
        String user = scanner.nextLine();
        String title = scanner.nextLine();
        String line = "";
        String details = "";
        boolean isIngredients = false, isInstructions = false;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            details += line + "\n";
        }
        scanner.close();
        db.editRecipe(user, title, details);
        response = "Recipe: " + title +  " edited";
        return response;
    }

    private String handleDelete(HttpExchange exchange) {
        String response = "Invalid DELETE Request";
        URI uri = exchange.getRequestURI();
        String fulluri = uri.toString();
        String query = uri.getRawQuery();
        String user = fulluri.substring(1, fulluri.indexOf("=") - 2);
        String title = query.substring(query.indexOf("=") + 1);
        title = title.replaceAll("%20", " ");
        boolean success = db.deleteRecipe(user, title);
        if (success) {
            response = "Recipe: " + title + " deleted";
        }else{
            response = "Recipe: " + title + " not found";
        }
        return response;
    }

    /*
     * Code inspired from
     * https://stackoverflow.com/questions/37869483/transfer-audio-file-from-client-
     * to-http-server-via-urlconnection
     */
    private File processMultipart(HttpExchange exchange) throws IOException {
        String CRLF = "\r\n";
        int fileSize = 0;
        String fileName = "";
        File output = null;
        boolean created = false;

        InputStream in = exchange.getRequestBody();
        String nextLine = "";
        do{
            nextLine = readLine(in);
            if(nextLine.contains("filename")){
                fileName = nextLine.substring(nextLine.indexOf("filename") + "filename".length() + 2, nextLine.length() - 1);
                output = new File("src/main/java/server/" + fileName);
                // Clear the existing file
                if (output.exists()) {
                    output.delete();
                }
                output.createNewFile();
                created = true;
            }
            if (nextLine.startsWith("Content-Length:")) {
                fileSize = Integer.parseInt(nextLine.replaceAll(" ", "").substring("Content-Length:".length()));
                System.out.println(fileSize);
            }
        }while(!nextLine.equals(""));

        System.out.println(fileName);

        byte[] wavFileBytes = new byte[fileSize];
        int readOffset = 0;
        while (readOffset < fileSize) {
            int bytesRead = in.read(wavFileBytes, readOffset, fileSize);
            readOffset += bytesRead;
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output));
        bos.write(wavFileBytes);
        bos.flush();
        return output;
    }

    /*
     * Code inspired from
     * https://stackoverflow.com/questions/37869483/transfer-audio-file-from-client-
     * to-http-server-via-urlconnection
     */
    private String readLine(InputStream is) throws IOException {
        String CRLF = "\r\n";

        int off = 0, i = 0;
        byte[] separator = CRLF.getBytes("UTF-8");
        byte[] lineBytes = new byte[1024];
        while (is.available() > 0) {
            int nextByte = is.read();
            if (nextByte < -1) {
                throw new IOException(
                        "Reached end of stream while reading the current line!");
            }

            lineBytes[i] = (byte) nextByte;
            if (lineBytes[i++] == separator[off++]) {
                if (off == separator.length) {
                    return new String(
                            lineBytes, 0, i - separator.length, "UTF-8");
                }
            } else {
                off = 0;
            }

            if (i == lineBytes.length) {
                throw new IOException("Maximum line length exceeded: " + i);
            }
        }

        throw new IOException(
                "Reached end of stream while reading the current line!");
    }

    private String getPostUser(HttpExchange exchange) throws IOException {
        InputStream in = exchange.getRequestBody();
        String CRLF = "\r\n";
        String user = "";
        String nextLine = "";
        do{
            nextLine = readLine(in);
            if(nextLine.contains("Username: ")){
                user = nextLine.substring(nextLine.indexOf("Username: ") + "Username: ".length(), nextLine.length());
                return user;
            }
        }while(!nextLine.equals(""));
        return null;
    }

    private String getGPT(String user){
        File meal = new File("src/main/java/server/mealTime.wav");
        File ingredients = new File("src/main/java/server/ingredients.wav");
        Whisper inputMeal = new Whisper();
        Whisper inputIngred = new Whisper();
        String transcribedIngred = "";
        String transcribedMeal = "";
        try {
            transcribedIngred = inputIngred.main(ingredients);
            transcribedMeal = inputMeal.main(meal);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // After acquiring transcripted inputs, concatenate and add necessary prompting
        // before sending to ChatGPT.
        String prompt = "Give me a" + transcribedMeal
                + "recipe given that strictly the ONLY ingredients I have are: " + transcribedIngred +
                "do not add any more ingredients";

        // send prompt/input to ChatGPT File: UNCOMMENT WHEN NO LONGER MOCKING
        // ChatGPT recipeMaker = new ChatGPT(prompt);
        ChatGPT recipeMaker = new ChatGPT(prompt, transcribedMeal);
        try {
            recipeMaker.main();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        List<String> lines = db.processFile(user, "src/main/java/recipe.txt");
        return lines.get(0);
    }
}
