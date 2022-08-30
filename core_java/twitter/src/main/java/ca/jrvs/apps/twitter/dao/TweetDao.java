package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.dto.HttpStatusCode;
import ca.jrvs.apps.twitter.dao.helper.dto.HttpVerb;
import ca.jrvs.apps.twitter.dao.helper.dto.TwitterApi;
import ca.jrvs.apps.twitter.model.Tweet;
import java.net.URI;

public class TweetDao implements CrdDao<Tweet, Long> {

  private final HttpHelper httpHelper;
  private static final TwitterApi twitterApi = new TwitterApi();

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
    URI request = TwitterApi.buildUri(HttpVerb.POST, tweet);
    return twitterApi.parseResponseHttpOK(httpHelper.httpPost(request));
    //      System.out.println("Failed to Create Tweet");
  }

  /**
   * Find an entity(Tweet) by its id
   *
   * @param tweetId entity id
   * @return Tweet entity
   */
  @Override
  public Tweet findById(Long tweetId) {
    URI request = TwitterApi.buildUri(HttpVerb.GET, tweetId);
    return twitterApi.parseResponse(httpHelper.httpGet(request), HttpStatusCode.OK);
    //      System.out.println("Failed to Create Tweet");
  }

  /**
   * Delete an entity(Tweet) by its ID
   *
   * @param tweetId of the entity to be deleted
   * @return deleted entity
   */
  @Override
  public Tweet deleteById(Long tweetId) {
    URI request = TwitterApi.buildUri(HttpVerb.DELETE, tweetId);
    return twitterApi.parseResponse(httpHelper.httpPost(request), HttpStatusCode.OK);
    //      System.out.println("Failed to Create Tweet");
  }
}
