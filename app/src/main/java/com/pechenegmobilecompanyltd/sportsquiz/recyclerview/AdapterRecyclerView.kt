package com.pechenegmobilecompanyltd.sportsquiz.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pechenegmobilecompanyltd.sportsquiz.databinding.PhotoItemBinding
import com.pechenegmobilecompanyltd.sportsquiz.entity.PhotoItem

class AdapterRecyclerView(val onClick: (PhotoItem) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    private var data: List<PhotoItem> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PhotoItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder.binding) {
            price.text = "Price: ${item.price}"
            Glide.with(img.context)
                .load(item.resource)
                .into(img)
        }
        holder.binding.root.setOnClickListener {
            item.let {
                onClick(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size
}

class ViewHolder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root)