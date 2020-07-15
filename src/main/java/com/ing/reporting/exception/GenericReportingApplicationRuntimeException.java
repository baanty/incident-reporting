package com.ing.reporting.exception;

/**
 * Use this exception to catch generic application exceptions and 
 * trap those exceptions in the custome exception handler, <code>CustomGlobalExceptionHandler</code>
 * class.
 * 
 * @author Pijush Kanti Das.
 *
 */
public class GenericReportingApplicationRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -2665276316016963349L;
	
	/**
	 * This is the constructor for the Custom exception.
	 * USe the event id to detail the error to the user.
	 * @param eventId : The event id, on which operation was performed.
	 * 
	 */
	public GenericReportingApplicationRuntimeException(int eventId, Throwable throwable) {
		super("Exception Occured while doing operation for event id - " + eventId + ". The real error is - " + throwable.getMessage(), throwable);
	}
	
	/**
	 * This constructor for the Custom exception.
	 * USe the event id to detail the error to the user.
	 * @param throwable : Original Throwable.
	 * 
	 */
	public GenericReportingApplicationRuntimeException(Throwable throwable) {
		super("Exception Occured while doing operation. Detailed exception is - " , throwable);
	}

}
