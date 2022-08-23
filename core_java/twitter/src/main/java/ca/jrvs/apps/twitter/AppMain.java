package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AppMain {

  public static Logger logger = LoggerFactory.getLogger(AppMain.class);

  public static void main(String[] args) {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");

    logger.debug(CONSUMER_KEY);
    logger.debug( CONSUMER_KEY_SECRET);
    logger.debug( ACCESS_TOKEN);
    logger.debug( ACCESS_TOKEN_SECRET);

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);
    URI uri;
    try {
      uri = new URI("https://api.twitter.com/1.1/statuses/show.json?id=1559613165377183744");
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    HttpResponse httpResponse = httpHelper.httpGet(uri);
    logger.debug(httpResponse.toString());


  }
}