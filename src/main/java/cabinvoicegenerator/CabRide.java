package cabinvoicegenerator;

public enum CabRide {
	NORMAL(10.0, 1.0, 5),
	PREMIUM(15.0, 2.0, 20);

	private final double costPerKilometer;
	private final double costPerMinute;
	private final int minimumFarePerRide;

	CabRide(double costPerKilometer, double costPerMinute, int minimumFarePerRide) {
		this.costPerKilometer = costPerKilometer;
		this.costPerMinute = costPerMinute;
		this.minimumFarePerRide = minimumFarePerRide;
	}

	public double calculateCostOfCabRide(Ride ride) {
		double costOfRide = ride.distance * costPerKilometer + ride.time * costPerMinute;
		return Math.max(costOfRide, minimumFarePerRide);
	}
}
