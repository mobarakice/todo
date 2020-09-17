package com.mobarak.todo.data.remote;

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
