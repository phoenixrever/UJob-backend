package io.renren.modules.front.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 案件列表
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-16 23:27:38
 */
@Data
@TableName("ft_job")
public class JobEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Integer id;
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
	private Integer workHome;

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


	private Date createdTime;
	private Date updatedTime;

	private Long businessUserId;
	private String companyName;
}
