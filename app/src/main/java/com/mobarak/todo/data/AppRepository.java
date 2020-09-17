package com.mobarak.todo.data;

import com.mobarak.todo.data.db.DbRepository;
import com.mobarak.todo.data.preference.PreferenceRepository;
import com.mobarak.todo.data.remote.RemoteRepository;

public interface AppRepository {

    DbRepository getDbRepository();

    PreferenceRepository getPrefRepository();

    RemoteRepository getRemoteRepository();
}
