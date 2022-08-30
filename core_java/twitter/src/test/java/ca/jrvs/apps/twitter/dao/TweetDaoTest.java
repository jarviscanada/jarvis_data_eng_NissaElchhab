package ca.jrvs.apps.twitter.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

public class TweetDaoTest {

  public Long getByIdIsAuthzAndExists;
  public String getUriIsAuthAndDoesNotExist;
  public String postUriIsNotAuthz;
  public String tweetText;
  public float tweetLong;
  public float tweetLat;

  PercentEscaper percentEscaper = new PercentEscaper("", false);
  private TweetDao tweetDao;


  @Before
  public void setUp() throws Exception {
    final String CONSUMER_KEY = System.getenv("consumerKey");
    final String CONSUMER_KEY_SECRET = System.getenv("consumerKeySecret");
    final String ACCESS_TOKEN = System.getenv("accessToken");
    final String ACCESS_TOKEN_SECRET = System.getenv("accessTokenSecret");

    getByIdIsAuthzAndExists =1564405218414305280L;
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
  public void createShouldReturnNewValidTweet() {
    Tweet tweet = new Tweet();
    tweet.setText(tweetText);
    tweet.setCoordinates(new Coordinates(tweetLong, tweetLat));
    Tweet responseTweet = this.tweetDao.create(tweet);
    assertThat(responseTweet).isNotNull();
    assertThat(responseTweet.getText()).isEqualTo(tweet.getText());
    assertThat(responseTweet.getCoordinates()).isEqualToComparingFieldByFieldRecursively(
        tweet.getCoordinates());
  }

  //  @Test(expected =  IllegalArgumentException.class)
  public void createShouldReturnNullTweetWithAnException() {
    Tweet tweet = new Tweet();
    this.tweetDao.create(tweet);
  }


  @Test
  public void findByIdShouldReturnExistingValidTweet() {

    Tweet responseTweet = this.tweetDao.findById(getByIdIsAuthzAndExists);
    assertThat(responseTweet).isNotNull();
    assertThat(responseTweet.getText()).contains("#ApiTestDrive: Hello Tout Le Monde");
    assertThat(responseTweet.getCoordinates().longitude()).isEqualTo(tweetLong);
    assertThat(responseTweet.getCoordinates().latitude()).isEqualTo(tweetLat);
  }

  @Test
  public void findByIdShouldReturnNullTweet() {
  }

  @Test
  public void deleteByIdShouldReturnValidDeletedTweet() throws InterruptedException {
    Tweet tweet = new Tweet();
    tweet.setText(tweetText);
    tweet.setCoordinates(new Coordinates(tweetLong, tweetLat));
    Tweet createdTweet = this.tweetDao.create(tweet);
    TimeUnit.SECONDS.sleep(30);
    Tweet deletedTweet = this.tweetDao.deleteById(createdTweet.getId());
    assertThat(deletedTweet).isEqualToComparingFieldByFieldRecursively(createdTweet);
  }

  @Test
  public void deleteByIdShouldReturnNullForNonExistingTweet() {
  }
}