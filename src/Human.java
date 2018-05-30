import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Human implements Player {
    private final char sign;

    public Human(char sign) {
        this.sign = sign;
    }

    @Override
    public void move(Board board) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Ruch gracza " + sign + ", wybierz pozycje:");
        try {
            String position = reader.readLine();
            String[] positionSplitted = position.split(" ");
            Integer posX = Integer.parseInt(positionSplitted[0]);
            Integer posY = Integer.parseInt(positionSplitted[1]);
            board.move(posX, posY, sign);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
