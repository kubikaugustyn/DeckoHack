package deckoapi.amf;

import deckoapi.amf.message.Message;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

public class Writer {
    Vector<Byte> data;
    Vector<Object> objects;
    HashMap<String, Integer> traits;
    HashMap<String, Integer> strings;
    int stringCount, traitCount, objectCount;

    Writer() {
        this.data = new Vector<>();
        this.objects = new Vector<>();
        this.traits = new HashMap<>();
        this.strings = new HashMap<>();
        this.stringCount = 0;
        this.traitCount = 0;
        this.objectCount = 0;
    }

    void write(byte a) {
        this.data.add(a);
//        Exception ex = new Exception("Write: " + a);
//        ex.printStackTrace();
    }

    public void writeShort(short a) {
        this.write((byte) (a >>> 8 & 255));
        this.write((byte) (a & 255));
    }

    public int writeUTF(String a, boolean b) {
        int d, e, f, g;
        for (f = a.length(), g = 0, e = 0; f > e; e++) {
            d = Character.codePointAt(a, e);
            if (d > 0 && 128 > d) g++;
            else g += d > 2047 ? 3 : 2;
        }
        Vector<Byte> c = new Vector<>();
        if (b) this.writeUInt29(g << 1 | 1);
        else {
            c.add((byte) (g >>> 8 & 255));
            c.add((byte) (255 & g));
        }
        for (e = 0; f > e; e++) {
            d = Character.codePointAt(a, e);
            if (d > 0 && 128 > d) {
                c.add((byte) d);
            } else {
                if (d > 2047) {
                    c.add((byte) (224 | d >> 12));
                    c.add((byte) (128 | d >> 6 & 63));
                    c.add((byte) (128 | d & 63)); // Simplified: b ? 128 | d >> 0 & 63 : 128 | 63 & d
                } else {
                    c.add((byte) (192 | d >> 6));
                    c.add((byte) (128 | d & 63)); // Simplified: b ? 128 | d >> 0 & 63 : 128 | 63 & d
                }
            }
        }
        this.writeAll(c);
        return b ? g : g + 2;
    }

    public void writeUInt29(int a) {
        if (128 > a) this.write((byte) a);
        else if (16384 > a) {
            this.write((byte) (a >> 7 & 127 | 128));
            this.write((byte) (127 & a));
        } else if (2097152 > a) {
            this.write((byte) (a >> 14 & 127 | 128));
            this.write((byte) (a >> 7 & 127 | 128));
            this.write((byte) (127 & a));
        } else {
            if (!(1073741824 > a)) {
                Exception ex = new Exception("Integer out of range: " + a);
                System.err.println("Integer out of range: " + a);
                ex.printStackTrace();
                System.exit(666);
            }
            this.write((byte) (a >> 22 & 127 | 128));
            this.write((byte) (a >> 15 & 127 | 128));
            this.write((byte) (a >> 8 & 127 | 128));
            this.write((byte) (255 & a));
        }
    }

    public void writeAll(Vector<Byte> a) {
        byte[] bytes = new byte[a.size()];
        for (int i = 0; i < a.size(); i++) {
            bytes[i] = a.get(i);
        }
        this.writeAll(bytes);
    }

    public void writeAll(byte[] a) {
        // Simplified: for (var b = 0; b < a.length; b++) this.write(a[b])
        for (byte value : a) this.write(value);
    }

    public void writeBoolean(boolean a) {
        this.write((byte) (a ? 1 : 0));
    }

    public void writeInt(int a) {
        this.write((byte) (a >>> 24 & 255));
        this.write((byte) (a >>> 16 & 255));
        this.write((byte) (a >>> 8 & 255));
        this.write((byte) (a & 255));
    }

    public void writeByte(byte a) {
        // this.write((byte) (a & 255));
        this.write(a);
    }

    public void writeUnsignedInt(int a) {
        Exception ex = new Exception("I don't know how to implement unsigned integer function in Java: " + a);
        System.err.println("I don't know how to implement unsigned integer function in Java: " + a);
        ex.printStackTrace();
        System.exit(666);
        /*if (a < 0) {
            a = -(4294967295 ^ a) - 1;
        } ;
         a &= 4294967295;
        this.write((byte) (a >>> 24 & 255));
        this.write((byte) (a >>> 16 & 255));
        this.write((byte) (a >>> 8 & 255));
        this.write((byte) (a & 255));*/
    }

    private double getDouble(double a) {
        Exception ex = new Exception("I don't know how to implement double function in Java: " + a);
        System.err.println("I don't know how to implement double function in Java: " + a);
        ex.printStackTrace();
        System.exit(666);
        /*var b = [0, 0];
        if (a != a) return b[0] = -524288, b;
        var c = 0 > a || 0 === a && 0 > 1 / a ? -2147483648 : 0,
                a = Math.abs(a);
        if (a === Number.POSITIVE_INFINITY) return b[0] = 2146435072 | c, b;
        for (var d = 0; a >= 2 && 1023 >= d;) d++, a /= 2;
        for (; 1 > a && d >= -1022;) d--, a *= 2;
        if (d += 1023, 2047 == d) return b[0] = 2146435072 | c, b;
        var e;
        return 0 == d ? (e = a * Math.pow(2, 23) / 2, a = Math.round(a * Math.pow(2, 52) / 2)) : (e = a * Math.pow(2, 20) - Math.pow(2, 20), a = Math.round(a * Math.pow(2, 52) - Math.pow(2, 52))), b[0] = c | d << 20 & 2147418112 | 1048575 & e, b[1] = a, b*/
        return 0;
    }

    public void writeDouble(long a) {
        Exception ex = new Exception("I don't know how to implement double function in Java: " + a);
        System.err.println("I don't know how to implement double function in Java: " + a);
        ex.printStackTrace();
        System.exit(666);
        //a = this._getDouble(a), this.writeUnsignedInt(a[0]), this.writeUnsignedInt(a[1])
    }

    public String getResult() {
        // return this.data.join("")
        /*byte[] bytes = new byte[this.data.size()];
        for (int i = 0; i < this.data.size(); i++) {
            bytes[i] = this.data.get(i);
        }
        return new String(bytes, StandardCharsets.UTF_8);*/
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.data.size(); i++) {
            result.append(this.data.get(i).intValue());
        }
        return result.toString();
    }

    public void reset() {
        this.objects = new Vector<>();
        this.traits = new HashMap<>();
        this.strings = new HashMap<>();
        this.stringCount = 0;
        this.traitCount = 0;
        this.objectCount = 0;
    }

    public void writeStringWithoutType(String a) {
        if (a.length() == 0) this.writeUInt29(1);
        else {
            if (this.stringByReference(a) == null) this.writeUTF(a, true);
        }
    }

    public Integer stringByReference(String a) {
        /*
        var b = this.strings[a];
        return b ? this.writeUInt29(b << 1) : this.strings[a] = this.stringCount++, b
        */
        if (this.strings.containsKey(a)) {
            int b = this.strings.get(a);
            this.writeUInt29(b << 1);
            return b;
        } else this.strings.put(a, this.stringCount++);
        return null;
    }

    public Integer objectByReference(Object a) {
        boolean c = false;
        int b;
        for (b = 0; b < this.objects.size(); b++)
            if (this.objects.get(b) == a) {
                c = true;
                break;
            }
        if (c) this.writeUInt29(b << 1);
        else {
            this.objects.add(a);
            this.objectCount++;
        }
        return c ? b : null;
    }

    public Integer traitsByReference(String[] a, String b) {
        StringBuilder c = new StringBuilder(b + "|");
        for (String s : a) c.append(s).append("|");
        Integer e = this.traits.get(c.toString());
        if (e != null) this.writeUInt29((byte) (e << 2 | 1));
        else this.traits.put(c.toString(), this.traitCount++);
        return e;
    }

    public void writeAmfInt(long a) {
        if (a >= amf.CONST.INT28_MIN_VALUE && a <= amf.CONST.INT28_MAX_VALUE) {
            a &= amf.CONST.UINT29_MASK;
            this.write(amf.CONST.INTEGER_TYPE);
            this.writeUInt29((int) a);
        } else {
            this.write(amf.CONST.DOUBLE_TYPE);
            this.writeDouble(a);
        }
    }

    public void writeDate(Date a) {
        this.write(amf.CONST.DATE_TYPE);
        if (this.objectByReference(a) == null) {
            this.writeUInt29(1);
            this.writeDouble(a.getTime());
        }
    }

    public void writeObject(Object a) {
        if (a == null) {
            this.write(amf.CONST.NULL_TYPE);
        } else {
            if (a instanceof String) {
                this.write(amf.CONST.STRING_TYPE);
                this.writeStringWithoutType((String) a);
            } else if (a instanceof JSONObject) {
                this.writeMap((JSONObject) a);
            } else if (a.getClass().isArray()) {
                this.writeArray((Object[]) a);
            } else if (a instanceof Number) {
                int b = (int) a;
                if (b >= 0 && b == (0 | b)) this.writeAmfInt(b);
                else {
                    this.write(amf.CONST.DOUBLE_TYPE);
                    this.writeDouble(b);
                }
            } else System.err.println("TODO");
            //else if (a instanceof Number) ...
        }
    }

    private void writeMap(JSONObject a) {
        this.write(amf.CONST.OBJECT_TYPE);
        if (this.objectByReference(a) == null) {
            this.writeUInt29(11);
            this.traitCount++;
            this.writeStringWithoutType(amf.CONST.EMPTY_STRING);
            for (Iterator<String> it = a.keys(); it.hasNext(); ) {
                String b = it.next();
                this.writeStringWithoutType(b == null ? amf.CONST.EMPTY_STRING : b);
                this.writeObject(a.get(b));
            }
            this.writeStringWithoutType(amf.CONST.EMPTY_STRING);
        }
    }

    private void writeArray(Object[] a) {
        this.write(amf.CONST.ARRAY_TYPE);
        if (this.objectByReference(a) == null) {
            this.writeUInt29(a.length << 1 | 1);
            this.writeUInt29(1);
        }
        for (Object b : a) this.writeObject(b);
    }

    public byte[] data() {
        byte[] bytes = new byte[this.data.size()];
        int i = 0;
        for (Byte b : data) bytes[i++] = b;
        return bytes;
    }
}
