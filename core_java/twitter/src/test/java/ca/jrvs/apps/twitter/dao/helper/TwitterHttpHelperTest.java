package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import java.net.URI;
import oauth.signpost.OAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterHttpHelperTest {

  @Mock
  OAuthConsumer oAuthConsumer;
  @Mock
  HttpClient httpClient;
  @Mock
  HttpHelper httpHelper;

  URI uri;

  @BeforeClass
  public static void classSetUp() {
  }

  @AfterClass
  public static void classTearDown() {
  }

  @Before
  public void setUp() throws Exception {


  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void httpPostShouldReturnAHttpResponse() {
    HttpResponse httpResponse = httpHelper.httpPost(uri);
    assertTrue(httpResponse != null);
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