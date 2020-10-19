package dx448.test;

import java.util.List;
import java.util.ArrayList;


public class Frame {
    Integer frameNumber;
    Integer totalScore;
    private List<Integer> knockedPins;
    private Boolean isLastFrame = false;

    public Frame(Integer number) {
        this.frameNumber = number;
        this.knockedPins = new ArrayList<Integer>();
        this.totalScore = 0;
        this.isLastFrame = number.equals(10);
    }

    public void addKnockedPinCount(Integer count) {
        if(!this.isLastFrame && this.knockedPins.size() < 2) {
            this.knockedPins.add(count);
        } else if(this.isLastFrame && this.knockedPins.size() < 3){
            this.knockedPins.add(count);
        }
    }

    public List<Integer> getKnockedPins() {
        return this.knockedPins;
    }

    public String generalFormat(int position) {
        Integer originalValue = this.knockedPins.get(position);
        if(originalValue == 10) {
            return "X";
        } else if(originalValue == 0) {
            return "F";
        } else {
            return Integer.toString(originalValue);
        }
    }

    public void showScores() {
        if(this.isLastFrame) {
            System.out.print(generalFormat(0) + " " + generalFormat(1) + " " + generalFormat(2));
        } else {
            if(this.knockedPins.get(0) == 10) {
                System.out.print("X\t");
            } else if(this.knockedPins.get(0) == 0 && this.knockedPins.get(1) != 0){
                System.out.print("F " + this.knockedPins.get(1) + "\t");
            } else if(this.knockedPins.get(0) + this.knockedPins.get(1) == 10) {
                System.out.print(this.knockedPins.get(0) + " " + "/" + "\t");
            } else if(this.knockedPins.get(0) != 0 && this.knockedPins.get(1) == 0){
                System.out.print(this.knockedPins.get(0) + " " + "F" + "\t");
            } else if(this.knockedPins.get(0) == 0 && this.knockedPins.get(1) == 0){
                System.out.print("F" + " " + "F" + "\t");
            } else {
                System.out.print(this.knockedPins.get(0) + " " + this.knockedPins.get(1) + "\t");
            }
        }    
    }
}
