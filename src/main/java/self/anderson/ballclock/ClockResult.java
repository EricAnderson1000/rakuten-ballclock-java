package self.anderson.ballclock;

import java.util.Optional;

public class ClockResult {

  private static final long MINUTES_IN_DAY = 60 * 24;

  long minutes;

  String representation;

  int balls;

  Optional<Integer> haltAtMinute;

  public ClockResult(long minutes, String representation, int balls, Optional<Integer> haltAtMinute) {
    this.minutes = minutes;
    this.representation = representation;
    this.balls = balls;
    this.haltAtMinute = haltAtMinute;
  }

  public int getDays() {
    return Math.toIntExact(minutes / MINUTES_IN_DAY);
  }

  public String printMessage() {

    return haltAtMinute.isPresent() ?
        representation :
        balls + " balls cycle after " + getDays() + " days.";
  }

}
