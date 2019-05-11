package ru.nchernetsov.dynamic;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FarmerBarnTest {

    @Test
    public void smallBarnTest1() {
        FarmerBarn farmerBarn = new FarmerBarn(4, 3);
        farmerBarn.setValue(1, 1);
        farmerBarn.setValue(0, 2);

        int smallBarn = farmerBarn.smallBarn();

        assertThat(smallBarn).isEqualTo(6);
    }

    @Test
    public void smallBarnTest2() {
        FarmerBarn farmerBarn = new FarmerBarn(6, 6);
        farmerBarn.setValue(1, 0);
        farmerBarn.setValue(2, 0);
        farmerBarn.setValue(1, 1);
        farmerBarn.setValue(0, 3);
        farmerBarn.setValue(2, 3);
        farmerBarn.setValue(4, 4);
        farmerBarn.setValue(3, 5);
        farmerBarn.setValue(5, 5);

        int smallBarn = farmerBarn.smallBarn();

        assertThat(smallBarn).isEqualTo(12);
    }

    @Test
    public void barnLengthTest1() {
        FarmerBarn farmerBarn = new FarmerBarn(4, 3);
        farmerBarn.setValue(1, 1);
        farmerBarn.setValue(0, 2);

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
        farmerBarn.setValue(1, 0);
        farmerBarn.setValue(2, 0);
        farmerBarn.setValue(1, 1);
        farmerBarn.setValue(0, 3);
        farmerBarn.setValue(2, 3);
        farmerBarn.setValue(4, 4);
        farmerBarn.setValue(3, 5);
        farmerBarn.setValue(5, 5);

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
}
