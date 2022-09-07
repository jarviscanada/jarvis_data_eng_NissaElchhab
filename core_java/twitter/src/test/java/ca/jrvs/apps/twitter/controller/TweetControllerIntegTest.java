package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TweetDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.model.Hashtag;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.UserMention;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TweetServiceImpl;
import ca.jrvs.apps.twitter.validation.Validator;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TweetControllerIntegTest {

  private String exampleInputJsonTweet;
  private Tweet exampleExpectedObjectTweet;
  private Tweet exampleInputObjectTweet;
  private String exampleExpectedSerializedTweet;

  @Before
  public void setUp() throws Exception {

    exampleInputJsonTweet = "    {\n"
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

    List<Hashtag> hashtags = Arrays.asList(
        new Hashtag("documentation", Arrays.asList(211, 225)),
        new Hashtag("parsingJSON", Arrays.asList(226, 238)),
        new Hashtag("GeoTagged", Arrays.asList(239, 249)));

    List<UserMention> userMentions = Arrays.asList(
        new UserMention(6253282L, "6253282", Arrays.asList(4, 15),
            "Twitter API", "twitterapi"),
        new UserMention(1253282L, "1253282", Arrays.asList(6, 22),
            "Google API", "googleapi"));

    Entities entities = new Entities(hashtags, userMentions);
    Coordinates coordinates = new Coordinates(new float[]{-75.14310264f, 40.05701649f},
        "Point");

    exampleExpectedObjectTweet = new Tweet(
        "Mon Feb 18 21:24:39 +0000 2019",
        1097607853932564480L, "1097607853932564480",
        "text with loc223", entities, coordinates, 11, 0,
        true, false);

    exampleInputObjectTweet = new Tweet(
        "Mon Feb 18 21:24:39 +0000 2019",
        1097607853932564480L, "1097607853932564480",
        "text with loc223", entities, coordinates, 11, 0,
        true, false);

    exampleExpectedSerializedTweet = exampleInputJsonTweet;
  }

  @After
  public void tearDown() {
    exampleInputObjectTweet = null;
    exampleInputJsonTweet = null;
    exampleExpectedObjectTweet = null;
    exampleExpectedSerializedTweet = null;
  }


  @Test
  public void shouldReturnPostedTweetWithAnID() {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);
    CrdDao<Tweet, Long> tweetDao = new TweetDao(httpHelper);
    Validator<Tweet> tweetValidator = new ca.jrvs.apps.twitter.validation.Tweet();
    Service tweetService = new TweetServiceImpl(tweetDao, tweetValidator);
    Controller tweetController = new TweetController(tweetService);

    String tweetText = "#TestTestTest Hello From Api at " + System.currentTimeMillis();
    Coordinates validCoordx = new Coordinates(10.10f, 14.14f);

    Tweet tweet = new Tweet(tweetText, validCoordx.longitude(), validCoordx.latitude());
    String[] argv = new String[]{"post", tweetText,
        validCoordx.latitude() + ":" + validCoordx.longitude()};

    Tweet postedTweet = tweetController.postTweet(argv);

    Assertions.assertThat(postedTweet).isNotNull();
    Assertions.assertThat(postedTweet.getIdStr()).isNotNull();
    Assertions.assertThat(postedTweet.getId()).isNotNull();
    Assertions.assertThat(postedTweet.getIdStr()).isEqualTo(postedTweet.getId().toString());
    Assertions.assertThat(postedTweet.getText()).isEqualTo(tweet.getText());
    Assertions.assertThat(postedTweet.getCoordinates().longitude())
        .isEqualTo( validCoordx.longitude());
    Assertions.assertThat(postedTweet.getCoordinates().latitude())
        .isEqualTo( validCoordx.latitude());

    Tweet deletedTweet = tweetService.deleteTweets(new String[]{postedTweet.getIdStr()}).get(0);
    Assertions.assertThat(deletedTweet).isEqualToComparingFieldByFieldRecursively(postedTweet);
  }

  @Test
  public void shouldShowTweetWithAllFields() {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);
    CrdDao<Tweet, Long> tweetDao = new TweetDao(httpHelper);
    Validator<Tweet> tweetValidator = new ca.jrvs.apps.twitter.validation.Tweet();
    Service tweetService = new TweetServiceImpl(tweetDao, tweetValidator);
    Controller tweetController = new TweetController(tweetService);

    String tweetText = "#TestTestTest Hello From Api at " + System.currentTimeMillis();
    Coordinates validCoordx = new Coordinates(10.10f, 14.14f);

    // post a tweet
    Tweet tweet = new Tweet(tweetText, validCoordx.longitude(), validCoordx.latitude());
    Tweet postedTweet = tweetService.postTweet(tweet);
    Assertions.assertThat(postedTweet).isNotNull();

    // show the tweet with all fields
    String[] argv = new String[]{"show", postedTweet.getIdStr()};
    Tweet showTweetFull = tweetController.showTweet(argv);

    Assertions.assertThat(showTweetFull.getIdStr()).isNotNull();
    Assertions.assertThat(showTweetFull.getId()).isNotNull();
    Assertions.assertThat(showTweetFull.getIdStr()).isEqualTo(postedTweet.getId().toString());
    Assertions.assertThat(showTweetFull.getText()).isEqualTo(postedTweet.getText());
    Assertions.assertThat(showTweetFull.getCoordinates().longitude())
        .isEqualTo( validCoordx.longitude());
    Assertions.assertThat(showTweetFull.getCoordinates().latitude())
        .isEqualTo( validCoordx.latitude());
    Assertions.assertThat(showTweetFull.isFavorited()).isEqualTo(postedTweet.isFavorited());
    Assertions.assertThat(showTweetFull.getRetweetCount()).isEqualTo(0);
    Assertions.assertThat(showTweetFull.getFavoriteCount()).isEqualTo(0);
    Assertions.assertThat(showTweetFull.isRetweeted()).isFalse();
    Assertions.assertThat(showTweetFull.getCreatedAt()).isNotNull();
    Assertions.assertThat(showTweetFull.getEntities()).isNotNull();

    Tweet deletedTweet = tweetService.deleteTweets(new String[]{postedTweet.getIdStr()}).get(0);
    Assertions.assertThat(deletedTweet).isEqualToComparingFieldByFieldRecursively(postedTweet);
  }

  @Test
  public void shouldShowTweetWithSomeFields() {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_KEY_SECRET, ACCESS_TOKEN,
        ACCESS_TOKEN_SECRET);
    CrdDao<Tweet, Long> tweetDao = new TweetDao(httpHelper);
    Validator<Tweet> tweetValidator = new ca.jrvs.apps.twitter.validation.Tweet();
    Service tweetService = new TweetServiceImpl(tweetDao, tweetValidator);
    Controller tweetController = new TweetController(tweetService);

    String tweetText = "#TestTestTest Hello From Api at " + System.currentTimeMillis();
    Coordinates validCoordx = new Coordinates(10.10f, 14.14f);

    // post a tweet
    Tweet tweet = new Tweet(tweetText, validCoordx.longitude(), validCoordx.latitude());
    Tweet postedTweet = tweetService.postTweet(tweet);
    Assertions.assertThat(postedTweet).isNotNull();

    // show the tweet with all fields
    String[] argv = new String[]{"show", postedTweet.getIdStr(), "[text,favorited,id_str]"};
    Tweet showTweetFull = tweetController.showTweet(argv);

    Assertions.assertThat(showTweetFull.getIdStr()).isNotNull();
//    Assertions.assertThat(showTweetFull.getId()).isNull();
    Assertions.assertThat(showTweetFull.getIdStr()).isEqualTo(postedTweet.getId().toString());
    Assertions.assertThat(showTweetFull.getText()).isEqualTo(postedTweet.getText());
    Assertions.assertThat(showTweetFull.getCoordinates()).isNull();
    Assertions.assertThat(showTweetFull.isFavorited()).isNotNull();
    Assertions.assertThat(showTweetFull.isFavorited()).isEqualTo(postedTweet.isFavorited());
    Assertions.assertThat(showTweetFull.getRetweetCount()).isNull();
    Assertions.assertThat(showTweetFull.getFavoriteCount()).isNull();
    Assertions.assertThat(showTweetFull.isRetweeted()).isNull();
    Assertions.assertThat(showTweetFull.getCreatedAt()).isNull();
    Assertions.assertThat(showTweetFull.getEntities()).isNull();

    // delete created tweet
    Tweet deletedTweet = tweetService.deleteTweets(new String[]{postedTweet.getIdStr()}).get(0);
    Assertions.assertThat(deletedTweet).isEqualToComparingFieldByFieldRecursively(postedTweet);
  }


}