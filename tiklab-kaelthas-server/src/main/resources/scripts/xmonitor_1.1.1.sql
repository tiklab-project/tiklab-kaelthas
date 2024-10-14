
CREATE TABLE "mtc_ku_graphics" (
                                            "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                            "name" varchar(64) COLLATE "pg_catalog"."default",
                                            "describe" varchar(64) COLLATE "pg_catalog"."default",
                                            "ku_id" varchar(32) COLLATE "pg_catalog"."default",
                                            "source" int2
)
;
COMMENT ON COLUMN "mtc_ku_graphics"."id" IS 'id';
COMMENT ON COLUMN "mtc_ku_graphics"."name" IS '图形名称';
COMMENT ON COLUMN "mtc_ku_graphics"."describe" IS '描述';
COMMENT ON COLUMN "mtc_ku_graphics"."ku_id" IS '集群id';
COMMENT ON COLUMN "mtc_ku_graphics"."source" IS '1.来源于主机中监控项创建2.来源于模板中监控项创建';
COMMENT ON TABLE "mtc_ku_graphics" IS '根据监控项设置的图表';

-- ----------------------------
-- Records of mtc_ku_graphics
-- ----------------------------
INSERT INTO "mtc_ku_graphics" VALUES ('f0895fab9584', 'CPU图形展示', 'CPU图形', 'e59b2e0d1809', NULL);
INSERT INTO "mtc_ku_graphics" VALUES ('a9eedb7ea4cf', '数量展示', NULL, 'e59b2e0d1809', NULL);
INSERT INTO "mtc_ku_graphics" VALUES ('52f4cbe14de4', '内存情况', NULL, 'e59b2e0d1809', NULL);
INSERT INTO "mtc_ku_graphics" VALUES ('f45ed420b2e9', '存储情况', NULL, 'e59b2e0d1809', NULL);

-- ----------------------------
-- Table structure for mtc_ku_graphics_monitor
-- ----------------------------
CREATE TABLE "mtc_ku_graphics_monitor" (
                                                    "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                                    "monitor_id" varchar(128) COLLATE "pg_catalog"."default",
                                                    "graphics_id" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_ku_graphics_monitor"."monitor_id" IS '监控项id';
COMMENT ON COLUMN "mtc_ku_graphics_monitor"."graphics_id" IS '图表id';
COMMENT ON TABLE "mtc_ku_graphics_monitor" IS '监控项和图表的关联表';

-- ----------------------------
-- Records of mtc_ku_graphics_monitor
-- ----------------------------
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('e32ac830f7e5', 'ceeffb94652e', 'f0895fab9584');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('0b63b605f213', 'd6599aacd147', 'f0895fab9584');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('559dbb927a52', '4ef9cdb1c7ad', 'f0895fab9584');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('2d453e86160d', '92079e30d872', 'f0895fab9584');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('0f03e4d39356', 'd0a75e9810bf', 'a9eedb7ea4cf');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('2db1001a6fc2', 'f571bc6f14c7', 'a9eedb7ea4cf');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('ce9552bd6c44', '18d12325d195', 'a9eedb7ea4cf');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('62ffc97aff62', '2b389632128f', 'a9eedb7ea4cf');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('4267e32707bb', '505710b0af77', '52f4cbe14de4');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('d82873a2d34c', 'b134ab0a513f', '52f4cbe14de4');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('60d10ad6ef48', 'c2e0acfc0450', '52f4cbe14de4');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('e4c86f4cf8ab', '3c4eff018c25', '52f4cbe14de4');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('8ec65639164d', '76ba4dbd2093', 'f45ed420b2e9');
INSERT INTO "mtc_ku_graphics_monitor" VALUES ('4dd6d8278dc2', 'b6b4b27f0eae', 'f45ed420b2e9');

-- ----------------------------
-- Table structure for mtc_ku_info
-- ----------------------------
CREATE TABLE "mtc_ku_info" (
                                        "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                        "name" varchar(64) COLLATE "pg_catalog"."default",
                                        "ip" varchar(64) COLLATE "pg_catalog"."default",
                                        "create_time" timestamp(6),
                                        "update_time" timestamp(6),
                                        "color" int2,
                                        "port" int2,
                                        "api_token" text COLLATE "pg_catalog"."default",
                                        "status" int2
)
;
COMMENT ON COLUMN "mtc_ku_info"."name" IS '名称';
COMMENT ON COLUMN "mtc_ku_info"."ip" IS 'ip';
COMMENT ON COLUMN "mtc_ku_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "mtc_ku_info"."update_time" IS '修改时间';
COMMENT ON COLUMN "mtc_ku_info"."color" IS '颜色类型';
COMMENT ON COLUMN "mtc_ku_info"."port" IS 'apiserver访问的端口';
COMMENT ON COLUMN "mtc_ku_info"."api_token" IS '用于访问apiserver的token';
COMMENT ON COLUMN "mtc_ku_info"."status" IS '是否开启使用';

-- ----------------------------
-- Records of mtc_ku_info
-- ----------------------------
INSERT INTO "mtc_ku_info" VALUES ('e59b2e0d1809', '172.11.1.40集群', '172.11.1.40', '2024-08-22 14:36:19', '2024-08-30 17:24:04', 2, 6443, 'eyJhbGciOiJSUzI1NiIsImtpZCI6IkRZWGpsbHdYRDhQUXhOcHlRZDBURkZ3UVg5WVlxNzN1UVM4ZEhzY0NqZ0EifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImFkbWluLXRva2VuLXh3NmNwIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQudWlkIjoiMDJkNWE3YjMtYzc1My00ZTliLTk0Y2YtN2QxMDU2YjdlYmJkIiwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4ifQ.Z5xcJAI1Hys9HtA9-zSwvp1kEOhH15k-cjkcJTKZq2oqS-ApzrZY7F9DYFVg3nCdHeUh3FxVzKDnE00qvORcAMLDX6CWXS-lyWuMIjRn-4hLDFOnqPcvYsqCjMioL6A3i6fkKqgwUcVxcAGMa2QsnlsFEoGXU1DFIHKMAhWSG7SQZgwohb3YafIZC-z13SolkOXnZe72kp0nIn81iRS483mbmPwkRy9YBIDZqd87ScYT622r1usjqWzWNLgO4X62rMPYzFxoJcXCiecCJUZ5WHAcRimf9f4KhWWdTgzIXH36VVu8nA4JcQ9C1J9b9-L_MB_ZaDgCC5qbGYLo_k3qbg', 1);

-- ----------------------------
-- Table structure for mtc_ku_item
-- ----------------------------
CREATE TABLE "mtc_ku_item" (
                                        "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                        "expression" varchar(128) COLLATE "pg_catalog"."default",
                                        "describe" varchar(128) COLLATE "pg_catalog"."default",
                                        "kubernetes_type" varchar(32) COLLATE "pg_catalog"."default",
                                        "report_type" int2
)
;
COMMENT ON TABLE "mtc_ku_item" IS 'Kubernetes的监控指标表';

-- ----------------------------
-- Records of mtc_ku_item
-- ----------------------------
INSERT INTO "mtc_ku_item" VALUES ('208', 'k8s_service_pod_memory_usage', 'Pod 的内存资源总使用量(未开发)', 'service', 1);
INSERT INTO "mtc_ku_item" VALUES ('209', 'k8s_service_pod_status_waiting', '当前处于等待状态的 Pod 和容器，并显示原因(未开发)', 'service', 2);
INSERT INTO "mtc_ku_item" VALUES ('210', 'k8s_service_pod_status_terminated', '当前处于终止状态的 Pod 和容器，并显示原因(未开发)', 'service', 2);
INSERT INTO "mtc_ku_item" VALUES ('211', 'k8s_service_pod_status_restarts_total', '与 Pod 相关的每个容器重启次数(未开发)', 'service', 2);
INSERT INTO "mtc_ku_item" VALUES ('202', 'k8s_service_pod_status', 'Pod 的当前状态', 'service', 2);
INSERT INTO "mtc_ku_item" VALUES ('201', 'k8s_service_pod_total', 'Pod 数量', 'service', 1);
INSERT INTO "mtc_ku_item" VALUES ('203', 'k8s_service_cpu_cores_requests', '此服务的 CPU 资源请求', 'service', 1);
INSERT INTO "mtc_ku_item" VALUES ('204', 'k8s_service_cpu_cores_limits', '此服务的 CPU 资源限制', 'service', 1);
INSERT INTO "mtc_ku_item" VALUES ('205', 'k8s_service_memory_requests', '此服务的内存资源请求', 'service', 1);
INSERT INTO "mtc_ku_item" VALUES ('206', 'k8s_service_memory_limits', '此服务的内存资源限制', 'service', 1);
INSERT INTO "mtc_ku_item" VALUES ('114', 'k8s_node_memory_usage', '节点总内存使用量(未开发)', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('104', 'k8s_node_cpu_cores_requests', '节点中Pod的CPU请求总数(未开发)', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('105', 'k8s_node_cpu_cores_limits', '节点中Pod的CPU限制总数(未开发)', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('108', 'k8s_node_memory_requests', '节点中所有 Pod 的内存请求总量(未开发)', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('109', 'k8s_node_memory_limits', '节点中所有 Pod 的内存限制总量(未开发)', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('207', 'k8s_service_pod_cpu_usage', 'Pod 的 CPU 资源总使用量(未开发)', 'service', 1);
INSERT INTO "mtc_ku_item" VALUES ('113', 'k8s_node_cpu_usage', '节点CPU 核心的总使用量(未开发)', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('107', 'k8s_node_memory_total', '节点总内存量', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('116', 'k8s_node_network_transmit', '节点网络发送(未开发)', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('115', 'k8s_node_network_receive', '节点网络接收(未开发)', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('1', 'k8s_cluster_node_total', '节点数', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('2', 'k8s_cluster_namespace_total', '命名空间的数量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('4', 'k8s_cluster_statefulset_total', 'Statefulset 的数量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('7', 'k8s_cluster_pod_total', 'Pod数量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('9', 'k8s_cluster_cpu_cores', '集群中的总CPU核心数', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('10', 'k8s_cluster_cpu_cores_requests', '集群中所有Pod的CPU请求总数', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('11', 'k8s_cluster_cpu_cores_limits', '集群中所有Pod的CPU限制总数', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('12', 'k8s_cluster_cpu_cores_allocatable', '集群中可分配的CPU核心数', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('13', 'k8s_cluster_memory_total', '集群中所有节点的总内存量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('14', 'k8s_cluster_memory_requests', '集群中所有 Pod 的内存请求总量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('15', 'k8s_cluster_memory_limits', '集群中所有 Pod 的内存限制总量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('16', 'k8s_cluster_memory_allocatable', '集群中可分配的内存量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('17', 'k8s_cluster_storage_total', '集群中所有节点的总存储容量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('18', 'k8s_cluster_storage_allocatable', '集群中所有节点的可分配存储容量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('19', 'k8s_cluster_node_status', '节点的当前状态', 'cluster', 2);
INSERT INTO "mtc_ku_item" VALUES ('20', 'k8s_cluster_deployment_status', '当前部署状态', 'cluster', 2);
INSERT INTO "mtc_ku_item" VALUES ('101', 'k8s_node_pod_total', '此节点中的 pod 数量', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('102', 'k8s_node_node_status', '该节点的当前状态', 'node', 2);
INSERT INTO "mtc_ku_item" VALUES ('103', 'k8s_node_cpu_cores', '节点总CPU核心数', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('106', 'k8s_node_cpu_cores_allocatable', '节点中可分配的CPU核心数', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('112', 'k8s_node_storage_allocatable', '节点中可分配存储容量', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('3', 'k8s_cluster_deployment_total', 'deployment数量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('5', 'k8s_cluster_daemonset_total', 'DaemonSet的数量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('6', 'k8s_cluster_service_total', 'Service数量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('8', 'k8s_cluster_container_total', 'Container数量', 'cluster', 1);
INSERT INTO "mtc_ku_item" VALUES ('21', 'k8s_cluster_service_pod_status', '服务当前状态，取决于相关 Pod 的状态', 'cluster', 2);
INSERT INTO "mtc_ku_item" VALUES ('110', 'k8s_node_memory_allocatable', '节点中可分配的内存量', 'node', 1);
INSERT INTO "mtc_ku_item" VALUES ('111', 'k8s_node_storage_total', '节点总存储容量', 'node', 1);

-- ----------------------------
-- Table structure for mtc_ku_monitor
-- ----------------------------
CREATE TABLE "mtc_ku_monitor" (
                                           "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                           "ku_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "name" varchar(64) COLLATE "pg_catalog"."default",
                                           "retention_time" int8,
                                           "status" int2,
                                           "ku_item_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "monitor_id" varchar(32) COLLATE "pg_catalog"."default",
                                           "expression" varchar(128) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_ku_monitor"."id" IS 'id';
COMMENT ON COLUMN "mtc_ku_monitor"."ku_id" IS '数据源id';
COMMENT ON COLUMN "mtc_ku_monitor"."name" IS '监控项名称';
COMMENT ON COLUMN "mtc_ku_monitor"."retention_time" IS '数据保留时间';
COMMENT ON COLUMN "mtc_ku_monitor"."status" IS '监控项状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_ku_monitor"."ku_item_id" IS 'dbitem表的id';
COMMENT ON COLUMN "mtc_ku_monitor"."monitor_id" IS '模板复制的时候使用,将本表的监控项复制一份出来';
COMMENT ON COLUMN "mtc_ku_monitor"."expression" IS '表达式';

-- ----------------------------
-- Records of mtc_ku_monitor
-- ----------------------------
INSERT INTO "mtc_ku_monitor" VALUES ('db4c4ce033aa', 'e59b2e0d1809', 'service-内存资源限制', 333, 1, '206', NULL, 'k8s_service_memory_limits(default,my-service,app)');
INSERT INTO "mtc_ku_monitor" VALUES ('18d12325d195', 'e59b2e0d1809', 'cluster-statefulset的数量', 2222222, 1, '4', NULL, 'k8s_cluster_statefulset_total');
INSERT INTO "mtc_ku_monitor" VALUES ('2b389632128f', 'e59b2e0d1809', 'cluster-pod的数量', 33333, 1, '7', NULL, 'k8s_cluster_pod_total');
INSERT INTO "mtc_ku_monitor" VALUES ('505710b0af77', 'e59b2e0d1809', 'cluster-集群总内存', 111111, 1, '13', NULL, 'k8s_cluster_memory_total');
INSERT INTO "mtc_ku_monitor" VALUES ('b134ab0a513f', 'e59b2e0d1809', 'cluster-集群内存请求总量', 1111, 1, '14', NULL, 'k8s_cluster_memory_requests');
INSERT INTO "mtc_ku_monitor" VALUES ('c2e0acfc0450', 'e59b2e0d1809', 'cluster-集群内存限制总量', 1111111, 1, '15', NULL, 'k8s_cluster_memory_limits');
INSERT INTO "mtc_ku_monitor" VALUES ('3c4eff018c25', 'e59b2e0d1809', 'cluster-集群中内存可分配量', 333333, 1, '16', NULL, 'k8s_cluster_memory_allocatable');
INSERT INTO "mtc_ku_monitor" VALUES ('76ba4dbd2093', 'e59b2e0d1809', 'cluster-集群中总存储量', 3333333, 1, '17', NULL, 'k8s_cluster_storage_total');
INSERT INTO "mtc_ku_monitor" VALUES ('b6b4b27f0eae', 'e59b2e0d1809', 'cluster-集群中可分配存储量', 2222, 1, '18', NULL, 'k8s_cluster_storage_allocatable');
INSERT INTO "mtc_ku_monitor" VALUES ('7773c04d856c', 'e59b2e0d1809', 'container的数量', 111111, 1, '8', NULL, 'k8s_cluster_container_total');
INSERT INTO "mtc_ku_monitor" VALUES ('7a4c3c819a36', 'e59b2e0d1809', 'cluster-节点的当前状态', 1111, 1, '19', NULL, 'k8s_cluster_node_status');
INSERT INTO "mtc_ku_monitor" VALUES ('85f74aaf6577', 'e59b2e0d1809', 'cluster-当前部署状态', 1111111, 1, '20', NULL, 'k8s_cluster_deployment_status');
INSERT INTO "mtc_ku_monitor" VALUES ('8cbf2f523654', 'e59b2e0d1809', 'cluster-deployment数量', 111111, 1, '3', NULL, 'k8s_cluster_deployment_total');
INSERT INTO "mtc_ku_monitor" VALUES ('f2fc53d3354c', 'e59b2e0d1809', 'node-该节点当前状态', 444, 1, '102', NULL, 'k8s_node_node_status(k8s-master)');
INSERT INTO "mtc_ku_monitor" VALUES ('ae0496873126', 'e59b2e0d1809', 'cluster-deamonset的数量', 1111111, 1, '5', NULL, 'k8s_cluster_daemonset_total');
INSERT INTO "mtc_ku_monitor" VALUES ('483545958eb5', 'e59b2e0d1809', 'service-k8s下pod的状态', 11111, 1, '202', NULL, 'k8s_service_pod_status(default,my-service,app)');
INSERT INTO "mtc_ku_monitor" VALUES ('0c6c6b926a38', 'e59b2e0d1809', 'service-pod数量', 33333, 1, '201', NULL, 'k8s_service_pod_total(default,my-service,app)');
INSERT INTO "mtc_ku_monitor" VALUES ('facbb369d21b', 'e59b2e0d1809', 'service-CPU资源请求', 44, 1, '203', NULL, 'k8s_service_cpu_cores_requests(default,my-service,app)');
INSERT INTO "mtc_ku_monitor" VALUES ('bccd96bfb1e1', 'e59b2e0d1809', 'service-CPU资源限制', 11111, 1, '204', NULL, 'k8s_service_cpu_cores_limits(default,my-service,app)');
INSERT INTO "mtc_ku_monitor" VALUES ('24f95cec88ec', 'e59b2e0d1809', 'service-内存资源请求', 1111, 1, '205', NULL, 'k8s_service_memory_requests(default,my-service,app)');
INSERT INTO "mtc_ku_monitor" VALUES ('319dd0c905d7', 'e59b2e0d1809', 'node-该节点总CPU核心数', 11, 1, '103', NULL, 'k8s_node_cpu_cores(k8s-master)');
INSERT INTO "mtc_ku_monitor" VALUES ('0335a375a6ab', 'e59b2e0d1809', 'node-节点中pod的CPU可分配数', 1111, 1, '106', NULL, 'k8s_node_cpu_cores_allocatable(k8s-master)');
INSERT INTO "mtc_ku_monitor" VALUES ('01493cf7dccd', 'e59b2e0d1809', 'node-节点总内存量', 11111, 1, '107', NULL, 'k8s_node_memory_total(k8s-master)');
INSERT INTO "mtc_ku_monitor" VALUES ('d435fe36bfbf', 'e59b2e0d1809', 'node-节点中可分配的内存量', 333, 1, '110', NULL, 'k8s_node_memory_allocatable(k8s-master)');
INSERT INTO "mtc_ku_monitor" VALUES ('1c40c955fde9', 'e59b2e0d1809', 'node-节点中可分配存储容量', 55, 1, '112', NULL, 'k8s_node_storage_allocatable(k8s-master)');
INSERT INTO "mtc_ku_monitor" VALUES ('432dae08cdef', 'e59b2e0d1809', 'node-节点中pod的数量', 1111111111, 1, '101', NULL, 'k8s_node_pod_total(k8s-master)');
INSERT INTO "mtc_ku_monitor" VALUES ('a265940725f4', 'e59b2e0d1809', 'node-节点总存储容量', 6, 1, '111', NULL, 'k8s_node_storage_total(k8s-master)');
INSERT INTO "mtc_ku_monitor" VALUES ('51e803955511', 'e59b2e0d1809', 'cluster-服务的数量', 1111111, 1, '6', NULL, 'k8s_cluster_service_total');
INSERT INTO "mtc_ku_monitor" VALUES ('3bce7a9ea7d5', 'e59b2e0d1809', 'cluster-服务的状态', 111111111, 1, '21', NULL, 'k8s_cluster_service_pod_status');
INSERT INTO "mtc_ku_monitor" VALUES ('ceeffb94652e', 'e59b2e0d1809', 'cluster-集群中总核心数', 11111103, 1, '9', NULL, 'k8s_cluster_cpu_cores');
INSERT INTO "mtc_ku_monitor" VALUES ('d6599aacd147', 'e59b2e0d1809', 'cluster-集群中pod的CPU请求总数', 3333, 1, '10', NULL, 'k8s_cluster_cpu_cores_requests');
INSERT INTO "mtc_ku_monitor" VALUES ('4ef9cdb1c7ad', 'e59b2e0d1809', 'cluster-集群中所有pod的CPU限制总数', 55555555, 1, '11', NULL, 'k8s_cluster_cpu_cores_limits');
INSERT INTO "mtc_ku_monitor" VALUES ('92079e30d872', 'e59b2e0d1809', 'cluster-集群中可分配的CPU核心数', 6666, 1, '12', NULL, 'k8s_cluster_cpu_cores_allocatable');
INSERT INTO "mtc_ku_monitor" VALUES ('d0a75e9810bf', 'e59b2e0d1809', 'cluster-节点数量', 1111111, 1, '1', NULL, 'k8s_cluster_node_total');
INSERT INTO "mtc_ku_monitor" VALUES ('f571bc6f14c7', 'e59b2e0d1809', 'cluster-命名空间的数量', 111111, 1, '2', NULL, 'k8s_cluster_namespace_total');

-- ----------------------------
-- Table structure for mtc_ku_trigger
-- ----------------------------
CREATE TABLE "mtc_ku_trigger" (
                                           "id" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
                                           "name" varchar(32) COLLATE "pg_catalog"."default",
                                           "describe" varchar(128) COLLATE "pg_catalog"."default",
                                           "state" int2,
                                           "severity_level" int2,
                                           "ku_id" varchar(20) COLLATE "pg_catalog"."default",
                                           "expression" text COLLATE "pg_catalog"."default",
                                           "scheme" int2,
                                           "range_time" int2,
                                           "percentage" int2
)
;
COMMENT ON COLUMN "mtc_ku_trigger"."id" IS '触发器主键';
COMMENT ON COLUMN "mtc_ku_trigger"."name" IS '触发器名称';
COMMENT ON COLUMN "mtc_ku_trigger"."describe" IS '描述（对触发器的描述）';
COMMENT ON COLUMN "mtc_ku_trigger"."state" IS '触发器状态(1. 已启用，2. 未启用）';
COMMENT ON COLUMN "mtc_ku_trigger"."severity_level" IS '严重性（1，灾难 2，严重 3，一般严重 4，告警  5，信息  6，未分类）';
COMMENT ON COLUMN "mtc_ku_trigger"."ku_id" IS '数据库id';
COMMENT ON COLUMN "mtc_ku_trigger"."expression" IS '触发器关系表达式';
COMMENT ON COLUMN "mtc_ku_trigger"."scheme" IS '1.平均值2.百分比,3.最后一个值';
COMMENT ON COLUMN "mtc_ku_trigger"."range_time" IS '时间范畴为几分钟的数据';
COMMENT ON COLUMN "mtc_ku_trigger"."percentage" IS '触发器的百分比达到多少进行告警';
COMMENT ON TABLE "mtc_ku_trigger" IS '数据库的触发器表';

-- ----------------------------
-- Records of mtc_ku_trigger
-- ----------------------------

-- ----------------------------
-- Table structure for mtc_ku_trigger_medium
-- ----------------------------
CREATE TABLE "mtc_ku_trigger_medium" (
                                                  "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
                                                  "trigger_id" varchar(32) COLLATE "pg_catalog"."default",
                                                  "medium_id" varchar(32) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "mtc_ku_trigger_medium"."trigger_id" IS '触发器id';
COMMENT ON COLUMN "mtc_ku_trigger_medium"."medium_id" IS '媒介id';
COMMENT ON TABLE "mtc_ku_trigger_medium" IS '触发器和媒介中间表';

-- ----------------------------
-- Records of mtc_ku_trigger_medium
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table mtc_ku_graphics
-- ----------------------------
ALTER TABLE "mtc_ku_graphics" ADD CONSTRAINT "mtc_graphics_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_graphics_monitor
-- ----------------------------
ALTER TABLE "mtc_ku_graphics_monitor" ADD CONSTRAINT "mtc_graphics_monitor_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_info
-- ----------------------------
ALTER TABLE "mtc_ku_info" ADD CONSTRAINT "mtc_kubernetes_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_item
-- ----------------------------
ALTER TABLE "mtc_ku_item" ADD CONSTRAINT "mtc_kubernetes_item_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_monitor
-- ----------------------------
ALTER TABLE "mtc_ku_monitor" ADD CONSTRAINT "mtc_db_monitor_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_trigger
-- ----------------------------
ALTER TABLE "mtc_ku_trigger" ADD CONSTRAINT "mtc_db_trigger_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table mtc_ku_trigger_medium
-- ----------------------------
ALTER TABLE "mtc_ku_trigger_medium" ADD CONSTRAINT "mtc_db_trigger_medium_copy1_pkey" PRIMARY KEY ("id");






CREATE TABLE "mtc_db_dynamic" (
       "id" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
       "db_id" varchar(32) COLLATE "pg_catalog"."default",
       "name" varchar(128) COLLATE "pg_catalog"."default",
       "time" timestamp(6),
       CONSTRAINT "mtc_db_dynamic_pkey" PRIMARY KEY ("id")
);

ALTER TABLE "mtc_db_dynamic" OWNER TO "postgres";

COMMENT ON COLUMN "mtc_db_dynamic"."db_id" IS '数据库id';

COMMENT ON COLUMN "mtc_db_dynamic"."name" IS '动态名称';

COMMENT ON COLUMN "mtc_db_dynamic"."time" IS '动态创建时间';

COMMENT ON TABLE "mtc_db_dynamic" IS '数据库的动态描述';