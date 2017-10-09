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
   * 取货状态
   *
   */
  public enum PickupStatus {
    /** 缺货 */
    LACK,
    /** 待取货 */
    WAIT_PICKUP,
    /** 已取货 */
    PICKUP

  }

  /**
   * 出货状态
   *
   */
  public enum ShipmentStatus {
    /** 未出货 */
    NOT_SHIPMENT,
    /** 出货成功 */
    SHIPMENT_SUCCESS,
    /** 出货失败 */
    SHIPMENT_FAIL
  }

  /**
   * 退款状态
   *
   */
  public enum RefundStatus {
    /** 已退款 */
    REFUNDED,
    /** 未退款 */
    NOT_REFUND
  }

  /**
   * 出货异常
   *
   */
  public enum ShipmentExcpType {
    /** 管家测试出货 */
    KEEPER_TEST,
    /** 客户取货 */
    USER_PICKUP
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
    ABOUT_US
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
  public enum UserChannel {
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
   * 货道商品状态
   *
   */
  public enum ChannelGoodsStatus {
    /** 充足 0 */
    SUFFICIENT,
    /** 缺货(达到预警值) 1 */
    LACK,
    /** 断货 2 */
    EMPTY
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

  /**
   * 警告条件
   *
   */
  public enum WarningCon {
    /** 空柜 0 */
    EMPTY,
    /** 达到预警值 1 */
    EARLY_WARNING
  }
  /**
   * 命令类型
   */
  public enum CmdType {
    /**
     * 出货
     */
    SELL_OUT,
    /**
     * APP升级
     */
    APP_UPDATE,
    /**
     * 广告更新
     */
    AD_UPDATE
  }


  /**
   * 设备类型
   */
  public enum DeviceType {
    /**
     * 无人货架
     */
    SHELF,
    /**
     * 无人货柜
     */
    CONTAINER
  }

  public enum CmdResponse {
    /**
     * 成功
     */
    Success,
    /**
     * 失败
     */
    Failed
  }
}
