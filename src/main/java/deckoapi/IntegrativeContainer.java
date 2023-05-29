package deckoapi;

import deckoapi.amf.amf;
import deckoapi.amf.message.VoidFunc;
import deckoapi.amf.message.VoidFuncBlank;
import deckoapi.jshelper.XMLHttpRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Objects;

import static deckoapi.DpCont.dpCont;

// From: https://decko.ceskatelevize.cz/cms/icontainerjs/js/IntegrativeContainerJS.min.js?v=1.0.0.287
// Just part
public class IntegrativeContainer {
    public static String url;

    public static void sendAppRequest(String a, String b, JSONObject c, String d, VoidFuncBlank e) {
        if (!b.startsWith("https://")) b = "https://decko.ceskatelevize.cz" + b;
        url = b;
        if (d != null) url += "?" + d;
        if (c == null) c = new JSONObject();
        c.put("app", a);
        StringBuilder targetUrl = new StringBuilder(url);
        for (Iterator<String> it = c.keys(); it.hasNext(); ) {
            String key = it.next();
            targetUrl.append(url.contains("?") ? "&" : "?");
            targetUrl.append(key).append("=").append(c.get(key).toString());
        }
        System.out.println("AppRequest to: " + targetUrl);
        XMLHttpRequest.getJSON(targetUrl.toString(), 10_000, ex -> {
            System.err.println("FlashAppError:");
            ex.printStackTrace();
        }, response -> {
            startIc(response.getJSONObject("flashvars"));
            if (e != null) e.get();
        });
    }

    public static void startIc(JSONObject a) {
        System.out.println("StartIC: " + a.toString());
        AmfConnector.init(a.getString("amfUrl"), a.getString("token"));
        AmfConnector.getAppConfig(IntegrativeContainer::getAppCongifCallback);
    }

    public static DpCont.ContManager getAppCongifCallback(Object a) {
        if (dpCont != null) return dpCont;
        if ("OK".equals(((JSONObject) a).getString("status")))
            return dpCont = new DpCont.ContManager(((JSONObject) a).getJSONObject("config"));
        return null;
    }
}
