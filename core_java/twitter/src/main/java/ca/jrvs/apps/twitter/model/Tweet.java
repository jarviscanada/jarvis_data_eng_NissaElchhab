package ca.jrvs.apps.twitter.model;

import static ca.jrvs.apps.twitter.validation.Tweet.CREATED_AT_PATTERN;
import static ca.jrvs.apps.twitter.validation.Tweet.EMPTY_ID;
import static ca.jrvs.apps.twitter.validation.Tweet.EMPTY_STRING;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * Reference: https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/overview
 * https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/object-model/tweet
 * https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/object-model/user
 * https://developer.twitter.com/en/docs/twitter-api/v1/data-dictionary/object-model/geo
 */
//     "created_at":"Mon Feb 18 21:24:39 +0000 2019",
//         "id":1097607853932564480,
//         "id_str":"1097607853932564480",
//         "text":"test with loc223",
//         "entities":
//
//  {
//    "hashtags":[],      //Find the object definition in twitter docs
//    "user_mentions":[]  //Find the object definition in twitter docs
//  },
//      "coordinates":null,    //Find the object definition in twitter docs
//      "retweet_count":0,
//      "favorite_count":0,
//      "favorited":false,
//      "retweeted":false

public class Tweet implements JsonParser{

  public static final DateTimeFormatter twitterDatetimeFormat =
      new DateTimeFormatterBuilder().appendPattern(CREATED_AT_PATTERN).toFormatter();
  // String UTC time when this Tweet was created. Example:
  private ZonedDateTime createdAt;
  //
  private Long id;
  //
  private String idStr;
  //
  private String text;
  //
  private Entities entities;
  //
  private Coordinates coordinates;
  //
  private int retweetCount;
  //
  private int favoriteCount;
  //
  private boolean favorited;
  //
  private boolean retweeted;

  public Tweet(String createdAt, Long id, String idStr, String text, Entities entities,
      Coordinates coordinates, int retweetCount, int favoriteCount, boolean favorited,
      boolean retweeted) {
    this.createdAt =
        createdAt == null ? null : ZonedDateTime.parse(createdAt, twitterDatetimeFormat);
//        ZonedDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME);

    // TODO based on Twitter api contract, id always== idStr: then, derive one from the other
    this.idStr = idStr;
    this.id = Long.parseLong(idStr);
    this.text = text;
    this.entities = entities;
    this.coordinates = coordinates;
    this.retweetCount = retweetCount;
    this.favoriteCount = favoriteCount;
    this.favorited = favorited;
    this.retweeted = retweeted;
  }

  public Tweet(String text) {
    this(null, null, EMPTY_ID, text, null, null, 0, 0, false, false);
  }

  public Tweet(String text, Coordinates coordinates) {
    this(null, null, EMPTY_ID, text, null, coordinates, 0, 0, false, false);
  }

  public Tweet(String text, float longitude, float latitude) {
    this(null,null, EMPTY_ID, text, null, new Coordinates(longitude, latitude),
        0, 0, false, false);
  }

  public Tweet() {
    this(null,null, EMPTY_ID, EMPTY_STRING, null, null,
        0, 0, false, false);
  }


  // copy constructor
  // TODO: id and idStr copy should be removed or conditional if null or not
  public Tweet(Tweet from) {
    this.id = from.id;
    this.idStr = from.idStr;
    this.createdAt = from.createdAt;
    this.text = from.text;
    // TODO deep or shallow copy enough?
    //     this.entities = new Entities(from.entities);
    //     this.coordinates = new Coordinates(from.coordinates);
    this.entities = from.entities;
    this.coordinates = from.coordinates;
    this.retweetCount = from.retweetCount;
    this.favoriteCount = from.favoriteCount;
    this.favorited = from.favorited;
    this.retweeted = from.retweeted;

  }

  public static Tweet from(String json) {
    Tweet unmarshalledObject;
    try {
      unmarshalledObject = JsonParser.parseJson(json, Tweet.class);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "JSON cannot be parsed into object: Tweet", e);
    }
    return unmarshalledObject;
  }

  @JsonProperty("created_at")
  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  @JsonProperty("created_at")
  public void setCreatedAt(String createdAt) {
    this.createdAt = ZonedDateTime.parse(createdAt, twitterDatetimeFormat);
  }

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @JsonProperty("id_str")
  public String getIdStr() {
    return idStr;
  }

  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  @JsonProperty("entities")
  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  @JsonProperty("coordinates")
  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  @JsonProperty("retweet_count")
  public int getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }

  @JsonProperty("favorite_count")
  public int getFavoriteCount() {
    return favoriteCount;
  }

  public void setFavoriteCount(int favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  @JsonProperty("favorited")
  public boolean isFavorited() {
    return favorited;
  }

  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  @JsonProperty("retweeted")
  public boolean isRetweeted() {
    return retweeted;
  }

  public void setRetweeted(boolean retweeted) {
    this.retweeted = retweeted;
  }

}
