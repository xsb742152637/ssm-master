package util.converter;

import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Comment：
 * Created by IntelliJ IDEA.
 * User: xiucai
 * Date: 2020/4/17 16:20
 */
public class BlobImpl implements Blob {

    private byte[] _bytes = new byte[0];

    private int _length = 0;

    /**
     * 构造函数，以byte[]构建blob
     *
     * @param bytes
     */
    public BlobImpl(byte[] bytes) {
        init(bytes);
    }

    /**
     * 构造函数，以blob重新构建blob
     *
     * @param blob
     */
    public BlobImpl(Blob blob) {
        init(blobToBytes(blob));
    }

    /**
     * 初始化byte[]
     *
     * @param bytes
     */
    private void init(byte[] bytes) {
        _bytes = bytes;
        _length = _bytes.length;
    }

    /**
     * 将blob转为byte[]
     *
     * @param blob
     * @return
     */
    private byte[] blobToBytes(Blob blob) {
        BufferedInputStream is = null;
        try {
            is = new BufferedInputStream(blob.getBinaryStream());
            byte[] bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0;
            int read = 0;
            while (offset < len
                    && (read = is.read(bytes, offset, len - offset)) >= 0) {
                offset += read;
            }
            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                is.close();
                is = null;
            } catch (IOException e) {
                return null;
            }

        }
    }

    /**
     * 获得blob中数据实际长度
     *
     * @return
     * @throws SQLException
     */
    public long length() throws SQLException {
        return _bytes.length;
    }

    public byte[] getBytes() throws SQLException {
        return _bytes;
    }
    /**
     * 返回指定长度的byte[]
     *
     * @param pos
     * @param len
     * @return
     * @throws SQLException
     */
    public byte[] getBytes(long pos, int len) throws SQLException {
        if (pos == 0 && len == length())
            return _bytes;
        try {
            byte[] newbytes = new byte[len];
            System.arraycopy(_bytes, (int) pos, newbytes, 0, len);
            return newbytes;
        } catch (Exception e) {
            throw new SQLException("Inoperable scope of this array");
        }
    }

    /**
     * 返回InputStream
     *
     * @return
     * @throws SQLException
     */
    public InputStream getBinaryStream() throws SQLException {
        return new ByteArrayInputStream(_bytes);
    }

    /**
     * 获取此byte[]中start的字节位置
     *
     * @param pattern
     * @param start
     * @return
     * @throws SQLException
     */
    public long position(byte[] pattern, long start) throws SQLException {
        start--;
        if (start < 0) {
            throw new SQLException("start < 0");
        }
        if (start >= _length) {
            throw new SQLException("start >= max length");
        }
        if (pattern == null) {
            throw new SQLException("pattern == null");
        }
        if (pattern.length == 0 || _length == 0 || pattern.length > _length) {
            return -1;
        }
        int limit = (int) _length - pattern.length;
        for (int i = (int) start; i <= limit; i++) {
            int p;
            for (p = 0; p < pattern.length && _bytes[i + p] == pattern[p]; p++) {
                if (p == pattern.length) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 获取指定的blob中start的字节位置
     *
     * @param pattern
     * @param start
     * @return
     * @throws SQLException
     */
    public long position(Blob pattern, long start) throws SQLException {
        return position(blobToBytes(pattern), start);
    }

    /**
     * 不支持操作异常抛出
     *
     */
    void nonsupport() {
        throw new UnsupportedOperationException("This method is not supported！");
    }

    /**
     * 释放Blob对象资源
     *
     * @throws SQLException
     */
    public void free() throws SQLException {
        _bytes = new byte[0];
        _length = 0;
    }

    /**
     * 返回指定长度部分的InputStream，并返回InputStream
     *
     * @param pos
     * @param len
     * @return
     * @throws SQLException
     */
    public InputStream getBinaryStream(long pos, long len) throws SQLException {
        return new ByteArrayInputStream(getBytes(pos, (int) len));
    }

    /**
     * 以指定指定长度将二进制流写入OutputStream，并返回OutputStream
     *
     * @param pos
     * @return
     * @throws SQLException
     */
    public OutputStream setBinaryStream(long pos) throws SQLException {
        // 暂不支持
        nonsupport();
        pos--;
        if (pos < 0) {
            throw new SQLException("pos < 0");
        }
        if (pos > _length) {
            throw new SQLException("pos > length");
        }
        // 将byte[]转为ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(_bytes);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes = new byte[(int) pos];
        try {
            bytes = new byte[inputStream.available()];
            int read;
            while ((read = inputStream.read(bytes)) >= 0) {
                os.write(bytes, 0, read);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }

        // 返回OutputStream
        return (OutputStream) os;
    }

    /**
     * 设定byte[]
     *
     * @param pos
     * @param bytes
     * @param offset
     * @param size
     * @param copy
     * @return
     * @throws SQLException
     */
    private int setBytes(long pos, byte[] bytes, int offset, int size,
                         boolean copy) throws SQLException {
        // 暂不支持
        nonsupport();
        pos--;
        if (pos < 0) {
            throw new SQLException("pos < 0");
        }
        if (pos > _length) {
            throw new SQLException("pos > max length");
        }
        if (bytes == null) {
            throw new SQLException("bytes == null");
        }
        if (offset < 0 || offset > bytes.length) {
            throw new SQLException("offset < 0 || offset > bytes.length");
        }
        if (size < 0 || pos + size > (long) Integer.MAX_VALUE
                || offset + size > bytes.length) {
            throw new SQLException();
        }
        // 当copy数据时
        if (copy) {
            _bytes = new byte[size];
            System.arraycopy(bytes, offset, _bytes, 0, size);
        } else { // 否则直接替换对象
            _bytes = bytes;
        }
        return _bytes.length;
    }

    /**
     * 设定指定开始位置byte[]
     *
     * @param pos
     * @param bytes
     * @return
     * @throws SQLException
     */
    public int setBytes(long pos, byte[] bytes) throws SQLException {
        // 暂不支持
        nonsupport();
        return setBytes(pos, bytes, 0, bytes.length, true);
    }

    /**
     * 设定byte[]
     *
     * @param pos
     * @param bytes
     * @param offset
     * @param len
     * @return
     * @throws SQLException
     */
    public int setBytes(long pos, byte[] bytes, int offset, int len)
            throws SQLException {
        // 暂不支持
        nonsupport();
        return setBytes(pos, bytes, offset, len, true);
    }

    /**
     * 截取相应部分数据
     *
     * @param len
     * @throws SQLException
     */
    public void truncate(long len) throws SQLException {
        if (len < 0) {
            throw new SQLException("len < 0");
        }
        if (len > _length) {
            throw new SQLException("len > max length");
        }
        _length = (int) len;
    }

    //public static void main(String[] args) {
    //    // 获得一个指定文件的blob
    //    // PS:天地良心啊，没抄袭hibernate写法，无奈的写一样了，不过比他多实现了点方法……（还特意把函数改名，不然更像|||）……
    //    Blob blob = Loon.makeBlob("D:\test.txt");
    //    // 以byte[]获得blob实例
    //    // Blob blob = new BlobImpl(bytes);
    //    try {
    //        // getBytes测试
    //        // 取出0到blob结束范围的byte[]
    //        byte[] buffer = blob.getBytes(0, (int) blob.length());
    //        // 以gb2312编码将byte[]转为string显示
    //        System.out.println(new String(buffer, "gb2312"));
    //
    //        // getBinaryStream测试
    //        BufferedInputStream is = new BufferedInputStream(
    //                blob.getBinaryStream());
    //        buffer = new byte[(int) blob.length()];
    //        int len = buffer.length;
    //        int offset = 0;
    //        int read = 0;
    //        while (offset < len
    //                && (read = is.read(buffer, offset, len - offset)) >= 0) {
    //            offset += read;
    //        }
    //        System.out.println(new String(buffer, "gb2312"));
    //
    //        // getBinaryStream范围取值测试,取0to4的byte[]
    //        is = new BufferedInputStream(blob.getBinaryStream(0, 4));
    //        // 将is转为byte[]后转为String显示
    //        System.out.println(FileHelper.readToString(is, "gb2312"));
    //
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //
    //}

}

