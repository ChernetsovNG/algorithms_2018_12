package ru.nchernetsov.dynamic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FarmGenerator {

    public void generateAndPrintRandomFarm(int N, int M, int T) {
        String[] randomFarm = generate(N, M, T);
        FarmerBarn farmerBarn = new FarmerBarn();
        farmerBarn.readStringArrayInputAndInitFarm(randomFarm);
        farmerBarn.printFarm();
    }

    public String[] generate(int N, int M, int T) {
        Set<Coord> occupiedPoints = new HashSet<>();
        Random random = new Random();
        String[] lines = new String[T + 2];
        lines[0] = N + " " + M;
        lines[1] = String.valueOf(T);
        for (int i = 0; i < T; i++) {
            Coord occupiedPoint;
            do {
                int x = random.nextInt(N);
                int y = random.nextInt(M);
                occupiedPoint = Coord.of(x, y);
            } while (occupiedPoints.contains(occupiedPoint));
            occupiedPoints.add(occupiedPoint);
            lines[2 + i] = occupiedPoint.getX() + " " + occupiedPoint.getY();
        }
        return lines;
    }
}
