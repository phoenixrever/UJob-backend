package io.renren.modules.front.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserJobInfoVo {
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
  private Integer reject;

  /**
   * 创建时间
   */
  private Date createdTime;

  /**
   * 更新时间
   */
  private Date updatedTime;



  /**
   * 职位名称
   */
  private String jobName;
  /**
   * 中国语对应 0 否 1 是
   */
  private Integer chinese;

  /**
   * 职位类型
   */
  private Integer jobType;

  /**
   * 雇佣类型
   */
  private String workType;

  /**
   * 是否在宅 0 否 1 是
   */
  private String workHome;

  /**
   * 勤务城市
   */
  private Integer city;
  /**
   * 勤务地区
   */
  private Integer area;

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
  private Integer line;
  /**
   * 最近车站
   */
  private Integer station;

  /**
   * 交通时间
   */
  private String distance;
  /**
   * 日语能力
   */
  private Integer japanese;
  /**
   * 公司训练 0 有料 1 无料
   */
  private Integer companyTrain;
  /**
   * 工作经验 0 无 1 有
   */
  private Integer experience;

  /**
   * 支付方式 0 月给 1 时给 2 日给
   */
  private Integer paidType;

  /**
   * 薪水
   */
  private String salary;

  /**
   * 雇佣人数
   */
  private Integer hiredNumber;

  /**
   * 福利标签 "," 分割
   */
  private String feature;

  /**
   * 工作时间
   */
  private String workTime;

  /**
   * 工作内容
   */
  private String workDetail;

  private Long businessUserId;
  private String companyName;
}
