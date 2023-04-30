package com.backdoor.vgr.View.Model.Game;

import static maes.tech.intentanim.CustomIntent.customType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Activity.Game.GameDetailsActivity;
import com.backdoor.vgr.View.Activity.MainActivity;
import com.backdoor.vgr.network.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private List<GameDetailsContact> contact;
    private Context mContext;

    public GameAdapter(List<GameDetailsContact> contacts, Context context) {
        this.contact = contacts;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.gameName.setText(contact.get(position).getName());

        holder.desc.setText(Html.fromHtml(contact.get(position).getDesc(), Html.FROM_HTML_MODE_COMPACT));
        holder.rating.setRating(contact.get(position).getRating());
        holder.ratingCount.setText(contact.get(position).getRating() + "(" + contact.get(position).getRating_count() + ")");

        if (contact.get(position).getBanner() != null) {
            if (!contact.get(position).getBanner().equals("")) {
                try {
                    Picasso.get().load(ApiClient.getImageUrl(mContext) + contact.get(position).getBanner())
                            .error(R.drawable.no_image_found)
                            .into(holder.image);
                } catch (Exception e) {
                    holder.image.setBackgroundResource(R.drawable.no_image_found);
                }
            }
        }

        holder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, GameDetailsActivity.class);
            intent.putExtra(MainActivity.GAME_ID, contact.get(position).getId());
            mContext.startActivity(intent);
            customType(mContext, "left-to-right");
        });

    }

    @Override
    public int getItemCount() {
        if (contact == null) {
            return 0;
        } else if (contact.size() == 0) {
            return 0;
        } else {
            return contact.size();
        }
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView gameName, desc, ratingCount;
        RatingBar rating;
        ImageView image;
        LinearLayout parentLayout;

        MyViewHolder(View itemView) {
            super(itemView);

            gameName = itemView.findViewById(R.id.gameNameTxt);
            desc = itemView.findViewById(R.id.gameDescTxt);
            image = itemView.findViewById(R.id.gameImage);
            rating = itemView.findViewById(R.id.rating);
            ratingCount = itemView.findViewById(R.id.ratingCount);
            parentLayout = itemView.findViewById(R.id.parentLayoutGameItem);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyChangeData(List<GameDetailsContact> dataList) {
        contact = dataList;
        notifyDataSetChanged();
    }


}
