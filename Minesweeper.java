import java.util.Scanner;

public class Minesweeper {
  public static void start() {
    Scanner scan = new Scanner(System.in);

    System.out.println("Minesweeper");
    int gridSize = 5;
    int numberfMines = 5;
    Grid grid = new Grid(gridSize, numberfMines);
    for (int i = 0; i < 25; ++i) {
      grid.dispaly();
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
          System.out.println("BOOM");
          grid.dispaly();
          break;
        }
      }
    }
    scan.close();
  }
}
