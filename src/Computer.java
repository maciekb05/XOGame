import java.util.HashSet;

public class Computer implements Player {
    private final char sign;
    private final int SCALE_FOR_GAIN_FUNCTION = 5;


    public Computer(char sign) {
        this.sign = sign;
    }

    @Override
    public void move(Board board) {
        HashSet<int[]> surroundingPositions = findSurroundingPositions(board);


        int max = Integer.MIN_VALUE;
        int[] maxPosition = new int[2];
        maxPosition[0] = 1;
        maxPosition[1] = 1;

        if(surroundingPositions.size() == 2 || surroundingPositions.size() == 1 || surroundingPositions.size() == 3) {
            for(int[] pos : surroundingPositions){
                try {
                    board.move(pos[0],pos[1],sign);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else{

            boolean maxPositionInitialized = false;
            for(int[] position : surroundingPositions) {
                if(board.charAtNormalized(position[0],position[1])==' ') {
                    if(!maxPositionInitialized){
                        maxPosition[0] = position[0];
                        maxPosition[1] = position[1];
                        maxPositionInitialized = true;
                    }

                    board.getGameBoard().get(position[0]-1).set(position[1]-1, sign);

                    HashSet<int[]> surroundingPositionsForOpponent = findSurroundingPositions(board);

                    int maxOpponent = 0;
                    int[] maxPositionOpponent = new int[2];
                    for(int[] positionOpponent : surroundingPositionsForOpponent) {
                        if (board.charAtNormalized(positionOpponent[0], positionOpponent[1]) == ' ') {

                            board.getGameBoard().get(positionOpponent[0] - 1).set(positionOpponent[1] - 1, opponentSign());

                            int currentOpponent = gainFromBoard(opponentSign(), board);
                            if (maxOpponent < currentOpponent) {
                                maxOpponent = currentOpponent;
                                maxPositionOpponent[0] = positionOpponent[0];
                                maxPositionOpponent[1] = positionOpponent[1];
                            }

                            board.getGameBoard().get(positionOpponent[0] - 1).set(positionOpponent[1] - 1, ' ');

                        }
                    }

                    board.getGameBoard().get(maxPositionOpponent[0] - 1).set(maxPositionOpponent[1] - 1, opponentSign());

                    HashSet<int[]> surroundingPositionsNext = findSurroundingPositions(board);

                    for(int[] positionNext : surroundingPositionsNext) {
                        if(board.charAtNormalized(positionNext[0],positionNext[1])==' ') {
                            board.getGameBoard().get(positionNext[0]-1).set(positionNext[1]-1, sign);

                            int current = gainFromBoard(sign, board);

                            if (max < current) {
                                max = current;
                                maxPosition[0] = position[0];
                                maxPosition[1] = position[1];
                            }

                            board.getGameBoard().get(positionNext[0]-1).set(positionNext[1]-1, ' ');
                        }
                    }

                    board.getGameBoard().get(maxPositionOpponent[0] - 1).set(maxPositionOpponent[1] - 1, ' ');
                    board.getGameBoard().get(position[0]-1).set(position[1]-1, ' ');
                }
            }
            try{
                board.move(maxPosition[0],maxPosition[1],sign);
            }
            catch (Exception e) {
                System.out.println(maxPosition[0]+"  "+maxPosition[1]);
                e.printStackTrace();
            }

        }
    }

    public HashSet<int[]> findSurroundingPositions(Board board) {
        HashSet<int[]> positions = new HashSet<>();
        for(int i = 1; i <= board.getSize(); i++) {
            for(int j = 1; j <= board.getSize(); j++) {
                if(board.charAtNormalized(i,j) == 'X' || board.charAtNormalized(i,j) == 'O'){

                    int[] current0 = new int[2];
                    current0[0] = i+1;
                    current0[1] = j;
                    if(board.positionIsInRangeOfBoard(current0[0]-1, current0[1]-1)){
                        if(board.charAtNormalized(current0[0],current0[1]) == ' ') {
                            positions.add(current0);
                        }

                    }

                    int[] current1 = new int[2];
                    current1[0] = i+1;
                    current1[1] = j+1;
                    if(board.positionIsInRangeOfBoard(current1[0]-1, current1[1]-1)){
                        if(board.charAtNormalized(current1[0],current1[1]) == ' ') {
                            positions.add(current1);
                        }
                    }

                    int[] current2 = new int[2];
                    current2[0] = i;
                    current2[1] = j+1;
                    if(board.positionIsInRangeOfBoard(current2[0]-1, current2[1]-1)){
                        if(board.charAtNormalized(current2[0],current2[1]) == ' ') {
                            positions.add(current2);
                        }
                    }

                    int[] current3 = new int[2];
                    current3[0] = i-1;
                    current3[1] = j+1;
                    if(board.positionIsInRangeOfBoard(current3[0]-1, current3[1]-1)){
                        if(board.charAtNormalized(current3[0],current3[1]) == ' ') {
                            positions.add(current3);
                        }
                    }

                    int[] current4 = new int[2];
                    current4[0] = i-1;
                    current4[1] = j;
                    if(board.positionIsInRangeOfBoard(current4[0]-1, current4[1]-1)){
                        if(board.charAtNormalized(current4[0],current4[1]) == ' ') {
                            positions.add(current4);
                        }
                    }

                    int[] current5 = new int[2];
                    current5[0] = i-1;
                    current5[1] = j-1;
                    if(board.positionIsInRangeOfBoard(current5[0]-1, current5[1]-1)){
                        if(board.charAtNormalized(current5[0],current5[1]) == ' ') {
                            positions.add(current5);
                        }
                    }

                    int[] current6 = new int[2];
                    current6[0] = i;
                    current6[1] = j-1;
                    if(board.positionIsInRangeOfBoard(current6[0]-1, current6[1]-1)){
                        if(board.charAtNormalized(current6[0],current6[1]) == ' ') {
                            positions.add(current6);
                        }
                    }

                    int[] current7 = new int[2];
                    current7[0] = i+1;
                    current7[1] = j-1;
                    if(board.positionIsInRangeOfBoard(current7[0]-1, current7[1]-1)){
                        if(board.charAtNormalized(current7[0],current7[1]) == ' ') {
                            positions.add(current7);
                        }
                    }
                }
            }
        }
        return positions;
    }

    public int gainFromBoard(char sign, Board board) {
        int gain = 0;
        for(int i = 1; i <= board.getSize(); i++) {
            for(int j = 1; j <= board.getSize(); j++) {
                if(board.charAt(i-1,j-1) == sign) {
                    gain += gainFromOnePoint(i,j,sign,board);
                }
                else if(board.charAt(i-1, j-1) == opponentSign()) {
                    int opponentGain = gainFromOnePoint(i,j,opponentSign(),board);
                    if (opponentGain < 0) {
                        gain -= opponentGain / 65;
                    }
                    else {
                        gain -= 65 * opponentGain;
                    }

                }
            }
        }
        return gain;
    }

    public int gainFromOnePoint(int posX, int posY, char sign, Board board) {
        int gainTop = gainFromOnePointTop(posX - 1, posY - 1, sign, board);
        int gainBottom = gainFromOnePointBottom(posX - 1, posY - 1, sign, board);
        int gainRight = gainFromOnePointRight(posX - 1, posY - 1, sign, board);
        int gainLeft = gainFromOnePointLeft(posX - 1, posY - 1, sign, board);
        int gainTopLeft = gainFromOnePointTopLeft(posX - 1, posY - 1, sign, board);
        int gainBottomLeft = gainFromOnePointBottomLeft(posX - 1, posY - 1, sign, board);
        int gainTopRight = gainFromOnePointTopRight(posX - 1, posY - 1, sign, board);
        int gainBottomRight = gainFromOnePointBottomRight(posX - 1, posY - 1, sign, board);
        return gainTop + gainBottom + gainRight + gainLeft + gainTopLeft + gainBottomLeft + gainTopRight + gainBottomRight;
    }

    private int gainFromOnePointTop(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        int placesToWin = 1;
        boolean restricted = false;
        for(int i = posX - 1; i >= posX - board.NUMBER_TO_WIN; i--) {
            if(board.positionIsInRangeOfBoard(i, posY)) {
                if(board.charAt(i, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(i, posY) == opponentSign()) {
                    gain /= SCALE_FOR_GAIN_FUNCTION;
                    restricted = true;
                    break;
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                restricted = true;
                break;
            }
        }
        for(int i = 0; i < board.NUMBER_TO_WIN - placesToWin; i++) {
            if(board.positionIsInRangeOfBoard(posX + 1 + i, posY)) {
                if(board.charAt(posX + 1 + i, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX + 1 + i, posY) == opponentSign()) {
                    if(restricted = true) {
                        return 0;
                    }
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                return 0;
            }
        }
        return gain;

    }

    private int gainFromOnePointRight(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        int placesToWin = 1;
        boolean restricted = false;
        for(int i = posY + 1; i < posY + board.NUMBER_TO_WIN; i++) {
            if(board.positionIsInRangeOfBoard(posX, i)) {
                if(board.charAt(posX, i) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, i) == opponentSign()) {
                    gain /= SCALE_FOR_GAIN_FUNCTION;
                    restricted = true;
                    break;
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                restricted = true;
                break;
            }
        }
        for(int i = 0; i < board.NUMBER_TO_WIN - placesToWin; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY - 1 - i)) {
                if(board.charAt(posX, posY - 1 - i) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY - 1 - i) == opponentSign()) {
                    if(restricted = true) {
                        return 0;
                    }
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                return 0;
            }
        }
        return gain;

    }

    private int gainFromOnePointLeft(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        int placesToWin = 1;
        boolean restricted = false;
        for(int i = posY - 1; i >= posY - board.NUMBER_TO_WIN; i--) {
            if(board.positionIsInRangeOfBoard(posX, i)) {
                if(board.charAt(posX, i) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, i) == opponentSign()) {
                    gain /= SCALE_FOR_GAIN_FUNCTION;
                    restricted = true;
                    break;
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                restricted = true;
                break;
            }
        }
        for(int i = 0; i < board.NUMBER_TO_WIN - placesToWin; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY + 1 + i)) {
                if(board.charAt(posX, posY + 1 + i) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY + 1 + i) == opponentSign()) {
                    if(restricted = true) {
                        return 0;
                    }
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                return 0;
            }
        }
        return gain;

    }

    private int gainFromOnePointBottom(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        int placesToWin = 1;
        boolean restricted = false;
        for(int i = posX + 1; i < posX + board.NUMBER_TO_WIN; i++) {
            if(board.positionIsInRangeOfBoard(i, posY)) {
                if(board.charAt(i, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(i, posY) == opponentSign()) {
                    gain /= SCALE_FOR_GAIN_FUNCTION;
                    restricted = true;
                    break;
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                restricted = true;
                break;
            }
        }
        for(int i = 0; i < board.NUMBER_TO_WIN - placesToWin; i++) {
            if (board.positionIsInRangeOfBoard(posX - 1 - i, posY)) {
                if (board.charAt(posX - 1 - i, posY) == sign) {
                    gain *= SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                } else if (board.charAt(posX - 1 - i, posY) == opponentSign()) {
                    if(restricted = true) {
                        return 0;
                    }
                } else {
                    placesToWin += 1;
                }
            } else {
                return 0;
            }
        }
        return gain;

    }

    private int gainFromOnePointBottomRight(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        int placesToWin = 1;
        boolean restricted = false;
        int oldposX = posX;
        int oldposY = posY;
        posX += 1;
        posY += 1;
        for(int i = 1; i < board.NUMBER_TO_WIN; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY)) {
                if(board.charAt(posX, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY) == opponentSign()) {
                    gain /= SCALE_FOR_GAIN_FUNCTION;
                    restricted = true;
                    break;
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                restricted = true;
                break;
            }
            posX += 1;
            posY += 1;
        }
        posX = oldposX - 1;
        posY = oldposY - 1;
        for(int i = 0; i < board.NUMBER_TO_WIN - placesToWin; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY)) {
                if(board.charAt(posX, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY) == opponentSign()) {
                    if(restricted = true) {
                        return 0;
                    }
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                return 0;
            }
            posX -= 1;
            posY -= 1;
        }
        return gain;

    }

    private int gainFromOnePointBottomLeft(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        int placesToWin = 1;
        boolean restricted = false;
        int oldposX = posX;
        int oldposY = posY;
        posX += 1;
        posY -= 1;
        for(int i = 1; i < board.NUMBER_TO_WIN; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY)) {
                if(board.charAt(posX, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY) == opponentSign()) {
                    gain /= SCALE_FOR_GAIN_FUNCTION;
                    restricted = true;
                    break;
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                restricted = true;
                break;
            }
            posX += 1;
            posY -= 1;
        }
        posX = oldposX - 1;
        posY = oldposY + 1;
        for(int i = 0; i < board.NUMBER_TO_WIN - placesToWin; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY)) {
                if(board.charAt(posX, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY) == opponentSign()) {
                    if(restricted = true) {
                        return 0;
                    }
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                return 0;
            }
            posX -= 1;
            posY += 1;
        }
        return gain;

    }

    private int gainFromOnePointTopRight(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        int placesToWin = 1;
        boolean restricted = false;
        int oldposX = posX;
        int oldposY = posY;
        posX -= 1;
        posY += 1;
        for(int i = 1; i < board.NUMBER_TO_WIN; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY)) {
                if(board.charAt(posX, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY) == opponentSign()) {
                    gain /= SCALE_FOR_GAIN_FUNCTION;
                    restricted = true;
                    break;
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                restricted = true;
                break;
            }
            posX -= 1;
            posY += 1;
        }
        posX = oldposX + 1;
        posY = oldposY - 1;
        for(int i = 0; i < board.NUMBER_TO_WIN - placesToWin; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY)) {
                if(board.charAt(posX, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY) == opponentSign()) {
                    if(restricted = true) {
                        return 0;
                    }
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                return 0;
            }
            posX += 1;
            posY -= 1;
        }
        return gain;

    }

    private int gainFromOnePointTopLeft(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        int placesToWin = 1;
        boolean restricted = false;
        int oldposX = posX;
        int oldposY = posY;
        posX -= 1;
        posY -= 1;
        for(int i = 1; i < board.NUMBER_TO_WIN; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY)) {
                if(board.charAt(posX, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY) == opponentSign()) {
                    gain /= SCALE_FOR_GAIN_FUNCTION;
                    restricted = true;
                    break;
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                restricted = true;
                break;
            }
            posX -= 1;
            posY -= 1;
        }
        posX = oldposX + 1;
        posY = oldposY + 1;
        for(int i = 0; i < board.NUMBER_TO_WIN - placesToWin; i++) {
            if(board.positionIsInRangeOfBoard(posX, posY)) {
                if(board.charAt(posX, posY) == sign) {
                    gain *=SCALE_FOR_GAIN_FUNCTION;
                    placesToWin += 1;
                }
                else if(board.charAt(posX, posY) == opponentSign()) {
                    if(restricted = true) {
                        return 0;
                    }
                }
                else {
                    placesToWin += 1;
                }
            }
            else {
                return 0;
            }
            posX += 1;
            posY += 1;
        }
        return gain;

    }



    private char opponentSign() {
        if(sign == 'X')
            return 'O';
        else
            return 'X';
    }
}
