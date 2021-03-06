# Web就业
基于spring-boot的web就业系统源码，基于maven构建

### 项目结构详细说明
```html
├── 就业数据库 就业脚本文件存放目录
     ├── oracle
           ├── markdown文档
                ├── 表
                ├── 视图
     ├── postgresql
           ├── markdown文档
                ├── 表
                ├── 视图
├── 开发文档 开发时语法和规范的注意 
├── src 源码目录  
     ├── main 主目录  
     ├── jp.smartcompany.job 主包  
          ├── advice Spring advice（文件夹）   
               ├── ExceptionAdvice 异常处理切面  
               ├── RequestAdvice 请求统一处理切面  
               ├── ResponseAdvice 返回统一处理切面  
               ├── annotation 系统自定义注解(文件夹)   
                      ├── Encrypt 标识是否将返回值加密  
                      ├── IgnoreResponseSerializable 告诉controler或者controller中的方法不需要经过ResponseAdvice切面处理   
                      ├── OperationLog 是否将操作记入数据库中的_operation_log表，注意，原则上只有对数据进行操作时（增，删，改）才需要用此注解进行标记   
               ├── aspect Spring aspect（文件夹)  
                      ├── LogAspect 日志切面（记录用户操作失败，登录动作以及CUD操作日志）  
               ├── common 系统公共类（文件夹，不需要开发人员维护，有专人维护）  
                      ├── Constant 公共常量  
                      ├── GlobalException 公共异常  
                      ├── GlobalResponse 公共返回  
                      ├── Query 请求公共参数封装类（分页参数，比如按字段排序，升序还是降序等）  
                      ├── Regex  正则表达式  
               ├── configuration 系统核心配置项，比如Shiro，跨域，MybatisPlus等的配置项（文件夹）  
               ├── controller 系统Controller层（文件夹）  
                      ├── SysErrorController 专门处理Spring-Boot的404和500错误的controller  
               ├── enums 公共枚举（文件夹，不需要开发人员维护，有专人维护）  
                      ├── ErrorMessage 返回给前端的错误信息   
                      ├── ResponseMessage 返回信息接口  
                      ├── SuccessMessage 返回给前端的成功信息   
               ├── filter 过滤器（文件夹，不需要开发人员维护，有专人维护）    
                      ├── CorsFilter 跨域设置过滤器  
               ├── group 参数校验组,配合spring的参数校验对前端传递过来的参数进行组验证，如某些字段在增加操作时需要验证，则加上AddGroup注解，某些参数只需要在修改操作时才需要验证，则加上UpdateGroup注解（文件夹，不需要开发人员维护，有专人维护）    
                      ├── AddGroup 添加动作  
                      ├── UpdateGroup  修改动作
                      ├── Group 默认组，一般不会使用
               ├── interceptor 过滤器（文件夹，不需要开发人员维护，有专人维护）  
                      ├── AuditInterceptor （请求记录日志拦截器）   
               ├── modules 业务模块存放文件夹，本系统业务的核心存放目录（文件）    
                      ├── base 用于存放其他模块会用到的公共类，如BaseBean，枚举（文件夹）   
                          ├── pojo 实体类目录   
                               ├── entity 数据库表映射的实体类    
                               ├── enums 枚举  
                               ├── handler 自定义类型映射处理器（如果数据库中的数据字段是json，就需要在此处定义handler来处理）  
                      ├── core 系统基建核心，除已有的quartz模块外，其后添加的业务模块都必须遵守严格遵守以下的包结构，以保持整个项目的清晰整洁（文件夹）  
                          ├── dao 数据库持久层，其下的每一个接口都继承于MyBatisPlus已有的公共BaseMapper，单表的增删改查语句不需要再书写（文件夹）  
                          ├── manager 通用业务处理层，借鉴阿里的架构指南，与传统MVC区别开的主要一层，这一层有以下三个特征： 1） 对第三方平台封装的层，预处理返回结果及转化异常信息。 2） 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理。 3） 与 DAO 层交互，对多个 DAO 的组合复用。
                          ├── pojo 实体类目录
                                ├── do 数据库映射的类，此包下的类必须以DO作为结尾
                                ├── dto 数据传输类，负责映射前端传入的参数，此包下的类必须以DTO结尾
                                ├── vo 数据包装类，负责将后端数据包装成对应VO返回给前端，此包下的类必须以VO结尾
                          ├── service 相对具体的业务逻辑服务层   
                          ├── package-info 模块内的公共常量和类定义文件  
                      ├── quartz 定时任务    
                          ├── dao 数据库持久层（文件夹），其下的每一个接口都继承于MyBatisPlus已有的公共BaseMapper，单表的增删改查语句不需要再书写（文件夹）  
                          ├── manager 通用业务处理层（文件夹），借鉴阿里的架构指南，与传统MVC区别开的主要一层，这一层有以下三个特征： 1） 对第三方平台封装的层，预处理返回结果及转化异常信息。 2） 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理。 3） 与 DAO 层交互，对多个 DAO 的组合复用。      
                          ├── pojo 实体类目录（文件夹）
                                ├── pojo 实体类目录
                                ├── do 数据库映射的类，此包下的类必须以DO作为结尾
                                ├── dto 数据传输类，负责映射前端传入的参数，此包下的类必须以DTO结尾
                                ├── vo 数据包装类，负责将后端数据包装成对应VO返回给前端，此包下的类必须以VO结尾
                          ├── task 自定义定时任务模块（文件夹）   
                          ├── util 定时任务类库封装工具类（文件夹）  
                          ├── package-info 模块内的公共常量和类定义文件  
               ├── util 系统工具类（文件夹，不需要开发人员维护，有专人维护） 
                    ├── ContextUtil 获取请求上下文  
                    ├── IpUtil 客户端ip获取工具
                    ├── LevelUtil 实体如果存在子父级关系，则会用到此工具类生成层级记录：0.1.2
                    ├── PageUtil 分页工具
                    ├── ShiroUtil Shiro工具类，配合shiro获取登录用户的信息
                    ├── SysUtil 系统公共业务工具类
          ├──NextJobApplication 系统启动类（开发环境下直接运行此类就可以启动项目） 
          ├──ServletInitializer 打成war包必须的Servlet容器类
     ├── resources  
          ├── mapper  
                ├── core 核心模块dao对应的mybatis-plus的xml文件
                ├── quartz 定时任务对应的mybatis-plus的xml文件
          ├── static  静态资源存放文件夹
          ├── templates 模板存放文件夹
          ├── application.yml 项目核心配置文件 
          ├── application-dev.yml 本地开发环境相关配置
          ├── application-prod.yml 线上生产环境相关配置
          ├── application-test.yml 线上测试环境相关配置(application.yml中默认的环境配置)
          ├── banner.txt  系统启动时的banner
     ├── test 单元测试（文件夹）
├── .gitignore  git忽略文件
├── HELP.md  系统引用的一些工具类
├── pom.xml  maven构建文件
├── README.md 项目结构说明文件
```

#### modules目录中必须具备以下形式的目录结构(必须遵守，各模块遵照此结构最大程度上保持各自的独立性)
```html
├── dao 数据库持久层（文件夹），其下的每一个接口都继承于MyBatisPlus已有的公共BaseMapper，单表的增删改查语句不需要再书写（文件夹）  
├── manager 通用业务处理层（文件夹），借鉴阿里的架构指南，与传统MVC区别开的主要一层，这一层有以下三个特征： 1） 对第三方平台封装的层，预处理返回结果及转化异常信息。 2） 对 Service 层通用能力的下沉，如缓存方案、中间件通用处理。 3） 与 DAO 层交互，对多个 DAO 的组合复用。      
├── service 相对具体的业务逻辑服务层 
├── pojo 实体类目录（文件夹）
    ├── pojo 实体类目录
    ├── do 数据库映射的类，此包下的类必须以DO作为结尾
    ├── dto 数据传输类，负责映射前端传入的参数，此包下的类必须以DTO结尾
    ├── vo 数据包装类，负责将后端数据包装成对应VO返回给前端，此包下的类必须以VO结尾 
├── JobBean spring的bean名称定义文件，防止使用默认bean名称时产生冲突和混淆  
├── package-info 1.为标注在包上的annotation提供便利；2.声明包的私有类，常量；3.提供包的整体注释说明
├── JobConstant 本模块所用到的公共常量
├── JobError 本模块抛出的异常枚举
├── JobSuccess 本模块成功返回数据时针对接口不同返回的定制成功信息枚举
├── JobValidateMessage Spring参数验证时的错误信息定义文件（暂保留，根据业务需要可能后续返回错误信息也要存入数据库中支持可配置）
├── ... 根据需要可自行添加额外的目录或类，但必须要在package-info中对这些额外添加的目录做好描述和解释
```
#### 其他说明
   1. 除了src下的util目录提供的工具类，系统主要还用到了hutool作为工具库（包括日期，JSON数据格式化等），切勿重复定义工具类，尽量查询hutool官方文档使用已经有的工具类。
   2. 系统目前数据库采用postgreql
   3. 代码风格保持遵循阿里巴巴的风格规范，请格局提示提示尽量避免出现不符合风格规范的写法
   4. 项目操作excel使用hutool的Excel对apache-poi进行的封装
   5. 项目操作pdf使用itext7
   6. 项目操作csv不使用任何工具，自定义工具完成
   7. 安全验证框架采用的是apache的shiro，权限认证机制以基于角色来授权的的rbac模式为准
   8. 系统相关数据表前缀说明：
        1. conf_XX_XX 系统表 控制功能开关
        2. dt_XX_XX 中间表 存储UPDS数据
        3. hist_XX_XX  smartcompany功能表  主要只用HIST_DESIGNATION
        4. mast_XX_XX  MAST数据　　
            MAST_GENERIC_DETAIL　　名称マスタ明細データ
        5. tmg_XX_XX  就业系统表,如：TMG_NTF_xx 请假，TMG_PAID_xx赋予
        6. t_xx 本次系统升级新增数据表
   
  
#### 开发必须插件：
  1. Postgreql(9.3+)
  2. Maven(3.6.1)
  3. JDK(1.8)
  4. intellij idea(推荐)
  
#### 经常会需要查询的官方文档
1. [MybatisPlus](https://mybatis.plus)
2. [Hutool](https://hutool.cn)  
3. [itext](https://itextpdf.com)

#### Idea必须提前安装的插件
> [Lombok](https://plugins.jetbrains.com/plugin/6317-lombok)  
> [GenerateSerialVersionUID 自动生成序列号](https://blog.csdn.net/qq_42651904/article/details/90680430)  
> [Alibaba Java Coding Guidelines 阿里巴巴代码风格检测工具](https://blog.csdn.net/hou549135295/article/details/82743698)  
> [MyBatisX MybatisXML文件编写插件](https://mybatis.plus/guide/mybatisx-idea-plugin.html)   

#### 提交代码时的格式规范:
> 「type」: 「subject」<br>
> 例：　git commit -m "refactor:&nbsp;重构此文件xxx方法的逻辑"

#### 提交代码的类型
1. feat： 新功能（feature）
2. fix： 修复bug
3. docs： 添加文档（documentation）
4. style： 只是修改了代码的格式（不影响代码逻辑，比如去掉了一些空格）
5. refactor： 重构
6. test： 增加了测试代码
7. chore： 配置文件等涉及系统构建时的修改
