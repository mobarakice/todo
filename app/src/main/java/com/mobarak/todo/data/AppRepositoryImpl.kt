package com.mobarak.todo.data;

import com.mobarak.todo.data.db.DbRepository;
import com.mobarak.todo.data.db.DbRepositoryImpl;
import com.mobarak.todo.data.preference.PreferenceRepository;
import com.mobarak.todo.data.preference.PreferenceRepositoryImpl;
import com.mobarak.todo.data.remote.RemoteRepository;
import com.mobarak.todo.data.remote.RemoteRepositoryImpl;

/**
 * This app repository implementation class and used to access all kind of data like local, preference or remote(network)
 *
 * @author mobarak
 */
public class AppRepositoryImpl implements AppRepository {
    private static AppRepositoryImpl mInstance;
    private DbRepository dbRepository;
    private PreferenceRepository prefRepository;
    private RemoteRepository remoteRepository;

    private AppRepositoryImpl() {
        dbRepository = DbRepositoryImpl.getInstance();
        prefRepository = PreferenceRepositoryImpl.getInstance();
        remoteRepository = RemoteRepositoryImpl.getInstance();
    }

    public static AppRepositoryImpl getInstance() {
        if (mInstance == null) {
            synchronized (AppRepositoryImpl.class) {
                if (mInstance == null) {
                    mInstance = new AppRepositoryImpl();
                }
            }
        }
        return mInstance;
    }

    @Override
    public DbRepository getDbRepository() {
        return dbRepository;
    }

    @Override
    public PreferenceRepository getPrefRepository() {
        return prefRepository;
    }

    @Override
    public RemoteRepository getRemoteRepository() {
        return remoteRepository;
    }
}
