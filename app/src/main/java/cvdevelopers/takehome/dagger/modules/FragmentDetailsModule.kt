package cvdevelopers.takehome.dagger.modules

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import cvdevelopers.takehome.dagger.vm.ViewModelKey
import cvdevelopers.takehome.ui.userdetails.UserDetailsFragment
import cvdevelopers.takehome.ui.userdetails.UserDetailsFragmentArgs
import cvdevelopers.takehome.ui.userdetails.UserDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module(includes = [FragmentDetailsModule.ProvidesModule::class])
abstract class FragmentDetailsModule(private val fragment: UserDetailsFragment) {

  @Binds
  @IntoMap
  @ViewModelKey(UserDetailsViewModel::class)
  abstract fun providesUserDetailsViewModel(director: UserDetailsViewModel): ViewModel

  @Module
  object ProvidesModule {
    @Provides
    @Named("userEmail")
    fun providesUserEmail(fragment: UserDetailsFragment) = fragment.navArgs<UserDetailsFragmentArgs>().value.userEmail
  }
}