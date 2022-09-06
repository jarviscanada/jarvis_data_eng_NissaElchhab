package ca.jrvs.apps.twitter.validation;

import ca.jrvs.apps.twitter.model.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Tweet extends ca.jrvs.apps.twitter.model.Tweet implements
    Validator<ca.jrvs.apps.twitter.model.Tweet> {

  public static final String CREATED_AT_PATTERN = "EEE MMM dd HH:mm:ss Z yyyy";
  public static final int MIN_TEXT_LENGTH = 0;
  public static final int MAX_TEXT_LENGTH = 140;
  public static final String EMPTY_STRING = "0";
  public static final String EMPTY_ID = EMPTY_STRING;

  public static final Logger logger = LoggerFactory.getLogger(
      ca.jrvs.apps.twitter.model.Tweet.class);

  private final Validator<Coordinates> coordinatesValidator =
      new ca.jrvs.apps.twitter.validation.Coordinates();

  @Override
  public boolean isValid(ca.jrvs.apps.twitter.model.Tweet tweet) {
    return Validator.isNotNull(tweet) && isTextValid(tweet)  && coordinatesValidator.isValid(
        tweet.getCoordinates());
  }

  private boolean isTextValid(ca.jrvs.apps.twitter.model.Tweet tweet) {
    String text = tweet.getText();
    return Validator.isNotNull(text) &&
        Validator.isValueBetweenInclusive(text.length(), MIN_TEXT_LENGTH, MAX_TEXT_LENGTH);
  }
}
