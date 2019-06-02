package capstone.moviewalk.moviewalk;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class HotelActivity extends AppCompatActivity implements View.OnClickListener {

    Calendar dateAndTime = Calendar.getInstance();
    Calendar dateAndTime2 = Calendar.getInstance();

    TextView tvDateAndTime;
    TextView tvDateAndTime2;
    Button btnDateSelect;
    Button btnDateSelect2;

    TextView people;
    Button conn;

    DatePickerDialog.OnDateSetListener datepickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateAndTime.set(Calendar.YEAR, year);
                    dateAndTime.set(Calendar.MONTH, month);
                    dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateAndTime();
                }
            };

    DatePickerDialog.OnDateSetListener datepickerListener2 =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateAndTime2.set(Calendar.YEAR, year);
                    dateAndTime2.set(Calendar.MONTH, month);
                    dateAndTime2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateAndTime();
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        tvDateAndTime = (TextView) findViewById(R.id.tvText2);
        tvDateAndTime2 = (TextView) findViewById(R.id.tvText);

        btnDateSelect = (Button) findViewById(R.id.btnDateSelect);
        btnDateSelect.setOnClickListener(this);

        btnDateSelect2 = (Button) findViewById(R.id.btnDateSelect2);
        btnDateSelect2.setOnClickListener(this);

        conn = (Button) findViewById(R.id.conn);
        people = (TextView) findViewById(R.id.people);

        updateDateAndTime();


        Intent intent = getIntent();
        final double latitude = intent.getDoubleExtra("latitude", -1);
        final double longitude = intent.getDoubleExtra("longitude", -1);


        conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("https://www.expedia.co.kr/Hotel-Search?&startDate="+dateAndTime.get(Calendar.YEAR)+"."+(dateAndTime.get(Calendar.MONTH)+1)+"."+dateAndTime.get(Calendar.DAY_OF_MONTH)+"&endDate="+dateAndTime2.get(Calendar.YEAR)+"."+(dateAndTime2.get(Calendar.MONTH)+1)+"."+dateAndTime2.get(Calendar.DAY_OF_MONTH)+"&adults="+people.getText().toString()+"&regionId=0&latLong="+latitude+","+longitude+"&sort=guestRating");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.expedia.co.kr/Hotel-Search?&startDate="+dateAndTime.get(Calendar.YEAR)+"."+(dateAndTime.get(Calendar.MONTH)+1)+"."+dateAndTime.get(Calendar.DAY_OF_MONTH)+"&endDate="+dateAndTime2.get(Calendar.YEAR)+"."+(dateAndTime2.get(Calendar.MONTH)+1)+"."+dateAndTime2.get(Calendar.DAY_OF_MONTH)+"&adults="+people.getText().toString()+"&regionId=0&latLong="+latitude+","+longitude+"&sort=guestRating"));

                startActivity(intent);

            }
        });
    }

    private void updateDateAndTime() {

        tvDateAndTime.setText(String.format("%d년 %d월 %d일",dateAndTime.get(Calendar.YEAR),dateAndTime.get(Calendar.MONTH)+1,dateAndTime.get(Calendar.DAY_OF_MONTH)));
        tvDateAndTime2.setText(String.format("%d년 %d월 %d일",dateAndTime2.get(Calendar.YEAR),dateAndTime2.get(Calendar.MONTH)+1,dateAndTime2.get(Calendar.DAY_OF_MONTH)));
        people.setText(String.format(people.getText().toString()));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDateSelect) {

            new DatePickerDialog(this, datepickerListener,

                    dateAndTime.get(Calendar.YEAR),

                    dateAndTime.get(Calendar.MONTH),

                    //show()를 호출해야 메모리상에 있는 datepicker객체를 보여줄 수 있다.

                    dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
        }
        if (v.getId() == R.id.btnDateSelect2) {

            new DatePickerDialog(this, datepickerListener2,

                    dateAndTime2.get(Calendar.YEAR),

                    dateAndTime2.get(Calendar.MONTH),

                    //show()를 호출해야 메모리상에 있는 datepicker객체를 보여줄 수 있다.

                    dateAndTime2.get(Calendar.DAY_OF_MONTH)).show();
        }

    }


}
