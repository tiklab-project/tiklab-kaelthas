<h1 align="center" style="border-bottom: none">
    <a href="https://kaelthas.tiklab.net/" target="_blank"><img alt="Kaelthas" src="assets/kaelthasLOGO.png"></a><br>
Kaelthas
</h1>

[//]: # (<div align="center">)

[//]: # ()

[//]: # (![Coverage Status]&#40;https://coveralls.io/repos/github/owner/repo/badge.svg&#41;)

[//]: # (![npm]&#40;https://img.shields.io/npm/dw/package-name&#41;)

[//]: # (![Dependencies]&#40;https://img.shields.io/depfu/owner/repo&#41;)

[//]: # (![License]&#40;https://img.shields.io/github/license/owner/repo&#41;)

[//]: # (![Version]&#40;https://img.shields.io/github/v/release/owner/repo&#41;)

[//]: # (![]&#40;https://img.shields.io/badge/React-%5E17.0.2-brightgreen&#41;)

[//]: # (![]&#40;https://img.shields.io/badge/Express-%5E4.17.2-yellow&#41;)

[//]: # ([![All Contributors]&#40;https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square&#41;]&#40;#contributors-&#41;)

[//]: # ()

[//]: # (</div>)

监控工具帮助企业管理复杂的基础设施并保持系统稳定。常见的监控工具有Zabbix、Prometheus、SwkingWalking等工具。下面推荐一款开源免费，操作简单的监控工具 -
kaelthas。
﻿

### 主要功能

对kaelthas整体功能的简要描述，kaelthas包含了哪些监控维度，能够使用户全面便捷地监控。

##### 全面监控

kaelthas已有的监控模块有 主机监控、数据库监控、k8s监控和网络监控，通过这些模块组成了全面的监控能力。

- 主机监控

主机监控是针对服务器或者电脑主机的监控，用户监控这些指标可以实时了解到当前服务器或者是电脑主机秒级的运行情况。

﻿

![img](https://tiklab.net/api/image/1e8ce42805ccd980)

﻿

- 数据库监控

数据库监控是对数据库的监控，数据库当前支持MYSQL数据库和pgsql数据库，监控的指标有：数据库插入数量、数据库查询数量、数据库查询走索引的数量、事务数量、活动会话数等。

﻿

![img](https://tiklab.net/api/image/3a8117e7ae1a7977)

﻿

数据库类型：

| 监控类型  | 含义                |
|-------|-------------------|
| MYSQL | 针对MYSQL数据库进行的数据监控 |
| pgsql | 针对pgsql数据库类型的监控   |

﻿

- k8s监控

k8s监控是对k8s集群的监控，目前主要有两个个大类来进行监控，分别为：Service和Node。主要监控了Node的状态、Pod的状态、可分配存储量、内存请求限制、cpu请求限制等。

﻿

![img](https://tiklab.net/api/image/8162c789bd8f7d6c)

﻿

- 网络监控

网络监控是对网络设备的监控，例如上行流量、下行流量等，能够提前预测可能出现的故障，进行网络优化。

﻿

![img](https://tiklab.net/api/image/83dacf8960fc67f0)

﻿

##### 1.2 监控配置

配置是Agent需要采集数据的信息、上报后哪些需要告警、图形如何展示等一系列的配置，包含了配置监控项、触发器、图形等配置，用于Agent的数据上报、触发告警、图形展示等。

- 监控项配置

监控项是配置Agent需要采集的数据，监控项目前包含了内存、cpu、网络、磁盘和端口检测五个大类，标注Agent需要上报的数据有哪些。

﻿

![img](https://tiklab.net/api/image/cce1f972afc8b13c)

﻿

- 触发器配置

触发器是对上报数据的过滤判断的设置，目前有三种触发方案，最近值、平均值和百分比，触发器的作用就是对超过阈值的数据进行告警操作。

﻿

![img](https://tiklab.net/api/image/6f12428b3c6cb6bd)

﻿

- 模板配置

模板是监控项的集合包含了很多监控项，在模板中可以对监控项添加删除，能够减少用户手动创建监控项，引入模板便可以将模板的监控项导入到当前的主机当中。

﻿

![img](https://tiklab.net/api/image/cb0bb02a5e907faa)

﻿

- 图形配置

图形是配置图形展示的设置，一个图形可以包含多个监控项的展示，能够根据监控项来配置图形的展示。

﻿

![img](https://tiklab.net/api/image/fea47224f3a8eafa)

﻿

##### 监控展示

监控模块是上报数据的展示，需要通过配置图形才能展示。有折线图、柱状图和面积图，告警部分是红色的，无需借助第三方工具，便可以使用户能够一目了然。

﻿

![img](https://tiklab.net/api/image/d8261ba9c551ca04)

﻿

##### 告警

智能告警是告警方式地多样化，目前包含了项目内告警和企业微信告警，能够将消息通过不同的渠道通知到用户。

- 企业微信告警

企业微信告警是将告警消息发送到企业微信当中。用户配置触发器，在kaelthas的设置中配置消息通知方案，能够将告警消息通知到指定的企业微信中。

﻿

![img](https://tiklab.net/api/image/edaa4e82f1a207cf)

﻿

- 告警模块通知

在项目中展示告警信息。配置触发器，出现告警信息后就会在告警模块展示出来。

﻿

![img](https://tiklab.net/api/image/9c585900fc31f0f0)

﻿

##### Agent

Agent是一个放置在电脑主机或者是服务器的一个运行程序，目前只包含了主机当中的监控内容，能够根据主机的配置上报数据。

- 数据采集

与Kaelthas服务端保持连接，拉取配置信息进行数据采集。

- 持续数据上报

定时拉取主机配置的监控项信息，将采集的数据进行上报。

﻿

### 产品优势

产品当中特别的功能点，kaelthas中结构和功能性的优点。

##### 覆盖范围广

监控包含的方向广泛，针对各种方面的监控。

- 主机监控

针对主机或者服务器的监控。

- 数据库监控

针对数据库插入数据，索引使用内存等的监控。

- k8s监控

针对k8s集群的监控。

- 网络监控

针对网络设备的监控，如交换机和路由器。

﻿

##### 简洁易用

界面简洁，可直接使用，支持一键式部署安装。

- 一键安装

线下部署版本支持一键式安装，内置数据库，不用单独安装数据库。

- 配置简洁

线下部署版本的配置只需要在一个文件中修改参数即可；公有云部署版本注册即可使用。

﻿

##### 多版本多终端

可以在多种操作系统上运行，也可以使用多种版本。

﻿

![img](https://tiklab.net/api/image/c7c78247b523cf3e)

﻿

##### 开源免费

- 免费使用

产品本身是开源免费的，使用者能够免费使用我们的产品。

- 代码开源

可以查看到源码，能够与kaelthas共同成长。

- 开源地址

Gitee： https://gitee.com/tiklab-project/tiklab-kaelthas

GitHub： https://github.com/tiklab-project/tiklab-kaelthas

﻿

##### 安全可靠

系统在遇到各种问题的情况下持续运行，能够保证用户的高体验。

- 权限控制

​ 应用级权限，系统级权限，项目级权限，全方面保护应用的安全。

- 日志审计

​ 实时记录任何变动，追溯到个人操作。

- 自动备份与恢复

​ 数据误删，数据损害，定时备份，实时恢复。

﻿

## 安装

### 系统要求

- Java 16+
- Maven 3.4+

### 克隆仓库

```
git clone https://github.com/tiklab-project/tiklab-kaelthas.git
cd tiklab-kaelthas
```

### 构建项目

**配置MAVEN仓库**

配置maven的setrings.xml文件的远程仓库为以下内容

```
<mirror>
    <id>hadess</id>
    <name>hadess</name>
    <url>https://mirror.tiklab.net/repository/tiklab-maven</url>  
    <mirrorOf>*</mirrorOf>
</mirror>
```

**构建**

- **MAC系统**：mvn clean package -P system-mac,env-dev
- **Linux系统**：mvn clean package -P system-linux,env-dev
- **Windows系统**：mvn clean package -P system-windows,env-dev

### 使用示例

1. 使用java编译工具运行KaelthasApplication。
2. 打开浏览器，访问 [http://localhost:8080](https://gitee.com/link?target=http%3A%2F%2Flocalhost%3A8090)
3. 登录信息，用户名：admin 密码：123456

### 贡献

我们欢迎社区的贡献！如果你有好的建议或发现了问题，请通过以下方式联系我：

[联系我们](https://gitee.com/link?target=https%3A%2F%2Ftiklab.net%2Faccount%2FworkOrder%2Fadd)

邮箱: [tiklab@163.com](https://gitee.com/link?target=mailto%3Atiklab%40163.com)

如需了解更多信息，请访问我们的官方网站或加入我们的社区讨论：

[官方网站](https://gitee.com/link?target=https%3A%2F%2Fwww.tiklab.net)

邮箱: [tiklab@163.com](https://gitee.com/link?target=mailto%3Atiklab%40163.com)

**立即体验 Kaelthas，解锁高效稳定监控！**