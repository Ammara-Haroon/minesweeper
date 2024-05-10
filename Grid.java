import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
    if (this.cells[x - 1][y - 1].isRevealed()) {
      return;
    }
    if (this.cells[x - 1][y - 1].isMarked()) {
      this.minesLeft++;
    } else {
      this.minesLeft--;
    }
    this.cells[x - 1][y - 1].toggleMark();
  }

  private List<int[]> getSurroundingCellLocations(int x, int y) {
    ArrayList<int[]> result = new ArrayList<>();
    for (int i = -1; i < 2; ++i) {
      for (int j = -1; j < 2; ++j) {
        int[] cord = { x + i, y + j };
        result.add(cord);
      }
    }
    return result
        .stream()
        .filter(loc -> loc[0] != x || loc[1] != y)
        .filter(loc -> loc[0] < this.gridRows && loc[0] >= 0)
        .filter(loc -> loc[1] < this.gridColumns && loc[1] >= 0)
        .toList();
  }

  public boolean reveal(int row, int col) {
    int x = row - 1;
    int y = col - 1;
    if (this.cells[x][y].isMarked()) {
      this.minesLeft++;
    }
    boolean result = this.cells[x][y].reveal();

    if (this.cells[x][y].getValue() == 0) {
      List<int[]> surroundingCells = getSurroundingCellLocations(x, y);
      surroundingCells
          .stream()
          .filter(loc -> !this.cells[loc[0]][loc[1]].isRevealed())
          .filter(loc -> !this.cells[loc[0]][loc[1]].isMarked())
          .forEach(loc -> this.reveal(loc[0] + 1, loc[1] + 1));
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
      List<int[]> surroundingCells = getSurroundingCellLocations(mineLocations[i][0], mineLocations[i][1]);
      surroundingCells
          .stream()
          .filter(loc -> this.cells[loc[0]][loc[1]].getType() != CellType.MINE)
          .forEach(loc -> {
            this.cells[loc[0]][loc[1]].setValue(this.cells[loc[0]][loc[1]].getValue() + 1);
            this.cells[loc[0]][loc[1]].setType(CellType.NEXT_TO_MINE);
          });
    }
  }

  private void setMineLocatons(int[][] mineLocations) {
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

  public String toString(){
    String finalStr =  "Number of mines left:" + this.minesLeft+"\n\t";

    for (int i = 0; i < this.gridColumns; ++i) {
      finalStr += i < 9 ?  " " + (i + 1) + " ":" " + (i + 1);;
    }
    finalStr +="\n";
    for (int i = 0; i < this.gridRows; ++i) {
      finalStr += (i + 1) + "\t";
      for (int j = 0; j < this.gridColumns; ++j) {
        finalStr += (this.cells[i][j].toString());
      }
          finalStr +="\n";
    }
    return finalStr;
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
