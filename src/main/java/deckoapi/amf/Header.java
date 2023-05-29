package deckoapi.amf;

import java.util.HashMap;

public class Header {
    public HashMap<String, Object> headers;

    public Header() {
        this.headers = new HashMap<>();
    }

    public Header(String name, Object value) {
        this();
        this.setHeader(name, value);
    }

    public Header setHeader(String name, Object value) {
        this.headers.put(name, value);
        return this;
    }
}
