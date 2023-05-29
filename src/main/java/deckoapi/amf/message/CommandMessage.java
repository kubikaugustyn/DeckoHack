package deckoapi.amf.message;

public class CommandMessage extends Message {
    public CommandMessage() {
        super();
        this._explicitType = "flex.messaging.messages.CommandMessage";
        this.destination = "";
        this.operation=5;
        this.clientId=null;
    }
}
