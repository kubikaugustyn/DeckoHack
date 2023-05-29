package deckoapi.amf.message;

public class RemotingMessage extends Message {
    public RemotingMessage() {
        super();
        this._explicitType = "flex.messaging.messages.RemotingMessage";
        this.destination = "";
        this.source = "";
        this.operation = "";
        this.clientId = null;
    }
}
