package ca.jrvs.apps.twitter.controller;

import java.util.List;

public class TweetArgsParserConfig {

  //TODO actions associated with each list of options should be added here and...
  //TODO ...this class should become an inner class/structure/enum
  private List<String> list;
  private String Separator;

  public TweetArgsParserConfig() {
  }

  public TweetArgsParserConfig(List<String> list, String separator) {
    this.list = list;
    Separator = separator;
  }

  public List<String> getList() {
    return list;
  }

  public void setList(List<String> list) {
    this.list = list;
  }

  public String getSeparator() {
    return Separator;
  }

  public void setSeparator(String separator) {
    Separator = separator;
  }
}
