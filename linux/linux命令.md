磁盘、内存空间问题
===========================

[TOC]

## 查看磁盘占用

df -h|查看磁盘空间占用情况
du -s /* \| sort -nr|查看哪个目录占用空间大
du -h –max-depth=1|查看当前目录下文件夹大小情况
lsof \| grep deleted|是否删除掉的文件仍然被进程占用而没有进行实际删除。

## 环境变量设置

ubuntu中可设置环境变量的文件有
/etc/profile:操作系统为每个用户设置的环境信息，在用户第一次登陆时执行
/etc/environment:登录操作系统时调用的第二个文件，在读取自定义的环境文件时，设置环境文件的环境变量
~/.bash_profile:登录时使用的第三个文件，可以用来设置环境变量，同时调用用户的.bashrc文件
~/.bashrc:包含专业的bash shell信息，登录时以及每次打开新的shell时该文件被读取

## 查看Linux系统版本

1. uname -a
2. cat /proc/version
3. lsb_release -a
4. cat /etc/rehat-release
5. cat /ect/issue

## 查看文件(日志)

1. tail

   ```
    tail -f test.log
    tail -100 test.log -- 显示最后10行日志
    tail -n +10 test.log -- 查询10行之后的日志
   ```

2. head

   ```
    head -10 test,log -- 查询头10行
    head -n -10 test.log -- 查询日志文件除了最后10行的其他所有日志;
   ```

3. 搜索 grep

   ```
   grep '18016029383' sms-proxy-zt-info-2018-02-11-0.log 
   ```

4. cat

   (是tac的反写,tac是倒序查看)

   ```
   cat -n test.log | grep "关键字"   -- 显示行号
   cat -n tma.log | grep -C 5 '11:08:18.384' -- 查询匹配字段的上下5行 （注意C大写）
   cat -n tma.log | grep -B 5 '11:08:18.384' -- 查询匹配字段的前5行
   cat -n tma.log | grep -A 5 '11:08:18.384' -- 查询匹配字段的后5行
   ```

5. more

   command :

   ```
   more test.log
   ```

   operate :

   ```
   # 按空格 - 向后翻页
   # b - 向前翻页
   ```

6. less

   command:

   ```
   less test.log
   ```

   operate:

   ```
   #搜索
        / - 使用模式进行搜索，并定位到下一个匹配的文本
        ? - 使用模式进行搜索，并定位到前一个匹配的文本
        n - 向前匹配
        N - 向后匹配
   #全屏导航
        ctrl + F - 向前一屏
        ctrl + B - 向后一屏
        ctrl + D - 向前半屏
        ctrl + U - 向前半屏
   #单行导航
        j - 向前移动一行
        k - 向后移动一行
   #其他导航
        G - 移动到最后一行
        g - 移动到第一行
        q/ZZ - 退出
   ```

7. 行号查询

   ```
    cat -n test.log | grep "关键字"   -- 显示行号 拿到想要的行号
    cat -n test.log | tail -n +30 | head -n 20  
   -- 选择关键字所在的中间一行. 然后查看这个关键字前10行和后10行的日志:
      tail -n +30 表示查询30行之后的日志
      head -n 20  表示在前面的查询结果里再查前20条记录
   ```

8. 根据时间查询

   ```
   sed -n '/11:11:26/,/11:12:26/p' tma.log -查询两个时间点之间日志
   前提两个时间点必须在日志中必须有
   则：先用grep '11:11:26' test.log
   ```

9. more less命令查询

   more

   ```
    cat -n test.log |grep "debug" | more  - 分页打印了,通过点击空格键翻页
    cat -n test.log |grep "debug"  > debug.txt - 将其保存到文件中 
    sz debug.txt - 下拉文件
   ```

   less

   ```
   cat -n test.log |grep "debug" | less  - 参看第5条operation
   ```