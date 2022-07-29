package io.renren.modules.front.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CaseDetailVo {

    private Integer id;
    /**
     * 案件名称
     */
    private String name;
    /**
     * 业务分野
     */
    private String menu;

    /**
     * 福利标签 "," 分割
     */
    private String feature;

    /**
     * 工作经验
     */
    private String experience;
    /**
     * 工作地点
     */
    private String area;
    /**
     * 日语能力
     */
    private String japanese;
    /**
     * 中国语对应 0 否 1 是
     */
    private String chinese;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 最近车站
     */
    private String station;
    /**
     * 薪水
     */
    private String salary;
    /**
     * 交通方式
     */
    private String distance;
    /**
     * 案例详情 待定 会改
     */
    private String detail;
    /**
     *
     */
    private String startDate;
    /**
     *
     */
    private String endDate;
    /**
     *
     */
    private Date createdTime;
    /**
     *
     */
    private Date updatedTime;

    private Long businessUserId;
}
