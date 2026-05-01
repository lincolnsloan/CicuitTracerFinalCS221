import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CircuitMaker {
    public static void main(String[] args) {
        int numCols = 20;
        int numRows = 20;
        int closedChance = 4;
        String fileName = "valid11.dat";

        int randomNum;
        Random random = new Random();

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));

            writer.append(numRows + " ");
            writer.append(numCols + " ");

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    randomNum = random.nextInt(closedChance);

                    if (randomNum % closedChance == 0) {
                        writer.append("X ");
                    } else {
                        writer.append("O ");
                    }
                }
                writer.println();
            }
            
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        
    }
}
