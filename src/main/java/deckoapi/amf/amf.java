package deckoapi.amf;

import deckoapi.amf.message.*;
import deckoapi.jshelper.XMLHttpRequest;
import gui.DeckoHack;

import java.util.Objects;
import java.util.UUID;
import java.util.Vector;

// From https://decko.ceskatelevize.cz/cms/common/js/AmfJsD.min.js
public class amf {
    public interface CONST {
        String EMPTY_STRING = "";
        String NULL_STRING = "null";
        byte UNDEFINED_TYPE = 0;
        byte NULL_TYPE = 1;
        byte FALSE_TYPE = 2;
        byte TRUE_TYPE = 3;
        byte INTEGER_TYPE = 4;
        byte DOUBLE_TYPE = 5;
        byte STRING_TYPE = 6;
        byte XML_TYPE = 7;
        byte DATE_TYPE = 8;
        byte ARRAY_TYPE = 9;
        byte OBJECT_TYPE = 10;
        byte XMLSTRING_TYPE = 11;
        byte BYTEARRAY_TYPE = 12;
        byte AMF0_AMF3 = 17;
        int UINT29_MASK = 536870911;
        int INT28_MAX_VALUE = 268435455;
        int INT28_MIN_VALUE = -268435456;
        String CLASS_ALIAS = "_explicitType";
    }

    int requestTimeout = 30000;
    int requestPoolSize = 6;
    Vector<XMLHttpRequest<amf>> requestPool = new Vector<>();
    Vector<MessageQueueItem> messageQueue = new Vector<>();
    boolean sendMessageId = false;
    String clientId = null;
    int sequence = 1;
    String destination = "";
    String endpoint = "";
    Vector<Header> headers = null;

    void init(String destination, String endpoint, int requestTimeout) {
        this.clientId = null;
        this.sequence = 1;
        this.destination = destination;
        this.endpoint = endpoint;
        this.requestTimeout = requestTimeout > 0 ? requestTimeout : 30000;
        this.headers = new Vector<>();
    }

    public void call(String a, String b, Object c, VoidFunc<Object> d, VoidFunc<Object> e) {
        this.init("amfphp", a, 0);
        this.invoke2(b, c, d, e);
    }

    void addHeader(String name, String value) {
        Header header = new Header(name, value);
        this.headers.add(header);
    }

    static ActionMessage ActionMessage() {
        return new ActionMessage();
    }

    static ActionMessage2 ActionMessage2() {
        return new ActionMessage2();
    }

    static MessageBody MessageBody() {
        return new MessageBody();
    }

    static MessageHeader MessageHeader() {
        return new MessageHeader();
    }

    static CommandMessage CommandMessage() {
        return new CommandMessage();
    }

    static RemotingMessage RemotingMessage() {
        return new RemotingMessage();
    }

    static AcknowledgeMessage AcknowledgeMessage() {
        return new AcknowledgeMessage();
    }

    public Message createMessage(String a, Object b, Vector<MessageBody> c) {
        Message d, e = amf.ActionMessage();
        MessageBody f = amf.MessageBody();
        if (Objects.equals(a, "ping")) {
            this.sequence = 1;
            f.responseURI = "/" + this.sequence++;
            d = amf.CommandMessage();
            d.destination = this.destination;
        } else {
            f.responseURI = "/" + this.sequence++;
            d = amf.RemotingMessage();
            d.destination = this.destination;
            d.source = a;
            d.operation = b;
            d.body = c;
            if (this.sendMessageId) d.messageId = amf.uuid(0, 0);
            d.clientId = this.clientId;
            /*
            Not even working in the source code
            for (int g = 0; g < this.headers.size(); g++) {
                Header h = this.headers.elementAt(g);
                for (String i : h.headers.keySet()) d.headers.set(i, h.headers.get(i));
            }*/
        }
        f.data.add(new MessageBodyDataItem<>(d));
        e.bodies.add(f);
        return e;
    }

    public Message createMessage2(String a, Object b) {
        Message d = amf.ActionMessage2();
        MessageBody c = amf.MessageBody();
        c.responseURI = "/1";
        c.targetURI = a;
        c.data.add(new MessageBodyDataItem<>(b));
        d.bodies.add(c);
        return d;
    }

    public void invoke(String a, Object b, Vector<MessageBody> c, VoidFunc<Object> d, VoidFunc<Object> e) {
        if (this.clientId == null && this.messageQueue.size() == 0) {
            this.messageQueue.add(new MessageQueueItem(this.createMessage("ping", null, null), d, e));
            this._processQueue();
        }
        this.messageQueue.add(new MessageQueueItem(this.createMessage(a, b, c), d, e));
        if (this.clientId != null) this._processQueue();
    }

    public void invoke2(String a, Object b, VoidFunc<Object> c, VoidFunc<Object> d) {
        this.messageQueue.add(new MessageQueueItem(this.createMessage2(a, b), c, d));
        this._processQueue2();
    }

    public void _processQueue() {
        XMLHttpRequest<amf> b;
        for (int a = 0; a < this.requestPoolSize && this.messageQueue.size() > 0; a++) {
            boolean allow = false;
            if (this.requestPool.size() == a) {
                b = new XMLHttpRequest<>();
                b.timeout = this.requestTimeout;
                b.parent = this;
                b.busy = false;
                allow = this.requestPool.add(b);
            } else {
                b = this.requestPool.elementAt(a);
                allow = !b.busy;
            }
            if (allow) {
                MessageQueueItem c = this.messageQueue.remove(0);
                this._send(b, c.msg, c.onDone, c.onFail);
                MessageBodyDataItem<?> item = c.msg.bodies.elementAt(0).data.elementAt(0);
                Message msg = (Message) item.item;
                if (Objects.equals(msg._explicitType, "flex.messaging.messages.CommandMessage")) return;
            }
        }
    }

    public void _processQueue2() {
        XMLHttpRequest<amf> b;
        for (int a = 0; a < this.requestPoolSize && this.messageQueue.size() > 0; a++) {
            boolean allow;
            if (this.requestPool.size() == a) {
                b = new XMLHttpRequest<>();
                b.timeout = this.requestTimeout;
                b.parent = this;
                b.busy = false;
                allow = this.requestPool.add(b);
            } else {
                b = this.requestPool.elementAt(a);
                allow = !b.busy;
            }
            if (allow) {
                MessageQueueItem c = this.messageQueue.remove(0);
                this._send2(b, c.msg, c.onDone, c.onFail);
            }
        }
    }

    public void _send(XMLHttpRequest<amf> a, Message b, VoidFunc<Object> c, VoidFunc<Object> d) {
        Serializer e = new Serializer();
        a.message = e.writeMessage(b);
        a.onreadystatechange = req -> {
            if (req.readyState == 1) {
                // System.out.println("AAAA: " + req.busy);
                if (!req.busy) {
                    req.busy = true;
                    req.setRequestHeader("Content-Type", "application/x-amf; charset=UTF-8");
                    req.responseType = "arraybuffer";
                    req.send(req.message);
                }
            } else if (req.readyState == 4) {
                req.onreadystatechange = a1 -> {
                };
                try {
                    if (req.status >= 200 && req.status <= 300) {
                        if (req.getResponseHeader("Content-Type").contains("application/x-amf")) {
                            Deserializer b1 = new Deserializer(req.response);
                            Message e1 = b1.readMessage();
                            for (int i = 0; i < e1.bodies.size(); i++) {
                                MessageBody g = e1.bodies.elementAt(i);
                                if (g.targetURI != null && g.targetURI.contains("/onResult")) {
                                    if (g.targetURI.equals("/1/onResult")) {
                                        req.parent.clientId = g.dataJSON().getString("clientId");
                                        for (int h = 0; h < req.parent.messageQueue.size(); h++) {
                                            req.parent.messageQueue.elementAt(h).msg.bodies.elementAt(0).dataJSON().put("clientId", req.parent.clientId);
                                        }
                                    } else c.get(g.dataJSON().getString("body"));
                                } else if (g.dataJSON().getString("_explicitType").equals("flex.messaging.messages.ErrorMessage")) {
                                    d.get(new FaultInfo(g.dataJSON().getInt("faultCode"), g.dataJSON().getString("faultDetail"), g.dataJSON().getString("faultString")));
                                }
                            }
                            req.busy = false;
                            req.message = null;
                            req.parent._processQueue();
                        } else d.get(new FaultInfo(1, req.responseText, ""));
                    } else d.get(new FaultInfo(1, req.responseText, ""));
                } catch (Exception i) {
                    d.get(new FaultInfo(2, "", ""));
                }
            }
        };
        DeckoHack.instance.setRequestHeaders(a, true); // Injected code...
        a.open("POST", this.endpoint, true);
    }

    public void _send2(XMLHttpRequest<amf> a, Message b, VoidFunc<Object> c, VoidFunc<Object> d) {
        Serializer e = new Serializer();
        a.message = e.writeMessage(b);
        a.onreadystatechange = req -> {
            if (req.readyState == 1) {
                // System.out.println("BBBB: " + req.busy);
                if (!req.busy) {
                    req.busy = true;
                    req.setRequestHeader("Content-Type", "application/x-amf; charset=UTF-8");
                    req.responseType = "arraybuffer";
                    req.send(req.message);
                }
            } else if (req.readyState == 4) {
                req.onreadystatechange = a1 -> {
                };
                try {
                    // System.err.println("Status: " + req.status);
                    if (req.status >= 200 && req.status <= 300) {
                        // System.err.println("Content-Type: " + req.getResponseHeader("Content-Type"));
                        if (req.getResponseHeader("Content-Type").contains("application/x-amf")) {
                            Deserializer b1 = new Deserializer(req.response);
                            Message e1 = b1.readMessage();
                            for (int i = 0; i < e1.bodies.size(); i++) {
                                MessageBody g = e1.bodies.elementAt(i);
                                if (g.targetURI != null && g.targetURI.contains("/onResult")) c.get(g.dataJSONArray());
                                else
                                    d.get(new FaultInfo(g.dataJSON().getInt("faultCode"), g.dataJSON().getString("faultDetail"), g.dataJSON().getString("faultString")));
                            }
                            req.busy = false;
                            req.message = null;
                            req.parent._processQueue();
                        } else d.get(new FaultInfo(1, req.responseText, ""));
                    } else d.get(new FaultInfo(1, req.responseText, ""));
                } catch (Exception h) {
                    h.printStackTrace();
                    d.get(new FaultInfo(2, "", ""));
                }
            }
        };
        DeckoHack.instance.setRequestHeaders(a, true); // Injected code...
        a.open("POST", this.endpoint, true);
    }

    public static String uuid(int b, int c) {
        //if (b > 0) return Integer.toString(c | (new Random().nextInt(16) >> (c / 4)), 16);
        //return "10000000-1000-4000-8000-100000000000".replace("/1|0|(8)/g", this::uuid);
        return UUID.randomUUID().toString();
    }
}
