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

public class HashtagTest {

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
            "text":"documentation",
            "indices":[
               211,
               225
            ]
         },
         {
            "text":"parsingJSON",
            "indices":[
               226,
               238
            ]
         },
         {
            "text":"GeoTagged",
            "indices":[
               239,
               249
            ]
         }
      ]
    */

    exampleInputJson = "     [\n"
        + "         {\n"
        + "            \"text\":\"documentation\",\n"
        + "            \"indices\":[\n"
        + "               211,\n"
        + "               225\n"
        + "            ]\n"
        + "         },\n"
        + "         {\n"
        + "            \"text\":\"parsingJSON\",\n"
        + "            \"indices\":[\n"
        + "               226,\n"
        + "               238\n"
        + "            ]\n"
        + "         },\n"
        + "         {\n"
        + "            \"text\":\"GeoTagged\",\n"
        + "            \"indices\":[\n"
        + "               239,\n"
        + "               249\n"
        + "            ]\n"
        + "         }\n"
        + "      ]";

    exampleInputJsonList = Arrays.asList("{\n"
            + "            \"text\":\"documentation\",\n"
            + "            \"indices\":[\n"
            + "               211,\n"
            + "               225\n"
            + "            ]\n"
            + "         }",
        " {\n"
            + "            \"text\":\"parsingJSON\",\n"
            + "            \"indices\":[\n"
            + "               226,\n"
            + "               238\n"
            + "            ]\n"
            + "         }",
        " {\n"
            + "            \"text\":\"GeoTagged\",\n"
            + "            \"indices\":[\n"
            + "               239,\n"
            + "               249\n"
            + "            ]\n"
            + "         }");
  }

  @Before
  public void setUp() throws Exception {
    List<Hashtag> expected = Arrays.asList(
        new Hashtag("documentation", Arrays.asList(211, 225)),
        new Hashtag("parsingJSON", Arrays.asList(226, 238)),
        new Hashtag("GeoTagged", Arrays.asList(239, 249)));
  }

  @Test
  public void shouldDeserializeExampleJsonIntoSimilarObject() {
    List<Hashtag> expected = Arrays.asList(
        new Hashtag("documentation", Arrays.asList(211, 225)),
        new Hashtag("parsingJSON", Arrays.asList(226, 238)),
        new Hashtag("GeoTagged", Arrays.asList(239, 249)));

    List<Hashtag> parsedList = exampleInputJsonList.stream().map(Hashtag::from).collect(
        Collectors.toList());

    for (int i = 0; i < parsedList.size(); ++i) {
      Assertions.assertThat(parsedList.get(i))
          .isEqualToComparingFieldByFieldRecursively(expected.get(i));
    }
  }

  @Test
  public void jsonParserShouldDeserializeExampleJsonIntoSimilarObject() {
    List<Hashtag> expected = Arrays.asList(
        new Hashtag("documentation", Arrays.asList(211, 225)),
        new Hashtag("parsingJSON", Arrays.asList(226, 238)),
        new Hashtag("GeoTagged", Arrays.asList(239, 249)));

    List<Hashtag> parsedList = exampleInputJsonList.stream().map(j -> {
      try {
        return JsonParser.parseJson(j,
            Hashtag.class);
      } catch (IOException e) {
        throw new RuntimeException(
            "Unexpected error while JsonParser::parseJson deserializing during HashtagTest", e);
      }
    }).collect(
        Collectors.toList());

    for (int i = 0; i < parsedList.size(); ++i) {
      Assertions.assertThat(parsedList.get(i))
          .isEqualToComparingFieldByFieldRecursively(expected.get(i));
    }
  }
}