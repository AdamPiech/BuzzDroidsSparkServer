
import java.io.PrintWriter;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Locale;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ola
 */
public class Path {

    private ArrayList<Point> path;
    private int width; //(x−a)^2+(y−b)^2 = ((1/2)width)^2
    //a i b to będą współrzędne położenia drona
    private Terrain t;
    private boolean fly_up;
    private double minute_width_lat;
    private double minute_width_lon;
    private boolean field_end = false;

    private void get_width_in_minutes() {
        this.minute_width_lat = (width * Constants.MINUTE) / Constants.MINUTE_METERS;
        double avg_lat = get_avg_latitude();
        this.minute_width_lon = (width * Constants.MINUTE) / (Constants.MINUTE_METERS * cos(avg_lat));
    }

    private double get_avg_latitude() {
        ArrayList<Point> points = t.get_bounary_points();
        Point max = points.get(0);
        Point min = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Point p = points.get(i);
            if (p.latitude > max.latitude) {
                max = p;
            } else {
                min = p;
            }
        }
        return (max.latitude - min.latitude) / 2;
    }

    private double change_latitude(double latitude, double value) {
        return latitude + (180 / Math.PI) * (value / Constants.EARTH_RADIUS);
    }

    private double change_longtitude(double longtitude, double value) {
        return longtitude + ((180 / Math.PI) * (value / Constants.EARTH_RADIUS)) / cos(longtitude * Math.PI / 180);
    }

    /*private boolean can_move(Point p, Boundary b, boolean horizontal) {
    	double a = border.a;
	double b = border.b;
	double x0 = p.longtitude;
	double y0 = p.latitude;

	double d = abs(a * x0 - y0 + b) / sqrt(a*a + 1);
	cout << "**********************" << endl;
	cout << "a " << a << endl;
	cout << "b " << b << endl;
	cout << "d " << d << endl;
	Sleep(1000);
	double alpha = atan(abs(a));
	//cout << "alpha1 " << alpha << endl;
	double radius = this->minute_width_lat * sin(alpha);
	//cout << "radius1 " << radius << endl;
	if (!horizontal)
	{
		//alpha = 90 * M_PI / 180 - alpha;
		radius = this->minute_width_lon * sin(alpha);
	}
	//cout << "alpha2 " << alpha << endl;
	cout << "radius " << radius << endl;
	return (d > radius*0.5);
	//return (d > 0.001);
    }*/
    private Point fly_vertically(Point/*&*/ end_arg, Boundary move_along, Boundary next_border) {
        int i = 0;
        double step = 1;
        if (!this.fly_up) {
            step = -1;
        }
        Point end = new Point();
        end.latitude = end_arg.latitude;
        end.longtitude = end_arg.longtitude;
        double a1 = next_border.a;
        double b1 = next_border.b;
        double a2 = move_along.a;
        double b2 = move_along.b;
        double x_prze = (b2 - b1) / (a1 - a2);
        double y_prze = a1 * x_prze + b1;
        //cout << "x_prze " << x_prze << endl;
        //cout << "y_prze " << y_prze << endl;

        //while (can_move(end, next_border, true))//abs(end.latitude - y_prze) > 0.00001 && abs(end.longtitude - x_prze) > 0.00001) // this->width)//&& can_move(end, next_horizontal_border, true))
        double x_chuj = (move_along.b - next_border.b) / (next_border.a - move_along.a);
        double y_chuj = move_along.a * x_chuj + move_along.b;

        while (abs(end.longtitude - x_chuj) > 0.00001 && abs(end.latitude - y_chuj) > 0.00001) {
            //end.latitude = change_latitude(end.latitude, 1);
            //end.latitude += step * MINUTE;
            //end.longtitude = (end.latitude - move_along.b) / move_along.a;

            double x_start = end.longtitude;
            double y_start = end.latitude;
            double a = move_along.a;
            double b = move_along.b;
            //cout << "v x_start " << x_start << endl;
            //cout << "v y_start " << y_start << endl;

            //double d = 1 * MINUTE;
            double delta_a = (a * a + 1);
            double delta_b = (-2 * x_start + 2 * a * b - 2 * a * y_start);
            double delta_c = (x_start * x_start + b * b - 2 * b * y_start + y_start * y_start - 0.00000001 * Constants.MINUTE * Constants.MINUTE);

            double delta = delta_b * delta_b - 4 * delta_a * delta_c;
            //if (delta >= 0)
            {
                //	cout << "chuj" << endl;
                double x = (-delta_b + sqrt(abs(delta))) / (2 * delta_a);
                double y = a * x + b;
                if (y < y_start) {
                    x = (-delta_b - sqrt(abs(delta))) / (2 * delta_a);
                    y = a * x + b;
                }
                //cout << "v x " << x << endl;
                //cout << "v y " << y << endl;

                end.latitude = y;
                end.longtitude = x;
                if (i % 20 == 0) {
                    this.path.add(new Point(y, x));
                }
                //Sleep(1000);
            }

            i++;
            //cout << "abs(end.latitude - y_prze) " << abs(end.latitude - y_prze) << endl;
            //cout << "abs(end.longtitude - x_prze) " << abs(end.longtitude - x_prze)  << endl;
        }

        this.path.add(new Point(end.latitude, end.longtitude));
        return new Point(end.latitude, end.longtitude);
    }

    private Point fly_horizontally(Point/*&*/ end_arg, Boundary move_along, boolean left, Boundary next_border) {
        int i = 0;
        double step = 1;
        if (left) {
            step = -1;
        }
        //cout << "step horizontal: " <<  step << endl;
        Point end = new Point();
        end.latitude = end_arg.latitude;
        end.longtitude = end_arg.longtitude;
        //printf("chuj\n");
        double x_chuj = (move_along.b - next_border.b) / (next_border.a - move_along.a);
        double y_chuj = move_along.a * x_chuj + move_along.b;

        while (abs(end.longtitude - x_chuj) > 0.000001 && abs(end.latitude - y_chuj) > 0.000001)//can_move(end, next_border, true))
        {
            //end.longtitude = change_longtitude(end.longtitude, step);
            //	end.longtitude += step * MINUTE;
            //	end.latitude = move_along.a * end.longtitude + move_along.b;
            double x_start = end.longtitude;
            double y_start = end.latitude;
            double a = move_along.a;
            double b = move_along.b;
            //cout << "h x_start " << x_start << endl;
            //cout << "h y_start " << y_start << endl;

            //double d = 1 * MINUTE;
            double delta_a = (a * a + 1);
            double delta_b = (-2 * x_start + 2 * a * b - 2 * a * y_start);
            double delta_c = (x_start * x_start + b * b - 2 * b * y_start + y_start * y_start - 0.00000001 * Constants.MINUTE * Constants.MINUTE);

            double delta = delta_b * delta_b - 4 * delta_a * delta_c;
            //if (delta >= 0)
            {
                //cout << "chuj " << endl;
                double x = (-delta_b + sqrt(abs(delta))) / (2 * delta_a);
                double y = a * x + b;
                if ((x < x_start && !left) || (x > x_start && left)) {
                    x = (-delta_b - sqrt(abs(delta))) / (2 * delta_a);
                    y = a * x + b;
                }

                //cout << "h x " << x << endl;
                //cout << "h y " << y << endl;
                end.latitude = y;
                end.longtitude = x;
                if (i % 20 == 0) {
                    this.path.add(new Point(y, x));
                }
            }
            i++;
        }

        this.path.add(new Point(end.latitude, end.longtitude));
        return new Point(end.latitude, end.longtitude);
    }

    private Boundary get_next_horizontal_border(Boundary previous_border) {
        Boundary next_horizontal_border = previous_border;
        if (this.fly_up) {
            next_horizontal_border.b = next_horizontal_border.b + this.minute_width_lat * sqrt(next_horizontal_border.a * next_horizontal_border.a + 1);
        } else {
            next_horizontal_border.b = next_horizontal_border.b - this.minute_width_lat * sqrt(next_horizontal_border.a * next_horizontal_border.a + 1);
        }

        return next_horizontal_border;
    }

    private double det_matrix(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x1 * y2 + x2 * y3 + x3 * y1 - x3 * y2 - x1 * y3 - x2 * y1);
    }
    
    private Boundary get_vertical_border(Point p, double a, double b, boolean left) {
        double x1 = p.longtitude - 1.5 * Constants.MINUTE;
        double y1 = a * x1 + b;
        //cout << x1 << "   " << y1 << endl;
        double x2 = p.longtitude + 1.5 * Constants.MINUTE;
        double y2 = a * x2 + b;
        //cout << x2 << "   " << y2 << endl;
        for (int i = 0; i < this.t.borders.size(); i++) {
            Boundary bord = this.t.borders.get(i);
            //cout << bord.start_point.longtitude << "  " << bord.start_point.latitude << endl;
            //cout << bord.end_point.longtitude << "  " << bord.end_point.latitude << endl;

            if ((det_matrix(x1, y1, x2, y2, bord.start_point.longtitude, bord.start_point.latitude)) * (det_matrix(x1, y1, x2, y2, bord.end_point.longtitude, bord.end_point.latitude)) >= 0) //printf("Odcinki sie NIE przecinaja 1 \n");
            {
                continue;
            } else if ((det_matrix(x2, y2, bord.end_point.longtitude, bord.end_point.latitude, x1, y1)) * (det_matrix(bord.start_point.longtitude, bord.start_point.latitude, bord.end_point.longtitude, bord.end_point.latitude, x2, y2)) >= 0) //printf("Odcinki sie NIE przecinaja 2 \n");
            {
                continue;
            } else {
                //printf("chuj\n");
                double x_chuj = (bord.b - b) / (a - bord.a);
                double y_chuj = a * x_chuj + b;
                //cout << "p x " << p.longtitude << endl;
                //cout << "p y " << p.latitude << endl;
                //cout << "x_chuj " << x_chuj << endl;
                //cout << "y_chuj " << y_chuj << endl;

                if (left && abs(x_chuj - p.longtitude) > 0.00001) {
                    return bord;
                } else if (!left && abs(x_chuj - p.longtitude) > 0.00001) {
                    return bord;
                }
            }
        }
        //Sleep(100000);
        return new Boundary();
    }

    private void print_vector2(ArrayList<Point> v) {
        try {
            Locale.setDefault(Locale.US);
            PrintWriter writer = new PrintWriter("example.txt", "UTF-8");

            for (int i = 0; i < v.size(); i++) {
                writer.printf("{lat: %.15f, lng: %.15f}", v.get(i).latitude, v.get(i).longtitude);
                if (i < v.size() - 1) {
                    writer.printf(",");
                }
                writer.printf("\n");
            }
            writer.close();
        } catch (Exception e) {

        }
    }

    public ArrayList<Point> calculate_path() {
        //cout.precision(15);
        boolean horizontal = true;

        Boundary horizontal_flight = t.borders.get(0);
        t.borders.remove(0);
        if (this.fly_up) {
            horizontal_flight.b = this.minute_width_lat / 2 * sqrt(horizontal_flight.a * horizontal_flight.a + 1) + horizontal_flight.b;
        } else {
            horizontal_flight.b = -1 * this.minute_width_lat / 2 * sqrt(horizontal_flight.a * horizontal_flight.a + 1) + horizontal_flight.b;
        }

        int horizontal_end_index = t.get_horizontal_index();
        Boundary horizontal_end = new Boundary(); //górna granica
        if (horizontal_end_index > -1) {
            horizontal_end = t.borders.get(horizontal_end_index);
            t.borders.remove(horizontal_end_index);
        }

        Point start = horizontal_flight.start_point;
        //start.latitude = change_latitude(start.latitude, this->width / 2);
        start.latitude += minute_width_lat / 2;
        this.path.add(start);
        //cout << "*start lat " << start.latitude << endl;
        //cout << "*start long " << start.longtitude << endl;

        Point end = start;
        int i = 0;
        Boundary next_horizontal_border = horizontal_flight;
        while (i < 14)//!this->field_end)
        {
            //	cout << i << "." << endl;
            //	cout << "nastepna pozioma a: " << next_horizontal_border.a << endl;
            //	cout << "nastepna pozioma b: " << next_horizontal_border.b << endl;

            //nastêpna 'pionowa'
            Boundary next_border = get_vertical_border(end, next_horizontal_border.a, next_horizontal_border.b, false);//t.get_next_border(end, true, this->fly_up);
            //next_border.b -= this->minute_width_lat / 2 * sqrt(next_border.a * next_border.a + 1);
            //cout << "nastepna pionowa a: " << next_border.a << endl;
            //cout << "nastepna pionowa b: " << next_border.b << endl;

            //lecimy w prawo
            //cout << "w prawo" << endl;
            end = fly_horizontally(end, next_horizontal_border, false, next_border);
            //	cout << "*end lat" << end.latitude << endl;
            //	cout << "*end long " << end.longtitude << endl;
            //	Sleep(10000);

            //nastêpna pozioma granica
            next_horizontal_border = get_next_horizontal_border(next_horizontal_border);
            //cout << "nastepna pozioma a: " << next_horizontal_border.a << endl;
            //cout << "nastepna pozioma b: " << next_horizontal_border.b << endl;

            //if (i < 3)
            //{
            //cout << "w gore" << endl;
            //lecimy w górê
            end = fly_vertically(end, next_border, next_horizontal_border);
            if (this.field_end) {
                break;
            }

            //nastêpna 'pionowa'
            next_border = get_vertical_border(end, next_horizontal_border.a, next_horizontal_border.b, true);
            //cout << "**nastepna pionowa a: " << next_border.a << endl;
            //cout << "**nastepna pionowa b: " << next_border.b << endl;
            //next_border.b += this->minute_width_lat / 2 * sqrt(next_border.a * next_border.a + 1);
            //cout << "*nastepna pionowa a: " << next_border.a << endl;
            //cout << "*nastepna pionowa b: " << next_border.b << endl;
            //Sleep(10000);

            //lecimy w lewo
            //cout << "w lewo" << endl;
            //Sleep(1000);
            end = fly_horizontally(end, next_horizontal_border, true, next_border);
            next_horizontal_border = get_next_horizontal_border(next_horizontal_border);

            //lecimy w górê
            //cout << "w gore" << endl;
            end = fly_vertically(end, next_border, next_horizontal_border);
            //}
            i++;

        }
        print_vector2(this.path);
		return this.path;
    }

    public ArrayList<Point> get_path() {
        return this.path;
    }

    public void set_path_width(int w) {
        this.width = w;
    }

    public Path(Terrain t, boolean fly_up, int w) {
        this.t = t;
        this.fly_up = fly_up;
        this.width = w;
        this.path = new ArrayList<Point>();
        get_width_in_minutes();
    }
}
