package deckoapi.jshelper;

import deckoapi.amf.message.VoidFunc;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class XMLHttpRequest<T> {
    public T parent;
    public boolean busy = false, allowRedirects = true;
    public XMLHttpRequestOnreadystatechangeListener<T> onreadystatechange;
    public XMLHttpRequestOnerrorListener<T> onerror;
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
    public int timeout = 0;
    private HashMap<String, String> requestHeaders;
    private HashMap<String, List<String>> responseHeadersReal;
    private HashMap<String, String> responseHeaders;

    public XMLHttpRequest() {
        readyState = UNSENT;
    }

    public void setRequestHeader(String name, String value) {
        if (readyState < OPENED) return;
        requestHeaders.put(name, value);
    }

    public HashMap<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void send(byte[] body) {
        if (body == null) body = new byte[0];
        // System.out.println("Send " + method + " XMLHttpRequest to " + url);
        try {
            URL u = new URL(url);
            boolean isHttps = Objects.equals(u.getProtocol(), "https");
            if (!isHttps) System.err.println("Probably will crash with protocol: " + u.getProtocol());
            HttpsURLConnection.setFollowRedirects(allowRedirects);
            HttpURLConnection.setFollowRedirects(allowRedirects);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod(method);
            c.setInstanceFollowRedirects(allowRedirects);
            c.setRequestProperty("Content-length", String.valueOf(body.length));
            c.setRequestProperty("Accept", "*/*");
            /*c.setRequestProperty("Origin", "https://decko.ceskatelevize.cz");
            c.setRequestProperty("Referer", "https://decko.ceskatelevize.cz/");
            c.setRequestProperty("accept-language", "en-GB,en;q=0.9,cs-CZ;q=0.8,cs;q=0.7,en-US;q=0.6,ru;q=0.5,be;q=0.4");
            c.setRequestProperty("accept-encoding", "gzip, deflate, br");*/
            c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
            // c.setRequestProperty("Cookie", "JSESSIONID=7AD1731997920E78C7877604325D1951; DPU=f920723b-effa-4bde-b921-5e1c13652194");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setDoInput(true);
            c.setDoOutput(true);
            c.setReadTimeout(timeout);
            /*System.out.println("Body length: " + body.length);
            for (byte b : body) {
                System.out.print(Integer.toHexString(b));
                System.out.print(" ");
            }
            System.out.println();
            for (byte b : body) System.out.print((char) b);
            System.out.println();*/
            for (String key : requestHeaders.keySet()) c.setRequestProperty(key, requestHeaders.get(key));
            c.connect();
            if (body.length > 0) {
                OutputStream os = c.getOutputStream();
                os.write(body);
                os.close();  //don't forget to close the OutputStream
            }
            status = c.getResponseCode();

            this.readyState = HEADERS_RECEIVED;
            if (this.onreadystatechange != null) this.onreadystatechange.onreadystatechange(this);

            InputStream is = status < 400 ? c.getInputStream() : c.getErrorStream();
            this.readyState = LOADING;
            if (this.onreadystatechange != null) this.onreadystatechange.onreadystatechange(this);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            byte[] next = new byte[1024];
            int len;
            while (-1 != (len = is.read(next))) {
                bo.write(next, 0, len);
            }
            response = bo.toByteArray();
            responseText = new String(response, StandardCharsets.UTF_8);
            bo.close();
            is.close();
            Map<String, List<String>> respHeaders = c.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : respHeaders.entrySet())
                if (entry.getKey() != null) {
                    responseHeaders.put(entry.getKey(), entry.getValue().get(0));
                    responseHeadersReal.put(entry.getKey(), entry.getValue());
                    // System.out.println("Key : " + entry.getKey() + " ,Value : " + entry.getValue().get(0));
                }
            c.disconnect();

            this.readyState = DONE;
            if (this.onreadystatechange != null) this.onreadystatechange.onreadystatechange(this);
            // System.out.println("Done " + method + " XMLHttpRequest to " + url);
            HttpURLConnection.setFollowRedirects(true);
            HttpsURLConnection.setFollowRedirects(true);
        } catch (Exception ex) {
            // System.out.println("Error " + method + " XMLHttpRequest to " + url);
            ex.printStackTrace();
            if (this.onerror != null) this.onerror.onerror(ex, this);
        }
    }

    public void send() {
        this.send(new byte[0]);
    }

    public String getResponseHeader(String name) {
        return responseHeaders.get(name);
    }

    public String[] getRealResponseHeader(String name) {
        String[] vals = new String[responseHeadersReal.get(name).size()];
        for (int i = 0; i < vals.length; i++) vals[i] = responseHeadersReal.get(name).get(i);
        return vals;
    }

    public void open(String method, String url, boolean async) {
        this.method = method;
        this.url = url;
        this.async = async;
        requestHeaders = new HashMap<>();
        responseHeaders = new HashMap<>();
        responseHeadersReal = new HashMap<>();
        this.readyState = OPENED;
        if (this.onreadystatechange != null) this.onreadystatechange.onreadystatechange(this);
    }

    public static void getJSON(String url, int timeout, VoidFunc<Exception> error, VoidFunc<JSONObject> success) {
        XMLHttpRequest.getJSON(url, timeout, error, success, c -> {
        });
    }

    public static void getJSON(String url, int timeout, VoidFunc<Exception> error, VoidFunc<JSONObject> success, VoidFunc<HttpsURLConnection> prepare) {
        try {
            URL u = new URL(url);
            HttpsURLConnection c = (HttpsURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            prepare.get(c);
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
