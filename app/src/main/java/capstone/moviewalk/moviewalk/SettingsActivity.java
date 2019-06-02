package capstone.moviewalk.moviewalk;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        final ArrayList<String> alarmList = new ArrayList<String>();
        alarmList.add("알림 활성화");
        ListView listView1 = (ListView) findViewById(R.id.alarmList);
        SwitchListviewAdapter listviewAdapter = new SwitchListviewAdapter(this, R.layout.switch_list, alarmList);
        listView1.setAdapter(listviewAdapter);

        final ArrayList<String> dataInitList = new ArrayList<String>();
        dataInitList.add("데이터 초기화");
        ListView listView2 = (ListView) findViewById(R.id.dataInitList);
        ArrayAdapter listviewAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataInitList);
        listView2.setAdapter(listviewAdapter2);
    }
}
