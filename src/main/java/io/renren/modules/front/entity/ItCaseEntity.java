package io.renren.modules.front.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 案件列表
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@Data
@TableName("ft_it_case")
public class ItCaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
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
	private Integer experience;
	/**
	 * 工作地点
	 */
	private Integer area;
	/**
	 * 日语能力
	 */
	private Integer japanese;
	/**
	 * 中国语对应 0 否 1 是
	 */
	private Integer chinese;
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


	/**
	 * 创建日期
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createdTime;
	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updatedTime;

}
