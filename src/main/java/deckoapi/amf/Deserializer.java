package deckoapi.amf;

import deckoapi.amf.message.Message;
import deckoapi.amf.message.MessageBody;
import deckoapi.amf.message.MessageBodyDataItem;
import deckoapi.amf.message.MessageHeader;
import org.json.JSONArray;

public class Deserializer {
    public Reader reader;

    public Deserializer(byte[] bytes) {
        reader = new Reader(bytes);
    }

    public Message readMessage() {
        Message a = amf.ActionMessage();
        a.version = reader.readUnsignedShort();
        short b = reader.readUnsignedShort();
        for (int c = 0; b > c; c++) a.headers.add(this.readHeader());
        var d = reader.readUnsignedShort();
        for (int c = 0; d > c; c++) a.bodies.add(this.readBody());
        return a;
    }

    public MessageHeader readHeader() {
        MessageHeader a = amf.MessageHeader();
        a.name = reader.readUTF();
        a.mustUnderstand = reader.readBoolean();
        reader.pos += 4;
        reader.reset();
        a.data = this.readObject();
        return a;
    }

    public MessageBody readBody() {
        MessageBody a = amf.MessageBody();
        a.targetURI = reader.readUTF();
        a.responseURI = reader.readUTF();
        reader.pos += 4;
        reader.reset();
        JSONArray arr = (JSONArray) this.readObject();
        for (int i = 0; i < arr.length(); i++) a.data.add(new MessageBodyDataItem<>(arr.get(i)));
        return a;
    }

    public Object readObject() {
        return reader.readObject();
    }
}
