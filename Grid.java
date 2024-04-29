import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class Grid {
  int gridSize = 10;
  int gridRows = 10;
  int gridColumns = 10;
  int numberOfMines = 10;
  Cell[][] cells;
  int minesLeft = 10;

  public Grid(int gridRows, int gridColumns, int numberfMines) {
    this.gridRows = gridRows;
    this.gridColumns = gridColumns;
    this.numberOfMines = numberOfMines;
    this.minesLeft = numberfMines;
    this.cells = createCells(gridRows, gridColumns);
    int[][] mineLocations = getUniqueMineLocations(numberfMines, gridRows, gridColumns);
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

  private ArrayList<Integer[]> getSurroundingCellLocations(int x, int y) {
    ArrayList<Integer[]> result = new ArrayList<>();
    if (y + 1 < this.gridColumns) {
      Integer[] coord = { x, y + 1 };
      result.add(coord);
    }
    if (y - 1 >= 0) {
      Integer[] coord = { x, y - 1 };
      result.add(coord);
    }
    if (x + 1 < this.gridRows) {
      Integer[] cord = { x + 1, y };
      result.add(cord);
      if (y + 1 < this.gridColumns) {
        Integer[] coord = { x + 1, y + 1 };
        result.add(coord);
      }
      if (y - 1 >= 0) {
        Integer[] coord = { x + 1, y - 1 };
        result.add(coord);
      }
    }
    if (x - 1 >= 0) {
      Integer[] cord = { x - 1, y };
      result.add(cord);
      if (y + 1 < this.gridColumns) {
        Integer[] coord = { x - 1, y + 1 };
        result.add(coord);
      }
      if (y - 1 >= 0) {
        Integer[] coord = { x - 1, y - 1 };
        result.add(coord);
      }
    }
    return result;
  }

  public boolean reveal(int row, int col) {
    int x = row - 1;
    int y = col - 1;
    boolean result = this.cells[x][y].reveal();
    if (this.cells[x][y].getValue() == 0) {
      if (y + 1 < this.gridColumns && !this.cells[x][y + 1].isRevealed()) {
        this.reveal(row, col + 1);
      }
      if (y - 1 >= 0 && !this.cells[x][y - 1].isRevealed()) {
        this.reveal(row, col - 1);
      }
      if (x + 1 < this.gridRows) {
        if (!this.cells[x + 1][y].isRevealed()) {
          this.reveal(row + 1, col);
        }
        if (y + 1 < this.gridColumns && !this.cells[x + 1][y + 1].isRevealed()) {
          this.reveal(row + 1, col + 1);
        }
        if (y - 1 >= 0 && !this.cells[x + 1][y - 1].isRevealed()) {
          this.reveal(row + 1, col - 1);
        }
      }
      if (x - 1 >= 0) {
        if (!this.cells[x - 1][y].isRevealed()) {
          this.reveal(row - 1, col);
        }
        if (y + 1 < this.gridColumns && !this.cells[x - 1][y + 1].isRevealed()) {
          this.reveal(row - 1, col + 1);
        }
        if (y - 1 >= 0 && !this.cells[x - 1][y - 1].isRevealed()) {
          this.reveal(row - 1, col - 1);
        }
      }
    }
    return result;
  }

  private Cell[][] createCells(int gridRows, int gridColumns) {
    Cell[][] cells = new Cell[gridRows][gridColumns];
    for (int i = 0; i < gridRows; ++i) {
      for (int j = 0; j < gridColumns; ++j) {
        cells[i][j] = new Cell();
      }
    }
    return cells;
  }

  private void setMineSurroundings(int[][] mineLocations) {

    for (int i = 0; i < mineLocations.length; ++i) {
      int x = mineLocations[i][0];
      int y = mineLocations[i][1];

      if (x + 1 < this.gridRows) {
        if (this.cells[x + 1][y].getType() != CellType.MINE) {
          this.cells[x + 1][y].setValue(this.cells[x + 1][y].getValue() + 1);
          this.cells[x + 1][y].setType(CellType.NEXT_TO_MINE);
        }
        if (y - 1 >= 0 && this.cells[x + 1][y - 1].getType() != CellType.MINE) {
          this.cells[x + 1][y - 1].setValue(this.cells[x + 1][y - 1].getValue() + 1);
          this.cells[x + 1][y - 1].setType(CellType.NEXT_TO_MINE);
        }
        if (y + 1 < this.gridColumns && this.cells[x + 1][y + 1].getType() != CellType.MINE) {
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
        if (y + 1 < this.gridColumns && this.cells[x - 1][y + 1].getType() != CellType.MINE) {
          this.cells[x - 1][y + 1].setValue(this.cells[x - 1][y + 1].getValue() + 1);
          this.cells[x - 1][y + 1].setType(CellType.NEXT_TO_MINE);
        }
      }
      if (y - 1 >= 0 && this.cells[x][y - 1].getType() != CellType.MINE) {
        this.cells[x][y - 1].setValue(this.cells[x][y - 1].getValue() + 1);
        this.cells[x][y - 1].setType(CellType.NEXT_TO_MINE);
      }
      if (y + 1 < this.gridColumns && this.cells[x][y + 1].getType() != CellType.MINE) {
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

  private int[][] getUniqueMineLocations(int numberfMines, int gridRows, int gridColumns) {
    Set<Integer> uniqueRandomNumbers = new HashSet<>();
    Random random = new Random();
    int randomMax = gridRows * gridColumns;
    while (uniqueRandomNumbers.size() < numberfMines) {
      uniqueRandomNumbers.add(random.nextInt(randomMax));
    }
    int[] randomArray = uniqueRandomNumbers.stream().mapToInt(Integer::intValue).toArray();
    int[][] mineLocations = new int[numberfMines][2];
    for (int i = 0; i < numberfMines; ++i) {
      mineLocations[i][0] = randomArray[i] / gridColumns;
      mineLocations[i][1] = randomArray[i] % gridColumns;
    }
    return mineLocations;
  }

  public void display() {
    System.out.println("Number of mines left:" + this.minesLeft);
    System.out.print("\t");

    for (int i = 0; i < this.gridColumns; ++i) {
      String str;
      if (i < 9) {

        str = " " + (i + 1) + " ";
      } else {
        str = " " + (i + 1);
      }
      System.out.print(str);
    }
    System.out.println("");
    for (int i = 0; i < this.gridRows; ++i) {
      System.out.print((i + 1) + "\t");
      for (int j = 0; j < this.gridColumns; ++j) {
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

  public boolean isGameFinished() {
    if (minesLeft != 0) {
      return false;
    }

    for (int i = 0; i < this.gridRows; ++i) {
      for (int j = 0; j < this.gridColumns; ++j) {
        if (this.cells[i][j].getType() == CellType.MINE) {
          if (!this.cells[i][j].isMarked()) {
            return false;
          }
        } else if (!this.cells[i][j].isRevealed()) {
          return false;
        }
      }
    }
    return true;
  }
}
