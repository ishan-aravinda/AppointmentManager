package iit.com.appointmentmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static String date;
    private Button btnAdd, btnDelete, btnEdit, btnMove, btnSearch;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DBHelper mydb;

    private CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnMove = (Button) findViewById(R.id.btn_move);
        btnSearch = (Button) findViewById(R.id.btn_search);

        calendar = (CalendarView) findViewById(R.id.calendarView);
        mydb = new DBHelper(this);

        date = getCurrentDate(); //The current date is set to the date variable first

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                setDate(date, month, year);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateIsValid(date)) {
                    Intent intent = new Intent(MainActivity.this, AddAppointmentActivity.class);
                    intent.putExtra("date", date);
                    startActivity(intent);
                } else{
                    Toast.makeText(MainActivity.this, "Date has passed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("date", getCurrentDate());
                startActivity(intent);
            }
        });
    }

    private void setDate(int date, int month, int year) {
        DecimalFormat df = new DecimalFormat("00");
        String strDate = df.format(date);
        String strMonth = df.format(month + 1);
        MainActivity.date = year + "-" + strMonth + "-" + strDate;
    }

    private String getCurrentDate() {
        Date date = new Date();
        return sdf.format(date);
    }

    private Boolean dateIsValid(String tempDate) {
        try {
            Date date = sdf.parse(tempDate);

            if (date.after(new Date())) {
                return true;
            } else if (tempDate.equalsIgnoreCase(sdf.format(new Date()))) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
