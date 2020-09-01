package cabinvoicegenerator;

public class InvoiceService {
	private static final int COST_PER_TIME = 1 ;
	private static final double MINIMUM_COST_PER_KILOMETER = 10.0;
	private static final double MINIMUM_FARE = 5;
	private RideRepository rideRepository;
	private double totalFare;

	public InvoiceService() {
		this.rideRepository = new RideRepository();
	}

	public double calculateFare(double distance, int time) {
		totalFare = distance * MINIMUM_COST_PER_KILOMETER + time * COST_PER_TIME;
		return Math.max(totalFare, MINIMUM_FARE);
	}

	public InvoiceSummary calculateFare(Ride[] rides) {
		totalFare = 0;
		for (Ride ride : rides) {
			totalFare += this.calculateFare(ride.distance, ride.time);
		}
		return new InvoiceSummary(rides.length, totalFare);
	}

	public void addRides(String userId, Ride[] rides) {
		rideRepository.addRides(userId, rides);
	}

	public InvoiceSummary getInvoiceSummary(String userId) {
		return this.calculateFare(rideRepository.getRides(userId));
	}
}
