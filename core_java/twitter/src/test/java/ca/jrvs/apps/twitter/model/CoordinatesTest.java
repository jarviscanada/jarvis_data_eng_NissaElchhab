package ca.jrvs.apps.twitter.model;

import ca.jrvs.apps.twitter.model.dto.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CoordinatesTest {
  public static String exampleInputJson;
  public static Tweet expectedObject;
  public static Tweet exampleInputObject;
  public static String expectedSerializedObject;

  @BeforeClass
  public static void beforeClass() throws Exception {
/*    {
      "coordinates":[
      -75.14310264,
          40.05701649
      ],
      "type":"Point"
    }*/

    exampleInputJson = "{\n"
        + "      \"coordinates\":[\n"
        + "         -75.14310264,\n"
        + "         40.05701649\n"
        + "      ],\n"
        + "      \"type\":\"Point\"\n"
        + "   }";
  }

  @Before
  public void setUp() throws Exception {
    Coordinates expected = new Coordinates(new float[]{-75.14310264f, 40.05701649f},
        "Point");
  }

  @Test
  public void  jsonParserShouldDeserializeExampleJsonIntoSimilarObject() {
    Coordinates expected = new Coordinates(new float[]{-75.14310264f, 40.05701649f},
        "Point");

    Coordinates parsed;
      try {
        parsed =  JsonParser.parseJson(exampleInputJson,
            Coordinates.class);
      } catch (IOException e) {
        throw new RuntimeException("Unexpected error while JsonParser::parseJson deserializing during CoordinatesTest", e);
      }

    Assertions.assertThat(parsed).isEqualToComparingFieldByFieldRecursively(expected);
    };

  @Test
  public void  shouldDeserializeExampleJsonIntoSimilarObject() {
    Coordinates expected = new Coordinates(new float[]{-75.14310264f, 40.05701649f},
        "Point");

    Coordinates parsed;

      parsed =Coordinates.from(exampleInputJson);


    Assertions.assertThat(parsed).isEqualToComparingFieldByFieldRecursively(expected);
  };



}