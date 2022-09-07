package ca.jrvs.apps.twitter.example;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonParser {

  /**
   * Convert a POJO to JSON string
   *
   * @param object            input Java object
   * @param prettyJson        indentation
   * @param includeNullValues
   * @return JSON string representation
   * @throws JsonProcessingException
   */
  public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws
      JsonProcessingException {
    ObjectMapper m = new ObjectMapper();
    if (!includeNullValues) {
      m.setSerializationInclusion(Include.NON_NULL);
    }
    if (prettyJson) {
      m.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return m.writeValueAsString(object);
  }


  public static <T> T toObjectFromJson(String json, Class<T> clazz) throws IOException {
    ObjectMapper m = new ObjectMapper();
//    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return m.readValue(json, clazz);
  }

}
