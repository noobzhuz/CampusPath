package backend;

public class Coordinate {

	private String name;
	private String id;
	private double x;
	private double y;

	/**
	 * @param: name_  The building
	 * @param: id_    The building id
	 * @param: x_     The x-coordinate of the building
	 * @param: y_     The y-coordinate of the building
	 * @requires: id_ != null
	 * @modifies: this
	 * @effects:  constructs a new Coordinate with the given name, id, x, and y values.
	 * @returns:  a new Coordinate instance.
	 */
	public Coordinate(String name_, String id_, double x_, double y_) {
		this.name = name_;
		this.id = id_;
		this.x = x_;
		this.y = y_;
	}

	/**
	 * @returns:  the name of this coordinate (may be an empty string).
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @returns:  the unique id of this coordinate.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @returns:  the x-coordinate of this location.
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @returns:  the y-coordinate of this location.
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * @param: other  Another Coordinate whose distance from this Coordinate is to be computed.
	 * @requires: other != null
	 * @returns:  the distance between this Coordinate and other,
	 *            computed as sqrt( (x - other.x)^2 + (y - other.y)^2 ).
	 */
	public double distance(Coordinate other) {
		return Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
	}

	/**
	 * @param: a  The starting Coordinate of the edge.
	 * @param: b  The ending Coordinate of the edge.
	 * @requires: a != null && b != null
	 * @returns:  the angle in degrees of the line from a to b relative to the
	 *            positive x-axis
	 */
	public static double angle(Coordinate a, Coordinate b) {
		double deltaX = b.getX() - a.getX();
		double deltaY = b.getY() - a.getY();
		return Math.toDegrees(Math.atan2(deltaY, deltaX));
	}

	/**
	 * @returns:  identify if a location is an intersection
	 */
	public String printName() {
		if (name.equals(new String())) {
			return "Intersection " + id;
		} else {
			return name;
		}
	}
}
