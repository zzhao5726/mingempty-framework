# mingempty-framework 项目介绍

## 1. 项目概述
`mingempty-framework` 是一个基于 Spring Cloud 和 Spring Boot 的微服务框架，旨在为企业级应用提供高效、稳定的基础架构支持。该项目集成了多种中间件和技术栈，涵盖了缓存、数据源管理、分布式锁、链路追踪、API 文档生成等功能模块，适用于构建复杂的分布式系统。

## 2. 主要功能模块

- **缓存模块 (`mingempty-cache`)**
  提供 Redis 和本地缓存的集成与操作接口，支持多种缓存类型（如字符串、集合、有序集合等），并封装了常用的缓存操作。

- **数据源模块 (`mingempty-datasource-spring-boot3-starter`)**
  支持多种数据库连接池（如 Druid、HikariCP 等）和分库分表（ShardingSphere），具备动态数据源切换和事务管理功能。

- **Mybatis模块 (`mingempty-mybatis-spring-boot3-starter`)**
  集成mybatis，提供逆向生成、审计字段字段注入、表名称替换功能

- **Mybatis模块 (`mingempty-mybatis-plus-spring-boot3-starter`)**
  集成mybatis-plus，提供逆向生成、审计字段字段注入、表名称替换功能，优化批量查询逻辑

- **Mybatis模块 (`mingempty-mybatis-flex-spring-boot3-starter`)**
  集成mybatis-flex，提供逆向生成、审计字段字段注入、表名称替换功能

- **分布式锁模块 (`mingempty-distributed-lock-spring-boot3-starter`)**
  基于 Redis 和 Zookeeper 实现分布式锁，确保在分布式环境中对共享资源的安全访问。

- **链路追踪模块 (`mingempty-trace`)**
  集成 Micrometer Tracing 和自定义链路追踪工具，帮助开发者监控和调试分布式系统的调用链路。

- **API 文档模块 (`mingempty-openapi-spring-boot3-starter`)**
  自动生成 Swagger API 文档，支持多语言和多环境配置，方便前后端联调。

- **事件模块 (`mingempty-event-spring-boot3-starter`)**
  提供事件发布/订阅机制，支持异步处理和分布式事件传播。

- **网关模块 (`mingempty-gateway`)**
  基于 Spring Cloud Gateway 实现的微服务网关，负责路由、限流、鉴权等功能。

- **序列号生成模块 (`mingempty-sequence-spring-boot3-starter`)**
  提供全局唯一 ID 生成器，支持基于数据库、Redis 和 Zookeeper 的实现方式。

- **服务发现模块 (`mingempty-discovery`)**
  集成 Nacos ，实现服务注册与发现功能。

## 3. 技术栈

- **编程语言**: Java 21
- **框架**: Spring Boot 3, Spring Cloud 2023
- **依赖管理**: Maven
- **缓存**: Redis, Caffeine
- **数据库**: MySQL, PostgreSQL 等
- **服务发现**: Nacos
- **链路追踪**: Tracing
- **API 文档**: Swagger, OpenAPI 3.0
- **Mybatis**: Mybatis, Mybatis Plus, Mybatis Flex

## 4. 使用场景

`mingempty-framework` 适用于需要构建高性能、高可用、易扩展的微服务架构的企业级应用，特别适合以下场景：

- 复杂的业务逻辑需要多个微服务协同工作。
- 需要高效的缓存机制来提升系统性能。
- 需要可靠的分布式锁来保证数据一致性。
- 需要完善的链路追踪工具来监控和调试分布式系统。
- 需要自动化的 API 文档生成工具来简化开发流程。

## 5. 安装与使用

```
# 下载并运行服务端代码
git clone https://gitee.com/mingempty/mingempty-framework.git

cd mingempty-framework && mvn clean install
```

在pom.xml内添加依赖
```
  <parent>
      <groupId>top.mingempty</groupId>
      <artifactId>spring-cloud-mingempty-dependencies</artifactId>
      <version>3.3.0_1.0.0</version>
  </parent>
```
## 6. 开源协议

`mingempty-framework` 采用 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) 许可证。这意味着您可以自由地使用、修改和分发本项目，但需遵守以下条款：

- 您必须保留原作者的版权声明和许可声明。
- 如果您修改了代码，必须在修改后的副本中注明您的修改。
- 如果您分发修改后的代码，必须提供完整的源代码。
- 您不能使用原作者的名称、商标或其他标识进行推广或宣传，除非获得明确授权。

有关 Apache License 2.0 的详细信息，请参阅 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)。

## 7. 致谢

在此特别感谢 [JetBrains](https://www.jetbrains.com/) 对开源社区的支持。JetBrains 提供了强大的开发工具，极大地提升了我们的开发效率和代码质量。感谢 JetBrains 对我们项目的持续支持！

---