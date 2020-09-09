package jp.smartcompany.boot.configuration;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import jp.smartcompany.boot.interceptor.SqlParameter2NullInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Xiao　Wenpeng
 *　MyBatisPlus核心
 */
@Configuration
public class MyBatisPlusConfiguration {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = CollUtil.newArrayList();
        // SQL攻击拦截器
        //　拒绝恶意删除
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        // 乐观锁
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public OracleKeyGenerator oracleKeyGenerator(){
        return new OracleKeyGenerator();
    }

    @Bean
    public SqlParameter2NullInterceptor sqlParameter2NullInterceptor() {
        return new SqlParameter2NullInterceptor();
    }

}
