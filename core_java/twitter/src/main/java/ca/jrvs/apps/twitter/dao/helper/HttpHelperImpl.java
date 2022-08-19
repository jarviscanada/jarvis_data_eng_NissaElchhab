package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHelperImpl implements HttpHelper {

  private static final Logger logger = LoggerFactory.getLogger(HttpHelperImpl.class);

  private final OAuthConsumer oAuthConsumer;
  private final HttpClient httpClient;

  public HttpHelperImpl(String consumerKey, String consumerKeySecret, String accessToken,
      String accessTokenSecret) {
    this.oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerKeySecret);
    this.httpClient = HttpClientBuilder.create().build();
  }

  /**
   * Execute a HTTP Post call
   *
   * @param uri
   * @return
   */
  @Override
  public HttpResponse httpPost(URI uri) {
    HttpPost httpPost = new HttpPost(uri);
    HttpResponse httpResponse;
    try {
      oAuthConsumer.sign(httpPost);
      httpResponse = httpClient.execute(httpPost);
    } catch (OAuthException e) {
      logger.debug(e.toString());
      throw new RuntimeException(e);
    } catch (ClientProtocolException e) {
      logger.debug(e.toString());
      throw new RuntimeException(e);
    } catch (IOException e) {
      logger.debug(e.toString());
      throw new RuntimeException(e);
    }
    return httpResponse;
  }

  /**
   * Execute a HTTP Get call
   *
   * @param uri
   * @return
   */
  @Override
  public HttpResponse httpGet(URI uri) {
    HttpGet httpGet = new HttpGet(uri);
    HttpResponse httpResponse;
    try {
      oAuthConsumer.sign(httpGet);
      httpResponse = httpClient.execute(httpGet);
    } catch (OAuthMessageSignerException e) {
      logger.debug(e.toString());
      throw new RuntimeException(e);
    } catch (OAuthExpectationFailedException e) {
      logger.debug(e.toString());
      throw new RuntimeException(e);
    } catch (OAuthCommunicationException e) {
      logger.debug(e.toString());
      throw new RuntimeException(e);
    } catch (ClientProtocolException e) {
      logger.debug(e.toString());
      throw new RuntimeException(e);
    } catch (IOException e) {
      logger.debug(e.toString());
      throw new RuntimeException(e);
    }
    return httpResponse;
  }
}
