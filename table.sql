CREATE TABLE `tb_goods` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `img` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `class_id` bigint(20) NOT NULL COMMENT '商品类别ID',
  `price` decimal(8,2) NOT NULL COMMENT '商品单价',
  `bar_code` varchar(20) DEFAULT NULL COMMENT '商品条码',
  `measure` varchar(50) NOT NULL COMMENT '商品计量单位(如：件，瓶)',
  `inventory` int(8) NOT NULL COMMENT '商品库存',
  `detail` varchar(200) DEFAULT NULL COMMENT '商品详情说明',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效  0：有效  1：无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

CREATE TABLE `tb_goods_class` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `class_name` varchar(20) NOT NULL COMMENT '商品类别名',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效  0：有效  1：无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类别表';

CREATE TABLE `tb_measure_word` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '单位名称',
  `priority` tinyint(2) NOT NULL DEFAULT '0' COMMENT '优先级 值越大 优先级越高',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效 0：有效 1：无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='量词表';

CREATE TABLE `tb_order` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `sn` varchar(20) NOT NULL COMMENT '订单号',
  `total_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `pay_amount` decimal(8,2) NOT NULL COMMENT '实际支付金额',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付方式  1:微信  2:支付宝 3:现金',
  `pay_status` tinyint(1) NOT NULL COMMENT '支付状态  0:未支付 1：已支付 2：已退款',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发货方式  0：自提  1：配送',
  `address` varchar(255) DEFAULT NULL COMMENT '配送地址',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发货状态  0:未发货 1：已发货',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效  0：有效  1：无效',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_SN` (`sn`) USING BTREE COMMENT '订单号唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单主表';

CREATE TABLE `tb_order_details` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `sn` varchar(20) NOT NULL COMMENT '订单号',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `price` decimal(8,2) NOT NULL COMMENT '商品单价',
  `num` int(8) NOT NULL COMMENT '数量',
  `total_amount` decimal(8,2) NOT NULL COMMENT '总金额',
  `pay_amount` decimal(8,2) NOT NULL COMMENT '实付金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_SN` (`sn`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

CREATE TABLE `tb_shopping_cart` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `num` int(8) NOT NULL COMMENT '数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `INDEX_USER_ID` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';

CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `img` varchar(255) DEFAULT NULL COMMENT '头像',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `openId` varchar(50) NOT NULL COMMENT '微信openid',
  `address` varchar(255) DEFAULT NULL COMMENT '配送地址',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效  0：有效  1：无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_OPENID` (`openId`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';