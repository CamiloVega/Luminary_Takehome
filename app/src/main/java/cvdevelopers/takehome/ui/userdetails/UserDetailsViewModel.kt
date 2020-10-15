package cvdevelopers.takehome.ui.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cvdevelopers.takehome.datarepository.UserDataRepository
import cvdevelopers.takehome.models.Client
import javax.inject.Inject
import android.util.Log

class UserDetailsViewModel @Inject constructor(
    private val dataRepository: UserDataRepository
) : ViewModel() {

  private val displayLiveData = MutableLiveData<UserDisplayData >()

  fun observerDisplayData() = displayLiveData as LiveData<UserDisplayData>

  fun fetchDataForUser(userEmail: String) = dataRepository.getUserForEmail(userEmail)
      .map { it.toDisplayData() }
      .subscribe({
        displayLiveData.postValue(it)
      },{
        Log.e("UserDetailsViewModel", "error fetching user Data", it)
      })
}

private fun Client.toDisplayData()= UserDisplayData(
    imageUrl = picture.large,
    fullName = "${name.first} ${name.last}",
    email = email,
    phoneNumber = "+12345345"
)

data class UserDisplayData(val imageUrl: String, val fullName: String, val email: String, val phoneNumber: String)