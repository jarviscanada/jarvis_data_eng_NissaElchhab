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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public
class TwitterCLIApp {

  public static final String USAGE = "TwitterCLIAPP post|show|delete [options]";
  public static Logger logger = LoggerFactory.getLogger(TwitterCLIApp.class);
  private Controller controller;

  @Autowired
  public TwitterCLIApp(Controller controller) {
    this.controller = controller;
  }

  public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(TwitterCLIApp.class);
    TwitterCLIApp app = ctx.getBean(TwitterCLIApp.class);
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