package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;

public class Hashtag implements JsonParser {

  //
  private int[] indices;

  //
  private String text;

  public Hashtag() {
  }

  public Hashtag(int[] indices, String text) {
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

  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
