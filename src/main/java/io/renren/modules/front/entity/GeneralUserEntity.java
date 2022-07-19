package io.renren.modules.front.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 一般用户
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-18 14:22:59
 */
@Data
@TableName("ft_general_user")
public class GeneralUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * 创建者ID
	 */
	private Long createUserId;
	/**
	 * 用户头像
	 */
	private String avatar;
	/**
	 * 一般用户权限 逗号分开
	 */
	private String permissionsIds;
	/**
	 * 用户简历
	 */
	private Integer resumeId;
	/**
	 * 收到的消息 逗号分开，消息那边也冗余保存下用户名
	 */
	private String messageIds;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updatedTime;

}
