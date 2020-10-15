package cvdevelopers.takehome.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import cvdevelopers.takehome.models.Client
import cvdevelopers.takehome.ui.userlist.UserDisplayData
import javax.inject.Inject

class UserDataSourceFactory @Inject constructor(
    private val userDataSource: ClientDataSource
): DataSource.Factory<Int, UserDisplayData>() {

  val dataSourceLiveData = MutableLiveData<ClientDataSource>()

  override fun create(): DataSource<Int, UserDisplayData> {
    dataSourceLiveData.postValue(userDataSource)
    return userDataSource
  }
}