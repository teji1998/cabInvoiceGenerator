package cabinvoicegenerator;

import lombok.EqualsAndHashCode;

//Used for Equal and hash methods
@EqualsAndHashCode

public class InvoiceSummary {
	private final int numberOfRides;
	private final double totalFare;
	private final double totalFareAverage;

	public InvoiceSummary(int numberOfRides, double totalFare) {
		this.numberOfRides = numberOfRides;
		this.totalFare = totalFare;
		this.totalFareAverage = this.totalFare / this.numberOfRides;
	}
}
