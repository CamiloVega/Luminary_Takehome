package cvdevelopers.takehome.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cvdevelopers.takehome.models.Client
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface ClientCacheDao {

  @Insert
  fun insertClientList(clientList: List<Client>): Completable

  @Query("SELECT * FROM client")
  fun getAllClients(): Observable<List<Client>>

  @Query("DELETE FROM client")
  fun clearCache(): Completable

  @Query("SELECT COUNT(*) AS userCount FROM client")
  fun getNumberOfUsersInCache(): Single<Int>

  @Query("SELECT *  FROM client WHERE email = :userEmail")
  fun getUserForEmail(userEmail: String): Maybe<Client>

}