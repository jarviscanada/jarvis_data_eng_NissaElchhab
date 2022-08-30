package ca.jrvs.apps.twitter.dao.helper.dto;

import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO convert to TweetDto, then change DAO to use DTO instead of model
public class TwitterApi {

  public static final int HTTP_OK = 200;
  private static final Logger logger = LoggerFactory.getLogger(TwitterApi.class);
  // URI symbols and separators
  private static final String QUERY_SYM;
  private static final String SEP;
  private static final String EQUAL;
  private static final String VOID_TWEET_TEXT;
  // API URI constant
  private static final String API_BASE_URI;
  // /1.1/statuses/update.json?status=$text&lat=$val&long=$val")
  private static final String POST_PATH;
  private static final String GET_PATH;
  private static final String DELETE_PATH;
  private static final String DELETE_EXTENSION;
  private static final String POST_STATUS;
  private static final String POST_LONGITUDE;
  private static final String POST_LATITUDE;
  private static final String GET_ID;
  private static final PercentEscaper percentEscaper;

  static {
    // URI symbols and separators
    QUERY_SYM = "?";
    SEP = "&";
    EQUAL = "=";
    VOID_TWEET_TEXT = "";
    // API URI constant
    API_BASE_URI = "https://api.twitter.com";
    // /1.1/statuses/update.json?status=$text&lat=$val&long=$val")
    POST_PATH = "/1.1/statuses/update.json";
    GET_PATH = "/1.1/statuses/show.json";
    DELETE_PATH = "/1.1/statuses/destroy";
    DELETE_EXTENSION = ".json";
    POST_STATUS = "status" + EQUAL;
    POST_LONGITUDE = "long" + EQUAL;
    POST_LATITUDE = "lat" + EQUAL;
    GET_ID = "id" + EQUAL;

    percentEscaper = new PercentEscaper("", false);
  }

  // REST request and response helper methods
  // TODO use Optional instead of null
  public Tweet parseResponseHttpOK(HttpResponse response) {
    if (response.getStatusLine().getStatusCode() == HTTP_OK) {
      try {
        return Tweet.from(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        throw new IllegalArgumentException("Argument error while parsing response", e);
      }
    } else {
      return null;
    }
  }

  // TODO use Optional instead of null
  public Tweet parseResponse(HttpResponse response, HttpStatusCode expectedStatusCode) {
    StatusLine responseStatus = response.getStatusLine();
    if (!expectedStatusCode.equals(responseStatus)) {
      try {
        logger.debug(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        logger.debug("Response has no valid entity: failed to convert to String");
        logger.debug(e.toString());
      }
      throw new RuntimeException("Unexpected HTTP status");
    }

    if (response.getEntity() == null) {
      logger.debug("Unexpected response: response has no body");
      throw new RuntimeException("Empty response body");
    }

    String jsonStr;
    try {
      jsonStr = EntityUtils.toString(response.getEntity());
    } catch (IOException e) {
      throw new RuntimeException("Failed to convert to String", e);
    }
    return Tweet.from(jsonStr);
  }

  public static URI buildUri(HttpVerb httpVerb, Tweet tweet) {
    URI queryUri;
    try {
      switch (httpVerb) {
        case GET: {
          queryUri = new URI(
              API_BASE_URI + GET_PATH + QUERY_SYM + GET_ID + tweet.getId().toString());
          break;
        }

        case POST: {
          queryUri = new URI(
              API_BASE_URI + POST_PATH + QUERY_SYM + POST_STATUS + percentEscaper.escape(
                  tweet.getText())
                  + SEP + POST_LATITUDE + tweet.getCoordinates().latitude() + SEP + POST_LONGITUDE
                  + tweet.getCoordinates()
                  .longitude());
          break;
        }

        case DELETE: {
          queryUri = new URI(
              API_BASE_URI + DELETE_PATH + "/" + tweet.getId().toString() + DELETE_EXTENSION);
          break;
        }
        default: {
          throw new IllegalArgumentException("Twitter API v1.1 doesn't support this HTTP verb");
        }
      }

    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Tweet argument invalid", e);
    }
    return queryUri;
  }

  public static URI buildUri(HttpVerb httpVerb, Long id) {
    URI queryUri;
    try {
      switch (httpVerb) {
        case GET: {
          queryUri = new URI(
              API_BASE_URI + GET_PATH + QUERY_SYM + GET_ID + id.toString());
          break;
        }
        // NOTE: POST with no Tweet is not a valid action
        case DELETE: {
          queryUri = new URI(
              API_BASE_URI + DELETE_PATH + "/" + id.toString() + DELETE_EXTENSION);
          break;
        }
        default: {
          throw new IllegalArgumentException("Twitter API v1.1 doesn't support this HTTP verb");
        }
      }
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Tweet argument invalid", e);
    }
    return queryUri;
  }
}
