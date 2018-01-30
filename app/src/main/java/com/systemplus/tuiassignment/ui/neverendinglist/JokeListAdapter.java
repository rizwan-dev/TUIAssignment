package com.systemplus.tuiassignment.ui.neverendinglist;

import android.arch.paging.PagedListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.systemplus.tuiassignment.R;
import com.systemplus.tuiassignment.model.JokeData;
import com.systemplus.tuiassignment.repository.NetworkState;
import com.systemplus.tuiassignment.repository.Status;
import com.systemplus.tuiassignment.util.ListItemClickListener;


public class JokeListAdapter extends PagedListAdapter<JokeData, RecyclerView.ViewHolder> {

    private NetworkState networkState;
    private ListItemClickListener itemClickListener;

    JokeListAdapter(ListItemClickListener itemClickListener) {
        super(JokeData.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == R.layout.item_joke_list) {
            view = layoutInflater.inflate(R.layout.item_joke_list, parent, false);
            return new JokeItemViewHolder(view);
        } else if (viewType == R.layout.network_state_item) {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view, itemClickListener);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_joke_list:
                ((JokeItemViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.item_joke_list;
        }
    }

    void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    static class JokeItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtJoke, txtJokeId;

        JokeItemViewHolder(View itemView) {
            super(itemView);
            txtJokeId = itemView.findViewById(R.id.txtJokeId);
            txtJoke = itemView.findViewById(R.id.txtJoke);
        }

        void bindTo(JokeData user) {
            txtJoke.setText(user.getJoke());
            txtJokeId.setText(String.valueOf(user.getId()));
        }
    }

    static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progressBar;
        private final TextView errorMsg;
        private Button button;

        NetworkStateItemViewHolder(View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            errorMsg = itemView.findViewById(R.id.error_msg);
            button = itemView.findViewById(R.id.retry_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listItemClickListener.onRetryClick(view, getAdapterPosition());
                }
            });
        }


        void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == Status.LOADING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == Status.ERROR) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.getMsg());
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
