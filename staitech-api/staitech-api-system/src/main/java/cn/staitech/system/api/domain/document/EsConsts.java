package cn.staitech.system.api.domain.document;


public interface EsConsts {

    /**
     * 所以index都使用该类型名称
     */
    String DEFAULT_TYPE_NAME = "_doc";

    /**
     * 操作日志索引名称
     */
    String INDEX_OPER_LOG = "operlog";

    /**
     * 登录日志索引名称
     */
    String INDEX_LOGIN_LOG = "loginlog";


    String INDEX_JOB_LOG="joblog";

}
