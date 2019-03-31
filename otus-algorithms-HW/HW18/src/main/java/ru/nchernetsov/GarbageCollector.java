package ru.nchernetsov;

/**
 * Имитация сборщика мусора на основе графа зависимостей
 */
public class GarbageCollector {

    int a = 10;
    String b = "Hello";

    ClassA[] array = new ClassA[]{new ClassA(1), new ClassA(2), new ClassA(3)};

    public static void main(String[] args) {
        ObjectTraversal objectTraversal = new ObjectTraversal();

        String json = objectTraversal.convertObjectToJSON(new GarbageCollector());

        System.out.println(json);
    }
}

class ClassA {
    private final int a;

    public ClassA(int a) {
        this.a = a;
    }
}
