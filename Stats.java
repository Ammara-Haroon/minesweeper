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
}
