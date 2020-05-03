package in.ccode.explorerschool.assignmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import in.ccode.explorerschool.R;

public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.ViewHolder> {

    private List<AssignmentListData> listdata;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");

    // RecyclerView recyclerView;
    public AssignmentListAdapter(List<AssignmentListData> listdata) {
        this.listdata = listdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.assignment_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.subject_id.setText(listdata.get(position).getSubject_id());
        holder.details.setText(listdata.get(position).getDetails());
        holder.due_date.setText(listdata.get(position).getDue_date());
        //TODO change date colour
        Date date = new Date();
        //Log.d("Assignment Adapter", "" + simpleDateFormat.parse(listdata.get(position).getDue_date()));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView subject_id;
        TextView details;
        TextView due_date;
        LinearLayout assignment_due_colour;
        public ViewHolder(View itemView) {
            super(itemView);
            this.subject_id = (TextView) itemView.findViewById(R.id.subject_name);
            this.details = (TextView) itemView.findViewById(R.id.assignment_description);
            this.due_date = (TextView) itemView.findViewById(R.id.assignment_due_date);
            assignment_due_colour = (LinearLayout) itemView.findViewById(R.id.assignment_due_colour);
        }
    }
}
