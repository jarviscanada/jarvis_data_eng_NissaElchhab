package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.dto.TwitterApi;
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

  private HttpHelper httpHelper;

  public TweetDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  /**
   * Create an entity(Tweet) to the underlying storage
   *
   * @param tweet entity that to be created
   * @return created entity
   */
  @Override
  public Tweet create(Tweet tweet) {
    URI request = TwitterApi.post(tweet);
    Tweet responseTweet = TwitterApi.parseResponse(httpHelper.httpPost(request));
    if (responseTweet != null) {
      return responseTweet;
    } else {
//      System.out.println("Failed to Create Tweet");
      return null;
    }
  }

  /**
   * Find an entity(Tweet) by its id
   *
   * @param tweetId entity id
   * @return Tweet entity
   */
  @Override
  public Tweet findById(Long tweetId) {
    URI request = TwitterApi.get(tweetId);
    Tweet responseTweet = TwitterApi.parseResponse(httpHelper.httpPost(request));
    if (responseTweet != null) {
      return responseTweet;
    } else {
//      System.out.println("Failed to Create Tweet");
      return null;
    }
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
