# 目录说明

# tiklab-kaelthas-agent



## tiklab-kaelthas-databasesAgent

| 目录    | 说明                                   |
| ------- | -------------------------------------- |
| dao     | 查询数据库信息，插入数据库采集的数据   |
| model   | 存储数据表的model和数据库监控项的model |
| service | mysql和pgsql的定时任务执行             |
| utils   | 数据库agent的工具类                    |



## tiklab-kaelthas-internetAgent

| 目录    | 说明                                                   |
| ------- | ------------------------------------------------------ |
| dao     | 查询网络监控项信息                                     |
| model   | 监控项的model                                          |
| service | 概况页面的数据采集和指标的定时数据采集，并且插入数据库 |
| utils   | 数据库agent的工具类                                    |



## tiklab-kaelthas-kubernetesAgent

| 目录        | 说明                                |
| ----------- | ----------------------------------- |
| dao         | 查询k8s的监控项信息                 |
| nodeItem    | node类型的监控项，方法类            |
| service     | 定时任务采集k8s的概况信息和配置信息 |
| serviceItem | service类型的监控项，方法类         |
| utils       | k8s的工具类                         |



# tiklab-kaelthas-api



## alarm

告警信息模块



## db

| 目录              | 说明                                 |
| ----------------- | ------------------------------------ |
| customize         | 自定义SQL的功能，这个功能没有开发    |
| databases         | 检控数据库列表的增删改查             |
| dbDynamic         | 监控数据库的动态信息                 |
| dbGraphics        | 监控数据库的图形配置模块             |
| dbGraphicsMonitor | 监控数据库的图形和监控项关联模块     |
| dbItem            | 监控数据库的字典表模块               |
| dbTrigger         | 监控数据库的触发器                   |
| dbTriggerMedium   | 监控数据库中触发器和告警渠道的中间表 |



## history

上报数据的汇总表，agent上报的数据都存放在这张表中，此外还有一分钟、五分钟、十五分钟的表。



## host

| 目录            | 说明                                       |
| --------------- | ------------------------------------------ |
| dynamic         | 主机的动态表，之前首页展示，当前没有展示   |
| graphics        | 主机下的图形信息                           |
| graphicsMonitor | 主机下图形和监控项的关联表                 |
| home            | 首页的请求接口存放目录                     |
| host            | 主机的增删改查                             |
| hostDynamic     | 主机下的动态信息，例如创建监控项           |
| hostGroup       | 主机组的增删改查                           |
| hostRecent      | 常用主机存放表，之前首页展示，当前没有展示 |



## internet

| 目录                    | 说明                               |
| ----------------------- | ---------------------------------- |
| internet                | 网络监控的信息                     |
| internetGraphics        | 网络监控下的图形信息               |
| internetGraphicsMonitor | 网络监控中图形和监控项的关联表     |
| internetItem            | 网络监控中监控项的字典表           |
| internetMoinitor        | 网络监控的监控项                   |
| internetTrigger         | 网络监控的触发器                   |
| internetTriggerMedium   | 网络监控中触发器与消息渠道的关联表 |



## kubernetes

| 目录                      | 说明                               |
| ------------------------- | ---------------------------------- |
| kubernetesGraphics        | 网络监控下的图形信息               |
| kubernetesGraphicsMonitor | 网络监控中图形和监控项的关联表     |
| kubernetesInfo            | 网络监控的信息                     |
| kubernetesItem            | 网络监控中监控项的字典表           |
| kubernetesMoinitor        | 网络监控的监控项                   |
| kubernetesTrigger         | 网络监控的触发器                   |
| kubernetesTriggerMedium   | 网络监控中触发器与消息渠道的关联表 |



## medium

消息渠道的字典表，企业微信、邮件、站内信等。



# tiklab-kaelthas-service



## alarm

告警信息模块



## common

| 目录      | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| timer     | 定时扫描监控主机、监控数据库、监控k8s、监控网络的状态，修改状态 |
| websocket | 收集agent上报数据的类                                        |



## db

| 目录              | 说明                                 |
| ----------------- | ------------------------------------ |
| customize         | 自定义SQL的功能，这个功能没有开发    |
| databases         | 检控数据库列表的增删改查             |
| dbDynamic         | 监控数据库的动态信息                 |
| dbGraphics        | 监控数据库的图形配置模块             |
| dbGraphicsMonitor | 监控数据库的图形和监控项关联模块     |
| dbItem            | 监控数据库的字典表模块               |
| dbTrigger         | 监控数据库的触发器                   |
| dbTriggerMedium   | 监控数据库中触发器和告警渠道的中间表 |



## history

上报数据的汇总表，agent上报的数据都存放在这张表中，此外还有一分钟、五分钟、十五分钟的表。



## host

| 目录            | 说明                                       |
| --------------- | ------------------------------------------ |
| dynamic         | 主机的动态表，之前首页展示，当前没有展示   |
| graphics        | 主机下的图形信息                           |
| graphicsMonitor | 主机下图形和监控项的关联表                 |
| home            | 首页的请求接口存放目录                     |
| host            | 主机的增删改查                             |
| hostDynamic     | 主机下的动态信息，例如创建监控项           |
| hostGroup       | 主机组的增删改查                           |
| hostRecent      | 常用主机存放表，之前首页展示，当前没有展示 |



## internet

| 目录                    | 说明                               |
| ----------------------- | ---------------------------------- |
| internet                | 网络监控的信息                     |
| internetGraphics        | 网络监控下的图形信息               |
| internetGraphicsMonitor | 网络监控中图形和监控项的关联表     |
| internetItem            | 网络监控中监控项的字典表           |
| internetMoinitor        | 网络监控的监控项                   |
| internetTrigger         | 网络监控的触发器                   |
| internetTriggerMedium   | 网络监控中触发器与消息渠道的关联表 |



## kubernetes

| 目录                      | 说明                               |
| ------------------------- | ---------------------------------- |
| kubernetesGraphics        | 网络监控下的图形信息               |
| kubernetesGraphicsMonitor | 网络监控中图形和监控项的关联表     |
| kubernetesInfo            | 网络监控的信息                     |
| kubernetesItem            | 网络监控中监控项的字典表           |
| kubernetesMoinitor        | 网络监控的监控项                   |
| kubernetesTrigger         | 网络监控的触发器                   |
| kubernetesTriggerMedium   | 网络监控中触发器与消息渠道的关联表 |



## medium

消息渠道的字典表



## util

使用的工具类存放文件夹