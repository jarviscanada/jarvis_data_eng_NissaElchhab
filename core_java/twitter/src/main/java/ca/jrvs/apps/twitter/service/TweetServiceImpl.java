package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.validation.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public class TweetServiceImpl implements Service {

  private final CrdDao<Tweet, Long> dao;
  private final Validator<Tweet> tweetValidator;

  public TweetServiceImpl(CrdDao<Tweet, Long> dao) {
    this.dao = dao;
    this.tweetValidator = new ca.jrvs.apps.twitter.service.validation.Tweet();
  }

  public TweetServiceImpl(CrdDao<Tweet, Long> dao,
      Validator<Tweet> tweetValidator) {
    this.dao = dao;
    this.tweetValidator = tweetValidator;
  }

  /**
   * Validate and post a user input Tweet
   *
   * @param tweet tweet to be created
   * @return created tweet
   * @throws IllegalArgumentException if text exceeds max number of allowed characters or lat/long
   *                                  out of range
   */
  @Override
  public Tweet postTweet(Tweet tweet) {
    if (!tweetValidator.isValid(tweet)) {
      throw new IllegalArgumentException("tweet's text is null or too long");
    }
    Tweet postedTweet = dao.create(tweet);
    return postedTweet;
  }

  /**
   * Search a tweet by ID
   *
   * @param id     tweet id
   * @param fields set fields not in the list to null
   * @return Tweet object which is returned by the Twitter API
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  @Override
  public Tweet showTweet(String id, String[] fields) {
    if (Validator.isNull(id) || Validator.isNull(fields)) {
      throw new IllegalArgumentException(
          "Tweet id and/or fields argument is null. \n"
              + "Please provide a valid tweet string id and/or an empty array instead if all returned fields are to be displayed");
    }
    Tweet foundTweet = dao.findById(Long.parseLong(id));
   /* if (!tweetValidator.isValid(foundTweet)) {
      try {
        ca.jrvs.apps.twitter.service.validation.Tweet.logger.debug(foundTweet.toJson(true, true));
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      throw new RuntimeException("returned Tweet was invalid. Possibly null text field");
    }*/

    return foundTweet;
  }

  /**
   * Delete Tweet(s) by id(s).
   *
   * @param ids tweet IDs which will be deleted
   * @return A list of Tweets
   * @throws IllegalArgumentException if one of the IDs is invalid.
   */
  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    return null;
  }
}
