package ru.nchernetsov.GradientDescent;

import java.util.Arrays;
import java.util.List;

public class DatasetElement {

    private double firstElement;

    private double CRIM;

    private double ZN;

    private double INDUS;

    private double CHAS;

    private double NOX;

    private double RM;

    private double AGE;

    private double DIS;

    private double RAD;

    private double TAX;

    private double PTRATIO;

    private double B;

    private double LSTAT;

    /**
     * Целевая колонка
     */
    private double MEDV;

    public List<Double> getVectorX() {
        return Arrays.asList(firstElement, CRIM, ZN, INDUS, CHAS, NOX, RM, AGE, DIS, RAD, TAX, PTRATIO, B, LSTAT);
    }

    public Double getY() {
        return MEDV;
    }

    public double getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(double firstElement) {
        this.firstElement = firstElement;
    }

    public double getCRIM() {
        return CRIM;
    }

    public void setCRIM(double CRIM) {
        this.CRIM = CRIM;
    }

    public double getZN() {
        return ZN;
    }

    public void setZN(double ZN) {
        this.ZN = ZN;
    }

    public double getINDUS() {
        return INDUS;
    }

    public void setINDUS(double INDUS) {
        this.INDUS = INDUS;
    }

    public double getCHAS() {
        return CHAS;
    }

    public void setCHAS(double CHAS) {
        this.CHAS = CHAS;
    }

    public double getNOX() {
        return NOX;
    }

    public void setNOX(double NOX) {
        this.NOX = NOX;
    }

    public double getRM() {
        return RM;
    }

    public void setRM(double RM) {
        this.RM = RM;
    }

    public double getAGE() {
        return AGE;
    }

    public void setAGE(double AGE) {
        this.AGE = AGE;
    }

    public double getDIS() {
        return DIS;
    }

    public void setDIS(double DIS) {
        this.DIS = DIS;
    }

    public double getRAD() {
        return RAD;
    }

    public void setRAD(double RAD) {
        this.RAD = RAD;
    }

    public double getTAX() {
        return TAX;
    }

    public void setTAX(double TAX) {
        this.TAX = TAX;
    }

    public double getPTRATIO() {
        return PTRATIO;
    }

    public void setPTRATIO(double PTRATIO) {
        this.PTRATIO = PTRATIO;
    }

    public double getB() {
        return B;
    }

    public void setB(double b) {
        B = b;
    }

    public double getLSTAT() {
        return LSTAT;
    }

    public void setLSTAT(double LSTAT) {
        this.LSTAT = LSTAT;
    }

    public double getMEDV() {
        return MEDV;
    }

    public void setMEDV(double MEDV) {
        this.MEDV = MEDV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DatasetElement that = (DatasetElement) o;

        if (Double.compare(that.firstElement, firstElement) != 0) return false;
        if (Double.compare(that.CRIM, CRIM) != 0) return false;
        if (Double.compare(that.ZN, ZN) != 0) return false;
        if (Double.compare(that.INDUS, INDUS) != 0) return false;
        if (Double.compare(that.CHAS, CHAS) != 0) return false;
        if (Double.compare(that.NOX, NOX) != 0) return false;
        if (Double.compare(that.RM, RM) != 0) return false;
        if (Double.compare(that.AGE, AGE) != 0) return false;
        if (Double.compare(that.DIS, DIS) != 0) return false;
        if (Double.compare(that.RAD, RAD) != 0) return false;
        if (Double.compare(that.TAX, TAX) != 0) return false;
        if (Double.compare(that.PTRATIO, PTRATIO) != 0) return false;
        if (Double.compare(that.B, B) != 0) return false;
        if (Double.compare(that.LSTAT, LSTAT) != 0) return false;
        return Double.compare(that.MEDV, MEDV) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(firstElement);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(CRIM);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ZN);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(INDUS);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(CHAS);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(NOX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(RM);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(AGE);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(DIS);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(RAD);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(TAX);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(PTRATIO);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(B);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(LSTAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(MEDV);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
