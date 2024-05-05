
public class Cell {
  private boolean revealed = false;
  private boolean marked = false;
  private int value = 0;
  private CellType type = CellType.EMPTY;

  public void setValue(int val) {
    this.value = val;
  }

  public void toggleMark() {
    if (!this.isRevealed()) {
      this.marked = !this.marked;
    }
  }

  public boolean reveal() {
    this.revealed = true;
    this.marked = false;
    if (this.type == CellType.MINE) {
      return false;
    }
    return true;

  }

  public boolean isRevealed() {
    return this.revealed;
  }

  public boolean isMarked() {
    return this.marked;
  }

  public int getValue() {
    return this.value;
  }

  public void setType(CellType type) {
    this.type = type;
  }

  public CellType getType() {
    return this.type;
  }
  public String toString(){
    if (this.isRevealed()) {
          if (this.getType() == CellType.MINE) {
            return "[*]";
          }
          return "[" + this.getValue() + "]";
          
    } else if (this.isMarked()) {
          return "[x]";
    } 
    return "[_]";
      
  }
}
