package ru.nchernetsov;

import ru.nchernetsov.graph.DirectedGraph;

import java.util.Comparator;

import static ru.nchernetsov.graph.GraphUtils.convertAdjVectorsToAdjMatrix;

public class DemucronAlg {

    /**
     * Топологическая сортировка графа при помощи алгоритма Демукрона
     *
     * @param graph граф, представленный векторами смежности
     * @return массив int[][], где первый индекс - номер уровня,
     * второй индекс - массив с номерами вершин, принадлежащих этому уровню
     * (номер уровня отсчитывается от нуля)
     */
    public static int[][] demucronTopologySort(DirectedGraph graph) {
        int vertexCount = graph.getVertexCount();
        MyArrayList<MyArrayList<Integer>> adjVectorsList = graph.getAdjVectorsList();

        // преобразуем представление графа в матрицу смежности
        int[][] B = convertAdjVectorsToAdjMatrix(vertexCount, adjVectorsList);

        // массив ord длины n, i-й элемент которого равен номеру уровня вершины v_i
        int[] ord = new int[vertexCount];

        /*
        0. Сформировать множество V1 сети. Значение счётчика уровней k положить равным 0. Найти
        суммы элементов по всем столбцам матрицы B (полустепени захода вершин) и заполнить ими массив M
         */
        boolean[] V1 = new boolean[vertexCount];
        int k = 0;
        int[] M = initVectorM(B);

        MyArrayList<Integer> I = new MyArrayList<>();
        /*
        1. Если множество V1 не пусто (не все вершины замаскированы), перейти на шаг 2, иначе закончить работу
         */
        while (!isAllMasked(V1)) {
            I.clear();
            /*
            2. Определить множество I номеров всех новых нулевых элементов массива M,
            т.е. таких, что соответствующие этим номерам вершины принадлежат множеству V1 (не замаскированы)
             */
            for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
                int vertexInputHalfDegree = M[vertexIndex];  // полустепень захода вершины
                if (vertexInputHalfDegree == 0 && !V1[vertexIndex]) {
                    /*
                    Присвоить элементам массива ord с номерами из множества I номер уровня k и
                    удалить вершины с этими номерами из множества V1 (замаскировать вершины)
                     */
                    I.add(vertexIndex);
                    ord[vertexIndex] = k;
                    V1[vertexIndex] = true;
                }
            }
            /*
            Если при очередном перевычислении массива M не появились новые нули,
            то заданный граф не является сетью
             */
            if (I.isEmpty()) {
                throw new IllegalStateException("demucronTopologySort: graph is not network");
            }
            /*
            Вычесть из массива M строки матрицы B, соответствующие вершинам с номерами
            из множества I (т.е. вершинам последнего вычисленного уровня)
            */
            for (Integer vertexIndex : I) {
                int[] rowB = B[vertexIndex];
                for (int j = 0; j < vertexCount; j++) {  // цикл по столбцам
                    M[j] -= rowB[j];
                }
            }
            /*
            Увеличить счётчик уровней на 1. Вернуться на шаг 1
             */
            k += 1;
        }

        // Преобразуем массив ord в итоговый результат
        return convertOrdToResultArray(ord, k);
    }

    private static int[] initVectorM(int[][] B) {
        int vertexCount = B.length;
        int[] M = new int[vertexCount];
        for (int j = 0; j < vertexCount; j++) {  // цикл по столбцам
            int sum = 0;
            for (int[] rowB : B) {  // цикл по строкам
                sum += rowB[j];
            }
            M[j] = sum;
        }
        return M;
    }

    private static int[][] convertOrdToResultArray(int[] ord, int levelCount) {
        MyArrayList<MyArrayList<Integer>> levelVertices = new MyArrayList<>(levelCount);
        for (int level = 0; level < levelCount; level++) {
            levelVertices.add(new MyArrayList<>());
        }

        for (int vertexIndex = 0; vertexIndex < ord.length; vertexIndex++) {
            int vertexLevel = ord[vertexIndex];
            levelVertices.get(vertexLevel).add(vertexIndex);
        }

        // Сортируем вершины в пределах каждого уровня
        for (int level = 0; level < levelCount; level++) {
            levelVertices.get(level).sort(Comparator.naturalOrder());
        }

        int[][] result = new int[levelCount][];
        for (int level = 0; level < levelCount; level++) {
            MyArrayList<Integer> levelV = levelVertices.get(level);
            result[level] = new int[levelV.size()];
            for (int i = 0; i < levelV.size(); i++) {
                result[level][i] = levelV.get(i);
            }
        }

        return result;
    }

    private static boolean isAllMasked(boolean[] V1) {
        for (boolean isVertexMasked : V1) {
            if (!isVertexMasked) {
                return false;
            }
        }
        return true;
    }
}
