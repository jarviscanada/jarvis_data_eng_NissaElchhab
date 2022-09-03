package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.validation.Validator;
import java.util.List;

public class TweetServiceImpl implements Service {

  private CrdDao<Tweet, Long> dao;
  private Validator<Tweet> tweetValidator;

/*  public TweetServiceImpl(CrdDao<Tweet, Long> dao) {
    this.dao = dao;
    this.tweetValidator = new ca.jrvs.apps.twitter.service.validation.Tweet();
  }*/

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
    if (Validator.isNull(tweet)) {
      throw new IllegalArgumentException("tweet's text is null");
    }
    if (!tweetValidator.isValid(tweet)) {
      ca.jrvs.apps.twitter.validation.Tweet.logger.debug(tweet.toString());
      throw new IllegalArgumentException(
          "tweet's text is above max length of " + ca.jrvs.apps.twitter.validation.Tweet.MAX_TEXT_LENGTH + " characters");
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
              + "Please provide a valid tweet string id and/or an empty array instead if all "
              + "returned fields are to be displayed");
    }
    Tweet foundTweet = dao.findById(Long.parseLong(id));
    ca.jrvs.apps.twitter.validation.Tweet.logger.debug(
        "TweetServiceImpl#showTweet\nfoundTweet.toString()\n");

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
