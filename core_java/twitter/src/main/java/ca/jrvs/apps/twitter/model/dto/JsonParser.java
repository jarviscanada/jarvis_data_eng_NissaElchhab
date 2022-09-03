package ca.jrvs.apps.twitter.model.dto;

import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
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
  static String marshall(Object object, boolean prettyJson, boolean includeNullValues) throws
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
   * Convert a JSON string to a POJO
   *
   * @param json
   * @param clazz
   * @param <T>
   * @return
   * @throws IOException
   */
  static <T> T parseJson(String json, Class<T> clazz) throws IOException {
    final ObjectMapper om = new ObjectMapper();
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return om.readValue(json, clazz);
  }

  /**
   * @param json
   * @return Tweet
   * @throws IOException
   */
  static Tweet parseJsonTweetWithFilter(String json, String[] fields)
      throws IOException {
    final ObjectMapper om = new ObjectMapper();
    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    TweetDeserializer tweetDeserializer = new TweetDeserializer(fields);
    SimpleModule tweetModule = new SimpleModule("TweetDeserializer",
        new Version(1, 0, 0, "SNAPSHOT", null, null));
    tweetModule.addDeserializer(Tweet.class, tweetDeserializer);
    om.registerModule(tweetModule);
    return om.readValue(json, Tweet.class);
  }

  /**
   * Convert a POJO to JSON string
   *
   * @return pretty JSON string representation
   * @throws JsonProcessingException
   */
  default String toJson() throws
      JsonProcessingException {
    return JsonParser.marshall(this, true, true);
  }

  /**
   * Convert a POJO to JSON string
   *
   * @param prettyJson indentation
   * @return JSON string representation
   * @throws JsonProcessingException
   */
  default String toJson(boolean prettyJson, boolean includeNullValues) throws
      JsonProcessingException {
    return JsonParser.marshall(this, prettyJson, includeNullValues);
  }

//  /**
//   * Convert a JSON string to a POJO
//   *
//   * @param json
//   * @param <T>
//   * @return
//   * @throws IOException
//   */
//  default <T extends JsonParser> T parseJson(String json) throws IOException {
//    Class<T> clazz = (Class<T>) this.getClass();
//    final ObjectMapper om = new ObjectMapper();
//    om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    return om.readValue(json, clazz);
//  }

}
