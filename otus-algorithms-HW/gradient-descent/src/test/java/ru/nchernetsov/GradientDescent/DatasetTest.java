package ru.nchernetsov.GradientDescent;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DatasetTest {

    @Test
    public void readDatasetFromFileTest() throws IOException {
        Dataset dataset = Dataset.readDatasetFromCsvFile(14, "boston_housing-31272-bf744f.csv");
        assertThat(dataset.size()).isEqualTo(506);
    }

    @Test
    public void test1() throws IOException {
        Dataset dataset = Dataset.readDatasetFromCsvFile(14, "boston_housing-31272-bf744f.csv");
        // проверяем, что все нужные значения вычисляются без ошибок
        List<Double[]> x = dataset.getX();
        List<Double> y = dataset.getY();
        List<Double> muX = dataset.getMuX();
        List<Double> sigmaX = dataset.getSigmaX();
        Double muY = dataset.getMuY();
        Double sigmaY = dataset.getSigmaY();
        List<Double[]> normalizeX = dataset.getNormalizeX();
        List<Double> normalizeY = dataset.getNormalizeY();
    }
}
