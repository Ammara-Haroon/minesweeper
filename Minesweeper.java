import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Minesweeper {
  public static void start() {
    StatsFileManager statFile = new StatsFileManager(".\\stats.txt");
    Stats stats = statFile.readStats();
    System.out.println("Wins: " + stats.getWins() + " Total Played: " + stats.getTotalPlayed());
    Scanner scan = new Scanner(System.in);

    System.out.println("Minesweeper");
    int gridSize = 5;
    int numberfMines = 1;
    Grid grid = new Grid(gridSize, numberfMines);
    boolean isGameLost = false;
    while (!grid.isGameFinished()) {
      grid.display();
      System.out.println("Enter row number:");
      int row = scan.nextInt();
      System.out.println("Enter column number:");
      int col = scan.nextInt();
      System.out.println("Do you want to mark/unmark (m) it or reveal (r) it?");
      char choice = scan.next().charAt(0);
      if (choice == 'm') {
        grid.toggleMark(row, col);
      }
      if (choice == 'r') {
        if (!grid.reveal(row, col)) {
          isGameLost = true;
          break;
        }
      }
    }

    scan.close();

    if (isGameLost) {
      System.out.println("BOOM");
    } else {
      System.out.println("congratulations you won");
      stats.addToWins();
    }
    grid.display();
    stats.addToTotalPlayed();
    statFile.writeStats(stats);
  }
}
