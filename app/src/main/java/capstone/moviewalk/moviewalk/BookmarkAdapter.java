package capstone.moviewalk.moviewalk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends BaseAdapter {

    private Context context;
    private List<Data> BookmarkList;
    private List<Data> TempB=new ArrayList<Data>();

    public BookmarkAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.BookmarkList = dataList;

    }
    @Override
    public int getCount() {
        return BookmarkList.size();
    }

    @Override
    public Object getItem(int position) {
        return BookmarkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.bookmark_data, null);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView information = (TextView) v.findViewById(R.id.INFORMATION);
        TextView title = (TextView) v.findViewById(R.id.TITLE);
        TextView infoURL = (TextView) v.findViewById(R.id.infoURL);
        Button Delete = (Button)v.findViewById(R.id.Delete);
        final int pos = position;
        final Context context = parent.getContext();


        name.setText(BookmarkList.get(position).getMember_name());
        title.setText(BookmarkList.get(position).getMember_title());
        information.setText(BookmarkList.get(position).getMember_information());
        infoURL.setText(BookmarkList.get(position).getMember_infoURL());
        System.out.println(position);

        Delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BookmarkDeleteRequest BookmarkDeleteRequest = new BookmarkDeleteRequest(Integer.parseInt(BookmarkList.get(position).getMember_id()),null);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(BookmarkDeleteRequest);
            }
        });


        v.setTag(BookmarkList.get(position).getMember_id());
        return v;
    }
}
