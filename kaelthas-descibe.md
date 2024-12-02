# 目录说明

# tiklab-kaelthas-agent



## tiklab-kaelthas-databasesAgent

| 目录    | 说明                                   |
| ------- | -------------------------------------- |
| dao     | 查询数据库信息，插入数据库采集的数据   |
| model   | 存储数据表的model和数据库监控项的model |
| service | mysql和pgsql的定时任务执行             |
| utils   | 数据库监控中agent的工具类              |



## tiklab-kaelthas-internetAgent

| 目录    | 说明                                                   |
| ------- | ------------------------------------------------------ |
| dao     | 查询网络监控项信息                                     |
| model   | 监控项的model                                          |
| service | 概况页面的数据采集和指标的定时数据采集，并且插入数据库 |
| utils   | 网络监控中agent的工具类                                |



## tiklab-kaelthas-kubernetesAgent

| 目录        | 说明                                |
| ----------- | ----------------------------------- |
| dao         | 查询k8s的监控项信息                 |
| nodeItem    | node类型的监控项，方法类            |
| service     | 定时任务采集k8s的概况信息和配置信息 |
| serviceItem | service类型的监控项，方法类         |
| utils       | k8s监控中的工具类                   |



# tiklab-kaelthas-api



## alarm

告警信息模块



## db

| 目录            | 说明                                 |
| --------------- | ------------------------------------ |
| customize       | 自定义SQL的功能，这个功能没有开发    |
| databases       | 检控数据库列表的增删改查             |
| dbDynamic       | 监控数据库的动态信息                 |
| graphics        | 监控数据库的图形配置模块             |
| graphicsMonitor | 监控数据库的图形和监控项关联模块     |
| item            | 监控数据库的字典表模块               |
| monitor         | 监控数据库的监控项                   |
| overview        | 监控数据库的概况页面接口             |
| setting         | 监控数据库的设置页面接口             |
| trigger         | 监控数据库的触发器                   |
| triggerMedium   | 监控数据库中触发器和告警渠道的中间表 |



## history

上报数据的汇总表，agent上报的数据都存放在这张表中，此外还有一分钟、五分钟、十五分钟的表。



## home

项目的设置，里面存放了设置页面的接口。



## host

| 目录              | 说明                                                         |
| ----------------- | ------------------------------------------------------------ |
| graphics          | 主机下的图形信息                                             |
| graphicsMonitor   | 主机下图形和监控项的关联表                                   |
| host              | 主机的增删改查                                               |
| hostDynamic       | 主机下的动态信息，例如创建监控项                             |
| hostGroup         | 主机组的增删改查                                             |
| hostTemplate      | 主机和模板中间表                                             |
| monitor           | 主机监控项                                                   |
| monitorItem       | 监控项字典表                                                 |
| overview          | 主机概况页面的接口                                           |
| setting           | 主机的设置页面接口                                           |
| template          | 模板表                                                       |
| templateMonitor   | 模板下监控项                                                 |
| trigger           | 主机下的触发器                                               |
| triggerExpression | 触发器的表达式                                               |
| triggerMedium     | 触发器和消息通知渠道的关联表（消息通知渠道：站内信、邮箱、企业微信等） |



## internet

| 目录            | 说明                               |
| --------------- | ---------------------------------- |
| graphics        | 网络监控下的图形信息               |
| graphicsMonitor | 网络监控中图形和监控项的关联表     |
| internet        | 网络监控的信息                     |
| item            | 网络监控中监控项的字典表           |
| moinitor        | 网络监控的监控项                   |
| overview        | 网络监控中的概况页面接口           |
| setting         | 网络监控中的设置页面接口           |
| trigger         | 网络监控的触发器                   |
| triggerMedium   | 网络监控中触发器与消息渠道的关联表 |



## kubernetes

| 目录            | 说明                               |
| --------------- | ---------------------------------- |
| graphics        | 网络监控下的图形信息               |
| graphicsMonitor | 网络监控中图形和监控项的关联表     |
| item            | 网络监控中监控项的字典表           |
| kubernetesInfo  | 网络监控的信息                     |
| moinitor        | 网络监控的监控项                   |
| overview        | k8s中的概况页面接口                |
| setting         | k8s中设置页面的接口                |
| trigger         | 网络监控的触发器                   |
| triggerMedium   | 网络监控中触发器与消息渠道的关联表 |



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

| 目录            | 说明                                 |
| --------------- | ------------------------------------ |
| customize       | 自定义SQL的功能，这个功能没有开发    |
| databases       | 检控数据库列表的增删改查             |
| dbDynamic       | 监控数据库的动态信息                 |
| graphics        | 监控数据库的图形配置模块             |
| graphicsMonitor | 监控数据库的图形和监控项关联模块     |
| item            | 监控数据库的字典表模块               |
| monitor         | 监控数据库的监控项                   |
| overview        | 监控数据库的概况页面接口             |
| setting         | 监控数据库的设置页面接口             |
| trigger         | 监控数据库的触发器                   |
| triggerMedium   | 监控数据库中触发器和告警渠道的中间表 |



## history

上报数据的汇总表，agent上报的数据都存放在这张表中，此外还有一分钟、五分钟、十五分钟的表。



## home

项目的设置，里面存放了设置页面的接口。



## host

| 目录              | 说明                                                         |
| ----------------- | ------------------------------------------------------------ |
| graphics          | 主机下的图形信息                                             |
| graphicsMonitor   | 主机下图形和监控项的关联表                                   |
| host              | 主机的增删改查                                               |
| hostDynamic       | 主机下的动态信息，例如创建监控项                             |
| hostGroup         | 主机组的增删改查                                             |
| hostTemplate      | 主机和模板中间表                                             |
| monitor           | 主机监控项                                                   |
| monitorItem       | 监控项字典表                                                 |
| overview          | 主机概况页面的接口                                           |
| setting           | 主机的设置页面接口                                           |
| template          | 模板表                                                       |
| templateMonitor   | 模板下监控项                                                 |
| trigger           | 主机下的触发器                                               |
| triggerExpression | 触发器的表达式                                               |
| triggerMedium     | 触发器和消息通知渠道的关联表（消息通知渠道：站内信、邮箱、企业微信等） |



## internet

| 目录            | 说明                               |
| --------------- | ---------------------------------- |
| graphics        | 网络监控下的图形信息               |
| graphicsMonitor | 网络监控中图形和监控项的关联表     |
| internet        | 网络监控的信息                     |
| item            | 网络监控中监控项的字典表           |
| moinitor        | 网络监控的监控项                   |
| overview        | 网络监控中的概况页面接口           |
| setting         | 网络监控中的设置页面接口           |
| trigger         | 网络监控的触发器                   |
| triggerMedium   | 网络监控中触发器与消息渠道的关联表 |



## kubernetes

| 目录            | 说明                               |
| --------------- | ---------------------------------- |
| graphics        | 网络监控下的图形信息               |
| graphicsMonitor | 网络监控中图形和监控项的关联表     |
| item            | 网络监控中监控项的字典表           |
| kubernetesInfo  | 网络监控的信息                     |
| moinitor        | 网络监控的监控项                   |
| overview        | k8s中的概况页面接口                |
| setting         | k8s中设置页面的接口                |
| trigger         | 网络监控的触发器                   |
| triggerMedium   | 网络监控中触发器与消息渠道的关联表 |



## medium

消息渠道的字典表



## util

| 文件                   | 说明                                           |
| ---------------------- | ---------------------------------------------- |
| ConversionDateUtil     | 工具类，大多用于获取时间，对时间的处理         |
| ConversionScriptsUtils | javaScipts脚本的工具，触发器进行占位符替换使用 |
| SqlUtil                | 用于数据拼接成SQL，批量插入的工具类            |
| StringUtil             | 字符串拼接的工具类                             |