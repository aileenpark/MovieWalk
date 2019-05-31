package capstone.moviewalk.moviewalk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookmarkListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
    SwipeRefreshLayout mSwipeRefreshLayout;

    //MySQL의 DB를 받아서 datalist에 add
    private ListView listView;
    private BookmarkAdapter adapter;
    private List<Data> BookmarkList;


    Double[][] LatLong = new Double[20][2];
    Double[][] Distance;

    String[] Lat_route_multipoint = new String[20];
    String[] Long_route_multipoint = new String[20];
    String[] name_multipoint = new String[20];
    int count2 = 0;

    public static int[][] W;
    public static int[][] dp;
    public static int N;
    public static final int INF = 1000000000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list2);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

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

                //LatLong[count][0]=  Double.parseDouble(latitude);
                //LatLong[count][1]=  Double.parseDouble(longitude);

                Lat_route_multipoint[count]=  latitude;
                Long_route_multipoint[count]=  longitude;
                name_multipoint[count]=  name;

                BookmarkList.add(data);
                count++;
                count2 = count;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new BookmarkAdapter(getApplicationContext(), BookmarkList);
        listView.setAdapter(adapter);


        //latlong[][0]-latitude
        //latlong[][1]-longitude
        route.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
/*
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
//////////////
                Scanner sc = new Scanner(System.in);
                N = sc.nextInt();
                W = new int[N + 1][N + 1];
                dp = new int[N + 1][1 << N];
                for (int i = 1; i <= N; i++) {
                    for (int j = 1; j <= N; j++) {
                        W[i][j] = sc.nextInt();
                    }
                }
                // 2차원 배열의 모든 원소를 -1로
                for (int i = 1; i <= N; i++) {
                    Arrays.fill(dp[i], -1);
                }

                int start = 1;
                System.out.println(getShortestPath(start, 1));


//////////
                for(int k=0;k<count2;k++){
                    System.out.println(LatLong[k][0]+" "+LatLong[k][1]+"");
                    routeRequest RouteRequest = new routeRequest(Double.toString(LatLong[k][0]),Double.toString(LatLong[k][1]),Integer.toString(k+1),null);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(RouteRequest);
                }
*/


                Intent intent = new Intent(BookmarkListActivity.this, MapActivity_multipoint.class);

                intent.putExtra("lat_multipoint", Lat_route_multipoint);
                intent.putExtra("long_multipoint", Long_route_multipoint);
                intent.putExtra("name_multipoint", name_multipoint);
                intent.putExtra("num_multipoint",count2);

                startActivity(intent);

            }
        });
        adapter = new BookmarkAdapter(getApplicationContext(), BookmarkList);
        listView.setAdapter(adapter);
    }

    public double getDistance(Double lat1, Double lon1, Double lat2, Double lon2){
        return Math.sqrt((lat1-lat2)*(lat1-lat2)+(lon1-lon2)*(lon1-lon2));
    }

    public static int getShortestPath(int current, int visited) {

        // 모든 정점을 다 들른 경우
        if (visited == (1 << N) - 1)
            return W[current][1];

        // 이미 들렀던 경로이므로 바로 return
        if (dp[current][visited] >= 0)
            return dp[current][visited];

        int ret = INF;

        // 집합에서 다음에 올 원소를 고르자!
        for (int i = 1; i <= N; i++) {
            int next = i;

            if ((visited & (1 << (next - 1))) != 0) // 중요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                continue;

            if(W[current][next] == 0) // 0은 경로가 없으므로 pass
                continue;

            int temp = W[current][next] + getShortestPath(next, visited + (1 << (next - 1)));
            ret = Math.min(ret, temp);
        }

        return dp[current][visited] = ret;

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                new BackgroundTask4().execute();
            }
        },300);

    }

    public class BackgroundTask4 extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://keeka2.cafe24.com/BookmarkList.php";
        }


        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }


        @Override
        //php 연결되면 ManagementActivity 로 전환
        public void onPostExecute(String result){
            Intent intent = new Intent(BookmarkListActivity.this, BookmarkListActivity.class);

            intent.putExtra("dataList", result);

            BookmarkListActivity.this.startActivity(intent);

        }
    }
}
