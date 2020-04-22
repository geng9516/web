package jp.smartcompany.job.generator;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.util.List;


/**
 * @author Xiao Wenpeng
 * 注意：不要随意运行！
 */
public class MPGenerator {

    public static void main(String[] args)  {
        //数据库配置
        String dataBaseUrl = "jdbc:postgresql://47.74.50.9:5532/web-work";
        String DataBaseDriver = "org.postgresql.Driver";
        String username = "soadmin";
        String password = "ab9t_P6TNm!WB2";
        //作者名，用于生成文件注解时使用。
        String author = "Wang ZiYue";
        //controller文件是不是带RestController
        boolean restControllerStyle = false;
        //entity是否需要继承父类，如果需要则将已有的父类路径写下面，同理可配置Controller等父类。
//        String superEntityClass = "jp.smartcompany.job.modules.base.pojo.entity.AbstractDBBean";

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath+ "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setEntityName("%sDO");
        gc.setFileOverride(false);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setIdType(IdType.AUTO);
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dataBaseUrl);
        dsc.setDriverName(DataBaseDriver);
        dsc.setUsername(username)
            .setPassword(password)
            .setTypeConvert(new PostgreSqlTypeConvert(){
                @Override
                public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                    if (fieldType.toLowerCase().contains("numeric")) {
                        return DbColumnType.LONG;
                    } else if (fieldType.toLowerCase().contains("date")) {
                        return DbColumnType.DATE;
                    } else if(fieldType.toLowerCase().contains("decimal")) {
                        return DbColumnType.DOUBLE;
                    }else {
                        return DbColumnType.STRING;
                    }
                }
        });
        dsc.setSchemaName("public");
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("jp.smartcompany.job.modules");
        pc.setModuleName("core");
        pc.setEntity("pojo.entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");


        mpg.setPackageInfo(pc);

        // 自定义输出配置
        List<FileOutConfig> focList = CollUtil.newArrayList();


        focList.add(new FileOutConfig("/generator/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });


        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity("/generator/entity.java.vm");
        templateConfig.setMapper("/generator/mapper.java.vm");
        templateConfig.setServiceImpl("/generator/serviceImpl.java.vm");
        templateConfig.setService("/generator/service.java.vm");
        templateConfig.setXml(null);
        templateConfig.setController(null);
        mpg.setTemplate(templateConfig);

        // 生成文件的策略配置 主要包括文件名啊，表前缀如何处理 ，之类的
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true);
        strategy.setSkipView(false);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setRestControllerStyle(restControllerStyle);
        strategy.setEntitySerialVersionUID(true);
        strategy.setEntityTableFieldAnnotationEnable(true);

        strategy.setExclude(

        );


        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
