package cabinvoicegenerator;

public class InvoiceService {

	private RideRepository rideRepository;
	private double totalFare;

	public void setRideRepository(RideRepository rideRepository){
		this.rideRepository = rideRepository;
	}

	public InvoiceSummary calculateTotalFare(Ride[] rides) {
		totalFare = 0;
		for (Ride ride : rides) {
			totalFare += ride.cabRide.calculateCostOfCabRide(ride);
		}
		return new InvoiceSummary(rides.length, totalFare);
	}

	public void addRides(String userId, Ride[] rides) throws InvoiceServiceException {
		if (userId == "" || userId == null) {
			throw new InvoiceServiceException("No user id provided", InvoiceServiceException.ExceptionType.EMPTY_USER_ID);
		}
		rideRepository.addRides(userId, rides);
	}

	public InvoiceSummary getInvoiceSummary(String userId) throws InvoiceServiceException {
		try {
			Ride[] rides = rideRepository.getRides(userId);
			return this.calculateTotalFare(rides);
		}catch (NullPointerException e) {
			throw new InvoiceServiceException("Wrong user id", InvoiceServiceException.ExceptionType.INVALID_USER_ID);
		}
	}
}
