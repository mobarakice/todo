package com.mobarak.todo.data.preference;


/**
 * This preference repository implementation class, all business logic that related to sharedpreference will be implemented here
 *
 * @author mobarak
 */
public class PreferenceRepositoryImpl implements PreferenceRepository {
    private static PreferenceRepositoryImpl mInstance;

    private PreferenceRepositoryImpl() {
    }

    public static PreferenceRepositoryImpl getInstance() {
        if (mInstance == null) {
            synchronized (PreferenceRepositoryImpl.class) {
                if (mInstance == null) {
                    mInstance = new PreferenceRepositoryImpl();
                }
            }
        }
        return mInstance;
    }
}
