package ca.jrvs.apps.twitter.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import com.google.gdata.util.common.base.PercentEscaper;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    exampleInputJsonTweet = "{\n"
        + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
        + "   \"id\":1097607853932564480,\n"
        + "   \"id_str\":\"1097607853932564480\",\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":[\n"
        + "         {\n"
        + "            \"text\":\"documentation\",\n"
        + "            \"indices\":[\n"
        + "               211,\n"
        + "               225\n"
        + "            ]\n"
        + "         },\n"
        + "         {\n"
        + "            \"text\":\"parsingJSON\",\n"
        + "            \"indices\":[\n"
        + "               226,\n"
        + "               238\n"
        + "            ]\n"
        + "         },\n"
        + "         {\n"
        + "            \"text\":\"GeoTagged\",\n"
        + "            \"indices\":[\n"
        + "               239,\n"
        + "               249\n"
        + "            ]\n"
        + "         }\n"
        + "      ],\n"
        + "      \"user_mentions\":[\n"
        + "         {\n"
        + "            \"name\":\"Twitter API\",\n"
        + "            \"indices\":[\n"
        + "               4,\n"
        + "               15\n"
        + "            ],\n"
        + "            \"screen_name\":\"twitterapi\",\n"
        + "            \"id\":6253282,\n"
        + "            \"id_str\":\"6253282\"\n"
        + "         }\n"
        + "      ]\n"
        + "   },\n"
        + "   \"coordinates\":{\n"
        + "      \"coordinates\":[\n"
        + "         -75.14310264,\n"
        + "         40.05701649\n"
        + "      ],\n"
        + "      \"type\":\"Point\"\n"
        + "   },\n"
        + "   \"retweet_count\":11,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":true,\n"
        + "   \"retweeted\":false\n"
        + "}";

    List<Hashtag> hashTags = Arrays.asList(
        new Hashtag("documentation", Arrays.asList( 211, 225)),
        new Hashtag("parsingJSON",  Arrays.asList(  226, 238 ) ),
        new Hashtag("GeoTagged",Arrays.asList( 239,249  ) ));

    List<UserMention> userMentions = Arrays.asList(
        new UserMention(6253282L,"6253282", Arrays.asList( 4, 15 ),
            "Twitter API","twitterapi" ),
        new UserMention(1253282L,"1253282", Arrays.asList( 3, 25),
            "googleapi","Google API") );

    Coordinates coordinates = new Coordinates(  new float[]{ -75.14310264f, 40.05701649f  },
        "Point");

    Entities entities =  new Entities( hashTags, userMentions );

    exampleExpectedObjectTweet = new Tweet(
      "Mon Feb 18 21:24:39 +0000 2019" ,
        1097607853932564480L, "1097607853932564480",
        "text with loc223",  entities,coordinates, 11,  0,
        true, false);

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
    Tweet parsedExampleInputJsonTweet;
    parsedExampleInputJsonTweet = Tweet.from(exampleInputJsonTweet);
//    assertEquals(exampleExpectedObjectTweet,parsedExampleInputJsonTweet);
  }


  public void shouldSerializeTweetObjectToJson() {

  }

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