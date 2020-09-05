package cabinvoicegenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

	@Mock
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
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	//Test case for returning total fare
	@Test
	public void givenDistanceAndTime_WhenCalculated_ShouldReturnTotalFare() {
		double distance = 2.0;
		int time = 5;
		double fare = invoiceService.calculateFare(distance, time);
		Assert.assertEquals(25, fare, 0.0);
	}

	//Test case when given wrong expected value
	@Test
	public void givenDistanceAndTime_WhenTheExpectedValueIsWrong_ShouldFailAndThrowAssertionError() {
		double distance = 2.0;
		int time = 5;
		double fare = invoiceService.calculateFare(distance, time);
		Assert.assertNotEquals(50, fare, 0.0);
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
		rides = new Ride[]
				  { new Ride(2.0, 5, CabRide.NORMAL),
						    new Ride(0.1, 1, CabRide.PREMIUM)
				  };
		InvoiceSummary summary = invoiceService.calculateTotalFare(rides);
		expectedInvoiceSummary = new InvoiceSummary(2, 45);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}

	//Test case for number of rides,total fare and average fare for multiple normal rides
	@Test
	public void givenDistanceAndTime_WhenCalculatedForMultipleNormalRides_ShouldReturnInvoiceSummary() {
		rides = new Ride[]
				  { new Ride(2.0, 5, CabRide.NORMAL),
							 new Ride(4, 4, CabRide.NORMAL)
				  };
		InvoiceSummary summary = invoiceService.calculateTotalFare(rides);
		expectedInvoiceSummary = new InvoiceSummary(2, 69);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}

	//Test case for number of rides,total fare and average fare for multiple premium rides
	@Test
	public void givenDistanceAndTime_WhenCalculatedForMultiplePremiumRides_ShouldReturnInvoiceSummary() {
		rides = new Ride[]
				  { new Ride(3, 5, CabRide.PREMIUM),
							 new Ride(0.1, 1, CabRide.PREMIUM)
				  };
		InvoiceSummary summary = invoiceService.calculateTotalFare(rides);
		expectedInvoiceSummary = new InvoiceSummary(2, 75);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}

	//Test case for number of rides,total fare and average fare for null rides
	@Test(expected = NullPointerException.class)
	public void givenDistanceAndTime_WhenGivenNullRides_ShouldThrowNullPointerException() {
		rides = new Ride[]
				  { new Ride(3, 5, CabRide.PREMIUM),
							 new Ride(0.1, 1, CabRide.PREMIUM)
				  };
		InvoiceSummary summary = invoiceService.calculateTotalFare(null);
		expectedInvoiceSummary = new InvoiceSummary(2, 75);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}

	//Test case for given user id and ride list
	@Test
	public void givenUserIdAndRides_WhenCalculatedForFare_ShouldReturnInvoiceSummary() {
		rides = new Ride[]
				  { new Ride(2.0, 5, CabRide.NORMAL),
							 new Ride(0.1, 1, CabRide.PREMIUM),
						    new Ride(10, 3, CabRide.NORMAL),
						    new Ride(1.3, 2, CabRide.PREMIUM)
				  };
		invoiceService.addRides(userId, rides);
		InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
		expectedInvoiceSummary = new InvoiceSummary(4, 171.5);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}

	//Test case for null rides and null userId 
	@Test(expected = NullPointerException.class)
	public void givenUserIdAndRides_WhenGivenNullRideAndUserId_ShouldThrowNullPointerException() {
		rides = new Ride[]
				  { new Ride(2.0, 5, CabRide.NORMAL),
							 new Ride(0.1, 1, CabRide.PREMIUM),
							 new Ride(10, 3, CabRide.NORMAL),
							 new Ride(1.3, 2, CabRide.PREMIUM)
				  };
		invoiceService.addRides(null, null);
		InvoiceSummary summary = invoiceService.getInvoiceSummary(null);
		expectedInvoiceSummary = new InvoiceSummary(4, 171.5);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}

	//Test case for wrong summary value
	@Test
	public void givenUserIdAndRides_WhenGivenWrongInvoiceSummary_ShouldThrowAssertionError() {
		rides = new Ride[]
				  { new Ride(2.0, 5, CabRide.NORMAL),
							 new Ride(0.1, 1, CabRide.PREMIUM),
							 new Ride(10, 3, CabRide.NORMAL),
							 new Ride(1.3, 2, CabRide.PREMIUM)
				  };
		invoiceService.addRides(userId, rides);
		InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
		expectedInvoiceSummary = new InvoiceSummary(4, 171.5);
		Assert.assertNotEquals(expectedInvoiceSummary, expectedException);
	}

	//Test case for null userId
	@Test(expected = NullPointerException.class)
	public void givenUserIdAndRides_WhenGivenNullUserId_ShouldThrowNullPointerException() {
		rides = new Ride[]
				  { new Ride(2.0, 5, CabRide.NORMAL),
							 new Ride(0.1, 1, CabRide.PREMIUM),
							 new Ride(10, 3, CabRide.NORMAL),
							 new Ride(1.3, 2, CabRide.PREMIUM)
				  };
		invoiceService.addRides(userId, rides);
		InvoiceSummary summary = invoiceService.getInvoiceSummary(null);
		expectedInvoiceSummary = new InvoiceSummary(4, 171.5);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}

	//Test case for wrong expected value
	@Test
	public void givenUserIdAndRides_WhenGivenWrongExpectedValue_ShouldThrowAssertionError() {
		rides = new Ride[]
				  { new Ride(2.0, 5, CabRide.NORMAL),
							 new Ride(0.1, 1, CabRide.PREMIUM),
							 new Ride(10, 3, CabRide.NORMAL),
							 new Ride(1.3, 2, CabRide.PREMIUM)
				  };
		invoiceService.addRides(userId, rides);
		InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
		expectedInvoiceSummary = new InvoiceSummary(5, 171.5);
		Assert.assertNotEquals(expectedInvoiceSummary, expectedException);
	}
}