import java.util.HashSet;

public class Computer implements Player {
    private final char sign;
    private final int WEIGHT_OF_OPPONENT_GAIN = 500;
    private final double SCALE_OPPONENT_GAIN = 0.05;
    private final int SCALE_FOR_GAIN_FUNCTION = 20;


    Computer(char sign) {
        this.sign = sign;
    }

    @Override
    public Board move(Board board) {
        System.out.println("Gain O: " + gainFromBoard('O', board));
        System.out.println("Gain X: " + gainFromBoard('X', board));
        HashSet<Position> surroundingPositions = findSurroundingPositions(board);

        int max = Integer.MIN_VALUE;
        Position maxPosition = new Position();

        if(surroundingPositions.size() == 2 || surroundingPositions.size() == 1 || surroundingPositions.size() == 3) {
            for(Position position : surroundingPositions){
                try {
                    board.move(position.positionX, position.positionY, sign);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            int opponentGainBeforeMove;
            int opponentGainAfterMove;

            boolean maxPositionInitialized = false;
            for(Position position : surroundingPositions) {
                if(board.charAtNormalized(position.positionX, position.positionY)==' ') {

                    if(!maxPositionInitialized){
                        maxPosition.positionX = position.positionX;
                        maxPosition.positionY = position.positionY;
                        maxPositionInitialized = true;
                    }

                    opponentGainBeforeMove = gainFromBoard(opponentSign(sign), board);
                    board.getGameBoard().get(position.positionX - 1).set(position.positionY - 1, sign);
                    opponentGainAfterMove = gainFromBoard(opponentSign(sign), board);

                    HashSet<Position> surroundingPositionsForOpponent = findSurroundingPositions(board);

                    int maxOpponent = 0;
                    Position maxPositionOpponent = new Position();
                    boolean maxPositionOpponentInitialized = false;

                    for(Position positionOpponent : surroundingPositionsForOpponent) {
                        if (board.charAtNormalized(positionOpponent.positionX, positionOpponent.positionY) == ' ') {

                            if(!maxPositionOpponentInitialized){
                                maxPositionOpponent.positionX = positionOpponent.positionX;
                                maxPositionOpponent.positionY = positionOpponent.positionY;
                                maxPositionOpponentInitialized = true;
                            }

                            board.getGameBoard().get(positionOpponent.positionX - 1).set(positionOpponent.positionY - 1, opponentSign(sign));

                            int currentOpponent = gainFromBoard(opponentSign(sign), board) - gainFromBoard(sign, board);
                            if (maxOpponent < currentOpponent) {
                                maxOpponent = currentOpponent;
                                maxPositionOpponent.positionX = positionOpponent.positionX;
                                maxPositionOpponent.positionY = positionOpponent.positionY;
                            }

                            board.getGameBoard().get(positionOpponent.positionX - 1).set(positionOpponent.positionY - 1, ' ');

                        }
                    }

                    board.getGameBoard().get(maxPositionOpponent.positionX - 1).set(maxPositionOpponent.positionY - 1, opponentSign(sign));

                    HashSet<Position> surroundingPositionsNext = findSurroundingPositions(board);

                    for(Position positionNext : surroundingPositionsNext) {
                        if(board.charAtNormalized(positionNext.positionX, positionNext.positionY) == ' ') {

                            board.getGameBoard().get(positionNext.positionX-1).set(positionNext.positionY - 1, sign);


                            int current = gainFromBoard(sign, board) - gainFromBoard(opponentSign(sign), board);

                            if(opponentGainAfterMove < opponentGainBeforeMove){
                                current += Math.abs(opponentGainBeforeMove - opponentGainAfterMove) * WEIGHT_OF_OPPONENT_GAIN;
                            }

                            if (max < current) {
                                max = current;
                                maxPosition.positionX = position.positionX;
                                maxPosition.positionY = position.positionY;
                            }

                            board.getGameBoard().get(positionNext.positionX-1).set(positionNext.positionY-1, ' ');
                        }
                    }

                    board.getGameBoard().get(maxPositionOpponent.positionX - 1).set(maxPositionOpponent.positionY - 1, ' ');
                    board.getGameBoard().get(position.positionX - 1).set(position.positionY - 1, ' ');
                }
            }
            try{
                board.move(maxPosition.positionX, maxPosition.positionY, sign);
            }
            catch (Exception e) {
                System.out.println(maxPosition.positionX + "  " + maxPosition.positionY);
                e.printStackTrace();
            }

        }
        return board;
    }

    private HashSet<Position> findSurroundingPositions(Board board) {
        HashSet<Position> positions = new HashSet<>();
        for(int i = 1; i <= board.getSize(); i++) {
            for(int j = 1; j <= board.getSize(); j++) {
                if(board.charAtNormalized(i,j) == 'X' || board.charAtNormalized(i,j) == 'O'){

                    Position current0 = new Position();
                    current0.positionX = i+1;
                    current0.positionY = j;
                    if(board.positionIsInRangeOfBoard(current0.positionX - 1, current0.positionY - 1)){
                        if(board.charAtNormalized(current0.positionX, current0.positionY) == ' ') {
                            positions.add(current0);
                        }

                    }

                    Position current1 = new Position();
                    current1.positionX = i+1;
                    current1.positionY = j+1;
                    if(board.positionIsInRangeOfBoard(current1.positionX-1, current1.positionY-1)){
                        if(board.charAtNormalized(current1.positionX,current1.positionY) == ' ') {
                            positions.add(current1);
                        }
                    }

                    Position current2 = new Position();
                    current2.positionX = i;
                    current2.positionY = j+1;
                    if(board.positionIsInRangeOfBoard(current2.positionX-1, current2.positionY-1)){
                        if(board.charAtNormalized(current2.positionX,current2.positionY) == ' ') {
                            positions.add(current2);
                        }
                    }

                    Position current3 = new Position();
                    current3.positionX = i-1;
                    current3.positionY = j+1;
                    if(board.positionIsInRangeOfBoard(current3.positionX-1, current3.positionY-1)){
                        if(board.charAtNormalized(current3.positionX,current3.positionY) == ' ') {
                            positions.add(current3);
                        }
                    }

                    Position current4 = new Position();
                    current4.positionX = i-1;
                    current4.positionY = j;
                    if(board.positionIsInRangeOfBoard(current4.positionX-1, current4.positionY-1)){
                        if(board.charAtNormalized(current4.positionX,current4.positionY) == ' ') {
                            positions.add(current4);
                        }
                    }

                    Position current5 = new Position();
                    current5.positionX = i-1;
                    current5.positionY = j-1;
                    if(board.positionIsInRangeOfBoard(current5.positionX-1, current5.positionY-1)){
                        if(board.charAtNormalized(current5.positionX,current5.positionY) == ' ') {
                            positions.add(current5);
                        }
                    }

                    Position current6 = new Position();
                    current6.positionX = i;
                    current6.positionY = j-1;
                    if(board.positionIsInRangeOfBoard(current6.positionX-1, current6.positionY-1)){
                        if(board.charAtNormalized(current6.positionX,current6.positionY) == ' ') {
                            positions.add(current6);
                        }
                    }

                    Position current7 = new Position();
                    current7.positionX = i+1;
                    current7.positionY = j-1;
                    if(board.positionIsInRangeOfBoard(current7.positionX-1, current7.positionY-1)){
                        if(board.charAtNormalized(current7.positionX,current7.positionY) == ' ') {
                            positions.add(current7);
                        }
                    }
                }
            }
        }
        return positions;
    }

    int gainFromBoard(char sign, Board board) {
        int gain = 0;
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                if(board.charAt(i, j) == sign) {
                    gain += gainFromOnePoint(i, j, sign, board);
                }
                else if(board.charAt(i, j) == opponentSign(sign)) {
                    int opponentGain = gainFromOnePoint(i, j, opponentSign(sign), board);
                    if(sign == opponentSign(this.sign)) {
                        gain -= (opponentGain * SCALE_OPPONENT_GAIN);
                    }
                }
            }
        }
        return gain;
    }

    private int gainFromOnePoint(int posX, int posY, char sign, Board board) {
        int gainTop = gainFromOnePointTop(posX, posY, sign, board);
        int gainBottom = gainFromOnePointBottom(posX, posY, sign, board);
        int gainRight = gainFromOnePointRight(posX, posY, sign, board);
        int gainLeft = gainFromOnePointLeft(posX, posY, sign, board);
        int gainTopLeft = gainFromOnePointTopLeft(posX, posY, sign, board);
        int gainBottomLeft = gainFromOnePointBottomLeft(posX, posY, sign, board);
        int gainTopRight = gainFromOnePointTopRight(posX, posY, sign, board);
        int gainBottomRight = gainFromOnePointBottomRight(posX, posY, sign, board);
        return gainTop + gainBottom + gainRight + gainLeft + gainTopLeft + gainBottomLeft + gainTopRight + gainBottomRight;
    }

    private int gainFromOnePointLeftOrBottom(int posX, int posY, char sign, Board board, Boolean left) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        Flags flags = new Flags();

        int oldposX = posX;
        int oldposY = posY;

        if(left){
            posY -= 1;
        }
        else {
            posX -= 1;
        }

        for(int i = 0; i < board.NUMBER_TO_WIN; i++) {
            gain = gainRescale(board, posX, posY, sign, gain, flags);

            if(flags.end) {
                break;
            }

            if(left) {
                posY -= 1;
            }
            else {
                posX -= 1;
            }
        }

        if(left) {
            posY = oldposY + 1;
        }
        else {
            posX = oldposX + 1;
        }


        int remaining = board.NUMBER_TO_WIN - flags.placesToWin;

        for(int i = 0; i < remaining; i++) {
            gain = gainRescaleSecondTime(board, posX, posY, sign, gain, flags);

            if(left) {
                posY += 1;
            }
            else {
                posX += 1;
            }
        }

        return gain;
    }

    private int gainFromOnePointTop(int posX, int posY, char sign, Board board) {
        return gainFromOnePointLeftOrBottom(posX, posY, sign, board, false);
    }

    private int gainFromOnePointLeft(int posX, int posY, char sign, Board board) {
        return gainFromOnePointLeftOrBottom(posX, posY, sign, board, true);
    }

    private int gainFromOnePointRightOrBottom(int posX, int posY, char sign, Board board, Boolean right) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        Flags flags = new Flags();

        int oldposY = posY;
        int oldposX = posX;


        if(right) {
            posY += 1;
        }
        else {
            posX += 1;
        }

        for(int i = 0; i < board.NUMBER_TO_WIN; i++) {
            gain = gainRescale(board, posX, posY, sign, gain, flags);

            if(flags.end) {
                break;
            }

            if(right) {
                posY += 1;
            }
            else {
                posX += 1;
            }
        }

        if(right) {
            posY = oldposY - 1;
        }
        else {
            posX = oldposX - 1;
        }

        int remaining = board.NUMBER_TO_WIN - flags.placesToWin;

        for(int i = 0; i < remaining; i++) {
            gain = gainRescaleSecondTime(board, posX, posY, sign, gain, flags);

            if(right) {
                posY -= 1;
            }
            else {
                posX -= 1;
            }
        }

        return gain;
    }

    private int gainFromOnePointRight(int posX, int posY, char sign, Board board) {
        return gainFromOnePointRightOrBottom(posX, posY, sign, board, true);
    }

    private int gainFromOnePointBottom(int posX, int posY, char sign, Board board) {
        return gainFromOnePointRightOrBottom(posX, posY, sign, board, false);
    }

    private int gainFromOnePointBottomRight(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        Flags flags = new Flags();

        int oldposX = posX;
        int oldposY = posY;

        posX += 1;
        posY += 1;

        for(int i = 0; i < board.NUMBER_TO_WIN; i++) {
            gain = gainRescale(board, posX, posY, sign, gain, flags);

            if(flags.end) {
                break;
            }

            posX += 1;
            posY += 1;
        }

        posX = oldposX - 1;
        posY = oldposY - 1;

        int remaining = board.NUMBER_TO_WIN - flags.placesToWin;

        for(int i = 0; i < remaining; i++) {
            gain = gainRescaleSecondTime(board, posX, posY, sign, gain, flags);

            posX -= 1;
            posY -= 1;
        }

        return gain;
    }

    private int gainFromOnePointBottomLeft(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        Flags flags = new Flags();

        int oldposX = posX;
        int oldposY = posY;

        posX += 1;
        posY -= 1;

        for(int i = 0; i < board.NUMBER_TO_WIN; i++) {
            gain = gainRescale(board, posX, posY, sign, gain, flags);

            if(flags.end) {
                break;
            }

            posX += 1;
            posY -= 1;
        }

        posX = oldposX - 1;
        posY = oldposY + 1;

        int remaining = board.NUMBER_TO_WIN - flags.placesToWin;

        for(int i = 0; i < remaining; i++) {
            gain = gainRescaleSecondTime(board, posX, posY, sign, gain, flags);

            posX -= 1;
            posY += 1;
        }

        return gain;
    }

    private int gainFromOnePointTopRight(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        Flags flags = new Flags();

        int oldposX = posX;
        int oldposY = posY;

        posX -= 1;
        posY += 1;

        for(int i = 0; i < board.NUMBER_TO_WIN; i++) {
            gain = gainRescale(board, posX, posY, sign, gain, flags);

            if(flags.end) {
                break;
            }

            posX -= 1;
            posY += 1;
        }

        posX = oldposX + 1;
        posY = oldposY - 1;

        int remaining = board.NUMBER_TO_WIN - flags.placesToWin;

        for(int i = 0; i < remaining; i++) {
            gain = gainRescaleSecondTime(board, posX, posY, sign, gain, flags);

            posX += 1;
            posY -= 1;
        }

        return gain;
    }

    private int gainFromOnePointTopLeft(int posX, int posY, char sign, Board board) {
        int gain =SCALE_FOR_GAIN_FUNCTION;
        Flags flags = new Flags();

        int oldposX = posX;
        int oldposY = posY;

        posX -= 1;
        posY -= 1;

        for(int i = 0; i < board.NUMBER_TO_WIN; i++) {
            gain = gainRescale(board, posX, posY, sign, gain, flags);

            if(flags.end) {
                break;
            }

            posX -= 1;
            posY -= 1;
        }

        posX = oldposX + 1;
        posY = oldposY + 1;

        int remaining = board.NUMBER_TO_WIN - flags.placesToWin;

        for(int i = 0; i < remaining; i++) {
            gain = gainRescaleSecondTime(board, posX, posY, sign, gain, flags);

            posX += 1;
            posY += 1;
        }

        return gain;
    }

    private int gainRescale(Board board, int x, int y, char sign, int gain, Flags flags) {
        if(board.positionIsInRangeOfBoard(x, y)) {
            if(board.charAt(x, y) == sign) {
                gain *= SCALE_FOR_GAIN_FUNCTION;
                flags.placesToWin += 1;
            }
            else if(board.charAt(x, y) == opponentSign(sign)) {
                gain /= SCALE_FOR_GAIN_FUNCTION;
                flags.restricted = true;
                flags.end = true;
            }
            else {
                flags.placesToWin += 1;
            }
        }
        else {
            gain /= SCALE_FOR_GAIN_FUNCTION;
            flags.restricted = true;
            flags.end = true;
        }
        return gain;
    }

    private int gainRescaleSecondTime(Board board, int x, int y, char sign, int gain, Flags flags) {
        if(board.positionIsInRangeOfBoard(x, y)) {
            if(board.charAt(x, y) == sign) {
                gain *= SCALE_FOR_GAIN_FUNCTION;
                flags.placesToWin += 1;
            }
            else if(board.charAt(x, y) == opponentSign(sign)) {
                return 0;
            }
            else {
                flags.placesToWin += 1;
            }
        }
        else {
            return 0;
        }
        return gain;
    }

    private char opponentSign(char sign) {
        if(sign == 'X')
            return 'O';
        else
            return 'X';
    }
}
