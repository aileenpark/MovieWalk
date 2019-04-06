package capstone.moviewalk.moviewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagementActivity extends AppCompatActivity {

    //MySQL의 DB를 받아서 datalist에 add13
    private ListView listView;
    private ListAdapter adapter;
    private List<Data> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        dataList = new ArrayList<Data>();
        adapter = new ListAdapter(getApplicationContext(), dataList);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("dataList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String id,name,title,latitude,longitude,address,image1,image2,information;
            while(count< jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                id = object.getString("id");
                name = object.getString("name");
                title = object.getString("title");
                latitude = object.getString("latitude");
                longitude = object.getString("longitude");
                address = object.getString("address");
                image1 = object.getString("image1");
                image2 = object.getString("image2");
                information = object.getString("information");

                Data data = new Data(id,name,title,latitude,longitude,address,image1,image2,information);
                dataList.add(data);
                count++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
