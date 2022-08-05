package io.renren.modules.front.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class ItCaseDetailVo {
    private Long id;
    /**
     * 案件名称
     */
    private String name;

    /**
     * 开发语言 "," 分割
     */
    private String language;
    /**
     * 使用数据库 ","分割
     */
    private String db;
    /**
     * 使用操作系统 使用数据库 ","分割
     */
    private String os;
    /**
     * 工作经验
     */
    private String experience;
    /**
     * 日语能力
     */
    private String japanese;
    /**
     * 勤务城市
     */
    private String city;
    /**
     * 勤务地区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 电车线路
     */
    private String line;
    /**
     * 最近车站
     */
    private String station;

    /**
     * 交通时间
     */
    private String distance;
    /**
     * 中国语对应 0 否 1 是
     */
    private Integer chinese;
    /**
     * 薪水
     */
    private String salary;

    /**
     * 案例详情 待定 会改
     */
    private String detail;
    private String startDate;
    private String endDate;


    private Integer pg;

    private Integer se;

    private Integer bse;

    private Integer leader;


    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;

    private Long businessUserId;
    private String companyName;

    private Integer delivered;
    private Date deliveredTime;

    private Integer favorite;
    private Integer caseType=1;

}
