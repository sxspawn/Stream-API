package ru.kovbasa.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(names.get(new Random().nextInt(names.size())), //
                    families.get(new Random().nextInt(families.size())), //
                    new Random().nextInt(100), //
                    Sex.values()[new Random().nextInt(Sex.values().length)], //
                    Education.values()[new Random().nextInt(Education.values().length)])//
            );
        }

        // 1. Найти количество несовершеннолетних (т.е. людей младше 18 лет).
        long underage = persons.stream()//
                .filter(p -> p.getAge() < 18)//
                .count();
        System.out.println("Количество несовершеннолетних: " + underage);

        // 2. Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
        List<String> conscript = persons.stream()//
                .filter(p -> p.getSex().equals(Sex.MAN))//
                .filter(p -> p.getAge() >= 18)//
                .filter(p -> p.getAge() < 27)//
                .map(Person::getFamily)//
                .collect(Collectors.toList());
        System.out.println("Список призывников: " + conscript);

        // 3. Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
        List<Person> employable = persons.stream()//
                .filter(p -> p.getEducation().equals(Education.HIGHER))//
                .filter(p -> p.getAge() >= 18)//
                .filter(p -> p.getSex().equals(Sex.WOMAN) && p.getAge() < 60 || p.getSex().equals(Sex.MAN) && p.getAge() < 65)//
                .sorted(Comparator.comparing(Person::getFamily))//
                .collect(Collectors.toList());

        System.out.println("Список работоспособных: " + employable);
    }
}