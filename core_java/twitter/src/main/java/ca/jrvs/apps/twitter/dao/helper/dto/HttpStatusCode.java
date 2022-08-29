package ca.jrvs.apps.twitter.dao.helper.dto;

public enum HttpStatusCode {
  CONTINUE(100),
  OK(200),
  CREATED(201),
  NO_CONTENT(204),
  MOVED_PERMANENTLY(301),
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
    FORBIDDEN(403),
  NOT_FOUND(404),
  METHOD_NOT_ALLOWED(405),
  INTERNAL_SERVER_ERROR(500);

  private final int statusCode;
  HttpStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return this.statusCode;
  }
}
