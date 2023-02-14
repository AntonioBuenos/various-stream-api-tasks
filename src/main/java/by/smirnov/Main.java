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
import java.util.Map;
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
        task16();
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
        List<Flower> catalog = flowers.stream()
                .sorted(
                        Comparator
                                .comparing(Flower::getOrigin, Comparator.reverseOrder())
                                .thenComparingInt(Flower::getPrice)
                                .thenComparing(Flower::getWaterConsumptionPerDay, Comparator.reverseOrder())
                )
                .toList();

        Double result = catalog.stream()
                .sorted(
                        Comparator.comparing(Flower::getCommonName).reversed()
                )
                .filter(flower -> flower.getCommonName().matches("^[C-S][a-zA-Z\\s]+"))
                .filter(Flower::isShadePreferred)
                .filter(flower -> flower.getFlowerVaseMaterial().contains("Aluminum")
                        || flower.getFlowerVaseMaterial().contains("Glass")
                        || flower.getFlowerVaseMaterial().contains("Steel"))
                .mapToDouble(flower ->
                        flower.getPrice() + (
                                flower.getWaterConsumptionPerDay()
                                        * getNumberOfDays(5)
                                        * 1.39 / 1000
                        ))
                .reduce(Double::sum)
                .orElse(0.0);
        System.out.printf("%.2f $%n", result);
    }

    private static void task16() throws IOException {
        List<Person> people = Util.getPersons();
        List<Flower> flowers = Util.getFlowers();
        Double result = people.stream()
                .collect(groupingBy(client -> {
                    String key = null;
                    if (client.getGender().equals("Male") && age(client) < 18) key = "youngMen";
                    else if (client.getGender().equals("Male") && age(client) >= 30 && age(client) < 51)
                        key = "businessMen";
                    else if (client.getGender().equals("Female") || age(client) > 65) key = "flowerLovers";
                    else key = "other";
                    return key;
                }, toList()))
                .entrySet().stream()
                .filter(entry -> !entry.getKey().equals("other"))
                .map(e -> Map.entry(
                        e.getKey(),
                        e.getValue().stream()
                                .sorted(Comparator
                                        .comparing(Person::getLastName, Comparator.reverseOrder())
                                        .thenComparing(Person::getFirstName)
                                )
                                .limit(10)
                                .toList()))
                .map(entry -> Map.entry(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(person -> {
                                    if (entry.getKey().equals("youngMen"))
                                        return Map.entry(
                                                person,
                                                flowers.stream()
                                                        .filter(f -> f.getPrice() < 40 && f.getWaterConsumptionPerDay() < 0.8)
                                                        .min(Comparator.comparing(Flower::getWaterConsumptionPerDay).thenComparing(Flower::getPrice))
                                                        .orElse(new Flower()));

                                    else if (entry.getKey().equals("businessMen"))
                                        return Map.entry(
                                                person,
                                                flowers.stream()
                                                        .filter(f -> f.getPrice() > 500 && f.getWaterConsumptionPerDay() < 0.5)
                                                        .min(Comparator.comparing(Flower::getPrice, Comparator.reverseOrder()).thenComparing(Flower::getWaterConsumptionPerDay))
                                                        .orElse(new Flower()));
                                    else return
                                                Map.entry(
                                                        person,
                                                        flowers.stream()
                                                                .filter(f -> f.getPrice() < 300 && f.getPrice() >= 40 && f.getWaterConsumptionPerDay() > 1)
                                                                .max(Comparator.comparing(Flower::getWaterConsumptionPerDay))
                                                                .orElse(new Flower()));
                                })
                                .toList()))
                .peek(entry -> System.out.printf("%s = %s%n", entry.getKey(), entry.getValue()))
                .map(entry -> entry.getValue().stream().mapToInt(e -> e.getValue().getPrice()).reduce(Integer::sum).orElse(0))
                .mapToDouble(Double::valueOf)
                .map(e -> e * 0.1 * 0.15)
                .reduce(Double::sum)
                .orElse(0);
        System.out.printf("%.2f $%n", result);
    }

    private static long getNumberOfDays(long years) {
        return ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusYears(years));
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

