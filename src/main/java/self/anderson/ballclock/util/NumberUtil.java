package self.anderson.ballclock.util;

import java.util.Optional;

public class NumberUtil {

  public static final Optional<Integer> parse(String in) {
    try {
      return Optional.of(Integer.parseInt(in));
    } catch (NumberFormatException nfe) {
      return Optional.empty();
    }
  }

}
