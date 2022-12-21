package uz.gita.bookapi.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import uz.gita.bookapi.R
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.databinding.ItemBookBinding
import uz.gita.bookapi.utils.mLog

/**Created: Bekzod Yusupov
Project: book api
Date: 2022/12/07
Time: 18:47
 */

class HomeAdapter(
    bookMarkListener:(BookResponseEntity) -> Unit,
    deleteBlockListener: (BookResponseEntity) -> Unit,
    editBlockListener: (BookResponseEntity) -> Unit

) : ListAdapter<BookResponseEntity, HomeAdapter.VH>(BookDiffUtil), HomeAdapterInterface {
    private var bookMarkClickListener: ((BookResponseEntity) -> Unit)? = null
    private var deleteClickListener: ((BookResponseEntity) -> Unit)? = null
    private var editClickListener: ((BookResponseEntity) -> Unit)? = null

    object BookDiffUtil : DiffUtil.ItemCallback<BookResponseEntity>() {
        override fun areItemsTheSame(oldItem: BookResponseEntity, newItem: BookResponseEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BookResponseEntity, newItem: BookResponseEntity): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.description == newItem.description &&
                    newItem.fav == oldItem.fav &&
                    oldItem.title == newItem.title &&
                    oldItem == newItem

        }

    }

    override fun triggerBookMarkClickListener(block: (BookResponseEntity) -> Unit) {
        bookMarkClickListener = block
    }

    override fun triggerDeleteClickListener(block: (BookResponseEntity) -> Unit) {
        deleteClickListener = block
    }

    override fun triggerEditClickListener(block: (BookResponseEntity) -> Unit) {
        editClickListener = block
    }

    override fun megaListener(
        bookMarkListener: (BookResponseEntity) -> Unit,
        deleteBlockListener: (BookResponseEntity) -> Unit,
        editBlockListener: (BookResponseEntity) -> Unit
    ) {
        bookMarkClickListener = bookMarkListener
        deleteClickListener = deleteBlockListener
        editClickListener = editBlockListener
    }


    inner class VH(private val itemBinding: ItemBookBinding) : ViewHolder(itemBinding.root) {
        init {
            itemBinding.ivEdit.setOnClickListener { editClickListener?.invoke(getItem(absoluteAdapterPosition)) }
            itemBinding.ivDelete.setOnClickListener { deleteClickListener?.invoke(getItem(absoluteAdapterPosition)) }
            itemBinding.ivLike.setOnClickListener { bookMarkClickListener?.invoke(getItem(absoluteAdapterPosition)) }
        }

          fun bind() {
            val item = getItem(absoluteAdapterPosition)
            itemBinding.tvTitle.text = item.title
            itemBinding.tvAuthor.text = item.author
            mLog("Adapter: item title = ${item.title}, fav = ${item.fav}")
            mLog(Thread.currentThread().name)
            when (getItem(absoluteAdapterPosition).fav) {
                true -> itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_24)
                false -> itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind()
}



