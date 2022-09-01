package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coordinates implements JsonParser {

  // references Twitter API 1.1 and GeoJson RFC
  public static final String DEFAULT_TYPE = "Point";
  // longitude, latitude
  public static final int LONGITUDE = 0;
  public static final int LATITUDE = 1;
  // For tweet coordinates field, always `"Point"`
  private String type;
  private float[] coordinates = new float[2];

  public Coordinates() {
    this.type = DEFAULT_TYPE;
  }

//  public Coordinates(float[] coordinates) {
//    this.coordinates[0] = coordinates[0];
//    this.coordinates[1] = coordinates[1];
//    this.type = DEFAULT_TYPE;
//  }

  public Coordinates(float[] coordinates, String type) {
    this.coordinates = coordinates;
    this.type = type;
  }

  public Coordinates(float longitude, float latitude) {
    this.coordinates[LONGITUDE] = longitude;
    this.coordinates[LATITUDE] = latitude;
    this.type = DEFAULT_TYPE;
  }

  public static Coordinates from(String json) {
    Coordinates unmarshalledObject;
    try {
      unmarshalledObject = JsonParser.parseJson(json, Coordinates.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("JSON cannot be parsed into object", e);
    }
    return unmarshalledObject;
  }

  @JsonProperty("coordinates")
  public float[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(float[] coordinates) {
      this.coordinates = coordinates;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  public float longitude() {
    return coordinates[LONGITUDE];
  }

  public float latitude() {
    return coordinates[LATITUDE];
  }


}
