package ca.jrvs.apps.twitter.example;

import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterApiTest {

  private static final String CONSUMER_KEY = System.getenv("consumerKey");
  private static final String CONSUMER_SECRET = System.getenv("consumerSecret");
  private static final String ACCESS_TOKEN = System.getenv("accessToken");
  private static final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");

  public static void main(String[] args)
      throws OAuthMessageSignerException, OAuthExpectationFailedException,
      OAuthCommunicationException, IOException {

    // logging
    final Logger logger = LoggerFactory.getLogger(TwitterApiTest.class);

    // setup oauth
    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
    consumer.setTokenWithSecret(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
    logger.info("setup oauth " + consumer);

    // create an HTTP POST request
    String twitterStatus =
        args.length == 0 ? "#TestTestTest Hello From Api at " + LocalDateTime.now()
            : args[1];
    PercentEscaper percentEscaper = new PercentEscaper("", false);
    HttpPost request = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status=" +
        percentEscaper.escape(twitterStatus) + "&lat=36.7821120598956&long=-123.400612831116");
    logger.info("Create HTTP POST request " + request);

    // sign the request / add headers
    consumer.sign(request);

    // enumerating HTTP request headers
    logger.info("HTTP Request Headers BEGIN <!-- \n");
    Arrays.stream(request.getAllHeaders()).forEach(h -> logger.info(h.toString()));
    logger.info("\n --> HTTP Request Headers END.\n");

    // send the request
    HttpClient httpClient = HttpClientBuilder.create().build();
    //    BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
    //    httpClient.execute(request, basicResponseHandler);

    HttpResponse httpResponse = httpClient.execute(request);
    logger.info("HttpResponse: " + EntityUtils.toString(httpResponse.getEntity()));


  }
}
