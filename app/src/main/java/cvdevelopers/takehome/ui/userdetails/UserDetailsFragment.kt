package cvdevelopers.takehome.ui.userdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.MainActivity
import cvdevelopers.takehome.dagger.vm.ViewModelFactory
import cvdevelopers.takehome.utils.image.ImageLoader
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.user_details_fragment.*
import javax.inject.Inject

class UserDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: UserDetailsViewModel

    override fun onAttach(context: Context) {
        (activity as MainActivity).activityComponent.fragmentComponent().create().fragmentDetailsComponent().create(this).inject(this)
        super.onAttach(context)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        viewModel.observerDisplayData().observe(viewLifecycleOwner, Observer {
          imageLoader.loadCircularImage(it.imageUrl, details_image)
          full_name.text = it.fullName
          email.text = it.email
          phone_number.text = it.phoneNumber
          toolbar.title = it.fullName
        })
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

}