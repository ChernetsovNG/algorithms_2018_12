package ru.nchernetsov.GradientDescent;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.nchernetsov.GradientDescent.VectorUtils.*;

public class VectorUtilsTest {

    @Test
    public void scalarProductTest1() {
        Double[] vector1 = {1.0, 2.0, 3.0};
        Double[] vector2 = {4.0, 5.0, 6.0};

        Double scalarProduct = scalarProduct(vector1, vector2);

        assertThat(scalarProduct).isEqualTo(32.0, Offset.offset(1e-6));
    }

    @Test
    public void vectorNormTest1() {
        Double[] vector1 = {1.0, 2.0, 3.0};
        Double vectorNorm = vectorNorm(vector1);
        assertThat(vectorNorm).isEqualTo(3.741657387, Offset.offset(1e-6));
    }

    @Test
    public void substractVectorsTest1() {
        Double[] vector1 = {1.0, 2.0, 3.0};
        Double[] vector2 = {4.0, 5.0, 6.0};

        Double[] resultVector = substractVectors(vector1, vector2);

        assertThat(resultVector).hasSize(3);
        assertThat(resultVector[0]).isEqualTo(-3.0, Offset.offset(1e-6));
        assertThat(resultVector[1]).isEqualTo(-3.0, Offset.offset(1e-6));
        assertThat(resultVector[2]).isEqualTo(-3.0, Offset.offset(1e-6));
    }

    @Test
    public void divideVectorsTest1() {
        Double[] vector1 = {1.0, 2.0, 3.0};
        Double[] vector2 = {4.0, 5.0, 6.0};

        Double[] resultVector = divideVectors(vector1, vector2);

        assertThat(resultVector).hasSize(3);
        assertThat(resultVector[0]).isEqualTo(0.25, Offset.offset(1e-6));
        assertThat(resultVector[1]).isEqualTo(0.4, Offset.offset(1e-6));
        assertThat(resultVector[2]).isEqualTo(0.5, Offset.offset(1e-6));
    }

    @Test
    public void multiplyVectorOnNumberTest1() {
        Double[] vector1 = {1.0, 2.0, 3.0};

        Double[] resultVector = multiplyVectorOnNumber(vector1, 2.7);

        assertThat(resultVector).hasSize(3);
        assertThat(resultVector[0]).isEqualTo(2.7, Offset.offset(1e-6));
        assertThat(resultVector[1]).isEqualTo(5.4, Offset.offset(1e-6));
        assertThat(resultVector[2]).isEqualTo(8.1, Offset.offset(1e-6));
    }
}
