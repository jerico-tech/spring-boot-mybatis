# 分布式ID
使用雪花算法生成分布式id，为了保证不同数据中心和节点生成的id不重复,要做到以下两点：
1. 要保证不同数据中心和节点时钟一致;
2. 保证workId和dataCenterId不同，由于同一个包在不同数据中心和节点部署，为了保证两个值不同，需要按数据中心和节点进行分配和配置（环境变量或者配置中心）；