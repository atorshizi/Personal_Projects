package main.java.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.sun.net.httpserver.*;

import org.bson.Document;


public class LoginHandler implements HttpHandler{
    private database db;
    public LoginHandler(database db){
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

    private String handleGet(HttpExchange exchange) {
        String response = "Invalid";
        URI uri = exchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            String pair = query.split("=")[1];
            System.out.println(pair);
            String username = pair.split("%20")[0];
            String password = pair.split("%20")[1];
            response = db.validUser(username, password) ? "Valid" : "Invalid";
        }
        return response;
    }

    private String handlePost(HttpExchange exchange) {
        String response = "Invalid";
        URI uri = exchange.getRequestURI();
        InputStream in = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(in));
        try{
            String username = reader.readLine();
            String password = reader.readLine();
            String query = uri.getRawQuery();
            if (query != null) {
                System.out.println("this is " + username + " " + password);
                if (db.containsUser(username)) {
                    response = "User already exists";
                }else{
                    response = db.addUser(username, password) ? username + " Added" : "Failed to add user";
                }
            }
            return response;
        }catch(Exception e){
            System.out.println("Error: " + e);
            return "Error: " + e;
        }
    }

    private String handlePut(HttpExchange exchange) {
        String response = "Invalid";
        URI uri = exchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            //TODO: implement
        }
        return response;
    }

    private String handleDelete(HttpExchange exchange) {
        String response = "Invalid";
        URI uri = exchange.getRequestURI();
        String query = uri.getRawQuery();
        if (query != null) {
            //TODO: implement
        }
        return response;
    }
}
