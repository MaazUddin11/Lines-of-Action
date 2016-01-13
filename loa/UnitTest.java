package loa;

import ucb.junit.textui;
import org.junit.Test;

import static loa.Piece.BP;
import static loa.Piece.EMP;
import static loa.Piece.WP;
import static org.junit.Assert.*;

/** The suite of all JUnit tests for the loa package.
 *  @author Maaz Uddin
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    /** Test for initialize(). */
    @Test
    public void initializeTest() {
        Board board;
        Piece[][] initial = {
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
        };
        board = new Board();
        board.initialize(initial, BP);
        Board board2 = new Board(initial, BP);
        assertEquals(board, board2);

        Piece[][] test1 = {
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, BP,  EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP }
        };
        board = new Board();
        board.initialize(test1, BP);
        Board board3 = new Board(test1, BP);
        assertEquals(board, board3);
    }

    /** Test for copyFrom(). */
    @Test
    public void copyFromTest() {
        Board board = new Board();
        Piece[][] test1 = {
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, BP,  EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP }
        };
        Board board2 = new Board(test1, BP);
        board.copyFrom(board2);
        assertEquals(board, board2);

        Piece[][] test3 = {
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, BP,  WP,  BP,  BP,  BP,  EMP, EMP },
                { EMP, WP,  BP,  WP,  WP,  EMP, EMP, EMP },
                { EMP, EMP, BP,  BP,  WP,  WP,  EMP, WP  },
                { EMP, WP,  WP,  BP,  EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, BP,  EMP, EMP, EMP, EMP }
        };
        Board board3 = new Board(test3, WP);
        board.copyFrom(board3);
        assertEquals(board, board3);
    }

    /** Test for get(). */
    @Test
    public void getTest() {
        Board board;
        Piece[][] initial = {
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
        };
        board = new Board(initial, BP);
        assertEquals(board.get(2, 1), BP);
        assertEquals(board.get(4, 4), EMP);
        assertEquals(board.get(8, 3), WP);
    }

    /** Test for set(). */
    @Test
    public void setTest() {
        Board board = new Board();
        board.set(3, 2, BP);
        board.set(3, 3, BP);
        board.set(4, 4, BP);
        board.set(4, 5, BP);
        board.set(3, 6, BP);
        board.set(3, 7, BP);
        Piece[][] test1 = {
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, BP,  EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, BP,  EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
        };
        Board board2 = new Board(test1, BP);
        assertEquals(board, board2);
    }

    /** Test for isLegal(). */
    @Test
    public void isLegalTest() {
        Board board;
        Piece[][] initial = {
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
        };
        board = new Board(initial, BP);
        Move m1 = Move.create("b1-d3", board);
        assertEquals(board.isLegal(m1), true);
        Move m2 = Move.create("b1-d4", board);
        assertEquals(board.isLegal(m2), false);

        Piece[][] test1 = {
                { EMP, EMP, BP,  BP,  BP,  BP,  BP,  EMP },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, BP,  EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
        };
        board = new Board(test1, WP);
        Move m3 = Move.create("a3-d3", board);
        assertEquals(board.isLegal(m3), true);

        Piece[][] test2 = {
                { EMP, EMP, BP,  BP,  BP,  BP,  BP,  EMP },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { EMP, EMP, EMP, WP,  EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
        };
        board = new Board(test2, BP);
        Move m4 = Move.create("f1-a1", board);
        assertEquals(board.isLegal(m4), true);
        Move m5 = Move.create("e1-a1", board);
        assertEquals(board.isLegal(m5), false);
    }

    /** 1st set of tests for piecesContiguous(). */
    @Test
    public void piecesContiguousTest1() {
        Board board;
        Piece[][] initial = {
            { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
            { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
        };
        board = new Board(initial, BP);
        assertEquals(board.piecesContiguous(BP), false);
        assertEquals(board.piecesContiguous(WP), false);
        assertEquals(board.gameOver(), false);

        Piece[][] test1 = {
            { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
            { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, BP,  EMP, EMP, EMP, WP  },
            { WP,  EMP, EMP, BP,  EMP, EMP, EMP, WP  },
            { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
            { WP,  EMP, BP,  EMP, EMP, EMP, EMP, WP  },
            { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP }
        };
        board = new Board(test1, BP);
        assertEquals(board.piecesContiguous(BP), true);
        assertEquals(board.piecesContiguous(WP), false);
        assertEquals(board.gameOver(), true);

        Piece[][] test2 = {
            { BP,  BP,  BP,  BP,  BP,  BP,  BP,  BP  },
            { BP,  BP,  BP,  BP,  BP,  BP,  BP,  BP  },
            { BP,  BP,  BP,  BP,  BP,  BP,  BP,  BP  },
            { BP,  BP,  BP,  BP,  BP,  BP,  BP,  BP  },
            { BP,  BP,  BP,  BP,  BP,  BP,  BP,  BP  },
            { BP,  BP,  BP,  BP,  BP,  BP,  BP,  BP  },
            { BP,  BP,  BP,  BP,  BP,  BP,  BP,  BP  },
            { EMP, WP,  EMP, EMP, EMP, WP,  EMP, EMP }
        };
        board = new Board(test2, BP);
        assertEquals(board.piecesContiguous(BP), true);
        assertEquals(board.piecesContiguous(WP), false);
        assertEquals(board.gameOver(), true);
    }

    /** 2nd set of tests for piecesContiguous(). */
    @Test
    public void piecesContiguousTest2() {
        Board board;
        Piece[][] test3 = {
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, BP,  WP,  BP,  BP,  BP,  EMP, EMP },
                { EMP, WP,  BP,  WP,  WP,  EMP, EMP, EMP },
                { EMP, EMP, BP,  BP,  WP,  WP,  EMP, WP  },
                { EMP, WP,  WP,  BP,  EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, BP,  EMP, EMP, EMP, EMP }
        };
        board = new Board(test3, BP);
        assertEquals(board.piecesContiguous(BP), true);
        assertEquals(board.piecesContiguous(WP), false);
        assertEquals(board.gameOver(), true);

        Piece[][] test4 = {
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP  },
                { WP,  EMP, EMP, EMP, WP,  EMP, EMP, EMP  },
                { WP,  EMP, EMP, WP,  EMP, WP,  EMP, EMP  },
                { WP,  WP,  WP,  EMP, EMP, EMP, WP,  EMP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP  },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP  },
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP  }
        };
        board = new Board(test4, BP);
        assertEquals(board.piecesContiguous(BP), false);
        assertEquals(board.piecesContiguous(WP), true);
        assertEquals(board.gameOver(), true);

        Piece[][] test5 = {
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { WP,  EMP, EMP, EMP, EMP, EMP, EMP, EMP },
                { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
        };
        board = new Board(test5, BP);
        assertEquals(board.piecesContiguous(BP), false);
        assertEquals(board.piecesContiguous(WP), true);
        assertEquals(board.gameOver(), true);
    }

    /** Test for incr(). */
    @Test
    public void incrTest() {
        Board board = new Board();
        int count = 0;
        for (Move move : board) {
            count += 1;
        }
        assertEquals(36, count);
    }
}
