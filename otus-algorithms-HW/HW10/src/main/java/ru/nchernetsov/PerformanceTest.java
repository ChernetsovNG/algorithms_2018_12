package ru.nchernetsov;

import static ru.nchernetsov.utils.RandomUtils.generateRandomIntArrayInRange;

public class PerformanceTest {

    public static void main(String[] args) {
        PerformanceTest test = new PerformanceTest();

        // Тест вставки в дерево случайных и упорядоченных чисел
        int[] randomArray = generateRandomIntArrayInRange(5_000_000, 0, Integer.MAX_VALUE - 1);
        int[] orderedArray = new int[5_000_000];
        for (int i = 0; i < 5_000_000; i++) {
            orderedArray[i] = i + 1;
        }

        new Thread(() -> test.insertTest("random array", randomArray)).start();
        new Thread(() -> test.insertTest("ordered array", orderedArray)).start();

        new Thread(test::searchTest).start();

        new Thread(test::removeTest).start();
    }

    private void removeTest() {
        int[] randomArray = generateRandomIntArrayInRange(5_000_000, 0, Integer.MAX_VALUE - 1);

        AVLTree avlTree = new AVLTree();
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();

        for (int i = 0; i < randomArray.length; i++) {
            avlTree.insert(randomArray[i]);
            redBlackTree.insert(randomArray[i]);
        }

        // 1 млн. случайных чисел для удаления
        int[] randomArrayToRemove = generateRandomIntArrayInRange(1_000_000, 0, Integer.MAX_VALUE - 1);

        long time1 = System.nanoTime();

        for (int i = 0; i < randomArrayToRemove.length; i++) {
            avlTree.remove(randomArrayToRemove[i]);
        }

        long time2 = System.nanoTime();

        int avlTreeHeight = avlTree.getRoot().getHeight();
        double avlTreeTimeMs = (time2 - time1) * 1.0 / 1e6;

        long time3 = System.nanoTime();

        for (int i = 0; i < randomArrayToRemove.length; i++) {
            redBlackTree.remove(randomArrayToRemove[i]);
        }

        long time4 = System.nanoTime();

        int redBlackTreeHeight = redBlackTree.getHeight();
        double redBlackTreeTimeMs = (time4 - time3) * 1.0 / 1e6;

        System.out.printf("Remove test. AVLTree: height = %d, time = %f. RedBlackTree: height = %d, time = %f\n",
            avlTreeHeight, avlTreeTimeMs, redBlackTreeHeight, redBlackTreeTimeMs);
    }

    private void insertTest(String text, int[] array) {
        long time1 = System.nanoTime();

        AVLTree avlTree = new AVLTree();
        for (int i = 0; i < array.length; i++) {
            avlTree.insert(array[i]);
        }

        long time2 = System.nanoTime();

        int avlTreeHeight = avlTree.getRoot().getHeight();
        double avlTreeTimeMs = (time2 - time1) * 1.0 / 1e6;

        long time3 = System.nanoTime();

        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        for (int i = 0; i < array.length; i++) {
            redBlackTree.insert(array[i]);
        }

        long time4 = System.nanoTime();

        int redBlackTreeHeight = redBlackTree.getHeight();
        double redBlackTreeTimeMs = (time4 - time3) * 1.0 / 1e6;

        System.out.printf("Insert %s test. AVLTree: height = %d, time = %f. RedBlackTree: height = %d, time = %f\n",
            text, avlTreeHeight, avlTreeTimeMs, redBlackTreeHeight, redBlackTreeTimeMs);
    }

    private void searchTest() {
        int[] randomArray = generateRandomIntArrayInRange(5_000_000, 0, Integer.MAX_VALUE - 1);

        AVLTree avlTree = new AVLTree();
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();

        for (int i = 0; i < randomArray.length; i++) {
            avlTree.insert(randomArray[i]);
            redBlackTree.insert(randomArray[i]);
        }

        // 5 тысяч случайных чисел для поиска
        int[] randomArrayToSearch = generateRandomIntArrayInRange(5_000, 0, Integer.MAX_VALUE - 1);

        // Поиск случайных числе в цикле 1000 раз
        long time1 = System.nanoTime();

        for (int k = 0; k < 1000; k++) {
            for (int i = 0; i < 5000; i++) {
                int randomValueToSearch = randomArrayToSearch[i];
                avlTree.find(randomValueToSearch);
            }
        }

        long time2 = System.nanoTime();

        double avlTreeTimeMs = ((time2 - time1) * 1.0 / 1000) / 1e6;

        long time3 = System.nanoTime();

        for (int k = 0; k < 1000; k++) {
            for (int i = 0; i < 5000; i++) {
                int randomValueToSearch = randomArrayToSearch[i];
                redBlackTree.search(randomValueToSearch);
            }
        }

        long time4 = System.nanoTime();

        double redBlackTreeTimeMs = ((time4 - time3) * 1.0 / 1000) / 1e6;

        System.out.printf("Time to search values in trees: AVLTree = %f, RedBlackTree = %f", avlTreeTimeMs, redBlackTreeTimeMs);
    }
}
