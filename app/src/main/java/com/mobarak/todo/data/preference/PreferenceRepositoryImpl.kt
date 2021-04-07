package com.mobarak.todo.data.preference

/**
 * This preference repository implementation class, all business logic that related to sharedpreference will be implemented here
 *
 * @author mobarak
 */
object PreferenceRepositoryImpl : PreferenceRepository {
    private var mInstance: PreferenceRepositoryImpl? = null
    val instance: PreferenceRepositoryImpl?
        get() {
            if (mInstance == null) {
                synchronized(PreferenceRepositoryImpl::class.java) {
                    if (mInstance == null) {
                        mInstance = PreferenceRepositoryImpl()
                    }
                }
            }
            return mInstance
        }
}