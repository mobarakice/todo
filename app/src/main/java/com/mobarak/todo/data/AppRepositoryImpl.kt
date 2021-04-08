package com.mobarak.todo.data

import com.mobarak.todo.data.db.DbRepository
import com.mobarak.todo.data.db.DbRepositoryImpl
import com.mobarak.todo.data.preference.PreferenceRepository
import com.mobarak.todo.data.preference.PreferenceRepositoryImpl
import com.mobarak.todo.data.remote.RemoteRepository
import com.mobarak.todo.data.remote.RemoteRepositoryImpl

/**
 * This app repository implementation class and used to access all kind of data like local, preference or remote(network)
 *
 * @author mobarak
 */
class AppRepositoryImpl private constructor() : AppRepository {
    private var dbRepository: DbRepository

    companion object {
        private var mInstance: AppRepositoryImpl? = null
        fun getInstance(): AppRepositoryImpl {
            if (mInstance == null) {
                synchronized(AppRepositoryImpl::class.java) {
                    if (mInstance == null) {
                        mInstance = AppRepositoryImpl()
                    }
                }
            }
            return mInstance!!
        }
    }

    init {
        dbRepository = DbRepositoryImpl.getInstance()!!
    }

    override fun getDbRepository(): DbRepository = dbRepository

    override fun getPrefRepository(): PreferenceRepository {
        TODO("Not yet implemented")
    }

    override fun getRemoteRepository(): RemoteRepository {
        TODO("Not yet implemented")
    }
}