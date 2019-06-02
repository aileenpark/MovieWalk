package capstone.moviewalk.moviewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class routeListActivity extends AppCompatActivity {

    private ListView listView;
    private routeAdapter adapter;
    private List<Data> routeList;

    routeSplash route_splash = (routeSplash)routeSplash._routeSplash;


    Double[][] LatLong_route = new Double[20][2];
    String[] Lat_route = new String[20];
    String[] Long_route = new String[20];

    int count2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_list);

        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView2);
//
//        Button map= (Button)findViewById(R.id.map);
        FloatingActionButton routefab = (FloatingActionButton)findViewById(R.id.routefab);

       // Button map= (Button)findViewById(R.id.map);
        routeList= new ArrayList<Data>();

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



                Lat_route[count]=  latitude;
                Long_route[count]=  longitude;

                routeList.add(data);
                count++;

                count2=count;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        routefab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(routeListActivity.this, MapActivity_route.class);

                intent.putExtra("lat", Lat_route);
                intent.putExtra("long", Long_route);
                intent.putExtra("num",count2);

                startActivity(intent);
            }
        });

        adapter = new routeAdapter(getApplicationContext(), routeList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            route_splash.finish();
        } catch (NullPointerException e) {

        }
    }
}
