package deckoapi.amf.message;


import java.util.Vector;

public class Message {
    public String _explicitType;
    public short version;
    public Vector<MessageHeader> headers;
    public Vector<MessageBody> bodies;
    public Vector<MessageBody> body;
    public String destination;
    public String source;
    public Object operation;
    public String clientId;
    public String messageId;

    Message() {
        this.headers = new Vector<>();
        this.bodies = new Vector<>();
        this.body = new Vector<>();
    }
}
