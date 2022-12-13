package uz.gita.bookapi.presentation.screen.home.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import uz.gita.bookapi.data.source.remote.dto.request.PostBookRequest
import uz.gita.bookapi.data.source.remote.dto.request.PutBookRequest
import uz.gita.bookapi.data.source.remote.dto.response.BooksResponseItem
import uz.gita.bookapi.databinding.DialogEditBinding
import uz.gita.bookapi.utils.config
import uz.gita.bookapi.utils.mLog

/**
Created: Bekzod Yusupov
Project: book api
Date: 2022/12/13
Time: 16:27
 */
class HomeDialog(context: Context) : Dialog(context) {
    private lateinit var binding: DialogEditBinding
    private var id = -1
    private var putBookListener: ((PutBookRequest) -> Unit)? = null
    private var postBookListener: ((PostBookRequest) -> Unit)? = null
    var setPredefinedText: ((BooksResponseItem) -> Unit)? = null

    fun triggerPutBookListener(block: (PutBookRequest) -> Unit) {
        putBookListener = block
    }

    fun triggerPostBookListener(block: (PostBookRequest) -> Unit) {
        postBookListener = block
    }

    private fun triggerPredefinedText(block: (BooksResponseItem) -> Unit) {
        setPredefinedText = block
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DialogEditBinding.inflate(layoutInflater)
        config(binding)

        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()
            val author = binding.etAuthor.text.toString()
            val page = binding.etPageCount.text.toString().toInt()

            if (id == -1) {
                val postBookRequest = PostBookRequest(title, author, desc, page)
                postBookListener?.invoke(postBookRequest)
                dismiss()
            } else {
                val putRequest = PutBookRequest(id, title, author, desc, page)
                putBookListener?.invoke(putRequest)
                dismiss()
            }

        }
    }

    override fun show() {
        super.show()
        triggerPredefinedText {
            id = it.id
            binding.apply {
                etTitle.setText(it.title)
                etDesc.setText(it.description)
                etAuthor.setText(it.author)
                etPageCount.setText(it.pageCount.toString())
            }
        }
    }

    override fun dismiss() {
        super.dismiss()
        binding.apply {
            etTitle.setText("")
            etDesc.setText("")
            etAuthor.setText("")
            etPageCount.setText("")
        }
        id = -1
    }

}