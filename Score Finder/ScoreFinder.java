import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class ScoreFinder implements ActionListener{
    private JLabel jlout;
    private JLabel errMsg;
    private List<JTextField> inputList;
    public static void main(String args[]) throws IOException{
        ScoreFinder test = new ScoreFinder();
        test.startGUI();
    }
    public void startGUI(){
        JFrame jf = new JFrame("Game Card");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jp = new JPanel();
        JLabel jl = new JLabel("Enter scores below:");
        jlout = new JLabel("Total Score: ---");
        JTextField input1 = new JTextField(3);
        JTextField input2 = new JTextField(3);
        JTextField input3 = new JTextField(3);
        JTextField input4 = new JTextField(3);
        JTextField input5 = new JTextField(3);
        JTextField input6 = new JTextField(3);
        JTextField input7 = new JTextField(3);
        JTextField input8 = new JTextField(3);
        JTextField input9 = new JTextField(3);
        JTextField input10 = new JTextField(5);
        inputList = Arrays.asList(input1,input2,input3,input4,input5,input6,input7,input8,input9,input10);
        JButton sub = new JButton("Submit");
        sub.addActionListener(this);
        Dimension size = jl.getPreferredSize();
        jp.setLayout(null);
        jf.setSize(550,200);
        jl.setBounds(25, 25, size.width + 50, size.height);
        int offset = 0;
        for (JTextField input : inputList){
            input.setLocation((int) jl.getLocation().getX() + offset, (int) jl.getLocation().getY() + size.height);
            Dimension inputSize = input.getPreferredSize();
            input.setSize(inputSize);
            input.setForeground(Color.red);
            jp.add(input);
            offset += input.getWidth();
        }
        Dimension sizeOut = jlout.getPreferredSize();
        jlout.setBounds((int) jl.getLocation().getX() + offset, (int) jl.getLocation().getY() + size.height, (int) sizeOut.getWidth() + 50, (int) sizeOut.getHeight());
        Dimension but = sub.getPreferredSize();
        sub.setBounds((int) jl.getLocation().getX(), (int)input1.getLocation().getY() + input1.getHeight(), (int)but.getWidth() + 50,(int) but.getHeight());
        errMsg = new JLabel(" ");
        errMsg.setBounds((int) sub.getLocation().getX(), (int) sub.getLocation().getY() + sub.getHeight() * 2 , (int) errMsg.getPreferredSize().getWidth() + 50, (int) errMsg.getPreferredSize().getHeight());
        jp.add(errMsg);
        jp.add(sub);
        jp.add(jl);
        jp.add(jlout);
        jf.add(jp);
        jf.setVisible(true);
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
                                        scoreNextOne = 10;
                                        sum += (int) scoreNextOne;
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
    @Override
    public void actionPerformed(ActionEvent e) {
        errMsg.setText(" ");
        errMsg.setSize(errMsg.getPreferredSize());
        ArrayList<Frame> Game = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            String frameScore = inputList.get(i).getText();
            String[] currFrame = frameScore.split(",");
            //decode the commonly used bowling symbols
            for (int j = 0; j < currFrame.length ; j++){
                String score = currFrame[j];
                if (score.equals("X")){
                    currFrame[j] = "10";
                }
                else if (score.equals("/")){
                    if (j == 0){
                        setErr();
                        return;
                    }
                    else {
                        try{
                            currFrame[j] =  String.valueOf((10 - Integer.parseInt(currFrame[j-1])));
                        } catch(Exception ex){
                            setErr();
                            return;
                        }
                    }
                }
            }

            switch (currFrame.length){
                case 1:
                    try {
                        Game.add(new Frame(i + 1, Integer.valueOf(currFrame[0])));
                    } catch (Exception e1) {
                        setErr();
                        return;
                    }
                    break;
                case 2:
                    try {
                        Game.add(new Frame(i + 1, Integer.valueOf(currFrame[0]), Integer.valueOf(currFrame[1])));
                    } catch (Exception e2) {
                        setErr();
                        return;
                    }
                    break;
                case 3:
                    try {
                        Game.add(new Frame(i + 1, Integer.valueOf(currFrame[0]), Integer.valueOf(currFrame[1]),Integer.valueOf(currFrame[2])));
                    } catch (Exception e3) {
                        setErr();
                        return;
                    }
                    break;
            }
        }
        jlout.setText("Total Score: " + calcScore(Game));
    }
    private void setErr(){
        errMsg.setText("INVALID SCORE ENTRY. PLEASE TRY AGAIN!");
        errMsg.setSize(errMsg.getPreferredSize());
        jlout.setText("Total Score: --- ");
    }
}
