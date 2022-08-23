package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import java.net.URI;
import oauth.signpost.OAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
class HttpHelperTest {

  @Mock
  OAuthConsumer oAuthConsumer;
  @Mock
  HttpClient httpClient;
  @Mock
  URI uri;

  @Before
  public void setUp() throws Exception {

  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void httpPostShouldReturnAHttpResponse() {
  }

    @Test
  public void httpPostShouldReturn401OnForbiddenPath() {
  }

  @Test
  public void httpPostShouldReturn404OnNonExistingURI() {
  }

  @Test
  public void httpPostShouldReturn204OnSuccessfulResourcePost() {
  }

  @Test
  public void httpPostShouldThrowOAuthExceptionOnOAuthErrors() {
  }

  @Test
  public void httpPostShouldThrowIOExceptionOnHttpClientErrors() {
  }

  @Test
  public void httpGetShouldReturnAHttpResponse() {
  }

    @Test
  public void httpGetShouldReturn404ResponseOnNonExistingURI() {
  }

    @Test
  public void httpGetShouldReturn200ResponseOnReachableURI() {
  }

    @Test
  public void httpGetShouldThrowOAuthExceptionOnOAuthErrors() {
  }

    @Test
  public void httpGetShouldThrowIOExceptionOnHttpClientErrors() {
  }


}