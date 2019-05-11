package ru.nchernetsov.dynamic;

import org.junit.Test;
import ru.nchernetsov.tuples.Pair;

import static org.assertj.core.api.Assertions.assertThat;

public class FarmerBarnTest {

    @Test
    public void smallBarnTest1() {
        FarmerBarn farmerBarn = new FarmerBarn(4, 3);
        farmerBarn.occupyPoint(1, 1);
        farmerBarn.occupyPoint(0, 2);

        int smallBarn = farmerBarn.smallBarn();

        assertThat(smallBarn).isEqualTo(6);
    }

    @Test
    public void smallBarnTest2() {
        FarmerBarn farmerBarn = new FarmerBarn(6, 6);
        farmerBarn.occupyPoint(1, 0);
        farmerBarn.occupyPoint(2, 0);
        farmerBarn.occupyPoint(1, 1);
        farmerBarn.occupyPoint(0, 3);
        farmerBarn.occupyPoint(2, 3);
        farmerBarn.occupyPoint(4, 4);
        farmerBarn.occupyPoint(3, 5);
        farmerBarn.occupyPoint(5, 5);

        int smallBarn = farmerBarn.smallBarn();

        assertThat(smallBarn).isEqualTo(12);
    }

    @Test
    public void barnLengthTest1() {
        FarmerBarn farmerBarn = new FarmerBarn(4, 3);
        farmerBarn.occupyPoint(1, 1);
        farmerBarn.occupyPoint(0, 2);

        int[][] barnLength = farmerBarn.barnLength();

        assertThat(barnLength).hasSize(3);
        assertThat(barnLength[0]).hasSize(4);
        assertThat(barnLength[1]).hasSize(4);
        assertThat(barnLength[2]).hasSize(4);

        assertThat(barnLength[0]).containsExactly(1, 1, 1, 1);
        assertThat(barnLength[1]).containsExactly(2, 0, 2, 2);
        assertThat(barnLength[2]).containsExactly(0, 1, 3, 3);
    }

    @Test
    public void barnLengthTest2() {
        FarmerBarn farmerBarn = new FarmerBarn(6, 6);
        farmerBarn.occupyPoint(1, 0);
        farmerBarn.occupyPoint(2, 0);
        farmerBarn.occupyPoint(1, 1);
        farmerBarn.occupyPoint(0, 3);
        farmerBarn.occupyPoint(2, 3);
        farmerBarn.occupyPoint(4, 4);
        farmerBarn.occupyPoint(3, 5);
        farmerBarn.occupyPoint(5, 5);

        int[][] barnLength = farmerBarn.barnLength();

        assertThat(barnLength).hasSize(6);
        assertThat(barnLength[0]).hasSize(6);
        assertThat(barnLength[1]).hasSize(6);
        assertThat(barnLength[2]).hasSize(6);
        assertThat(barnLength[3]).hasSize(6);
        assertThat(barnLength[4]).hasSize(6);
        assertThat(barnLength[5]).hasSize(6);

        assertThat(barnLength[0]).containsExactly(1, 0, 0, 1, 1, 1);
        assertThat(barnLength[1]).containsExactly(2, 0, 1, 2, 2, 2);
        assertThat(barnLength[2]).containsExactly(3, 1, 2, 3, 3, 3);
        assertThat(barnLength[3]).containsExactly(0, 2, 0, 4, 4, 4);
        assertThat(barnLength[4]).containsExactly(1, 3, 1, 5, 0, 5);
        assertThat(barnLength[5]).containsExactly(2, 4, 2, 0, 1, 0);
    }

    @Test
    public void barnWidthTest1() {
        FarmerBarn farmerBarn = new FarmerBarn();

        int[] A = {1, 1, 1, 4, 4, 2, 3, 3, 2, 2};

        Pair<int[], int[]> pairLR = farmerBarn.barnWidth(A);

        int[] L = pairLR.getFirst();
        int[] R = pairLR.getSecond();

        assertThat(L).hasSize(10);
        assertThat(R).hasSize(10);

        assertThat(L).containsExactly(0, 0, 0, 3, 3, 3, 6, 6, 3, 3);
        assertThat(R).containsExactly(9, 9, 9, 4, 4, 9, 7, 7, 9, 9);
    }

    @Test
    public void barnWidthTest2() {
        FarmerBarn farmerBarn = new FarmerBarn();

        int[] A = {6, 5, 4, 3, 2, 1, 0, 1, 2, 3};

        Pair<int[], int[]> pairLR = farmerBarn.barnWidth(A);

        int[] L = pairLR.getFirst();
        int[] R = pairLR.getSecond();

        assertThat(L).hasSize(10);
        assertThat(R).hasSize(10);

        assertThat(L).containsExactly(0, 0, 0, 0, 0, 0, 0, 7, 8, 9);
        assertThat(R).containsExactly(0, 1, 2, 3, 4, 5, 9, 9, 9, 9);
    }

    @Test
    public void bigBarnTest1() {
        FarmerBarn farmerBarn = new FarmerBarn(4, 3);
        farmerBarn.occupyPoint(1, 1);
        farmerBarn.occupyPoint(0, 2);

        int bigBarn = farmerBarn.bigBarn();

        assertThat(bigBarn).isEqualTo(6);
    }

    @Test
    public void bigBarnTest2() {
        FarmerBarn farmerBarn = new FarmerBarn(6, 6);
        farmerBarn.occupyPoint(1, 0);
        farmerBarn.occupyPoint(2, 0);
        farmerBarn.occupyPoint(1, 1);
        farmerBarn.occupyPoint(0, 3);
        farmerBarn.occupyPoint(2, 3);
        farmerBarn.occupyPoint(4, 4);
        farmerBarn.occupyPoint(3, 5);
        farmerBarn.occupyPoint(5, 5);

        int bigBarn = farmerBarn.bigBarn();

        assertThat(bigBarn).isEqualTo(12);
    }
}
