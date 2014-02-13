/**
 * 
 */
package exception;

/**
 * @author VJ
 *
 */
public class DuplicateEntryException extends Exception {

	private static final long serialVersionUID = 6807729861972260771L;
	
	public DuplicateEntryException()
	{
		super("Duplicate Entry in Database");
	}
}
