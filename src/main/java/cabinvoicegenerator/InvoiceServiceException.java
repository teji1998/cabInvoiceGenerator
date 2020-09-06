package cabinvoicegenerator;

public class InvoiceServiceException extends Exception {
	public enum ExceptionType {
		EMPTY_USER_ID,
		INVALID_USER_ID;
	}

	public ExceptionType type;

	public InvoiceServiceException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}
