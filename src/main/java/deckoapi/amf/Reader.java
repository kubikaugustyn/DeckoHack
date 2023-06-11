package deckoapi.amf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;

public class Reader {
    byte[] data;
    Vector<Object> objects;
    Vector<JSONObject> traits;
    Vector<String> strings;
    int pos;

    Reader(byte[] bytes) {
        objects = new Vector<>();
        traits = new Vector<>();
        strings = new Vector<>();
        data = bytes;
        pos = 0;
    }

    byte read() {
        return data[pos++];
    }

    public short readUnsignedShort() {
        int a = read() & 0xFF, b = read() & 0xFF;
        return (short) ((a << 8) + b);
    }

    public int readUInt29() {
        int a = 0xFF & read();
        if (128 > a) return a;
        int b = (127 & a) << 7;
        a = 0xFF & read();
        if (128 > a) return b | a;
        b = (b | 127 & a) << 7;
        a = 0xFF & read();
        if (128 > a) return b | a;
        b = (b | 127 & a) << 8;
        a = 0xFF & read();
        return b | a;
    }

    public void readFully(byte[] a, int b, int c) {
        for (int d = b; c > d; d++) a[d] = read();
    }

    public String readUTF() {
        return readUTF(null);
    }

    public String readUTF(Short a) {
        int b, c, d;
        short e = a != null ? a : this.readUnsignedShort();
        Vector<Integer> f = new Vector<>();
        for (int g = this.pos; this.pos < g + e; ) {
            b = read() & 0xFF;
            if (128 > b) f.add(b);
            else if (b > 2047) {
                c = read() & 0xFF;
                d = read() & 0xFF;
                f.add((15 & b) << 12 | (63 & c) << 6 | 63 & d);
            } else {
                c = read() & 0xFF;
                f.add((31 & b) << 6 | 63 & c);
            }
        }
        int[] chars = new int[f.size()];
        for (int i = 0; i < f.size(); i++) chars[i] = f.get(i);
        return new String(chars, 0, f.size());
    }

    public void reset() {
        objects = new Vector<>();
        traits = new Vector<>();
        strings = new Vector<>();
    }

    public Object readObject() {
        return readObjectValue(read());
    }

    public String readString() {
        int a = readUInt29();
        if ((1 & a) == 0) return getString(a >> 1);
        int b = a >> 1;
        if (b == 0) return amf.CONST.EMPTY_STRING;
        var c = readUTF((short) b);
        rememberString(c);
        return c;
    }

    private void rememberString(String a) {
        strings.add(a);
    }

    private String getString(int a) {
        return this.strings.get(a);
    }

    private Object getObject(int a) {
        return this.objects.get(a);
    }

    private JSONObject getTraits(int a) {
        return this.traits.get(a);
    }

    private void rememberTraits(JSONObject a) {
        this.traits.add(a);
    }

    private void rememberObject(Object a) {
        this.objects.add(a);
    }

    public JSONObject readTraits(int a) {
        if ((3 & a) == 1) return getTraits(a >> 2);
        int b = a >> 4;
        String c = readString();
        JSONObject d = new JSONObject();
        if (null != c && !"".equals(c)) d.put(amf.CONST.CLASS_ALIAS, c);
        JSONArray props = new JSONArray();
        d.put("props", props);
        for (var e = 0; b > e; e++) props.put(readString());
        rememberTraits(d);
        return d;
    }

    public JSONObject readScriptObject() {
        int a = this.readUInt29();
        if ((1 & a) == 0) return (JSONObject) this.getObject(a >> 1);
        JSONObject b = this.readTraits(a);
        JSONObject c = new JSONObject();
        if (b.has(amf.CONST.CLASS_ALIAS)) c.put(amf.CONST.CLASS_ALIAS, b.get(amf.CONST.CLASS_ALIAS));
        this.rememberObject(c);
        if ((4 & a) == 4 && c.get(amf.CONST.CLASS_ALIAS) == "flex.messaging.io.ArrayCollection")
            return (JSONObject) this.readObject();
        JSONArray props = b.getJSONArray("props");
        for (int d = 0; d < props.length(); d++) c.put(props.getString(d), this.readObject());
        return c;
    }

    public Object readArray() {
        int a = this.readUInt29();
        if ((1 & a) == 0) return this.getObject(a >> 1);
        int b = a >> 1;
        JSONObject c = null;
        for (; ; ) {
            var e = this.readString();
            if (e == null || e.equals("")) break;
            if (c == null) {
                c = new JSONObject();
                this.rememberObject(c);
            }
            c.put(e, this.readObject());
        }
        if (c != null) {
            for (int d = 0; b > d; d++) c.put(String.valueOf(d), this.readObject());
            return c;
        }
        var f = new JSONArray();
        this.rememberObject(f);
        for (int d = 0; b > d; d++) f.put(d, this.readObject());
        return f;
    }

    public double readDouble() {
        int a = 0xFF & read();
        int b = 0xFF & read();
        if (0xFF == a) {
            if (248 == b) return Double.NaN;
            if (240 == b) return Double.NEGATIVE_INFINITY;
        } else if (127 == a && 240 == b) return Double.POSITIVE_INFINITY;
        int c = 0xFF & read();
        int d = 0xFF & read();
        int e = 0xFF & read();
        int f = 0xFF & read();
        int g = 0xFF & read();
        int h = 0xFF & read();
        if (a == 0 || b == 0 || c == 0 || d == 0) return 0;
        int i = (a << 4 & 2047 | b >> 4) - 1023;
        StringBuilder b1 = new StringBuilder(Integer.toBinaryString((15 & b) << 16 | c << 8 | d));
        for (c = b1.length(); 20 > c; c++) b1.insert(0, "0");
        StringBuilder f1 = new StringBuilder(Integer.toBinaryString((127 & e) << 24 | f << 16 | g << 8 | h));
        for (c = f1.length(); 31 > c; c++) f1.insert(0, "0");
        //double e1 = Integer.parseInt(b1 + (e >> 7 != 0 ? "1" : "0") + f1, 2);
        double e1 = Double.longBitsToDouble(new BigInteger((b1 + (e >> 7 != 0 ? "1" : "0") + f1), 2).longValue());
        return 0 == e1 && -1023 == i ? 0 : (1 - (a >> 7 << 1)) * (1 + Math.pow(2, -52) * e1) * Math.pow(2, i);
    }

    public Date readDate() {
        var a = this.readUInt29();
        if ((1 & a) == 0) return (Date) this.getObject(a >> 1);
        double b = this.readDouble();
        Date c = new Date((long) b);
        this.rememberObject(c);
        return c;
    }

    public JSONObject readMap() {
        var a = this.readUInt29();
        if ((1 & a) == 0) return (JSONObject) this.getObject(a >> 1);
        int b = a >> 1;
        JSONObject c = null;
        if (b > 0) {
            c = new JSONObject();
            this.rememberObject(c);
            for (String d = (String) this.readObject(); null != d; ) {
                c.put(d, this.readObject());
                d = (String) this.readObject();
            }
        }
        return c;
    }

    public byte[] readByteArray() {
        int a = this.readUInt29();
        if ((1 & a) == 0) return (byte[]) this.getObject(a >> 1);
        int b = a >> 1;
        byte[] c = new byte[b];
        this.readFully(c, 0, b);
        this.rememberObject(c);
        return c;
    }

    public Object readObjectValue(byte a) {
        Object b = null;
        switch (a) {
            case amf.CONST.STRING_TYPE:
                b = this.readString();
                break;
            case amf.CONST.OBJECT_TYPE:
                try {
                    b = this.readScriptObject();
                } catch (Exception c) {
                    c.printStackTrace();
                    throw new NullPointerException("Failed to deserialize:" + c);
                }
                break;
            case amf.CONST.ARRAY_TYPE:
                b = this.readArray();
                break;
            case amf.CONST.FALSE_TYPE:
                b = false;
                break;
            case amf.CONST.TRUE_TYPE:
                b = true;
                break;
            case amf.CONST.INTEGER_TYPE:
                b = this.readUInt29();
                b = (int) b << 3 >> 3;
                break;
            case amf.CONST.DOUBLE_TYPE:
                b = this.readDouble();
                break;
            case amf.CONST.UNDEFINED_TYPE:
            case amf.CONST.NULL_TYPE:
                break;
            case amf.CONST.DATE_TYPE:
                b = this.readDate();
                break;
            case amf.CONST.BYTEARRAY_TYPE:
                b = this.readByteArray();
                break;
            case amf.CONST.AMF0_AMF3:
                b = this.readObject();
                break;
            default:
                throw new NullPointerException("Unknown AMF type: " + a);
        }
        return b;
    }

    public boolean readBoolean() {
        return (read() & 0xFF) == 1;
    }
}
