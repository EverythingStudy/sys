package cn.staitech.common.log.elasticsearchRepositories;

import cn.staitech.system.api.domain.document.SysOperLogDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OperLogESRepository extends ElasticsearchRepository<SysOperLogDoc, Long> {
}
