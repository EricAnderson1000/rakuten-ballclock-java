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

  private static final long MINUTES_IN_DAY = 60 * 24;

  private Queue<Ball> balls = new LinkedList<>();


  public int start(int numberOfBalls, Optional<Integer> haltAtMinute) {

    for (int i = 1; i <= numberOfBalls; i++) {
      balls.add(new Ball(i));
    }

    List<Ball> originalOrder = ImmutableList.copyOf(balls);

    //Prime
    long minutes = 1l;
    MINUTE_TRACK.addBall(balls.remove());

    while (!originalOrder.equals(balls)) {
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

      if (haltAtMinute.isPresent() && haltAtMinute.get() == minutes) {
        JsonView jsonView = new JsonView(MINUTE_TRACK, FIVE_MINUTE_TRACK, HOUR_TRACK, balls);
        System.out.println(new JsonHelper().toJson(jsonView));
        return -1;
      }
    }

    int days = Math.toIntExact(minutes / MINUTES_IN_DAY);
    return days;
  }

  /**
   * Valid numbers are in the range 27 to 127.
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

      Optional<Integer> b = NumberUtil.parse(input.get(1));

      arguments.add(Pair.of(a.get(), b));
      line = scanner. nextLine();
    }

    return arguments;
  }

  public static void main(String[] args) {

    List<Pair<Integer, Optional<Integer>>> input = getInput();

    input.forEach( pair -> {
      BallClock ballClock = new BallClock();
      int result = ballClock.start(pair.getLeft(), pair.getRight());
      if (result != -1) {
        System.out.println(pair.getLeft() + " balls cycle after " + result + " days.");
      }
    });
  }

}
