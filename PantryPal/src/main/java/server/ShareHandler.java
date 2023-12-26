package main.java.server;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ShareHandler implements HttpHandler{
    private database db;
    
    public ShareHandler(database db) {
        this.db = db;
    }

    public void handle(HttpExchange exchange) throws IOException{
        String response = "Request received";
        String method = exchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                response = handleGet(exchange);
            }else {
                response = "Invalid Request Method";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            response = "Error: " + e.getMessage();
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream out = exchange.getResponseBody();
        out.write(response.getBytes());
        out.close();
    }

    private String handleGet(HttpExchange exchange) {
        String response = "Invalid GET request";
        URI uri = exchange.getRequestURI();
        String fulluri = uri.toString();
        String query = uri.getRawQuery();
        String name = "";
        String user = "";
        if (query != null) {
            user = fulluri.substring(7, fulluri.indexOf("=")-2);
            name = query.substring(query.indexOf("=") + 1);
            name = name.replaceAll("%20", " ");
        }else{
            return "Invalid Request";
        }
        System.out.println("Sharing User: " + user);
        System.out.println("Name: " + name);
        recipe r = db.getRecipe(user, name);

        /* *
        String localImage = name+"image.png";
        File imageFile = new File(localImage);
        ;

        if(!imageFile.exists()){
            return "image not found";
        }
        String imagePath = "file://"+imageFile.getAbsolutePath().replace(" ", "%20");
        */
    
        if (r == null) {
            return "Invalid Recipe Name";
        }
        StringBuilder htmlBuilder = new StringBuilder();
        //Build html response
        //TODO: Implement image support
        htmlBuilder.append("<html>")
            .append("<body>")
            .append("<h1>").append(r.getName()).append("</h1>")
            //Image Here
            .append("<img src=\"").append(r.getImageURL()).append("\" alt=\"Recipe Image\"/>")
            .append("<h2>").append("Details").append("</h2>")
            .append("<p>").append(r.getDetails().replaceAll("\n","<br\\>")).append("</p>")
            .append("</body>")
            .append("</html>");

        response = htmlBuilder.toString();
        return response;
    }
}
