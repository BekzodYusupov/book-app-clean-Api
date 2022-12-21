package uz.gita.bookapi.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.gita.bookapi.R
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.databinding.ItemBookBinding
import uz.gita.bookapi.utils.mLog

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/21
Time: 12:06
 */
class HomeReAdapter : RecyclerView.Adapter<HomeReAdapter.VH>() {
    private val dataList: ArrayList<BookResponseEntity> = ArrayList()
    private var bookMarkClickListener: ((BookResponseEntity) -> Unit)? = null
    private var deleteClickListener: ((BookResponseEntity) -> Unit)? = null
    private var editClickListener: ((BookResponseEntity) -> Unit)? = null

    fun triggerBookMarkClickListener(block: (BookResponseEntity) -> Unit) {
        bookMarkClickListener = block
    }

    fun triggerDeleteClickListener(block: (BookResponseEntity) -> Unit) {
        deleteClickListener = block
    }

    fun triggerEditClickListener(block: (BookResponseEntity) -> Unit) {
        editClickListener = block
    }


    fun submitList(items: ArrayList<BookResponseEntity>) {
        val callBack = MyDiffUtil(dataList, items)
        val result = DiffUtil.calculateDiff(callBack)
        dataList.clear()
        dataList.addAll(items)
        result.dispatchUpdatesTo(this)
//        notifyDataSetChanged()
    }

    inner class VH(private val itemBinding: ItemBookBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.ivEdit.setOnClickListener { editClickListener?.invoke(dataList[absoluteAdapterPosition]) }
            itemBinding.ivDelete.setOnClickListener { deleteClickListener?.invoke(dataList[absoluteAdapterPosition]) }
            itemBinding.ivLike.setOnClickListener { bookMarkClickListener?.invoke(dataList[absoluteAdapterPosition]) }
        }

        fun bind() {
            val item = dataList[absoluteAdapterPosition]
            itemBinding.tvTitle.text = item.title
            itemBinding.tvAuthor.text = item.author
            mLog("Adapter: item title = ${item.title}, fav = ${item.fav}")
            mLog(Thread.currentThread().name)
            when (dataList[absoluteAdapterPosition].fav) {
                true -> itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_24)
                false -> itemBinding.ivLike.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
            }
        }


    }


    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}
