package by.smirnov;

import by.smirnov.model.Animal;
import by.smirnov.model.Car;
import by.smirnov.model.Flower;
import by.smirnov.model.House;
import by.smirnov.model.Person;
import by.smirnov.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() >= 10 && a.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getOrigin().equals("Japanese") && a.getGender().equals("Female"))
                .map(Animal::getBread)
                .forEach(a -> System.out.println(a.toUpperCase()));
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(o -> o.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(a -> a.getGender().equals("Female"))
                .count());
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(a -> a.getAge() >= 20 && a.getAge() <= 30)
                .anyMatch(a -> a.getOrigin().equals("Hungarian")));
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        List<String> maleAnimals = animals.stream()
                .filter(a -> a.getGender().equals("Male"))
                .map(Animal::getBread)
                .distinct()
                .sorted()
                .toList();
        List<String> femaleAnimals = animals.stream()
                .filter(a -> a.getGender().equals("Female"))
                .map(Animal::getBread)
                .distinct()
                .sorted()
                .toList();
        System.out.println(maleAnimals.equals(femaleAnimals));
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .noneMatch(a -> a.getOrigin().equals("Oceania")));
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .map(Animal::getAge)
                .max(Integer::compareTo)
                .orElse(-1));
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .map(a -> a.length)
                .min(Integer::compareTo)
                .orElse(-1));
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .map(Animal::getAge)
                .reduce(Integer::sum)
                .orElse(-1));
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(a -> a.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .orElse(-1));
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(person -> person.getGender().equals("Male")
                        && age(person) >= 18
                        && age(person) < 27)
                .sorted(Comparator.comparingInt(Person::getRecruitmentGroup))
                .limit(200)
                .forEachOrdered(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        Stream<Person> hospitalized = houses.stream()
                .filter(h -> h.getBuildingType().equals("Hospital"))
                .flatMap(h -> h.getPersonList().stream());
        Stream<Person> kidsAndretired = houses.stream()
                .filter(h -> !h.getBuildingType().equals("Hospital"))
                .flatMap(h -> h.getPersonList().stream())
                .filter(person -> age(person) < 18
                        || age(person) >= 63
                        || (person.getGender().equals("Female")
                        && age(person) >= 58));

        Stream.concat(
                        Stream.concat(hospitalized, kidsAndretired),
                        houses.stream()
                                .flatMap(h -> h.getPersonList().stream())
                )
                .distinct()
                .limit(500)
                .forEachOrdered(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        Double result = cars.stream().map(c -> Map.entry(getDirection(c), c))
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())))
                .entrySet().stream()
                .map(e -> Map.entry(
                        e.getKey(),
                        e.getValue().stream()
                                .map(car -> Double.valueOf(car.getMass())).reduce(Double::sum).orElse(0.0)))
                .filter(entry -> !entry.getKey().equals("other"))
                .map(entry -> Map.entry(entry.getKey(), Math.round(entry.getValue() / 1000 * 7.14 * 100) / 100.0))
                .peek(System.out::println)
                .mapToDouble(Map.Entry::getValue)
                .reduce(Double::sum)
                .orElse(0);
        System.out.println(result);
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }

    private static long age(Person person) {
        LocalDate birthday = person.getDateOfBirth();
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }

    private static String getDirection(Car c) {
        if (c.getCarMake().equals("Jaguar")
                || c.getColor().equals("White"))
            return "Turkmenistan";
        else if (c.getMass() < 1500
                || c.getCarMake().equals("BMW")
                || c.getCarMake().equals("Lexus")
                || c.getCarMake().equals("Chrysler")
                || c.getCarMake().equals("Toyota")) return "Uzbekistan";
        else if (c.getCarMake().equals("GMC")
                || c.getCarMake().equals("Dodge")
                || (c.getColor().equals("Black") && c.getMass() > 4000)) return "Kazakhstan";
        else if (c.getReleaseYear() < 1982
                || c.getCarModel().equals("Civic")
                || c.getCarModel().equals("Cherokee")) return "Kyrgyzstan";
        else if (c.getPrice() > 40000
                || (
                !c.getColor().equals("Yellow")
                        && !c.getColor().equals("Red")
                        && !c.getColor().equals("Green")
                        && !c.getColor().equals("Blue")
        )) return "Russia";
        else if (c.getVin().contains("59")) return "Mongolia";
        else return "other";
    }
}

