package capstone.moviewalk.moviewalk;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
//123
public class BookmarkDeleteAll extends StringRequest {

    final static private String URL = "http://keeka2.cafe24.com/BookmarkDeleteAll.php";
    private Map<String, String> parameters;

    public BookmarkDeleteAll(Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();

    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}