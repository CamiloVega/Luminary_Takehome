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

  abstract class UserItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
    abstract fun  bind(imageLoader: ImageLoader, user: UserDisplayData)
  }

  class UserItemViewHolderNormal(val view: View): UserItemViewHolder(view)  {
    override fun bind(imageLoader: ImageLoader, user: UserDisplayData) {
      (user as? UserDisplayDataNormal)?.let {user ->
        imageLoader.loadCircularImage(user.imageUrl, view.imageView)
        view.textView.text = user.fullName
        view.setOnClickListener { user.onClick() }
      }
    }
  }

  class UserItemViewHolderReverse(val view: View) : UserItemViewHolder(view) {
    override fun bind(imageLoader: ImageLoader, user: UserDisplayData) {
      (user as? UserDisplayDataReversed)?.let {user ->
        imageLoader.loadCircularImage(user.imageUrl, view.imageView)
        view.textView.text = user.fullName
        view.setOnClickListener { user.onClick() }
      }
    }
  }

  override fun getItemViewType(position: Int): Int {

    return getItem(position)?.getViewType() ?: 0
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
     return when(viewType) {
      1 -> UserItemViewHolderNormal( LayoutInflater.from(parent.context)
          .inflate(R.layout.user_item, parent, false))
      2 -> UserItemViewHolderReverse(LayoutInflater.from(parent.context)
          .inflate(R.layout.user_item_reversed, parent, false))
      else -> throw Throwable("View type not recognized")
    }

  }

  override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
    getItem(position)?.let { holder.bind(imageLoader, it) }
  }

  companion object {

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

sealed class UserDisplayData() {
  abstract fun getViewType(): Int
  abstract fun getUniqueKey(): Int
}

data class UserDisplayDataNormal(val fullName: String, val imageUrl: String, val onClick: () -> Unit): UserDisplayData() {
  override fun getViewType(): Int = 1
  override fun getUniqueKey(): Int = fullName.hashCode()
}

data class UserDisplayDataReversed(val fullName: String, val imageUrl: String, val onClick: () -> Unit): UserDisplayData() {
  override fun getViewType(): Int = 2
  override fun getUniqueKey(): Int = fullName.hashCode()
}