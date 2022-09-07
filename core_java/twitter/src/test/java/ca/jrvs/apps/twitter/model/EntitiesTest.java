package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntitiesTest {

  public static String exampleInputJson;
  public static Tweet expectedObject;
  public static Tweet exampleInputObject;
  public static String expectedSerializedObject;

  @BeforeClass
  public static void beforeClass() throws Exception {
/*
{
      "hashtags":  [
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
      ],
      "user_mentions":[
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
   }

    */

    exampleInputJson = "{\n"
        + "      \"hashtags\":  [\n"
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
        + "      ],\n"
        + "      \"user_mentions\":[\n"
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
        + "      ]\n"
        + "   }";
  }

  @Before
  public void setUp() throws Exception {
    List<Hashtag> expectedHashtags = Arrays.asList(
        new Hashtag("documentation", Arrays.asList(211, 225)),
        new Hashtag("parsingJSON", Arrays.asList(226, 238)),
        new Hashtag("GeoTagged", Arrays.asList(239, 249)));

    List<UserMention> expectedUserMentions = Arrays.asList(
        new UserMention(6253282L, "6253282", Arrays.asList(4, 15),
            "Twitter API", "twitterapi"),
        new UserMention(1253282L, "1253282", Arrays.asList(6, 22),
            "Google API", "googleapi"));

    Entities expected = new Entities(expectedHashtags, expectedUserMentions);
  }


  @Test
  public void classBuilderShouldDeserializeExampleJsonIntoSimilarObject() {
    List<Hashtag> expectedHashtags = Arrays.asList(
        new Hashtag("documentation", Arrays.asList(211, 225)),
        new Hashtag("parsingJSON", Arrays.asList(226, 238)),
        new Hashtag("GeoTagged", Arrays.asList(239, 249)));

    List<UserMention> expectedUserMentions = Arrays.asList(
        new UserMention(6253282L, "6253282", Arrays.asList(4, 15),
            "Twitter API", "twitterapi"),
        new UserMention(1253282L, "1253282", Arrays.asList(6, 22),
            "Google API", "googleapi"));

    Entities expected = new Entities(expectedHashtags, expectedUserMentions);
    Entities parsed = Entities.from(exampleInputJson);

    Assertions.assertThat(parsed)
        .isEqualToComparingFieldByFieldRecursively(expected);
  }


  @Test
  public void jsonParserShouldDeserializeExampleJsonIntoSimilarObject() throws IOException {
    List<Hashtag> expectedHashtags = Arrays.asList(
        new Hashtag("documentation", Arrays.asList(211, 225)),
        new Hashtag("parsingJSON", Arrays.asList(226, 238)),
        new Hashtag("GeoTagged", Arrays.asList(239, 249)));

    List<UserMention> expectedUserMentions = Arrays.asList(
        new UserMention(6253282L, "6253282", Arrays.asList(4, 15),
            "Twitter API", "twitterapi"),
        new UserMention(1253282L, "1253282", Arrays.asList(6, 22),
            "Google API", "googleapi"));

    Entities expected = new Entities(expectedHashtags, expectedUserMentions);

    Entities parsed = JsonParser.parseJson(exampleInputJson, Entities.class);

    Assertions.assertThat(parsed)
        .isEqualToComparingFieldByFieldRecursively(expected);
  }

}