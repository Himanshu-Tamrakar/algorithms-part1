package PriorityQueues.EightPuzzle;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private int[][] tiles;

    public Board(int[][] tiles) {
        this.tiles = tiles;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(tiles.length);
        s.append("\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                s.append(tiles[i][j]);
                s.append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }


    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n-1 && j == n-1) break;
                if (tiles[i][j] != (i * n + j + 1)) count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != n * i + j + 1) {
                    int item = tiles[i][j] - 1;
                    int shouldBeInX = Math.abs(item / n);
                    int shouldBeInY = Math.abs((item % n));

                    count += Math.abs(i - shouldBeInX);
                    count += Math.abs(j - shouldBeInY);
                }
            }

        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int n = tiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n-1 && j == n-1 && tiles[i][j] == 0) continue;
                if (i == n-1 && j == n-1 && tiles[i][j] != 0) return false;
                if (tiles[i][j] != i * n + j + 1) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;

        if (that.dimension() != this.dimension())
            return false;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++)
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighborBoards = new ArrayList<>();

        int emptyRow = -1;
        int emptyCol = -1;
        outerloop:
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                    break outerloop;
                }
            }
        }

        // Define the possible moves (up, down, left, right).
        int[][] moves = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };

        for (int[] move : moves) {
            int newRow = emptyRow + move[0];
            int newCol = emptyCol + move[1];

            if (checkBoundry(newRow, newCol)) {
                int[][] newTiles = new int[tiles.length][tiles.length];
                for (int i = 0; i < tiles.length; i++) {
                    for (int j = 0; j < tiles.length; j++) {
                        newTiles[i][j] = this.tiles[i][j];
                    }
                }

                int tileToSwap = newTiles[newRow][newCol];
                newTiles[emptyRow][emptyCol] = tileToSwap;
                newTiles[newRow][newCol] = 0;

                neighborBoards.add(new Board(newTiles));
            }
        }

        return neighborBoards;
    }

    // Assuming checkBoundry is a private helper method.
    private boolean checkBoundry(int row, int col) {
        return row >= 0 && row < tiles.length && col >= 0 && col < tiles.length;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int n = tiles.length;
        // Step 1: Create a deep copy of the board's tile configuration.
        int[][] twinTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinTiles[i][j] = this.tiles[i][j];
            }
        }

        // Step 2: Find and swap two non-blank adjacent tiles in the same row.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                // Ensure both tiles are non-blank before attempting to swap.
                if (twinTiles[i][j] != 0 && twinTiles[i][j + 1] != 0) {
                    // Step 3: Perform a single swap.
                    int temp = twinTiles[i][j];
                    twinTiles[i][j] = twinTiles[i][j + 1];
                    twinTiles[i][j + 1] = temp;

                    // Step 4: Return the new board and exit immediately.
                    return new Board(twinTiles);
                }
            }
        }

        return new Board(twinTiles);
    }


    public static void main(String[] args) {
        int[][] puzzle = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };

        Board board = new Board(puzzle);
        StdOut.println(board);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());

        for (Board b : board.neighbors()) {
            System.out.println(b);
        }
    }
}
