package com.mobarak.todo.data.preference;

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
