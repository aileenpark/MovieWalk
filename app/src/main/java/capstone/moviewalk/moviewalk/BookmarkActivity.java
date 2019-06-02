package capstone.moviewalk.moviewalk;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class BookmarkActivity extends AppCompatActivity
        implements BookmarkFragment.OnFragmentInteractionListener, LocationBookmarkFragment.OnFragmentInteractionListener, RouteBookmarkFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);


        Fragment fragment = new BookmarkFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.bookmark_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}
