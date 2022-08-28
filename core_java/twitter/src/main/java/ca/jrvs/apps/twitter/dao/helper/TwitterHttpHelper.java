package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterHttpHelper implements HttpHelper {

  //  TODO public static final String DEFAULT_POST_BODY = "";
//  TODO public static final String DEFAULT_CHARSET = "UTF-8";
  private static final Logger logger = LoggerFactory.getLogger(TwitterHttpHelper.class);
  private final OAuthConsumer oAuthConsumer;
  private final HttpClient httpClient;

  public TwitterHttpHelper(String consumerKey, String consumerKeySecret, String accessToken,
      String accessTokenSecret) {
    this.oAuthConsumer = new CommonsHttpOAuthConsumer(consumerKey, consumerKeySecret);
    this.oAuthConsumer.setTokenWithSecret(accessToken, accessTokenSecret);
//    this.httpClient = HttpClientBuilder.create().build();
    this.httpClient = new DefaultHttpClient();
  }

  public TwitterHttpHelper(OAuthConsumer oAuthConsumer, HttpClient httpClient) {
    this.oAuthConsumer = oAuthConsumer;
    this.httpClient = httpClient;
  }

  public TwitterHttpHelper(OAuthConsumer oAuthConsumer) {
    this(oAuthConsumer, new DefaultHttpClient());
  }

  /**
   * Execute a HTTP Get call
   *
   * @param uri
   * @return
   */
  @Override
  public HttpResponse httpGet(URI uri) {
    try {
      return executeHttpMethod(HttpMethod.GET, uri, null);
    } catch (OAuthMessageSignerException | OAuthExpectationFailedException |
             OAuthCommunicationException e) {
      logger.debug("GET OAuthMessageSignerException | OAuthExpectationFailedException |  \n"
          + "             OAuthCommunicationException: " + e);
      throw new IllegalStateException(e);
    } catch (ClientProtocolException e) {
      logger.debug("GET ClientProtocolException: " + e);
      throw new IllegalStateException(e);
    } catch (IOException e) {
      logger.debug("GET IOException: " + e);
      throw new IllegalStateException(e);
    }
  }

  /**
   * Execute a HTTP Post call
   *
   * @param uri
   * @return
   */
  @Override
  public HttpResponse httpPost(URI uri) {
    try {
      return executeHttpMethod(HttpMethod.POST, uri, null);
    } catch (OAuthMessageSignerException | OAuthExpectationFailedException |
             OAuthCommunicationException e) {
      logger.debug("POST OAuthMessageSignerException | OAuthExpectationFailedException |  \n"
          + "             OAuthCommunicationException: " + e);
      throw new IllegalStateException(e);
    } catch (ClientProtocolException e) {
      logger.debug("POST ClientProtocolException: " + e);
      throw new IllegalStateException(e);
    } catch (IOException e) {
      logger.debug("POST IOException: " + e);
      throw new IllegalStateException(e);
    }
  }

  private HttpResponse executeHttpMethod(HttpMethod httpMethod, URI uri, StringEntity entity)
      throws OAuthMessageSignerException, OAuthExpectationFailedException,
      OAuthCommunicationException, IOException {
    if (uri == null) {
      throw new IllegalArgumentException("URI argument should not be null");
    }
    HttpUriRequest request;
    switch (httpMethod) {
      case GET: {
        request = new HttpGet(uri);
        break;
      }
      case POST: {
        request = new HttpPost(uri);
        if (entity != null) {
          ((HttpEntityEnclosingRequest) request).setEntity(entity);
        }
        break;
      }
      default: {
        logger.error("Invalid Http Method: " + httpMethod);
        throw new IllegalArgumentException("Invalid Http Method: " + httpMethod);
      }
    }

    oAuthConsumer.sign(request);
    return httpClient.execute(request);
  }
}
