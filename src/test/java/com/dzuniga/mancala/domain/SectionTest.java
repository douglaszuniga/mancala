package com.dzuniga.mancala.domain;

import com.dzuniga.mancala.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SectionTest {

  @Test
  public void testEquality() {
    Section alpha = Section.of(0, 6);
    Section beta = Section.of(0, 6);
    Section gama = Section.of(0, 6);
    Section delta = Section.of(7, 13);

    TestUtils.assertEqualsContract(alpha, beta, gama, delta);
  }

  /*
  TESTING PRECONDITIONS
   */
  @Test
  public void shouldReturnIllegalArgumentExceptionWhenSectionLowValueIsBelowZero() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> Section.of(-1, 10));
  }

  @Test
  public void shouldReturnIllegalArgumentExceptionWhenSectionHighValueIsAboveGameboardSize() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> Section.of(0, 100));
  }
}
