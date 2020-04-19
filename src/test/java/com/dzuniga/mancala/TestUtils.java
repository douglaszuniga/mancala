package com.dzuniga.mancala;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

/** Provides a set of functions around the equal test */
public class TestUtils {
  /**
   * Assert equals contract
   *
   * @param o1 first object
   * @param o2 second equal object
   * @param o3 third equal object
   * @param o4 fourth different object
   * @param <T> type of the object
   */
  public static <T> void assertEqualsContract(T o1, T o2, T o3, T o4) {
    assertReflexive(o1);
    assertSymmetric(o1, o2);
    assertHashCodeEquality(o1, o2);
    assertTransitive(o1, o2, o3);
    assertNonNullity(o1);
    assertDifferent(o1, o4);
  }
  /** For any non-null reference api x, x.equals(x) should return true. */
  private static <T> void assertReflexive(T o) {
    assertEquals("Object o should be reflexibly equal to itself.", o, o);
  }

  /**
   * For any non-null reference values x and y, x.equals(y) should return true if and only if
   * y.equals(x) returns true.
   */
  private static <T> void assertSymmetric(T o1, T o2) {
    assertEquals("o1 and o2 should be symmetrically equal to each other.", o1, o2);
    assertEquals("o2 and o1 should be symmetrically equal to each other.", o2, o1);
  }

  private static <T> void assertHashCodeEquality(T o1, T o2) {
    assertEquals(
        "if o1 and 02 are equal their hash codes should be equal as well",
        o1.hashCode(),
        o2.hashCode());
  }

  /**
   * For any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z)
   * returns true, then x.equals(z) should return true.
   */
  private static <T> void assertTransitive(T o1, T o2, T o3) {
    assertEquals("o1 should transitively be equal to o2.", o1, o2);
    assertEquals("o2 should transitively be equal to o3.", o2, o3);
    assertEquals("o1 should transitively be equal to o3.", o1, o3);
  }

  /** For any non-null reference api x, x.equals(null) should return false. */
  private static <T> void assertNonNullity(T o1) {
    assertNotEquals("o1 should not be equals to null!", null, o1);
  }

  /** Test that o1 and o2 are different */
  private static <T> void assertDifferent(T o1, T o2) {
    assertNotEquals("o1 should not be equals to o2.", o1, o2);
  }
}
