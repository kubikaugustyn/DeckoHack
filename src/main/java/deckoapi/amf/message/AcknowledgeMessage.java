package deckoapi.amf.message;

public class AcknowledgeMessage extends Message {
    public AcknowledgeMessage() {
        super();
        this._explicitType = "flex.messaging.messages.AcknowledgeMessage";
        this.body = null;
        this.messageId = null;
        this.clientId = null;
    }
}
