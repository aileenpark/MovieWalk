package capstone.moviewalk.moviewalk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context context;
    //원본 데이터 리스트
    private List<Data> dataList;
    //검색 때 사용할 리스트(글만 불러오기)
    private Activity parentActivity;
    private List<Data> saveList;



    Bitmap bitmap1;
    Bitmap bitmap2;

    public ListAdapter(Context context, List<Data> dataList, Activity parentActivity, List<Data> saveList){
        this.context = context;
        this.dataList = dataList;
        this.parentActivity = parentActivity;
        this.saveList= saveList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }


    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //한 리스트 안에 들어가는거(user.xml)
        View v = View.inflate(context,R.layout.user,null);


        TextView NAME = (TextView) v.findViewById(R.id.NAME);
        TextView TITLE = (TextView) v.findViewById(R.id.TITLE);
        TextView INFORMATION = (TextView) v.findViewById(R.id.INFORMATION);

        //이미지 출력
        ImageView IMAGEVIEW1 = (ImageView) v.findViewById(R.id.ImageView1);
        ImageView IMAGEVIEW2 = (ImageView) v.findViewById(R.id.ImageView2);

        //지도 이동 버튼
        Button mapButton = (Button) v.findViewById(R.id.mapBtn);


        //이미지 주소 저장
        final String ImageV1 = dataList.get(position).getMember_image1();
        final String ImageV2 = dataList.get(position).getMember_image2();

        //위도 경도 주소 string->double로 저장
        final Double latitude =Double.parseDouble(dataList.get(position).getMember_latitude());
        final Double longitude =Double.parseDouble(dataList.get(position).getMember_longitude());


        //map버튼 클릭시 mapactivity로 이동
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MapActivity.class);
                //Bundle bundle = new Bundle();

                //bundle.putDouble("edittext",edittext.getText().toString());
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                context.startActivity(intent);
            }
        });


        //출력
        NAME.setText(dataList.get(position).getMember_name());
        TITLE.setText(dataList.get(position).getMember_title());
        INFORMATION.setText(dataList.get(position).getMember_information());

        //이미지1 출력
        Thread mThread = new Thread(){
            @Override
            public void run(){
                try{
                    URL url = new URL(ImageV1);

                    HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                    conn1.setDoInput(true);
                    conn1.connect();

                    InputStream is1 = conn1.getInputStream();
                    bitmap1 = BitmapFactory.decodeStream(is1);


                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();;
                }

            }
        };

        mThread.start();

        try{
            mThread.join();
            IMAGEVIEW1.setImageBitmap(bitmap1);

        } catch (InterruptedException e){
            e.printStackTrace();
        }

        //이미지2 출력
        Thread mThread1 = new Thread(){
            @Override
            public void run(){
                try{
                    URL url2 = new URL(ImageV2);

                    HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                    conn2.setDoInput(true);
                    conn2.connect();

                    InputStream is2 = conn2.getInputStream();
                    bitmap2 = BitmapFactory.decodeStream(is2);

                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        };

        mThread1.start();

        try{
            mThread1.join();
            IMAGEVIEW2.setImageBitmap(bitmap2);

        } catch (InterruptedException e){
            e.printStackTrace();
        }




        //끝
        v.setTag(dataList.get(position).getMember_id());
        return v;
    }


}

