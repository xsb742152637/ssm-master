package util.io.file;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Comment：
 * Created by IntelliJ IDEA.
 * User: xiucai
 * Date: 2020/3/23 17:22
 */
@SuppressWarnings("ALL")
public class Directorys {
    public Directorys() {
    }

    public static Path copy(final Path source, Path target, final CopyOption... options) throws IOException {
        if (Files.notExists(source, new LinkOption[0])) {
            throw new NoSuchFileException(source.toString());
        } else if (!Files.isDirectory(source, new LinkOption[0])) {
            throw new NotDirectoryException(source.toString());
        } else {
            checkCopyMove(source, target, options);
            final Path finalTarget = Files.createDirectories(target);
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectories(finalTarget.resolve(source.relativize(dir).toString()));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, finalTarget.resolve(source.relativize(file).toString()), options);
                    return FileVisitResult.CONTINUE;
                }
            });
            return finalTarget;
        }
    }

    public static boolean delete(Path path) throws IOException {
        if (Files.notExists(path, new LinkOption[0])) {
            throw new NoSuchFileException(path.toString());
        } else if (!Files.isDirectory(path, new LinkOption[0])) {
            throw new NotDirectoryException(path.toString());
        } else {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exec) throws IOException {
                    if (!Files.isWritable(dir)) {
                        throw new IOException("“" + dir.toString() + "”不可写.");
                    } else {
                        return FileVisitResult.CONTINUE;
                    }
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (!Files.isWritable(file)) {
                        throw new IOException("“" + file.toString() + "”不可写.");
                    } else {
                        return FileVisitResult.CONTINUE;
                    }
                }
            });
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exec) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }
            });
            return true;
        }
    }

    public static Path move(final Path source, Path target, final CopyOption... options) throws IOException {
        if (Files.notExists(source, new LinkOption[0])) {
            throw new NoSuchFileException(source.toString());
        } else if (!Files.isDirectory(source, new LinkOption[0])) {
            throw new NotDirectoryException(source.toString());
        } else {
            checkCopyMove(source, target, options);
            final Path finalTarget = Files.createDirectories(target);
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectories(finalTarget.resolve(source.relativize(dir).toString()));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exec) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.move(file, finalTarget.resolve(source.relativize(file).toString()), options);
                    return FileVisitResult.CONTINUE;
                }
            });
            return finalTarget;
        }
    }

    private static void checkCopyMove(final Path source, final Path target, CopyOption... options) throws IOException {
        if (!ArrayUtils.contains(options, StandardCopyOption.REPLACE_EXISTING) && Files.exists(target, new LinkOption[0])) {
            throw new FileAlreadyExistsException(target.toString());
        } else {
            Path checkTarget;
            for(checkTarget = target; checkTarget != null; checkTarget = checkTarget.getParent()) {
                if (Files.exists(checkTarget, new LinkOption[0])) {
                    if (!Files.isWritable(checkTarget)) {
                        throw new IOException("“" + target.toString() + "”不可写.");
                    }
                    break;
                }
            }

            if (checkTarget == null) {
                throw new IOException("“" + target + "”不可写.");
            } else {
                Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (!Files.isReadable(dir)) {
                            throw new IOException("“" + dir.toString() + "”不可读.");
                        } else {
                            Path tPath = target.resolve(source.relativize(dir).toString());
                            if (Files.exists(tPath, new LinkOption[0]) && !Files.isWritable(tPath)) {
                                throw new IOException("“" + tPath.toString() + "”不可写.");
                            } else {
                                return FileVisitResult.CONTINUE;
                            }
                        }
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (!Files.isReadable(file)) {
                            throw new IOException("“" + file.toString() + "”不可读.");
                        } else {
                            Path tPath = target.resolve(source.relativize(file).toString());
                            if (Files.exists(tPath, new LinkOption[0]) && !Files.isWritable(tPath)) {
                                throw new IOException("“" + tPath.toString() + "”不可写.");
                            } else {
                                return FileVisitResult.CONTINUE;
                            }
                        }
                    }
                });
            }
        }
    }

    public static long size(Path path) throws IOException {
        if (Files.notExists(path, new LinkOption[0])) {
            throw new NoSuchFileException(path.toString());
        } else if (!Files.isDirectory(path, new LinkOption[0])) {
            throw new NotDirectoryException(path.toString());
        } else {
            final long[] length = new long[]{0L};
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    length[0] += Files.size(file);
                    return FileVisitResult.CONTINUE;
                }
            });
            return length[0];
        }
    }


    public static Collection<Path> getFiles(Path dir, boolean recursive) throws IOException {
        return getFiles(dir, (DirectoryStream.Filter)null, recursive);
    }

    public static Collection<Path> getFiles(Path dir, DirectoryStream.Filter<? super Path> filter, boolean recursive) throws IOException {
        return getPaths(dir, filter, true, recursive);
    }

    public static Collection<Path> getPaths(Path dir, boolean recursive) throws IOException {
        return getPaths(dir, (DirectoryStream.Filter)null, recursive);
    }

    public static Collection<Path> getPaths(Path dir, DirectoryStream.Filter<? super Path> filter, boolean recursive) throws IOException {
        return getPaths(dir, filter, false, recursive);
    }

    private static Collection<Path> getPaths(Path dir, final DirectoryStream.Filter<? super Path> filter, final boolean excludeDir, boolean recursive) throws IOException {
        final DirectoryStream.Filter<? super Path> filter2 = new DirectoryStream.Filter<Path>() {
            public boolean accept(Path entry) throws IOException {
                return true;
            }
        };
        if (Files.notExists(dir, new LinkOption[0])) {
            throw new NoSuchFileException(dir.toString());
        } else if (!Files.isDirectory(dir, new LinkOption[0])) {
            throw new NotDirectoryException(dir.toString());
        } else {

            final List<Path> pathList = new ArrayList();
            if (recursive) {
                Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (!excludeDir && (filter != null && filter.accept(dir) || filter2.accept(dir))) {
                            pathList.add(dir);
                        }

                        return FileVisitResult.CONTINUE;
                    }

                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (filter != null && filter.accept(file) || filter2.accept(file)) {
                            pathList.add(file);
                        }

                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                DirectoryStream<Path> directoryStream;
                if(filter == null){
                    directoryStream = Files.newDirectoryStream(dir, filter2);
                }else{
                    directoryStream = Files.newDirectoryStream(dir, filter);
                }
                Throwable var7 = null;

                try {
                    Iterator i$ = directoryStream.iterator();

                    label115:
                    while(true) {
                        Path tmpPath;
                        do {
                            if (!i$.hasNext()) {
                                break label115;
                            }

                            tmpPath = (Path)i$.next();
                        } while(excludeDir && Files.isDirectory(tmpPath, new LinkOption[0]));

                        pathList.add(tmpPath);
                    }
                } catch (Throwable var17) {
                    var7 = var17;
                    throw var17;
                } finally {
                    if (directoryStream != null) {
                        if (var7 != null) {
                            try {
                                directoryStream.close();
                            } catch (Throwable var16) {
                                var7.addSuppressed(var16);
                            }
                        } else {
                            directoryStream.close();
                        }
                    }

                }
            }

            pathList.remove(dir);
            return pathList;
        }
    }

    public static boolean deleteIfEmpty(Path path) throws IOException {
        if (Files.notExists(path, new LinkOption[0])) {
            throw new NoSuchFileException(path.toString());
        } else if (!Files.isDirectory(path, new LinkOption[0])) {
            throw new NotDirectoryException(path.toString());
        } else {
            try {
                Files.delete(path);
                return true;
            } catch (DirectoryNotEmptyException var2) {
                return false;
            }
        }
    }

    public static void deleteDescendantsAndSelfIfEmpty(Path path) throws IOException {
        deleteDescendantsIfEmpty(path, true);
    }

    private static void deleteDescendantsIfEmpty(final Path path, final boolean andSelf) throws IOException {
        if (Files.notExists(path, new LinkOption[0])) {
            throw new NoSuchFileException(path.toString());
        } else if (!Files.isDirectory(path, new LinkOption[0])) {
            throw new NotDirectoryException(path.toString());
        } else {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (dir.equals(path) && !andSelf) {
                        return FileVisitResult.CONTINUE;
                    } else {
                        try {
                            Files.delete(dir);
                        } catch (DirectoryNotEmptyException var4) {
                            return FileVisitResult.CONTINUE;
                        }

                        return FileVisitResult.SKIP_SIBLINGS;
                    }
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exec) throws IOException {
                    if (dir.equals(path) && !andSelf) {
                        return FileVisitResult.CONTINUE;
                    } else {
                        try {
                            Files.delete(dir);
                        } catch (DirectoryNotEmptyException var4) {
                            ;
                        }

                        return FileVisitResult.CONTINUE;
                    }
                }
            });
        }
    }

    public static void deleteDescendantsIfEmpty(Path path) throws IOException {
        deleteDescendantsIfEmpty(path, false);
    }

    public static void deleteAncestorsIfEmpty(Path path) throws IOException {
        if (Files.notExists(path, new LinkOption[0])) {
            throw new NoSuchFileException(path.toString());
        } else if (!Files.isDirectory(path, new LinkOption[0])) {
            throw new NotDirectoryException(path.toString());
        } else {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    try {
                        Files.delete(dir);
                    } catch (DirectoryNotEmptyException var4) {
                        return FileVisitResult.CONTINUE;
                    }

                    return FileVisitResult.SKIP_SUBTREE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exec) throws IOException {
                    try {
                        Files.delete(dir);
                    } catch (DirectoryNotEmptyException var4) {
                        ;
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
            Path rootPath = path.getRoot();

            for(Path parentPath = path.getParent(); parentPath != null && !rootPath.equals(parentPath); parentPath = parentPath.getParent()) {
                try {
                    Files.delete(parentPath);
                } catch (DirectoryNotEmptyException var4) {
                    break;
                }
            }

        }
    }
}
