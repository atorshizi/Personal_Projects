package main.java.server;

import com.sun.net.httpserver.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Server {
    private static final int SERVER_PORT = 8100;
    private static final String SERVER_HOSTNAME = "localhost";
    private static database db; 
    private HttpServer server; 
    ThreadPoolExecutor threadPoolExecutor; 

    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        db = new database();

        // create a server
        HttpServer server = HttpServer.create(
                new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
                0);

        // TODO: create the context
        // TODO: set the executor
        // TODO: start the server

        server.createContext("/", new RequestHandler(db));
        server.createContext("/login/", new LoginHandler(db));
        server.createContext("/share/", new ShareHandler(db));
        server.setExecutor(threadPoolExecutor);
        server.start();

        System.out.println("Server started on port " + SERVER_PORT); 
    } 
    public void startServer(String[] args) throws IOException { 
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        db = new database();

        // create a server
        this.server = HttpServer.create( 
                new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT),
                0); 
        server.createContext("/", new RequestHandler(db));
        server.createContext("/login/", new LoginHandler(db));
        server.setExecutor(threadPoolExecutor);
        server.start(); 
        System.out.println("Server started on port " + SERVER_PORT); 
    } 
    public void endServer(String[] args) throws IOException { 
        this.server.stop(0);
        this.threadPoolExecutor.shutdownNow(); 
    } 

}
