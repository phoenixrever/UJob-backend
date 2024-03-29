package io.renren.modules.front.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户案件信息表
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-25 20:44:20
 */
@Data
@TableName("ft_user_case_info")
public class UserCaseInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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

	/**
	 * 案件类型 0 正社员 1 IT案件 默认0
	 */
	private Integer caseType;

	private Integer favorite;
	private Date favoriteTime;

	/**
	 * 是否投递过简历
	 */
	private Integer delivery;
	private Date deliveryTime;

	/**
	 * 可能在在列表投递，没有点进去看,点进去看了为1，多次点击++
	 */
	private Integer visited;


	/**
	 * 案件查看时间
	 */
	private Date visitedTime;
	/**
	 * 投递简历是否被公司查看过
	 */
	private Integer checked;

	/**
	 * 简历查看时间
	 */
	private Date checkedTime;

	/**
	 * 待沟通
	 */
	private Integer communicate;
	/**
	 * 待沟通时间
	 */
	private Date communicateTime;

	/**
	 * 邀请面试
	 */
	private Integer interview;
	/**
	 * 邀请面试时间
	 */
	private Date interviewTime;
	/**
	 * 不合适
	 */
	private Integer unqualified;
	/**
	 * 拒绝时间
	 */
	private Date unqualifiedTime;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createdTime;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updatedTime;

	private Long businessUserId;
}
