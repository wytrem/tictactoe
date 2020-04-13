package net.wytrem.spigot.tictactoe.board;

import com.google.common.base.Preconditions;

import java.util.Arrays;

public class Board {
    public final String[][] content;
    private final int winningCount;
    private int remainingSpace;

    public Board(int size) {
        this(size, size);
    }

    public Board(int size, int winningCount) {
        this(size, size, winningCount);
    }

    public Board(int width, int height, int winningCount) {
        Preconditions.checkArgument(width > 0);
        Preconditions.checkArgument(height > 0);
        this.content = new String[width][height];
        this.winningCount = winningCount;
        this.remainingSpace = width * height;
    }

    public String get(int x, int y) {
        return this.content[x][y];
    }

    public int getWidth() {
        return this.content.length;
    }

    public int getHeight() {
        return this.content[0].length;
    }

    public void clear() {
        for (int i = 0; i < this.content.length; i++) {
            Arrays.fill(this.content[i], null);
        }
        this.remainingSpace = this.content.length * this.content[0].length;
    }

    public PlayResult play(int x, int y, String s) {
        Preconditions.checkNotNull(s);
        Preconditions.checkArgument(s.equals("x") || s.equals("o"));
        Preconditions.checkElementIndex(x, this.getWidth());
        Preconditions.checkElementIndex(y, this.getHeight());
        Preconditions.checkArgument(this.content[x][y] == null);

        this.content[x][y] = s;
        this.remainingSpace--;

        String winning = this.getWinning();

        if (winning != null) {
            return PlayResult.won(winning);
        }
        else if (this.remainingSpace == 0) {
            return PlayResult.PAT;
        }

        return PlayResult.CONTINUE;
    }

    public String getWinning() {
        if (this.wins("x")) {
            return "x";
        }
        if (this.wins("o")) {
            return "o";
        }
        return null;
    }

    public boolean wins(String player) {
        // check columns
        for (int i = 0; i < this.getWidth(); i++) {
            if (Array2DExplorer.consecutives(Array2DExplorer.column(this.content, i), player) >= this.winningCount) {
                return true;
            }
        }

        // check lines
        for (int i = 0; i < this.getHeight(); i++) {
            if (Array2DExplorer.consecutives(Array2DExplorer.line(this.content, i), player) >= this.winningCount) {
                return true;
            }
        }

        // check diagonals starting on first line
        for (int i = 0; i < this.getWidth(); i++) {
            if (Array2DExplorer.consecutives(Array2DExplorer.forwardsDiagonal(this.content, i, 0), player) >= this.winningCount) {
                return true;
            }


            if (Array2DExplorer.consecutives(Array2DExplorer.backwardsDiagonal(this.content, i, 0), player) >= this.winningCount) {
                return true;
            }
        }


        // check diagonals starting on first column
        for (int i = 0; i < this.getHeight(); i++) {
            if (Array2DExplorer.consecutives(Array2DExplorer.forwardsDiagonal(this.content, 0, i), player) >= this.winningCount) {
                return true;
            }

            if (Array2DExplorer.consecutives(Array2DExplorer.backwardsDiagonal(this.content, 0, i), player) >= this.winningCount) {
                return true;
            }
        }

        return false;
    }

    public void print() {
        for (int i = 0; i < this.content.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < this.content[i].length; j++) {
                if (this.content[i][j] == null) {
                    System.out.print(" ");
                }
                else {
                    System.out.print(this.content[i][j]);
                }
                System.out.print(" ");
            }
            System.out.println("]");
        }
    }

    public static class PlayResult {
        public static final PlayResult CONTINUE = new PlayResult();
        public static final PlayResult PAT = new PlayResult();

        public static PlayResult won(String player) {
            return new PlayerWon(player);
        }
    }

    public static class PlayerWon extends PlayResult {
        public final String player;

        public PlayerWon(String player) {
            this.player = player;
        }
    }

}
