package capstone.moviewalk.moviewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity {

    //MySQL의 DB를 받아서 datalist에 add
    private ListView listView;
    private ListAdapter adapter;
    private List<Data> dataList;
    private List<Data> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView);
        dataList = new ArrayList<Data>();
        saveList = new ArrayList<Data>();
        adapter = new ListAdapter(getApplicationContext(), dataList,this, saveList);
        listView.setAdapter(adapter);

        final String query = intent.getExtras().getString("query");

        final EditText search = (EditText)findViewById(R.id.search);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("dataList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String id,name,title,latitude,longitude,address,image1,image2,information,infoURL;
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
                infoURL = object.getString("infoURL");

                Data data = new Data(id,name,title,latitude,longitude,address,image1,image2,information,infoURL);
                dataList.add(data);
                saveList.add(data);
                count++;

                adapter.searchData(query);
                search.setText(query);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = search.getText().toString();
                adapter.searchData(text);

            }
        });

    }




}
