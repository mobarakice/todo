package com.mobarak.todo.data

import com.mobarak.todo.data.db.DbRepository
import com.mobarak.todo.data.preference.PreferenceRepository
import com.mobarak.todo.data.remote.RemoteRepository

/**
 * This is app repository class and handle all kind data(local,preference, remote) related operation through this
 *
 * @author mobarak
 */
interface AppRepository {
    fun getDbRepository(): DbRepository
    fun getPrefRepository(): PreferenceRepository
    fun getRemoteRepository(): RemoteRepository
}