package co.edu.pdam.eci.persistenceapiintegration.network;

/**
 * Created by 2105409 on 4/18/18.
 */

public interface RequestCallback<T> {
    void onSuccess( T response );

    void onFailed( NetworkException e );
}