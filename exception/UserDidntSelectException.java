/**
 * 
 */
package exception;

/**
 * @author VJ
 *
 */
public class UserDidntSelectException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public UserDidntSelectException()
	{
		super("No Results from SQL Query");
	}
}
