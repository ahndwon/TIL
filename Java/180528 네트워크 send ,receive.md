# 180528 네트워크 send ,receive
```java
public static void sendFile(DataOutputStream out, String fileName) throws IOException {
    FileInputStream fis = new FileInputStream(fileName);
    try {
        byte[] bytes = new byte[8192];
        int len;
        out.writeInt((int) fis.getChannel().size());
        while ((len = fis.read(bytes)) > 0)
            out.write(bytes, 0, len);
    } finally {
        fis.close();
    }
}

public static void recvFile(DataInputStream in, String fileName) throws IOException {
    FileOutputStream fos = new FileOutputStream(fileName);
    try {
        byte[] bytes = new byte[8192];
        int left = in.readInt();
        int len;
        while(left > 0 && (len = in.read(bytes, 0, Math.min(left, bytes.length))) > 0) {
            fos.write(bytes, 0, len);
            left -= len;
        }
    } finally {
        fos.close();
    }
}
```

[file - -Java- Data ‘loss’ in TCP (strange, since TCP doesn’t allow the data to escape) - Stack Overflow](https://stackoverflow.com/questions/11777828/java-data-loss-in-tcp-strange-since-tcp-doesnt-allow-the-data-to-escape)
#더더랩JAVA