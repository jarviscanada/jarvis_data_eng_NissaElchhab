package ca.jrvs.apps.twitter.model;

import static org.junit.Assert.assertEquals;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class TweetTest {

  public static String exampleInputJsonTweet;
  public static Tweet exampleExpectedObjectTweet;
  public static Tweet exampleInputObjectTweet;
  public static Tweet exampleExpectedSerializedTweet;
  public static URI getUriIsNotAuthz;
  public static URI getUriIsAuthzAndExists;
  public static URI getUriIsAuthAndDoesNotExist;
  public static URI postUriIsNotAuthz;
  public static URI postUriIsAuthzAndIsValid;
  public static URI postUriIsAuthAndDoesNotExist;

  @BeforeClass
  public static void beforeClass() throws Exception {
    exampleInputJsonTweet = "{\"created_at\":\"Tue Aug 16 18:48:49 +0000 2022\",\"id\":1559613165377183744,\"id_str\":\"1559613165377183744\",\"text\":\"#TestTestTest Hello From Api at 2022-08-16T12:48:48.787\",\"truncated\":false,\"entities\":{\"hashtags\":[{\"text\":\"TestTestTest\",\"indices\":[0,13]}],\"symbols\":[],\"user_mentions\":[],\"urls\":[]},\"source\":\"\\u003ca href=\\\"https:\\/\\/jrvs.ca\\\" rel=\\\"nofollow\\\"\\u003evisjar\\u003c\\/a\\u003e\",\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"user\":{\"id\":1554290146664521728,\"id_str\":\"1554290146664521728\",\"name\":\"Anissaa\",\"screen_name\":\"anissaappears\",\"location\":\"\",\"description\":\"Student, Java dev, Spring Boot, Golang, React\",\"url\":null,\"entities\":{\"description\":{\"urls\":[]}},\"protected\":false,\"followers_count\":114,\"friends_count\":3094,\"listed_count\":0,\"created_at\":\"Tue Aug 02 02:17:50 +0000 2022\",\"favourites_count\":197,\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":true,\"verified\":false,\"statuses_count\":43,\"lang\":null,\"contributors_enabled\":false,\"is_translator\":false,\"is_translation_enabled\":false,\"profile_background_color\":\"F5F8FA\",\"profile_background_image_url\":null,\"profile_background_image_url_https\":null,\"profile_background_tile\":false,\"profile_image_url\":\"http:\\/\\/pbs.twimg.com\\/profile_images\\/1554290347202580480\\/3LGtF_RO_normal.png\",\"profile_image_url_https\":\"https:\\/\\/pbs.twimg.com\\/profile_images\\/1554290347202580480\\/3LGtF_RO_normal.png\",\"profile_link_color\":\"1DA1F2\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"has_extended_profile\":true,\"default_profile\":true,\"default_profile_image\":false,\"following\":false,\"follow_request_sent\":false,\"notifications\":false,\"translator_type\":\"none\",\"withheld_in_countries\":[]},\"geo\":{\"type\":\"Point\",\"coordinates\":[36.78211206,-123.40061283]},\"coordinates\":{\"type\":\"Point\",\"coordinates\":[-123.40061283,36.78211206]},\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"retweet_count\":0,\"favorite_count\":0,\"favorited\":false,\"retweeted\":false,\"lang\":\"in\"}";
    exampleExpectedObjectTweet = null;
    exampleInputObjectTweet = null;
    exampleExpectedSerializedTweet = null;

    PercentEscaper percentEscaper = new PercentEscaper("", false);
    try {
      getUriIsNotAuthz = new URI(
          "https://api.twitter.com/1.1/statuses/show.json?id=1549615165367183744");
      getUriIsAuthzAndExists = new URI(
          "https://api.twitter.com/1.1/statuses/show.json?id=1559613165377183744");
      getUriIsAuthAndDoesNotExist = new URI(
          "https://api.twitter.com/1.1/statuses/show.json?id=0");
      postUriIsNotAuthz = new URI(
          "https://api.twitter.com/1.1/statuses/show.json?id=1549615165367183744");
      postUriIsAuthzAndIsValid = new URI(
          "https://api.twitter.com/1.1/statuses/update.json?status=" +
              percentEscaper.escape("#TestTestTest Hello From Api at " + LocalDateTime.now())
              + "&lat=36.7821120598956&long=-123.400612831116");
      postUriIsAuthAndDoesNotExist = new URI(
          "https://api.twitter.com/1.1/statuses/show.json?id=0");
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("URI initialization error in classSetup", e);
    }
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }


  @Test
  public void shouldDeserializeTweetObjectFromJson() {
    Tweet tweet;
    Tweet

  }

  @Test
  public void shouldSerializeTweetObjectToJson() {

  }

  @Test
  public void create() {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);
    URI uri;

    HttpResponse httpResponse = httpHelper.httpPost(postUriIsAuthzAndIsValid);
    //   System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    assertEquals(HttpStatus.OK.value(), httpResponse.getStatusLine().getStatusCode());
  }

  @Test
  public void findBy() {
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


}