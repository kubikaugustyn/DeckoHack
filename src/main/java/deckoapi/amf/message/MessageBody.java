package deckoapi.amf.message;

import org.json.JSONObject;

import java.util.Vector;

public class MessageBody {
    public String targetURI;
    public String responseURI;
    public Vector<MessageBodyDataItem<?>> data;
    public JSONObject dataJSON;

    public MessageBody() {
        this.targetURI = "null";
        this.responseURI = "/1";
        this.data = new Vector<>();
        this.dataJSON = new JSONObject();
    }
}
