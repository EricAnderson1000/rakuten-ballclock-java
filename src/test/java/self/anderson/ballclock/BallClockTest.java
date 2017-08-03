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

    int result = ballClock.start(27, MISSING);

    Assert.assertEquals(23,  result);
  }

  //Upper
  @Test(expected = IllegalArgumentException.class)
  public void testSimple_128() {

    ballClock.start(128, MISSING);
  }

  @Test
  public void testSimple_127() {

    int result = ballClock.start(127, MISSING);

    Assert.assertEquals(2415,  result);
  }

  @Test
  public void testSimple_30() {

    int result = ballClock.start(30, MISSING);

    Assert.assertEquals(15,  result);
  }

  @Test
  public void testSimple_45() {

    int result = ballClock.start(45, MISSING);

    Assert.assertEquals(378,  result);
  }

}
