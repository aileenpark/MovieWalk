package capstone.moviewalk.moviewalk;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BookmarkRequest extends StringRequest {
    final static private String URL = "http://keeka2.cafe24.com/Bookmark.php";
    private Map<String, String> parameters;

    public BookmarkRequest(int id, String name, String title, String latitude, String longitude, String address, String image1, String image2, String information, String infoURL, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("id",id+"");
        parameters.put("name",name);
        parameters.put("title",title);
        parameters.put("latitude",latitude);
        parameters.put("longitude",longitude);
        parameters.put("address",address);
        parameters.put("image1",image1);
        parameters.put("image2",image2);
        parameters.put("information",information);
        parameters.put("infoURL", infoURL);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
