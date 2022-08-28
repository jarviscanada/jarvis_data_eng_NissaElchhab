package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

public class Hashtag implements JsonParser {

  //
  private String text;
  //
  private int[] indices;

  public Hashtag() {
  }

  public Hashtag(String text, int[] indices) {
    this.indices = indices;
    this.text = text;
  }

  public static Hashtag from(String json) {
    Hashtag unmarshalledObject;
    try {
      unmarshalledObject = JsonParser.unmarshall(json, Hashtag.class);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "JSON cannot be parsed into object", e);
    }
    return unmarshalledObject;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  @JsonProperty("text")
  public void setText(String text) {
    this.text = text;
  }

  @JsonProperty("indices")
  public int[] getIndices() {
    return indices;
  }

  @JsonProperty("indices")
  public void setIndices(int[] indices) {
    this.indices = indices;
  }


}
