package ru.nchernetsov.GradientDescent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.nchernetsov.GradientDescent.VectorUtils.divideVectors;
import static ru.nchernetsov.GradientDescent.VectorUtils.substractVectors;

public class Dataset {

    /**
     * Размерность вектора X
     */
    private final int xSize;

    private final List<DatasetElement> elements = new ArrayList<>();

    public Dataset(int xSize) {
        this.xSize = xSize;
    }

    public void addElement(DatasetElement element) {
        if (element.getVectorX().size() != xSize) {
            throw new IllegalArgumentException("Size of added element != xSize: xSize = " +
                xSize + " element vector size = " + element.getVectorX().size());
        }
        elements.add(element);
    }

    public List<DatasetElement> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public int size() {
        return elements.size();
    }

    /**
     * Найти математическое ожидание для каждого элемента набора данных (вектор)
     */
    public List<Double> getMuX() {
        List<Double> mu = new ArrayList<>(xSize);

        List<Double> vectorSum = new ArrayList<>(xSize);
        for (int i = 0; i < xSize; i++) {
            vectorSum.add(0.0);
            mu.add(0.0);
        }

        int count = elements.size();
        for (DatasetElement element : elements) {
            List<Double> vectorX = element.getVectorX();
            for (int i = 0; i < xSize; i++) {
                Double vectorXi = vectorX.get(i);
                Double sumI = vectorSum.get(i);
                sumI += vectorXi;
                vectorSum.set(i, sumI);
            }
        }

        for (int i = 0; i < xSize; i++) {
            Double sumI = vectorSum.get(i);
            mu.set(i, sumI / count);
        }

        return mu;
    }

    public Double getMuY() {
        double mu = 0.0;
        int count = elements.size();
        double sum = 0.0;
        for (DatasetElement element : elements) {
            Double yi = element.getY();
            sum += yi;
        }

        return sum / count;
    }

    /**
     * Найти среднеквадратическое отклонение для каждого элемента набора данных (вектор)
     */
    public List<Double> getSigmaX() {
        List<Double> sigma = new ArrayList<>(xSize);

        List<Double> mu = getMuX();

        List<Double> vectorSum = new ArrayList<>(xSize);
        for (int i = 0; i < xSize; i++) {
            vectorSum.add(0.0);
            sigma.add(0.0);
        }

        int count = elements.size();

        for (DatasetElement element : elements) {
            List<Double> vectorX = element.getVectorX();
            for (int i = 0; i < xSize; i++) {
                Double vectorXi = vectorX.get(i);
                Double muI = mu.get(i);
                Double sumI = vectorSum.get(i);

                double deltaXMu = vectorXi - muI;

                sumI += deltaXMu * deltaXMu;

                vectorSum.set(i, sumI);
            }
        }

        for (int i = 0; i < xSize; i++) {
            Double sumI = vectorSum.get(i);
            sigma.set(i, Math.sqrt(sumI / count));
        }

        return sigma;
    }

    public Double getSigmaY() {
        int count = elements.size();
        double sum = 0.0;
        double mu = getMuY();
        for (DatasetElement element : elements) {
            Double yi = element.getY();
            double deltaY = yi - mu;
            sum += deltaY * deltaY;
        }
        return Math.sqrt(sum / count);
    }

    public List<List<Double>> getX() {
        return elements.stream()
            .map(DatasetElement::getVectorX)
            .collect(Collectors.toList());
    }

    public List<Double> getY() {
        return elements.stream()
            .map(DatasetElement::getY)
            .collect(Collectors.toList());
    }

    /**
     * Нормализованный массив данных X: x_i_norm = (x_i - mu) / sigma
     */
    public List<List<Double>> getNormalizeX() {
        List<List<Double>> initialX = getX();
        List<Double> mu = getMuX();
        List<Double> sigma = getSigmaX();
        return initialX.stream()
            .map(vectorX -> {
                List<Double> xMinusMu = substractVectors(vectorX, mu);
                return divideVectors(xMinusMu, sigma);
            })
            .collect(Collectors.toList());
    }

    /**
     * Нормализованный вектор Y
     */
    public List<Double> getNormalizeY() {
        List<Double> initialY = getY();
        Double mu = getMuY();
        Double sigma = getSigmaY();
        return initialY.stream()
            .map(y -> (y - mu) / sigma)
            .collect(Collectors.toList());
    }

    public static Dataset readDatasetFromCsvFile(String fileName) throws IOException {
        Dataset dataset = new Dataset(14);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream(fileName);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)))) {
            String line;
            int lineNumber = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) {  // первую строку с заголовками пропускаем
                    continue;
                }
                String[] values = line.split(",");
                double firstElement = Double.parseDouble(values[0]);
                double CRIM = Double.parseDouble(values[1]);
                double ZN = Double.parseDouble(values[2]);
                double INDUS = Double.parseDouble(values[3]);
                double CHAS = Double.parseDouble(values[4]);
                double NOX = Double.parseDouble(values[5]);
                double RM = Double.parseDouble(values[6]);
                double AGE = Double.parseDouble(values[7]);
                double DIS = Double.parseDouble(values[8]);
                double RAD = Double.parseDouble(values[9]);
                double TAX = Double.parseDouble(values[10]);
                double PTRATIO = Double.parseDouble(values[11]);
                double B = Double.parseDouble(values[12]);
                double LSTAT = Double.parseDouble(values[13]);
                double MEDV = Double.parseDouble(values[14]);

                DatasetElement element = new DatasetElement();
                element.setFirstElement(firstElement);
                element.setCRIM(CRIM);
                element.setZN(ZN);
                element.setINDUS(INDUS);
                element.setCHAS(CHAS);
                element.setNOX(NOX);
                element.setRM(RM);
                element.setAGE(AGE);
                element.setDIS(DIS);
                element.setRAD(RAD);
                element.setTAX(TAX);
                element.setPTRATIO(PTRATIO);
                element.setB(B);
                element.setLSTAT(LSTAT);
                element.setMEDV(MEDV);

                dataset.addElement(element);
            }
        }

        return dataset;
    }
}
