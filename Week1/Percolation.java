import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private static final byte CLOSED = 0;
  private static final byte OPEN = 1;
  private static final byte CONNECTED_TOP = 2;
  private static final byte CONNECTED_BOTTOM = 4;
  private static final byte CONNECTED_BOTH = 8;
  private byte[] pl;
  private boolean isPercolated;
  private int openSites, inputSize; // fullSize,
  private WeightedQuickUnionUF uf;

  private static int[] dx = {0, -1, 0, 1};
  private static int[] dy = {-1, 0, 1, 0};

  public Percolation(int n) {                // create n-by-n grid, with all sites blocked
    if(n <= 0)
      throw new IllegalArgumentException(Integer.toString(n));
    pl = new byte[n*n];
    uf = new WeightedQuickUnionUF(n*n);
    openSites = 0;
    isPercolated = false;
    inputSize = n;
    //fullSize = n*n;
  }

  private int index(int row, int col) {
    return (row-1)*inputSize+(col-1);
  }

  public void open(int row, int col) {    // open site (row, col) if it is not open already
    if(row <= 0 || row > inputSize || col <= 0 || col > inputSize)
      throw new IndexOutOfBoundsException();
    if(!isOpen(row, col)){
      int index = index(row, col);
      int flag;
      pl[index] = OPEN;
      openSites++;
      if(inputSize == 1){
        pl[index] = CONNECTED_BOTH;
        isPercolated = true;
      }
      else if(row == 1){
        pl[index] = CONNECTED_TOP;
      }
      else if(row == inputSize){
        pl[index] = CONNECTED_BOTTOM;
      }

      for(int i = 0; i < 4; i++){
        int x = row + dx[i], y = col + dy[i];
        int ind = index(x, y);
        int root, root2;
        root2 = uf.find(index);
        if(x > 0 && x <= inputSize && y > 0 && y <= inputSize && isOpen(x, y)){
          root = uf.find(ind);
          if((pl[root] == 2 && pl[root2] == 4) || (pl[root] == 4 && pl[root2] == 2)){
            pl[root2] = pl[root] = CONNECTED_BOTH;
            uf.union(root2, root);
            isPercolated = true;
          }
          else if(pl[root] == 8 || pl[root2] == 8){
            pl[root2] = pl[root] = CONNECTED_BOTH;
            uf.union(root2, root);
          }
          else if(pl[root] == 2 || pl[root2] == 2){
            pl[root2] = pl[root] = CONNECTED_TOP;
            uf.union(root2, root);
          }
          else if(pl[root] == 4 || pl[root2] == 4){
            pl[root2] = pl[root] = CONNECTED_BOTTOM;
            uf.union(root2, root);
          }
          else{
            uf.union(root2, root);
          }
        }
      }
      //System.out.println("It's opening now.");
    }
    //else
      //System.out.println("Already Opened.");
  }

  public boolean isOpen(int row, int col){  // is site (row, col) open?
    if(row <= 0 || row > inputSize || col <= 0 || col > inputSize)
      throw new IndexOutOfBoundsException();
    int index = index(row, col);
    if(pl[index] > 0)
      return true;
    else
      return false;
  }

  public boolean isFull(int row, int col){  // is site (row, col) full?
    if(row <= 0 || row > inputSize || col <= 0 || col > inputSize)
      throw new IndexOutOfBoundsException();
    int index = index(row, col);
    int root = uf.find(index);
    if(pl[root] == CONNECTED_TOP || pl[root] == CONNECTED_BOTH)
      return true;
    else
      return false;
  }

  public int numberOfOpenSites(){       // number of open sites
    return openSites;
  }

  public boolean percolates(){              // does the system percolate?
    return isPercolated;
  }

  public static void main(String[] args){  // test client (optional)
  }
}
