package uz.gita.bookapi.presentation.screen.home.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import uz.gita.bookapi.data.source.local.entity.BookResponseEntity
import uz.gita.bookapi.data.source.local.entity.State
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
    private var book: BookResponseEntity? = null
    private var putBookListener: ((BookResponseEntity) -> Unit)? = null
    private var postBookListener: ((BookResponseEntity) -> Unit)? = null
    var setPredefinedText: ((BookResponseEntity) -> Unit)? = null

    fun triggerPutBookListener(block: (BookResponseEntity) -> Unit) {
        mLog("trigger put from dialog")
        putBookListener = block
    }

    fun triggerPostBookListener(block: (BookResponseEntity) -> Unit) {
        mLog("trigger post from dialog")
        postBookListener = block
    }

    private fun triggerPredefinedText(block: (BookResponseEntity) -> Unit) {
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
            val page = binding.etPageCount.text.toString()

            if (page.isEmpty()) {
                Toast.makeText(context, "page bo'sh bo'lmasligi kere", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (title.length <= 3) {
                Toast.makeText(context, "Title uzunligi kamida 3 ta bo'lishi kere", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (desc.length < 30) {
                Toast.makeText(context, "Description uzunligi 30 dan katta bo'lishi kere", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (author.length < 10) {
                Toast.makeText(context, "Author uzunligi 10 dan katta bo'lishi kere", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (book == null) {
                mLog("Dialog: books is null so post book")
                val entity = BookResponseEntity(
                    title = title,
                    author = author,
                    description = desc,
                    pageCount = page.toInt(),
                    state = State.LocalAdded
                )
                postBookListener?.invoke(entity)
                dismiss()
            } else {
                mLog("Dialog: books isn't null so post book")

                val entity = BookResponseEntity(
                    id = book!!.id,
                    title = title,
                    author = author,
                    description = desc,
                    pageCount = page.toInt(),
                    state = State.LocalEdited,
                    fav = book!!.fav
                )
                putBookListener?.invoke(entity)
                dismiss()
            }

        }
    }

    override fun show() {
        super.show()
        triggerPredefinedText {
            book = it
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
        book = null
    }

}