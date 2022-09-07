package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TweetController implements Controller {

  private Service service;
  private ArgsParser argsParser;

  @Autowired
  public TweetController(Service service) {
    this.service = service;
    argsParser = new ArgsParser();
  }

  public TweetController(Service service, ArgsParser argsParser) {
    this.service = service;
    this.argsParser = argsParser;
  }

  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet postTweet(String[] args) {
    argsParser.configure(args, 2, ":");
    String text = argsParser.getArg();
    Float latitude = Float.parseFloat(argsParser.getArg());
    Float longitude = Float.parseFloat(argsParser.getArg());

    Tweet tweet = new Tweet(text, longitude, latitude);
    return service.postTweet(tweet);
  }

  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public Tweet showTweet(String[] args) {
    argsParser.configure(args, 1, 2, ":");
    String tweetId = argsParser.getArg();
    String[] fields = new String[]{};
    if (args.length == 3 && argsParser.hasOptionalArguments()) {
      fields = argsParser.getArgAsStringArray();
    }
    return service.showTweet(tweetId, fields);
  }

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  @Override
  public List<Tweet> deleteTweet(String[] args) {
    argsParser.configure(args, 1);
    String[] ids = argsParser.getArgAsStringArray();
    return service.deleteTweets(ids);  }
}
