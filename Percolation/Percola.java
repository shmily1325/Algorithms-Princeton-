/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Percola {
    private boolean status[];
    private int size;
    private WeightedQuickUnionUF UF;
    private WeightedQuickUnionUF UFTop;

    private int getPosition(int row, int col) {
        return (row - 1) * size + col;
    }

    private void checkBounds(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size)
            throw new IndexOutOfBoundsException("Index out of bounds");
    }

    private boolean isOpen(int pos) {
        return status[pos] == true;
    }

    private void union(int aPos, int bPos, WeightedQuickUnionUF aUF) {
        if (!aUF.connected(aPos, bPos))
            aUF.union(aPos, bPos);
    }

    /**
     * Instantiates a new Percolation.
     *
     * @param n the n
     */
    public Percola(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Illegal argument");
        size = n;

        status = new boolean[n * n + 2];
        for (int i = 1; i < n * n + 1; i++)
            status[i] = false;
        status[0] = status[n * n + 1] = true;
        UF = new WeightedQuickUnionUF(n * n + 2);
        UFTop = new WeightedQuickUnionUF(n * n + 1);
    }

    /**
     * Open.
     *
     * @param row the row
     * @param col the col
     */
    public void open(int row, int col) {
        checkBounds(row, col);
        if (isOpen(row, col))
            return;

        int nowPos = getPosition(row, col);
        int prevRowPos = getPosition(row - 1, col), nextRowPos = getPosition(row + 1, col),
                prevColPos = getPosition(row, col - 1), nextColPos = getPosition(row, col + 1);

        status[nowPos] = true;

        if (row == 1) {
            union(0, nowPos, UF);
            union(0, nowPos, UFTop);
        }
        else if (isOpen(prevRowPos)) {
            union(nowPos, prevRowPos, UF);
            union(nowPos, prevRowPos, UFTop);
        }

        if (row == size) {
            union(size * size + 1, nowPos, UF);
        }
        else if (isOpen(nextRowPos)) {
            union(nowPos, nextRowPos, UF);
            union(nowPos, nextRowPos, UFTop);
        }
        if (col != 1 && isOpen(prevColPos)) {
            union(nowPos, prevColPos, UF);
            union(nowPos, prevColPos, UFTop);
        }
        if (col != size && isOpen(nextColPos)) {
            union(nowPos, nextColPos, UF);
            union(nowPos, nextColPos, UFTop);
        }
    }

    /**
     * Is full boolean.
     *
     * @param row the row
     * @param col the col
     * @return the boolean
     */
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        return UFTop.connected(0, getPosition(row, col));
    }

    /**
     * Is open boolean.
     *
     * @param row the row
     * @param col the col
     * @return the boolean
     */
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return isOpen(getPosition(row, col));
    }

    /**
     * Number of open sites int.
     *
     * @return the int
     */
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i < size * size + 1; i++) {
            count += (status[i] == true ? 1 : 0);
        }
        return count;
    }

    /**
     * Percolates boolean.
     *
     * @return the boolean
     */
    public boolean percolates() {
        return UF.connected(0, size * size + 1);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        StdOut.println("This is a simple check for pecolation!");
    }
}

