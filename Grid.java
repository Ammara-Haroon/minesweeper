import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Grid {
  int gridSize = 10;
  int numberOfMines = 10;
  Cell[][] cells;
  int minesLeft = 10;

  public Grid(int gridSize, int numberfMines) {
    this.gridSize = gridSize;
    this.numberOfMines = numberOfMines;
    this.minesLeft = numberfMines;
    this.cells = getCells(gridSize);
    int[][] mineLocations = getUniqueMineLocations(numberfMines, gridSize);
    // System.out.println(Arrays.deepToString(mineLocations));
    setMineLocatons(mineLocations);
    setMineSurroundings(mineLocations);
  }

  public void toggleMark(int x, int y) {
    if (this.cells[x - 1][y - 1].isMarked()) {
      this.minesLeft++;
    } else {
      this.minesLeft--;
    }
    this.cells[x - 1][y - 1].toggleMark();
  }

  public boolean reveal(int x, int y) {
    return this.cells[x - 1][y - 1].reveal();
  }

  private Cell[][] getCells(int gridSize) {
    Cell[][] cells = new Cell[gridSize][gridSize];
    for (int i = 0; i < gridSize; ++i) {
      for (int j = 0; j < gridSize; ++j) {
        cells[i][j] = new Cell();
      }
    }
    return cells;
  }

  private void setMineSurroundings(int[][] mineLocations) {

    for (int i = 0; i < mineLocations.length; ++i) {
      int x = mineLocations[i][0];
      int y = mineLocations[i][1];

      if (x + 1 < this.gridSize) {
        if (this.cells[x + 1][y].getType() != CellType.MINE) {
          this.cells[x + 1][y].setValue(this.cells[x + 1][y].getValue() + 1);
          this.cells[x + 1][y].setType(CellType.NEXT_TO_MINE);
        }
        if (y - 1 >= 0 && this.cells[x + 1][y - 1].getType() != CellType.MINE) {
          this.cells[x + 1][y - 1].setValue(this.cells[x + 1][y - 1].getValue() + 1);
          this.cells[x + 1][y - 1].setType(CellType.NEXT_TO_MINE);
        }
        if (y + 1 < this.gridSize && this.cells[x + 1][y + 1].getType() != CellType.MINE) {
          this.cells[x + 1][y + 1].setValue(this.cells[x + 1][y + 1].getValue() + 1);
          this.cells[x + 1][y + 1].setType(CellType.NEXT_TO_MINE);

        }
      }
      if (x - 1 >= 0) {
        if (this.cells[x - 1][y].getType() != CellType.MINE) {
          this.cells[x - 1][y].setValue(this.cells[x - 1][y].getValue() + 1);
          this.cells[x - 1][y].setType(CellType.NEXT_TO_MINE);
        }
        if (y - 1 >= 0 && this.cells[x - 1][y - 1].getType() != CellType.MINE) {
          this.cells[x - 1][y - 1].setValue(this.cells[x - 1][y - 1].getValue() + 1);
          this.cells[x - 1][y - 1].setType(CellType.NEXT_TO_MINE);
        }
        if (y + 1 < this.gridSize && this.cells[x - 1][y + 1].getType() != CellType.MINE) {
          this.cells[x - 1][y + 1].setValue(this.cells[x - 1][y + 1].getValue() + 1);
          this.cells[x - 1][y + 1].setType(CellType.NEXT_TO_MINE);
        }
      }
      if (y - 1 >= 0 && this.cells[x][y - 1].getType() != CellType.MINE) {
        this.cells[x][y - 1].setValue(this.cells[x][y - 1].getValue() + 1);
        this.cells[x][y - 1].setType(CellType.NEXT_TO_MINE);
      }
      if (y + 1 < this.gridSize && this.cells[x][y + 1].getType() != CellType.MINE) {
        this.cells[x][y + 1].setValue(this.cells[x][y + 1].getValue() + 1);
        this.cells[x][y + 1].setType(CellType.NEXT_TO_MINE);
      }
    }
  }

  private void setMineLocatons(int[][] mineLocations) {
    // System.out.println(Arrays.deepToString(this.cells));
    for (int i = 0; i < mineLocations.length; ++i) {
      this.cells[mineLocations[i][0]][mineLocations[i][1]].setValue(-1);
      this.cells[mineLocations[i][0]][mineLocations[i][1]].setType(CellType.MINE);
    }
  }

  private int[][] getUniqueMineLocations(int numberfMines, int gridSize) {
    Set<Integer> uniqueRandomNumbers = new HashSet<>();
    Random random = new Random();
    int randomMax = gridSize * gridSize;
    while (uniqueRandomNumbers.size() < numberfMines) {
      uniqueRandomNumbers.add(random.nextInt(randomMax));
    }
    int[] randomArray = uniqueRandomNumbers.stream().mapToInt(Integer::intValue).toArray();
    int[][] mineLocations = new int[numberfMines][2];
    for (int i = 0; i < numberfMines; ++i) {
      mineLocations[i][0] = randomArray[i] / gridSize;
      mineLocations[i][1] = randomArray[i] % gridSize;
    }
    return mineLocations;
  }

  public void dispaly() {
    System.out.println("Number of mines left:" + this.minesLeft);
    System.out.print("\t");

    for (int i = 0; i < gridSize; ++i) {
      System.out.print(" " + (i + 1) + " ");
    }
    System.out.println("");
    for (int i = 0; i < gridSize; ++i) {
      System.out.print((i + 1) + "\t");
      for (int j = 0; j < gridSize; ++j) {
        if (this.cells[i][j].isRevealed()) {
          if (this.cells[i][j].getType() == CellType.MINE) {
            System.out.print("[*]");

          } else {
            System.out.print("[" + this.cells[i][j].getValue() + "]");
          }
        } else if (this.cells[i][j].isMarked()) {
          System.out.print("[x]");
        } else {
          System.out.print("[_]");
        }
      }
      System.out.println("");
    }

  }
}
