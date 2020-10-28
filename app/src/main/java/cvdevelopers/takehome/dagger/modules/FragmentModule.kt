package cvdevelopers.takehome.dagger.modules

import androidx.lifecycle.ViewModel
import cvdevelopers.takehome.dagger.vm.ViewModelKey
import cvdevelopers.takehome.ui.userlist.UserListViewModel
import cvdevelopers.takehome.utils.ErrorMessagesDispatcher
import cvdevelopers.takehome.utils.IErrorMessagesListener
import cvdevelopers.takehome.utils.IErrorMessagesSender
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
  abstract fun providesErrorMessageListener(errorMessagesDispatcher: ErrorMessagesDispatcher): IErrorMessagesListener

  @Binds
  abstract fun providesErrorMessageSender(errorMessagesDispatcher: ErrorMessagesDispatcher): IErrorMessagesSender


}