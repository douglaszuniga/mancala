package com.dzuniga.mancala.domain;

import lombok.Data;
import org.apache.commons.lang3.Validate;

/** Data class that contains a board section range */
@Data
public class Section {
  private final int low;
  private final int high;

  private Section(int low, int high) {
    Validate.isTrue(low >= 0, "Low value must be greater or equals to zero");
    Validate.isTrue(high < Gameboard.SIZE, "High value must be less than the size of the gameboard");

    this.low = low;
    this.high = high;
  }

  /**
   * Factory method to create a new section
   * @param low start of the section in the board
   * @param high end of the section in the board
   * @return new {@link Section}
   */
  public static Section of(int low, int high) {
    return new Section(low, high);
  }
}
