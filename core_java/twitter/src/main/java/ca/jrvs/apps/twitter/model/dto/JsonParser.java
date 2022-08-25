package ca.jrvs.apps.twitter.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public interface JsonParser {
  /**
   * Convert a POJO to JSON string
   *
   * @param object            input Java object
   * @param prettyJson        indentation
   * @param includeNullValues
   * @return JSON string representation
   * @throws JsonProcessingException
   */
  static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws
      JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    if (!includeNullValues) {
      om.setSerializationInclusion(Include.NON_NULL);
    }
    if (prettyJson) {
      om.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return om.writeValueAsString(object);
  }

  /**
   * Convert a JSON string to a POJO
   * @param json
   * @param clazz
   * @return
   * @param <T>
   * @throws IOException
   */
  static <T> T toObjectFromJson(String json, Class<T> clazz) throws IOException {
    ObjectMapper om = new ObjectMapper();
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return (T) om.readValue(json, clazz);
  }


}
