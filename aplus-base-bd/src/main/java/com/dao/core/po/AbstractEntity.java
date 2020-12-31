package com.dao.core.po;

import com.dao.core.dao.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-12-31 13:45
 */
@Getter
@Setter
public abstract class AbstractEntity implements Entity {
    long id;
    LocalDateTime createTime;
    LocalDateTime updateTime;
    String dbopr;
    long jpaVersion;
    String dsuNo;
    boolean isTimeSet = false;

}
