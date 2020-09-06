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
	public void givenDistanceAndTime_WhenGivenNullRides_ShouldThrowException() {
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
	public void givenUserIdAndRides_WhenCalculatedForFare_ShouldReturnInvoiceSummary() throws InvoiceServiceException {
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

	//Test case for wrong summary value
	@Test
	public void givenUserIdAndRides_WhenGivenWrongInvoiceSummary_ShouldThrowException() throws InvoiceServiceException {
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
	@Test
	public void givenUserIdAndRides_WhenGivenNullUserId_ShouldThrowNullPointerException() {
		try {
			userId = null;
			rides = new Ride[]
					  {new Ride(2.0, 5, CabRide.NORMAL),
							    new Ride(0.1, 1, CabRide.PREMIUM),
							    new Ride(10, 3, CabRide.NORMAL),
							    new Ride(1.3, 2, CabRide.PREMIUM)
					  };
			invoiceService.addRides(userId, rides);
			InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
			expectedInvoiceSummary = new InvoiceSummary(4, 171.5);
			Assert.assertEquals(expectedInvoiceSummary, summary);
		} catch (InvoiceServiceException e) {
			Assert.assertEquals(InvoiceServiceException.ExceptionType.EMPTY_USER_ID, e.type);
		}
	}

	//Test case for empty userId
	@Test
	public void givenUserIdAndRides_WhenGivenEmptyUserId_ShouldThrowNullPointerException() {
		try {
			userId = "";
			rides = new Ride[]
					  {new Ride(2.0, 5, CabRide.NORMAL),
								 new Ride(0.1, 1, CabRide.PREMIUM),
								 new Ride(10, 3, CabRide.NORMAL),
								 new Ride(1.3, 2, CabRide.PREMIUM)
					  };
			invoiceService.addRides(userId, rides);
			InvoiceSummary summary = invoiceService.getInvoiceSummary(userId);
			expectedInvoiceSummary = new InvoiceSummary(4, 171.5);
			Assert.assertEquals(expectedInvoiceSummary, summary);
		} catch (InvoiceServiceException e) {
			Assert.assertEquals(InvoiceServiceException.ExceptionType.EMPTY_USER_ID, e.type);
		}
	}

	//Test case for wrong expected value
	@Test
	public void givenUserIdAndRides_WhenGivenWrongExpectedValue_ShouldThrowAssertionError() throws InvoiceServiceException {
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

	//Test case for wrong userId
	@Test
	public void givenWrongUserIdAndRides_WhenCalculatedForFare_ShouldReturnInvoiceSummary() {
		try {
			String userId1 = "Exo";
			rides = new Ride[]
					  {new Ride(2.0, 5, CabRide.NORMAL),
							    new Ride(0.1, 1, CabRide.PREMIUM),
							    new Ride(10, 3, CabRide.NORMAL),
							    new Ride(1.3, 2, CabRide.PREMIUM)
					  };
			invoiceService.addRides(userId, rides);
			InvoiceSummary summary = invoiceService.getInvoiceSummary(userId1);
			expectedInvoiceSummary = new InvoiceSummary(4, 171.5);
			Assert.assertEquals(expectedInvoiceSummary, summary);
		} catch(InvoiceServiceException e) {
			Assert.assertEquals(InvoiceServiceException.ExceptionType.INVALID_USER_ID, e.type);
		}
	}
}