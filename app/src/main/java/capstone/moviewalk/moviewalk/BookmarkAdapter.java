package capstone.moviewalk.moviewalk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class BookmarkAdapter extends BaseAdapter {

    private Context context;
    private List<Data> BookmarkList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.bookmark_data, null);
        TextView name = (TextView) v.findViewById(R.id.name);
        TextView information = (TextView) v.findViewById(R.id.INFORMATION);
        TextView title = (TextView) v.findViewById(R.id.TITLE);
        TextView infoURL = (TextView) v.findViewById(R.id.infoURL);


        name.setText(BookmarkList.get(position).getMember_name());
        title.setText(BookmarkList.get(position).getMember_title());
        information.setText(BookmarkList.get(position).getMember_information());
        infoURL.setText(BookmarkList.get(position).getMember_infoURL());


        v.setTag(BookmarkList.get(position).getMember_id());
        return v;
    }
}
