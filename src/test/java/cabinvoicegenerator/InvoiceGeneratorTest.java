package cabinvoicegenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InvoiceGeneratorTest {

	public InvoiceGenerator invoiceGenerator;

	@Before
	public void initialSetUp() throws Exception {
		invoiceGenerator = new InvoiceGenerator();
	}

	//Test case for returning total fare
	@Test
	public void givenDistanceAndTime_WhenCalculated_ShouldReturnTotalFare() {
		double distance = 2.0;
		int time = 5;
		double fare = invoiceGenerator.calculateFare(distance, time);
		Assert.assertEquals(25, fare, 0.0);
	}

	//Test case for minimum fare
	@Test
	public void givenLessDistanceOrTime_WhenCalculated_ShouldReturnMinimumFare() {
		double distance = 0.1;
		int time = 1;
		double fare = invoiceGenerator.calculateFare(distance, time);
		Assert.assertEquals(5, fare, 0.0);
	}

	//Test case to find fare for multiple rides
	@Test
	public void givenMultipleRides_WhenCalculated_ShouldReturnTotalFare() {
		Ride[] rides = { new Ride(2.0, 5),
				  new Ride(0.1, 1)
		};
		double fare = invoiceGenerator.calculateFare(rides);
		Assert.assertEquals(30, fare, 0.0);
	}
}

