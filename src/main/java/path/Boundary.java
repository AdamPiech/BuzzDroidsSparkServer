/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ola
 */
public class Boundary {
    public Point start_point;
    public Point end_point;
    public double length;
    public double a;
    public double b;
    
    public Boundary() {
        this.start_point = new Point();
        this.end_point = new Point();
    }
}
