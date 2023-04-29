package com.backdoor.vgr.View.Model.Game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.backdoor.vgr.R;
import com.backdoor.vgr.databinding.ItemReviewBinding;

import java.util.List;
import java.util.Objects;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private List<GameReviews> contact;
    private final Context mContext;
    private OnItemClickListener onItemClickListener;

    public ReviewAdapter(List<GameReviews> contacts, Context context, OnItemClickListener onItemClickListener) {
        this.contact = contacts;
        this.mContext = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_review, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        String firstName = contact.get(position).getUserContact().getFirst_name();
        String lastName = contact.get(position).getUserContact().getLast_name() != null ? contact.get(position).getUserContact().getLast_name() : "";

        String name = firstName + " " + lastName;

        if (contact.get(position).is_mine()) {
            name = name + " (You)";
            holder.binding.parentLayoutForUserDetails.setBackgroundTintList(AppCompatResources.getColorStateList(mContext, R.color.whiteLight));
            holder.binding.deleteReviewBtn.setVisibility(View.VISIBLE);
        } else {
            holder.binding.parentLayoutForUserDetails.setBackgroundTintList(AppCompatResources.getColorStateList(mContext, R.color.white));
        }

        if (!Objects.equals(contact.get(position).getStatus(), "Active")) {
            name = name + " (" + contact.get(position).getStatus() + ")";
        }

        holder.binding.userName.setText(name);
        holder.binding.comment.setText(contact.get(position).getComment());
        holder.binding.rating.setText(String.valueOf(contact.get(position).getRating()));

        holder.binding.deleteReviewBtn.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
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
        ItemReviewBinding binding;

        MyViewHolder(ItemReviewBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyChangeData(List<GameReviews> gameReviewsList) {
        contact = gameReviewsList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
