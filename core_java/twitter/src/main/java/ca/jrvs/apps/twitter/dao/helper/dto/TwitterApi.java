package ca.jrvs.apps.twitter.dao.helper.dto;

import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterApi extends Tweet {

  // URI symbols and separators
  private static final String QUERY_SYM;
  private static final String SEP;
  private static final String EQUAL;
  // API URI constant
  private static final String API_BASE_URI;
  // /1.1/statuses/update.json?status=$text&lat=$val&long=$val")
  private static final String POST_PATH;
  private static final String GET_PATH;
  private static final String DELETE_PATH;
  private static final String POST_STATUS;
  private static final String POST_LONGITUDE;
  private static final String POST_LATITUDE;
  private static final String GET_ID;

  private static final PercentEscaper percentEscaper;
  public static final int HTTP_OK = 200;

  static {
    // URI symbols and separators
    QUERY_SYM = "?";
    SEP = "&";
    EQUAL = "=";
    // API URI constant
    API_BASE_URI = "https://api.twitter.com";
    // /1.1/statuses/update.json?status=$text&lat=$val&long=$val")
    POST_PATH = "/1.1/statuses/update.json";
    GET_PATH = "/1.1/statuses/show.json";
    DELETE_PATH = "/1.1/statuses/destroy.json";
    POST_STATUS = "status" + EQUAL;
    POST_LONGITUDE = "long" + EQUAL;
    POST_LATITUDE = "lat" + EQUAL;
    GET_ID = "id" + EQUAL;

    percentEscaper = new PercentEscaper("", false);
  }

  // REST request helper methods
  public static URI post(Tweet tweet) {
    URI query;
    try {
      query = new URI(
          API_BASE_URI + POST_PATH + QUERY_SYM + POST_STATUS + percentEscaper.escape(
              tweet.getText())
              + SEP + POST_LATITUDE + tweet.getCoordinates().latitude() + SEP + POST_LONGITUDE
              + tweet.getCoordinates()
              .longitude());
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Post Tweet argument invalid", e);
    }
    return query;
  }


  public static URI get(Tweet tweet) {
    URI query;
    try {
      query = new URI(
          API_BASE_URI + GET_PATH + QUERY_SYM + GET_ID + tweet.getIdStr());
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Get Tweet argument invalid", e);
    }
    return query;
  }


  public static URI delete(Tweet tweet) {
    URI query;
    PercentEscaper percentEscaper = new PercentEscaper("", false);

    try {
      query = new URI(
          API_BASE_URI + POST_PATH + QUERY_SYM + POST_STATUS + percentEscaper.escape(
              tweet.getText())
              + SEP + POST_LATITUDE + tweet.getCoordinates().latitude() + SEP + POST_LONGITUDE
              + tweet.getCoordinates()
              .longitude());
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Tweet argument invalid", e);
    }
    return query;
  }

}
