package deckoapi.jshelper;

import deckoapi.amf.message.VoidFunc;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XMLHttpRequest<T> {
    public T parent;
    public boolean busy = false;
    public XMLHttpRequestOnreadystatechangeListener<T> onreadystatechange;
    public byte[] message;
    public int readyState;
    public static int UNSENT = 0;
    public static int OPENED = 1;
    public static int HEADERS_RECEIVED = 2;
    public static int LOADING = 3;
    public static int DONE = 4;
    public String responseType;
    public String responseText;
    public byte[] response;
    public int status;
    public String method;
    public String url;
    public boolean async;

    public void setRequestHeader(String name, String value) {

    }

    public void send(byte[] body) {
        System.out.println("Send " + method + " XMLHttpRequest to " + url);
    }

    public void send() {
        this.send(new byte[0]);
    }

    public String getResponseHeader(String name) {
        return "";
    }

    public void open(String method, String url, boolean async) {
        this.method = method;
        this.url = url;
        this.async = async;
        this.readyState = OPENED;
        this.onreadystatechange.onreadystatechange(this);
    }

    public static void getJSON(String url, int timeout, VoidFunc<Exception> error, VoidFunc<JSONObject> success) {
        try {
            URL u = new URL(url);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200, 201 -> {
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                    success.get(new JSONObject(sb.toString()));
                }
            }
        } catch (Exception ex) {
            error.get(ex);
        }
    }
}
