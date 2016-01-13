package loa;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.HashSet;

import static loa.Piece.*;
import static loa.Direction.*;

/** Represents the state of a game of Lines of Action.
 *  @author Maaz Uddin
 */
class Board implements Iterable<Move> {

    /** Size of a board. */
    static final int M = 8;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row-1][col-1]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is MxM.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        clear();
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        _moves.clear();

        for (int r = 1; r <= M; r += 1) {
            for (int c = 1; c <= M; c += 1) {
                set(c, r, contents[r - 1][c - 1]);
            }
        }
        _turn = side;
    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }
        _moves.clear();
        _moves.addAll(board._moves);
        _turn = board._turn;

        for (int c = 1; c <= M; c++) {
            for (int r = 1; r <= M; r++) {
                set(c, r, board.get(c, r), _turn);
            }
        }
    }

    /** Return the contents of column C, row R, where 1 <= C,R <= 8,
     *  where column 1 corresponds to column 'a' in the standard
     *  notation. */
    Piece get(int c, int r) {
        return _currentState[r - 1][c - 1];
    }

    /** Return the contents of the square SQ.  SQ must be the
     *  standard printed designation of a square (having the form cr,
     *  where c is a letter from a-h and r is a digit from 1-8). */
    Piece get(String sq) {
        return get(col(sq), row(sq));
    }

    /** Return the column number (a value in the range 1-8) for SQ.
     *  SQ is as for {@link get(String)}. */
    static int col(String sq) {
        if (!ROW_COL.matcher(sq).matches()) {
            throw new IllegalArgumentException("bad square designator");
        }
        return sq.charAt(0) - 'a' + 1;
    }

    /** Return the row number (a value in the range 1-8) for SQ.
     *  SQ is as for {@link get(String)}. */
    static int row(String sq) {
        if (!ROW_COL.matcher(sq).matches()) {
            throw new IllegalArgumentException("bad square designator");
        }
        return sq.charAt(1) - '0';
    }

    /** Set the square at column C, row R to V, and make NEXT the next side
     *  to move, if it is not null. */
    void set(int c, int r, Piece v, Piece next) {
        _currentState[r - 1][c - 1] = v;
        if (next != null) {
            _turn = next;
        }
    }

    /** Set the square at column C, row R to V. */
    void set(int c, int r, Piece v) {
        set(c, r, v, null);
    }

    /** Assuming isLegal(MOVE), make MOVE. */
    void makeMove(Move move) {
        assert isLegal(move);
        _moves.add(move);
        Piece replaced = move.replacedPiece();
        int c0 = move.getCol0(), c1 = move.getCol1();
        int r0 = move.getRow0(), r1 = move.getRow1();
        if (replaced != EMP) {
            set(c1, r1, EMP);
        }
        set(c1, r1, move.movedPiece());
        set(c0, r0, EMP);
        _turn = _turn.opposite();
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        assert movesMade() > 0;
        Move move = _moves.remove(_moves.size() - 1);
        Piece replaced = move.replacedPiece();
        int c0 = move.getCol0(), c1 = move.getCol1();
        int r0 = move.getRow0(), r1 = move.getRow1();
        Piece movedPiece = move.movedPiece();
        set(c1, r1, replaced);
        set(c0, r0, movedPiece);
        _turn = _turn.opposite();
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff MOVE is legal for the player currently on move. */
    boolean isLegal(Move move) {
        if (move == null) {
            return false;
        }
        int c0 = move.getCol0();
        int r0 = move.getRow0();
        Piece startingPiece = get(c0, r0);
        if (!startingPiece.abbrev().equals(_turn.abbrev())) {
            return false;
        }
        if (move.length() != pieceCountAlong(move)) {
            return false;
        }
        if (blocked(move)) {
            return false;
        }
        return true;
    }

    /** Return a sequence of all legal moves from this position. */
    Iterator<Move> legalMoves() {
        return new MoveIterator();
    }

    @Override
    public Iterator<Move> iterator() {
        return legalMoves();
    }

    /** Return true if there is at least one legal move for the player
     *  on move. */
    public boolean isLegalMove() {
        return iterator().hasNext();
    }

    /** Return true iff either player has all his pieces continguous. */
    boolean gameOver() {
        return piecesContiguous(BP) || piecesContiguous(WP);
    }

     /** Return an ArrayList of ArrayLists of Integers that
      *  contain the coordinates for each SIDE piece in the board. */
    ArrayList<ArrayList<Integer>> arrayofCoordinates(Piece side) {
        ArrayList<ArrayList<Integer>> answer = new ArrayList<>();
        for (int c = 1; c <= M; c++) {
            for (int r = 1; r <= M; r++) {
                if (side.abbrev().equals(get(c, r).abbrev())) {
                    ArrayList<Integer> coords = new ArrayList<>();
                    coords.add(c);
                    coords.add(r);
                    answer.add(coords);
                }
            }
        }
        return answer;
    }

    /** Checks all the places around a coordinate C and R and adds
     *  the coordinates of that to a HASHSET. */
    void checkAroundPiece(int c, int r, HashSet<ArrayList<Integer>> hashSet) {
        String piece = get(c, r).abbrev();
        ArrayList<Integer> starting = new ArrayList<>();
        starting.add(c);
        starting.add(r);
        hashSet.add(starting);
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                int col = c + x;
                int row = r + y;
                if (col >= 1 && col <= M && row >= 1 && row <= M) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(col);
                    temp.add(row);
                    if (get(col, row).abbrev().equals(piece)
                            && !hashSet.contains(temp)) {
                        checkAroundPiece(col, row, hashSet);
                    }
                }
            }
        }
    }

    /** Return true iff SIDE's pieces are contiguous. */
    boolean piecesContiguous(Piece side) {
        ArrayList<ArrayList<Integer>> coordinates = arrayofCoordinates(side);
        int col = coordinates.get(0).get(0);
        int row = coordinates.get(0).get(1);
        HashSet<ArrayList<Integer>> hashSet = new HashSet<>();
        checkAroundPiece(col, row, hashSet);
        return hashSet.size() == coordinates.size();
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return b.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = M; r >= 1; r -= 1) {
            out.format("    ");
            for (int c = 1; c <= M; c += 1) {
                out.format("%s ", get(c, r).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return the direction of movement based on a MOVE. */
    private Direction getDirection(Move move) {
        int c0 = move.getCol0();
        int r0 = move.getRow0();
        int c1 = move.getCol1();
        int r1 = move.getRow1();
        int dRow = r1 - r0;
        int dCol = c1 - c0;
        if (dCol == 0 && dRow > 0) {
            return N;
        }
        if (dCol == 0 && dRow < 0) {
            return S;
        }
        if (dCol < 0 && dRow == 0) {
            return W;
        }
        if (dCol > 0 && dRow == 0) {
            return E;
        }
        if (dCol > 0 && dRow > 0) {
            return NE;
        }
        if (dCol < 0 && dRow > 0) {
            return NW;
        }
        if (dCol > 0 && dRow < 0) {
            return SE;
        }
        if (dCol < 0 && dRow < 0) {
            return SW;
        }
        return NOWHERE;
    }

    /** Return the number of pieces in the line of action indicated by MOVE. */
    private int pieceCountAlong(Move move) {
        Direction d = getDirection(move);
        return pieceCountAlong(move.getCol0(), move.getRow0(), d);
    }

    /** Return the number of pieces in the line of action in direction DIR and
     *  containing the square at column C and row R. */
    private int pieceCountAlong(int c, int r, Direction dir) {
        int counter = -1;
        int tempC = c;
        int tempR = r;
        while ((tempC >= 1 && tempR >= 1) && (tempC <= M && tempR <= M)) {
            if (!get(tempC, tempR).abbrev().equals("-")) {
                counter++;
            }
            tempR += dir.dr;
            tempC += dir.dc;
        }
        tempC = c;
        tempR = r;
        while ((tempC >= 1 && tempR >= 1) && (tempC <= M && tempR <= M)) {
            if (!get(tempC, tempR).abbrev().equals("-")) {
                counter++;
            }
            tempR -= dir.dr;
            tempC -= dir.dc;
        }
        return counter;
    }

    /** Return whether a MOVE is blocked by an opposing piece in its path
     * or by a piece of the same side at the target square. */
    public boolean blocked(Move move) {
        if (move == null) {
            return true;
        }
        int c0 = move.getCol0();
        int r0 = move.getRow0();
        int c1 = move.getCol1();
        int r1 = move.getRow1();
        if (get(c1, r1).abbrev().equals(_turn.abbrev())) {
            return true;
        }
        int deltaR = 0, deltaC = 0;
        if (r1 > r0) {
            deltaR = 1;
        }
        if (r1 < r0) {
            deltaR = -1;
        }
        if (c1 > c0) {
            deltaC = 1;
        }
        if (c1 < c0) {
            deltaC = -1;
        }
        for (int i = 1; i < move.length(); i++) {
            if (get(c0 + deltaC, r0
                    + deltaR).abbrev().equals(_turn.opposite().abbrev())) {
                return true;
            }
            c0 += deltaC;
            r0 += deltaR;
        }
        return false;
    }

    /** The standard initial configuration for Lines of Action. */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** The current state of the board. */
    private Piece[][] _currentState = {
            { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** An iterator returning the legal moves from the current board. */
    private class MoveIterator implements Iterator<Move> {
        /** Current piece under consideration. */
        private int _c, _r;
        /** Next direction of current piece to return. */
        private Direction _dir;
        /** Next move. */
        private Move _move;

        /** A new move iterator for turn(). */
        MoveIterator() {
            _c = 1; _r = 1; _dir = NOWHERE;
            incr();
        }

        @Override
        public boolean hasNext() {
            return _move != null;
        }

        @Override
        public Move next() {
            if (_move == null) {
                throw new NoSuchElementException("no legal move");
            }

            Move move = _move;
            incr();
            return move;
        }

        @Override
        public void remove() {
        }

        /** Advance to the next legal move. */
        private void incr() {
            _move = null;
            while (_r <= M) {
                while (_c <= M) {
                    if (!get(_c, _r).abbrev().equals(EMP.abbrev())) {
                        while (_dir != null) {
                            _move = Move.create(_c, _r,
                                    pieceCountAlong(_c, _r, _dir),
                                    _dir, Board.this);
                            _dir = _dir.succ();
                            if (isLegal(_move)) {
                                return;
                            }
                        }
                    }
                    if (_c == M) {
                        _c = 1;
                        _dir = N;
                        break;
                    } else {
                        _c++;
                        _dir = N;
                    }
                }
                _r++;
            }
        }
    }
}
