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
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

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
                || age(person)  >= 63
                || (person.getGender().equals("Female")
                        && age(person)  >= 58));

        Stream.concat(
                Stream.concat(hospitalized, kidsAndretired),
                        houses.stream()
                                .flatMap(h -> h.getPersonList().stream())
                )
                .distinct()
                .forEachOrdered(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        //        Продолжить...
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }

    private static long age(Person person){
        LocalDate birthday = person.getDateOfBirth();
        return ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
}
