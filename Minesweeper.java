import java.util.InputMismatchException;
import java.util.Scanner;

public class Minesweeper {
  private static final Scanner scan = new Scanner(System.in);
  private static final StatsFileManager statFile = new StatsFileManager(".\\stats.txt");

  // displays welcome message
  private static void displayWelcomeMessage() {
    System.out.println("********************************************************");
    System.out.println("\t\tWELCOME TO JAVA MINESWEEPER");
    System.out.println("********************************************************\n");
  }

  // a generic function that gets an integer input from console and makes sure it
  // falls in a certain range and displays
  private static int getIntInput(int limitLo, int limitHi, String question) {
    int input = limitLo - 1;
    boolean isValid = true;
    String errMsg = "Input is invalid. Enter a number between " + limitLo + " and " + limitHi;
    while (input < limitLo || input > limitHi) {
      if (!isValid) {
        System.out.println(errMsg);
        System.out.println("");
      }
      isValid = true;
      System.out.print(question);
      try {
        input = scan.nextInt();
      } catch (InputMismatchException e) {
        System.out.print("Only whole numbers are accepted. ");
      }
      scan.nextLine();
      isValid = false;
    }
    return input;
  }

  // gets menu choice from the player
  private static ActionChoice displayStartMenu() {
    int input = getIntInput(1, 3,
        "Choose an option (1, 2 or 3):\n\n 1.Display Game Stats\n 2.Play Minesweeper\n 3.Quit\n\n");
    if (input == 1) {
      return ActionChoice.SHOW_STATS;
    } else if (input == 2) {
      return ActionChoice.PLAY_GAME;
    }
    return ActionChoice.QUIT;
  }

  // use a file reader class to read stats from a file and displays them
  private static void displayGameStats() {
    Stats stats = statFile.readStats();
    System.out.println("*********************************");
    System.out.println("        Game Stats");
    System.out.println("*********************************");

    System.out.println("\nTotal Games Played:\t" + stats.getTotalPlayed());
    System.out.println("        Total Wins:\t" + stats.getWins());
    System.out.printf("    Win Percentage:\t%.1f %%\n", stats.getWinPercentage());
    System.out.println("\n*********************************");

  }

  // a generic function that ask player to choose between two character options
  private static ActionChoice getActionChoiceInput(String question, char choice1, char choice2) {
    char input = ' ';
    boolean isValid = true;
    String errMsg = "Input is invalid. Choose '" + choice1 + "' or '" + choice2 + "'";
    while (input != choice1 && input != choice2) {
      if (!isValid) {
        System.out.println(errMsg);
        System.out.println("");
      }
      isValid = true;
      System.out.print(question);
      try {
        input = scan.next().charAt(0);
      } catch (InputMismatchException e) {
        System.out.print("Only letters are accepted. ");
      }
      scan.nextLine();
      isValid = false;
    }
    if (input == 'm') {
      return ActionChoice.MARK;
    }
    return ActionChoice.REVEAL;
  }

  // sets up the game loop
  private static void playGame() {
    // game setup options : asks player to choose grid size and number of mines
    System.out.println("\n*********************************");
    System.out.println("Let's setup the game for you.\n");
    int rowInput = getIntInput(1, 30, "Enter number of rows (1-30): ");
    int colInput = getIntInput(1, 30, "Enter number of columns (1-30): ");
    int numberOfCells = rowInput * colInput;
    int minesInput = getIntInput(1, numberOfCells, "Enter number of mines (1-" + numberOfCells + "): ");

    Grid grid = new Grid(rowInput, colInput, minesInput);
    boolean isGameLost = false;
    System.out.println("\n********LETS START THE GAME********\n");

    // basic interaction of player : reveal or mark the cell until game is finished
    // or a mine is revealed
    while (!grid.isGameFinished()) {
      System.out.print(grid);// grid.display();
      int row = getIntInput(1, rowInput, "Enter row number (1-" + rowInput + "): ");
      int col = getIntInput(1, colInput, "Enter column number (1-" + colInput + "): ");
      ActionChoice action = getActionChoiceInput("Do you want to mark/unmark (m) it or reveal (r) it? ", 'm', 'r');
      if (action == ActionChoice.MARK) {
        grid.toggleMark(row, col);
      }
      if (action == ActionChoice.REVEAL) {
        if (!grid.reveal(row, col)) {
          isGameLost = true;
          break;
        }
      }
    }

    // update game stats int the stats file
    Stats stats = statFile.readStats();
    System.out.print(grid);// grid.display();

    if (isGameLost) {
      ASCIIArt.displayBlast();
      System.out.println("\nSorry, You Lost ! Want to play again ?\n");

    } else {
      ASCIIArt.displayCongratulations();
      stats.addToWins();
    }
    stats.addToTotalPlayed();
    statFile.writeStats(stats);
  }

  public static void start() {
    displayWelcomeMessage();
    // main menu
    while (true) {
      ActionChoice menuChoice = displayStartMenu();
      if (menuChoice == ActionChoice.SHOW_STATS) {
        displayGameStats();
      } else if (menuChoice == ActionChoice.PLAY_GAME) {
        Minesweeper.playGame();
      } else {
        System.out.println("Good Bye !");
        break;
      }
    }
    scan.close();
  }
}