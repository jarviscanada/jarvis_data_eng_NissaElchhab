package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;

public class Coordinates implements JsonParser {

  // longitude, latitude
  private float[] coordinates;

  // For tweet coordinates field, always `"Point"`
  private String type;

  public Coordinates build(String json) {
    Coordinates coordinates;
    try {
      coordinates = this.parseJson(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("JSON cannot be parsed into object",e);
    }
    return coordinates;
  }

  public float[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(float[] coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
