package ca.jrvs.apps.twitter.model.dto;

import ca.jrvs.apps.twitter.model.UserMention;
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
    final ObjectMapper om = new ObjectMapper();
    if (!includeNullValues) {
      om.setSerializationInclusion(Include.NON_NULL);
    }
    if (prettyJson) {
      om.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return om.writeValueAsString(object);
  }

  /**
   * Convert a POJO to JSON string
   *
   * @param object            input Java object
   * @return pretty JSON string representation
   * @throws JsonProcessingException
   */
  default String toJson(Object object) throws
      JsonProcessingException {
    return JsonParser.toJson(object,true,true);
  }

  /**
   * Convert a POJO to JSON string
   *
   * @param object            input Java object
   * @param prettyJson        indentation
   * @return JSON string representation
   * @throws JsonProcessingException
   */
  default String toJson(Object object, boolean prettyJson) throws
      JsonProcessingException {
    return JsonParser.toJson(object, prettyJson,true);
  }

  /**
   * Convert a JSON string to a POJO
   * @param json
   * @param clazz
   * @return
   * @param <T>
   * @throws IOException
   */
  static <T> T parseJson(String json, Class<T> clazz) throws IOException {
    final ObjectMapper om = new ObjectMapper();
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return (T) om.readValue(json, clazz);
  }

  /**
   * Convert a JSON string to a POJO
   * @param json
   * @return
   * @param <T>
   * @throws IOException
   */
  default <T extends JsonParser>  T parseJson(String json) throws IOException {
    Class<T> clazz = (Class<T>) this.getClass();
    final ObjectMapper om = new ObjectMapper();
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return (T) om.readValue(json, clazz);
  }
}
