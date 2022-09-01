package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.validation.Validator;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class TweetServiceImplUnitTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();
  public Long getByIdIsAuthzAndExists;
  public String getUriIsAuthAndDoesNotExist;
  public String postUriIsNotAuthz;
  public String tweetText;
  public String tooLongTweetText;
  public String tweetAsJsonStr;
  public float tweetLong;
  public float tweetLat;
  @Mock
  CrdDao<Tweet, Long> dao;
  @Mock
  Validator<Tweet> tweetValidator;
  @Mock
  Validator<Coordinates> coordinatesValidator;
  @InjectMocks
  TweetServiceImpl tweetService;

  @Before
  public void setUp() throws Exception {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");

    getByIdIsAuthzAndExists = 1564405218414305280L;
    getUriIsAuthAndDoesNotExist =
        "https://api.twitter.com/1.1/statuses/show.json?id=0";
    postUriIsNotAuthz =
        "https://api.twitter.com/1.1/statuses/show.json?id=1549615165367183744";
    tweetText = "#TestTestTest Hello From Api at ";
    tooLongTweetText = Stream.generate(() -> "A")
        .limit(ca.jrvs.apps.twitter.service.validation.Tweet.MAX_TEXT_LENGTH + 1)
        .reduce("", (acc, s) -> acc + s);

    tweetAsJsonStr = " {\n"
        + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
        + "   \"id\":1097607853932564480,\n"
        + "   \"id_str\":\"1097607853932564480\",\n"
        + "   \"text\":\"" + tweetText + "\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":  [\n"
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
        + "         },\n"
        + "\n"
        + "          {\n"
        + "            \"name\":\"Google API\",\n"
        + "            \"indices\":[\n"
        + "               6,\n"
        + "               22\n"
        + "            ],\n"
        + "            \"screen_name\":\"googleapi\",\n"
        + "            \"id\":1253282,\n"
        + "            \"id_str\":\"1253282\"\n"
        + "         }\n"
        + "\n"
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

/*    tweetAsJsonStr = " {\n"
        + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
        + "   \"id\":1097607853932564480,\n"
        + "   \"id_str\":\"1097607853932564480\",\n"
        + "   \"text\":\"text with loc223\",\n"
        + "   \"entities\":{\n"
        + "      \"hashtags\":  [\n"
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
        + "         },\n"
        + "\n"
        + "          {\n"
        + "            \"name\":\"Google API\",\n"
        + "            \"indices\":[\n"
        + "               6,\n"
        + "               22\n"
        + "            ],\n"
        + "            \"screen_name\":\"googleapi\",\n"
        + "            \"id\":1253282,\n"
        + "            \"id_str\":\"1253282\"\n"
        + "         }\n"
        + "\n"
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
        + "}";*/

    tweetLong = -75.14310264f;
    tweetLat = 40.05701649f;
  /*  tweetLong = -123.400612831116f;
    tweetLat = 36.7821120598956f;*/

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);
  }

  @Test
  public void postTweetHappyPath() {
    given(dao.create(any(Tweet.class))).willReturn(new Tweet(tweetText, tweetLong, tweetLat));
    given(tweetValidator.isValid(any(Tweet.class))).willReturn(true);
    given(coordinatesValidator.isValid(any(Coordinates.class))).willReturn(true);
    Tweet responseTweet = tweetService.postTweet(new Tweet(tweetText, tweetLong, tweetLat));
    assertNotNull(responseTweet);
    assertEquals(tweetText, responseTweet.getText());
    assertEquals(tweetLong, responseTweet.getCoordinates().longitude(), 0.0001);
    assertEquals(tweetLat, responseTweet.getCoordinates().latitude(), 0.0001);
    assertNotNull(responseTweet.getIdStr());
  }

//  @Test(expected = IllegalArgumentException.class)
  public void postTweetTextExceedsMaxChars() {
    given(tweetService.postTweet(new Tweet(tooLongTweetText, tweetLong, tweetLat))).willThrow(
        IllegalArgumentException.class);

    Tweet responseTweet = tweetService.postTweet(new Tweet(tooLongTweetText, tweetLong, tweetLat));
//   assertNull(responseTweet);
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweetTextIsNull() {
    given(tweetService.postTweet(null)).willThrow(IllegalArgumentException.class);
    Tweet responseTweet = tweetService.postTweet(null);
//   assertNull(responseTweet);
  }

  @Test
  public void showTweetHappyPathAllFields() {
    given(dao.findById(anyLong())).willReturn(Tweet.from(tweetAsJsonStr));
    Tweet responseTweet = tweetService.showTweet("1097607853932564480", new String[]{});
    assertNotNull(responseTweet);
    Assertions.assertThat(responseTweet.getText())
        .containsPattern("#TestTestTest Hello From Api at");
    assertEquals(tweetLong, responseTweet.getCoordinates().longitude(), 0.0001);
    assertEquals(tweetLat, responseTweet.getCoordinates().latitude(), 0.0001);
    assertNotNull(responseTweet.getIdStr());
  }

  @Test
  public void showTweetHappyPathSomeFields() {
    given(dao.findById(anyLong())).willReturn(Tweet.from(tweetAsJsonStr));
    Tweet responseTweet = tweetService.showTweet("1097607853932564480", new String[]{});
    assertNotNull(responseTweet);
    Assertions.assertThat(responseTweet.getText())
        .containsPattern("#TestTestTest Hello From Api at");
    assertEquals(tweetLong, responseTweet.getCoordinates().longitude(), 0.0001);
    assertEquals(tweetLat, responseTweet.getCoordinates().latitude(), 0.0001);
    assertNotNull(responseTweet.getIdStr());
  }

  @Test(expected = IllegalArgumentException.class)
  public void showTweetInvalidArguments() {
    given(dao.findById(anyLong())).willReturn(Tweet.from(tweetAsJsonStr));
    Tweet responseTweet = tweetService.showTweet(null, null);
  }

  @Test
  public void deleteTweets() {
    fail();
  }

  @Test
  public void validatedTweetCoordinatesOutOfBoundsShouldFail() {
    float invalidLongitude =
        ca.jrvs.apps.twitter.service.validation.Coordinates.MAX_LONGITUDE+0.00001f;
    Validator<Coordinates> coordxValidator = new ca.jrvs.apps.twitter.service.validation.Coordinates();
    boolean isValid = coordxValidator.isValid(new Coordinates(
        invalidLongitude, 0));
    assertFalse(isValid);
  }

  @Test
  public void validatedTweetTextOutOfBoundsShouldFail() {
    Validator<Tweet> twt = new ca.jrvs.apps.twitter.service.validation.Tweet();
    boolean isValid = twt.isValid(new Tweet(tooLongTweetText, tweetLong, tweetLat));
    assertFalse(isValid);
  }

}