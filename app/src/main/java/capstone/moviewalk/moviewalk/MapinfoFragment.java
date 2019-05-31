package capstone.moviewalk.moviewalk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MapinfoFragment  extends android.app.Fragment {
    LinearLayout ll_fragment;
    TextView tv_fragment;
    Button button;
    String URL;

    public MapinfoFragment(){   }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_info, container, false);
        tv_fragment = (TextView)v.findViewById(R.id.tv_fragment);

        addListenerOnButton(v);
        return v;
    }

    public void changeFragmentURL(String infoURL_map) {
        URL = infoURL_map;
    }

    public void addListenerOnButton(View v){
        button = (Button)v.findViewById(R.id.blogbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(browserIntent);
            }
        });
    }
    public void changeFragmentTextView(String text){
        tv_fragment.setText(text);
    }
}
