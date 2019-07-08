# Quartz任务执行

[TOC]

## quartz并发执行

多线程时，同一个Job在同一时刻执行，可能造成数据的重复修改问题，需要控制在同一时刻相同的Job只有一个在执行，需要将任务定义为有状态的Job

### quartz与spring结合

需要在定义JobDetail的bean中添加参数

```xml
<property name="concurrent" value="false" />
```

### quartz单独使用

需要在实现了Job接口的Job类中加@DisallowConcurrentExecution注解

```java
@DisallowConcurrentExecution
public class TestJob implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobEcecutionException {
        
    }
}
```

