package in.ccode.explorerschool.circularActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.ccode.explorerschool.R;

public class CircularListAdapter extends RecyclerView.Adapter<CircularListAdapter.ViewHolder> {
    private List<CircularListData> listData;

    public CircularListAdapter(List<CircularListData> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.circular_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CircularListAdapter.ViewHolder holder, int position) {
        holder.heading.setText(listData.get(position).getHeading());
        holder.details.setText(listData.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView heading;
        TextView details;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.heading = (TextView) itemView.findViewById(R.id.heading);
            this.details = (TextView) itemView.findViewById(R.id.detail);
        }
    }
}
