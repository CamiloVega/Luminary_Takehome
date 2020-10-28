package cvdevelopers.takehome.ui.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cvdevelopers.takehome.datarepository.UserDataRepository
import cvdevelopers.takehome.models.Client
import javax.inject.Inject
import android.util.Log
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Named

class UserDetailsViewModel @Inject constructor(
        private val dataRepository: UserDataRepository,
        @Named("userEmail")
        private val userEmail: String
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val displayLiveData = MutableLiveData<UserDisplayData>()

    fun observerDisplayData() = displayLiveData as LiveData<UserDisplayData>

    init {
        dataRepository.getUserForEmail(userEmail)
                .map { it.toDisplayData() }
                .subscribe({
                    displayLiveData.postValue(it)
                }, {
                    Log.e("UserDetailsViewModel", "error fetching user Data", it)
                }).addTo(disposable)
    }

    private fun Client.toDisplayData() = UserDisplayData(
            imageUrl = picture.large,
            fullName = "${name.first} ${name.last}",
            email = email,
            phoneNumber = "+12345345"
    )

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}


data class UserDisplayData(val imageUrl: String, val fullName: String, val email: String, val phoneNumber: String)