package ca.jrvs.apps.twitter.validation;

public class Validation<V extends Validator> implements Validator {

  public boolean isValid(V validatedModel) {
    return Validator.isNotNull(validatedModel) && this.isValid(validatedModel);
  }
}
