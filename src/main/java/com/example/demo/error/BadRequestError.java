package com.example.demo.error;

public class BadRequestError extends RuntimeException {

  public BadRequestError(String message) {
    super(message);
  }

  public static BadRequestError withId(String itemType, long id) {
    return new BadRequestError(itemType + " with id " + id + " not found");
  }

  public static BadRequestError missingField(String itemType) {
    return new BadRequestError("missing required field: " + itemType);
  }

}
