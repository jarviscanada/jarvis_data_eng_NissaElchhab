package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

//@RunWith(Parameterized.class)
public class TwitterHttpHelperTest {

  @Rule
  public MockitoRule mockitoJUnitRunner = MockitoJUnit.rule();

  @Mock
  public OAuthConsumer oAuthConsumer;

  @Mock
  public HttpClient httpClient;

  @Mock
  public HttpHelper httpHelper;

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

  @Test(expected = OAuthException.class)
  public void httpPostShouldThrowOAuthExceptionOnOAuthErrors() {
  }

  @Test(expected = IOException.class)
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

  @Test(expected = OAuthException.class)
  public void httpGetShouldThrowOAuthExceptionOnOAuthErrors() {
  }

  @Test(expected = IOException.class)
  public void httpGetShouldThrowIOExceptionOnHttpClientErrors() {
  }


}