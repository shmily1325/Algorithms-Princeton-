/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] board;
    private final int size;
    private int openedCount;
    private final WeightedQuickUnionUF uf1;
    private final WeightedQuickUnionUF uf2;
    private final int bottom;
    private final int top;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Illegal argument");
        size = n;
        openedCount = 0;

        // n-by-n grid, all sites initiallly blocked
        board = new boolean[n][n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                board[j][i] = false;
            }
        }

        uf1 = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        top = n*n;
        bottom = n*n+1;
    }

    // check position available
    private void checkPosition(int row, int col){
        if(row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }
    private int twoDtoOneD(int row, int col) {
        return (row-1)*size + col - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkPosition(row, col);
        int index = twoDtoOneD(row, col);
        int tmpY = row-1;
        int tmpX = col-1;
        if (board[tmpY][tmpX] == false){
            board[tmpY][tmpX] = true;
            if (tmpY == size-1){ //bottom
                uf1.union(bottom, index);
            }
            if (tmpY == 0){ // top
                uf1.union(top, index);
                uf2.union(top, index);
            }
            if (tmpY-1 >= 0 && board[tmpY-1][tmpX]==true){ // up
                uf1.union(index, twoDtoOneD(tmpY-1+1, tmpX+1));
                uf2.union(index, twoDtoOneD(tmpY-1+1, tmpX+1));
            }
            if (tmpY+1 < size && board[tmpY+1][tmpX]==true){ // down
                uf1.union(index, twoDtoOneD(tmpY+1+1, tmpX+1));
                uf2.union(index, twoDtoOneD(tmpY+1+1, tmpX+1));
            }
            if (tmpX-1 >= 0 && board[tmpY][tmpX-1]==true){ // left
                uf1.union(index, twoDtoOneD(tmpY+1, tmpX-1+1));
                uf2.union(index, twoDtoOneD(tmpY+1, tmpX-1+1));
            }
            if (tmpX+1 < size && board[tmpY][tmpX+1]==true){ // right
                uf1.union(index, twoDtoOneD(tmpY+1, tmpX+1+1));
                uf2.union(index, twoDtoOneD(tmpY+1, tmpX+1+1));
            }
            openedCount++;
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        checkPosition(row, col);
        return board[row-1][col-1] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        checkPosition(row, col);
        int index = twoDtoOneD(row, col);
        return uf2.connected(top, index);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openedCount;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf1.connected(top, bottom);
    }

    // test client (optional)
    // public static void main(String[] args){

    // }
}

