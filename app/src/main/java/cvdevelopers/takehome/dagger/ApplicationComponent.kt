package cvdevelopers.takehome.dagger

import cvdevelopers.takehome.MainActivity
import cvdevelopers.takehome.LuminaryTakeHomeApplication
import cvdevelopers.takehome.ui.userdetails.UserDetailsFragment
import cvdevelopers.takehome.ui.userlist.UserListFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by CamiloVega on 10/7/17.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkClientModule::class, FragmentModule::class, DataModule::class))
interface ApplicationComponent {
  fun inject(app: LuminaryTakeHomeApplication)
  fun inject(target: MainActivity)
  fun inject(target: UserListFragment)
  fun inject(target: UserDetailsFragment)
}