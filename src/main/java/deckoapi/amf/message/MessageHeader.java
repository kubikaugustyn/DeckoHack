package deckoapi.amf.message;

import deckoapi.amf.Header;


public class MessageHeader extends Header {
    public String name;
    public boolean mustUnderstand;
    public Object data;

    public MessageHeader() {
        this.name = "";
        this.mustUnderstand = false;
        this.data = null;
    }

    public MessageHeader(String name, boolean mustUnderstand, Object data) {
        this.name = name;
        this.mustUnderstand = mustUnderstand;
        this.data = data;
    }
}
