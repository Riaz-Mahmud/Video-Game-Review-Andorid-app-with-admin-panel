package com.backdoor.vgr.View.Model.Game

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backdoor.vgr.R
import com.backdoor.vgr.View.Activity.Game.GameDetailsActivity
import com.backdoor.vgr.View.Activity.MainActivity
import com.backdoor.vgr.network.ApiClient
import com.squareup.picasso.Picasso
import maes.tech.intentanim.CustomIntent

class GameAdapter(

    private var contact: List<GameDetailsContact>?,
    private val mContext: Context) :

    RecyclerView.Adapter<GameAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.gameName.text = contact!![position].name
        holder.desc.text = Html.fromHtml(contact!![position].desc, Html.FROM_HTML_MODE_COMPACT)
        holder.rating.rating = contact!![position].rating
        holder.ratingCount.text =
            contact!![position].rating.toString() + "(" + contact!![position].rating_count + ")"
        if (contact!![position].banner != null) {
            if (contact!![position].banner != "") {
                try {
                    Picasso.get().load(ApiClient.getImageUrl(mContext) + contact!![position].banner)
                        .error(R.drawable.no_image_found)
                        .into(holder.image)
                } catch (e: Exception) {
                    holder.image.setBackgroundResource(R.drawable.no_image_found)
                }
            }
        }
        holder.parentLayout.setOnClickListener { view: View? ->
            val intent = Intent(mContext, GameDetailsActivity::class.java)
            intent.putExtra(MainActivity.GAME_ID, contact!![position].id)
            mContext.startActivity(intent)
            CustomIntent.customType(mContext, "left-to-right")
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

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gameName: TextView
        var desc: TextView
        var ratingCount: TextView
        var rating: RatingBar
        var image: ImageView
        var parentLayout: LinearLayout

        init {
            gameName = itemView.findViewById(R.id.gameNameTxt)
            desc = itemView.findViewById(R.id.gameDescTxt)
            image = itemView.findViewById(R.id.gameImage)
            rating = itemView.findViewById(R.id.rating)
            ratingCount = itemView.findViewById(R.id.ratingCount)
            parentLayout = itemView.findViewById(R.id.parentLayoutGameItem)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChangeData(dataList: List<GameDetailsContact>?) {
        contact = dataList
        notifyDataSetChanged()
    }
}