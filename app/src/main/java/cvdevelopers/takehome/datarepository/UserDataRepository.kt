package cvdevelopers.takehome.datarepository

import cvdevelopers.takehome.api.RandomUserApiEndpoint
import cvdevelopers.takehome.cache.dao.ClientCacheDao
import cvdevelopers.takehome.models.Client
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val randomUserApiEndpoint: RandomUserApiEndpoint,
    private val userCacheDao: ClientCacheDao
) {

  fun getUsers() =
      userCacheDao.getAllClients()

  //          .do
//      randomUserApiEndpoint.getClient("1").map { it.results }
//          .doOnEvent { list, error ->
//            if (error != null) {
//              userCacheDao.insertClientList(list)
//            }
//          }
  fun fetchAndCacheUsers() = randomUserApiEndpoint.getClient("1")
      .map {
        it.results
      }.flatMapCompletable {
        clearCacheAndInsertUsers(it)
      }

  private fun clearCacheAndInsertUsers(userList: List<Client>) = userCacheDao.clearCache()
      .andThen(userCacheDao.insertClientList(userList))

  fun getNumberOfUsersInCache() = userCacheDao.getNumberOfUsersInCache().subscribeOn(Schedulers.io())
  fun getUserForEmail(userEmail: String): Maybe<Client> = userCacheDao.getUserForEmail(userEmail).subscribeOn(Schedulers.io())
}