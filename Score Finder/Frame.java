import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Frame {
    private int frameNum;
    private LinkedList<Integer> scores = new LinkedList<>();

    // one attempt - must be strike
    Frame(int frame, int attemptOne) throws IOException{
        if ((attemptOne != 10)){
            throw new IOException();
        }
        if (frame == 10 && attemptOne == 10){
            throw new IOException();
        }
        this.frameNum = frame;
        scores.add(attemptOne); 
    }
    // two attempts - can be open or spare
    Frame (int frame, int attemptOne, int attemptTwo) throws IOException{
        if ((attemptOne == 10) || (attemptOne + attemptTwo > 10)){
            throw new IOException();
        }
        if ((frame == 10) && (attemptOne + attemptTwo == 10)){
            throw new IOException();
        }
        this.frameNum = frame;
        scores.add(attemptOne);
        scores.add(attemptTwo);
    }
    // three attempts - must be spare on 10th frame
    Frame (int frame, int attemptOne, int attemptTwo, int attemptThree) throws IOException{
        if ((frame != 10) || (attemptTwo > 10) || (attemptThree > 10)){
            throw new IOException();
        }
        if (attemptOne != 10 && (attemptOne + attemptTwo != 10)){
            throw new IOException();
        }
        this.frameNum = frame;
        scores.add(attemptOne);
        scores.add(attemptTwo);
        scores.add(attemptThree);
    }
    public ArrayList<Object> getScores(){
        ArrayList<Object> toReturn = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++){
            if (i == 0){
                if (scores.get(i) == 10){
                    toReturn.add("X");
                    continue;
                }
                else{
                    toReturn.add(scores.get(i));
                    continue;
                }
            }
            else if (i == 1){
                if (scores.get(i) == 10 && scores.get(0) == 10){
                    toReturn.add("X");
                    continue;
                }
                else if (scores.get(i) + scores.get(0) == 10 && scores.get(0) != 10){
                    toReturn.add("/");
                    continue;
                }
                else{
                    toReturn.add(scores.get(i));
                    continue;
                }
            }
            else{
                if (scores.get(i) == 10 && ((scores.get(1) + scores.get(0) == 10) || (scores.get(1) == 10 && scores.get(0) == 10))){
                    toReturn.add("X");
                    continue;
                }
                else if (scores.get(i) + scores.get(1) == 10 && scores.get(0) == 10 && scores.get(1) != 10){
                    toReturn.add("/");
                    continue;
                }
                else{
                    toReturn.add(scores.get(i));
                    continue;
                }
            }
        }
        return toReturn;
    }
    public int getFrameNum(){
        return this.frameNum;
    }
}
