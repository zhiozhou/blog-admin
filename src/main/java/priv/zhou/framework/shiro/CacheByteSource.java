package priv.zhou.framework.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UID;

/**
 * 解决 SimpleByteSource 无法序列化问题
 */
@SuppressWarnings(value = "unused")
public class CacheByteSource implements ByteSource, Serializable {

    public static final long serialVersionUID = SERIAL_VERSION_UID;

    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public CacheByteSource() {
    }

    public CacheByteSource(byte[] bytes) {
        this.bytes = bytes;
    }


    public CacheByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }


    public CacheByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }


    public CacheByteSource(ByteSource source) {
        this.bytes = source.getBytes();
    }


    public CacheByteSource(File file) {
        this.bytes = new CacheByteSource.BytesHelper().getBytes(file);
    }


    public CacheByteSource(InputStream stream) {
        this.bytes = new CacheByteSource.BytesHelper().getBytes(stream);
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String ||
                o instanceof ByteSource || o instanceof File || o instanceof InputStream;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    @Override
    public String toHex() {
        if (this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(getBytes());
        }
        return this.cachedHex;
    }

    @Override
    public String toBase64() {
        if (this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(getBytes());
        }
        return this.cachedBase64;
    }

    @Override
    public String toString() {
        return toBase64();
    }

    @Override
    public int hashCode() {
        if (this.bytes == null || this.bytes.length == 0) {
            return 0;
        }
        return Arrays.hashCode(this.bytes);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ByteSource) {
            ByteSource byteSource = (ByteSource) o;
            return Arrays.equals(getBytes(), byteSource.getBytes());
        }
        return false;
    }

    private static final class BytesHelper extends CodecSupport {

        /**
         * 嵌套类也需要提供无参构造器
         */
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return toBytes(stream);
        }
    }
}
