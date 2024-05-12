import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//this class saves statistics of the game and manages file reads and writes
public class StatsFileManager {
  private String filePath = ".\\stats.txt";

  public StatsFileManager(String filePath) {
    this.filePath = filePath;
    try {
      File statsFile = new File(filePath);
      statsFile.createNewFile();
    } catch (IOException e) {
      System.out.println("Error opening/creating game stats file. Try again later.");
      e.printStackTrace();
    }
  }

  public void writeStats(Stats stats) {
    try {
      FileWriter statsWriter = new FileWriter(this.filePath);
      statsWriter.write("total=" + stats.getTotalPlayed() + "\nwins=" + stats.getWins());
      statsWriter.close();
    } catch (IOException e) {
      System.out.println("srror");
      e.printStackTrace();
    }

  }

  public Stats readStats() {
    try {
      FileReader statsReader = new FileReader(this.filePath);
      String fileContent = "";
      int ch;
      while ((ch = statsReader.read()) != -1) {
        fileContent += (char) ch;
      }
      statsReader.close();

      String[] fileLines = fileContent.split("\n");
      String[] valuesTotal = fileLines[0].split("=");
      String[] valuesWins = fileLines[1].split("=");

      return (new Stats(Long.parseLong(valuesTotal[1]), Long.parseLong(valuesWins[1])));
    } catch (Exception e) {
      System.out.println("Error in reading file in this format.");
      e.printStackTrace();
    }
    return new Stats(0, 0);

  }
}