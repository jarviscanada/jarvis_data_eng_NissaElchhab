package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.dto.JsonParser;
import ca.jrvs.apps.twitter.validation.Validator;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
          "tweet's text is above max length of "
              + ca.jrvs.apps.twitter.validation.Tweet.MAX_TEXT_LENGTH + " characters");
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

    return filterTweet(foundTweet, fields);
  }

  /**
   * Search a tweet by ID
   *
   * @param id tweet id
   * @return Tweet object which is returned by the Twitter API
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  public Tweet showTweet(String id) {
    if (Validator.isNull(id)) {
      throw new IllegalArgumentException("Tweet id is null");
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
    List<Tweet> deleted;
    if (Validator.isNull(ids)) {
      throw new IllegalArgumentException("Tweet List is null");
    }
    return Arrays.stream(ids)
        .filter(Objects::nonNull)
        .parallel()
        .map(this::deleteTweet)
        .collect(Collectors.toList());
  }

  private Tweet filterTweet(Tweet tweet, String[] fields) {
    Tweet filtered = null;
    try {
      filtered = JsonParser.parseJsonTweetWithFilter(tweet.toJson(), fields);
    } catch (IOException e) {
      throw new RuntimeException("filterTweet parseJsonTweetWithFilter", e);
    }
    return filtered;
  }

  /**
   * deletes a tweet a idStr and returns it if successful,
   * @param id a non-null string
   * @return
   */
  public Tweet deleteTweet(String id) {
    Tweet tweet = dao.deleteById(Long.parseLong(id));
    ca.jrvs.apps.twitter.validation.Tweet.logger.debug(
        "TweetServiceImpl#deleteTweet\ndeletedTweet.toString()\n");
    return tweet;
  }
}
