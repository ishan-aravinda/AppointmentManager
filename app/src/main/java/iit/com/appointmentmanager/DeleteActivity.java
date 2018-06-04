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

public class DeleteActivity extends AppCompatActivity {
    private ListView objList;
    private Button btnDeleteAll;
    private DBHelper mydb;
    private ArrayList<String> appointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");

        objList = (ListView) findViewById(R.id.appointmentList);
        btnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);
        mydb = new DBHelper(this);

        populateList(date);

        objList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                String[] arr = appointmentList.get(position).split(",");
                mydb.deleteAppointment(arr[0], date);
                populateList(date);
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydb.deleteAllAppointments(date);
                finish();
                startActivity(new Intent(DeleteActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void populateList(String date) {
        appointmentList = mydb.getAppointmentsOnDate(date);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, appointmentList);
        objList.setAdapter(arrayAdapter);
    }
}
