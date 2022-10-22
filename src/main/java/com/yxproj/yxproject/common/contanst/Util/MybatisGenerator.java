package com.yxproj.yxproject.common.contanst.Util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.yxproj.yxproject.entity.BaseEntity;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.sql.SQLException;
import java.util.Collections;

@Log4j2
public class MybatisGenerator {

//    /**
//     * <p>
//     * 读取控制台内容
//     * </p>
//     */
//    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + "：");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotEmpty(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "！");
//    }

    /**
     * 执行初始化数据库脚本
     */
//    public static void before() throws SQLException {
//        Connection conn = DATA_SOURCE_CONFIG.build().getConn();
//        InputStream inputStream = H2CodeGeneratorTest.class.getResourceAsStream("/sql/init.sql");
//        ScriptRunner scriptRunner = new ScriptRunner(conn);
//        scriptRunner.setAutoCommit(true);
//        scriptRunner.runScript(new InputStreamReader(inputStream));
//        conn.close();
//    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/yxproject?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=GMT%2B8&useAffectedRows=true",
            "root", "123456");

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author("ly")
                        .outputDir(new File(System.getProperty("user.dir"), "src/main/java")
                                .getAbsolutePath().replaceAll("\\\\", "/") + "/").fileOverride())
                // 包配置
//                .packageConfig((scanner, builder) -> builder.parent("com.yxproj.yxproject")
//                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml,"F:\\java\\yxproject\\src\\main\\resources\\mapper"))
//                )
                .packageConfig((scanner, builder) -> builder.parent("com.yxproj.yxproject")
                        .mapper("mapper")
                        .xml("mapper")
//                        .controller("controller")
                        .entity("entity")
                        .service("service")
                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "F:\\java\\yxproject\\src\\main\\resources\\mapper"))
                )

                // 策略配置
                .strategyConfig(builder -> builder.addInclude("t_user_role")

                                .addTablePrefix("t_")

                                .entityBuilder()
                                .columnNaming(NamingStrategy.underline_to_camel)
                                        .superClass(BaseEntity.class)
                                .disableSerialVersionUID()
                                .enableChainModel()
                                .enableLombok()
                                .enableRemoveIsPrefix()
                                .enableTableFieldAnnotation()
                                .enableActiveRecord()
                                .versionColumnName("version")
                                .versionPropertyName("version")
                                .logicDeleteColumnName("isDeleted")
                                .logicDeletePropertyName("deleteFlag")
                                .naming(NamingStrategy.underline_to_camel)
//                        .columnNaming(NamingStrategy.valueOf("_"))
                                .addSuperEntityColumns("id", "is_deleted", "creator_UserId", "creation_Time", "deleter_UserId", "modify_Time")
                                .addTableFills(new Column("creation_Time", FieldFill.INSERT))
                                .addTableFills(new Property("modify_Time", FieldFill.INSERT_UPDATE))
                                .idType(IdType.AUTO)

                                .controllerBuilder().enableRestStyle()
                                .build()


                )

                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                 */
                .templateEngine(new FreemarkerTemplateEngine())

                .execute();
    }

}
