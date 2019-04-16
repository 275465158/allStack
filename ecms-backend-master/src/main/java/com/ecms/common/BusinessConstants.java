package com.ecms.common;

public interface BusinessConstants {

    Integer STATUS_CONFIRM = 2;
    Integer STATUS_DEFAULT = 1;
    Integer STATUS_INVALID = 0;

    /**星期**/
    String MONDAY_NUMSTR = "1";
    String TUESDAY_NUMSTR = "2";
    String WEDNESDAY_NUMSTR = "3";
    String THURSDAY_NUMSTR = "4";
    String FRIDAY_NUMSTR = "5";
    String SATURDAY_NUMSTR = "6";
    String SUNDAY_NUMSTR = "0";
    Integer SUNDAY_NUM = 7;

    /**最大月数**/
    Integer MAX_MONTH = 13;

    /**工作状态1：正常上班，2：休息**/
    Integer WORK_DAY = 1;
    Integer REST_DAY = 2;

    /**休息日工时**/
    Double REST_WORK_HOURS = 0.0;
    Integer DEFAULT_PAGE_SIZE = 20;
    //人员查询不分页，设置当前页面size为10000条
    Integer PAGE_SIZE = 10000;

    /**订单管理待生产状态**/
    Integer PLAN_WAIT_PRODUCE = 2;
    Integer PLAN_WAIT_DELIVERY = 4;

    /**生产检测状态 1：未检测 2：已检测**/
    Integer PRODUCT_UNCHECK_FLAG = 1;
    Integer PRODUCT_CHECK_FLAG = 2;

    /**设置默认工艺 1:默认 0:不是默认**/
    Integer DEFAULT_PROCESS = 1;
    Integer UN_DEFAULT_PROCESS = 0;

    /**采购原因1：数量，2：批量**/
    Integer PURCHASE_NUMBER = 1;
    Integer PURCHASE_BATCH = 2;


    /**入库状态2:待入库,3:已入库**/
    Integer STAY_INBOUND_STATUS = 2;
    Integer ALREADY_INBOUND_STATUS = 3;

    /**检测状态**/
    Integer CHECK_STAY_STATUS = 0;
    Integer CHECK_ALREADY_STATUS = 1;

    /**生产状态1:待生产，2：完成生产**/
    Integer ALREADY_PRODUCTION = 2;
    Integer STAY_PRODUTION = 1;

    /**物料来源1:来料，2：生产**/
    Integer INCOMING_MATERIAL = 1;
    Integer PRODUCTION_MATERIAL = 2;

    /**
     * 物料状态1：备齐，2:缺失
     **/
    Integer READY_STATUS = 1;
    Integer LACK_STATUS = 2;

    /**
     * 生产状态1：未完成，2：完成(待入库)，3：已入库,
     */
    Integer PRODUCTION_STATUS_UNCOMPLETE = 1;
    Integer PRODUCTION_STATUS_COMPLETE = 2;
    Integer PRODUCTION_STATUS_INBOUND = 3;

    /**
     * 产品类型0：物料 1：产品
     **/
    Integer MATERIAL_TYPE = 0;
    Integer PRODUCT_TYPE = 1;

    /**
     * 发料flag 1：计划单 2：返工返修
     **/
    Integer PRODUCE_PLAN = 1;
    Integer PRODUCE_AGAIN = 2;

    /**
     * 检验类型0：来料，1：生产
     */
    Integer INCOMING_CHECK = 0;
    Integer PRODUCE_CHECK = 1;

    /**
     * 生产制程完成按钮
     */
    Integer START_PRODUCT = 1;
    Integer COMPLETE_PRODUCE = 2;

    /**
     * 制程是否需要检测，0：不需要，1：需要
     */
    Integer PROCESS_NO_CHECK = 0;
    Integer PROCESS_NEED_CHECK = 1;

    /**
     * 制程是否已经完成0：未完成,1：已完成
     */
    Integer PROCESS_NO_COMPLETE = 0;
    Integer PROCESS_COMPLETE = 1;

    /**
     * 物料是否合格1-合格，0-不合格
     */
    Integer UNQUALIFIED_FLAG = 0;
    Integer QUALIFIED_FLAG = 1;

    /**
     * 待采购单下单状态 0未下单 1已下单
     */
    Integer NO_ORDER = 0;
    Integer ALREADY_ORDER = 1;

    /**
     * 判断是否被使用
     */
    Integer USE_STATUS = 1;
    Integer UN_USE_STATUS = 0;
    /**
     * 审核状态0:审核通过，1:审核中，2:待审核，3:审核不通过 4:停用
     */
    Integer AUDITING_PASS = 0;
    Integer IN_AUDITING = 1;
    Integer STAY_AUDITING = 2;
    Integer AUDITING_NO_PASS = 3;
    Integer BLOCK_UP = 4;

    /**
     * 库存status 0:入库，1:出库，2:发料，3:发货
     */

    Integer IN_REPERTORY=0;
    Integer OUT_REPERTORY=1;
    Integer DISPATCH_MATERIAL=2;
    Integer DISPATCH_CARGO=3;

    /**
     * 订单状态0：未完成，1：已完成
     */
    Integer UNFINISHED_ORDER = 0;
    Integer ALREADY_FINISHED_ORDER = 1;

    /**
     * 功能模块对应工作流命名 workflowKey
     */
    //物料列表
    String WORKFLOW_MATERIAL_BOM = "MaterialBomReview";
    //采购订单
    String WORKFLOW_PURCHASE_ORDER = "PurchaseOrderReview";
}
