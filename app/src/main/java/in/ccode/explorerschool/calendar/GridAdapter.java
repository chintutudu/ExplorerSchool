package in.ccode.explorerschool.calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import in.ccode.explorerschool.R;

public class GridAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    List<Events> allEvent;

    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<Events> allEvents) {
        super(context, R.layout.calendar_single_cell);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvent = allEvents;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.calendar_single_cell, parent, false);
        }
        if (displayMonth == currentMonth && displayYear == currentYear) {
            //view.setBackgroundResource(R.drawable.circle);
        } else {
            view.setAlpha(0.0f);
        }
        // Add day to calendar
        TextView cellNumber= (TextView) view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));
        cellNumber.setTextColor(ContextCompat.getColor(getContext(), R.color.darkGray));
        // Add events to calendar
        Calendar eventCalendar = Calendar.getInstance();
        for (int i=0; i<allEvent.size(); i++) {
            eventCalendar.setTime(allEvent.get(i).getDate());
            if (dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1) {
                cellNumber.setTextColor(Color.WHITE);
                view.setBackgroundResource(R.drawable.red_circle);
            }
        }
        return view;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return monthlyDates.indexOf(item);
    }
}
