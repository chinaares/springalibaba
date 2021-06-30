package com.example.jwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jwt.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wh
 * @since 2021-06-25
 */
@Component
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> getPermissionsByUserId(@Param("userId") Integer userId);

    List<SysPermission> findByUrl(String requestUrl);
}
