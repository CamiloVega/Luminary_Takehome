package cvdevelopers.takehome.datarepository

import cvdevelopers.takehome.api.RandomUserApiEndpoint
import cvdevelopers.takehome.cache.dao.ClientCacheDao
import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.pagination.UserDataSourceFactory
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class UserDataRepository @Inject constructor(
        private val randomUserApiEndpoint: RandomUserApiEndpoint,
        private val userCacheDao: ClientCacheDao,
        private val userDataSourceFactory: UserDataSourceFactory
) {

    fun getUsers() =
            userCacheDao.getAllClients()

    fun getUserDataSourceFactory() = userDataSourceFactory

    fun fetchAndCacheUsers() = randomUserApiEndpoint.getClient("1")
            .map {
                it.results
            }.flatMapCompletable {
                clearCacheAndInsertUsers(it)
            }

    private fun clearCacheAndInsertUsers(userList: List<Client>) = userCacheDao.clearCache()
            .andThen(userCacheDao.insertClientList(userList))

    fun getUserForEmail(userEmail: String): Maybe<Client> = userCacheDao.getUserForEmail(userEmail).subscribeOn(Schedulers.io())

    fun refreshUserDataSourceFactory() {
        userDataSourceFactory.invalidateData()
    }
}