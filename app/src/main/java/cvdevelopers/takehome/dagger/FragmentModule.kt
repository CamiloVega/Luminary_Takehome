package cvdevelopers.takehome.dagger

import androidx.lifecycle.ViewModel
import cvdevelopers.takehome.dagger.vm.ViewModelKey
import cvdevelopers.takehome.ui.userdetails.UserDetailsFragment
import cvdevelopers.takehome.ui.userdetails.UserDetailsViewModel
import cvdevelopers.takehome.ui.userlist.UserListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {

  @Binds
  @IntoMap
  @ViewModelKey(UserListViewModel::class)
  abstract fun providesUserListViewModel(director: UserListViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(UserDetailsViewModel::class)
  abstract fun providesUserDetailsViewModel(director: UserDetailsViewModel): ViewModel

}