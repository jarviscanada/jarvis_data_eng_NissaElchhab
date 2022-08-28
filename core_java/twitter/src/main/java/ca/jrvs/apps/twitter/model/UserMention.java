package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.util.List;

public class UserMention implements JsonParser {

  //
  private Long id;

  //
  private String idStr;

  //
  private List<Integer> indices;

  //
  private String name;

  //
  private String screenName;

  public UserMention() {
  }

  public UserMention(Long id, String idStr, List<Integer> indices, String name, String screenName) {
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
          "JSON cannot be parsed into object UserMention", e);
    }
    return unmarshalledObject;
  }

  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Long id) {
    this.id = id;
  }

  @JsonProperty("id_str")
  public String getIdStr() {
    return idStr;
  }

  @JsonProperty("id_str")
  public void setIdStr(String idStr) {
    this.idStr = idStr;
  }

  @JsonProperty("indices")
  public List<Integer> getIndices() {
    return indices;
  }

  public void setIndices(List<Integer> indices) {
    this.indices = indices;
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("screen_name")
  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }
}
