package cvdevelopers.takehome.ui.userlist

import androidx.lifecycle.ViewModel
import cvdevelopers.takehome.datarepository.UserDataRepository
import cvdevelopers.takehome.models.Client
import javax.inject.Inject
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import cvdevelopers.takehome.pagination.UserDataSourceFactory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import java.util.*

class UserListViewModel @Inject constructor(
        private val userDataRepository: UserDataRepository
) : ViewModel() {

  private val userListLiveData = MutableLiveData<List<UserDisplayData>>()
  private val navigateLiveData = PublishSubject.create<String>()

  var listLiveData: LiveData<PagedList<UserDisplayData>>

  fun observeUserListData() = userListLiveData as LiveData<List<UserDisplayData>>
  fun observeNavigationData() = navigateLiveData as Observable<String>
  private val disposable = CompositeDisposable()
  init {
    val config = PagedList.Config.Builder()
        .setPageSize(10)
        .setInitialLoadSizeHint(10)
        .setEnablePlaceholders(true)
        .build()
    listLiveData = LivePagedListBuilder<Int, UserDisplayData>(
            userDataRepository.getUserDataSourceFactory()
                    .map { it.toDisplayData() }
            , config).build()

  }

  private fun Client.toDisplayData() = UserDisplayData(
          fullName = "${name.first} ${name.last}",
          imageUrl = picture.thumbnail,
          onClick = {
            navigateLiveData.onNext(email)
          }
  )

  override fun onCleared() {
    disposable.clear()
    super.onCleared()
  }

  fun refreshUserData() {
      userDataRepository.refreshUserDataSourceFactory()

  }

}


