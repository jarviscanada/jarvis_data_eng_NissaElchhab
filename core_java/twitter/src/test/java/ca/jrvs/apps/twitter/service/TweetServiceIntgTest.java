package ca.jrvs.apps.twitter.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.validation.Validator;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class TweetServiceIntgTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  Coordinates mockCoordinates;
  @Mock
  Tweet mockTweet;

  @Mock
  private Validator<Coordinates> mockCoordinatesValidator;
  @Mock
  private CrdDao<Tweet, Long> mockDao;
  @Mock
  private Validator<Tweet> mockTweetValidator;
  @InjectMocks
  private TweetServiceImpl mockTweetService;

  private String tweetText;
  private String tooLongTweetText;
  private String tweetAsJsonStr;
  private Tweet tweetFromJsonStr;
  private ZonedDateTime createdAt;
  private float tweetLong;
  private float tweetLat;
  private float invalidLongitude;
  private float invalidLatitude;

  @Before
  public void setUp() throws Exception {
    tweetText = "#TestTestTest Hello From Api at ";
    tooLongTweetText = Stream.generate(() -> "A")
        .limit(ca.jrvs.apps.twitter.validation.Tweet.MAX_TEXT_LENGTH + 1)
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

    tweetFromJsonStr = Tweet.from(tweetAsJsonStr);
    createdAt = ZonedDateTime.now();

    tweetLong = -75.14310264f;
    tweetLat = 40.05701649f;
  /*  tweetLong = -123.400612831116f;
    tweetLat = 36.7821120598956f;*/
    invalidLongitude =
        ca.jrvs.apps.twitter.validation.Coordinates.MAX_LONGITUDE + 0.00001f;
    invalidLatitude =
        ca.jrvs.apps.twitter.validation.Coordinates.MAX_LATITUDE + 0.00001f;
  }

  @After
  public void tearDown() throws Exception {
    tweetText = null;
    tooLongTweetText = null;
    tweetAsJsonStr = null;
    tweetFromJsonStr = null;
    tweetLong = 0;
    tweetLat = 0;
    invalidLongitude =
        ca.jrvs.apps.twitter.validation.Coordinates.MIN_LONGITUDE;
    invalidLatitude =
        ca.jrvs.apps.twitter.validation.Coordinates.MIN_LATITUDE;
//    Mockito.reset(mockTweet,mockDao, mockTweetValidator, mockCoordinatesValidator, mockTweetService);
  }

  @Test
  public void postTweetHappyPath() {
 /*   given(mockTweet.toString()).willReturn("mockTweet#toString()");
    given(mockTweet.getId()).willReturn(1234567890123456789L);
    given(mockTweet.getIdStr()).willReturn("1234567890123456789");
    given(mockTweet.getCreatedAt()).willReturn(createdAt);
    given(mockTweet.getText()).willReturn(tweetText);
    given(mockTweet.getCoordinates()).willReturn(mockCoordinates);
    given(mockCoordinates.longitude()).willReturn(tweetLong);
    given(mockCoordinates.latitude()).willReturn(tweetLat);*/
    Tweet fromJsonStr = Tweet.from(tweetAsJsonStr);

    given(mockDao.create(any(Tweet.class))).willReturn(fromJsonStr);
    given(mockTweetValidator.isValid(any(Tweet.class))).willReturn(true);
    given(mockCoordinatesValidator.isValid(any(Coordinates.class))).willReturn(true);

    Tweet responseTweet = mockTweetService.postTweet(fromJsonStr);
    assertNotNull(responseTweet);
    assertEquals(tweetText, responseTweet.getText());
    assertEquals(tweetLong, responseTweet.getCoordinates().longitude(), 0.0001);
    assertEquals(tweetLat, responseTweet.getCoordinates().latitude(), 0.0001);
    assertNotNull(responseTweet.getIdStr());
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweetTextExceedsMaxChars() {
    given(mockTweet.toString()).willReturn("mockTweet#toString()");
    given(mockTweet.getText()).willReturn(tooLongTweetText);
    given(mockTweet.getCoordinates()).willReturn(mockCoordinates);
    given(mockCoordinates.longitude()).willReturn(tweetLong);
    given(mockCoordinates.latitude()).willReturn(tweetLat);

    Tweet responseTweet = mockTweetService.postTweet(mockTweet);
//   assertNull(responseTweet);
  }

  @Test(expected = IllegalArgumentException.class)
  public void postTweetTextIsNull() {
//    given(tweetService.postTweet(null)).willThrow(IllegalArgumentException.class);
    Tweet responseTweet = mockTweetService.postTweet(null);
//   assertNull(responseTweet);
  }

  @Test
  public void showTweetHappyPathAllFields() {
    given(mockDao.findById(anyLong())).willReturn(tweetFromJsonStr);
    Tweet responseTweet = mockTweetService.showTweet("1097607853932564480", new String[]{});
    assertNotNull(responseTweet);
    Assertions.assertThat(responseTweet.getText())
        .containsPattern("#TestTestTest Hello From Api at");
    assertEquals(tweetLong, responseTweet.getCoordinates().longitude(), 0.0001);
    assertEquals(tweetLat, responseTweet.getCoordinates().latitude(), 0.0001);
    assertNotNull(responseTweet.getIdStr());
  }

  @Test
  public void showTweetHappyPathSomeFields() {
    given(mockDao.findById(anyLong())).willReturn(tweetFromJsonStr);
    Tweet responseTweet = mockTweetService.showTweet("1097607853932564480",
        new String[]{"id_str", "text"});
    assertNotNull(responseTweet);
    Assertions.assertThat(responseTweet.getText())
        .containsPattern("#TestTestTest Hello From Api at");
    assertNull(responseTweet.getCoordinates());
    assertNull(responseTweet.getCreatedAt());
    assertNotNull(responseTweet.getIdStr());
  }

  @Test(expected = IllegalArgumentException.class)
  public void showTweetInvalidArguments() {
    given(mockDao.findById(anyLong())).willReturn(tweetFromJsonStr);
    Tweet responseTweet = mockTweetService.showTweet(null, null);
  }

  @Test
  public void deleteATweetSuccess() {
     /*   given(mockTweet.toString()).willReturn("mockTweet#toString()");
    given(mockTweet.getId()).willReturn(1234567890123456789L);
    given(mockTweet.getIdStr()).willReturn("1234567890123456789");
    given(mockTweet.getCreatedAt()).willReturn(createdAt);
    given(mockTweet.getText()).willReturn(tweetText);
    given(mockTweet.getCoordinates()).willReturn(mockCoordinates);
    given(mockCoordinates.longitude()).willReturn(tweetLong);
    given(mockCoordinates.latitude()).willReturn(tweetLat);*/
    Tweet fromJsonStr = Tweet.from(tweetAsJsonStr);
    given(mockDao.deleteById(fromJsonStr.getId())).willReturn(fromJsonStr);
    Tweet responseTweet = mockTweetService.deleteTweet(fromJsonStr.getIdStr());

    assertNotNull(responseTweet);
    assertEquals(fromJsonStr.getText(), responseTweet.getText());
    assertEquals(fromJsonStr.getCoordinates().longitude(),
        responseTweet.getCoordinates().longitude(), 0.0001);
    assertEquals(fromJsonStr.getCoordinates().latitude(), responseTweet.getCoordinates().latitude(),
        0.0001);
    assertNotNull(responseTweet.getIdStr());
  }

  @Test(expected = RuntimeException.class)
  public void deleteATweetNotFound() {
    Long inexistantOrWrongTweetId = 0L;
    String inexistantOrWrongTweetIdStr = inexistantOrWrongTweetId.toString();
    given(mockDao.deleteById(inexistantOrWrongTweetId)).willThrow(RuntimeException.class);
    Tweet responseTweet = mockTweetService.deleteTweet(inexistantOrWrongTweetIdStr);
  }

  @Test
  public void deleteManyUniqueTweetIdsSuccessfully() {
    Long baseId = tweetFromJsonStr.getId();
    List<Integer> intIds = Arrays.asList(0, 10, 6, 4, 8, 2, 1, 9, 5, 3, 7, 42, 11, 13);
    // set up a List of different tweets with randomized no null gaps
    List<Tweet> fromJsonStrs = new ArrayList<>();
    for (Integer i : intIds) {
      Tweet t = Tweet.from(tweetAsJsonStr);
      t.setId(baseId + i);
      t.setIdStr(t.getId().toString());
      t.setText("Text for Id: " + t.getIdStr() + "iteration: " + i.toString());
      fromJsonStrs.add(t);
      given(mockDao.deleteById(t.getId())).willReturn(t);
    }
    String[] ids = new String[fromJsonStrs.size()];
    fromJsonStrs.stream()
        .map(Tweet::getIdStr)
        .collect(Collectors.toList())
        .toArray(ids);
    // verification of String[] ids
    List<Tweet> responseTweets = mockTweetService.deleteTweets(ids);

    assertNotNull(responseTweets);
    assertEquals(fromJsonStrs.size(), responseTweets.size());
    assertThat(responseTweets.stream().map(Tweet::getId).collect(Collectors.toList()))
        .isEqualTo(fromJsonStrs.stream().map(Tweet::getId).collect(Collectors.toList()));
  }


  @Test
  public void deleteManyUniqueTweetIdsWithGapsSuccessfully() {
    Long baseId = tweetFromJsonStr.getId();
    List<Integer> intIds = Arrays.asList(null, 0, 10, null, 6, 4, 8, null, 2, 1, 9, null, null, 5,
        3, 7, 42, null, 11, 13);
    // set up a List of different tweets with randomized no null gaps
    List<Tweet> fromJsonStrs = new ArrayList<>();
    for (Integer i : intIds) {
      if (i == null) {
        fromJsonStrs.add(null);
        continue;
      }
      Tweet t = Tweet.from(tweetAsJsonStr);
      t.setId(baseId + i);
      t.setIdStr(t.getId().toString());
      t.setText("Text for Id: " + t.getIdStr() + "iteration: " + i.toString());
      fromJsonStrs.add(t);
      given(mockDao.deleteById(t.getId())).willReturn(t);
    }
    String[] ids = new String[fromJsonStrs.size()];
    fromJsonStrs.stream()
        .map(t -> t == null ? null : t.getIdStr())
        .collect(Collectors.toList())
        .toArray(ids);
    // verification of String[] ids
    List<Tweet> responseTweets = mockTweetService.deleteTweets(ids);

    assertNotNull(responseTweets);
    assertEquals(fromJsonStrs.stream().filter(Objects::nonNull).collect(Collectors.toList()).size(),
        responseTweets.size());
    assertThat(responseTweets.stream().map(Tweet::getId).collect(Collectors.toList()))
        .isEqualTo(fromJsonStrs.stream().filter(Objects::nonNull).map(Tweet::getId)
            .collect(Collectors.toList()));
  }


  @Test
  public void validatedTweetCoordinatesOutOfBoundsShouldFail() {
    Validator<Coordinates> coordxValidator = new ca.jrvs.apps.twitter.validation.Coordinates();
    boolean isValidLong = coordxValidator.isValid(new Coordinates(
        invalidLongitude, 0));
    boolean isValidLat = coordxValidator.isValid(new Coordinates(
        0, invalidLatitude));
    assertFalse(isValidLong || isValidLat);
  }

  @Test
  public void validatedTweetTextOutOfBoundsShouldFail() {
    Validator<Tweet> twt = new ca.jrvs.apps.twitter.validation.Tweet();
    boolean isValid = twt.isValid(new Tweet(tooLongTweetText, tweetLong, tweetLat));
    assertFalse(isValid);
  }

}