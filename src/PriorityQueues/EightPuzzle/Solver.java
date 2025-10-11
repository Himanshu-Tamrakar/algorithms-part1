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
    private int minMoves = 0;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new java.lang.IllegalArgumentException();

        int moves = 0;
        int twinMoves = 0;

        Queue<Board> neighbors = new Queue<Board>();
        Queue<Board> twinNeighbors = new Queue<Board>();

        MinPQ<SearchNode> searchNodes = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinNodes = new MinPQ<SearchNode>();

        SearchNode searchNode = new SearchNode(initial, moves, null);
        SearchNode twinSearchNode = new SearchNode(initial.twin(), twinMoves, null);

        twinNodes.insert(twinSearchNode);
        searchNodes.insert(searchNode);

        boolean solved = false;
        boolean twinSolved = false;
        //System.out.println("Get Board: " + searchNodes.delMin().getBoard());
        SearchNode current = null;

        while (!solved && !twinSolved) {
            current = searchNodes.delMin();
            SearchNode predecessor = current.getPredecessor();
            Board temp = current.getBoard();
            solved = temp.isGoal();

            SearchNode twinCurrent = twinNodes.delMin();
            SearchNode twinPredecessor = twinCurrent.getPredecessor();
            Board twinTemp = twinCurrent.getBoard();
            twinSolved = twinTemp.isGoal();

            for (Board b : temp.neighbors())
                neighbors.enqueue(b);

            for (Board b : twinTemp.neighbors())
                twinNeighbors.enqueue(b);

            while(neighbors.size() > 0) {
                Board board = neighbors.dequeue();
                int move = current.getMoves();
                move++;
                if (predecessor != null && predecessor.getBoard().equals(board))
                    continue;

                SearchNode neighborNode = new SearchNode(board, move, current);
                //System.out.println("Priorities " + neighborNode.getPriority());
                searchNodes.insert(neighborNode);
            }

            while(twinNeighbors.size() > 0) {
                Board board = twinNeighbors.dequeue();
                int twinMove = current.getMoves();
                twinMove++;
                if (twinPredecessor != null && twinPredecessor.getBoard().equals(board))
                    continue;

                SearchNode neighborNode = new SearchNode(board, twinMove, twinCurrent);
                twinNodes.insert(neighborNode);
            }

            moves = current.getMoves() + 1;
            twinMoves = twinCurrent.getMoves() + 1;
            lastNode = current;
        }

        solvable = !twinSolved;
        minMoves = moves - 1;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable())
            return -1;
        return minMoves;

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
//        int[][] puzzle = {
//                {0,  1,  3},
//                {4,  2,  5},
//                {7,  8,  6}
//        };

        int[][] puzzle = {
                {1,  2,  3},
                {4,  5,  6},
                {8,  7,  0}

        };

        Board board = new Board(puzzle);

        Solver solver = new Solver(board);
//        for (Board solution: solver.solution()) {
//            StdOut.println(solution);
//        }
        StdOut.println("Can Solve: " + solver.isSolvable());
        StdOut.println("Moves: " + solver.moves());
    }

}
