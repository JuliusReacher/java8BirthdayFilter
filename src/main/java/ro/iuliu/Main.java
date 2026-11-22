package ro.iuliu;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) throws IOException {
    if (args.length != 3) {
      throw new IllegalArgumentException("Expected an input file, a month and an output file.");
    }

    Path inputFile = Paths.get(args[0]);
    int month = Integer.parseInt(args[1]);
    Path outputFile = Paths.get(args[2]);

    if (month < 1 || month > 12) {
      throw new IllegalArgumentException("Month must be between 1 and 12.");
    }

    BirthdayCsvProcessor processor = new BirthdayCsvProcessor();
    processor.process(inputFile, month, outputFile);
  }
}
