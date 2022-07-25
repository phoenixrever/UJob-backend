package io.renren.modules.front.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 简历表，备注简历信息可能和个人信息填的不一样。
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2022-07-23 17:11:00
 */
@Data
@TableName("ft_resume")
public class ResumeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 姓名
	 */
	private String name;

	private String avatar;

	/**
	 * 平假名姓名
	 */
	private String hiraganaName;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 生日
	 */
	private String birthday;
	/**
	 * 电话号码
	 */
	private String phone;
	/**
	 * 微信号
	 */
	private String wechat;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 详细地址
	 */
	private String areaDetail;
	/**
	 * 线路
	 */
	private String line;
	/**
	 * 駅
	 */
	private String station;
	/**
	 * 搬家预定
	 */
	private Integer moveHouse;
	/**
	 * 再留资格
	 */
	private String visa;
	/**
	 * 来日年数
	 */
	private String year;
	/**
	 * 配偶
	 */
	private Integer spouse;
	/**
	 * 家族
	 */
	private String family;
	/**
	 * 日语水平
	 */
	private String japaneseLevel;
	/**
	 * 日本語能力試験
	 */
	private String jlpt;
	/**
	 * 最终学历
	 */
	private String education;
	/**
	 * 本籍地
	 */
	private String hometown;
	/**
	 * ステイタス
	 */
	private String status;
	/**
	 * 学校名
	 */
	private String schoolName;
	/**
	 * 入学年度
	 */
	private String admissionDate;
	/**
	 * 卒業年度
	 */
	private String graduationDate;
	/**
	 * 学位
	 */
	private String degree;
	/**
	 * 専攻
	 */
	private String major;
	/**
	 * 会社名
	 */
	private String companyName;
	/**
	 * 入社日
	 */
	private String hiredDate;
	/**
	 * 退社日
	 */
	private String leaveDate;
	/**
	 * 资格证书
	 */
	@TableField(exist = false)
	private List<CertificateEntity> certificateEntities;
	/**
	 * 興味
	 */
	private String hobby;
	/**
	 * 特技
	 */
	private String skill;
	/**
	 * 自己紹介
	 */
	private String selfIntro;
	/**
	 * 志望動機
	 */
	private String motivation;

	private Long userId;

}
