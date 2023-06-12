package deckoapi;

import deckoapi.AmfConnectorResult;
import deckoapi.amf.amf;
import deckoapi.amf.message.Func;
import deckoapi.amf.message.VoidFunc;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

// From https://decko.ceskatelevize.cz/cms/common/js/this.min.js
public class AmfConnector {
    String VERSION = "1.0.0";
    String gatewayURL = "";
    String token = "";
    // My own
    static amf amf = new amf();

    public void init(String gatewayURL, String token) {
        this.gatewayURL = gatewayURL;
        this.token = token;
    }

    public void getAppConfig(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(this.GetAppConfigResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(this.ErrorResult(tmp));
        String d = "AppConfig/amf";
        String e = "getAppConfig";
        JSONObject f = new JSONObject();
        f.put("token", this.token);


        // AMFConnection amf = new AMFConnection();
        // System.out.println("amf.call: " + this.gatewayURL + d);
        amf.call(this.gatewayURL + d, e, f, b, c);
    }

    public void getTokenStatus(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(this.GetTokenStatusResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(this.ErrorResult(tmp));
        String d = "Token/amf";
        String e = "getStatus";
        JSONObject f = new JSONObject();
        f.put("token", this.token);


        amf.call(this.gatewayURL + d, e, f, b, c);
    }

    public void killToken(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(this.KillTokenResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(this.ErrorResult(tmp));
        String d = "Token/amf";
        String e = "killToken";
        JSONObject f = new JSONObject();
        f.put("token", this.token);


        amf.call(this.gatewayURL + d, e, f, b, c);
    }

    public void getDate(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(this.GetDateResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(this.ErrorResult(tmp));
        String d = "ServerStatus/amf";
        String e = "getServerStatus";
        JSONObject f = new JSONObject();
        f.put("token", this.token);


        amf.call(this.gatewayURL + d, e, f, b, c);
    }

    public void getAppStates(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(this.GetAppStatesResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(this.ErrorResult(tmp));
        String d = "AppState/amf";
        String e = "getAppStates";
        JSONObject f = new JSONObject();
        f.put("token", this.token);


        amf.call(this.gatewayURL + d, e, f, b, c);
    }

    public void saveAppState(AmfConnectorCallback a, int b, String c, Object d) {
        VoidFunc<Object> e = tmp -> a.callback(this.SaveAppStateResult(tmp));
        VoidFunc<Object> f = tmp -> a.callback(this.ErrorResult(tmp));
        String g = "AppState/amf";
        String h = "saveAppState";
        JSONObject i = new JSONObject();
        i.put("token", this.token);
        i.put("slotNumber", b);
        i.put("metaData", c);
        i.put("data", d);


        amf.call(this.gatewayURL + g, h, i, e, f);
    }

    public void loadAppState(AmfConnectorCallback a, int b, String c, Object d) {
        VoidFunc<Object> e = tmp -> a.callback(this.LoadAppStateResult(tmp, d));
        VoidFunc<Object> f = tmp -> a.callback(this.ErrorResult(tmp));
        String g = "AppState/amf";
        String h = "loadAppState";
        JSONObject i = new JSONObject();
        i.put("token", this.token);
        i.put("slotNumber", b);
        i.put("resourceName", c);


        amf.call(this.gatewayURL + g, h, i, e, f);
    }

    public void removeAppState(AmfConnectorCallback a, int b) {
        VoidFunc<Object> c = tmp -> a.callback(this.RemoveAppStateResult(tmp));
        VoidFunc<Object> d = tmp -> a.callback(this.ErrorResult(tmp));
        String e = "AppState/amf";
        String f = "removeAppState";
        JSONObject g = new JSONObject();
        g.put("token", this.token);
        g.put("slotNumber", b);


        amf.call(this.gatewayURL + e, f, g, c, d);
    }

    public void saveRank(AmfConnectorCallback a, int b, int c, int d, int e, boolean f, String g) {
        VoidFunc<Object> h = tmp -> a.callback(this.SaveRankResult(tmp));
        VoidFunc<Object> i = tmp -> a.callback(this.ErrorResult(tmp));
        String j = "Ranking/amf";
        String k = "saveRank";
        JSONObject l = new JSONObject();
        l.put("token", this.token);
        l.put("score", b);
        l.put("slotNumber", c);
        l.put("time", d);
        l.put("level", e);
        l.put("finished", f);
        l.put("answer", g);


        amf.call(this.gatewayURL + j, k, l, h, i);
    }

    public void getRanking(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(this.GetRankingResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(this.ErrorResult(tmp));
        String d = "Ranking/amf";
        String e = "getRanking";
        JSONObject f = new JSONObject();
        f.put("token", this.token);


        amf.call(this.gatewayURL + d, e, f, b, c);
    }

    public void sendMessage(AmfConnectorCallback a, String b, Object c, String d, String e, String f, String g, String h) {
        VoidFunc<Object> i = tmp -> a.callback(this.SendMessageResult(tmp));
        VoidFunc<Object> j = tmp -> a.callback(this.ErrorResult(tmp));
        String k = "Message/amf";
        String l = "sendMessage";
        JSONObject m = new JSONObject();
        m.put("token", this.token);
        m.put("messageTypeUiud", b);
        m.put("params", c);
        m.put("receiver", d);
        m.put("subject", e);
        m.put("text", f);
        m.put("html", g);
        m.put("replyTo", h);


        amf.call(this.gatewayURL + k, l, m, i, j);
    }

    public void updateProfile(AmfConnectorCallback a, int b, String c, String d) {
        VoidFunc<Object> e = tmp -> a.callback(this.UpdateProfileResult(tmp));
        VoidFunc<Object> f = tmp -> a.callback(this.ErrorResult(tmp));
        String g = "Profile/amf";
        String h = "updateProfile";
        JSONObject i = new JSONObject();
        i.put("token", this.token);
        i.put("slotNumber", b);
        i.put("resourceName", c);
        i.put("gender", d);


        amf.call(this.gatewayURL + g, h, i, e, f);
    }

    AmfConnectorResult ErrorResult(Object a) {
        AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
        b.status = "ERROR";
        b.error = "amf status error";
        b.errorObject = a;
        return b;
    }

    AmfConnectorResult GetAppConfigResult(Object a) {
        if (Objects.equals(((JSONArray) a).getJSONObject(0).optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.AppConfigResult b = new AmfConnectorResult.AppConfigResult();
        b.status = "OK";
        b.config = ((JSONArray) a).getJSONObject(0);
        return b;
    }

    AmfConnectorResult GetAppStatesResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.AppStatesResult b = new AmfConnectorResult.AppStatesResult();
        b.status = "OK";
        JSONArray d = c.getJSONArray("slots");
        b.slots = new AmfConnectorResult.AppStatesResult.Slot[d.length()];
        for (int i = 0; i < d.length(); i++) {
            AmfConnectorResult.AppStatesResult.Slot slot = new AmfConnectorResult.AppStatesResult.Slot();
            JSONObject slotObj = d.getJSONObject(i);
            slot.slotNumber = slotObj.getInt("slotNumber");
            slot.metadata = slotObj.getString("metadata");
            b.slots[i] = slot;
        }
        return b;
    }

    AmfConnectorResult GetDateResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.DateResult b = new AmfConnectorResult.DateResult();
        b.status = "OK";
        b.date = new Date(c.getLong("now"));
        return b;
    }

    AmfConnectorResult GetRankingResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.RankingResult b = new AmfConnectorResult.RankingResult();
        b.status = "OK";
        b.playerBest = c.getJSONObject("playerBest");
        b.topList = c.getJSONObject("topList");
        return b;
    }

    AmfConnectorResult GetTokenStatusResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("tokenStatus"), "dead")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.TokenStatusResult b = new AmfConnectorResult.TokenStatusResult();
        b.status = "OK";
        return b;
    }

    AmfConnectorResult KillTokenResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("tokenStatus"), "noaction")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.KillTokenResult b = new AmfConnectorResult.KillTokenResult();
        b.status = "OK";
        return b;
    }

    AmfConnectorResult LoadAppStateResult(Object a, Object b) {
        //for (var b = "", c = 0; c < a.length; c++)
        //            b += String.fromCharCode(a[c]);
        //        return b
        //int[] h = new int[arr.length];
        //for (int i = 0; i < arr.length; i++) h[i] = arr[i] & 0xFF;
        Func<byte[], String> e = arr -> new String(arr, StandardCharsets.UTF_8);
        // return decodeURIComponent(escape(e(a)))
        //return URLDecoder.decode(URLEncoder.encode(e.get(arr), StandardCharsets.US_ASCII).replace("+", "%2B"), StandardCharsets.UTF_8)
        //        .replace("%2B", "+");
        Func<byte[], String> c = e::get;
        Func<byte[], String> d = arr -> {
            // var b = e(a)
            //          , c = btoa(b);
            //        return "data:image/png;base64," + c
            // String h = e.get(arr);
            //byte[] h = new byte[arr.length];
            //for (int i = 0; i < arr.length; i++) h[i] = arr[i];
            String i = Base64.getEncoder().encodeToString(arr);
            return "data:image/png;base64," + i;
        };
        JSONObject g = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(g.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult f = new AmfConnectorResult.ErrorResult();
            f.status = "ERROR";
            f.error = "wrong resource name, or invalid token";
            return f;
        }
        if (!g.has("state")) { // Injected code...
            AmfConnectorResult.ErrorResult f = new AmfConnectorResult.ErrorResult();
            f.status = "ERROR";
            f.error = "Response doesn't have state!";
            return f;
        }
        AmfConnectorResult.LoadAppStateResult f = new AmfConnectorResult.LoadAppStateResult();
        f.status = "OK";
        f.resourceType = (String) b;
        switch ((String) b) {
            case "text" -> f.state = c.get((byte[]) g.get("state"));
            case "image" -> f.image = d.get((byte[]) g.get("state"));
        }
        return f;
    }

    AmfConnectorResult RemoveAppStateResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "slot does not exist, or invalid token";
            return b;
        }
        AmfConnectorResult.RemoveAppStateResult b = new AmfConnectorResult.RemoveAppStateResult();
        b.status = "OK";
        b.date = new Date(c.getLong("now"));
        return b;
    }

    AmfConnectorResult SaveAppStateResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.SaveAppStateResult b = new AmfConnectorResult.SaveAppStateResult();
        b.status = "OK";
        return b;
    }

    AmfConnectorResult SaveRankResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "slot does not exist, or invalid token";
            return b;
        }
        AmfConnectorResult.RemoveAppStateResult b = new AmfConnectorResult.RemoveAppStateResult();
        b.status = "OK";
        b.date = new Date(c.getLong("now"));
        return b;
    }

    AmfConnectorResult SendMessageResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "wrong message UUID, format, parameters, or invalid token";
            return b;
        }
        AmfConnectorResult.SendMessageResult b = new AmfConnectorResult.SendMessageResult();
        b.status = "OK";
        b.code = c.getString("code");
        b.subcode = c.getString("subcode");
        b.message = c.getString("message");
        b.detail = c.getString("detail");
        return b;
    }

    AmfConnectorResult UpdateProfileResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.optString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.UpdateProfileResult b = new AmfConnectorResult.UpdateProfileResult();
        b.status = "OK";
        return b;
    }

    public String getToken() {
        return token;
    }
}
