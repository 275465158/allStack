package com.ecms.dal.mapper;

import com.ecms.common.BasicMapper;
import com.ecms.dal.entity.VersionEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VersionMapper extends BasicMapper<VersionEntity> {
    @Results(id = "versionMap", value= {
        @Result(column="app_type", property="appType"),
        @Result(column="app_version", property="appVersion"),
    })
    @Select({
        "select ",
        "id, app_type, app_version, name, status, created, updated",
        "from version"})
    List<VersionEntity> selectAll();

    @ResultMap("versionMap")
    @Select("select * from version where app_type=#{appType} and status=1 order by id desc limit 1")
    VersionEntity selectLatestVersion(int appType);
}