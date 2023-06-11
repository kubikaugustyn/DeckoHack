package deckoapi.amf.message;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class MessageBody {
    public String targetURI;
    public String responseURI;
    public Vector<MessageBodyDataItem<?>> data;

    public MessageBody() {
        this.targetURI = "null";
        this.responseURI = "/1";
        this.data = new Vector<>();
    }

    public JSONObject dataJSON() {
        System.out.println("dataJSONArray: " + dataJSONArray().toString());
        return dataJSONArray().getJSONObject(0);
    }

    public JSONArray dataJSONArray() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < data.size(); i++) array.put(i, data.get(i).item);
        return array;
    }
}
