package self.anderson.ballclock;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import self.anderson.ballclock.model.Ball;
import self.anderson.ballclock.model.Track;

import java.util.*;

public class BallClock {


  private final Track MINUTE_TRACK = new Track(5);
  private final Track FIVE_MINUTE_TRACK = new Track(12);
  private final Track HOUR_TRACK = new Track(12);

  private static final long MINUTES_IN_DAY = 60 * 24;

  private Queue<Ball> balls = new LinkedList<>();

  public void start(int numberOfBalls) {

    for (int i = 0; i < numberOfBalls; i++) {
      balls.add(new Ball());
    }

    ImmutableList<Ball> startOrder = ImmutableList.copyOf(balls);

    long minutes = 0l;


    while (minutes == 0 || !startOrder.equals(Lists.newArrayList(balls))) {
      minutes++;

      MINUTE_TRACK.addBall(balls.remove());

      if (MINUTE_TRACK.isFull()) {
        List<Ball> spillage = MINUTE_TRACK.spill();
        FIVE_MINUTE_TRACK.addBall(spillage.remove(0));
        balls.addAll(spillage);
      }

      if (FIVE_MINUTE_TRACK.isFull()) {
        List<Ball> spillage = FIVE_MINUTE_TRACK.spill();
        HOUR_TRACK.addBall(spillage.remove(0));
        balls.addAll(spillage);
      }

      if (HOUR_TRACK.isFull()) {
        List<Ball> spillage = HOUR_TRACK.spill();
        balls.addAll(spillage);
      }

    }

    System.out.println("FOUND days : " + (minutes / MINUTES_IN_DAY) );
  }


  /**
   * Valid numbers are in the range 27 to 127.
   */
  private static List<Pair> getInput() {
    List<Pair> arguments = new ArrayList<>();
    Scanner scanner = new Scanner(System. in);


    String line = scanner. nextLine();
    while (StringUtils.isNotEmpty(line)) {

      Iterable<String> split = Splitter.on(' ').split(line);
      List<String> input = Lists.newArrayList(split);
      Preconditions.checkArgument(input.size() > 0 && input.size() < 3, "Invalid input [" + line + "]");

      Integer a = Integer.parseInt(input.get(0));
      Integer b = (input.size() == 2) ?
          Integer.parseInt(input.get(1)) : null;

      Preconditions.checkArgument(a > 26 && a < 128, "Invalid input [" + a + "], Valid numbers are in the range 27 to 127 ");
      Preconditions.checkArgument(b == null || (b > 26 && b < 128), "Invalid input [" + b + "], Valid numbers are in the range 27 to 127");

      arguments.add(Pair.of(a, b));
      line = scanner. nextLine();
    }

    return arguments;
  }



  public static void main(String[] args) {

//    List<Pair> input = getInput();

    BallClock ballClock = new BallClock();
    ballClock.start(30);

//    input.forEach(
//        System.out::println
//    );


  }

}
