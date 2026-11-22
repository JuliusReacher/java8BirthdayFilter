package ro.iuliu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BirthdayCsvProcessor {

  public void process(Path inputFile, int month, Path outputFile) throws IOException {
    List<String> lines = Files.readAllLines(inputFile);
    List<Person> persons = new ArrayList<>();

    for (String line : lines) {
      if (line.trim().isEmpty() || line.equals("firstName,lastName,dateOfBirth")) {
        continue;
      }

      String[] fields = line.split(",");
      String firstName = fields[0].trim();
      String lastName = fields[1].trim();
      LocalDate dateOfBirth = LocalDate.parse(fields[2].trim());

      Person person = new Person(firstName, lastName, dateOfBirth);
      persons.add(person);
    }

    List<String> names =
        persons.stream()
            .filter(person -> person.getDateOfBirth().getMonthValue() == month)
            .sorted(Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName))
            .map(person -> person.getFirstName() + "," + person.getLastName())
            .collect(Collectors.toList());

    Files.write(outputFile, names);
  }
}
