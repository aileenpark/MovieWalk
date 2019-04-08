package capstone.moviewalk.moviewalk;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubActivity extends AppCompatActivity {

    Button btn_prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btn_prev = (Button)findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                //이전 페이지로 화면전환
                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(intent);
                //이렇게 하면 화면이 계속 겹쳐져서 뒤로가기 많이눌러야 종료가능
                */

                finish(); //현재 액티비티 종료 .. 뒤로가기 한번에 메인에서 종료가능
            }
        });
    }
}