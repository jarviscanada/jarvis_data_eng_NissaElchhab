package ca.jrvs.apps.twitter.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coordinates extends ca.jrvs.apps.twitter.model.Coordinates implements
    Validator<ca.jrvs.apps.twitter.model.Coordinates> {

  public static final Logger logger = LoggerFactory.getLogger(Coordinates.class);
  // references Twitter API 1.1 and GeoJson RFC
  public static final float MIN_LONGITUDE = -180f;
  public static final float MAX_LONGITUDE = 180f;
  public static final float MIN_LATITUDE = -90f;
  public static final float MAX_LATITUDE = 90f;

  @Override
  public boolean isValid(ca.jrvs.apps.twitter.model.Coordinates c) {
    return Validator.isNotNull(c) && isCoordinatesValid();
  }

  private boolean isCoordinatesValid() {
    if (getCoordinates() == null || getCoordinates().length != 2) {
      logger.debug(
          "Coordinates must be a collection of 2 floating-point numbers referencing longitude and latitude");
      return false;
    }
    if (!isLongitudeValid()) {
      logger.debug("Longitude value out of bounds");
      return false;
    }
    if (!isLatitudeValid()) {
      logger.debug("Latitude value out of bounds");
      return false;
    }
    return true;
  }


  private boolean isLongitudeValid() {
    return isBetweenInclusive(this.longitude(), MIN_LONGITUDE, MAX_LONGITUDE);
  }

  private boolean isLatitudeValid() {
    return isBetweenInclusive(this.longitude(), MIN_LATITUDE, MAX_LATITUDE);
  }


}
