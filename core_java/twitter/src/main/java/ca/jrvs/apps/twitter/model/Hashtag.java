package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;

public class Hashtag implements JsonParser {

  //
  private int[] indices;

  //
  private String text;

  public Hashtag build(String json) {
    Hashtag hashtag;
    try {
      hashtag = this.parseJson(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("JSON cannot be parsed into object", e);
    }
    return hashtag;
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
