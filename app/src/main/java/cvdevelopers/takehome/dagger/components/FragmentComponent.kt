package cvdevelopers.takehome.dagger.components

import cvdevelopers.takehome.dagger.modules.FragmentModule
import cvdevelopers.takehome.dagger.scopes.FragmentScope
import cvdevelopers.takehome.ui.userlist.UserListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(target: UserListFragment)

    fun fragmentDetailsComponent(): FragmentDetailsComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(): FragmentComponent
    }
}
