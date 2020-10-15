package cvdevelopers.takehome.pagination

import androidx.paging.PageKeyedDataSource
import cvdevelopers.takehome.api.RandomUserApiEndpoint
import cvdevelopers.takehome.models.Client
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject
import android.util.Log
import cvdevelopers.takehome.ui.userlist.UserDisplayData
import cvdevelopers.takehome.ui.userlist.UserDisplayDataNormal
import cvdevelopers.takehome.ui.userlist.UserDisplayDataReversed
import java.util.*

class ClientDataSource @Inject constructor(
    private val randomUserApiEndpoint: RandomUserApiEndpoint
) : PageKeyedDataSource<Int, UserDisplayData>() {

  private val compositeDisposable = CompositeDisposable()

  override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, UserDisplayData>) {
    randomUserApiEndpoint.getClient("1", params.requestedLoadSize.toString())
        .map { it.results }
        .map { it.map { it.toDisplayData() } }
        .subscribe({
          callback.onResult(it, null, 2)
        }, {
          Log.e("ClientDataSource", "Error on loadInitial")
        }).addTo(compositeDisposable)
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, UserDisplayData>) {
    randomUserApiEndpoint.getClient(params.key.toString(), params.requestedLoadSize.toString())
        .map { it.results }
        .map { it.map { it.toDisplayData() } }
        .subscribe({
          callback.onResult(it, params.key+1)
        }, {
          Log.e("ClientDataSource", "Error on loadInitial")
        }).addTo(compositeDisposable)
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, UserDisplayData>) {

  }

  private fun Client.toDisplayData() = if (Random().nextBoolean()) UserDisplayDataNormal(
      fullName = "${name.first} ${name.last}",
      imageUrl = picture.thumbnail,
      onClick = { }
  ) else
    UserDisplayDataReversed(
        fullName = "${name.first} ${name.last}",
        imageUrl = picture.thumbnail,
        onClick = { }
    )

  fun clearSubscriptions(){
    compositeDisposable.clear()
  }
}