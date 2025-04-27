package com.wanwan.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * <p>
 * Mybatis-Plus代码生成器
 * </p>
 *
 * @author Patrick
 * @since 2024-12-23
 */
public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/数据库?serverTimezone=Asia/Shanghai", "username", "password")

                //~~~~~~~~~~~~~~~Part1: 全局配置~~~~~~~~~~开始~~~~~~~~~~
                .globalConfig((scanner, builder) -> {
                    builder.author("作者") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .outputDir(System.getProperty("user.dir") + "/src/main/java") // 设置输出目录
                            .dateType(DateType.ONLY_DATE) // 设置日期类型
                            .commentDate("yyyy-MM-dd") // 设置注释日期
                            .disableOpenDir(); //禁止打开文件目录，默认false
                })
                //~~~~~~~~~~~~~~~全局配置~~~~~~~~~~结束~~~~~~~~~~

                //~~~~~~~~~~~~~~~Part2: 包配置~~~~~~~~~~开始~~~~~~~~~~
                .packageConfig((scanner, builder) -> {
                    builder.parent("com.***.***")  // 设置父包名
                            // .moduleName(null) // 设置模块名
                            .entity("entity")  // 设置实体包名
                            .service("service")  // 设置服务接口包名
                            .serviceImpl("service.impl")  // 设置服务实现包名
                            .mapper("mapper")  // 设置mapper包名
                            .xml("mapper.xml")  // 设置xml包名
                            .controller("controller")  // 设置controller包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper"));  // 设置mapper xml文件路径
                })
                //~~~~~~~~~~~~~~~包配置~~~~~~~~~~结束~~~~~~~~~~

                //~~~~~~~~~~~~~~~Part3: 策略配置~~~~~~~~~~开始~~~~~~~~~~
                .strategyConfig((scanner, builder) -> builder
                        .addInclude("***") // 设置需要生成的表名
                        .addTablePrefix("*_") // 设置过滤表前缀

                        /**
                         * 实体配置
                         */
                        .entityBuilder()
                        //.superClass(SuperCommonPO.class) // 设置实体类父类-父类中存在的字段不会在实体类中存在
                        .enableLombok() // 开启 lombok 模式
                        //.disableSerialVersionUID() // 禁用生成 serialVersionUID
                        .naming(NamingStrategy.underline_to_camel) // 数据表映射实体命名策略：默认下划线转驼峰underline_to_camel
                        .columnNaming(NamingStrategy.underline_to_camel) // 表字段映射实体属性命名规则：默认null，不指定按照naming执行
                        .idType(IdType.AUTO) // 添加全局主键类型
                        .enableTableFieldAnnotation() // 开启实体字段注解
                        .formatFileName("%s") // 格式化实体名称，%s取消首字母I,

                        /**
                         * mapper配置
                         */
                        .mapperBuilder()
                        .enableMapperAnnotation() // 开启mapper注解
                        .enableBaseResultMap() // 启用xml文件中的BaseResultMap 生成
                        //.enableBaseColumnList() // 启用xml文件中的BaseColumnList
                        .formatMapperFileName("%sMapper") // 格式化Mapper类名称
                        .formatXmlFileName("%sMapper") // 格式化xml文件名称

                        /**
                         * service配置
                         */
                        .serviceBuilder()  // 设置service策略
                        .formatServiceFileName("%sService") // 格式化 service 接口文件名称
                        .formatServiceImplFileName("%sServiceImpl") // 格式化 service 接口文件名称
                        .controllerBuilder()  // 设置controller策略
                        .enableRestStyle()  // 开启rest风格
                        //~~~~~~~~~~~~~~~策略配置~~~~~~~~~~结束~~~~~~~~~~

                        .build())

                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())  // 设置模板引擎
                .execute();
    }
}

