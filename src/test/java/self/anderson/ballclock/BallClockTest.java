package self.anderson.ballclock;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class BallClockTest {

  BallClock ballClock = new BallClock();

  private static final Optional<Integer> MISSING = Optional.empty();

  //Lower
  @Test(expected = IllegalArgumentException.class)
  public void testSimple_26() {

    ballClock.start(26, MISSING);
  }

  @Test
  public void testSimple_27() {

    ClockResult result = ballClock.start(27, MISSING);

    Assert.assertEquals(23,  result.getDays());
  }

  //Upper
  @Test(expected = IllegalArgumentException.class)
  public void testSimple_128() {

    ballClock.start(128, MISSING);
  }

  @Test
  public void testSimple_127() {

    ClockResult result = ballClock.start(127, MISSING);

    Assert.assertEquals(2415,  result.getDays());
  }

  @Test
  public void testSimple_30() {

    ClockResult result = ballClock.start(30, MISSING);

    Assert.assertEquals(15,  result.getDays());
  }

  @Test
  public void testSimple_45() {

    ClockResult result = ballClock.start(45, MISSING);

    Assert.assertEquals(378,  result.getDays());
  }

  @Test
  public void testHaltAt_325() {
    ClockResult result = ballClock.start(30, Optional.of(325));

    String expected = "{\"Min\":[],\"FiveMin\":[22,13,25,3,7],\"Hour\":[6,12,17,4,15],\"Main\"" +
        ":[11,5,26,18,2,30,19,8,24,10,29,20,16,21,28,1,23,14,27,9]}";
    Assert.assertEquals(expected,  result.printMessage());
  }

}
