package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileId;
    private String filename;
    private String contentType;
    private String filesize;
    private int userid;
    private byte[] filedata;

    public File(Integer fileId, String filename, String contentType, String filesize, int userid, byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.filesize = filesize;
        this.userid = userid;
        this.filedata = filedata;
    }

    public Integer getFileId() { return fileId; }
    public void setFileId(Integer fileId) { this.fileId = fileId; }

    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getFilesize() { return filesize; }
    public void setFilesize(String filesize) { this.filesize = filesize; }

    public int getUserid() { return userid; }
    public void setUserid(int userid) { this.userid = userid; }

    public byte[] getFiledata() { return filedata; }
    public void setFiledata(byte[] filedata) { this.filedata = filedata; }
}