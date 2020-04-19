package com.dzuniga.mancala.controller;

import com.dzuniga.mancala.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestErrorTest {
  @Test
  public void testEquality() {
    RestError alpha = new RestError(HttpStatus.ACCEPTED, "this is a message");
    RestError beta = new RestError(HttpStatus.ACCEPTED, "this is a message");
    RestError gama = new RestError(HttpStatus.ACCEPTED, "this is a message");
    RestError delta = new RestError(HttpStatus.CREATED, "this is a message");

    TestUtils.assertEqualsContract(alpha, beta, gama, delta);
  }

  @Test
  public void shouldThrowNullPointerExceptionWhenStatusIsNull() {
    assertThrows(NullPointerException.class, () -> RestError.builder().message("random").build());
  }

  @Test
  public void testToString() {
    // language=JSON
    final String expected = "{\"status\":\"202 ACCEPTED\",\"message\":\"message\"}";
    final RestError actual =
        RestError.builder().message("message").status(HttpStatus.ACCEPTED).build();
    assertEquals(expected, actual.toString());
  }
}
