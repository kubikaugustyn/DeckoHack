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

// From https://decko.ceskatelevize.cz/cms/common/js/AmfConnector.min.js
public class AmfConnector {
    static String VERSION = "1.0.0";
    static String gatewayURL = "";
    static String token = "";
    // My own
    static amf amf = new amf();

    static void init(String gatewayURL, String token) {
        AmfConnector.gatewayURL = gatewayURL;
        AmfConnector.token = token;
    }

    static void getAppConfig(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(AmfConnector.GetAppConfigResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String d = "AppConfig/amf";
        String e = "getAppConfig";
        JSONObject f = new JSONObject();
        f.put("token", AmfConnector.token);


        // AMFConnection amf = new AMFConnection();
        System.out.println("amf.call: " + AmfConnector.gatewayURL + d);
        amf.call(AmfConnector.gatewayURL + d, e, f, b, c);
    }

    static void getTokenStatus(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(AmfConnector.GetTokenStatusResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String d = "Token/amf";
        String e = "getStatus";
        JSONObject f = new JSONObject();
        f.put("token", AmfConnector.token);


        amf.call(AmfConnector.gatewayURL + d, e, f, b, c);
    }

    static void killToken(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(AmfConnector.KillTokenResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String d = "Token/amf";
        String e = "killToken";
        JSONObject f = new JSONObject();
        f.put("token", AmfConnector.token);


        amf.call(AmfConnector.gatewayURL + d, e, f, b, c);
    }

    static void getDate(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(AmfConnector.GetDateResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String d = "ServerStatus/amf";
        String e = "getServerStatus";
        JSONObject f = new JSONObject();
        f.put("token", AmfConnector.token);


        amf.call(AmfConnector.gatewayURL + d, e, f, b, c);
    }

    static void getAppStates(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(AmfConnector.GetAppStatesResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String d = "AppState/amf";
        String e = "getAppStates";
        JSONObject f = new JSONObject();
        f.put("token", AmfConnector.token);


        amf.call(AmfConnector.gatewayURL + d, e, f, b, c);
    }

    static void saveAppState(AmfConnectorCallback a, int b, String c, Object d) {
        VoidFunc<Object> e = tmp -> a.callback(AmfConnector.SaveAppStateResult(tmp));
        VoidFunc<Object> f = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String g = "AppState/amf";
        String h = "saveAppState";
        JSONObject i = new JSONObject();
        i.put("token", AmfConnector.token);
        i.put("slotNumber", b);
        i.put("metaData", c);
        i.put("data", d);


        amf.call(AmfConnector.gatewayURL + g, h, i, e, f);
    }

    static void loadAppState(AmfConnectorCallback a, int b, String c, Object d) {
        VoidFunc<Object> e = tmp -> a.callback(AmfConnector.LoadAppStateResult(tmp, d));
        VoidFunc<Object> f = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String g = "AppState/amf";
        String h = "loadAppState";
        JSONObject i = new JSONObject();
        i.put("token", AmfConnector.token);
        i.put("slotNumber", b);
        i.put("resourceName", c);


        amf.call(AmfConnector.gatewayURL + g, h, i, e, f);
    }

    static void removeAppState(AmfConnectorCallback a, int b) {
        VoidFunc<Object> c = tmp -> a.callback(AmfConnector.RemoveAppStateResult(tmp));
        VoidFunc<Object> d = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String e = "AppState/amf";
        String f = "removeAppState";
        JSONObject g = new JSONObject();
        g.put("token", AmfConnector.token);
        g.put("slotNumber", b);


        amf.call(AmfConnector.gatewayURL + e, f, g, c, d);
    }

    static void saveRank(AmfConnectorCallback a, int b, int c, int d, int e, boolean f, String g) {
        VoidFunc<Object> h = tmp -> a.callback(AmfConnector.SaveRankResult(tmp));
        VoidFunc<Object> i = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String j = "Ranking/amf";
        String k = "saveRank";
        JSONObject l = new JSONObject();
        l.put("token", AmfConnector.token);
        l.put("score", b);
        l.put("slotNumber", c);
        l.put("time", d);
        l.put("level", e);
        l.put("finished", f);
        l.put("answer", g);


        amf.call(AmfConnector.gatewayURL + j, k, l, h, i);
    }

    static void getRanking(AmfConnectorCallback a) {
        VoidFunc<Object> b = tmp -> a.callback(AmfConnector.GetRankingResult(tmp));
        VoidFunc<Object> c = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String d = "Ranking/amf";
        String e = "getRanking";
        JSONObject f = new JSONObject();
        f.put("token", AmfConnector.token);


        amf.call(AmfConnector.gatewayURL + d, e, f, b, c);
    }

    static void sendMessage(AmfConnectorCallback a, String b, Object c, String d, String e, String f, String g, String h) {
        VoidFunc<Object> i = tmp -> a.callback(AmfConnector.SendMessageResult(tmp));
        VoidFunc<Object> j = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String k = "Message/amf";
        String l = "sendMessage";
        JSONObject m = new JSONObject();
        m.put("token", AmfConnector.token);
        m.put("messageTypeUiud", b);
        m.put("params", c);
        m.put("receiver", d);
        m.put("subject", e);
        m.put("text", f);
        m.put("html", g);
        m.put("replyTo", h);


        amf.call(AmfConnector.gatewayURL + k, l, m, i, j);
    }

    static void updateProfile(AmfConnectorCallback a, int b, String c, String d) {
        VoidFunc<Object> e = tmp -> a.callback(AmfConnector.UpdateProfileResult(tmp));
        VoidFunc<Object> f = tmp -> a.callback(AmfConnector.ErrorResult(tmp));
        String g = "Profile/amf";
        String h = "updateProfile";
        JSONObject i = new JSONObject();
        i.put("token", AmfConnector.token);
        i.put("slotNumber", b);
        i.put("resourceName", c);
        i.put("gender", d);


        amf.call(AmfConnector.gatewayURL + g, h, i, e, f);
    }

    static AmfConnectorResult ErrorResult(Object a) {
        AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
        b.status = "ERROR";
        b.error = "amf status error";
        b.errorObject = a;
        return b;
    }

    static AmfConnectorResult GetAppConfigResult(Object a) {
        if (Objects.equals(((JSONArray) a).getJSONObject(0).getString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.AppConfigResult b = new AmfConnectorResult.AppConfigResult();
        b.status = "OK";
        b.config = a;
        return b;
    }

    static AmfConnectorResult GetAppStatesResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("status"), "ERROR")) {
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

    static AmfConnectorResult GetDateResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("status"), "ERROR")) {
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

    static AmfConnectorResult GetRankingResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("status"), "ERROR")) {
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

    static AmfConnectorResult GetTokenStatusResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("tokenStatus"), "dead")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.TokenStatusResult b = new AmfConnectorResult.TokenStatusResult();
        b.status = "OK";
        return b;
    }

    static AmfConnectorResult KillTokenResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("tokenStatus"), "noaction")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.KillTokenResult b = new AmfConnectorResult.KillTokenResult();
        b.status = "OK";
        return b;
    }

    static AmfConnectorResult LoadAppStateResult(Object a, Object b) {
        Func<JSONArray, String> e = arr -> {
            //for (var b = "", c = 0; c < a.length; c++)
            //            b += String.fromCharCode(a[c]);
            //        return b
            int[] h = new int[arr.length()];
            for (int i = 0; i < arr.length(); i++) h[i] = arr.getInt(i);
            return new String(h, 0, arr.length());
        };
        Func<JSONArray, String> c = arr -> {
            // return decodeURIComponent(escape(e(a)))
            return URLDecoder.decode(URLEncoder.encode(e.get(arr), StandardCharsets.US_ASCII).replace("+", "%2B"), StandardCharsets.UTF_8)
                    .replace("%2B", "+");
        };
        Func<JSONArray, String> d = arr -> {
            // var b = e(a)
            //          , c = btoa(b);
            //        return "data:image/png;base64," + c
            // String h = e.get(arr);
            byte[] h = new byte[arr.length()];
            for (int i = 0; i < arr.length(); i++) h[i] = (byte) arr.getInt(i);
            String i = Base64.getEncoder().encodeToString(h);
            return "data:image/png;base64," + i;
        };
        JSONObject g = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(g.getString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult f = new AmfConnectorResult.ErrorResult();
            f.status = "ERROR";
            f.error = "wrong resource name, or invalid token";
            return f;
        }
        AmfConnectorResult.LoadAppStateResult f = new AmfConnectorResult.LoadAppStateResult();
        f.status = "OK";
        f.resourceType = (String) b;
        switch ((String) b) {
            case "text" -> f.state = c.get(g.getJSONArray("state"));
            case "image" -> f.image = d.get(g.getJSONArray("state"));
        }
        return f;
    }

    static AmfConnectorResult RemoveAppStateResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("status"), "ERROR")) {
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

    static AmfConnectorResult SaveAppStateResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.SaveAppStateResult b = new AmfConnectorResult.SaveAppStateResult();
        b.status = "OK";
        return b;
    }

    static AmfConnectorResult SaveRankResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("status"), "ERROR")) {
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

    static AmfConnectorResult SendMessageResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("status"), "ERROR")) {
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

    static AmfConnectorResult UpdateProfileResult(Object a) {
        JSONObject c = ((JSONArray) a).getJSONObject(0);
        if (Objects.equals(c.getString("status"), "ERROR")) {
            AmfConnectorResult.ErrorResult b = new AmfConnectorResult.ErrorResult();
            b.status = "ERROR";
            b.error = "invalid token";
            return b;
        }
        AmfConnectorResult.UpdateProfileResult b = new AmfConnectorResult.UpdateProfileResult();
        b.status = "OK";
        return b;
    }
}
