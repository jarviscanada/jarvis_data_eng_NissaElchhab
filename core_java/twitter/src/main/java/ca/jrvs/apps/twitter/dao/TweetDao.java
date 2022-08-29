package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

public class TweetDao implements CrdDao<Tweet,Long> {
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  public TweetDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  /**
   * Create an entity(Tweet) to the underlying storage
   *
   * @param entity entity that to be created
   * @return created entity
   */
  @Override
  public Tweet create(Tweet entity) {
    String text =         entity.getText();
    float longitude = entity.getCoordinates().longitude();
    float latitude = entity.getCoordinates().latitude();
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    URI uri = null;
    try {
      uri = new URI("https://api.twitter.com/1.1/statuses/update.json?status=" +
          percentEscaper.escape(text) + "&lat=36.7821120598956&long=-123.400612831116");
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    HttpResponse response = httpHelper.httpPost(uri);
    if (response.getStatusLine().getStatusCode() == HTTP_OK) {
      try {
        return Tweet.from(EntityUtils.toString(response.getEntity()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      return null;
    }
  }

  /**
   * Find an entity(Tweet) by its id
   *
   * @param aLong entity id
   * @return Tweet entity
   */
  @Override
  public Tweet findById(Long aLong) {
    Tweet tweet;
    return null ;// tweet;
  }

  /**
   * Delete an entity(Tweet) by its ID
   *
   * @param aLong of the entity to be deleted
   * @return deleted entity
   */
  @Override
  public Tweet deleteById(Long aLong) {
    Tweet tweet = findById(aLong);
    return tweet;
  }
}
