package path;

import dataModel.Coordinates;

public class Boundary {

    public Coordinates startPoint;
    public Coordinates endPoint;

    public double factorA;
    public double factorB;
    public double length;

    public Boundary(Coordinates startPoint, Coordinates endPoint, double factorA, double factorB, double length) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.factorA = factorA;
        this.factorB = factorB;
        this.length = length;
    }

    public Coordinates getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Coordinates startPoint) {
        this.startPoint = startPoint;
    }

    public Coordinates getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Coordinates endPoint) {
        this.endPoint = endPoint;
    }

    public double getFactorA() {
        return factorA;
    }

    public void setFactorA(double factorA) {
        this.factorA = factorA;
    }

    public double getFactorB() {
        return factorB;
    }

    public void setFactorB(double factorB) {
        this.factorB = factorB;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

}
