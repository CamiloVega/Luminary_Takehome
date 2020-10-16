package cvdevelopers.takehome.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cvdevelopers.githubstalker.R
import cvdevelopers.takehome.utils.image.ImageLoader
import kotlinx.android.synthetic.main.user_item.view.*
import javax.inject.Inject


class UserListAdapter @Inject constructor(val imageLoader: ImageLoader) : PagedListAdapter<UserDisplayData, UserListAdapter.UserItemViewHolder>(DiffCallback()) {

  class UserItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
    fun  bind(imageLoader: ImageLoader, user: UserDisplayData) {
      imageLoader.loadCircularImage(user.imageUrl, itemView.imageView)
      itemView.textView.text = user.fullName
      itemView.setOnClickListener { user.onClick() }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
      return UserItemViewHolder(LayoutInflater.from(parent.context)
          .inflate(R.layout.user_item, parent, false))
  }

  override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
    getItem(position)?.let { holder.bind(imageLoader, it) }
  }
}

class DiffCallback : DiffUtil.ItemCallback<UserDisplayData>() {
  override fun areItemsTheSame(oldItem: UserDisplayData, newItem: UserDisplayData): Boolean {
    return oldItem.getUniqueKey() == newItem.getUniqueKey()
  }

  override fun areContentsTheSame(oldItem: UserDisplayData, newItem: UserDisplayData): Boolean {
    return oldItem == newItem
  }
}

data class UserDisplayData(val fullName: String, val imageUrl: String, val onClick: () -> Unit) {
   fun getUniqueKey() = fullName.hashCode()
}