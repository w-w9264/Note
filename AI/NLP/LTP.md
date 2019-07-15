# LTP: Language Technology Platform

# （语言技术平台）

语言技术平台（Language Technology Platform，LTP）是[哈工大社会计算与信息检索研究中心](http://ir.hit.edu.cn/)历时十年开发的一整套中文语言处理系统。LTP制定了基于XML的语言处理结果表示，并在此基础上提供了一整套自底向上的丰富而且高效的中文语言处理模块（包括词法、句法、语义等6项中文处理核心技术），以及基于动态链接库（Dynamic Link Library, DLL）的应用程序接口、可视化工具，并且能够以网络服务（Web Service）的形式进行使用。

[在线演示](http://ltp.ai/demo.html)

在分词、句法分析、语义依存分析方面有比较好的结果，但是在命名实体识别方面只通过在线演示的体验没看到效果。。

该项目使用的是c++编写，提供docker镜像进行部署使用，可通过webservice的方式进行使用，该项目提供了ltp4j方便java调用ltp但是底层还是c++，故调用时需添加c++的代理