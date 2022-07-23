package io.renren.modules.front.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 资格证书表
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-23 17:11:00
 */
@Data
@TableName("ft_certificate")
public class CertificateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId()
	private Long id;

	/**
	 * uuid
	 */
	private String uuid;
	/**
	 * 资格证书名称
	 */
	private String name;
	/**
	 * 取得日期
	 */
	private String date;

	private Long resumeId;

}
