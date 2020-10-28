package cvdevelopers.takehome.dagger.components

import cvdevelopers.takehome.dagger.modules.FragmentDetailsModule
import cvdevelopers.takehome.ui.userdetails.UserDetailsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [FragmentDetailsModule::class])
interface FragmentDetailsComponent {
    fun inject(target: UserDetailsFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: UserDetailsFragment): FragmentDetailsComponent
    }
}
