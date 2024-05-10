public class Stats {
  private long wins;
  private long totalPlayed;

  public Stats(long totalPlayed, long wins) {
    this.wins = wins;
    this.totalPlayed = totalPlayed;
  }

  public long getWins() {
    return this.wins;
  }

  public long getTotalPlayed() {
    return this.totalPlayed;
  }

  public void addToTotalPlayed() {
    this.totalPlayed++;
  }

  public void addToWins() {
    this.wins++;
  }

  public double getWinPercentage() {
    if (this.totalPlayed != 0) {
      return ((double) this.wins / this.totalPlayed) * 100;
    }
    return 0;
  }
}
