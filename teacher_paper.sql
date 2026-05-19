/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : localhost:3306
 Source Schema         : teacher_paper

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 25/06/2025 03:43:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for papers
-- ----------------------------
DROP TABLE IF EXISTS `papers`;
CREATE TABLE `papers`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `teacher_id` bigint NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `publish_date` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `file_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_size` bigint NOT NULL,
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_papers_teachers1_idx`(`teacher_id` ASC) USING BTREE,
  CONSTRAINT `fk_papers_teachers1` FOREIGN KEY (`teacher_id`) REFERENCES `teachers` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of papers
-- ----------------------------
INSERT INTO `papers` VALUES (1, 2, '一种有效负载均衡的网格Web服务体系结构模型', '基于Web服务资源的小世界模型,提出了一种可无缝结合网格 OGSA框架的 Web服务体系结构与动态的资源负载均衡分配模型,它以 Web服务资源注册中心(Web Services Resource Register Center,WSRRC)作为Web服务资源查找的入口,以Web服务资源 ID类形成的虚拟资源树作为资源逻辑组织与负载均衡分配结构,以小世界形成的区域代理自治系统(Area Proxy Autonomy System, APAS)来进行 Web服务资源的维护.并详细描述了该模型的实现机制、组织协议与数据结构,着重研究了逻辑资源树负载信息的传播机制及负载均衡策略,模拟试验表明该模型是合理而有效的:它可以在较小开销下获得较满意的资源组织与定位性能,能适应网格 Web服务资源的异构、复杂与动态性,具有良好的可扩展性及保持全局网络负载均衡的良好性能.', '2024-12-01 00:00:00', '83377cdd-6ea7-4151-bed4-2cf4755c2608_一种有效负载均衡的网格Web服务体系结构模型.pdf', '一种有效负载均衡的网格Web服务体系结构模型.pdf', 784301, 'application/pdf', '2025-06-24 14:26:45', '2025-06-24 16:02:55');
INSERT INTO `papers` VALUES (2, 3, '恰有k个悬挂点的n阶双圈和三圈图的拟拉普拉斯谱半径', '设k,n为两个确定的正整数.本文得到了当1≤k≤n-7时恰有k个悬挂点的n阶连通三圈图的最大拟拉普拉斯谱半径的唯一极图,也得到了当1≤k≤n-5时恰有k个悬挂点的n阶连通双圈图的最大拟拉普拉斯谱半径的唯一极图.', '2011-02-01 00:00:00', 'c61b0834-f776-4e38-952a-274703e8889e_恰有k个悬挂点的n阶双圈和三圈图的拟拉普拉斯谱半径.pdf', '恰有k个悬挂点的n阶双圈和三圈图的拟拉普拉斯谱半径.pdf', 267984, 'application/pdf', '2025-06-24 16:31:36', '2025-06-24 16:31:36');
INSERT INTO `papers` VALUES (3, 4, '基于集成学习的改进深度嵌入聚类算法', '近年来深度学习的迅速发展为聚类研究提供了一个有力的工具,并衍生出了许多基于深度神经网络的聚类方法。在这些方法中,深度嵌入聚类(DEC)因其可对深度表示学习和聚类分配同时进行优化的优势而日益受到关注。但是,深度嵌入聚类的一个局限性在于其超参数λ的敏感性,而往往需要诉诸人工调节来解决。对此,提出一种基于集成学习的改进深度嵌入聚类(IDECEL)方法。相较于寻求单个最优超参数的常规做法,提出以多样化超参数λ构建一组具有差异性的基聚类,并结合熵理论对基聚类集合的簇不确定性进行评估与加权,进而在簇与样本之间构建一个局部加权二部图模型,再将之高效划分以得到一个更优聚类结果。在多个数据集上的实验结果表明,提出的IDECEL方法不仅可缓解常规DEC算法超参数敏感性的问题,同时也表现出比其他多个深度聚类和集成聚类方法更为鲁棒的聚类性能。', '2024-04-01 00:00:00', '6a4e4cee-65dd-4a8b-8786-da0b55cb57df_基于深度学习和时空关联建模的降水数据地形订正方法.pdf', '基于深度学习和时空关联建模的降水数据地形订正方法.pdf', 664201, 'application/pdf', '2025-06-24 17:02:47', '2025-06-24 17:07:07');
INSERT INTO `papers` VALUES (4, 4, '基于决策加权的聚类集成算法', '聚类集成的目标是融合多个聚类成员的信息以得到一个更优、更鲁棒的聚类结果。针对聚类成员可靠度估计与加权问题,提出了一个基于二部图模型与决策加权机制的聚类集成方法。在该方法中,每个聚类成员被视作一个包含若干连接决策的集合。每个聚类成员的决策集合享有一个单位的可信度,该可信度由集合内的各个决策共同分享。基于可信度分享的思想,进一步对各个聚类成员内的决策进行加权,并将此决策加权机制整合至一个统一的二部图模型;然后利用快速二部图分割算法将该图划分为若干子集,以得到最终聚类结果。实验结果表明,该方法相较于其他对比方法在聚类效果及运算效率上均表现出显著优势。', '2016-03-01 00:00:00', '270e56e4-65d3-4760-898e-7bf0211a29ac_基于决策加权的聚类集成算法.pdf', '基于决策加权的聚类集成算法.pdf', 584848, 'application/pdf', '2025-06-24 17:12:50', '2025-06-24 17:12:50');
INSERT INTO `papers` VALUES (5, 5, '人脸表情的LBP特征分析', '为了有效提取面部表情特征,提出了一种新的基于LBP(局部二值模式)特征的人脸表情识别特征提取方法。首先用均值方差法对表情图像进行灰度规一化,通过对图像进行积分投影,定位出眉毛、眼睛、鼻和嘴巴这些关键特征点,进而划分出各特征部件所在子区域,然后对子区域进行分块,提取各个子区域的分块LBP直方图特征。为了验证所提出的方法的合理性,最后在JAFFE表情库上进行了实验,结果表明提出的方法能够有效地描述表情的特征。', '2011-02-01 00:00:00', '63bbd13a-4add-4b3f-a69d-514e930a2bed_人脸表情的LBP特征分析.pdf', '人脸表情的LBP特征分析.pdf', 1499454, 'application/pdf', '2025-06-24 17:51:19', '2025-06-24 17:51:19');

-- ----------------------------
-- Table structure for teachers
-- ----------------------------
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers`  (
  `id` bigint NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `department` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `school` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_teachers_user`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_teachers_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_teachers_users` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teachers
-- ----------------------------
INSERT INTO `teachers` VALUES (2, '张连明', '信息科学与工程学院', '工学博士，二级教授，博士生导师，湖南师范大学世承人才学术带头人，计算机网络与安全团队负责人，物联网工程系主任，物联网技术与应用重点实验室主任，智能感知与计算湖南省现代产业学院副院长，网络与通信湖南省研究生优秀教学团队负责人。', '2025-06-24 10:33:22', '2025-06-24 18:57:47', '湖南师范大学', 2);
INSERT INTO `teachers` VALUES (3, '刘木伙', '数学与信息(软件)学院', '副教授，硕士研究生导师。2014年于南京师范大学获理学博士学位，美国Mathematical Reviews评论员，主要从事结构图论和图谱理论等方面的研究', '2025-06-24 16:27:37', '2025-06-24 18:57:47', '华南农业大学', 3);
INSERT INTO `teachers` VALUES (4, '黄栋', '数学与信息学院', '华南农业大学数学与信息学院/软件学院副院长、副教授， 广东省青年珠江学者，广东省青科协理事，中国计算机学会（CCF）数字农业分会副秘书长。致力于人工智能与大数据分析研究，主要研究内容包括复杂大数据聚类（大规模聚类/集成聚类/多视图聚类/深度聚类）、无监督/自监督学习、多模态学习、大语言模型（LLM）及其应用等。', '2025-06-24 16:58:44', '2025-06-24 18:57:47', '华南农业大学', 4);
INSERT INTO `teachers` VALUES (5, '刘伟锋', '控制科学与工程学院', '博士，教授，博士生导师，山东省优秀研究生指导教师，青岛市拔尖人才，山东省高等学校青年创新团队带头人，山东省人工智能学会理事，山东省自动化学会常务理事，CCF计算机视觉专委会委员，IEEE SMC协会感知计算技术委员会主席，CCF高级会员，CSIG会员，IEEE高级会员，ACM会员，ACM SIGMM中国分会会员。', '2025-06-24 17:31:12', '2025-06-24 18:57:47', '中国石油大学（华东）', 5);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `enabled` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `UKr43af9ap4edm43mmtq01oddj6`(`username` ASC) USING BTREE,
  UNIQUE INDEX `UK6dotkott2kjsp8vw4d0m25fb7`(`email` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '$2a$10$Nj1ckyEQKBs/b1q2GmgiT.GUVQ4W/HEX2FrCBUdlvghfW4NENlTme', 'admin@example.com', 'ROLE_ADMIN', '2025-06-24 03:05:09', '2025-06-24 03:05:09', 1);
INSERT INTO `users` VALUES (2, '1', '$2a$10$7TDpBvJ8cysW4C8wI2tu5OJXFdhAGKII/85Q7hYfZ4rYjSP9m6QvW', '1@example.com', 'ROLE_TEACHER', '2025-06-24 10:33:22', '2025-06-24 22:28:57', 1);
INSERT INTO `users` VALUES (3, '2', '$2a$10$W8Zcn88wzqgYlJ8zxb1kpenBln3kJ1XCY6WS17d0mZ6YdWkl3eE/W', '2@example.com', 'ROLE_TEACHER', '2025-06-24 16:27:37', '2025-06-24 16:27:37', 1);
INSERT INTO `users` VALUES (4, '3', '$2a$10$sCKJG0rV5mWiCApoplIYYeGPfWkuIq6Gp9BjdcPvmkO8EcNH/QE/C', '3@example.com', 'ROLE_TEACHER', '2025-06-24 16:58:44', '2025-06-24 16:58:44', 1);
INSERT INTO `users` VALUES (5, '4', '$2a$10$sedfp.H/Rqiz70VwpXf0oueOOuReMgkG6fsvhNDhiLDMn/xm2h2p6', '4@example.com', 'ROLE_TEACHER', '2025-06-24 17:31:12', '2025-06-24 17:31:12', 1);

SET FOREIGN_KEY_CHECKS = 1;
