package ca.jrvs.apps.twitter.dao;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.time.LocalDateTime;
import java.util.IllformedLocaleException;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TweetDaoUnitTest {
  @Mock
  HttpHelper mockHelper;

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
public void showTweetFailedRequest() {
   when(mockHelper.httpPost(isNotNull())).thenThrow(new IllegalArgumentException("mock"));
   try {
     tweetDao.create(new Tweet(tweetText));
     fail();
   } catch (RuntimeException e) {
     assertTrue(true);
   }
 }
 }