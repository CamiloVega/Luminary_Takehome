package cvdevelopers.takehome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import cvdevelopers.githubstalker.R

class MainActivity : AppCompatActivity() {

    private val navController by lazy { Navigation.findNavController(this, R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as LuminaryTakeHomeApplication).appComponent.inject(this)
//        setupActionBar()
    }

//    private fun setupActionBar() = NavigationUI.setupActionBarWithNavController(this, navController)

    override fun onSupportNavigateUp() = navController.navigateUp() || super.onSupportNavigateUp()
}
