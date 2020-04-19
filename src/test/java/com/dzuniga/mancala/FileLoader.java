package com.dzuniga.mancala;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

/**
 * Handy interface that load a file (JSON) from system and returns its content as String Used in the
 * integration tests
 */
public interface FileLoader {
  /**
   * read and load file content into a String
   *
   * @param path path of the file
   * @return String with its content
   * @throws IOException When there is an issue finding or loading the file
   */
  default String loadJsonFromFile(final String path) throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
      if (Objects.isNull(inputStream)) {
        return StringUtils.EMPTY;
      }
      Scanner s = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A");
      return s.hasNext() ? s.next() : "";
    }
  }
}
