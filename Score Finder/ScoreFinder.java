import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScoreFinder {
    public static void main(String args[]) throws IOException{
        ArrayList<Frame> Game = new ArrayList<>();
        // Scanner input = new Scanner(System.in);
        Scanner input = new Scanner(new File("C:\\Users\\ators\\AndroidStudioProjects\\Score Finder\\score.txt"));
        for (int i = 0; i < 10; i++){
            String strInput = input.next();
            String[] currFrame = strInput.split(",");
            switch (currFrame.length){
                case 1:
                    Game.add(new Frame(1, Integer.valueOf(currFrame[0])));
                    break;
                case 2:
                    Game.add(new Frame(1, Integer.valueOf(currFrame[0]), Integer.valueOf(currFrame[1])));
                    break;
                case 3:
                    Game.add(new Frame(10, Integer.valueOf(currFrame[0]), Integer.valueOf(currFrame[1]),Integer.valueOf(currFrame[2])));
                    break;
            }
        }
        for (int i = 0; i < 10; i++){
            System.out.print(Game.get(i).getScores() + ",");
        }
        System.out.println(calcScore(Game));
        input.close();
    }
    public static int calcScore(ArrayList<Frame> gameList){
        int sum = 0;
        for (int j = 0; j < gameList.size() ; j++){
                Frame currFrame = gameList.get(j);
                for (int i = 0; i < currFrame.getScores().size(); i++){
                    Object score = currFrame.getScores().get(i);
                    String testStr = " ";
                    if (score.getClass() == testStr.getClass()){
                        if (score.equals("X")){
                            sum += 10;
                            if (currFrame.getFrameNum() != 10){
                                Frame frameToDouble = gameList.get(j+1);
                                Object scoreNextOne = frameToDouble.getScores().get(0);
                                if (scoreNextOne.getClass() == testStr.getClass()){
                                    // if (scoreNextOne.equals("X")){
                                        scoreNextOne = 10;
                                        sum += (int) scoreNextOne;
                                    // }
                                }
                                else{
                                    sum += (int) scoreNextOne;
                                }
                                if (frameToDouble.getScores().size() < 2){
                                    Frame frameToDoubleTwo = gameList.get(j+2);
                                    scoreNextOne = frameToDoubleTwo.getScores().get(0);
                                    if (scoreNextOne.getClass() == testStr.getClass()){
                                        scoreNextOne = 10;
                                        sum += (int) scoreNextOne;
                                    }
                                    else{
                                        sum += (int) scoreNextOne;
                                    }
                                }
                                else{
                                    Object scoreNextTwo = frameToDouble.getScores().get(1);
                                    if (scoreNextTwo.getClass() == testStr.getClass()){
                                        if (scoreNextTwo.equals("/"))
                                            sum += 10 - (int) scoreNextOne;
                                        else 
                                            sum += 10;
                                    }
                                    else{
                                        sum += (int) scoreNextTwo;
                                    }
                                }
                            }
                            continue;
                        }
                        else if (score.equals("/")){
                            sum += (10 - (int) currFrame.getScores().get(i-1));
                            if (currFrame.getFrameNum() != 10){
                                Frame frameToDouble = gameList.get(j+1);
                                Object scoreNextOne = frameToDouble.getScores().get(0);
                                if (scoreNextOne.getClass() == testStr.getClass()){
                                    sum += 10;
                                }
                                else{
                                    sum += (int) scoreNextOne;
                                }
                            }
                            continue;
                        }
                    }
                    else{
                        sum += (int) score;
                        continue;
                    }
                }
            }
        return sum;
    }
}
