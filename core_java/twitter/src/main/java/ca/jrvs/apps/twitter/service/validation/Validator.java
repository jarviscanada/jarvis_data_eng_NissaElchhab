package ca.jrvs.apps.twitter.service.validation;

import ca.jrvs.apps.twitter.model.dto.JsonParser;

public interface Validator<T extends JsonParser> {

  static boolean isNotNull(Object o) {
    return null != o;
  }

  static boolean isNull(Object o) {
    return null == o;
  }

  static boolean isValueBetweenInclusive(int value, int min, int max) {
    if (max < min) {
      throw new RuntimeException(
          "Validation#isValueBetween arguments: max cannot be strictly smaller than min. Unexpected error");
    }
    return value >= min && value <= max;
  }

  static boolean isValueBetweenInclusive(float value, float min, float max) {
    if (Float.compare(max, min) < 0) {
      throw new RuntimeException(
          "Validation#isValueBetween arguments: max cannot be strictly smaller than min. Unexpected error");
    }
    return Float.compare(value, min) >= 0 && Float.compare(value, max) <= 0;
  }

  default boolean isValid(T modelOrDto) {
    return false;
  }

//  default boolean isNotValid(T modelOrDto) {
//    return !isValid(modelOrDto);
//  }
//
//  default boolean isValid() {
//    return isValid((T) this);
//  }
//
//  default boolean isNotValid() {
//    return isNotValid((T) this);
//  }

  default boolean isBetweenInclusive(int value, int min, int max) {
    return isValueBetweenInclusive(value, min, max);
  }

  default boolean isBetweenInclusive(float value, float min, float max) {
    return isValueBetweenInclusive(value, min, max);
  }
}
