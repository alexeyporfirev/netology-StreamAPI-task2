import person.Person;
import person.features.Education;
import person.features.Sex;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        Stream<Person> streamUnder18 = persons.stream();
        long personUnder18Count = streamUnder18.filter(x -> x.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + personUnder18Count);


        Stream<Person> streamArmy = persons.stream();
        List<String> personArmy = streamArmy.filter(x -> x.getSex() == Sex.MAN)
                .filter(x -> x.getAge() >= 18 && x.getAge() < 27)
                .map(x -> x.getFamily())
                .collect(Collectors.toList());
        System.out.println("\n\nСписок призывников: ");
        printList(personArmy, 20);


        Stream<Person> streamWorkers = persons.stream();
        List<Person> personWorker = streamWorkers.filter(x -> x.getEducation() == Education.HIGHER)
                .filter(x -> x.getAge() >= 18)
                .filter(x -> (x.getSex() == Sex.WOMAN && x.getAge() < 60) || (x.getSex() == Sex.MAN && x.getAge() < 65))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());
        System.out.println("\n\nСписок потенциально работоспособных людей с высшим образованием: ");
        printList(personWorker, 20);
    }

    /**
     * Метод вывода заданного количества элементов из списка
     * @param list Список с элементами
     * @param numberOfPrintedNames Количество элементов, которые будут выведены
     */
    private static <T> void printList(List<T> list, int numberOfPrintedNames) {
        int i = 0;
        for(T item: list) {
            System.out.println(item.toString());
            if (i > numberOfPrintedNames)
                break;
            i++;
        }
    }


}
