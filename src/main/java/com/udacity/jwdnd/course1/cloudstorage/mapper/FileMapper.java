package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {

    @Select("SELECT filename FROM FILES WHERE userid = #{userid}")
    public List<String> getFileNames(int userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    public List<File> getAllFiles(int userid);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    public File getFile(Integer fileId);

    @Insert("INSERT INTO FILES " +
        "(filename, contenttype, filesize, userid, filedata)" +
        " VALUES (#{filename}, #{contentType}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int storeFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileid}")
    public void deleteFile(Integer fileid);
}