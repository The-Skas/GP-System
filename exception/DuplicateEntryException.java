/**
 * 
 */
package exception;

/**
 * @author VJ
 *
 */
public class DuplicateEntryException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DuplicateEntryException()
	{
		super("Duplicate Entry in Database");
	}
}
