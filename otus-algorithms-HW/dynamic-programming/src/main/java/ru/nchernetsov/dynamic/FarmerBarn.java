package ru.nchernetsov.dynamic;

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
            setValue(X, Y);
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
     * Длина сарая:
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

    // PRIVATE section

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
     * количество пустых элементов справа от заданной клетки (включая саму эту клетку)
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
}
