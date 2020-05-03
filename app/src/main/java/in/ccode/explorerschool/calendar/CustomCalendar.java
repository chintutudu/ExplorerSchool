package in.ccode.explorerschool.calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import in.ccode.explorerschool.R;
import in.ccode.explorerschool.attendanceActivity.AttendanceActivity;

public class CustomCalendar extends LinearLayout implements CalendarUtils {
    private static final String TAG = "CustomCalendar";
    private ImageView previousButton, nextButton;
    private TextView currentDate;
    private Context context;
    public GridView calendarGridView;
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private static final int MAX_CALENDAR_COLUMN = 42;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
    private GridAdapter mAdapter;
    List<Events> eventsList = new ArrayList<>();
    List<Absent> absentList = new ArrayList<>();

    public CustomCalendar(Context context) {
        super(context);
        this.context = context;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
    }

    public CustomCalendar(Context context, List<Events> eventsList) {
        super(context);
        this.context = context;
        this.eventsList = eventsList;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
    }

    /*public CustomCalendar(AttendanceActivity context, List<Absent> mAbsent) {
        super(context);
        this.context = context;
        this.absentList = mAbsent;
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();

    }*/

    private void initializeUILayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = (ImageView) view.findViewById(R.id.previous_month);
        nextButton = (ImageView) view.findViewById(R.id.next_month);
        currentDate = (TextView) view.findViewById(R.id.display_current_date);
        calendarGridView = (GridView) findViewById(R.id.calendar_grid);
    }

    private void setUpCalendarAdapter() {
        List<Date> dayValueInCells = new ArrayList<Date>();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        String sDate = formatter.format(cal.getTime());
        currentDate.setText(sDate);
        //Calendar TODO
        Log.d(TAG, "" +context);
        mAdapter = new GridAdapter(context, dayValueInCells, cal, eventsList);
        calendarGridView.setAdapter(mAdapter);
    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCalendar.this.previousMonths();
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCalendar.this.nextMonth();
            }
        });
    }

    @Override
    public void nextMonth() {
        cal.add(Calendar.MONTH, 1);
        setUpCalendarAdapter();
    }

    @Override
    public void previousMonths() {
        cal.add(Calendar.MONTH, -1);
        setUpCalendarAdapter();
    }
}
