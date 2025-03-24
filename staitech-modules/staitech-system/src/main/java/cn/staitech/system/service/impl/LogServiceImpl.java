package cn.staitech.system.service.impl;

import cn.staitech.common.core.constant.HttpStatus;
import cn.staitech.common.core.utils.DateUtils;
import cn.staitech.common.core.web.page.TableDataInfo;
import cn.staitech.common.security.auth.AuthLogic;
import cn.staitech.common.security.utils.SecurityUtils;
import cn.staitech.system.api.domain.document.SysLoginInfoDoc;
import cn.staitech.system.api.domain.document.SysOperLogDoc;
import cn.staitech.system.service.ILogService;
import cn.staitech.system.utils.MessageSource;
import org.apache.commons.collections4.MapUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author mugw
 * @version 1.0
 * @description 日志管理
 * @date 2023/5/26 11:12:11
 */
@Service
public class LogServiceImpl implements ILogService {

    private Integer pageNum;

    private Integer pageSize;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public TableDataInfo queryLoginLogByPage(Map params) throws Exception {
        //查询参数处理
        doPageParams(params);
        String userName = MapUtils.getString(params, "userName", "");
        //if (pageSize!=5&&"".equals(userName)){
        if (pageSize != 5) {
            AuthLogic authLogic = new AuthLogic();
            authLogic.checkPermiAnd("system:log:login");
        }
        //7.17需求调整添加查询权限，admin查所有其他用户查询自己
        Set<String> set = SecurityUtils.getLoginUser().getRoles();
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        set.forEach(u -> {
            if ("admin".equals(u)) {
                isAdmin.set(true);
            }
        });
        if (isAdmin.get()) {
            params.put("userName", userName);
        } else {
            params.put("userName", SecurityUtils.getUsername());
        }
        SearchHits<SysLoginInfoDoc> searchHits = elasticsearchRestTemplate.search(buildLoginLogNativeSearchQuery(params, true), SysLoginInfoDoc.class);
        List<SearchHit<SysLoginInfoDoc>> list = searchHits.getSearchHits();
        return getTableDataInfo(list, Integer.parseInt(String.valueOf(searchHits.getTotalHits())));
    }

    @Override
    public List<SysLoginInfoDoc> queryLoginLog(Map params) throws Exception {
        //查询参数处理
        SearchHits<SysLoginInfoDoc> searchHits = elasticsearchRestTemplate.search(buildLoginLogNativeSearchQuery(params, false), SysLoginInfoDoc.class);
        List<SearchHit<SysLoginInfoDoc>> list = searchHits.getSearchHits();
        List<SysLoginInfoDoc> loginInfoDocs = new ArrayList<>();
        for (SearchHit<SysLoginInfoDoc> hit : list) {
            loginInfoDocs.add(hit.getContent());
        }
        return loginInfoDocs;
    }

    private NativeSearchQuery buildLoginLogNativeSearchQuery(Map params, boolean isPage) {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String userName = MapUtils.getString(params, "userName", "");
        if (!"".equals(userName)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("userName", "*" + userName + "*"));
        }
        String ipaddr = MapUtils.getString(params, "ipaddr", "");
        if (!"".equals(ipaddr)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("ipaddr", "*" + ipaddr + "*"));
        }
        String status = MapUtils.getString(params, "status", "");
        if (!"".equals(status)) {
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("status", status));
        }
        processTimeRange(boolQueryBuilder, params);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withSort(Sort.by(DESC, "infoId"));
        if (isPage) {
            //2023-08-02解决分页限制
            nativeSearchQueryBuilder.withTrackTotalHits(true);
            Pageable pageable = PageRequest.of(pageNum, pageSize);
            nativeSearchQueryBuilder.withPageable(pageable);
        }
        return nativeSearchQueryBuilder.build();
    }


    /**
     * 操作日志分页查询
     *
     * @param params
     * @return
     */
    @Override
    public TableDataInfo queryOperLogByPage(Map params) throws Exception {
        doPageParams(params);
        //7.17需求调整添加查询权限，admin查所有其他用户查询自己
        String operName = SecurityUtils.getUsername();
        Set<String> set = SecurityUtils.getLoginUser().getRoles();
        AtomicBoolean isAdmin = new AtomicBoolean(false);
        set.forEach(u -> {
            if ("admin".equals(u)) {
                isAdmin.set(true);
            }
        });
        if (!isAdmin.get()) {
            params.put("operName", operName);
        }
        SearchHits<SysOperLogDoc> searchHits = elasticsearchRestTemplate.search(buildOperLogNativeSearchQuery(params, true), SysOperLogDoc.class);
        List<SearchHit<SysOperLogDoc>> list = searchHits.getSearchHits();
        return getTableDataInfo(list, Integer.parseInt(String.valueOf(searchHits.getTotalHits())));
    }

    /**
     * 操作日志列表查询
     *
     * @param params
     * @return
     */
    @Override
    public List<SysOperLogDoc> queryOperLog(Map params) throws Exception {
        SearchHits<SysOperLogDoc> searchHits = elasticsearchRestTemplate.search(buildOperLogNativeSearchQuery(params, false), SysOperLogDoc.class);
        List<SearchHit<SysOperLogDoc>> list = searchHits.getSearchHits();
        List<SysOperLogDoc> operLogDocs = new ArrayList<>();
        for (SearchHit<SysOperLogDoc> hit : list) {
            operLogDocs.add(hit.getContent());
        }
        return operLogDocs;
    }

    private NativeSearchQuery buildOperLogNativeSearchQuery(Map params, boolean isPage) {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String title = MapUtils.getString(params, "title", "");
        if (!"".equals(title)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("title", "*" + title + "*"));
        }
        String menu = MapUtils.getString(params, "menu", "");
        if (!"".equals(menu)) {
            //boolQueryBuilder.must(QueryBuilders.multiMatchQuery(title,"title","menu","subMenu"));
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(menu, "menu", "subMenu"));
            //boolQueryBuilder.must(QueryBuilders.matchQuery("menu",title));
        }
        String operName = MapUtils.getString(params, "operName", "");
        if (!"".equals(operName)) {
            //boolQueryBuilder.must(QueryBuilders.matchQuery("operName",operName));
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("operName", "*" + operName + "*"));
        }
        String operIp = MapUtils.getString(params, "operIp", "");
        if (!"".equals(operIp)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("operIp", "*" + operIp + "*"));
        }
        String businessType = MapUtils.getString(params, "businessType", "");
        if (!"".equals(businessType)) {
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("businessType", businessType));
        }
        String status = MapUtils.getString(params, "status", "");
        if (!"".equals(status)) {
            boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("status", status));
        }
        processTimeRange(boolQueryBuilder, params);
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        nativeSearchQueryBuilder.withSort(Sort.by(DESC, "operId"));
        if (isPage) {
            //2023-08-02解决分页限制
            nativeSearchQueryBuilder.withTrackTotalHits(true);
            Pageable pageable = PageRequest.of(pageNum, pageSize);
            nativeSearchQueryBuilder.withPageable(pageable);
        }
        return nativeSearchQueryBuilder.build();
    }

    /**
     * 时间段查询条件处理
     *
     * @param boolQueryBuilder
     * @param params
     */
    private void processTimeRange(BoolQueryBuilder boolQueryBuilder, Map params) {
        //处理查询时间参数
        Map accessTimeMap = (Map) params.get("accessTime");
        if (accessTimeMap != null && !MapUtils.getString(accessTimeMap, "beginTime", "").equals("")) {
            String beginTime = MapUtils.getString(accessTimeMap, "beginTime", "");
            String endTime = MapUtils.getString(accessTimeMap, "endTime");
            boolQueryBuilder.must(QueryBuilders.rangeQuery("time")
                    .gt(DateUtils.dateTime(DateUtils.YYYY_MM_DD, beginTime).getTime())
                    .lt(DateUtils.dateTime(DateUtils.YYYY_MM_DD, endTime).getTime() + (3600 * 1000 * 24 - 1)));
        }
    }

    private TableDataInfo getTableDataInfo(List list, int total) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setMsg(MessageSource.M("QUERY_SUCCESS"));
        rspData.setTotal(total);
        return rspData;
    }

    private void doPageParams(Map params) {
        pageNum = MapUtils.getInteger(params, "pageNum", 0) == 0 ? 0 : MapUtils.getInteger(params, "pageNum") - 1;
        pageSize = MapUtils.getInteger(params, "pageSize", 10);
    }
}
