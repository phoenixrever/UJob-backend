package io.renren.modules.front.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserItCaseInfoVo {
  /**
   * id
   */
  @TableId
  private Long id;
  /**
   * 用户id
   */
  private Long userId;
  /**
   * 案件id
   */
  private Long caseId;

  private Integer caseType;

  private Integer favorite;
  /**
   * 是否投递过简历
   */
  private Integer delivery;
  /**
   * 可能在在列表投递，没有点进去看,点进去看了为1，多次点击++
   */
  private Integer visited;
  /**
   * 投递简历是否被公司查看过
   */
  private Integer checked;
  /**
   * 待沟通
   */
  private Integer communicate;
  /**
   * 邀请面试
   */
  private Integer interview;
  /**
   * 不合适
   */
  private Integer unqualified;

  /**
   * 创建时间
   */
  private Date createdTime;

  /**
   * 更新时间
   */
  private Date updatedTime;


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
   * 使用操作系统
   */
  private String os;
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
  private String startDate;
  private String endDate;


  private Integer pg;

  private Integer se;

  private Integer bse;

  private Integer leader;

  private Long businessUserId;

}
