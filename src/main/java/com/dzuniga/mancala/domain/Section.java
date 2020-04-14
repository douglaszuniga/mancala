package com.dzuniga.mancala.domain;

import lombok.Data;
import org.apache.commons.lang3.Validate;

@Data
public class Section {
  private final int low;
  private final int high;

  private Section(int low, int high) {
    Validate.isTrue(low >= 0, "Low value must be greater or equals to zero");

    this.low = low;
    this.high = high;
  }

  public static Section sectionOf(int low, int high) {
    return new Section(low, high);
  }
}
