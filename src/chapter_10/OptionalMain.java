package chapter_10;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OptionalMain {

    public String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("UnKnown");
    }

    public Set<String> getCarInsuranceNames(List<Person> peoples) {
        return peoples.stream()
                .map(Person::getCar)
                .map(optCar -> optCar.flatMap(Car::getInsurance))
                .map(optInsurance -> optInsurance.map(Insurance::getName))
                .map(optStr -> optStr.orElse("UnKnown"))
                .collect(Collectors.toSet());
    }

}
