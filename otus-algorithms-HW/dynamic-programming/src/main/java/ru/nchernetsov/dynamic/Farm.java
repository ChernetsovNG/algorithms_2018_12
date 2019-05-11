package ru.nchernetsov.dynamic;

/**
 * Ферма
 */
public class Farm {

    /**
     * Размеры матрицы (N - кол-во столбцов (размер по горизонтали), M - кол-во строк (размер по вертикали)
     */
    private final int N, M;

    /**
     * Матрица из 0 и 1 (false и true), представляющая ферму
     */
    private boolean[][] farm;

    public Farm(int N, int M) {
        this.N = N;
        this.M = M;
        farm = new boolean[M][N];
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
     * Длина сарая:
     * Матрица N x M из чисел -
     * сколько "выше" есть свободных клеток подряд, начиная с указанной клетки поля
     * Всего M строк, по N чисел на каждой, записанных через пробел
     */
    public int[][] barnLength() {
        int[][] result = new int[M][N];
        for (int y = 0; y < M; y++) {
            for (int x = 0; x < N; x++) {
                if (farm[y][x]) {
                    result[y][x] = 0;
                } else if (y == 0) {
                    result[y][x] = 1;
                } else {
                    result[y][x] = result[y - 1][x] + 1;
                }
            }
        }
        return result;
    }
}
