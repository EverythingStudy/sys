/*
 Navicat Premium Dump SQL

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80027 (8.0.27)
 Source Host           : 192.168.160.105:3306
 Source Schema         : pathmedics

 Target Server Type    : MySQL
 Target Server Version : 80027 (8.0.27)
 File Encoding         : 65001

 Date: 02/12/2025 13:11:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aipre_airepost
-- ----------------------------
DROP TABLE IF EXISTS `aipre_airepost`;
CREATE TABLE `aipre_airepost`  (
  `report_uuid` int NOT NULL AUTO_INCREMENT,
  `ai_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ai_report_json` json NULL,
  `image_uuid_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `project_id` bigint NOT NULL,
  `start_time` datetime(6) NOT NULL,
  `end_time` datetime(6) NOT NULL,
  `waste_time` int NOT NULL,
  `msg` varchar(12000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `json_addr` varchar(588) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `task_id` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `graphics_card_model` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `server_model` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `width` double NULL DEFAULT NULL,
  `height` double NULL DEFAULT NULL,
  `center_x` double NULL DEFAULT NULL,
  `center_y` double NULL DEFAULT NULL,
  `rotation` int NULL DEFAULT NULL,
  `level` int NULL DEFAULT NULL,
  `primary` int NULL DEFAULT NULL,
  `visible` tinyint(1) NOT NULL,
  `modelName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `init_center_x` double NULL DEFAULT NULL,
  `init_center_y` double NULL DEFAULT NULL,
  `init_level` int NULL DEFAULT NULL,
  `algorithm_id` int NULL DEFAULT NULL,
  `single_slide_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`report_uuid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7253 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for aipre_algorithm
-- ----------------------------
DROP TABLE IF EXISTS `aipre_algorithm`;
CREATE TABLE `aipre_algorithm`  (
  `algorithm_uuid` int NOT NULL AUTO_INCREMENT,
  `algorithm_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `whether_to_compare` tinyint(1) NULL DEFAULT NULL,
  `compare_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `abbreviation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `min_mem` double NOT NULL,
  `video_memory` double NOT NULL,
  `algorithm_lesion` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `model_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `direction` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nividia_amount` int NOT NULL,
  `request_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `request_ip` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `image_size` int NULL DEFAULT NULL,
  `timeout_seconds` int NULL DEFAULT NULL,
  `organ_tag_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '脏器标签编码',
  PRIMARY KEY (`algorithm_uuid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for aipre_algorithm_copy1
-- ----------------------------
DROP TABLE IF EXISTS `aipre_algorithm_copy1`;
CREATE TABLE `aipre_algorithm_copy1`  (
  `algorithm_uuid` int NOT NULL AUTO_INCREMENT,
  `algorithm_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `whether_to_compare` tinyint(1) NULL DEFAULT NULL,
  `compare_group` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `abbreviation` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime(6) NOT NULL,
  `min_mem` double NOT NULL,
  `video_memory` double NOT NULL,
  `algorithm_lesion` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `file_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `model_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `direction` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nividia_amount` int NOT NULL,
  `request_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `request_ip` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `image_size` int NULL DEFAULT NULL,
  `timeout_seconds` int NULL DEFAULT NULL,
  `organ_tag_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '脏器标签编码',
  PRIMARY KEY (`algorithm_uuid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_ai_forecast
-- ----------------------------
DROP TABLE IF EXISTS `fr_ai_forecast`;
CREATE TABLE `fr_ai_forecast`  (
  `forecast_id` int NOT NULL AUTO_INCREMENT,
  `quantitative_indicators` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定量指标',
  `quantitative_indicators_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '定量指标英文',
  `results` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '预测结果',
  `forecast_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '范围',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `single_slide_id` bigint NULL DEFAULT NULL COMMENT '单脏器切片id',
  `unit` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `struct_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT ' 结构指标类别0：产品呈现指标1：算法输出指标',
  `structure_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '指标计算机构编码',
  PRIMARY KEY (`forecast_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28862 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '量化指标表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fr_contour_json
-- ----------------------------
DROP TABLE IF EXISTS `fr_contour_json`;
CREATE TABLE `fr_contour_json`  (
  `contour_json_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `slide_id` bigint NULL DEFAULT NULL COMMENT '切片id',
  `tile_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '瓦片名称',
  `structure_size` int NULL DEFAULT NULL COMMENT '结构大小',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `single_slide_id` bigint NULL DEFAULT NULL COMMENT '单切片id',
  `middle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '中结构json文件',
  `small` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小结构json文件',
  `middle_small` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `big` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '大结构json文件',
  PRIMARY KEY (`contour_json_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1024859 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fr_group
-- ----------------------------
DROP TABLE IF EXISTS `fr_group`;
CREATE TABLE `fr_group`  (
  `group_id` bigint NOT NULL AUTO_INCREMENT COMMENT '组别id',
  `group_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组别',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_json_file
-- ----------------------------
DROP TABLE IF EXISTS `fr_json_file`;
CREATE TABLE `fr_json_file`  (
  `file_id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `task_id` bigint NOT NULL COMMENT '任务ID',
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  `structure_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构Id',
  `structure_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构名称',
  `ai_status` int NULL DEFAULT NULL COMMENT 'AI识别状态 0成功 1失败',
  `status` int NULL DEFAULT NULL COMMENT '状态(0未进行解析、1解析中、2解析成功、3解析失败)',
  `times` int NULL DEFAULT NULL COMMENT '执行次数（第几次）',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`file_id`, `task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16996 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'ai预测json文件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fr_json_task
-- ----------------------------
DROP TABLE IF EXISTS `fr_json_task`;
CREATE TABLE `fr_json_task`  (
  `task_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `slide_id` bigint NULL DEFAULT NULL COMMENT '切片ID',
  `special_id` int NULL DEFAULT NULL COMMENT '专题ID',
  `image_id` int NULL DEFAULT NULL COMMENT '图像ID',
  `single_id` bigint NULL DEFAULT NULL COMMENT '单脏器切片id',
  `organization_id` bigint NULL DEFAULT 0 COMMENT '机构ID',
  `category_id` bigint NULL DEFAULT NULL COMMENT '脏器标签ID',
  `algorithm_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '算法ID',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '算法返回状态',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '算法返回msg',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '算法返回数据',
  `status` int NULL DEFAULT NULL COMMENT '0未进行解析、1解析中、2解析成功、3解析失败',
  `times` int NULL DEFAULT NULL COMMENT '执行次数（第几次）',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`task_id`) USING BTREE,
  UNIQUE INDEX `uk_single_id`(`single_id` ASC) USING BTREE COMMENT '切面id'
) ENGINE = InnoDB AUTO_INCREMENT = 4753 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'json解析任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fr_measure
-- ----------------------------
DROP TABLE IF EXISTS `fr_measure`;
CREATE TABLE `fr_measure`  (
  `measure_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `slide_id` bigint NOT NULL COMMENT '切片id',
  `annotation_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注类型(AI表示AI算出的标注，Draw表示前端绘制的标注，Measure表示测量工具数据)',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '面积',
  `perimeter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '周长',
  `number` bigint NULL DEFAULT NULL COMMENT '标注名称',
  `measure_type` int NULL DEFAULT NULL COMMENT '测量轮廓类型(0:正常,表示有关系,默认为0\")',
  `measure_relation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '测量关系',
  `measure_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '测量轮廓表示名称:L',
  `measure_number` int NULL DEFAULT NULL COMMENT '测量轮廓标识：1',
  `mean_distance` double NULL DEFAULT NULL COMMENT '平均间距',
  `max_distance` double NULL DEFAULT NULL COMMENT '最大间距',
  `min_distance` double NULL DEFAULT NULL COMMENT '最小间距',
  `inner_angle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内角',
  `exterior_angle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外角',
  `center_point` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中心',
  `location_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注数据类型(LineString,Polygon,point,pc,p,L)',
  `radius` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '周长（圆）',
  `contour` json NULL COMMENT '标注数据',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `measure_full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注名称',
  PRIMARY KEY (`measure_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_measure_del
-- ----------------------------
DROP TABLE IF EXISTS `fr_measure_del`;
CREATE TABLE `fr_measure_del`  (
  `measure_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `slide_id` bigint NOT NULL COMMENT '切片id',
  `annotation_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注类型(AI表示AI算出的标注，Draw表示前端绘制的标注，Measure表示测量工具数据)',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '面积',
  `perimeter` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '周长',
  `number` bigint NULL DEFAULT NULL COMMENT '标注名称',
  `measure_type` int NULL DEFAULT NULL COMMENT '测量轮廓类型(0:正常,表示有关系,默认为0\")',
  `measure_relation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '测量关系',
  `measure_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '测量轮廓表示名称:L',
  `measure_number` int NULL DEFAULT NULL COMMENT '测量轮廓标识：1',
  `mean_distance` double NULL DEFAULT NULL COMMENT '平均间距',
  `max_distance` double NULL DEFAULT NULL COMMENT '最大间距',
  `min_distance` double NULL DEFAULT NULL COMMENT '最小间距',
  `inner_angle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内角',
  `exterior_angle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外角',
  `center_point` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中心',
  `location_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注数据类型(LineString,Polygon,point,pc,p,L)',
  `radius` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '周长（圆）',
  `contour` json NULL COMMENT '标注数据',
  `create_by` bigint NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `measure_full_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注名称',
  `delete_by` bigint NULL DEFAULT NULL COMMENT '删除者',
  `delete_time` datetime NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`measure_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_production
-- ----------------------------
DROP TABLE IF EXISTS `fr_production`;
CREATE TABLE `fr_production`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `special_id` bigint NOT NULL DEFAULT 0 COMMENT '专题ID',
  `organ_tag_id` bigint NOT NULL DEFAULT 0 COMMENT '脏器标签ID',
  `species_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '种属ID',
  `wax_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '蜡块编号',
  `organ_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '脏器名称',
  `organ_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '英文名称',
  `block_count` int NOT NULL DEFAULT 0 COMMENT '取材块数',
  `sex_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '性别（M；F；N）',
  `organ_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '脏器编码',
  `abbreviation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '脏器缩写',
  `organization_id` bigint NOT NULL DEFAULT 0 COMMENT '机构ID',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_special_id`(`species_id` ASC) USING BTREE,
  INDEX `idx_species_id`(`species_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4515 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '专题制片信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fr_single_slide
-- ----------------------------
DROP TABLE IF EXISTS `fr_single_slide`;
CREATE TABLE `fr_single_slide`  (
  `single_id` bigint NOT NULL AUTO_INCREMENT COMMENT '单脏器切片id',
  `slide_id` bigint NOT NULL COMMENT '切片id',
  `thumb_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单脏器图片缩略图地址',
  `category_id` bigint NOT NULL COMMENT '单脏器类型',
  `forecast_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '结构化状态 0未预测、1预测成功、2预测失败、3预测中',
  `diagnosis_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '人工诊断状态 0：未诊断；1：已诊断',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单切片描述',
  `abnormal_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '异常状态 0：默认值 ；1：未见异常',
  `abnormal_create_by` bigint NULL DEFAULT NULL COMMENT '未见异常创建人',
  `abnormal_create_time` datetime NULL DEFAULT NULL COMMENT '未见异常创建时间',
  `area` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '精细轮廓总面积',
  `ai_status_fine` int NULL DEFAULT 0 COMMENT '精轮廓状态：0未预测、1预测成功、2预测失败、3预测中',
  `screening_difference_status` int NOT NULL DEFAULT 0 COMMENT '筛差状态：0未预测、1预测成功、2预测失败、3预测中',
  `fine_contour_time` bigint NULL DEFAULT NULL COMMENT '精轮廓总时间',
  `structure_time` bigint NULL DEFAULT NULL COMMENT '结构化总时间',
  `start_time` datetime NULL DEFAULT NULL COMMENT 'ai算法开始时间',
  `perimeter` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务id',
  PRIMARY KEY (`single_id`) USING BTREE,
  INDEX `idx_slide_id`(`slide_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9015 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '单脏器切片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for fr_slide
-- ----------------------------
DROP TABLE IF EXISTS `fr_slide`;
CREATE TABLE `fr_slide`  (
  `slide_id` bigint NOT NULL AUTO_INCREMENT COMMENT '切片ID',
  `special_id` bigint NULL DEFAULT NULL COMMENT '专题ID',
  `image_id` bigint NULL DEFAULT NULL COMMENT '图像ID',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0存在，1删除',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `viewers` json NULL COMMENT '已阅片用户',
  `ai_status` int NOT NULL DEFAULT 0 COMMENT 'AI分析状态：0-未分析；1-脏器识别中；2-脏器识别异常；3-脏器识别完成（算法接口成功并且核对一致）',
  `group_code` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组别号',
  `gender_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别（M:雄；F:雌）',
  `wax_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜡块编号',
  PRIMARY KEY (`slide_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4005 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '专题选片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_special
-- ----------------------------
DROP TABLE IF EXISTS `fr_special`;
CREATE TABLE `fr_special`  (
  `special_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `topic_id` bigint NULL DEFAULT NULL COMMENT '专题id',
  `topic_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专题编号',
  `special_name` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专题名称',
  `species_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '种属id',
  `trial_id` bigint NULL DEFAULT NULL COMMENT '试验类型',
  `color_type` tinyint NULL DEFAULT NULL COMMENT '染色类型',
  `indicator_id` int NULL DEFAULT 0 COMMENT '病理指标id',
  `status` int NULL DEFAULT 0 COMMENT '状态(0待启动，1进行中，2暂停，3已完成，4锁定)',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常，1删除)',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '机构id',
  `control_group` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对照组',
  `create_by` int NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `principal` bigint NULL DEFAULT NULL COMMENT '专题负责人',
  `is_permanent_del` tinyint NOT NULL DEFAULT 0 COMMENT '是否永久删除',
  `production_save` tinyint NOT NULL DEFAULT 0 COMMENT '制片信息是否保存过：0-未保存过；1-保存过',
  PRIMARY KEY (`special_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 204 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '专题表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_special_annotation_rel
-- ----------------------------
DROP TABLE IF EXISTS `fr_special_annotation_rel`;
CREATE TABLE `fr_special_annotation_rel`  (
  `special_annotation_rel_id` bigint NOT NULL AUTO_INCREMENT COMMENT '项目标注关系id',
  `special_id` bigint NULL DEFAULT NULL COMMENT '专题ID',
  `sequence_number` bigint NULL DEFAULT NULL COMMENT '表序列号',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`special_annotation_rel_id`) USING BTREE,
  INDEX `mr_pal_project_id`(`special_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目标注序列关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_special_lock_log
-- ----------------------------
DROP TABLE IF EXISTS `fr_special_lock_log`;
CREATE TABLE `fr_special_lock_log`  (
  `lock_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `special_id` bigint NULL DEFAULT NULL COMMENT '专题ID',
  `type` smallint NULL DEFAULT 0 COMMENT '类型(0：初始值 1：进行中，2：暂停，3：已完成，4：锁定 5：解锁)',
  `reason` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原因',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`lock_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '专题锁定日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_special_member
-- ----------------------------
DROP TABLE IF EXISTS `fr_special_member`;
CREATE TABLE `fr_special_member`  (
  `member_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户ID',
  `special_id` bigint NOT NULL DEFAULT 0 COMMENT '专题ID',
  `organization_id` bigint NOT NULL DEFAULT 0 COMMENT '机构ID',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`member_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 245 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '专题成员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fr_special_recycling
-- ----------------------------
DROP TABLE IF EXISTS `fr_special_recycling`;
CREATE TABLE `fr_special_recycling`  (
  `recycling_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `special_id` bigint NULL DEFAULT NULL COMMENT '专题表关联id',
  `slide_num` int NULL DEFAULT NULL COMMENT '切片数量',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '到期时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常，1:删除)',
  `create_by` int NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` int NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`recycling_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '专题回收站表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_label_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典标签(英语)',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `filter` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '过滤条件',
  `color` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '色值 如：rgba(217,128,95,1)',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '提示信息',
  `access_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1269 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `menu_name_en` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'enName' COMMENT 'cai dan ming cheng ying wen ',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT ' ' COMMENT '菜单图标',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `is_functional_modules` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '0:是功能模块；1：不是功能模块',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `full_width` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '该页面铺满横向布局，不需要app-sidebar侧边栏，默认0/false',
  `no_fit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '该页面不要需要填充整个页面（超出部分导致content区域出现滚动条），默认\'0\'false',
  `no_header` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '该页面不需要app-header,默认false',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 724 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1消息 2公告）',
  `notice_content` longblob NULL COMMENT '消息内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '消息状态（0未读 1已读）',
  `recipient` bigint NULL DEFAULT NULL COMMENT '消息接收者',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息通知表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `user_id` bigint NULL DEFAULT NULL COMMENT '操作人员ID',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int NULL DEFAULT NULL COMMENT '操作状态（0正常 1异常）',
  `project_id` bigint NULL DEFAULT NULL COMMENT '项目ID',
  `slide_id` bigint NULL DEFAULT NULL COMMENT '切片ID',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17894 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`  (
  `organization_id` bigint NOT NULL AUTO_INCREMENT COMMENT '机构ID、序号',
  `organization_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构名称',
  `contact_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常开启 1禁用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `organization_number` int NULL DEFAULT NULL COMMENT '机构编号',
  PRIMARY KEY (`organization_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机构表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_organization_authorization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization_authorization`;
CREATE TABLE `sys_organization_authorization`  (
  `organization_id` bigint NOT NULL AUTO_INCREMENT COMMENT '机构ID、序号',
  `authorization_member_limit` bigint NULL DEFAULT 0 COMMENT '授权用户人数',
  `authorization_member_used` bigint NULL DEFAULT 0 COMMENT '当前用户数',
  `authorization_image_limit` bigint NULL DEFAULT 0 COMMENT '授权图像数量',
  `authorization_image_used` bigint NULL DEFAULT 0 COMMENT '当前使用图像数量',
  `authorization_time` datetime NULL DEFAULT NULL COMMENT '授权时间',
  `expiration_time` datetime NULL DEFAULT NULL COMMENT '到期时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`organization_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机构授权信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_name_en` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称英文',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `role_sort` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
  `role_level` int NOT NULL DEFAULT 0 COMMENT '角色级别0:普通1:机构管理员',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_system_manage
-- ----------------------------
DROP TABLE IF EXISTS `sys_system_manage`;
CREATE TABLE `sys_system_manage`  (
  `system_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `system_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统code',
  `urls` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'urls',
  `base` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'base',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'username',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'password',
  `system_status` int NULL DEFAULT 1 COMMENT '1开启，2关闭',
  `del_flag` int NULL DEFAULT 1 COMMENT '默认为1，2删除',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`system_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'ldap专用表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_code` int NOT NULL COMMENT '用户编码',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '机构ID',
  `user_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户姓名',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（0系统用户）',
  `dept` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门',
  `email` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女）',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `login_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0首次登录 1非首次登录）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `role_id`(`role_id` ASC) USING BTREE,
  INDEX `organization_id`(`organization_id` ASC) USING BTREE,
  INDEX `sys_user__index1`(`user_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_access_project_records
-- ----------------------------
DROP TABLE IF EXISTS `tb_access_project_records`;
CREATE TABLE `tb_access_project_records`  (
  `records_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `project_id` bigint NULL DEFAULT NULL COMMENT '项目id',
  `access_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`records_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4806 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '访问项目记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_access_view_records
-- ----------------------------
DROP TABLE IF EXISTS `tb_access_view_records`;
CREATE TABLE `tb_access_view_records`  (
  `view_record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `slide_id` bigint NULL DEFAULT NULL COMMENT '切片id',
  `project_id` bigint NULL DEFAULT NULL COMMENT '项目id',
  `access_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`view_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2568 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '访问view页面次数记录首页日活' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_access_view_records_copy1
-- ----------------------------
DROP TABLE IF EXISTS `tb_access_view_records_copy1`;
CREATE TABLE `tb_access_view_records_copy1`  (
  `view_record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `slide_id` bigint NULL DEFAULT NULL COMMENT '切片id',
  `project_id` bigint NULL DEFAULT NULL COMMENT '项目id',
  `access_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`view_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 225 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '访问view页面次数记录首页日活' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_color
-- ----------------------------
DROP TABLE IF EXISTS `tb_color`;
CREATE TABLE `tb_color`  (
  `color_id` int NOT NULL AUTO_INCREMENT COMMENT '颜色ID',
  `rgb` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'rgb',
  `hex` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'hex',
  PRIMARY KEY (`color_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_group
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group`  (
  `group_id` bigint NOT NULL AUTO_INCREMENT COMMENT '组别id',
  `group_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组别',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分组表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_image
-- ----------------------------
DROP TABLE IF EXISTS `tb_image`;
CREATE TABLE `tb_image`  (
  `image_id` bigint NOT NULL AUTO_INCREMENT COMMENT '图像ID',
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '无扩展名文件名称',
  `image_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图像名称（文件名）',
  `image_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图像的绝对路径',
  `image_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图像URL地址',
  `thumb_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图URL地址',
  `macro_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'macro图片URL地址',
  `label_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'label图片RUL地址',
  `cache_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '1024缩略图路径（用于缓存、标注缩略图时需要）',
  `multiple` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原图缩到cache图的倍数',
  `format` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件格式',
  `width` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '宽度',
  `height` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '高度',
  `depth` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '深度',
  `size` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '大小',
  `global_size` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '全局大小',
  `resolving_power` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分辨率',
  `tile_count_list` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '每层的切片个数',
  `level_count` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '总层数（小于2则失败）',
  `chunk_total` smallint UNSIGNED NULL DEFAULT NULL COMMENT '前端总切片个数',
  `md5` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片的MD5值',
  `resolution_x` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'x轴分辨率',
  `resolution_y` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'y轴分辨率',
  `source_lens` int NULL DEFAULT NULL COMMENT '原放大倍数',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `image_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片（切片）编号',
  `status` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '0-上传中；1-上传失败；2-解析中；3-解析失败；4-解析成功',
  `host_id` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '所在主机ID',
  `organization_id` bigint NULL DEFAULT 0 COMMENT '机构ID',
  `round_id` bigint NULL DEFAULT 0 COMMENT '轮次ID（现默认1到8）',
  `biz_type` tinyint NULL DEFAULT NULL COMMENT '业务类型（1原始切片（默认）、2预测切片）',
  `source` tinyint NULL DEFAULT 1 COMMENT '图像来源(1前端上传，2目录选片，3TCP客户端上传)',
  `fuzzy_count_chunk` bigint NULL DEFAULT 0 COMMENT '总块数',
  `analyze_status` tinyint NULL DEFAULT 1 COMMENT '文件名解析状态（0失败1成功）',
  `topic_id` bigint NULL DEFAULT NULL COMMENT '专题id',
  `topic_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '专题号',
  `animal_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '动物号',
  `wax_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '蜡块号',
  `wax_code_order` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `group_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组别号',
  `sex_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别（M；F）',
  `process_flag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '5' COMMENT '处理状态，1-解析中、2-解析失败、3-可用、4-上传失败、5-上传中',
  `period` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '解剖期限',
  PRIMARY KEY (`image_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18968 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_organ
-- ----------------------------
DROP TABLE IF EXISTS `tb_organ`;
CREATE TABLE `tb_organ`  (
  `organ_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '脏器编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脏器名称',
  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脏器名称en',
  `species_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '种属编码',
  `organization_id` bigint NOT NULL COMMENT '机构ID',
  UNIQUE INDEX `organ_uk`(`organ_code` ASC, `name` ASC, `species_id` ASC, `organization_id` ASC) USING BTREE COMMENT '脏器唯一约束'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_organ_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_organ_tag`;
CREATE TABLE `tb_organ_tag`  (
  `organ_tag_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `species_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '种属id',
  `organ_tag_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脏器标签编码',
  `organ_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文名称',
  `organ_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脏器名称',
  `abbreviation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签简称',
  `rgb` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `chromatic_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '色值',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '机构',
  `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除标志(0-正常，1-删除)',
  `algorithm_support_status` tinyint NULL DEFAULT 1 COMMENT '算法是否支持(0-支持，1-不支持)',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `organ_recognition` tinyint NOT NULL DEFAULT 0 COMMENT '脏器识别：0-不可用；1-可用',
  `fine_contour` tinyint NOT NULL DEFAULT 0 COMMENT '精细轮廓：0-不可用；1-可用',
  `structured_analysis` tinyint NOT NULL DEFAULT 0 COMMENT '结构化分析：0-不可用；1-可用',
  `screening_difference` tinyint NOT NULL DEFAULT 0 COMMENT '筛差：0-不可用；1-可用',
  PRIMARY KEY (`organ_tag_id`) USING BTREE,
  INDEX `idx_species_id`(`species_id` ASC) USING BTREE,
  INDEX `idx_organization_id`(`organization_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 261 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_pathological_indicator
-- ----------------------------
DROP TABLE IF EXISTS `tb_pathological_indicator`;
CREATE TABLE `tb_pathological_indicator`  (
  `indicator_id` bigint NOT NULL AUTO_INCREMENT COMMENT '指标ID',
  `indicator_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签集',
  `indicator_name_en` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签集英文',
  `project_total` int NULL DEFAULT NULL COMMENT '关联项目数量',
  `annotation_category_total` int NULL DEFAULT NULL COMMENT '标注类别数量',
  `species_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `organ_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脏器ID',
  `number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '病理指标编号',
  `organization_id` bigint NULL DEFAULT 1 COMMENT '组织机构ID',
  `del_flag` int NULL DEFAULT 0 COMMENT '默认为0，1为删除',
  `indicator_type` int NULL DEFAULT 0 COMMENT '标签类型 0:下拉筛选标签；1:自定义标签',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`indicator_id`) USING BTREE,
  INDEX `index_pathological_indicator`(`indicator_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'tb_pathological_indicator' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_pathological_indicator_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_pathological_indicator_category`;
CREATE TABLE `tb_pathological_indicator_category`  (
  `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '标注类别ID',
  `indicator_id` bigint NULL DEFAULT NULL COMMENT '结构指标ID',
  `category_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注类别名称',
  `structure_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构ID',
  `rgb` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色的RGB值',
  `hex` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色的HEX值',
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色名称(备用)',
  `number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '完整编码',
  `order_number` int NULL DEFAULT NULL COMMENT '图层顺序',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '组织机构ID',
  `anno_type` int NULL DEFAULT 0 COMMENT '0:默认标注类型；1:unlable',
  `del_flag` int NULL DEFAULT 0 COMMENT '默认为0，1为删除',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `category_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注编码',
  `group_number` int NULL DEFAULT NULL COMMENT '组内标签顺序',
  `category_type` int NULL DEFAULT 0 COMMENT '标签类型 0:下拉筛选标签；1:自定义标签',
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `index_annotation_category`(`indicator_id` ASC) USING BTREE COMMENT '唯一约束'
) ENGINE = InnoDB AUTO_INCREMENT = 150 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'tb_pathological_indicator_category' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_species
-- ----------------------------
DROP TABLE IF EXISTS `tb_species`;
CREATE TABLE `tb_species`  (
  `species_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '种属ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '种属名称',
  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '种属名称EN',
  `organization_id` bigint NOT NULL COMMENT '机构ID',
  `badge` int NULL DEFAULT NULL COMMENT '标记(1医学评审使用)',
  UNIQUE INDEX `species_uk`(`species_id` ASC, `name` ASC, `name_en` ASC, `organization_id` ASC) USING BTREE COMMENT '种属唯一约束'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_species_wax_code_template
-- ----------------------------
DROP TABLE IF EXISTS `tb_species_wax_code_template`;
CREATE TABLE `tb_species_wax_code_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `species_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '种属ID',
  `wax_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '蜡块编号',
  `organ_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '脏器名称',
  `organ_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '英文名称',
  `block_count` int NOT NULL DEFAULT 0 COMMENT '取材块数',
  `sex_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'N' COMMENT '性别（M；F；N）',
  `organ_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '脏器编码',
  `abbreviation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '脏器缩写',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_species_id`(`species_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 164 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '种属蜡块模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_structure
-- ----------------------------
DROP TABLE IF EXISTS `tb_structure`;
CREATE TABLE `tb_structure`  (
  `species_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '种属ID',
  `organ_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脏器编码',
  `structure_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构名称',
  `name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构名称en',
  `structure_size` smallint NULL DEFAULT NULL COMMENT '结构标签大小 1：大 2：中 3：小',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'RO' COMMENT 'RO：结构类型  ROA:标注区域 ROE:考核区域',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '机构ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_structure_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_structure_tag`;
CREATE TABLE `tb_structure_tag`  (
  `structure_tag_id` bigint NOT NULL AUTO_INCREMENT COMMENT '结构标签id',
  `structure_tag_set_id` bigint NULL DEFAULT NULL COMMENT '结构标签集ID',
  `structure_tag_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标注类别名称',
  `structure_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结构ID',
  `rgb` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色的RGB值',
  `hex` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色的HEX值',
  `color` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '颜色名称(备用)',
  `order_number` int NULL DEFAULT NULL COMMENT '图层顺序',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '组织机构ID',
  `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除标志(0-正常，1-删除)',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `group_inner_order` int NULL DEFAULT NULL COMMENT '组内标签顺序',
  `type` tinyint NULL DEFAULT 0 COMMENT '标签类型 0-下拉筛选标签；1-自定义标签',
  PRIMARY KEY (`structure_tag_id`) USING BTREE,
  INDEX `idx_structure_tag_set_id`(`structure_tag_set_id` ASC) USING BTREE,
  INDEX `idx_organization_id`(`organization_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3975 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '结构标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_structure_tag_set
-- ----------------------------
DROP TABLE IF EXISTS `tb_structure_tag_set`;
CREATE TABLE `tb_structure_tag_set`  (
  `structure_tag_set_id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签集ID',
  `structure_tag_set_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签集',
  `structure_tag_set_name_en` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签集英文',
  `species_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '种属id',
  `organ_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脏器编码',
  `organization_id` bigint NULL DEFAULT 1 COMMENT '组织机构ID',
  `del_flag` tinyint NULL DEFAULT 0 COMMENT '删除标志(0-正常，1-删除)',
  `type` tinyint NULL DEFAULT 0 COMMENT '标签类型 0-下拉筛选标签；1-自定义标签',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`structure_tag_set_id`) USING BTREE,
  INDEX `IDX_SPECIES_ID`(`species_id` ASC) USING BTREE,
  INDEX `IDX_ORGANIZATION_ID`(`organization_id` ASC) USING BTREE,
  INDEX `IDX_ORGAN_CODE`(`organ_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1537 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '结构标签集' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_topic
-- ----------------------------
DROP TABLE IF EXISTS `tb_topic`;
CREATE TABLE `tb_topic`  (
  `topic_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `topic_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '专题名称（唯一约束）',
  `organization_id` bigint NULL DEFAULT NULL COMMENT '组织机构ID',
  `project_type_id` bigint NULL DEFAULT NULL COMMENT '项目类型ID',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`topic_id`, `topic_name`) USING BTREE,
  UNIQUE INDEX `topic_name_uk`(`topic_name` ASC, `organization_id` ASC, `project_type_id` ASC) USING BTREE COMMENT 'topic_name唯一约束'
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_user_manual
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_manual`;
CREATE TABLE `tb_user_manual`  (
  `user_manual_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_manual_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户手册地址',
  `messages_type` int NULL DEFAULT 0 COMMENT '语种类型 1:中文；2:英文',
  `organization_id` bigint NULL DEFAULT 1 COMMENT '组织机构ID',
  `del_flag` int NULL DEFAULT 0 COMMENT '默认为0，1为删除',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者ID',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_manual_id`) USING BTREE,
  INDEX `index_user_manual_om`(`organization_id` ASC, `messages_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户手册' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for tb_user_setting
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_setting`;
CREATE TABLE `tb_user_setting`  (
  `user_settings_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `enhance_switch` tinyint NOT NULL DEFAULT 1 COMMENT '图像增强开关: 0-关闭, 1-开启',
  `reading_mode` tinyint NOT NULL DEFAULT 2 COMMENT '阅片模式: 1-列表模式, 2-矩阵模式',
  `default_screenshot_width` int NOT NULL DEFAULT 800 COMMENT '默认截图尺寸-宽',
  `default_screenshot_height` int NOT NULL DEFAULT 600 COMMENT '默认截图尺寸-高',
  `scroll_sensitivity` int NOT NULL DEFAULT 50 COMMENT '滚轮灵敏度',
  `drag_sensitivity` int NOT NULL DEFAULT 50 COMMENT '拖拽灵敏度',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_settings_id`) USING BTREE,
  UNIQUE INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table tb_image
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_insert_update_wax_code_order`;
delimiter ;;
CREATE TRIGGER `trg_before_insert_update_wax_code_order` BEFORE INSERT ON `tb_image` FOR EACH ROW BEGIN
    IF NEW.wax_code REGEXP '^[0-9]$' THEN
        -- 情况1: 一位数字
        SET NEW.wax_code_order = LPAD(NEW.wax_code, 2, '0');
    ELSEIF NEW.wax_code REGEXP '^[0-9][A-Za-z]' THEN
        -- 情况2: 一位数字+字母
        SET NEW.wax_code_order = CONCAT('0', NEW.wax_code);
    ELSEIF NEW.wax_code REGEXP '^[0-9]-[A-Za-z0-9]' THEN
        -- 情况3: 一位数字+-+字母/数字
        SET NEW.wax_code_order = CONCAT('0', NEW.wax_code);
    ELSE
        SET NEW.wax_code_order = NEW.wax_code;
    END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table tb_image
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_update_wax_code_order`;
delimiter ;;
CREATE TRIGGER `trg_before_update_wax_code_order` BEFORE UPDATE ON `tb_image` FOR EACH ROW BEGIN
    IF NEW.wax_code <> OLD.wax_code THEN
        IF NEW.wax_code REGEXP '^[0-9]$' THEN
            SET NEW.wax_code_order = LPAD(NEW.wax_code, 2, '0');
        ELSEIF NEW.wax_code REGEXP '^[0-9][A-Za-z]' THEN
            SET NEW.wax_code_order = CONCAT('0', NEW.wax_code);
        ELSEIF NEW.wax_code REGEXP '^[0-9]-[A-Za-z0-9]' THEN
            SET NEW.wax_code_order = CONCAT('0', NEW.wax_code);
        ELSE
            SET NEW.wax_code_order = NEW.wax_code;
        END IF;
    END IF;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
