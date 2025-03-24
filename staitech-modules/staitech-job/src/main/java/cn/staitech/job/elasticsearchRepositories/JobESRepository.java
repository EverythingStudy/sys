package cn.staitech.job.elasticsearchRepositories;

import cn.staitech.job.domain.document.SysJobLogDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface JobESRepository extends ElasticsearchRepository<SysJobLogDoc,Long>{

}
