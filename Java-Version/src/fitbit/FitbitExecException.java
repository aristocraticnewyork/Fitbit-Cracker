package fitbit;

/**
 * Created by aurelienschiltz on 11/08/2016.
 */
public class FitbitExecException extends RuntimeException {

    private static final long serialVersionUID = 8555581489120167250L;

    public FitbitExecException( ) {
        super( );
    }

    public FitbitExecException( Throwable t ) {
        super( t );
    }
}