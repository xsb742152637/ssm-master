package util.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//附件对象
public class Attachment {
    private String relativePath;//相对路径
    private Path path;
    private String name;

    Attachment(final String relativePath) {
        if (!WebPathUtils.isRelativePath(relativePath)) {
            throw new IllegalArgumentException("错误的相对路径.");
        }
        this.path = Paths.get(WebPathUtils.getAbsolutePathByRelativePath(relativePath));
        this.relativePath = relativePath;
        this.name = this.path.getFileName().toString();
    }

    public String getRelativePath() { return relativePath; }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * 得到下载附件路径
     *
     * @return 返回下载附件路径
     */
    public String getDownloadUri() {
        return this.getRequestUri(AttachmentUtils._downloadServet);
    }

    /**
     * 得到显示附件路径
     *
     * @return 返回显示附件路径
     */
    public String getDisplayUri() {
        return this.getRequestUri(AttachmentUtils._displayServet);
    }

    /**
     * 得到 WebDav 访问文件路径
     *
     * @return 返回WebDav 访问文件路径
     */
    public String getWebDavUri() {
        return this.getRequestUri(AttachmentUtils._webDavServet);
    }

    /**
     * 得到 FileInfo 访问文件路径
     *
     * @return 返回 FileInfo 访问文件路径
     */
    public String getFileInfoUri() {
        return this.getRequestUri(AttachmentUtils._fileInfoServet);
    }

    //得到文件大小
    public long getSize() {
        try {
            return Files.size(this.path);
        } catch (IOException ignored) {
        }
        return 0;
    }

    private String getRequestUri(String requestKey) {
        return requestKey + this.getRelativePath();
    }
}
