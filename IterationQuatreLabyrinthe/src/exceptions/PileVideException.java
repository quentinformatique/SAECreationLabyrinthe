package exceptions;

/** 
 * exception levee si une pile est vide
 */
public class PileVideException extends RuntimeException {

	public PileVideException() {
		super();
	}

	public PileVideException(String message) {
		super(message);
	}
	
}
