/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ola
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	
		FlightArea fa = new FlightArea();
		List<PointLocation> borderPoints = fa.getFullFlightArea();
	
        Terrain t = new Terrain();

		for(int i = 0; i < borderPoints.size(); i++)
		{
			PointLocation p = borderPoints[i];
			Coordinates c = p.getCoordinates();
			t.add_point_coord(c.getLatitude(), c.getLongitude());
		}
	/*t.add_point_coord(50.087569, 20.192174);
	t.add_point_coord(50.086889, 20.210059);
	t.add_point_coord(50.088522, 20.208980);
	t.add_point_coord(50.090294, 20.208766);
	t.add_point_coord(50.090220, 20.192249);
	t.add_point_coord(50.089507, 20.192356);
	t.add_point_coord(50.087569, 20.192174);*/
 
 
		t.generate_borders();
 
		Path p = new Path(t, true, fa.getPathResolution());
		List<Point> path = p.calculate_path();
		DronePath dp = new DronePath();
		List<PointLocation> path2 = new ArrayList<PointLocation>();
		for(int i = 0; i < path.size(); i++)
		{
			Point p = path[i];
			Coordinates cr = new Coordinates(p.latitude, p.longtitude);
			PointLocation pl = new PointLocation("point"+i, i, cr);
			path2.add(pl);
		}
    }
    
}
