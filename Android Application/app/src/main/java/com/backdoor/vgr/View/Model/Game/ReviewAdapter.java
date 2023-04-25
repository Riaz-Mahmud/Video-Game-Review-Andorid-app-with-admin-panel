package com.backdoor.vgr.View.Model.Game;

import static com.backdoor.vgr.View.Activity.MainActivity.perfConfig;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.backdoor.vgr.R;
import com.backdoor.vgr.View.Model.Default_Contact;
import com.backdoor.vgr.databinding.ItemReviewBinding;
import com.backdoor.vgr.network.ApiClient;
import com.backdoor.vgr.network.ApiInterface;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_review, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        String name = contact.get(position).getUserContact().getFirst_name() + " " +
                contact.get(position).getUserContact().getLast_name();

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
            if (contact.get(position).is_mine()) {
                deleteReview(contact.get(position));
            }
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

    private void deleteReview(GameReviews review) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        confirmDelete(review);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    private void confirmDelete(GameReviews review) {
        if (checkConnection()) {

            perfConfig.LoadingBar(mContext, "Please wait...");

            ApiInterface apiService = ApiClient.getClient(mContext.getApplicationContext()).create(ApiInterface.class);
            Call<Default_Contact> call = apiService.deleteSingleReview(review.getGameId(), review.getId());

            call.enqueue(new Callback<Default_Contact>() {
                @Override
                public void onResponse(Call<Default_Contact> call, Response<Default_Contact> response) {
                    perfConfig.loadingBar.dismiss();
                    if (response.isSuccessful()) {
                        perfConfig.displayToast(response.body().getError());
                        if (response.body().isSuccess()) {
                            refreshDataSet(review);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Default_Contact> call, Throwable t) {
                    call.cancel();
                    perfConfig.loadingBar.dismiss();
                    perfConfig.displayToast("Something went wrong!");
                }
            });
        } else {
            perfConfig.displayToast("No Internet Connection!");
        }
    }

    private void refreshDataSet(GameReviews review) {
        if (checkConnection()) {
            ApiInterface apiService = ApiClient.getClient(mContext.getApplicationContext()).create(ApiInterface.class);
            Call<SingleGameContact> call = apiService.getSingleGameData(review.getGameId());

            call.enqueue(new Callback<SingleGameContact>() {
                @Override
                public void onResponse(Call<SingleGameContact> call, Response<SingleGameContact> response) {
                    if (response.isSuccessful()) {
                        notifyChangeData(response.body().getGameDetailsContact().getGameReviewsList());
                    } else {
                        Log.d("SingleRoom", "not success");
                        perfConfig.displayToast("Something going wrong! Please try again!");
                    }
                }

                @Override
                public void onFailure(Call<SingleGameContact> call, Throwable t) {
                    Log.d("SingleRoom", "OnFailure");
                }
            });
        } else {
            perfConfig.displayToast("No Internet Connection!");
        }
    }

    public boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager)
                mContext.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null;
    }

}
