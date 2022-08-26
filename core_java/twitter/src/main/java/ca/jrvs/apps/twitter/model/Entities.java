package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;
import java.util.List;

public class Entities implements JsonParser {

  //
  private List<Hashtag> hashtags;

  //
  private List<UserMention> userMentions;

  public Entities build(String json) {
    Entities entities;
    try {
      entities = this.parseJson(json);
    } catch (IOException e) {
      throw new IllegalArgumentException("JSON cannot be parsed into object", e);
    }
    return entities;
  }

  public List<Hashtag> getHashtags() {
    return hashtags;
  }

  public void setHashtags(List<Hashtag> hashtags) {
    this.hashtags = hashtags;
  }

  public List<UserMention> getUserMentions() {
    return userMentions;
  }

  public void setUserMentions(List<UserMention> userMentions) {
    this.userMentions = userMentions;
  }
}
