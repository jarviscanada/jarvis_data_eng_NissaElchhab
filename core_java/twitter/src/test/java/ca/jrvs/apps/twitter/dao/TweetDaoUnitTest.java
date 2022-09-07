package ca.jrvs.apps.twitter.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.dto.HttpStatusCode;
import ca.jrvs.apps.twitter.dao.helper.dto.TwitterApi;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.dto.JsonParser;
import com.google.gdata.util.common.base.PercentEscaper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.IllformedLocaleException;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TweetDaoUnitTest {
  @Mock
  HttpHelper mockHelper;
  @Mock
  TwitterApi mockTwitterApi;

  @InjectMocks
  TweetDao tweetDao;

  public Long getByIdIsAuthzAndExists;
  public String getUriIsAuthAndDoesNotExist;
  public String postUriIsNotAuthz;
  public String tweetText;
  public float tweetLong;
  public float tweetLat;

  PercentEscaper percentEscaper = new PercentEscaper("", false);

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
    tweetText = "#TestTestTest Hello From Api at " + LocalDateTime.now();
    tweetLong = -123.400612831116f;
    tweetLat = 36.7821120598956f;

    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);

    this.tweetDao = new TweetDao(httpHelper);
  }

 @Test
public void createTweetFailedRequest() {
   when(mockHelper.httpPost(isNotNull())).thenThrow(new IllegalArgumentException("mock"));
   try {
     tweetDao.create(new Tweet(tweetText));
     fail();
   } catch (RuntimeException e) {
     assertTrue(true);
   }
 }

  @Test
  public void createTweetHappyPathRequest() {

    String tweetAsJsonStr = " {\n"
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
        + "}";

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TweetDao spyDao = Mockito.spy(tweetDao);
    TwitterApi twitterApi = Mockito.spy(mockTwitterApi);
    Tweet expectedTweet = null;
    try {
      expectedTweet = JsonParser.parseJson(tweetAsJsonStr, Tweet.class);
    } catch (IOException e) {
      throw new RuntimeException("Unexpected JsonParser error during happy path test", e);
    }
    // mock parseResponse
    doReturn(expectedTweet).when(twitterApi).parseResponse(any(), (HttpStatusCode) any());
    Tweet tweet = spyDao.create(new Tweet(tweetText, tweetLong, tweetLat));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());

  }

 }