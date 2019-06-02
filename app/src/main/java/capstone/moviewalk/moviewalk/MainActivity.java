package capstone.moviewalk.moviewalk;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Data> dataList;
    private List<Data> saveList;
    private ListAdapter adapter;

    SplashActivity splash_activity = (SplashActivity)SplashActivity._splashActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //데이터 받아오는 부분
        Intent intent = getIntent();
        final String str = intent.getStringExtra("dataList");

        //여기까지


        Button bookmarkButton = (Button) findViewById(R.id.button3);
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), BookmarkSelect.class);
                startActivity(intent);
            }
        });

        Button settingsButton = (Button) findViewById(R.id.button2);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });



        //inflate searchview
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQuery("", false);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager methodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                methodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

                Intent intent = new Intent(getApplicationContext(), SearchListActivity.class);
                intent.putExtra("query", query);
                intent.putExtra("dataList", str);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }


        });
        SearchManager searchManager = (SearchManager) getApplicationContext().getSystemService(Context.SEARCH_SERVICE);
        if(searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setIconified(true);

        //hide underline of searchview
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate!=null) {
            searchPlate.setBackgroundColor (Color.TRANSPARENT);
            int searchTextId = searchPlate.getContext ().getResources ().getIdentifier ("android:id/search_src_text", null, null);
        }



        /*Spinner spinner = (Spinner)findViewById(R.id.spinner2);
>>>>>>> imewoo/workspace
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
<<<<<<< HEAD
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(position)+"is selected", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
=======

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });*/



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            splash_activity.finish();
        } catch (NullPointerException e) {

        }
    }
}
