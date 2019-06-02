package capstone.moviewalk.moviewalk;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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

    BookmarkSplash bookmark_splash = (BookmarkSplash)BookmarkSplash._bookmarkSplash;


    //Double[][] LatLong = new Double[20][2];
    //Double[][] Distance;

    String[] Lat_route_multipoint = new String[20];
    String[] Long_route_multipoint = new String[20];
    String[] name_multipoint = new String[20];
    String[] address_multipoint = new String[20];
    String[] information_multipoint = new String[20];
    String[] info_URL_multipoint = new String[20];
    int count2 = 0;

    private static double[][] distances;
    private static double[][] LatLong = new double[7][2];
    private static double finalResults[];
    private static String paths[];
    private static int counter = 0;
    private static int size = 0;


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

                LatLong[count][0]=  Double.parseDouble(latitude);
                LatLong[count][1]=  Double.parseDouble(longitude);

                Lat_route_multipoint[count]=  latitude;
                Long_route_multipoint[count]=  longitude;
                name_multipoint[count]=  name;
                address_multipoint[count] = address;
                information_multipoint[count] = address;
                info_URL_multipoint[count] = address;

                BookmarkList.add(data);
                count++;
                size =  count;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        adapter = new BookmarkAdapter(getApplicationContext(), BookmarkList);
        listView.setAdapter(adapter);

        System.out.println("카운터"+size);


        route.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                distances= new double[size][size];

                //////////////////////no touch/////////////////////거리행렬생성
                for(int k=0;k<size;k++){
                    for(int i=k;i<size;i++){

                        if(k==i){
                            distances[k][i]=0.0;
                        }
                        else{
                            distances[k][i]=getDistance(LatLong[k][0],LatLong[k][1],LatLong[i][0],LatLong[i][1]);
                            distances[i][k]=distances[k][i];
                        }
                    }
                }

                for(int k=0;k<size;k++){
                    for(int i=0;i<size;i++){

                        System.out.println(distances[k][i]+" ");

                    }
                    System.out.println("\n");
                }
                ////////////////////no touch///////////////////거리행렬생성


                int numSolutions = factorial(size - 1);
                finalResults = new double[numSolutions];
                paths = new String[numSolutions];


                /* ------------------------- ALGORITHM INITIALIZATION ----------------------- */

                // Initial variables to start the algorithm
                String path = "";
                int[] vertices = new int[size - 1];

                // Filling the initial vertices array with the proper values
                for (int i = 1; i < size; i++) {
                    vertices[i - 1] = i;
                }

                // FIRST CALL TO THE RECURSIVE FUNCTION
                double distance = procedure(0, vertices, path, 0);

                int optimal = 0;
                for (int i = 0; i < numSolutions; i++) {

                    System.out.print("Path: " + paths[i] + ". Distance = " + finalResults[i] + "\n");

                    // When we reach the optimal one, its index is saved
                    if (finalResults[i] == distance) {
                        optimal = i;
                    }
                }
                //System.out.println();
                System.out.println("Path: " + paths[optimal] + ". Distance = " + finalResults[optimal] + " (OPTIMAL)");
                String part[];
                part = paths[optimal].split("[^A-Z0-9]+|[^0-9$]");
                int foo;
                int nu=0;
                for(int i=0;i<part.length-1;i++){
                    System.out.println(part[i]);
                    foo = Integer.parseInt(part[i]);
                    routeRequest RouteRequest = new routeRequest(Double.toString(LatLong[foo][0]),Double.toString(LatLong[foo][1]),Integer.toString(nu+1),null);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(RouteRequest);
                    nu++;
                }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                for(int k=0;k<count2;k++){
                    System.out.println(LatLong[k][0]+" "+LatLong[k][1]+"");
                    routeRequest RouteRequest = new routeRequest(Double.toString(LatLong[k][0]),Double.toString(LatLong[k][1]),Integer.toString(k+1),null);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(RouteRequest);
                }



                Intent intent = new Intent(BookmarkListActivity.this, MapActivity_multipoint.class);

                intent.putExtra("lat_multipoint", Lat_route_multipoint);
                intent.putExtra("long_multipoint", Long_route_multipoint);
                intent.putExtra("name_multipoint", name_multipoint);
                intent.putExtra("num_multipoint",size);
                intent.putExtra("address_multipoint", address_multipoint);
                intent.putExtra("information_multipoint", information_multipoint);
                intent.putExtra("info_URL_multipoint", info_URL_multipoint);

                startActivity(intent);

            }
        });


    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2){
        return Math.sqrt((lat1-lat2)*(lat1-lat2)+(lon1-lon2)*(lon1-lon2));
    }

    private static int factorial(int n) {

        if (n <= 1) return 1;
        else return (n * factorial(n - 1));
    }

    /* ------------------------------- RECURSIVE FUNCTION ---------------------------- */

    private static double procedure(int initial, int vertices[], String path, double costUntilHere) {

        // We concatenate the current path and the vertex taken as initial
        path = path + Integer.toString(initial) + " - ";
        int length = vertices.length;
        double newCostUntilHere;


        // Exit case, if there are no more options to evaluate (last node)
        if (length == 0) {

            // Both results, numerical distances and paths to those distances, are stored
            paths[counter] = path + "0";
            finalResults[counter] = costUntilHere + distances[initial][0];

            counter++;
            return (distances[initial][0]);
        }


        // Common case, where there are more than 1 node
        else {

            int[][] newVertices = new int[length][(length - 1)];
            double costCurrentNode, costChild;
            double bestCost = Double.MAX_VALUE;

            // For each of the nodes of the list
            for (int i = 0; i < length; i++) {

                // Each recursion new vertices list is constructed
                for (int j = 0, k = 0; j < length; j++, k++) {

                    // The current child is not stored in the new vertices array
                    if (j == i) {
                        k--;
                        continue;
                    }
                    newVertices[i][k] = vertices[j];
                }

                // Cost of arriving the current node from its parent
                costCurrentNode = distances[initial][vertices[i]];

                // Here the cost to be passed to the recursive function is computed
                newCostUntilHere = costCurrentNode + costUntilHere;

                // RECURSIVE CALLS TO THE FUNCTION IN ORDER TO COMPUTE THE COSTS
                costChild = procedure(vertices[i], newVertices[i], path, newCostUntilHere);

                // The cost of every child + the current node cost is computed
                double totalCost = costChild + costCurrentNode;

                // Finally we select from the minimum from all possible children costs
                if (totalCost < bestCost) {
                    bestCost = totalCost;
                }
            }

            return (bestCost);
        }
    }


    /////////////////////////////////////////////no touch//////////////////////////////////////////////
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            bookmark_splash.finish();
        } catch (NullPointerException e) {

        }
    }
}
