package dx448.test;

import java.io.IOException;

public class App {
    public static void main( String[] args ) throws IOException {
      
        BowlingGame newGame = new BowlingGame("test.txt");
        newGame.showScores();
    }
}
