package com.mobarak.todo.data.remote;

/**
 * This remote(network) repository implementation class, all business logic that related to network will be implemented here
 *
 * @author mobarak
 */
public class RemoteRepositoryImpl implements RemoteRepository {
    private static RemoteRepositoryImpl mInstance;

    private RemoteRepositoryImpl() {
    }

    public static RemoteRepositoryImpl getInstance() {
        if (mInstance == null) {
            synchronized (RemoteRepositoryImpl.class) {
                if (mInstance == null) {
                    mInstance = new RemoteRepositoryImpl();
                }
            }
        }
        return mInstance;
    }
}
