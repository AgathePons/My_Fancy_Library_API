package com.example.demo.error;

public class BadRequestError extends RuntimeException {

  public BadRequestError(String message) {
    super(message);
  }

  public static BadRequestError withId(String itemType, long id) {
    return new BadRequestError(itemType + " with id " + id + " not found");
  }

  public static BadRequestError missingFieldId(String itemType) {
    return new BadRequestError("missing id for required field: " + itemType);
  }

  public static BadRequestError deletionNotAllowedLinkedItem(String itemType,
                                                             String linkedItemType,
                                                             int numberOfLinkedItem) {
    return new BadRequestError(
            itemType
            + " deletion not allowed because it is linked to "
            + numberOfLinkedItem + " " + linkedItemType
    );
  }

}
