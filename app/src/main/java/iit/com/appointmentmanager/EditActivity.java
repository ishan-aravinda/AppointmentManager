package iit.com.appointmentmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    String title, hour, minute, desc, date;
    private EditText editTitle, editHour, editMinute, editDesc;
    private Button btnSave;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent incomingIntent = getIntent();
        date = incomingIntent.getStringExtra("date");
        title = incomingIntent.getStringExtra("title");
        hour = incomingIntent.getStringExtra("hour");
        minute = incomingIntent.getStringExtra("minute");
        desc = incomingIntent.getStringExtra("description");
        mydb = new DBHelper(this);

        editTitle = (EditText) findViewById(R.id.editTitle);
        editHour = (EditText) findViewById(R.id.editHour);
        editMinute = (EditText) findViewById(R.id.editMinute);
        editDesc = (EditText) findViewById(R.id.editDesc);
        btnSave = (Button) findViewById(R.id.btnEditSave);

        editTitle.setText(title);
        editHour.setText(hour);
        editMinute.setText(minute);
        editDesc.setText(desc);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prevTitle = title;
                title = String.valueOf(editTitle.getText());
                hour = String.valueOf(editHour.getText());
                minute = String.valueOf(editMinute.getText());
                desc = String.valueOf(editDesc.getText());

                if(mydb.updateAppointment(date, prevTitle, title, (hour + ":" + minute), desc)){
                    Toast.makeText(EditActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(EditActivity.this, ViewActivity.class).putExtra("date", date));
                }else{
                    Toast.makeText(EditActivity.this, "Duplicate Title", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
