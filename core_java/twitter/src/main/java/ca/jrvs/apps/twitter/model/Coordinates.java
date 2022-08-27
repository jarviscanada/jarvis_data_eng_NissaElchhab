package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coordinates implements JsonParser {

  // references Twitter API 1.1 and GeoJson RFC
  public static final float MIN_LONGITUDE = -180f;
  public static final float MAX_LONGITUDE = 180f;
  public static final float MIN_LATITUDE = -90f;
  public static final float MAX_LATITUDE = 90f;
  public static final String DEFAULT_TYPE = "Point";
  // longitude, latitude
  public static final int LONGITUDE = 0;
  public static final int LATITUDE = 1;
  private static final Logger logger = LoggerFactory.getLogger(Coordinates.class);
  // For tweet coordinates field, always `"Point"`
  private final String type;
  private float[] coordinates = new float[2];

  public Coordinates() {
    this.type = DEFAULT_TYPE;
  }

  public Coordinates(float[] coordinates) {
    this.coordinates[0] = coordinates[0];
    this.coordinates[1] = coordinates[1];
    this.type = DEFAULT_TYPE;
  }

  public static Coordinates from(String json) {
    Coordinates unmarshalledObject;
    try {
      unmarshalledObject = JsonParser.unmarshall(json, Coordinates.class);
    } catch (IOException e) {
      throw new IllegalArgumentException("JSON cannot be parsed into object", e);
    }
    return unmarshalledObject;
  }

  public float[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(float[] coordinates) {
    if (isCoordinatesValid(coordinates)) {
      this.coordinates = coordinates;
    } else {
      throw new IllegalArgumentException(
          "Coordinates must be: float[longitude, latitude], where -90 deg <= longitude <= 90 deg and 0 deg <= latitude <= 180 deg");
    }
  }

  public String getType() {
    return type;
  }

  private boolean isCoordinatesValid(float[] coords) {
    boolean isValid = false;
    if (coords == null || coords.length != 2) {
      logger.debug(
          "Coordinates must be a collection of 2 floating-point numbers referencing longitude and latitude");
    } else if (coords[LONGITUDE] >= MIN_LONGITUDE && coords[LONGITUDE] <= MAX_LONGITUDE) {
      logger.debug("Longitude value out of bounds");
    } else if (coords[LATITUDE] >= MIN_LATITUDE && coords[LATITUDE] <= MAX_LATITUDE) {
      logger.debug("Latitude value out of bounds");
    } else {
      isValid = true;
    }
    return isValid;
  }

}
