# my-mybatis


手写Mybatis

核心步骤:

1.  加载xml或注解中的配置信息， 完成各模块的初始化工作

2.  获取sqlsession, 通过sqlsession获取动态代理对象

3.  讲查询结果通过反射的方式进行封装映射到返回结果中返回

