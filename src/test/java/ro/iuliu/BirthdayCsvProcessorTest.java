package ro.iuliu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BirthdayCsvProcessorTest {

  private Path inputFile;
  private Path outputFile;
  private BirthdayCsvProcessor processor;

  @BeforeEach
  void setUp() throws IOException {
    processor = new BirthdayCsvProcessor();
    inputFile = Files.createTempFile("persons-test-", ".csv");
    outputFile = Files.createTempFile("result-test-", ".csv");

    Files.write(
        inputFile,
        Arrays.asList(
            "firstName,lastName,dateOfBirth",
            "Ion,Popescu,1995-11-12",
            "Maria,Ionescu,1998-05-20",
            "Ana,Popescu,2001-11-10",
            "Mihai,Constantin,1992-11-25"));
  }

  @Test
  void shouldWriteMatchingNamesInAlphabeticalOrder() throws IOException {
    processor.process(inputFile, 11, outputFile);

    List<String> actual = Files.readAllLines(outputFile);
    List<String> expected = Arrays.asList("Mihai,Constantin", "Ana,Popescu", "Ion,Popescu");

    assertEquals(expected, actual);
    System.out.println("Expected = " + expected);
    System.out.println("Actual   = " + actual);
  }

  @Test
  void shouldWriteEmptyFileWhenNobodyIsBornInSelectedMonth() throws IOException {
    processor.process(inputFile, 2, outputFile);

    List<String> actual = Files.readAllLines(outputFile);

    assertTrue(actual.isEmpty());
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(inputFile);
    Files.deleteIfExists(outputFile);
  }
}
