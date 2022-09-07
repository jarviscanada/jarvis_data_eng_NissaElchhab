package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserMentionTest {

  public static String exampleInputJson;
  public static List<String> exampleInputJsonList;
  public static Tweet expectedObject;
  public static Tweet exampleInputObject;
  public static String expectedSerializedObject;

  @BeforeClass
  public static void beforeClass() throws Exception {
/*
[
         {
            "name":"Twitter API",
            "indices":[
               4,
               15
            ],
            "screen_name":"twitterapi",
            "id":6253282,
            "id_str":"6253282"
         },

          {
            "name":"Google API",
            "indices":[
               6,
               22
            ],
            "screen_name":"googleapi",
            "id":1253282,
            "id_str":"1253282"
         }

      ]
    */

    exampleInputJson = "[\n"
        + "         {\n"
        + "            \"name\":\"Twitter API\",\n"
        + "            \"indices\":[\n"
        + "               4,\n"
        + "               15\n"
        + "            ],\n"
        + "            \"screen_name\":\"twitterapi\",\n"
        + "            \"id\":6253282,\n"
        + "            \"id_str\":\"6253282\"\n"
        + "         },\n"
        + "\n"
        + "          {\n"
        + "            \"name\":\"Google API\",\n"
        + "            \"indices\":[\n"
        + "               6,\n"
        + "               22\n"
        + "            ],\n"
        + "            \"screen_name\":\"googleapi\",\n"
        + "            \"id\":1253282,\n"
        + "            \"id_str\":\"1253282\"\n"
        + "         }\n"
        + "\n"
        + "      ]";

    exampleInputJsonList = Arrays.asList("         {\n"
            + "            \"name\":\"Twitter API\",\n"
            + "            \"indices\":[\n"
            + "               4,\n"
            + "               15\n"
            + "            ],\n"
            + "            \"screen_name\":\"twitterapi\",\n"
            + "            \"id\":6253282,\n"
            + "            \"id_str\":\"6253282\"\n"
            + "         }",
        " {\n"
            + "            \"name\":\"Google API\",\n"
            + "            \"indices\":[\n"
            + "               6,\n"
            + "               22\n"
            + "            ],\n"
            + "            \"screen_name\":\"googleapi\",\n"
            + "            \"id\":1253282,\n"
            + "            \"id_str\":\"1253282\"\n"
            + "         }");
  }

  @Before
  public void setUp() throws Exception {
    List<UserMention> expected = Arrays.asList(
        new UserMention(6253282L, "6253282", Arrays.asList(4, 15),
            "Twitter API", "twitterapi"),
        new UserMention(1253282L, "1253282", Arrays.asList(6, 22),
            "Google API", "googleapi"));
  }


  @Test
  public void classBuilderShouldDeserializeExampleJsonIntoSimilarObject() {
    List<UserMention> expected = Arrays.asList(
        new UserMention(6253282L, "6253282", Arrays.asList(4, 15),
            "Twitter API", "twitterapi"),
        new UserMention(1253282L, "1253282", Arrays.asList(6, 22),
            "Google API", "googleapi"));

    List<UserMention> parsedList = exampleInputJsonList.stream().map(UserMention::from).collect(
        Collectors.toList());

    for (int i = 0; i < parsedList.size(); ++i) {
      Assertions.assertThat(parsedList.get(i))
          .isEqualToComparingFieldByFieldRecursively(expected.get(i));
    }
  }

  @Test
  public void jsonParserShouldDeserializeExampleJsonIntoSimilarObject() {
    List<UserMention> expected = Arrays.asList(
        new UserMention(6253282L, "6253282", Arrays.asList(4, 15),
            "Twitter API", "twitterapi"),
        new UserMention(1253282L, "1253282", Arrays.asList(6, 22),
            "Google API", "googleapi"));

    List<UserMention> parsedList = exampleInputJsonList.stream().map(j -> {
      try {
        return JsonParser.parseJson(j,
            UserMention.class);
      } catch (IOException e) {
        throw new RuntimeException(
            "Unexpected error while JsonParser::parseJson deserializing during UserMentionTest", e);
      }
    }).collect(
        Collectors.toList());

    for (int i = 0; i < parsedList.size(); ++i) {
      Assertions.assertThat(parsedList.get(i))
          .isEqualToComparingFieldByFieldRecursively(expected.get(i));
    }
  }

}