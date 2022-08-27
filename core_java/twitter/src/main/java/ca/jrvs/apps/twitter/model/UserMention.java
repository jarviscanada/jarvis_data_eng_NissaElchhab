package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;

public class UserMention implements JsonParser {

  //
  private Long id;

  //
  private String idStr;

  //
  private int[] indices;

  //
  private String name;

  //
  private String screenName;

  public UserMention() {
  }

  public UserMention(Long id, String idStr, int[] indices, String name, String screenName) {
    this.id = id;
    this.idStr = idStr;
    this.indices = indices;
    this.name = name;
    this.screenName = screenName;
  }

  public static UserMention from(String json) {
    UserMention unmarshalledObject;
    try {
      unmarshalledObject = JsonParser.unmarshall(json, UserMention.class);
    } catch (IOException e) {
      throw new IllegalArgumentException(
          "JSON cannot be parsed into object", e);
    }
    return unmarshalledObject;
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

  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }
}
