package com.yxkj.entity.commonenum;

/**
 * 公共枚举数据
 * 
 * @author shijun
 *
 */
public class CommonEnum {


  /**
   * 系统类型
   */
  public enum SystemType {
    /** 运营后台 0 */
    OPERATION,
    /** 售货机APP 1 */
    MACHINE_APP,
    /** 用户APP 2 */
    USER_APP,
    /** 微信公众号 3 */
    WECHAT_PUBLIC,
    /** 配货员app 4 */
    DELIVERY_APP

  }

  /**
   * 通用状态
   */
  public enum CommonStatus {

    /** 可用 */
    ACITVE,
    /** 不可用 */
    INACTIVE
  }


  /**
   * 帐号状态
   */
  public enum AccountStatus {

    /** 帐号正常 0 */
    ACTIVED,

    /** 帐号禁用 1 */
    LOCKED,

    /** 帐号删除 2 */
    DELETE
  }

  /**
   * 性别
   */
  public enum Gender {

    /** 男 */
    MALE,

    /** 女 */
    FEMALE
  }



  /**
   * 短信验证码类型
   * 
   * @author sujinxuan
   *
   */
  public enum SmsCodeType {
    /** 注册 */
    REG,
    /** 登录 */
    LOGIN,
    /** 重置密码 */
    RESETPWD,
    /** 修改登录密码 */
    UPDATELOGINPWD,
    /** 修改支付密码 */
    UPDATEPAYPWD,
    /** 银行预留手机号验证 */
    RESERVEDMOBILE,
    /** 转账 */
    TRANSFER
  }

  /**
   * 审核状态
   * 
   *
   */
  public enum ApplyStatus {
    /** 待审核 0 */
    AUDIT_WAITING,

    /** 审核通过 1 */
    AUDIT_PASSED,

    /** 审核退回 2 */
    AUDIT_FAILED

  }

  /**
   * 代理级别
   * 
   *
   */
  public enum AgencyLevel {
    /** 省 */
    PROVINCE,
    /** 市 */
    CITY,
    /** 区,县 */
    COUNTY

  }

  /**
   * 系统参数配置
   * 
   *
   */
  public enum SystemConfigKey {
  }

  /**
   * 数据字典配置项名称
   * 
   *
   */
  public enum DictKey {
    /** 软件许可协议 0 */
    LICENSE_AGREEMENT,
    /** 客服电话 1 */
    CUSTOMER_PHONE,
    /** 关于 2 */
    ABOUT_US,
    /** 支付宝商户名 3 */
    ALIPAY_COMPANY,
    /** 支付宝商户编号 4 */
    ALIPAY_ACCOUNT,
    /** 微信商户名 5 */
    WECHAT_COMPANY,
    /** 微信应用ID 6 */
    WECHAT_ACCOUNT
  }


  /**
   * app platform
   * 
   */
  public enum AppPlatform {
    /** android 0 */
    ANDROID,
    /** IOS 1 */
    IOS
  }


  public enum ImageType {
    /** 头像 */
    PHOTO,
    /** 店铺环境 */
    STORE_ENV,
    /** 身份证照片 */
    AUTH_IDCARD
  }

  /**
   * 广告类型
   * 
   * @author Andrea
   * @version 2017年9月18日 下午6:36:18
   */
  public enum FileType {

    /** 图片 0 */
    IMAGE,
    /** 视频 1 */
    VIDEO,
    /** 文件 2 */
    FILE
  }

  public enum OrderStatus {
    /** 未支付 0 */
    UNPAID,
    /** 已支付 1 */
    PAID,
    /** 出货后已完成 2 */
    FINISHED
  }

  /**
   * 手机接口查询排序类型
   * 
   */
  public enum SortType {
    /**
     * 距离由近及远
     */
    DISTANCEASC,
    /**
     * 好评分由高到低
     */
    SCOREDESC,
    /**
     * 收藏由高到低
     */
    COLLECTDESC,
    /** 默认排序（时间先后顺序） */
    DEFAULT
  }
  /**
   * 图片大小
   *
   */
  public enum ImageSize {
    /** 小图 */
    SMALL,
    /** 中图 */
    MIDDLE,
    /** 大图 */
    BIG
  }
  /**
   * 二维码类型
   *
   */
  public enum QrCodeType {
    /** 推广二维码 */
    SHARE,
    /** 支付二维码 */
    PAID
  }

  /**
   * 用户获取渠道
   *
   */
  public enum UseChannel {
    /** 微信 0 */
    WECHAT,
    /** 支付宝 1 */
    ALIPAY
  }


  /**
   * 购买方式
   *
   */
  public enum PurMethod {
    /** 扫码购买 0 */
    SCAN_CODE,
    /** 输码购买 1 */
    INPUT_CODE,
    /** 中控购买 2 */
    CONTROLL_MACHINE,
    /** 在线购买 3 */
    ONLINE
  }


  /**
   * 通知类型
   *
   */
  public enum RemindType {
    /** 上货通知 0 */
    SUPPLY,
    /** 缺货通知 1 */
    LACK
  }

}
