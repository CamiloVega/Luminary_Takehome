package cvdevelopers.takehome.ui.userlist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.LuminaryTakeHomeApplication
import cvdevelopers.takehome.dagger.vm.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.user_list_fragment.*
import javax.inject.Inject

class UserListFragment : Fragment() {

    @Inject
    lateinit var adapter: UserListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    companion object {
        fun newInstance() = UserListFragment()
    }

    private val disposable = CompositeDisposable()

    private lateinit var viewModel: UserListViewModel

    override fun onAttach(context: Context) {
        (context.applicationContext as LuminaryTakeHomeApplication).appComponent.inject(this)
        super.onAttach(context)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.user_list_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = adapter
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            swiperefresh.isRefreshing = false
        })
        viewModel.observeNavigationData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    navigateToUser(it)
                }.addTo(disposable)
        swiperefresh.setOnRefreshListener {
            viewModel.refreshUserData()
        }
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }

    private fun navigateToUser(userEmail: String) {
        findNavController().navigate(UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(userEmail))
    }

}