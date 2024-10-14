
INSERT INTO "pcs_mec_message_notice" ("id", "message_type_id", "type", "bgroup", "message_send_type_id", "scope", "is_open") VALUES ('1a7eb55fcc03', '5b6767f3981a', 1, 'xmonitor', 'qywechat', 1, 'true');
INSERT INTO "pcs_mec_message_notice" ("id", "message_type_id", "type", "bgroup", "message_send_type_id", "scope", "is_open") VALUES ('9f5213884191', '351ca0c78d53', 1, 'xmonitor', 'qywechat', 1, 'true');
INSERT INTO "pcs_mec_message_notice" ("id", "message_type_id", "type", "bgroup", "message_send_type_id", "scope", "is_open") VALUES ('a6fee480f9cb', '8d3cdab5012d', 1, 'xmonitor', 'qywechat', 1, 'true');
INSERT INTO "pcs_mec_message_notice" ("id", "message_type_id", "type", "bgroup", "message_send_type_id", "scope", "is_open") VALUES ('b2ffa783e5a2', 'e2c259bd8d43', 1, 'xmonitor', 'qywechat', 1, 'true');


INSERT INTO "pcs_mec_message_template" ("id", "msg_type_id", "msg_send_type_id", "title", "content", "link", "bgroup", "link_params") VALUES ('0f8aec2513cd', 'e2c259bd8d43', 'qywechat', NULL, '## <font color="warning">**警告信息**</font>
> 主机名称：<font color="comment">${hostName}</font>
> 告警信息：<font color="info">**[${alarmInformation}](${qywxurl})**</font>', '/#/alarm', 'xmonitor', NULL);
INSERT INTO "pcs_mec_message_template" ("id", "msg_type_id", "msg_send_type_id", "title", "content", "link", "bgroup", "link_params") VALUES ('246a56da2117', '5b6767f3981a', 'qywechat', NULL, '## <font color="info">**创建主机**</font>
> 添加人：<font color="comment">${userName}</font>
> 主机名称：<font color="info">**[${hostName}](${qywxurl})**</font>', '/#/configuration', 'xmonitor', NULL);
INSERT INTO "pcs_mec_message_template" ("id", "msg_type_id", "msg_send_type_id", "title", "content", "link", "bgroup", "link_params") VALUES ('abb737b001df', '8d3cdab5012d', 'qywechat', NULL, '## <font color="info">**删除主机**</font>
> 删除人：<font color="comment">${userName}</font>
> 主机名称：<font color="info">**[${hostName}](${qywxurl})**</font>', '/#/configuration', 'xmonitor', NULL);
INSERT INTO "pcs_mec_message_template" ("id", "msg_type_id", "msg_send_type_id", "title", "content", "link", "bgroup", "link_params") VALUES ('4c70eeda0f71', '351ca0c78d53', 'qywechat', NULL, '## <font color="info">**修改主机**</font>
> 修改人：<font color="comment">${userName}</font>
> 主机名称：<font color="info">**[${hostName}](${qywxurl})**</font>', '/#/configuration', 'xmonitor', NULL);


INSERT INTO "pcs_mec_message_type" ("id", "name", "description", "bgroup") VALUES ('e2c259bd8d43', '告警消息', '告警消息的描述', 'xmonitor');
INSERT INTO "pcs_mec_message_type" ("id", "name", "description", "bgroup") VALUES ('5b6767f3981a', '添加主机', '添加主机的描述', 'xmonitor');
INSERT INTO "pcs_mec_message_type" ("id", "name", "description", "bgroup") VALUES ('351ca0c78d53', '修改主机', '修改主机的描述', 'xmonitor');
INSERT INTO "pcs_mec_message_type" ("id", "name", "description", "bgroup") VALUES ('8d3cdab5012d', '删除主机', '删除主机的描述', 'xmonitor');


INSERT INTO "pcs_mec_message_notice_connect_user" ("id", "message_notice_id", "user_id") VALUES ('c7624373f6cb', 'b2ffa783e5a2', '111111');