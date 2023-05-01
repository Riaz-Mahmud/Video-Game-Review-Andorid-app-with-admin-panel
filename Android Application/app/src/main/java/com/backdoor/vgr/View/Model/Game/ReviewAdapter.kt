package com.backdoor.vgr.View.Model.Game

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.backdoor.vgr.R
import com.backdoor.vgr.databinding.ItemReviewBinding

class ReviewAdapter(
    private var contact: List<GameReviews>?,
    private val mContext: Context,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<ItemReviewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_review, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val firstName = contact!![position].userContact.first_name
        val lastName =
            if (contact!![position].userContact.last_name != null) contact!![position].userContact.last_name else ""
        var name = "$firstName $lastName"
        if (contact!![position].is_mine) {
            name = "$name (You)"
            holder.binding.parentLayoutForUserDetails.backgroundTintList =
                AppCompatResources.getColorStateList(mContext, R.color.whiteLight)
            holder.binding.deleteReviewBtn.visibility = View.VISIBLE
        } else {
            holder.binding.parentLayoutForUserDetails.backgroundTintList =
                AppCompatResources.getColorStateList(mContext, R.color.white)
        }
        if (contact!![position].status != "Active") {
            name = name + " (" + contact!![position].status + ")"
        }
        holder.binding.userName.text = name
        holder.binding.comment.text = contact!![position].comment
        holder.binding.rating.text = contact!![position].rating.toString()
        holder.binding.deleteReviewBtn.setOnClickListener { view: View? ->
            onItemClickListener.onItemClick(
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return if (contact == null) {
            0
        } else if (contact!!.isEmpty()) {
            0
        } else {
            contact!!.size
        }
    }

    class MyViewHolder(var binding: ItemReviewBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChangeData(gameReviewsList: List<GameReviews>?) {
        contact = gameReviewsList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}