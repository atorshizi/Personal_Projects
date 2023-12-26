package main.java.client;

import java.io.File;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
/**
 * Handles requests from the client and sends them to the server.
 * 
 */
public class Model {
    public String performRequest(String method, File mealTime, File ingredients, String postType, String recipeName, String details, String user){
        String response = "";
        try{
            String urlString = "http://localhost:8100/" + user + "/";
            if(recipeName != null){
                urlString += "?=" + recipeName;
                urlString = urlString.replaceAll(" ", "%20");
                System.out.println("Method : " + method + "\nURL: " + urlString);
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
            connection.setRequestMethod(method); 
            connection.setDoOutput(true);
            if(method.equals("POST")){
                
                //Send mealtime.wav to server
                String boundary = Long.toHexString(System.currentTimeMillis());
                String CRLF = "\r\n";

                //Set up request headers
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true);
                writer.append("--" + boundary).append(CRLF);
                writer.append("Username: " + user).append(CRLF);
                if(postType.equals("mealTime")){
                    writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + mealTime.getName() + "\"").append(CRLF);
                    writer.append("Content-Length: " + mealTime.length()).append(CRLF);
                    writer.append("Content-Type: " + connection.guessContentTypeFromName(mealTime.getName())).append(CRLF);
                    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                    writer.append(CRLF).flush();
                    Files.copy(mealTime.toPath(), output);
                    output.flush();
                }else if(postType.equals("ingredients")){
                    writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + ingredients.getName() + "\"").append(CRLF);
                    writer.append("Content-Length: " + ingredients.length()).append(CRLF);
                    writer.append("Content-Type: " + connection.guessContentTypeFromName(ingredients.getName())).append(CRLF);
                    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                    writer.append(CRLF).flush();
                    Files.copy(ingredients.toPath(), output);
                    output.flush();
                }else{
                    throw new Exception("Not Valid Post Type");
                }
            }else if (method.equals("GET")) {
                System.out.println(urlString);
            }else if(method.equals("PUT")){
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(user + "\n" + recipeName + "\n" + details);
                writer.close();
            } else if (method.equals("DELETE")){
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(recipeName + "\n" + details); 
                writer.close(); 
            }
            else{
                throw new Exception("Not Valid Request Method");
            }
            response = processResponse(connection);
            System.out.println("Received: " + response);
            return response;
        }catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }

    public String performLoginRequest(String method, String username, String password){
        String response = "";
        try{
            String urlString = "http://localhost:8100/login/";
            if(username != null){
                urlString += "?=" + username + "%20" + password;
                urlString = urlString.replaceAll(" ", "%20");
                System.out.println("Method : " + method + "\nURL: " + urlString);
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
            connection.setRequestMethod(method); 
            connection.setDoOutput(true);
            if(method.equals("POST")){
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(username + "\n" + password);
                writer.close();
            }else if (method.equals("GET")) {
                System.out.println(urlString);
            }else if(method.equals("PUT")){
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(username + "\n" + password);
                writer.close();
            } else if (method.equals("DELETE")){
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(username + "\n" + password); 
                writer.close(); 
            }
            else{
                throw new Exception("Not Valid Request Method");
            }
            response = processResponse(connection);
            System.out.println("Received: " + response);
            return response;
        }catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }

    private String processResponse(HttpURLConnection connection) throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        String response = "";
        while ((line = reader.readLine()) != null) {
            response += line + "\n";
        }
        reader.close();
        return response;
    }

    public void saveUser(String username, String password){
        try{
            PrintWriter writer = new PrintWriter("src/main/java/client/user.dat", "UTF-8");
            writer.println(username);
            writer.println(password);
            writer.close();
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }

    public String shareRequest(String username, String recipeName){
        try{
            String urlString = "http://localhost:8100/share/" + username + "/";
            if(recipeName != null){
                urlString += "?=" + recipeName;
                urlString = urlString.replaceAll(" ", "%20");
                System.out.println("URL: " + urlString);
            }
            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
            connection.setRequestMethod("GET"); 
            connection.setDoOutput(true);
            String response = processResponse(connection);
            System.out.println("Received: " + response);
            if(response.contains("Invalid")){
                return "Invalid Request";
            }else{
            return urlString;
            }
        }catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }
}
