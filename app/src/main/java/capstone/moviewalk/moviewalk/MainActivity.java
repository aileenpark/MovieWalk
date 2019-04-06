package capstone.moviewalk.moviewalk;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button managementButton = (Button)findViewById(R.id.managementButton);

        managementButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
                 new BackgroundTask().execute();
           }
        });
    }

    //DB 받아오는 php 파일로 연결2

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://keeka2.cafe24.com/List.php";
        }

        @Override
        protected String doInBackground(Void... voids){
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
        //php 연결되면 ManagementActivity 로 전환2
        public void onPostExecute(String result){
            Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
            intent.putExtra("dataList", result);
            MainActivity.this.startActivity(intent);

        }

    }
}
