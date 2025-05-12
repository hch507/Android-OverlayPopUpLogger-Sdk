package com.example.overlaypopuplogger.core.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.overlaypopuplogger.databinding.ItemLogBinding
import com.example.overlaypopuplogger.model.OverlayLogItem

class OverlayLoggerAdapter : ListAdapter<OverlayLogItem, OverlayLoggerAdapter.LogItemViewHolder>(diffUtil){
    inner class LogItemViewHolder(private val binding: ItemLogBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(overlayLogItem: OverlayLogItem){
            binding.apply {
                tvTag.text = overlayLogItem.tag
                tvTag.setTextColor(overlayLogItem.color)
                tvContent.text = overlayLogItem.msg
                tvContent.setTextColor(overlayLogItem.color)
                tvTime.text= overlayLogItem.time
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogItemViewHolder {
        Log.d("test_logd", "OverlayLoggerAdapter-onCreateViewHolder() called")
        return LogItemViewHolder(ItemLogBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: LogItemViewHolder, position: Int) {
        Log.d("OverlayLoggerAdapter", "Binding item at position $position: ${currentList[position].tag} - ${currentList[position].msg}")
        holder.bind(currentList[position])

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OverlayLogItem>() {
            override fun areItemsTheSame(oldItem: OverlayLogItem, newItem: OverlayLogItem): Boolean {
                Log.d("test_logd", "OverlayLoggerAdapter-areItemsTheSame() called")
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: OverlayLogItem, newItem: OverlayLogItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}