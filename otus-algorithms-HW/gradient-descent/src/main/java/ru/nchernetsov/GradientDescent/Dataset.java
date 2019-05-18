package ru.nchernetsov.GradientDescent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Dataset {

    private final List<DatasetElement> elements = new ArrayList<>();

    public void addElement(DatasetElement element) {
        elements.add(element);
    }

    public List<DatasetElement> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public static Dataset readDatasetFromCsvFile(String fileName) throws IOException {
        Dataset dataset = new Dataset();
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
