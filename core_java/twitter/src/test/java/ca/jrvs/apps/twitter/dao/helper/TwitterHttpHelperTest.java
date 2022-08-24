package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import oauth.signpost.OAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;

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

  public static URI getUriIsNotAuthz;
  public static URI getUriIsAuthzAndExists;
  public static URI getUriIsAuthAndDoesNotExist;

  @BeforeClass
  public static void classSetup() {
    try {
      getUriIsNotAuthz = new URI(
          "https://api.twitter.com/1.1/statuses/show.json?id=1549615165367183744");
      getUriIsAuthzAndExists = new URI(
          "https://api.twitter.com/1.1/statuses/show.json?id=1559613165377183744");
      getUriIsAuthAndDoesNotExist = new URI(
          "https://api.twitter.com/1.1/statuses/show.json?id=0");
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("URI initialization error in classSetup", e);
    }
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
  public void shouldReturn2xxOnGetAndPostRequests() {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);
    URI uri;

    HttpResponse httpResponse = httpHelper.httpGet(getUriIsAuthzAndExists);
    //   System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
  }

  @Test
  public void httpPostShouldReturn404OnNonExistingURI() {
  }

  @Test
  public void httpPostShouldReturn204OnSuccessfulResourcePost() {
  }

  @Test(expected = IllegalArgumentException.class)
  public void httpPostShouldThrowIllegalArgumentExceptionOnNullUri() {
    httpHelper = new TwitterHttpHelper(oAuthConsumer, httpClient);
    httpHelper.httpPost(null);
  }

  //  @Test(expected = IOException.class)
  public void httpPostShouldThrowIllegalStateExceptionOnOnHttpClientNetworkErrors() {
    httpHelper.httpPost(null);
  }


  @Test
  public void httpGetShouldReturn404ResponseOnNonExistingURI() {
  }

  @Test
  public void httpGetShouldReturn200ResponseOnReachableURI() {
  }

  @Test(expected = IllegalArgumentException.class)
  public void httpGetShouldThrowIllegalArgumentExceptionOnNullUri() {
    httpHelper = new TwitterHttpHelper(oAuthConsumer, httpClient);
    httpHelper.httpGet(null);
  }

  //  @Test(expected = IOException.class)
  public void httpGetShouldThrowIllegalStateExceptionOnOnHttpClientNetworkErrors() {
  }


}