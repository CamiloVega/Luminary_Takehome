package cvdevelopers.takehome.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import cvdevelopers.takehome.api.RandomUserApiEndpoint
import cvdevelopers.takehome.cache.dao.ClientCacheDao
import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.ui.userlist.UserDisplayData
import javax.inject.Inject

class UserDataSourceFactory @Inject constructor(
        private val randomUserApiEndpoint: RandomUserApiEndpoint,
        private val userCacheDao: ClientCacheDao
): DataSource.Factory<Int, Client>() {

  private val dataSourceLiveData = MutableLiveData<ClientDataSource>()

  override fun create(): DataSource<Int, Client> {
    val userDataSource =  ClientDataSource(randomUserApiEndpoint, userCacheDao)
    dataSourceLiveData.postValue(userDataSource)
    return userDataSource
  }

  fun invalidateData() {
    dataSourceLiveData.value?.invalidate()
  }
}