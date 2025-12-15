package com.example.taskmanagerapplication.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.model.History;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<History> historyList;

    public HistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        History log = historyList.get(position);
        holder.txtAction.setText(log.action);
        holder.txtDetails.setText(log.details);
        holder.txtDate.setText(log.createdAt);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtAction, txtDetails, txtDate;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAction = itemView.findViewById(R.id.txtAction);
            txtDetails = itemView.findViewById(R.id.txtDetails);
            txtDate = itemView.findViewById(R.id.txtDate);
        }
    }
}
