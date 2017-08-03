package self.anderson.ballclock.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonView {

  //Default constructor for Jackson
  public JsonView(){}

  public JsonView(Track minutes, Track five, Track hour, Queue<Ball> main) {

    Function<Ball, Integer> transform =  ball -> ball.getBallNumber() ;

    min = minutes.view().stream().map( transform ).collect(Collectors.toList());
    fiveMine = five.view().stream().map( transform ).collect(Collectors.toList());
    this.hour = hour.view().stream().map( transform ).collect(Collectors.toList());
    this.main = main.stream().map( transform ).collect(Collectors.toList());
  }

  @JsonProperty("Min")
  List<Integer> min;

  @JsonProperty("FiveMin")
  List<Integer> fiveMine;

  @JsonProperty("Hour")
  List<Integer> hour;

  @JsonProperty("Main")
  List<Integer> main;

}
