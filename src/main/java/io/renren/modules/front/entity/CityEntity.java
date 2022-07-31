package io.renren.modules.front.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 工作地点
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-08 19:48:10
 */
@Data
@TableName("ft_city")
public class CityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 工作地点
	 */
	private String name;

}
