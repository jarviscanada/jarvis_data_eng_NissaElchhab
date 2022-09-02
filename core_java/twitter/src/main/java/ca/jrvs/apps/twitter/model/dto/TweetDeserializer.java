package ca.jrvs.apps.twitter.model.dto;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.service.validation.Tweet;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import java.io.IOException;

public class TweetDeserializer extends StdDeserializer<Tweet> {

  public static final String[] DEFAULT_FIELDS = {""};
  private final ObjectMapper om = new ObjectMapper();
  private JsonNode node;
  private String[] fields;


  public TweetDeserializer() {
    this(null);
  }

  /**
   * initializes the list of fields that are going to be matched for selection fields==null is
   * equivalent to an empty field
   *
   * @param
   */
  public TweetDeserializer(Class<?> vc) {
    super(vc);
  }

  public String[] getFields() {
    return fields;
  }

  public void setFields(String[] fields) {
    this.fields = fields == null ? DEFAULT_FIELDS : fields;
  }

  /**
   * Method that can be called to ask implementation to deserialize JSON content into the value type
   * this serializer handles. Returned instance is to be constructed by method itself.
   * <p>
   * Pre-condition for this method is that the parser points to the first event that is part of
   * value to deserializer (and which is never JSON 'null' literal, more on this below): for simple
   * types it may be the only value; and for structured types the Object start marker or a
   * FIELD_NAME.
   * </p>
   * <p>
   * The two possible input conditions for structured types result from polymorphism via fields. In
   * the ordinary case, Jackson calls this method when it has encountered an OBJECT_START, and the
   * method implementation must advance to the next token to see the first field name. If the
   * application configures polymorphism via a field, then the object looks like the following.
   * <pre>
   *      {
   *          "@class": "class name",
   *          ...
   *      }
   *  </pre>
   * Jackson consumes the two tokens (the <tt>@class</tt> field name and its value) in order to
   * learn the class and select the deserializer. Thus, the stream is pointing to the FIELD_NAME for
   * the first field after the @class. Thus, if you want your method to work correctly both with and
   * without polymorphism, you must begin your method with:
   * <pre>
   *       if (p.getCurrentToken() == JsonToken.START_OBJECT) {
   *         p.nextToken();
   *       }
   *  </pre>
   * This results in the stream pointing to the field name, so that the two conditions align.
   * <p>
   * Post-condition is that the parser will point to the last event that is part of deserialized
   * value (or in case deserialization fails, event that was not recognized or usable, which may be
   * the same event as the one it pointed to upon call).
   * <p>
   * Note that this method is never called for JSON null literal, and thus deserializers need (and
   * should) not check for it.
   *
   * @param jsonParser Parsed used for reading JSON content
   * @param ctxt       Context that can be used to access information about this deserialization
   *                   activity.
   * @return Deserialized value
   */
  @Override
  public Tweet deserialize(JsonParser jsonParser, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    node = jsonParser.getCodec().readTree(jsonParser);
    // TODO note ideally we should use a HashMap<property, value> both here and in...
    // ... new tweet constructor with a switch to enable for extensibility
    String createdAt = getString("created_at");
    Long id = (Long) getNumber("id");
    String idStr = getString("id_str");
    String text = getString("text");
    int retweetCount = (Integer) getNumber("retweet_count");
    int favoriteCount = (Integer) getNumber("favorite_count");
    boolean favorited = getBoolean("favorited");
    boolean retweeted = getBoolean("retweeted");
    Entities entities = geObject("entities", Entities.class);
    Coordinates coordinates = geObject("coordinates", Coordinates.class);

    Tweet tweet = new Tweet();
    tweet.setCreatedAt(createdAt);
    tweet.setId(id);
    tweet.setIdStr(idStr);
    tweet.setText(text);
    tweet.setRetweetCount(retweetCount);
    tweet.setFavoriteCount(favoriteCount);
    tweet.setRetweeted(retweeted);
    tweet.setFavorited(favorited);
    tweet.setEntities(entities);
    tweet.setCoordinates(coordinates);
    return tweet;
  }

  private String getString(String key) {
    return this.node.get(key).asText();
  }

  private Number getNumber(String key) {
    JsonNode n = this.node.get(key);
    if (n.isLong()) {
      return (Long) ((LongNode) n).numberValue();
    } else if (n.isInt()) {
      return (Integer) ((IntNode) n).numberValue();
    } else {
      throw new RuntimeException("TweetDeserializer#getNumber unknown number format or NaN");
    }
  }

  private boolean getBoolean(String key) {
    return (Boolean) ((BooleanNode) this.node.get(key)).asBoolean();
  }

  private <T> T geObject(String key, Class<T> clazzOfKey) {
    JsonNode subNode = this.node.at(key);
    T object;
    try {
      object = om.treeToValue(subNode, clazzOfKey);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to convert subNode to equivalent clazz Object", e);
    }
    return object;
  }
}
