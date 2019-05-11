package ru.nchernetsov.dynamic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
     * Матрица из 0 и 1 (false и true), представляющая ферму
     */
    private boolean[][] farm;

    public FarmerBarn() {
    }

    public FarmerBarn(int N, int M) {
        this.N = N;
        this.M = M;
        farm = new boolean[M][N];
    }

    public void readInputAndInitFarm() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // На первой строке вводится размер матрицы N M (через пробел) от 1 до 1000
        String line = reader.readLine();

        String[] NM = line.split(" ");
        int N = Integer.parseInt(NM[0]);
        int M = Integer.parseInt(NM[1]);

        this.N = N;
        this.M = M;
        farm = new boolean[M][N];

        // На второй строке вводится количество построек T (от 0 до 10000)
        line = reader.readLine();
        int T = Integer.parseInt(line);

        // Далее на T строках вводится координаты построек по два числа X Y, где 0 <= X < N; 0 <= Y < M
        for (int i = 0; i < T; i++) {
            line = reader.readLine();
            String[] XY = line.split(" ");
            int X = Integer.parseInt(XY[0]);
            int Y = Integer.parseInt(XY[1]);
            setValue(X, Y);
        }
    }

    public void setValue(int X, int Y) {
        if (X < 0 || X >= N) {
            throw new IllegalArgumentException(String.format("must be 0 <= X < N, but X = %d where N = %d", X, N));
        }
        if (Y < 0 || Y >= M) {
            throw new IllegalArgumentException(String.format("must be 0 <= Y < M, but Y = %d where M = %d", Y, M));
        }
        farm[Y][X] = true;
    }

    /**
     * Маленький сарай
     * (решение задачи прямым перебором)
     *
     * @return одно число, соответствующее максимальной площади сарая (количество ячеек)
     */
    public int smallBarn() {
        int result = 0;
        for (int y = 0; y < M; y++) {
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
     * Найти максимальную площадь свободного прямоугольника, начиная от заданной клетки
     */
    private int findMaxSquare(int x, int y) {
        int height = 1;  // на сколько сдвинулись вниз
        int limitLength = findMaxEmptyElementsCountOnTheRight(x, y);  // ограничение по длине прямоугольника
        if (limitLength == 0) {
            return 0;
        }
        int maxSquare = height * limitLength;  // максимальная площадь, найденная на данный момент
        for (int yi = y + 1; yi < M; yi++) {
            height++;
            int width = findMaxEmptyElementsCountOnTheRight(x, yi);
            if (limitLength > width) {
                limitLength = width;
            }
            if (width > limitLength) {
                width = limitLength;
            }
            int wh = width * height;
            if (maxSquare < wh) {
                maxSquare = wh;
            }
        }
        return maxSquare;
    }

    /**
     * Вспомогательная функция, которая для заданной клетки определяет, какое максимальное
     * количество пустых элементов справа от заданной клетки (включая саму эту клетку)
     */
    private int findMaxEmptyElementsCountOnTheRight(int x, int y) {
        int result = 0;
        while (x < N) {
            if (farm[y][x]) {
                break;
            }
            result++;
            x++;
        }
        return result;
    }

    /**
     * Длина сарая:
     * Матрица N x M из чисел -
     * сколько "выше" есть свободных клеток подряд, начиная с указанной клетки поля
     * Всего M строк, по N чисел на каждой, записанных через пробел
     */
    public int[][] barnLength() {
        int[][] result = new int[M][N];
        // первую строку инициализируем единицами
        Arrays.fill(result[0], 1);
        for (int y = 1; y < M; y++) {
            for (int x = 0; x < N; x++) {
                if (farm[y][x]) {
                    result[y][x] = 0;
                } else {
                    result[y][x] = result[y - 1][x] + 1;
                }
            }
        }
        return result;
    }
}
