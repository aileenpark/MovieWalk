package capstone.moviewalk.moviewalk;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class HotelActivity extends AppCompatActivity {

    TextView textViewSD, textViewED;
    Calendar cStart, cEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_2);

        Button connectButton = (Button)findViewById(R.id.connectButton);
        final EditText peopleNum = (EditText)findViewById(R.id.peopleNum);

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        this.cStart = (Calendar)calendar.clone();
        this.cEnd = (Calendar)calendar.clone();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, listenerSD, year, month, day);
        final DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, listenerED, year, month, day);

        textViewSD = (TextView) findViewById(R.id.startDate);
        textViewSD.setText(year + "년 " + (month + 1) + "월 " + day + "일");
        textViewED = (TextView) findViewById(R.id.endDate);
        textViewED.setText(year + "년 " + (month + 1) + "월 " + day + "일");

        textViewSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        textViewED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog2.show();
            }
        });

        Intent intent = getIntent();
        final double latitude = intent.getDoubleExtra("latitude", -1);
        final double longitude = intent.getDoubleExtra("longitude", -1);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("https://www.expedia.co.kr/Hotel-Search?&startDate="+cStart.get(Calendar.YEAR)+"."+(cStart.get(Calendar.MONTH)+1)+"."+cStart.get(Calendar.DAY_OF_MONTH)+"&endDate="+cEnd.get(Calendar.YEAR)+"."+(cEnd.get(Calendar.MONTH)+1)+"."+cEnd.get(Calendar.DAY_OF_MONTH)+"&adults="+peopleNum.getText().toString()+"&regionId=0&latLong="+latitude+","+longitude+"&sort=guestRating");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.expedia.co.kr/Hotel-Search?&startDate="+cStart.get(Calendar.YEAR)+"."+(cStart.get(Calendar.MONTH)+1)+"."+cStart.get(Calendar.DAY_OF_MONTH)+"&endDate="+cEnd.get(Calendar.YEAR)+"."+(cEnd.get(Calendar.MONTH)+1)+"."+cEnd.get(Calendar.DAY_OF_MONTH)+"&adults="+peopleNum.getText().toString()+"&regionId=0&latLong="+latitude+","+longitude+"&sort=guestRating"));

                startActivity(intent);

            }
    });
    }

    private DatePickerDialog.OnDateSetListener listenerSD = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView textView = (TextView)findViewById(R.id.startDate);
            textView.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
            HotelActivity.this.cStart.set(Calendar.MONTH, monthOfYear);
            HotelActivity.this.cStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }

    };

    private DatePickerDialog.OnDateSetListener listenerED = new DatePickerDialog.OnDateSetListener() {

        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TextView textView = (TextView)findViewById(R.id.endDate);
            textView.setText(year + "년 " + (monthOfYear + 1) + "월 " + dayOfMonth + "일");
            HotelActivity.this.cEnd.set(Calendar.MONTH, monthOfYear);
            HotelActivity.this.cEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }

    };







}
