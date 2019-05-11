package ru.nchernetsov.dynamic;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FarmerBarnTest {

    @Test
    public void smallBarnTest() {
        FarmerBarn farmerBarn = new FarmerBarn(4, 3);
        farmerBarn.setValue(1, 1);
        farmerBarn.setValue(0, 2);

        int smallBarn = farmerBarn.smallBarn();

        assertThat(smallBarn).isEqualTo(6);
    }

    @Test
    public void barnLengthTest() {
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
}
