package org.uom.cse.cs4262;

public class File {
    private int fileSize;
    private String fileHash;
    private String fileName;

    public int getFileSize() {
        return fileSize;
    }

    public String getFileHash() {
        return fileHash;
    }

    public String getFileName() {
        return fileName;
    }

    public File(int fileSize, String fileHash, String fileName) {
        this.fileSize = fileSize;
        this.fileHash = fileHash;
        this.fileName = fileName;
    }
    public String getFileDetails() {
        return "File Name: " + fileName + " , File Hash: " + fileHash + " , File Size: " + fileSize+"MB";
    }
}
