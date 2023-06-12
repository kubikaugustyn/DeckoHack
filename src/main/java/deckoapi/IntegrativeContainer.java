package deckoapi;

import deckoapi.amf.amf;
import deckoapi.amf.message.VoidFunc;
import deckoapi.amf.message.VoidFuncBlank;
import deckoapi.jshelper.XMLHttpRequest;
import gui.DeckoHack;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

// From: https://decko.ceskatelevize.cz/cms/icontainerjs/js/IntegrativeContainerJS.min.js?v=1.0.0.287
// Just part
public class IntegrativeContainer {
    public String url;
    public DpCont dpCont;
    public AmfConnector amfConnector;
    public JSONObject appRequestJSON;

    public IntegrativeContainer() {
        dpCont = new DpCont();
        amfConnector = new AmfConnector();
    }

    public void sendAppRequest(String a, String b, JSONObject c, String d, VoidFuncBlank e) {
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
            appRequestJSON = response;
            startIc(response.getJSONObject("flashvars"));
            if (e != null) e.get();
        }, con -> {
            // Injected code...
            XMLHttpRequest<IntegrativeContainer> request = new XMLHttpRequest<>();
            request.open("", "", false);
            DeckoHack.instance.setRequestHeaders(request, true);
            for (String key : request.getRequestHeaders().keySet())
                con.setRequestProperty(key, request.getRequestHeaders().get(key));
        });
    }

    public void startIc(JSONObject a) {
        // System.out.println("StartIC: " + a.toString());
        amfConnector.init(a.getString("amfUrl"), a.getString("token"));
        // AmfConnector.init("https://webhook.site/6aec3577-ac8f-446a-99d3-bcf5cff5621b/", a.getString("token"));
        amfConnector.getAppConfig(this::getAppCongifCallback);
    }

    public DpCont.ContManager getAppCongifCallback(AmfConnectorResult a) {
        // System.out.println("getAppCongifCallback: " + a);
        if (dpCont.dpCont != null) return dpCont.dpCont;
        if ("OK".equals(a.status)) {
            // System.out.println("Got app config: " + ((AmfConnectorResult.AppConfigResult) a).config.toString());
            return dpCont.dpCont = new DpCont.ContManager(dpCont, ((AmfConnectorResult.AppConfigResult) a).config);
        }// else System.out.println("Failed app config: " + a.status);
        return null;
    }
}
