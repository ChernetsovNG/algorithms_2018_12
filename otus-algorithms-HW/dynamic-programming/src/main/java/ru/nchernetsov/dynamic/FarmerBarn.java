package ru.nchernetsov.dynamic;

import ru.nchernetsov.Stack;
import ru.nchernetsov.tuples.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Задача про сарай фермера
 * Под "сараем" здесь понимается максимальная по размеру прямоугольная область,
 * которую можно вместить в матрицу farm
 */
public class FarmerBarn {

    /**
     * Размеры матрицы (N - кол-во столбцов (размер по горизонтали), M - кол-во строк (размер по вертикали)
     */
    private int N, M;

    /**
     * Множество занятых точек
     */
    private Set<Coord> occupiedPoints;

    public FarmerBarn() {
    }

    public FarmerBarn(int N, int M) {
        this.N = N;
        this.M = M;
        occupiedPoints = new HashSet<>();
    }

    public void readStringArrayInputAndInitFarm(String[] lines) {
        String line = lines[0];

        String[] NM = line.split(" ");
        int N = Integer.parseInt(NM[0]);
        int M = Integer.parseInt(NM[1]);

        this.N = N;
        this.M = M;
        occupiedPoints = new HashSet<>();

        line = lines[1];
        int T = Integer.parseInt(line);

        for (int i = 0; i < T; i++) {
            line = lines[2 + i];
            String[] XY = line.split(" ");
            int X = Integer.parseInt(XY[0]);
            int Y = Integer.parseInt(XY[1]);
            occupyPoint(X, Y);
        }
    }

    public void readConsoleInputAndInitFarm() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // На первой строке вводится размер матрицы N M (через пробел) от 1 до 1000
        String line = reader.readLine();

        String[] NM = line.split(" ");
        int N = Integer.parseInt(NM[0]);
        int M = Integer.parseInt(NM[1]);

        this.N = N;
        this.M = M;
        occupiedPoints = new HashSet<>();

        // На второй строке вводится количество построек T (от 0 до 10000)
        line = reader.readLine();
        int T = Integer.parseInt(line);

        // Далее на T строках вводится координаты построек по два числа X Y, где 0 <= X < N; 0 <= Y < M
        for (int i = 0; i < T; i++) {
            line = reader.readLine();
            String[] XY = line.split(" ");
            int X = Integer.parseInt(XY[0]);
            int Y = Integer.parseInt(XY[1]);
            occupyPoint(X, Y);
        }
    }

    public void occupyPoint(int X, int Y) {
        if (X < 0 || X >= N) {
            throw new IllegalArgumentException(String.format("must be 0 <= X < N, but X = %d and N = %d", X, N));
        }
        if (Y < 0 || Y >= M) {
            throw new IllegalArgumentException(String.format("must be 0 <= Y < M, but Y = %d and M = %d", Y, M));
        }
        occupiedPoints.add(Coord.of(X, Y));
    }

    public void printFarm() {
        for (int y = 0; y < M; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < N; x++) {
                if (occupiedPoints.contains(Coord.of(x, y))) {
                    row.append('*');
                } else {
                    row.append('-');
                }
            }
            System.out.println(row);
        }
    }

    /**
     * Маленький сарай
     * (решение задачи прямым перебором за O(M^2 x N^2))
     *
     * @return одно число, соответствующее максимальной площади сарая (количество ячеек)
     */
    public int smallBarn() {
        int result = 0;
        for (int y = 0; y < M; y++) {  // двойной цикл O(N x M)
            for (int x = 0; x < N; x++) {
                int maxSquare = findMaxSquare(x, y);
                if (maxSquare > result) {
                    result = maxSquare;
                }
            }
        }
        return result;
    }

    /**
     * Длина сарая (в направлении "на север"):
     * Матрица N x M из чисел -
     * сколько "выше" есть свободных клеток подряд, начиная с указанной клетки поля
     * Всего M строк, по N чисел на каждой, записанных через пробел
     */
    public int[][] barnLength() {
        int[][] result = new int[M][N];
        for (int y = 0; y < M; y++) {
            for (int x = 0; x < N; x++) {
                if (occupiedPoints.contains(Coord.of(x, y))) {
                    result[y][x] = 0;
                } else if (y == 0) {  // 1-я строка
                    result[y][x] = 1;
                } else {
                    result[y][x] = result[y - 1][x] + 1;
                }
            }
        }
        return result;
    }

    /**
     * Ширина сарая:
     * Нужно определить подходящую ширину сарая, для этого нужно для каждой позиции вычислить,
     * сколько клеток влево и вправо можно использовать для выбора прямоугольника той длины, которая доступна из этой клетки
     * Входные данные: массив A длин сарая (сколько элементов вверх свободно из каждой клетки)
     * Вывод результата: массивы L и R.
     * Построить две новые последовательности чисел по следующему правилу:
     * Массив L. L[j] = x
     * Для каждого элемента A[j] определить индекс x наиболее отдаленного элемента слева, который больше или равен A[j]
     * И то же самое в другую сторону
     * Массив R. R[j] = x
     * Для каждого элемента А[j] определить индекс x наиболее отдаленного элемента справа, который больше или равен А[j]
     */
    public Pair<int[], int[]> barnWidth(int[] A) {
        int[] L = calcL(A);
        int[] R = calcR(A);
        return Pair.of(L, R);
    }

    /**
     * Большой сарай
     * (решение задачи методом динамического программирования за O(N x M))
     *
     * @return одно число, соответствующее максимальной площади сарая (количество ячеек)
     */
    public int bigBarn() {
        int result = 0;
        int[][] lengthMatrix = barnLength();  // O(N x M)
        for (int[] A : lengthMatrix) {  // M раз
            Pair<int[], int[]> pairLR = barnWidth(A);  // O(2 x N) == O(N)
            int[] L = pairLR.getFirst();
            int[] R = pairLR.getSecond();
            for (int i = 0; i < A.length; i++) {  // N раз
                int length = A[i];
                int width = R[i] - L[i] + 1;
                int square = length * width;
                if (square > result) {
                    result = square;
                }
            }
        }
        return result;
    }

    // PRIVATE section

    /**
     * Найти максимальную площадь свободного прямоугольника, начиная от заданной клетки
     * O(N x M)
     */
    private int findMaxSquare(int x, int y) {
        int height = 1;  // на сколько сдвинулись вниз
        int limitLength = findMaxEmptyElementsCountOnTheRight(x, y);  // ограничение по длине прямоугольника
        if (limitLength == 0) {
            return 0;
        }
        int maxSquare = height * limitLength;  // максимальная площадь, найденная на данный момент
        for (int yi = y + 1; yi < M; yi++) {  // идём вниз
            height++;
            int width = findMaxEmptyElementsCountOnTheRight(x, yi);
            // ограничения, чтобы не выйти за границы прямоугольника вправо
            if (limitLength > width) {
                limitLength = width;
            }
            if (width > limitLength) {
                width = limitLength;
            }
            // вычисляем площадь
            int wh = width * height;
            if (maxSquare < wh) {
                maxSquare = wh;
            }
        }
        return maxSquare;
    }

    /**
     * Вспомогательная функция, которая для заданной клетки определяет, какое максимальное
     * количество пустых элементов справа от заданной клетки (включая саму эту клетку), сложность - O(N)
     */
    private int findMaxEmptyElementsCountOnTheRight(int x, int y) {
        int result = 0;
        while (x < N) {
            if (occupiedPoints.contains(Coord.of(x, y))) {
                break;
            }
            result++;
            x++;
        }
        return result;
    }

    private int[] calcL(int[] A) {
        int len = A.length;
        int[] L = new int[len];
        Stack<Integer> stack = new Stack<>(len);
        for (int i = len - 1; i >= 0; i--) {
            while (!stack.isEmpty()) {
                if (A[i] < A[stack.peek()]) {
                    L[stack.pop()] = i + 1;
                } else {
                    break;
                }
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            L[stack.pop()] = 0;
        }
        return L;
    }

    private int[] calcR(int[] A) {
        int len = A.length;
        int[] R = new int[len];
        Stack<Integer> stack = new Stack<>(len);
        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty()) {
                if (A[i] < A[stack.peek()]) {
                    R[stack.pop()] = i - 1;
                } else {
                    break;
                }
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            R[stack.pop()] = len - 1;
        }
        return R;
    }
}
