package uz.gita.bookapi.presentation.screen.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookapi.R
import uz.gita.bookapi.data.source.remote.dto.response.BooksResponseItem
import uz.gita.bookapi.databinding.ItemBookBinding
import uz.gita.bookapi.utils.mLog

/**Created: Bekzod Yusupov
Project: book api
Date: 2022/12/07
Time: 18:47
 */
class HomeAdapter : ListAdapter<BooksResponseItem, HomeAdapter.VH>(BookDiffUtil) {
    private var bookMarkClickListener: ((Int) -> Unit)? = null
    private var deleteClickListener: ((Int) -> Unit)? = null
    private var editClickListener: ((BooksResponseItem) -> Unit)? = null
    var progressListener: ((Boolean) -> Unit)? = null
    var changeFav: ((Boolean) -> Unit)? = null
    private var selectedPosition = -1
    private var progressItemPosFlow = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    @OptIn(DelicateCoroutinesApi::class)
    val global = GlobalScope
    var coroutine = CoroutineScope(Dispatchers.Main)


    private fun triggerProgressListener(block: (Boolean) -> Unit) {
        progressListener = block
    }

    private fun triggerChangeFav(block: (Boolean) -> Unit) {
        changeFav = block
    }

    fun triggerBookMarkClickListener(block: (Int) -> Unit) {
        bookMarkClickListener = block
    }

    fun triggerDeleteClickListener(block: (Int) -> Unit) {
        deleteClickListener = block
    }

    fun triggerEditClickListener(block: (BooksResponseItem) -> Unit) {
        editClickListener = block
    }


    inner class VH(val itemBinding: ItemBookBinding) : ViewHolder(itemBinding.root) {
        init {
            itemBinding.ivEdit.setOnClickListener { editClickListener?.invoke(getItem(absoluteAdapterPosition)) }
            itemBinding.ivDelete.setOnClickListener { deleteClickListener?.invoke(getItem(absoluteAdapterPosition).id) }

            itemBinding.ivLike.setOnClickListener {
                getItem(absoluteAdapterPosition).fav = !getItem(absoluteAdapterPosition).fav
                if (getItem(absoluteAdapterPosition).fav) itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_24)
                else itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
                bookMarkClickListener?.invoke(getItem(absoluteAdapterPosition).id)
//                clickedItemID = getItem(absoluteAdapterPosition).id
                selectedPosition = absoluteAdapterPosition
                mLog("selectedPosition = $selectedPosition")
                progressItemPosFlow.tryEmit(absoluteAdapterPosition)
//                mLog("itemId = $clickedItemID")
            }
/*            triggerProgressListener { likeState ->
                state = likeState

                mLog("Before forEach::::::@@@@@::::::Listening loading status from Adapter status = $likeState")
                currentList.forEach { item ->
                    mLog("${item.id == clickedItemID} -> itemID = ${item.id} and clickedItemID = $clickedItemID")
                    if (clickedItemID != -1 && clickedItemID == item.id) {
                        mLog("----::::::@@@@@::::::Listening loading status from Adapter status = $likeState")
                        if (likeState) {
                            itemBinding.progress.visibility = View.VISIBLE
                            itemBinding.progressView.visibility = View.VISIBLE
                        } else {
                            itemBinding.progress.visibility = View.GONE
                            itemBinding.progressView.visibility = View.GONE
                        }
                    }
                }
            }*/
        }

        fun bind() {
            val item = getItem(absoluteAdapterPosition)
            itemBinding.tvTitle.text = item.title
            itemBinding.tvAuthor.text = item.author
            if (item.fav) itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_24)
            else itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val absolutePosition = holder.absoluteAdapterPosition
        triggerProgressListener { state ->
            progressItemPosFlow.onEach {
                mLog("selectedPos = $it")
                mLog("absPos = ${absolutePosition}")
                if (holder.absoluteAdapterPosition == it) {
                    mLog("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                    mLog("selectedPos == absolutePos")
                    if (state) {
                        holder.itemBinding.progress.visibility = View.VISIBLE
                        holder.itemBinding.progressView.visibility = View.VISIBLE
                    } else {
                        holder.itemBinding.progress.visibility = View.GONE
                        holder.itemBinding.progressView.visibility = View.GONE
                    }
                }
            }.launchIn(coroutine)
            mLog("onBindViewHolder absolutePos-${holder.absoluteAdapterPosition} and selectedPosition - $selectedPosition")
        }

        triggerChangeFav { isFav ->
            if (selectedPosition == holder.absoluteAdapterPosition) {
                mLog("HomeAdapter isFav success - $isFav")
                if (isFav) holder.itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_24)
                else holder.itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
            }
        }
        holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

object BookDiffUtil : DiffUtil.ItemCallback<BooksResponseItem>() {
    override fun areItemsTheSame(oldItem: BooksResponseItem, newItem: BooksResponseItem): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: BooksResponseItem, newItem: BooksResponseItem): Boolean {
        return oldItem.fav == newItem.fav
    }

}