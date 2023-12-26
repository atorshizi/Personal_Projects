package test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

import main.java.server.IWhisper;
import main.java.server.Whisper;

public class MockWhisper extends Whisper implements IWhisper{

    @Override
    public String connect(File args) {
        return "Give me a Lunch recipe that I can make with Chicken, rice, and beans.";
    }

    @Override
    public String main(File args) throws IOException, URISyntaxException, JSONException {
        return "";
    }
}
