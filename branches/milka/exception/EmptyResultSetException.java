/**
 * 
 */
package exception;

/**
 * @author VJ
 *
 */
public class EmptyResultSetException extends Exception {

	private static final long serialVersionUID = -7625074051990852950L;
	
	public EmptyResultSetException()
	{
		super("No Results from SQL Query");
	}
}
