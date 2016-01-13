package loa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/** An automated Player.
 *  @author Maaz Uddin
 *  */
class MachinePlayer extends Player {

    /** A Hashset of all the moves that have been played. */
    private HashSet<Move> _moves = new HashSet<>();

    /** A counter of how many moves are made before clearing
     *  the Hashset. */
    private int hashCounter = 0;

    /** A MachinePlayer that plays the SIDE pieces in GAME. */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
    }

    @Override
    Move makeMove() {
        if (hashCounter % 10 == 0) {
            _moves.clear();
            hashCounter = 0;
        }
        hashCounter += 1;
        return minMax(getBoard().turn(), 1, Integer.MIN_VALUE,
                Integer.MAX_VALUE);
    }

    /** Return a Move using eval to determine the best possible move
     *  for SIDE, at a certain DEPTH, with ALPHA and BETA values. */
    Move minMax(Piece side, int depth, int alpha, int beta) {
        Board board = new Board(getBoard());
        Iterator<Move> iter = board.legalMoves();
        Move currMove;
        Move bestMove = null;
        int currVal = 0;
        int bestVal = Integer.MIN_VALUE;
        while (iter.hasNext()) {
            currMove = iter.next();
            if (_moves.contains(currMove)) {
                continue;
            }
            currVal = eval(currMove);
            if (currVal > bestVal) {
                bestVal = currVal;
                bestMove = currMove;
            }
        }
        _moves.add(bestMove);
        return bestMove;
    }

    /** Return an int that is the calculation of the distances of all
     *  the pieces in a board from an ArrayList of PIECES. */
    int piecesDist(ArrayList<ArrayList<Integer>> pieces) {
        int sum = 0;
        for (ArrayList<Integer> coords : pieces) {
            for (ArrayList<Integer> coords2 : pieces) {
                if (coords != coords2) {
                    int x1 = coords.get(0);
                    int y1 = coords.get(1);
                    int x2 = coords2.get(0);
                    int y2 = coords2.get(1);
                    double dist = Math.sqrt(Math.pow((y2 - y1), 2)
                            + Math.pow((x2 - x1), 2));
                    sum += (int) dist;
                }
            }
        }
        return sum;
    }

    /** Return an int that represents how good a MOVE is in
     *  the current board. */
    int eval(Move move) {
        Board board = new Board(getBoard());
        int val;
        board.makeMove(move);
        if (board.piecesContiguous(board.turn())) {
            val = Integer.MAX_VALUE;
        } else {
            int humanMove = piecesDist(board.arrayofCoordinates(board.turn()));
            int machineMove = piecesDist(board.arrayofCoordinates
                    (board.turn().opposite()));
            val = humanMove - machineMove;
        }
        return val;
    }
}
