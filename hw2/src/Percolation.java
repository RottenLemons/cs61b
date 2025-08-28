import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    int[] board;
    WeightedQuickUnionUF fullSet;
    int boardLength;
    int numOfOpenSites;
    WeightedQuickUnionUF percSet;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.board = new int[N * N];
        this.fullSet = new WeightedQuickUnionUF(N * N);
        this.percSet = new WeightedQuickUnionUF(N * N);
        this.boardLength = N;
        this.numOfOpenSites = 0;

        for (int i = 0; i < N - 1; i++) {
            this.fullSet.union(i, N - 1);
            this.percSet.union(i, N - 1);
            this.percSet.union(N * N - 2 - i, N * N - 1);
        }
    }

    public void open(int row, int col) {
        int flatIdx = this.flattenedIdx(row, col);
        if (this.board[flatIdx] == 0) {
            this.board[flatIdx] = 1;
            this.numOfOpenSites++;

            if (isOpenNoErrors(row - 1, col)) {
                this.fullSet.union(flatIdx, flatIdx - this.boardLength);
                this.percSet.union(flatIdx, flatIdx - this.boardLength);
            }

            if (isOpenNoErrors(row + 1, col)) {
                this.fullSet.union(flatIdx, flatIdx + this.boardLength);
                this.percSet.union(flatIdx, flatIdx + this.boardLength);
            }

            if (isOpenNoErrors(row, col - 1)) {
                this.fullSet.union(flatIdx, flatIdx - 1);
                this.percSet.union(flatIdx, flatIdx - 1);
            }

            if (isOpenNoErrors(row, col + 1)) {
                this.fullSet.union(flatIdx, flatIdx + 1);
                this.percSet.union(flatIdx, flatIdx + 1);
            }

        }
    }

    public boolean isOpen(int row, int col) {
        if (isOut(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return (this.board[flattenedIdx(row, col)] == 1);
    }

    private boolean isOpenNoErrors(int row, int col) {
        if (isOut(row, col)) {
            return false;
        }
        return (this.board[flattenedIdx(row, col)] == 1);
    }

    private boolean isOut(int row, int col) {
        return (row > (this.boardLength - 1) ||
                col > (this.boardLength - 1) ||
                row < 0 ||
                col < 0);
    }

    public boolean isFull(int row, int col) {
        if (isOut(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        int flatIdx = this.flattenedIdx(row, col);

        return this.fullSet.connected(0, flatIdx) && this.isOpenNoErrors(row, col);
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        return this.percSet.connected(0, this.boardLength * this.boardLength - 1);
    }

    private int flattenedIdx(int row, int col) {
        return this.boardLength * row + col;
    }
}
