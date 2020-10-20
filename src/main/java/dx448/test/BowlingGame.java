package dx448.test;

import java.util.List;
import java.util.LinkedList;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class BowlingGame {
    Integer frameNumber = 10;
    Map<String, List<Frame>> playerFrames;
    Integer temporalScore = 0;

    
    public BowlingGame(String file) throws IOException {
        this.playerFrames = new HashMap<String, List<Frame>>();
        readData(file);
    }

    void readData(String file) throws IOException {
        try(Scanner scan = new Scanner(new BufferedReader(new FileReader(file)))) {
            FrameDataLine previousData = new FrameDataLine("", "0");
            while(scan.hasNext()) {    
                FrameDataLine currentData = new FrameDataLine(scan.next(), scan.next());
                Boolean isLastFrame = getLastFrame(currentData);
                if(validateData(previousData, currentData, isLastFrame)) {
                    processFrames(previousData, currentData);
                }
                previousData = currentData;
            } 
        } catch(IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        
    }

    void processFrames(FrameDataLine previousData, FrameDataLine currentData) {
        Integer frameNumber = 1;
        List<Frame> frameList = this.playerFrames.get(currentData.playerName);
        frameNumber = getFrameNumber(frameList);
        Frame newFrame;
        if(currentData.knockedPins != 10 && previousData.playerName.equals(currentData.playerName)) {
            newFrame = frameList.get(frameList.size() - 1); 
        } else {
            newFrame = new Frame(frameNumber);
        }
        if(frameList == null)
            this.playerFrames.put(currentData.playerName, frameList=new LinkedList<Frame>());
        newFrame.addKnockedPinCount(currentData.knockedPins);
        if(!previousData.playerName.equals(currentData.playerName))
            frameList.add(newFrame);
    }

    public Boolean validateData(FrameDataLine previousData, FrameDataLine currentData, Boolean isLastFrame) {
        Integer pinLimit = isLastFrame ? 10 : 20;
        if(!currentData.playerName.equals(previousData.playerName)) {
            return currentData.knockedPins <= pinLimit;
        } else {
            return (currentData.knockedPins + previousData.knockedPins) <= pinLimit;
        }
    }

    public void showScores() {
        System.out.println("Frame scores: ");
        showFrameScores();
        System.out.println("Total scores: ");
        showTotalScores();
    }

    public void showFrameScores() {
        for(String player : this.playerFrames.keySet()) {
            List<Frame> currentPlayerFrames = new LinkedList<>(this.playerFrames.get(player));
            printScoreHeader();
            System.out.print(player + ":\t");
            for(Frame frame: currentPlayerFrames) {  
                frame.showScores(); 
            }
            System.out.print("\n");
        }
    }

    public void showTotalScores() {
        for(String player : this.playerFrames.keySet()) {
            List<Frame> currentPlayerFrames = new LinkedList<>(this.playerFrames.get(player));
            System.out.print(player + ": ");
            for(int i = 0; i < 9; i++) {
                Frame currentFrame = currentPlayerFrames.get(i);
                Frame nextFrame = currentPlayerFrames.get(i + 1);
                System.out.print(calculateScores(currentFrame, nextFrame) + "\t");
            }
            System.out.print(calculateLastScore(currentPlayerFrames.get(9)));
            this.temporalScore = 0;
            System.out.print("\n");
        }
    }

    public Integer calculateScores(Frame currentFrame, Frame nextFrame) {
        Integer currentFrameTotal;
        Integer nextFrameScore = nextFrame.getKnockedPins().get(0);
        if(currentFrame.getKnockedPins().get(0) == 10) {
            currentFrameTotal = 20;
        } else {
            currentFrameTotal = currentFrame.getKnockedPins().stream().mapToInt(i -> i.intValue()).sum();
        }
        this.temporalScore += currentFrameTotal + nextFrameScore;
        return temporalScore;
    }

    public Integer calculateLastScore(Frame lastFrame) {
        return temporalScore + lastFrame.getKnockedPins().stream().mapToInt(i -> i.intValue()).sum();
    }

    public void printScoreHeader() {
        System.out.print("Frame\t");    
        for(int i = 1; i < 11; i++) {
            System.out.print(i + "\t");
        }    
        System.out.print("\n");
    }

    public Integer getFrameNumber(List<Frame> frameList) {
        if(frameList != null) {
            return frameList.size() + 1;
        } else {
            return 1;
        }
    }

    public Boolean getLastFrame(FrameDataLine currentData) {
        if(this.playerFrames.get(currentData.playerName) != null)
            return this.playerFrames.get(currentData.playerName).size() == 9;
        return false;
    }

}
