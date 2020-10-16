package cvdevelopers.takehome.pagination

import androidx.paging.PageKeyedDataSource
import cvdevelopers.takehome.api.RandomUserApiEndpoint
import cvdevelopers.takehome.models.Client
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import android.util.Log
import cvdevelopers.takehome.cache.dao.ClientCacheDao
import cvdevelopers.takehome.datarepository.UserDataRepository

class ClientDataSource constructor(
    private val randomUserApiEndpoint: RandomUserApiEndpoint,
    private val userCacheDao: ClientCacheDao
) : PageKeyedDataSource<Int, Client>() {

  private val compositeDisposable = CompositeDisposable()

  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Client>) {
    randomUserApiEndpoint.getClient("1", params.requestedLoadSize.toString())
        .map { it.results }
            .doOnEvent { list, error ->
                if (error == null) {
                    userCacheDao.insertClientList(list).subscribe()
                }
            }
        .subscribe({
          callback.onResult(it, null, 2)
        }, {
          Log.e("ClientDataSource", "Error on loadInitial")
        }).addTo(compositeDisposable)
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Client>) {
    randomUserApiEndpoint.getClient(params.key.toString(), params.requestedLoadSize.toString())
        .map { it.results }
            .doOnEvent { list, error ->
                if (error == null){
                    userCacheDao.insertClientList(list).subscribe()
                }
            }
        .subscribe({
          callback.onResult(it, params.key+1)
        }, {
          Log.e("ClientDataSource", "Error on loadInitial")
        }).addTo(compositeDisposable)
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Client>) {

  }
    
}