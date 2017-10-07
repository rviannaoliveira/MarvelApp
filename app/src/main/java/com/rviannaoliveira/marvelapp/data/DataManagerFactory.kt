package com.rviannaoliveira.marvelapp.data

/**
 * Criado por rodrigo on 01/10/17.
 */
object DataManagerFactory {
    private var dataManager: DataManager? = null

    fun getDefaultInstance(): DataManager? {
        if (dataManager == null) {
            return DataManager()
        }
        return dataManager
    }
}