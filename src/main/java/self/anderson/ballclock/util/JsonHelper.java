package self.anderson.ballclock.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

  private JsonFactory factory = new JsonFactory();

  ObjectMapper mapper = new ObjectMapper();

  public String toJson(Object object) {

    try {
      return mapper.writeValueAsString(object);

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

  }


}
