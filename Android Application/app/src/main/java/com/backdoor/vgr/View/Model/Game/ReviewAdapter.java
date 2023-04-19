package com.backdoor.vgr.View.Model.Game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.backdoor.vgr.R;

import java.util.List;

import retrofit2.Response;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private List<GameReviews> contact;
    private final Context mContext;

    public ReviewAdapter(List<GameReviews> contacts, Context context) {
        this.contact = contacts;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        String name = contact.get(position).getUserContact().getFirst_name() + " " +
                contact.get(position).getUserContact().getLast_name();

        if (contact.get(position).is_mine()) {
            name = name + " (You)";
            holder.parentLayout.setBackgroundTintList(AppCompatResources.getColorStateList(mContext, R.color.whiteLight));
        } else {
            holder.parentLayout.setBackgroundTintList(AppCompatResources.getColorStateList(mContext, R.color.white));
        }

        holder.userName.setText(name);
        holder.desc.setText(contact.get(position).getComment());
        holder.rating.setText(String.valueOf(contact.get(position).getRating()));

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

        TextView rating, userName, desc;
        LinearLayout parentLayout;

        MyViewHolder(View itemView) {
            super(itemView);

            rating = itemView.findViewById(R.id.rating);
            userName = itemView.findViewById(R.id.userName);
            desc = itemView.findViewById(R.id.comment);
            parentLayout = itemView.findViewById(R.id.parentLayoutForUserDetails);
        }
    }

    public void notifyChangeData(Response<List<GameReviews>> response) {
        contact = response.body();
        notifyDataSetChanged();
    }

}
