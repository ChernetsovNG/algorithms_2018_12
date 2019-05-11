package ru.nchernetsov.dynamic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача про сарай фермера
 */
public class FarmerBarn {

    public static void main(String[] args) throws IOException {
        Farm farm = readInputAndCreateFarm();
        System.out.println(farm);
    }

    // Читаем входные данные
    private static Farm readInputAndCreateFarm() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // На первой строке вводится размер матрицы N M (через пробел) от 1 до 1000
        String line = reader.readLine();

        String[] NM = line.split(" ");
        int N = Integer.parseInt(NM[0]);
        int M = Integer.parseInt(NM[1]);

        Farm farm = new Farm(N, M);

        // На второй строке вводится количество построек T (от 0 до 10000)
        line = reader.readLine();
        int T = Integer.parseInt(line);

        // Далее на T строках вводится координаты построек по два числа X Y, где 0 <= X < N; 0 <= Y < M
        for (int i = 0; i < T; i++) {
            line = reader.readLine();
            String[] XY = line.split(" ");
            int X = Integer.parseInt(XY[0]);
            int Y = Integer.parseInt(XY[1]);
            farm.setValue(X, Y);
        }
        return farm;
    }
}
