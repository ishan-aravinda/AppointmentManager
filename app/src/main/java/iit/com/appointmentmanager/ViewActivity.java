package iit.com.appointmentmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    private ListView objList;
    private Button btnDone;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent incomingIntent = getIntent();
        final String date = incomingIntent.getStringExtra("date");

        objList = (ListView) findViewById(R.id.viewAppointmentList);
        btnDone = (Button) findViewById(R.id.btnDone);
        mydb = new DBHelper(this);

        final ArrayList<String> arrayList = mydb.getAppointmentsOnDate(date);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        objList.setAdapter(arrayAdapter);
        objList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                String[] arr = arrayList.get(position).split(",");
                String[] timeArr = arr[1].split(":");

                Intent intent = new Intent(ViewActivity.this, EditActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("title", arr[0]);
                intent.putExtra("hour", timeArr[0]);
                intent.putExtra("minute", timeArr[1]);
                intent.putExtra("description", arr[2]);
                finish();
                startActivity(intent);
            }
        });
    }
}
