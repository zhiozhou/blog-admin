/*
 Navicat Premium Data Transfer

 Source Server         : test_conn
 Source Server Type    : SQL Server
 Source Server Version : 11002100
 Source Host           : 119.3.224.160:1433
 Source Catalog        : test_server
 Source Schema         : dbo

 Target Server Type    : SQL Server
 Target Server Version : 11002100
 File Encoding         : 65001

 Date: 22/04/2020 15:18:02
*/


-- ----------------------------
-- Table structure for system_dict
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[system_dict]') AND type IN ('U'))
	DROP TABLE [dbo].[system_dict]
GO

CREATE TABLE [dbo].[system_dict] (
  [id] int  IDENTITY(1000,1) NOT NULL,
  [name] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [key] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [state] int  NULL,
  [remark] varchar(256) COLLATE Chinese_PRC_CI_AS  NULL,
  [create_id] int  NULL,
  [modified_id] int  NULL,
  [gmt_create] datetime  NULL,
  [gmt_modified] datetime  NULL
)
GO

ALTER TABLE [dbo].[system_dict] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'system_dict',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典标识',
'SCHEMA', N'dbo',
'TABLE', N'system_dict',
'COLUMN', N'key'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态 0正常 11停用',
'SCHEMA', N'dbo',
'TABLE', N'system_dict',
'COLUMN', N'state'
GO

EXEC sp_addextendedproperty
'MS_Description', N'备注',
'SCHEMA', N'dbo',
'TABLE', N'system_dict',
'COLUMN', N'remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建人',
'SCHEMA', N'dbo',
'TABLE', N'system_dict',
'COLUMN', N'create_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改人',
'SCHEMA', N'dbo',
'TABLE', N'system_dict',
'COLUMN', N'modified_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'system_dict',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'system_dict',
'COLUMN', N'gmt_modified'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典表',
'SCHEMA', N'dbo',
'TABLE', N'system_dict'
GO


-- ----------------------------
-- Records of system_dict
-- ----------------------------
SET IDENTITY_INSERT [dbo].[system_dict] ON
GO

INSERT INTO [dbo].[system_dict] ([id], [name], [key], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1009', N'模块生成', N'extend_config', N'0', N'模块生成所需参数', N'1000', N'1000', N'2020-04-22 14:35:35.467', N'2020-04-22 15:10:57.840')
GO

INSERT INTO [dbo].[system_dict] ([id], [name], [key], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1005', N'菜单类型', N'system_menu_type', N'0', N'系统菜单类型', N'1000', N'1000', N'2020-04-20 14:21:44.697', N'2020-04-21 11:26:05.573')
GO

INSERT INTO [dbo].[system_dict] ([id], [name], [key], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1006', N'菜单状态', N'system_menu_state', N'0', N'系统菜单状态', N'1000', N'1000', N'2020-04-20 15:08:08.843', N'2020-04-21 11:28:15.637')
GO

INSERT INTO [dbo].[system_dict] ([id], [name], [key], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1007', N'角色状态', N'system_role_state', N'0', N'系统角色状态', N'1000', N'1000', N'2020-04-20 15:38:50.510', N'2020-04-21 11:28:45.250')
GO

INSERT INTO [dbo].[system_dict] ([id], [name], [key], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1008', N'用户状态', N'system_user_state', N'0', N'系统用户状态', N'1000', N'1000', N'2020-04-20 16:41:32.240', N'2020-04-21 11:29:56.457')
GO

SET IDENTITY_INSERT [dbo].[system_dict] OFF
GO


-- ----------------------------
-- Table structure for system_dict_data
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[system_dict_data]') AND type IN ('U'))
	DROP TABLE [dbo].[system_dict_data]
GO

CREATE TABLE [dbo].[system_dict_data] (
  [id] int  IDENTITY(1000,1) NOT NULL,
  [code] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [label] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [dict_key] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [type] int  NULL,
  [top] int  NULL,
  [css] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL
)
GO

ALTER TABLE [dbo].[system_dict_data] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'标识码',
'SCHEMA', N'dbo',
'TABLE', N'system_dict_data',
'COLUMN', N'code'
GO

EXEC sp_addextendedproperty
'MS_Description', N'标签',
'SCHEMA', N'dbo',
'TABLE', N'system_dict_data',
'COLUMN', N'label'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典标识',
'SCHEMA', N'dbo',
'TABLE', N'system_dict_data',
'COLUMN', N'dict_key'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型 0 正常 1 预留 11系统',
'SCHEMA', N'dbo',
'TABLE', N'system_dict_data',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'默认 0 正常 1 默认',
'SCHEMA', N'dbo',
'TABLE', N'system_dict_data',
'COLUMN', N'top'
GO

EXEC sp_addextendedproperty
'MS_Description', N'css名称',
'SCHEMA', N'dbo',
'TABLE', N'system_dict_data',
'COLUMN', N'css'
GO

EXEC sp_addextendedproperty
'MS_Description', N'字典数据表',
'SCHEMA', N'dbo',
'TABLE', N'system_dict_data'
GO


-- ----------------------------
-- Records of system_dict_data
-- ----------------------------
SET IDENTITY_INSERT [dbo].[system_dict_data] ON
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1127', N'0', N'正常', N'system_user_state', N'0', N'1', N'layui-bg-blue')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1128', N'9', N'注销', N'system_user_state', N'11', N'0', N'layui-btn-disabled')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1129', N'11', N'冻结', N'system_user_state', N'0', N'0', N'layui-bg-orange')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1130', N'12', N'移除', N'system_user_state', N'11', N'0', N'layui-btn-disabled')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1118', N'0', N'目录', N'system_menu_type', N'0', N'0', N'layui-bg-orange')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1119', N'1', N'菜单', N'system_menu_type', N'0', N'0', N'layui-bg-green')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1120', N'2', N'按钮', N'system_menu_type', N'0', N'0', N'layui-bg-blue')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1123', N'0', N'正常', N'system_menu_state', N'0', N'0', N'layui-bg-blue')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1124', N'1', N'隐藏', N'system_menu_state', N'0', N'0', N'layui-bg-orange')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1125', N'0', N'正常', N'system_role_state', N'0', N'0', N'layui-bg-blue')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1126', N'11', N'停用', N'system_role_state', N'0', N'0', N'layui-bg-orange')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1138', N'author', N'zhou', N'extend_config', N'0', N'0', N'')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1139', N'packet', N'priv.zhou', N'extend_config', N'0', N'0', N'')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1140', N'keepPrefix', N'false', N'extend_config', N'0', N'0', N'')
GO

INSERT INTO [dbo].[system_dict_data] ([id], [code], [label], [dict_key], [type], [top], [css]) VALUES (N'1141', N'module', N'system', N'extend_config', N'0', N'0', N'')
GO

SET IDENTITY_INSERT [dbo].[system_dict_data] OFF
GO


-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[system_menu]') AND type IN ('U'))
	DROP TABLE [dbo].[system_menu]
GO

CREATE TABLE [dbo].[system_menu] (
  [id] int  IDENTITY(1000,1) NOT NULL,
  [parent_id] int  NULL,
  [name] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [icon] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [path] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [type] int  NULL,
  [state] int  NULL,
  [sort] int  NULL,
  [key] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [create_id] int  NULL,
  [modified_id] int  NULL,
  [gmt_create] datetime  NULL,
  [gmt_modified] datetime  NULL
)
GO

ALTER TABLE [dbo].[system_menu] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'父级id',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'parent_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'fontawesome 图标',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'icon'
GO

EXEC sp_addextendedproperty
'MS_Description', N'路径',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'path'
GO

EXEC sp_addextendedproperty
'MS_Description', N'类型 关联字典',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'type'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态 关联字典',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'state'
GO

EXEC sp_addextendedproperty
'MS_Description', N'排序 从小直大',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'sort'
GO

EXEC sp_addextendedproperty
'MS_Description', N'权限标识',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'key'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建人id',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'create_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改人id',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'modified_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'system_menu',
'COLUMN', N'gmt_modified'
GO

EXEC sp_addextendedproperty
'MS_Description', N'菜单表',
'SCHEMA', N'dbo',
'TABLE', N'system_menu'
GO


-- ----------------------------
-- Records of system_menu
-- ----------------------------
SET IDENTITY_INSERT [dbo].[system_menu] ON
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1003', N'1035', N'菜单列表', N'fa-gear', N'system/menu/list', N'1', N'0', N'0', N'system:menu:list', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1011', N'1003', N'菜单添加', N'fa-gear', N'system/menu/add', N'2', N'0', NULL, N'system:menu:add', N'1000', N'1000', N'2020-04-10 14:32:32.443', N'2020-04-10 14:32:32.443')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1035', N'0', N'系统管理', N'fa fa-gear', N'', N'0', N'0', N'1', NULL, N'1000', N'1000', N'2020-04-14 19:33:42.017', N'2020-04-15 14:58:40.717')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1061', N'1035', N'角色列表', N'', N'system/role/list', N'1', N'0', N'1', N'system:role:list', N'1000', N'1000', N'2020-04-15 13:43:58.753', N'2020-04-15 13:43:58.753')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1062', N'0', N'客户管理', N'fa fa-address-book-o', N'', N'0', N'0', N'2', NULL, N'1000', N'1000', N'2020-04-15 16:00:41.073', N'2020-04-15 16:00:41.073')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1063', N'1062', N'公司列表', N'', N'1231', N'1', N'0', N'2', N'12312', N'1000', N'1000', N'2020-04-15 16:42:07.670', N'2020-04-15 16:42:07.670')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1065', N'0', N'关于我们', N'fa fa-address-card-o', N'', N'0', N'0', N'3', NULL, N'1000', N'1000', N'2020-04-15 16:43:45.477', N'2020-04-22 08:51:51.143')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1066', N'1003', N'菜单修改', N'', N'', N'2', N'0', NULL, N'system:menu:update', N'1000', N'1000', N'2020-04-16 10:55:52.063', N'2020-04-16 10:55:52.063')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1067', N'1035', N'模块生成', N'', N'system/extend/index', N'1', N'0', N'3', N'system:extend:index', N'1000', N'1000', N'2020-04-16 16:24:48.580', N'2020-04-16 16:24:48.580')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1068', N'1035', N'字典列表', N'', N'system/dict/list', N'1', N'0', N'3', N'system:dict:list', N'1000', N'1000', N'2020-04-17 13:08:35.947', N'2020-04-17 13:08:35.947')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1069', N'1003', N'菜单移除', N'', N'', N'2', N'0', NULL, N'system:menu:remove', N'1000', N'1000', N'2020-04-17 13:27:38.113', N'2020-04-17 13:27:38.113')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1070', N'1061', N'角色修改', NULL, NULL, N'2', N'0', NULL, N'system:role:update', N'1000', N'1000', N'2020-04-17 13:08:35.947', N'2020-04-17 13:08:35.947')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1072', N'1061', N'角色详情', N'', N'', N'2', N'0', NULL, N'system:role:detail', N'1000', N'1000', N'2020-04-17 13:41:03.377', N'2020-04-17 13:41:03.377')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1073', N'1061', N'角色删除', N'', N'', N'2', N'0', NULL, N'system:role:remove', N'1000', N'1000', N'2020-04-17 13:41:41.307', N'2020-04-17 13:41:41.307')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1074', N'1067', N'批量生成', N'', N'', N'2', N'0', NULL, N'system:extend:extend', N'1000', N'1000', N'2020-04-17 13:43:10.210', N'2020-04-17 13:43:10.210')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1075', N'1068', N'添加字典', N'', N'', N'2', N'0', NULL, N'system:dict:add', N'1000', N'1000', N'2020-04-17 14:13:18.670', N'2020-04-17 14:13:18.670')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1076', N'1068', N'移除字典', N'', N'', N'2', N'0', NULL, N'system:dict:remove', N'1000', N'1000', N'2020-04-17 14:14:32.483', N'2020-04-17 14:14:32.483')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1077', N'1068', N'字典修改', N'', N'', N'2', N'0', NULL, N'system:dict:update', N'1000', N'1000', N'2020-04-17 14:14:53.700', N'2020-04-17 14:14:53.700')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1078', N'1068', N'字典详情', N'', N'', N'2', N'0', NULL, N'system:dict:detail', N'1000', N'1000', N'2020-04-17 14:37:51.807', N'2020-04-17 14:37:51.807')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1080', N'1035', N'用户列表', N'', N'system/user/list', N'1', N'0', N'3', N'system:user:list', N'1000', N'1000', N'2020-04-20 16:46:04.650', N'2020-04-20 16:46:04.650')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1081', N'1080', N'添加用户', N'', N'', N'2', N'0', NULL, N'system:user:add', N'1000', N'1000', N'2020-04-20 16:47:45.180', N'2020-04-20 16:47:45.180')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1082', N'1080', N'修改用户', N'', N'', N'2', N'0', NULL, N'system:user:update', N'1000', N'1000', N'2020-04-20 16:48:05.630', N'2020-04-20 16:48:05.630')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1083', N'1080', N'用户详情', N'', N'', N'2', N'0', NULL, N'system:user:detail', N'1000', N'1000', N'2020-04-20 16:48:28.617', N'2020-04-20 16:48:28.617')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1084', N'1080', N'移除用户', N'', N'', N'2', N'0', NULL, N'system:user:remove', N'1000', N'1000', N'2020-04-20 16:51:34.260', N'2020-04-20 16:51:34.260')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1085', N'1035', N'在线监控', N'', N'system/monitor/list', N'1', N'0', N'6', N'system:monitor:list', N'1000', N'1000', N'2020-04-22 11:20:27.200', N'2020-04-22 11:20:27.200')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1086', N'1085', N'强退', N'', N'', N'2', N'0', NULL, N'system:monitor:forceOff', N'1000', N'1000', N'2020-04-22 14:03:41.370', N'2020-04-22 14:03:41.370')
GO

INSERT INTO [dbo].[system_menu] ([id], [parent_id], [name], [icon], [path], [type], [state], [sort], [key], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1087', N'1061', N'添加角色', N'', N'', N'2', N'0', NULL, N'system:role:add', N'1000', N'1000', N'2020-04-22 14:56:54.633', N'2020-04-22 14:56:54.633')
GO

SET IDENTITY_INSERT [dbo].[system_menu] OFF
GO


-- ----------------------------
-- Table structure for system_role
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[system_role]') AND type IN ('U'))
	DROP TABLE [dbo].[system_role]
GO

CREATE TABLE [dbo].[system_role] (
  [id] int  IDENTITY(1000,1) NOT NULL,
  [key] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [name] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [state] int  NULL,
  [remark] varchar(256) COLLATE Chinese_PRC_CI_AS  NULL,
  [create_id] int  NULL,
  [modified_id] int  NULL,
  [gmt_create] datetime  NULL,
  [gmt_modified] datetime  NULL
)
GO

ALTER TABLE [dbo].[system_role] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'key',
'SCHEMA', N'dbo',
'TABLE', N'system_role',
'COLUMN', N'key'
GO

EXEC sp_addextendedproperty
'MS_Description', N'名称',
'SCHEMA', N'dbo',
'TABLE', N'system_role',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态 关联字典',
'SCHEMA', N'dbo',
'TABLE', N'system_role',
'COLUMN', N'state'
GO

EXEC sp_addextendedproperty
'MS_Description', N'备注',
'SCHEMA', N'dbo',
'TABLE', N'system_role',
'COLUMN', N'remark'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建人id',
'SCHEMA', N'dbo',
'TABLE', N'system_role',
'COLUMN', N'create_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改人id',
'SCHEMA', N'dbo',
'TABLE', N'system_role',
'COLUMN', N'modified_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'system_role',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'system_role',
'COLUMN', N'gmt_modified'
GO

EXEC sp_addextendedproperty
'MS_Description', N'角色表',
'SCHEMA', N'dbo',
'TABLE', N'system_role'
GO


-- ----------------------------
-- Records of system_role
-- ----------------------------
SET IDENTITY_INSERT [dbo].[system_role] ON
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'1001', N'root', N'超级管理员', N'0', N'这角色嘛都能操作', N'0', N'1000', N'2020-04-08 14:58:59.000', N'2020-04-22 14:58:51.880')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2002', N'didi', N'弟弟', N'0', N'这就是个弟弟', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2003', N'gege', N'哥哥', N'0', N'这就是个哥哥', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2004', N'mama', N'妈妈', N'0', N'这就是个妈妈', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2005', N'baba', N'爸爸', N'0', N'这就是个爸爸', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2006', N'yeye', N'爷爷', N'0', N'这就是个爷爷', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2007', N'nainia', N'奶奶', N'0', N'这就是个奶奶', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2008', N'laolao', N'姥姥', N'0', N'这就是个姥姥', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2009', N'laoye', N'姥爷', N'0', N'这就是个姥爷', N'0', N'1000', N'2020-04-08 14:58:59.000', N'2020-04-17 15:30:56.213')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2010', N'dajiu', N'大舅', N'0', N'这就是个大舅', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2011', N'dajiuma', N'大舅妈', N'0', N'这就是个大舅妈', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2012', N'erjiu', N'二舅', N'0', N'这就是个二舅', N'0', N'0', N'2020-04-08 14:58:59.000', N'2020-04-08 14:59:02.000')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2013', N'liudaye', N'刘大爷', N'0', N'刘大爷专用角色', N'1000', N'1000', N'2020-04-16 10:14:51.560', N'2020-04-16 10:14:51.560')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2014', N'liudaniang', N'刘大娘', N'0', N'这是刘大娘的账号', N'1000', N'1000', N'2020-04-16 10:23:05.420', N'2020-04-16 10:23:05.420')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2016', N'aishidasaduo', N'爱仕达撒多', N'0', N'', N'1000', N'1000', N'2020-04-16 11:45:55.253', N'2020-04-16 11:45:55.253')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2019', N'zhou', N'周', N'11', N'我改一下备注', N'1000', N'1000', N'2020-04-16 14:28:34.423', N'2020-04-16 14:38:10.177')
GO

INSERT INTO [dbo].[system_role] ([id], [key], [name], [state], [remark], [create_id], [modified_id], [gmt_create], [gmt_modified]) VALUES (N'2020', N'1231', N'1231', N'0', N'', N'1000', N'1000', N'2020-04-20 11:52:06.913', N'2020-04-20 11:52:06.913')
GO

SET IDENTITY_INSERT [dbo].[system_role] OFF
GO


-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[system_role_menu]') AND type IN ('U'))
	DROP TABLE [dbo].[system_role_menu]
GO

CREATE TABLE [dbo].[system_role_menu] (
  [role_id] int  NULL,
  [menu_id] int  NULL
)
GO

ALTER TABLE [dbo].[system_role_menu] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'角色关联菜单表',
'SCHEMA', N'dbo',
'TABLE', N'system_role_menu'
GO


-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1035')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2019', N'1035')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1003')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1066')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2019', N'1003')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2019', N'1011')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2019', N'1061')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2019', N'1062')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1011')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1069')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1061')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1087')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1070')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1072')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1073')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1067')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1074')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1068')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1075')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1076')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2009', N'1035')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2009', N'1003')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2009', N'1011')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'2009', N'1069')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1077')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1078')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1080')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1081')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1082')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1083')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1084')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1085')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1086')
GO

INSERT INTO [dbo].[system_role_menu] ([role_id], [menu_id]) VALUES (N'1001', N'1065')
GO


-- ----------------------------
-- Table structure for system_user
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[system_user]') AND type IN ('U'))
	DROP TABLE [dbo].[system_user]
GO

CREATE TABLE [dbo].[system_user] (
  [id] int  IDENTITY(1000,1) NOT NULL,
  [username] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [password] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [salt] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [name] varchar(64) COLLATE Chinese_PRC_CI_AS  NULL,
  [state] int  NULL,
  [gmt_create] datetime  NULL,
  [gmt_modified] datetime  NULL
)
GO

ALTER TABLE [dbo].[system_user] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户名',
'SCHEMA', N'dbo',
'TABLE', N'system_user',
'COLUMN', N'username'
GO

EXEC sp_addextendedproperty
'MS_Description', N'密码',
'SCHEMA', N'dbo',
'TABLE', N'system_user',
'COLUMN', N'password'
GO

EXEC sp_addextendedproperty
'MS_Description', N'盐',
'SCHEMA', N'dbo',
'TABLE', N'system_user',
'COLUMN', N'salt'
GO

EXEC sp_addextendedproperty
'MS_Description', N'姓名',
'SCHEMA', N'dbo',
'TABLE', N'system_user',
'COLUMN', N'name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'状态 关联字典',
'SCHEMA', N'dbo',
'TABLE', N'system_user',
'COLUMN', N'state'
GO

EXEC sp_addextendedproperty
'MS_Description', N'创建时间',
'SCHEMA', N'dbo',
'TABLE', N'system_user',
'COLUMN', N'gmt_create'
GO

EXEC sp_addextendedproperty
'MS_Description', N'修改时间',
'SCHEMA', N'dbo',
'TABLE', N'system_user',
'COLUMN', N'gmt_modified'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户表',
'SCHEMA', N'dbo',
'TABLE', N'system_user'
GO


-- ----------------------------
-- Records of system_user
-- ----------------------------
SET IDENTITY_INSERT [dbo].[system_user] ON
GO

INSERT INTO [dbo].[system_user] ([id], [username], [password], [salt], [name], [state], [gmt_create], [gmt_modified]) VALUES (N'1000', N'zhou', N'96e79218965eb72c92a549dd5a330112', N'111', N'周某意', N'0', N'2020-04-20 16:50:19.000', N'2020-04-21 15:36:48.543')
GO

INSERT INTO [dbo].[system_user] ([id], [username], [password], [salt], [name], [state], [gmt_create], [gmt_modified]) VALUES (N'1001', N'liudehua', N'517134ce578abaae312b0b95872de891', N'1rb8TL', N'刘德华', N'12', N'2020-04-21 11:50:34.883', N'2020-04-21 13:52:54.013')
GO

INSERT INTO [dbo].[system_user] ([id], [username], [password], [salt], [name], [state], [gmt_create], [gmt_modified]) VALUES (N'1002', N'li', N'ac18ab7f956279e37023e0c9a9c6b56d', N'Ax470Y', N'李太平', N'0', N'2020-04-22 13:44:11.117', N'2020-04-22 14:27:30.100')
GO

INSERT INTO [dbo].[system_user] ([id], [username], [password], [salt], [name], [state], [gmt_create], [gmt_modified]) VALUES (N'1003', N'liu', N'940de8285854b1b12a71309c8edee241', N'uyCXXI', N'刘', N'0', N'2020-04-22 14:27:48.830', N'2020-04-22 14:27:48.830')
GO

SET IDENTITY_INSERT [dbo].[system_user] OFF
GO


-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
IF EXISTS (SELECT * FROM sys.all_objects WHERE object_id = OBJECT_ID(N'[dbo].[system_user_role]') AND type IN ('U'))
	DROP TABLE [dbo].[system_user_role]
GO

CREATE TABLE [dbo].[system_user_role] (
  [user_id] int  NULL,
  [role_id] int  NULL
)
GO

ALTER TABLE [dbo].[system_user_role] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户id',
'SCHEMA', N'dbo',
'TABLE', N'system_user_role',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'角色id',
'SCHEMA', N'dbo',
'TABLE', N'system_user_role',
'COLUMN', N'role_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'用户关联角色表',
'SCHEMA', N'dbo',
'TABLE', N'system_user_role'
GO


-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO [dbo].[system_user_role] ([user_id], [role_id]) VALUES (N'1000', N'1001')
GO

INSERT INTO [dbo].[system_user_role] ([user_id], [role_id]) VALUES (N'1001', N'2002')
GO

INSERT INTO [dbo].[system_user_role] ([user_id], [role_id]) VALUES (N'1002', N'1001')
GO

INSERT INTO [dbo].[system_user_role] ([user_id], [role_id]) VALUES (N'1003', N'2020')
GO


-- ----------------------------
-- Auto increment value for system_dict
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[system_dict]', RESEED, 1009)
GO


-- ----------------------------
-- Auto increment value for system_dict_data
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[system_dict_data]', RESEED, 1141)
GO


-- ----------------------------
-- Auto increment value for system_menu
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[system_menu]', RESEED, 1087)
GO


-- ----------------------------
-- Primary Key structure for table system_menu
-- ----------------------------
ALTER TABLE [dbo].[system_menu] ADD CONSTRAINT [PK__system_m__3213E83FA93F06E4] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for system_role
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[system_role]', RESEED, 2020)
GO


-- ----------------------------
-- Primary Key structure for table system_role
-- ----------------------------
ALTER TABLE [dbo].[system_role] ADD CONSTRAINT [PK__system_m__3213E83FA93F06E4_copy1] PRIMARY KEY CLUSTERED ([id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)  
ON [PRIMARY]
GO


-- ----------------------------
-- Auto increment value for system_user
-- ----------------------------
DBCC CHECKIDENT ('[dbo].[system_user]', RESEED, 1003)
GO

