package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TweetController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TweetDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.dto.JsonParser;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TweetServiceImpl;
import ca.jrvs.apps.twitter.validation.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class TwitterCLIApp {

  public static final String USAGE = "TwitterCLIAPP post|show|delete [options]";
  public static Logger logger = LoggerFactory.getLogger(TwitterCLIApp.class);
  private Controller controller;

  @Autowired
  public TwitterCLIApp(Controller controller) {
    this.controller = controller;
  }

  public static void main(String[] args) {
    // oauth secrets
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");

    // dependencies
    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);
    CrdDao<Tweet, Long> dao = new TweetDao(httpHelper);
    Validator<Tweet> validator = new ca.jrvs.apps.twitter.validation.Tweet();
    Service service = new TweetServiceImpl(dao, validator);
    Controller controller = new TweetController(service);
    TwitterCLIApp app = new TwitterCLIApp(controller);

    app.run(args);
  }

  public void run(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException(USAGE);
    }

    switch (args[0].toUpperCase()) {
      case "POST": {
        printTweet(controller.postTweet(args));
        break;
      }

      case "SHOW": {
        printTweet(controller.showTweet(args));
        break;
      }

      case "DELETE": {
        controller.deleteTweet(args).forEach(this::printTweet);
        break;
      }

      default: {
        throw new IllegalArgumentException(USAGE);
      }
    }
  }

  public void printTweet(Tweet tweet) {
    try {
      System.out.println(JsonParser.marshall(tweet, true, false));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("unable to convert Tweet object to string", e);

    }
  }
}