
import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ola
 */
public class Terrain {

    public ArrayList<Boundary> borders;
    public ArrayList<Point> boundary_points;

    public Terrain(ArrayList<Point> boundary_points) {
        this.boundary_points = boundary_points;
        this.borders = new ArrayList<Boundary>();
    }
    
    public Terrain() {
        this.boundary_points = new ArrayList<Point>();
        this.borders = new ArrayList<Boundary>();
    }

    public void add_point_coord(double lat, double lon) {
        Point coords = new Point();
        coords.latitude = lat;
        coords.longtitude = lon;
	this.boundary_points.add(coords);
    }

    public double get_distance(Point start_point, Point end_point) {
        double x = end_point.longtitude - start_point.longtitude;
	double y = end_point.latitude - start_point.latitude;

	return sqrt(pow(x, 2) + pow(y, 2));
    }

    public void generate_borders() {
        	this.borders.clear();
    for (int i=0; i < this.boundary_points.size(); i++)
    {
        Point start = this.boundary_points.get(i);
        Point end = new Point();
        
        if(i < this.boundary_points.size() - 1)
            end = this.boundary_points.get(i+1);
        else
            end = this.boundary_points.get(0);

        double distance = get_distance(start, end);
        double a = (end.latitude - start.latitude)/(end.longtitude - start.longtitude);
	double b = start.latitude - a * start.longtitude;
        Boundary bound = new Boundary();
        bound.start_point = start;
        bound.end_point = end;
        bound.length = distance;
        bound.a = a;
        bound.b = b;
        this.borders.add(bound);
    }
    }

    public ArrayList<Point> get_bounary_points() {
        return this.boundary_points;
    }

    public ArrayList<Boundary> get_borders() {
        return this.borders;
    }

    public int get_horizontal_index() {
    	for (int i = 0; i < this.borders.size(); i++)
	{
		Boundary bord = this.borders.get(i);
		if (abs(atan(bord.a)) < 10 * Math.PI / 180)
			return i;
	}
	return -1;
    }

    public Boundary get_next_border(Point p, boolean on_the_right, boolean up) {
        System.out.printf("p.lat: %.15f\n", p.latitude);
        System.out.printf("p.long: %.15f\n", p.longtitude);

        for (int i = 0; i < this.borders.size(); i++) {
            Boundary bord = this.borders.get(i);
            if (up) {
                if (on_the_right) {
                    if (bord.start_point.latitude < p.latitude
                            && bord.start_point.longtitude > p.longtitude
                            && bord.end_point.latitude > p.latitude
                            && bord.end_point.longtitude > p.longtitude) {
                        return bord;
                    }
                } else if (bord.end_point.latitude < p.latitude
                        && bord.end_point.longtitude < p.longtitude
                        && bord.start_point.latitude > p.latitude
                        && bord.start_point.longtitude < p.longtitude) {
                    return bord;
                }
            } else if (on_the_right) {
                if (bord.start_point.latitude > p.latitude && bord.end_point.longtitude < p.longtitude) {
                    return bord;
                } else {
                    if (bord.end_point.latitude > p.latitude && bord.start_point.longtitude < p.longtitude) {
                        return bord;
                    }
                }
            }
            /*if (bord.end_point.latitude > p.latitude && up)
		{
			if (bord.start_point.longtitude > p.longtitude && on_the_right)
				return bord;
			else if (bord.start_point.longtitude < p.longtitude && !on_the_right)
				return bord;
		}
		else if (bord.end_point.latitude < p.latitude && !up)
		{
			if (bord.start_point.longtitude > p.longtitude && on_the_right)
				return bord;
			else if (bord.start_point.longtitude < p.longtitude && !on_the_right)
				return bord;
		}*/
        }
        return new Boundary();
    }

}
