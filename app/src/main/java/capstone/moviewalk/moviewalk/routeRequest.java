package capstone.moviewalk.moviewalk;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class routeRequest extends StringRequest {

    final static private String URL = "http://keeka2.cafe24.com/route.php";
    private Map<String, String> parameters;

    public routeRequest(String latitude, String longitude,String ord, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();

        parameters.put("latitude",latitude);
        parameters.put("longitude",longitude);
        parameters.put("ord",ord);

    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }

}
