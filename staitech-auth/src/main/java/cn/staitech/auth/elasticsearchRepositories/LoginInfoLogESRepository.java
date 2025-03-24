package cn.staitech.auth.elasticsearchRepositories;

import cn.staitech.system.api.domain.document.SysLoginInfoDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LoginInfoLogESRepository extends ElasticsearchRepository<SysLoginInfoDoc, Long> {

}
