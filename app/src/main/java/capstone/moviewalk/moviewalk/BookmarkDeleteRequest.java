package capstone.moviewalk.moviewalk;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BookmarkDeleteRequest extends StringRequest {

    final static private String URL = "http://keeka2.cafe24.com/BookmarkDelete.php";
    private Map<String, String> parameters;

    public BookmarkDeleteRequest(int id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("id",id+"");
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
