package deckoapi.amf;

import deckoapi.amf.message.Message;
import deckoapi.amf.message.MessageBody;
import deckoapi.amf.message.MessageBodyDataItem;
import deckoapi.amf.message.MessageHeader;

public class Serializer {
    public Writer writer;

    public Serializer() {
        writer = new Writer();
    }

    public void writeObject(Object a) {
        writer.writeObject(a);
    }

    public void writeHeader(MessageHeader a) {
        writer.writeUTF(a.name, false);
        writer.writeBoolean(a.mustUnderstand);
        writer.writeInt(1);
        writer.reset();
        writer.write((byte) 1);
        writer.writeBoolean(true);
    }

    public void writeBody(MessageBody a) {
        writer.writeUTF(a.targetURI == null ? amf.CONST.NULL_STRING : a.targetURI, false);
        writer.writeUTF(a.responseURI == null ? amf.CONST.NULL_STRING : a.responseURI, false);
        writer.writeInt(1);
        writer.reset();
        writer.write((byte) amf.CONST.AMF0_AMF3);
        Object[] b = new Object[a.data.size()];
        int i = 0;
        for (Object c : a.data.toArray()) b[i++] = ((MessageBodyDataItem<?>) c).item;
        writer.writeObject(b);
    }

    public byte[] writeMessage(Message message) {
        try {
            writer.writeShort(message.version);
            writer.writeShort((short) message.headers.size());
            for (Header b : message.headers) if (b instanceof MessageHeader) this.writeHeader((MessageHeader) b);
            writer.writeShort((short) message.bodies.size());
            for (MessageBody b : message.bodies) this.writeBody(b);
        } catch (Exception ex) {
            System.out.println("Serializer writeMessage error:");
            ex.printStackTrace();
        }
        return writer.data();
    }
}
