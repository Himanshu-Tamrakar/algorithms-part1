package PriorityQueues.EightPuzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class Solver {
    private SearchNode lastNode;
    private boolean solvable;
    private int moves = 0;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();

        MinPQ<SearchNode> searchNodes = new MinPQ<>();
        searchNodes.insert(new SearchNode(initial, 0, null));

        Queue<Board> neighbourBoards = new Queue<>();
        Set<Board> visited = new HashSet<>();

        while (!searchNodes.isEmpty() && !solvable) {
            SearchNode currentSearchNode = searchNodes.delMin();
            Board currentBoard = currentSearchNode.getBoard();

            if (currentBoard.isGoal()) {
                solvable = true;
                lastNode = currentSearchNode;
                return;
            }

            if (visited.contains(currentBoard)) {
                continue;
            }
            visited.add(currentBoard);

            for (Board neighborBoard : currentBoard.neighbors()) {
                // This is optimization
                if (neighborBoard.isGoal()) {
                    solvable = true;
                    lastNode = new SearchNode(neighborBoard, currentSearchNode.getMoves() + 1, currentSearchNode);
                    break;
                }
                // Check against predecessor and visited set
                if (!visited.contains(neighborBoard)) {
                    SearchNode neighborNode = new SearchNode(neighborBoard, currentSearchNode.getMoves() + 1, currentSearchNode);
                    searchNodes.insert(neighborNode);
                }
            }
        }
        moves = lastNode.getMoves();
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return moves;

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> boards = new Stack<Board>();
        SearchNode lastNode = this.lastNode;
        if (isSolvable()) {
            while (lastNode.getPredecessor() != null) {
                boards.push(lastNode.getBoard());
                lastNode = lastNode.getPredecessor();
            }
            boards.push(lastNode.getBoard());
            return boards;
        }
        return null;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode predecessor = null;
        private int moves = 0;
        private Board currentBoard;
        private int priority;

        public SearchNode(Board board, int movesTill, SearchNode predecessorOf) {
            predecessor = predecessorOf;
            moves = movesTill;
            currentBoard = board;
            priority = movesTill + board.manhattan();
        }

        public Board getBoard() {
            return currentBoard;
        }

        public int getMoves() {
            return moves;
        }

        public SearchNode getPredecessor() {
            return predecessor;
        }

        public int getPriority() {
            return priority;
        }


        @Override
        public int compareTo(SearchNode that) {
            return this.getPriority() - that.getPriority();
        }
    }
    // test client (see below)
    public static void main(String[] args) {
        int[][] puzzle = {
                {0,  1,  3},
                {4,  2,  5},
                {7,  8,  6}
        };

        Board board = new Board(puzzle);

        Solver solver = new Solver(board);
        for (Board solution: solver.solution()) {
            StdOut.println(solution);
        }

        StdOut.println("Moves: " + solver.moves());
    }

}
