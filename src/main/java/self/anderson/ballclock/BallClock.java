package self.anderson.ballclock;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import self.anderson.ballclock.model.Ball;
import self.anderson.ballclock.model.JsonView;
import self.anderson.ballclock.model.Track;
import self.anderson.ballclock.util.JsonHelper;
import self.anderson.ballclock.util.NumberUtil;

import java.util.*;

public class BallClock {


  private final Track MINUTE_TRACK = new Track(5);
  private final Track FIVE_MINUTE_TRACK = new Track(12);
  private final Track HOUR_TRACK = new Track(12);
  private Queue<Ball> balls = new LinkedList<>();

  /**
   *
   * Starts the clock at 1:00 and starts moving balls. Once for each minute.
   *
   * @param numberOfBalls
   * @param haltAtMinute
   * @return The number days to complete a cycle, or the time it takes for all balls to return to their original order.
   *          Unless the machine is halted early by the optional haltAtMinute param. In which it will return -1.
   */
  public ClockResult start(int numberOfBalls, Optional<Integer> haltAtMinute) {

    Preconditions.checkArgument(numberOfBalls > 26 && numberOfBalls < 128, "Number of balls must be between 27 and 127 inclusive");

    for (int i = 1; i <= numberOfBalls; i++) {
      balls.add(new Ball(i));
    }

    List<Ball> originalOrder = ImmutableList.copyOf(balls);

    //Prime
    long minutes = 0l;

    while (minutes == 0 || !originalOrder.equals(balls)) {
      //Terminate early
      if (haltAtMinute.isPresent() && haltAtMinute.get() == minutes) {
        break;
      }

      //Drop the ball
      minutes++;
      MINUTE_TRACK.addBall(balls.remove());

      if (MINUTE_TRACK.isFull()) {
        List<Ball> spillage = MINUTE_TRACK.spill();
        Ball ball = spillage.remove(0);
        FIVE_MINUTE_TRACK.addBall(ball);
        balls.addAll(spillage);
      }

      if (FIVE_MINUTE_TRACK.isFull()) {
        List<Ball> spillage = FIVE_MINUTE_TRACK.spill();
        Ball ball = spillage.remove(0);
        HOUR_TRACK.addBall(ball);
        balls.addAll(spillage);
      }

      if (HOUR_TRACK.isFull()) {
        List<Ball> spillage = HOUR_TRACK.spill();
        Ball ball = spillage.remove(0);
        balls.addAll(spillage);
        balls.add(ball);
      }
    }

    String representation = buildJson();
    return new ClockResult(minutes, representation, numberOfBalls, haltAtMinute);
  }

  private String buildJson() {
    JsonView jsonView = new JsonView(MINUTE_TRACK, FIVE_MINUTE_TRACK, HOUR_TRACK, balls);
    return new JsonHelper().toJson(jsonView);
  }

  /**
   * Valid number balls are in the range 27 to 127.
   */
  private static List<Pair<Integer, Optional<Integer>>>  getInput() {
    List<Pair<Integer, Optional<Integer>>> arguments = new ArrayList<>();
    Scanner scanner = new Scanner(System. in);


    String line = scanner. nextLine();
    while (StringUtils.isNotEmpty(line)) {

      Iterable<String> split = Splitter.on(' ').split(line);
      List<String> input = Lists.newArrayList(split);
      Preconditions.checkArgument(input.size() > 0 && input.size() < 3, "Invalid input [" + line + "]");

      Optional<Integer> a = NumberUtil.parse(input.get(0));
      Preconditions.checkArgument(a.isPresent(), "Invalid input : " + input.get(0));
      Preconditions.checkArgument(a.get() > 26 && a.get() < 128, "Invalid input [" + a + "], Valid numbers are in the range 27 to 127 ");

      Optional<Integer> b = input.size() == 2 ?
          NumberUtil.parse(input.get(1)) :
          Optional.empty();

      arguments.add(Pair.of(a.get(), b));
      line = scanner. nextLine();
    }

    return arguments;
  }

  public static void main(String[] args) {

    List<Pair<Integer, Optional<Integer>>> input = getInput();

    input.forEach( pair -> {
      BallClock ballClock = new BallClock();
      ClockResult result = ballClock.start(pair.getLeft(), pair.getRight());
      System.out.println(result.printMessage());
   });
  }

}
