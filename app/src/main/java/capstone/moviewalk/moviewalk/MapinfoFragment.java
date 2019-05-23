package capstone.moviewalk.moviewalk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MapinfoFragment  extends android.app.Fragment {
    LinearLayout ll_fragment;
    TextView tv_fragment;


    public MapinfoFragment(){   }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapinfo, container, false);
        tv_fragment = (TextView)v.findViewById(R.id.tv_fragment);

        return v;
    }

    public void changeFragmentTextView(String text){
        tv_fragment.setText(text);
    }
}
