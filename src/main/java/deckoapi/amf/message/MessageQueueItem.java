package deckoapi.amf.message;
public class MessageQueueItem {
    public Message msg;
    public VoidFunc<Object> onDone;
    public VoidFunc<Object> onFail;

    public MessageQueueItem(Message msg, VoidFunc<Object> onDone, VoidFunc<Object> onFail) {
        this.msg = msg;
        this.onDone = onDone;
        this.onFail = onFail;
    }
}
