package path;

public class Main {

    public static void main(String[] args) {

        FlightArea fa = new FlightArea();
        List<PointLocation> borderPoints = fa.getFullFlightArea();

        Terrain t = new Terrain();

        for (int i = 0; i < borderPoints.size(); i++) {
            PointLocation p = borderPoints[i];
            Coordinates c = p.getCoordinates();
            t.addPointCoordinates(c.getLatitude(), c.getLongitude());
        }

        t.generateBorders();

        Path p = new Path(t, true, fa.getPathResolution());
        List<Coordinates> path = p.calculate_path();
        DronePath dp = new DronePath();
        List<PointLocation> path2 = new ArrayList<PointLocation>();
        for (int i = 0; i < path.size(); i++) {
            Coordinates p = path[i];
            Coordinates cr = new Coordinates(p.latitude, p.longitude);
            PointLocation pl = new PointLocation("point" + i, i, cr);
            path2.add(pl);
        }
    }

}
