package cabinvoicegenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvoiceServiceTest {

	public InvoiceService invoiceService;

	@Before
	public void initialSetUp() throws Exception {
		invoiceService = new InvoiceService();
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
		Ride[] rides = { new Ride(2.0, 5),
				  new Ride(0.1, 1)
		};
		InvoiceSummary summary = invoiceService.calculateFare(rides);
		InvoiceSummary expectedInvoiceSummary = new InvoiceSummary(2, 30);
		Assert.assertEquals(expectedInvoiceSummary, summary);
	}
}

