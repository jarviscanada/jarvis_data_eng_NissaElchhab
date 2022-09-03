package ca.jrvs.apps.twitter.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coordinates extends ca.jrvs.apps.twitter.model.Coordinates implements
    Validator<ca.jrvs.apps.twitter.model.Coordinates> {

  public static final Logger logger = LoggerFactory.getLogger(Coordinates.class);
  // references Twitter API 1.1 and GeoJson RFC
  public static final float MIN_LONGITUDE = -180.0f;
  public static final float MAX_LONGITUDE = 180.0f;
  public static final float MIN_LATITUDE = -90.0f;
  public static final float MAX_LATITUDE = 90.0f;

  @Override
  public boolean isValid(ca.jrvs.apps.twitter.model.Coordinates coordx) {
    return isNotNull(coordx) && isCoordinatesValid(coordx);
  }

  private boolean isCoordinatesValid(ca.jrvs.apps.twitter.model.Coordinates coordx) {
    if (coordx.getCoordinates() == null || coordx.getCoordinates().length != 2) {
      logger.debug(
          "Coordinates must be a collection of 2 floating-point numbers referencing longitude and latitude");
      return false;
    }
    if (!isLongitudeValid(coordx)) {
      logger.debug("Longitude value out of bounds");
      return false;
    }
    if (!isLatitudeValid(coordx)) {
      logger.debug("Latitude value out of bounds");
      return false;
    }
    return true;
  }


  private boolean isLongitudeValid(ca.jrvs.apps.twitter.model.Coordinates coordx) {
    return Validator.isValueBetweenInclusive(coordx.longitude(), MIN_LONGITUDE, MAX_LONGITUDE);
  }

  private boolean isLatitudeValid(ca.jrvs.apps.twitter.model.Coordinates coordx) {
    return Validator.isValueBetweenInclusive(coordx.latitude(), MIN_LATITUDE, MAX_LATITUDE);
  }


}
