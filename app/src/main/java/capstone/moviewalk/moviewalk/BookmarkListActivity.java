package capstone.moviewalk.moviewalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookmarkListActivity extends AppCompatActivity {

    //MySQL의 DB를 받아서 datalist에 add
    private ListView listView;
    private BookmarkAdapter adapter;
    private List<Data> BookmarkList;

    int count2 = 0;
    Double[][] LatLong = new Double[20][2];
    Double[][] Distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list2);

        Intent intent = getIntent();
        listView = (ListView) findViewById(R.id.listView2);

        Button route= (Button)findViewById(R.id.route);
        BookmarkList= new ArrayList<Data>();

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

                LatLong[count][0]=  Double.parseDouble(latitude);
                LatLong[count][1]=  Double.parseDouble(longitude);

                BookmarkList.add(data);
                count++;
                count2 = count;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new BookmarkAdapter(getApplicationContext(), BookmarkList);
        listView.setAdapter(adapter);

        route.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Distance= new Double[count2][count2];
                int c=0;
                System.out.println(count2);


                for(int k=0;k<count2;k++){
                    for(int i=k;i<count2;i++){

                        if(k==i){
                            Distance[k][i]=0.0;
                        }
                        else{
                            Distance[k][i]=getDistance(LatLong[k][0],LatLong[k][1],LatLong[i][0],LatLong[i][1]);
                            Distance[i][k]=Distance[k][i];
                        }
                    }

                }

                for(int k=0;k<count2;k++){
                    for(int i=0;i<count2;i++){

                        System.out.println(Distance[k][i]+" ");

                    }
                    System.out.println("\n");
                }



                for(int k=0;k<count2;k++){
                    System.out.println(LatLong[k][0]+" "+LatLong[k][1]+"");
                    routeRequest RouteRequest = new routeRequest(Double.toString(LatLong[k][0]),Double.toString(LatLong[k][1]),Integer.toString(k+1),null);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(RouteRequest);
                }



            }
        });
    }

    public double getDistance(Double lat1, Double lon1, Double lat2, Double lon2){
        return Math.sqrt((lat1-lat2)*(lat1-lat2)+(lon1-lon2)*(lon1-lon2));
    }
}
