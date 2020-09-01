package cabinvoicegenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvoiceServiceTest {
	
	InvoiceService invoiceService;
	String userId = "bts";
	Ride[] rides;
	InvoiceSummary expectedInvoiceSummary;
	RideRepository rideRepository;

	@Before
	public void initialSetUp() {
		invoiceService = new InvoiceService();
		rideRepository = new RideRepository();
		invoiceService.setRideRepository(rideRepository);
		rides = new Ride[]
				  { new Ride(2.0, 5, CabRide.NORMAL),
				  new Ride(0.1, 1, CabRide.PREMIUM)
		};
		expectedInvoiceSummary = new InvoiceSummary(2, 45);
	}

	//Test case for returning total fare
	@Test
	public void givenDistanceAndTime_WhenCalculated_ShouldReturnTotalFare() {
		double distance = 2.0;
		int time = 5;
		double fare = invoiceService.calculateFare(distance, time);
		Assert.assertEquals(25, fare, 0.0);
	}

	//Test case for minimum fare
	@Test
	public void givenLessDistanceOrTime_WhenCalculated_ShouldReturnMinimumFare() {
		double distance = 0.1;
		int time = 1;
		double fare = invoiceService.calculateFare(distance, time);
		Assert.assertEquals(5, fare, 0.0);
	}

	//Test case for number of rides,total fare and average fare for multiple rides
	@Test
	public void givenDistanceAndTime_WhenCalculatedForMultipleRides_ShouldReturnInvoiceSummary() {
		InvoiceSummary summary = invoiceService.calculateTotalFare(rides);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}

	//Test case for given user id and ride list
	@Test
	public void givenUserIdAndRides_WhenCalculatedForFare_ShouldReturnInvoiceSummary() {
		invoiceService.addRides(userId, rides);
		InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}
}

