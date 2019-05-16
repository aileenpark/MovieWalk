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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class ListAdapter extends BaseAdapter {

    private Context context;
    //원본 데이터 리스트
    private List<Data> dataList;
    //검색 때 사용할 리스트(글만 불러오기)
    private Activity parentActivity;//나중에 북마크 때 사용
    private List<Data> saveList;


    Bitmap bitmap1;
    Bitmap bitmap2;

    public ListAdapter(Context context, List<Data> dataList, Activity parentActivity, List<Data> saveList) {
        this.context = context;
        this.dataList = dataList;
        this.parentActivity = parentActivity;
        this.saveList = saveList;
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
        View v = View.inflate(context, R.layout.user, null);


        TextView NAME = (TextView) v.findViewById(R.id.NAME);
        TextView TITLE = (TextView) v.findViewById(R.id.TITLE);
        TextView INFORMATION = (TextView) v.findViewById(R.id.INFORMATION);
        TextView infoURL = (TextView) v.findViewById(R.id.infoURL);


        //이미지 출력
        ImageView IMAGEVIEW1 = (ImageView) v.findViewById(R.id.ImageView1);
        ImageView IMAGEVIEW2 = (ImageView) v.findViewById(R.id.ImageView2);

        //지도 이동 버튼
        Button mapButton = (Button) v.findViewById(R.id.mapBtn);

        //북마크버튼
        final Button BookmarkButton = (Button) v.findViewById(R.id.Bookmark);

        final String id = dataList.get(position).getMember_id();
        final String title = dataList.get(position).getMember_title();


        //이미지 주소 저장
        final String ImageV1 = dataList.get(position).getMember_image1();
        final String ImageV2 = dataList.get(position).getMember_image2();

        //위도 경도 주소 string->double로 저장
        final Double latitude = Double.parseDouble(dataList.get(position).getMember_latitude());
        final Double longitude = Double.parseDouble(dataList.get(position).getMember_longitude());
        final String address = (dataList.get(position).getMember_address());
        final String name = (dataList.get(position).getMember_name());
        final String information = (dataList.get(position).getMember_information());


        //map버튼 클릭시 mapactivity로 이동
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MapActivity.class);
                //Bundle bundle = new Bundle();

                //bundle.putDouble("edittext",edittext.getText().toString());
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("address", address);
                intent.putExtra("name", name);
                intent.putExtra("information", information);
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        //북마크
        BookmarkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BookmarkRequest bookmarkRequest = new BookmarkRequest(Integer.parseInt(dataList.get(position).getMember_id()), name, title, dataList.get(position).getMember_latitude(), dataList.get(position).getMember_longitude(), address, ImageV1, ImageV2, information,dataList.get(position).getMember_infoURL(),null);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(bookmarkRequest);
            }
        });



        //출력
        NAME.setText(dataList.get(position).getMember_name());
        TITLE.setText(dataList.get(position).getMember_title());
        INFORMATION.setText(dataList.get(position).getMember_information());
        infoURL.setText(dataList.get(position).getMember_infoURL());


        //이미지1 출력
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(ImageV1);

                    HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                    conn1.setDoInput(true);
                    conn1.connect();

                    InputStream is1 = conn1.getInputStream();
                    bitmap1 = BitmapFactory.decodeStream(is1);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    ;
                }

            }
        };

        mThread.start();

        try {
            mThread.join();
            IMAGEVIEW1.setImageBitmap(bitmap1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //이미지2 출력
        Thread mThread1 = new Thread() {
            @Override
            public void run() {
                try {
                    URL url2 = new URL(ImageV2);

                    HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
                    conn2.setDoInput(true);
                    conn2.connect();

                    InputStream is2 = conn2.getInputStream();
                    bitmap2 = BitmapFactory.decodeStream(is2);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        mThread1.start();

        try {
            mThread1.join();
            IMAGEVIEW2.setImageBitmap(bitmap2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //끝
        v.setTag(dataList.get(position).getMember_id());
        return v;
    }

    //검색기능(제목, 정보, 주소로 해놓음 일단)
    public void searchData(String search) {
        search = search.toLowerCase(Locale.getDefault());
        dataList.clear();

        if (search.length() == 0) {
            dataList.addAll(saveList);
        } else {
            for (int i = 0; i < saveList.size(); i++) {
                if (saveList.get(i).getMember_title().contains(search)) {
                    dataList.add(saveList.get(i));
                }
                if (saveList.get(i).getMember_information().contains(search)) {
                    dataList.add(saveList.get(i));
                }
                if (saveList.get(i).getMember_address().contains(search)) {
                    dataList.add(saveList.get(i));
                }
            }
        }

        notifyDataSetChanged();
    }



}
