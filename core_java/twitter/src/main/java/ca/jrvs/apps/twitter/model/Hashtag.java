package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.util.List;

public class Hashtag implements JsonParser {

  //
  private String text;
  //
  private List<Integer> indices;

  public Hashtag() {
  }

  public Hashtag(String text, List<Integer> indices) {
    this.indices = indices;
    this.text = text;
  }

  public static Hashtag from(String json) {
    Hashtag unmarshalledObject;
    try {
      unmarshalledObject = JsonParser.unmarshall(json, Hashtag.class);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "JSON cannot be parsed into object Hashtag", e);
    }
    return unmarshalledObject;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @JsonProperty("indices")
  public List<Integer> getIndices() {
    return indices;
  }

  public void setIndices(List<Integer> indices) {
    this.indices = indices;
  }


}
