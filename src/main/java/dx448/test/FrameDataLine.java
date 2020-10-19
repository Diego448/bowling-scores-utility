package dx448.test;

import java.text.NumberFormat;

public class FrameDataLine {
    String playerName;
    Integer knockedPins;
    public FrameDataLine(String playerName, String knockedPins){
        this.playerName = setPlayerName(playerName);
        this.knockedPins = parseKnockedPins(knockedPins);
    }

    public Integer parseKnockedPins(String knockedPins) {
        if(validateScoreInput(knockedPins)) {
            Integer value = 0;
            if(knockedPins.equals("F")) {
                return value;
            } else {
                return Integer.parseInt(knockedPins);
            }
        } else {
            return 0;
        }
    }

    public Boolean validateScoreInput(String input) {
        if(input.length() <= 2 && input.length() > 0) {
            if(input.chars().allMatch(Character::isDigit)) {
                return true;
            } else if(input.equals("F")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Boolean validateNameInput(String input) {
        if(!input.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public String setPlayerName(String name) {
        if(validateNameInput(name)) {
            return name;
        } else {
            return "Unknown"; 
        }
    }
}
