package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
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



}
