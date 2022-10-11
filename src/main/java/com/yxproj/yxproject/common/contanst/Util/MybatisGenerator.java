package com.yxproj.yxproject.common.contanst.Util;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.yxproj.yxproject.entity.BaseEntity;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.sql.SQLException;

@Log4j2
public class MybatisGenerator{

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
            .Builder("jdbc:mysql://localhost:3306/waimai?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=GMT%2B8&useAffectedRows=true",
            "root", "123456");

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        File file = new File(System.getProperty("user.dir"), "src/main/java/com/yxproj/yxproject");
        String path = file.getPath();
        log.info("path:"+path);
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) -> builder.author("ly")
                        .outputDir(new File(System.getProperty("user.dir"), "src/main/java")
                                .getAbsolutePath().replaceAll("\\\\", "/") + "/").fileOverride())
                // 包配置
                .packageConfig((scanner, builder) -> builder.parent("com.yxproj.yxproject"))
                // 策略配置
                .strategyConfig(builder -> builder.addInclude("tbl_user")
                        .addTablePrefix("tbl_")
                        .entityBuilder()
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
                        .naming(NamingStrategy.no_change)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .addSuperEntityColumns("id","isDeleted", "creatorUserId", "creationTime", "deleterUserId", "modifyTime")
                        .addTableFills(new Column("creationTime", FieldFill.INSERT))
                        .addTableFills(new Property("modifyTime", FieldFill.INSERT_UPDATE))
                        .idType(IdType.AUTO)
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
