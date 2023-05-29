package deckoapi;

import org.json.JSONObject;

import java.util.Date;

public class AmfConnectorResult {
    public String status;

    public static class ErrorResult extends AmfConnectorResult {
        public String error;
        public Object errorObject;
    }

    public static class AppConfigResult extends AmfConnectorResult {
        public Object config;
    }

    public static class AppStatesResult extends AmfConnectorResult {
        public Slot[] slots;

        public static class Slot {
            public int slotNumber;
            public String metadata;
        }
    }

    public static class DateResult extends AmfConnectorResult {
        public Date date;
    }

    public static class RankingResult extends AmfConnectorResult {
        public JSONObject playerBest; // Type: ???
        public JSONObject topList; // Type: ???
    }

    public static class TokenStatusResult extends AmfConnectorResult {
    }

    public static class KillTokenResult extends AmfConnectorResult {
    }

    public static class LoadAppStateResult extends AmfConnectorResult {
        public String resourceType;
        public String state;
        public String image;
    }

    public static class RemoveAppStateResult extends AmfConnectorResult {
        public Date date;
    }

    public static class SaveAppStateResult extends AmfConnectorResult {
    }

    public static class SendMessageResult extends AmfConnectorResult {
        public String code;
        public String subcode;
        public String message;
        public String detail;
    }

    public static class UpdateProfileResult extends AmfConnectorResult {
    }
}
