package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;
import java.time.ZonedDateTime;

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

public class Tweet implements JsonParser {

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

  public Tweet build(String json) {
    Tweet tweet;
    try {
      tweet = this.parseJson(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("JSON cannot be parsed into object", e);
    }
    return tweet;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIdStr() {
    return idStr;
  }

  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }

  public int getFavoriteCount() {
    return favoriteCount;
  }

  public void setFavoriteCount(int favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  public boolean isFavorited() {
    return favorited;
  }

  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  public boolean isRetweeted() {
    return retweeted;
  }

  public void setRetweeted(boolean retweeted) {
    this.retweeted = retweeted;
  }
}
