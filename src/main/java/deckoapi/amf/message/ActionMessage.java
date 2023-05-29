package deckoapi.amf.message;

public class ActionMessage extends Message {
    public ActionMessage() {
        super();
        this._explicitType = "flex.messaging.io.amf.ActionMessage";
        this.version = 3;
        MessageHeader header = new MessageHeader("mobile", false, true);
        this.headers.add(header);
    }
}
