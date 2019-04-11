package capstone.moviewalk.moviewalk;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment1_info  extends android.app.Fragment {
    public static Fragment1_info newInstance(){
        return new Fragment1_info();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }



    Button btn_prev;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);


        /////맵마커 클릭값 받아오기
        Bundle bundle = getArguments();
        String markerID = bundle.getString("markerID");



        btn_prev = (Button)v.findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }
}
