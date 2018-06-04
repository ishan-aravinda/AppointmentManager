package iit.com.appointmentmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText txtSearch;
    private Button btnSearchText;
    private ListView objList;
    private DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent incomingIntent = getIntent();
        final String date = incomingIntent.getStringExtra("date");

        txtSearch = (EditText) findViewById(R.id.searchText);
        btnSearchText = (Button) findViewById(R.id.btnSearchText);
        objList = (ListView) findViewById(R.id.searchListView);
        mydb = new DBHelper(this);

        btnSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logic logic = Logic.getInstance();
                ArrayList<String> list = logic.searchFor(String.valueOf(txtSearch.getText()), date, mydb);
                populateList(list);
            }
        });
    }

    private void populateList(ArrayList<String> list) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        objList.setAdapter(arrayAdapter);
    }
}
