package path;

import dataModel.Coordinates;

import java.io.PrintWriter;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Path {

    private List<Coordinates> path;
    private Terrain t;

    private double minute_width_lat;
    private double minute_width_lon;
    private int width;
    private boolean fly_up;
    private boolean field_end = false;

    public Path(Terrain t, boolean fly_up, int w) {
        this.t = t;
        this.fly_up = fly_up;
        this.width = w;
        this.path = new ArrayList<>();
        get_width_in_minutes();
    }

    private void get_width_in_minutes() {
        this.minute_width_lat = (width * Constants.MINUTE) / Constants.MINUTE_METERS;
        double avg_lat = get_avg_latitude();
        this.minute_width_lon = (width * Constants.MINUTE) / (Constants.MINUTE_METERS * cos(avg_lat));
    }

    private double get_avg_latitude() {
        List<Coordinates> points = t.getBoundaryPoints();
        Coordinates max = points.get(0);
        Coordinates min = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Coordinates p = points.get(i);
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

    private Coordinates fly_vertically(Coordinates/*&*/ end_arg, Boundary move_along, Boundary next_border) {
        int i = 0;
        double step = 1;
        if (!this.fly_up) {
            step = -1;
        }
        Coordinates end = new Coordinates();
        end.latitude = end_arg.latitude;
        end.longitude = end_arg.longitude;
        double a1 = next_border.factorA;
        double b1 = next_border.factorB;
        double a2 = move_along.factorA;
        double b2 = move_along.factorB;
        double x_prze = (b2 - b1) / (a1 - a2);
        double y_prze = a1 * x_prze + b1;

        double x_chuj = (move_along.factorB - next_border.factorB) / (next_border.factorA - move_along.factorA);
        double y_chuj = move_along.factorA * x_chuj + move_along.factorB;

        while (abs(end.longitude - x_chuj) > 0.00001 && abs(end.latitude - y_chuj) > 0.00001) {

            double x_start = end.longitude;
            double y_start = end.latitude;
            double a = move_along.factorA;
            double b = move_along.factorB;

            double delta_a = (a * a + 1);
            double delta_b = (-2 * x_start + 2 * a * b - 2 * a * y_start);
            double delta_c = (x_start * x_start + b * b - 2 * b * y_start + y_start * y_start - 0.00000001 * Constants.MINUTE * Constants.MINUTE);

            double delta = delta_b * delta_b - 4 * delta_a * delta_c;
            {
                double x = (-delta_b + sqrt(abs(delta))) / (2 * delta_a);
                double y = a * x + b;
                if (y < y_start) {
                    x = (-delta_b - sqrt(abs(delta))) / (2 * delta_a);
                    y = a * x + b;
                }

                end.latitude = y;
                end.longitude = x;
                if (i % 20 == 0) {
                    this.path.add(new Coordinates(y, x));
                }
            }

            i++;
        }

        this.path.add(new Coordinates(end.latitude, end.longitude));
        return new Coordinates(end.latitude, end.longitude);
    }

    private Coordinates fly_horizontally(Coordinates/*&*/ end_arg, Boundary move_along, boolean left, Boundary next_border) {
        int i = 0;
        double step = 1;
        if (left) {
            step = -1;
        }
        Coordinates end = new Coordinates();
        end.latitude = end_arg.latitude;
        end.longitude = end_arg.longitude;
        double x_chuj = (move_along.factorB - next_border.factorB) / (next_border.factorA - move_along.factorA);
        double y_chuj = move_along.factorA * x_chuj + move_along.factorB;

        while (abs(end.longitude - x_chuj) > 0.000001 && abs(end.latitude - y_chuj) > 0.000001) {
            double x_start = end.longitude;
            double y_start = end.latitude;
            double a = move_along.factorA;
            double b = move_along.factorB;

            double delta_a = (a * a + 1);
            double delta_b = (-2 * x_start + 2 * a * b - 2 * a * y_start);
            double delta_c = (x_start * x_start + b * b - 2 * b * y_start + y_start * y_start - 0.00000001 * Constants.MINUTE * Constants.MINUTE);

            double delta = delta_b * delta_b - 4 * delta_a * delta_c;
            {
                double x = (-delta_b + sqrt(abs(delta))) / (2 * delta_a);
                double y = a * x + b;
                if ((x < x_start && !left) || (x > x_start && left)) {
                    x = (-delta_b - sqrt(abs(delta))) / (2 * delta_a);
                    y = a * x + b;
                }

                end.latitude = y;
                end.longitude = x;
                if (i % 20 == 0) {
                    this.path.add(new Coordinates(y, x));
                }
            }
            i++;
        }

        this.path.add(new Coordinates(end.latitude, end.longitude));
        return new Coordinates(end.latitude, end.longitude);
    }

    private Boundary get_next_horizontal_border(Boundary previous_border) {
        Boundary next_horizontal_border = previous_border;
        if (this.fly_up) {
            next_horizontal_border.factorB = next_horizontal_border.factorB + this.minute_width_lat * sqrt(next_horizontal_border.factorA * next_horizontal_border.factorA + 1);
        } else {
            next_horizontal_border.factorB = next_horizontal_border.factorB - this.minute_width_lat * sqrt(next_horizontal_border.factorA * next_horizontal_border.factorA + 1);
        }

        return next_horizontal_border;
    }

    private double det_matrix(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (x1 * y2 + x2 * y3 + x3 * y1 - x3 * y2 - x1 * y3 - x2 * y1);
    }

    private Boundary get_vertical_border(Coordinates p, double a, double b, boolean left) {
        double x1 = p.longitude - 1.5 * Constants.MINUTE;
        double y1 = a * x1 + b;
        double x2 = p.longitude + 1.5 * Constants.MINUTE;
        double y2 = a * x2 + b;
        for (int i = 0; i < this.t.borders.size(); i++) {
            Boundary bord = this.t.borders.get(i);

            if ((det_matrix(x1, y1, x2, y2, bord.startPoint.longitude, bord.startPoint.latitude)) * (det_matrix(x1, y1, x2, y2, bord.endPoint.longitude, bord.endPoint.latitude)) >= 0) {
                continue;
            } else if ((det_matrix(x2, y2, bord.endPoint.longitude, bord.endPoint.latitude, x1, y1)) * (det_matrix(bord.startPoint.longitude, bord.startPoint.latitude, bord.endPoint.longitude, bord.endPoint.latitude, x2, y2)) >= 0) {
                continue;
            } else {
                double x_chuj = (bord.factorB - b) / (a - bord.factorA);
                double y_chuj = a * x_chuj + b;

                if (left && abs(x_chuj - p.longitude) > 0.00001) {
                    return bord;
                } else if (!left && abs(x_chuj - p.longitude) > 0.00001) {
                    return bord;
                }
            }
        }
        return new Boundary();
    }

    private void print_vector2(List<Coordinates> v) {
        try {
            Locale.setDefault(Locale.US);
            PrintWriter writer = new PrintWriter("example.txt", "UTF-8");

            for (int i = 0; i < v.size(); i++) {
                writer.printf("{lat: %.15f, lng: %.15f}", v.get(i).latitude, v.get(i).longitude);
                if (i < v.size() - 1) {
                    writer.printf(",");
                }
                writer.printf("\n");
            }
            writer.close();
        } catch (Exception e) {

        }
    }

    public List<Coordinates> calculate_path() {
        boolean horizontal = true;

        Boundary horizontal_flight = t.borders.get(0);
        t.borders.remove(0);
        if (this.fly_up) {
            horizontal_flight.factorB = this.minute_width_lat / 2 * sqrt(horizontal_flight.factorA * horizontal_flight.factorA + 1) + horizontal_flight.factorB;
        } else {
            horizontal_flight.factorB = -1 * this.minute_width_lat / 2 * sqrt(horizontal_flight.factorA * horizontal_flight.factorA + 1) + horizontal_flight.factorB;
        }

        int horizontal_end_index = t.getHorizontalIndex();
        Boundary horizontal_end = new Boundary();
        if (horizontal_end_index > -1) {
            horizontal_end = t.borders.get(horizontal_end_index);
            t.borders.remove(horizontal_end_index);
        }

        Coordinates start = horizontal_flight.startPoint;
        start.latitude += minute_width_lat / 2;
        this.path.add(start);

        Coordinates end = start;
        int i = 0;
        Boundary next_horizontal_border = horizontal_flight;
        while (i < 14) {
            Boundary next_border = get_vertical_border(end, next_horizontal_border.factorA, next_horizontal_border.factorB, false);
            end = fly_horizontally(end, next_horizontal_border, false, next_border);
            next_horizontal_border = get_next_horizontal_border(next_horizontal_border);
            end = fly_vertically(end, next_border, next_horizontal_border);
            if (this.field_end) {
                break;
            }

            next_border = get_vertical_border(end, next_horizontal_border.factorA, next_horizontal_border.factorB, true);
            end = fly_horizontally(end, next_horizontal_border, true, next_border);
            next_horizontal_border = get_next_horizontal_border(next_horizontal_border);

            end = fly_vertically(end, next_border, next_horizontal_border);
            i++;

        }
        print_vector2(this.path);
        return this.path;
    }

    public List<Coordinates> get_path() {
        return this.path;
    }

    public void set_path_width(int w) {
        this.width = w;
    }

}
