package MysticOrbs.exceptions;

import MysticOrbs.oven.Oven;

public class CannotFixException extends RuntimeException {

  public enum Reason {
    IS_NOT_BROKEN,
    FIXED_MAXIMUM_TIMES,
    NOT_ENOUGH_RESOURCES
  }

  private final Reason reason;
  private final Oven oven;

  /**
   * constructor
   */
  public CannotFixException(Oven oven, Reason reason) {
    this.oven = oven;
    this.reason = reason;

  }

  public Oven getOven() {
    return oven;
  }

  public Reason getReason() {
    return reason;
  }
}
