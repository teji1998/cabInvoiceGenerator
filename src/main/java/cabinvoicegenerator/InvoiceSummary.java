package cabinvoicegenerator;

public class InvoiceSummary {
	private final int numberOfRides;
	private final double totalFare;
	private final double totalFareAverage;

	public InvoiceSummary(int numberOfRides, double totalFare) {
		this.numberOfRides = numberOfRides;
		this.totalFare = totalFare;
		this.totalFareAverage = this.totalFare / this.numberOfRides;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InvoiceSummary summary = (InvoiceSummary) o;
		return numberOfRides == summary.numberOfRides &&
				  Double.compare(summary.totalFare, totalFare) == 0 &&
				  Double.compare(summary.totalFareAverage, totalFareAverage) == 0;
	}
}
