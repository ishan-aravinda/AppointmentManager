package iit.com.appointmentmanager;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

public class AddAppointmentActivity extends AppCompatActivity {
    private EditText editTitle, editDesc;
    private TextView txtHour, txtMinute;
    private Button btnSave, btnSetTime;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        System.out.println("Date:" + date);

        editTitle = (EditText) findViewById(R.id.addTitle);
        editDesc = (EditText) findViewById(R.id.addDesc);
        txtHour = (TextView) findViewById(R.id.addAppHour);
        txtMinute = (TextView) findViewById(R.id.addAppMinute);
        btnSetTime = (Button)findViewById(R.id.btnSetTime);
        btnSave = (Button)findViewById(R.id.btnAddSave);

        mydb = new DBHelper(this);

        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        DecimalFormat df = new DecimalFormat("00");
                        txtHour.setText(df.format(selectedHour));
                        txtMinute.setText(df.format(selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.valueOf(editTitle.getText());
                String time = String.valueOf(txtHour.getText()) + ":" + String.valueOf(txtMinute.getText());
                String description = String.valueOf(editDesc.getText());

                if(mydb.addAppointment(title, date, time, description)){
                    Toast.makeText(AddAppointmentActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(AddAppointmentActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }else{
                    Toast.makeText(AddAppointmentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
