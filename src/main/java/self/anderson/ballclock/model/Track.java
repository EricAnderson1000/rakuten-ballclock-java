package self.anderson.ballclock.model;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Track {

  private List<Ball> balls = new ArrayList<>();

  public Track(int capacity) {
    this.capacity = capacity;
  }

  int capacity;

  public int getCapacity() {
    return capacity;
  }

  public boolean isFull() {
    return balls.size() >= capacity;
  }

  public boolean isNotFull() {
    return !isFull();
  }

  public void addBall(Ball ball) {
    Preconditions.checkState(isNotFull(), "Cannot add another ball to a full Track");
    balls.add(ball);
  }

  public List<Ball> spill() {
    List<Ball> result = balls;
    balls = new ArrayList<>();
    //Always spill in the reverse order
    Collections.reverse(result);
    return result;
  }

}
