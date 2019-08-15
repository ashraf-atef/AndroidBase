package com.example.androidbase.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidbase.R
import com.example.androidbase.common.presentationLayer.BaseDiffUtilCallback
import com.example.androidbase.pagination.data.Data
import kotlinx.android.synthetic.main.item_data.view.*
import kotlinx.android.synthetic.main.item_retry.view.*


class DataAdapter : RecyclerView.Adapter<DataAdapter.BaseViewHolder>() {

    private val DATA_LAYOUT = R.layout.item_data
    private val LOAD_MORE_LAYOUT = R.layout.item_loader
    private val RETRY_LAYOUT = R.layout.item_retry

    private val list: MutableList<Data> = mutableListOf()
    private val diffUtilCallback: DataDiffCallback by lazy {
        DataDiffCallback()
    }
    private lateinit var itemClickListener: ItemClickListener

    var loadMore: Boolean = false
    var error: Boolean = false

    override fun getItemCount(): Int = if (loadMore || error) list.size + 1 else list.size

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            DATA_LAYOUT -> DataViewHolder(view)
            LOAD_MORE_LAYOUT -> LoaderViewHolder(view)
            RETRY_LAYOUT -> RetryViewHolder(view)
            else -> throw IllegalArgumentException("Unknown Layout")
        }

    }

    override fun onBindViewHolder(@NonNull holder: BaseViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemViewType(position: Int): Int = when {
        loadMore && position == list.size-> LOAD_MORE_LAYOUT
        error && position == list.size -> RETRY_LAYOUT
        else -> DATA_LAYOUT
    }

    fun addData(newDataList: List<Data>) {
        removeLoadAndRetryRows()
        diffUtilCallback.setLists(list, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        diffResult.dispatchUpdatesTo(this)
        this.list.clear()
        this.list.addAll(newDataList)
    }

    private fun removeLoadAndRetryRows() {
        if (loadMore || error) {
            loadMore = false
            error = false
            notifyItemRemoved(list.size)
        }
    }

    fun addLoadMoreRow() {
        removeLoadAndRetryRows()
        loadMore = true
        notifyItemInserted(list.size)
    }

    fun addRetryRow() {
        removeLoadAndRetryRows()
        error = true
        notifyItemInserted(list.size)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind()
    }

    inner class DataViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind() {
            val data = list[adapterPosition]
            itemView.tv_name.text = (data.first_name + " " + data.last_name)
            itemView.tv_email.text = data.email
        }
    }

    inner class LoaderViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind() {
        }
    }

    inner class RetryViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind() {
            itemView.btn_retry.setOnClickListener{ itemClickListener.onRetryClick() }
        }
    }

    inner class DataDiffCallback: BaseDiffUtilCallback<Data>() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
           oldData[oldItemPosition].id == newData[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition] == newData[newItemPosition]

    }

    interface ItemClickListener {
        fun onRetryClick()
    }
}