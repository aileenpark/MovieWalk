package capstone.moviewalk.moviewalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContext=this;


        Button init = (Button) findViewById(R.id.init);
        init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookmarkDeleteAll BookmarkDeleteAll = new BookmarkDeleteAll(null);
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(BookmarkDeleteAll);
            }
        });
    }
}
