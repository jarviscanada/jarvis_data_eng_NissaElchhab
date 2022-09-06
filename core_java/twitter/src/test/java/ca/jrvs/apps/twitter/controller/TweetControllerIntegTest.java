package ca.jrvs.apps.twitter.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class TweetControllerIntegTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();
  @Mock
  Service mockService;
  @Mock
  ArgsParser mockArgsParser;
  @InjectMocks
  TweetController mockController;

  private Tweet sampleTweet;
  private String created_at = "Mon Feb 18 21:24:39 +0000 2019";
  private Long tweetId = 1097607853932564480L;
  private String tweetIdStr = "1097607853932564480";
  private String tweetText = "#TestTestTest Hello From Api at ";
  private Coordinates validCoordx = new Coordinates(10.10f, 14.14f);

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void postTweet() {
    String[] args = {tweetText, "14.14:10.10"};
    Tweet sampleTweet = new Tweet(tweetText, validCoordx.longitude(), validCoordx.latitude());
    given(mockArgsParser.getArg())
        .willReturn(tweetText)
        .willReturn("14.14")
        .willReturn("10.10");
    given(mockService.postTweet(any())).willReturn(sampleTweet);
    Tweet postedTweet = mockController.postTweet(args);

    Assertions.assertThat(postedTweet.getText()).isEqualTo(sampleTweet.getText());
    Assertions.assertThat(postedTweet.getCoordinates().longitude())
        .isEqualTo(sampleTweet.getCoordinates().longitude());
    Assertions.assertThat(postedTweet.getCoordinates().latitude())
        .isEqualTo(sampleTweet.getCoordinates().latitude());
  }

  @Test
  public void showTweet() {
    String[] argv = {"SHOW", tweetIdStr, "[text,favorited,id,id_str]"};
    String[] fields = new String[]{"text", "favorited", "id", "id_str"};
    Tweet sampleTweet = new Tweet(tweetText, validCoordx.longitude(), validCoordx.latitude());
    sampleTweet.setIdStr(tweetIdStr);
    sampleTweet.setId(tweetId);
    sampleTweet.setFavorited(true);

    given(mockArgsParser.hasOptionalArguments()).willReturn(true);
    given(mockArgsParser.getArgAsStringArray()).willReturn(fields);
    given(mockArgsParser.getArg())
        .willReturn(argv[1])
        .willReturn(argv[2]);
    given(mockService.showTweet(argv[1], fields)).willReturn(sampleTweet);
    Tweet shownTweet = mockController.showTweet(argv);

    Assertions.assertThat(shownTweet.getIdStr()).isEqualTo(sampleTweet.getIdStr());
    Assertions.assertThat(shownTweet.getId()).isEqualTo(sampleTweet.getId());
    Assertions.assertThat(shownTweet.getText()).isEqualTo(sampleTweet.getText());
    Assertions.assertThat(shownTweet.isFavorited()).isEqualTo(sampleTweet.isFavorited());
    Assertions.assertThat(shownTweet.getCoordinates()).isNull();
    Assertions.assertThat(shownTweet.getCreatedAt()).isNull();
    Assertions.assertThat(shownTweet.getEntities()).isNull();
    Assertions.assertThat(shownTweet.getRetweetCount()).isNull();
    Assertions.assertThat(shownTweet.getFavoriteCount()).isNull();
    Assertions.assertThat(shownTweet.isRetweeted()).isNull();
  }

  @Test
  public void deleteTweet() {
    String[] args = {"1", "2", "3"};
    Tweet sampleTweet1 = new Tweet(tweetText + " 1");
    sampleTweet1.setIdStr("1");
    Tweet sampleTweet2 = new Tweet(tweetText + " 2");
    sampleTweet1.setIdStr("2");
    Tweet sampleTweet3 = new Tweet(tweetText + " 3");
    sampleTweet1.setIdStr("3");
    String[] ids = new String[]{"2", "3", "1"};

    given(mockArgsParser.getArgAsStringArray())
        .willReturn(ids);
    given(mockService.deleteTweets(any())).willReturn(
        Arrays.asList(sampleTweet1, sampleTweet2, sampleTweet3));
    List<Tweet> deletedTweets = mockController.deleteTweet(ids);

    Assertions.assertThat(deletedTweets.size()).isEqualTo(3);
  }
}