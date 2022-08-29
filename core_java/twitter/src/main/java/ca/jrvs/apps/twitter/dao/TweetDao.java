package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.dto.HttpVerb;
import ca.jrvs.apps.twitter.dao.helper.dto.TwitterApi;
import ca.jrvs.apps.twitter.model.Tweet;
import java.net.URI;

public class TweetDao implements CrdDao<Tweet, Long> {

  private final HttpHelper httpHelper;

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
    Tweet responseTweet = TwitterApi.parseResponseHttpOK(httpHelper.httpPost(request));
    //      System.out.println("Failed to Create Tweet");
    return responseTweet;
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
    Tweet responseTweet = TwitterApi.parseResponseHttpOK(httpHelper.httpGet(request));
    //      System.out.println("Failed to Create Tweet");
    return responseTweet;
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
    Tweet responseTweet = TwitterApi.parseResponseHttpOK(httpHelper.httpPost(request));
    //      System.out.println("Failed to Create Tweet");
    return responseTweet;
  }
}
