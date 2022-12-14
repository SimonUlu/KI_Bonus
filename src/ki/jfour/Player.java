package ki.jfour;

public enum Player {
  NONE,
  RED,
  BLUE;

  private String value;
  public Player next() {
    switch (this) {
      case NONE: return NONE;
      case RED:  return BLUE;
      case BLUE: return RED;
    }



    throw new RuntimeException("should never happen");
  }



}
