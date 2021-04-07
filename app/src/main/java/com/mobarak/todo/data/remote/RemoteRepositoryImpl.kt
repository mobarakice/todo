package com.mobarak.todo.data.remote

/**
 * This remote(network) repository implementation class, all business logic that related to network will be implemented here
 *
 * @author mobarak
 */
object RemoteRepositoryImpl : RemoteRepository {
    private var mInstance: RemoteRepositoryImpl? = null
    val instance: RemoteRepositoryImpl?
        get() {
            if (mInstance == null) {
                synchronized(RemoteRepositoryImpl::class.java) {
                    if (mInstance == null) {
                        mInstance = RemoteRepositoryImpl()
                    }
                }
            }
            return mInstance
        }
}