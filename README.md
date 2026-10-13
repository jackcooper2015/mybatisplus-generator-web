# MyBatis-Plus Generator Web

**简体中文** | [English](README.en.md)

基于 MyBatis-Plus 的可视化代码生成工具：在浏览器中配置数据源、查看表结构、维护生成策略与 Velocity 模板，并下载 Java、XML、HTML、Vue 和 JavaScript 代码。

适合个人开发与可信团队的内部使用。界面目前主要为中文；双语 README 不代表界面已完成国际化。

## 功能

- **数据源管理**：添加、测试和保存数据库连接，浏览表与字段。
- **单表与批量生成**：配置包名、作者、表前缀和各层命名规则，下载 `src.zip`。
- **可复用配置**：将数据源、单表生成策略和自定义模板保存在本地 H2 **文件数据库**，重启后保留。
- **自定义模板组**：在线编辑 Velocity 模板，按项目选择模板组。
- **前后端代码**：Entity、Mapper、Mapper XML、Service、ServiceImpl、Controller，以及 HTML、Vue、JavaScript 页面模板。
- **浏览器界面**：使用 Thymeleaf、Vue 和 Element UI，前端资源随应用提供，无需单独运行 Node.js 构建。

## 环境要求

| 组件 | 要求或当前版本 |
| --- | --- |
| Java | JDK 17 或更高版本 |
| Maven | 推荐使用仓库 Wrapper（3.9.11），或安装 Maven 3.9.x |
| Spring Boot | 4.1.1 |
| MyBatis-Plus / Generator | 3.5.17 |
| 存储 | 可写的用户目录和临时目录，用于 H2 数据及生成文件 |
| 网络 | 首次构建需下载 Maven 依赖；运行时需能访问目标数据库 |

具体依赖以 [pom.xml](pom.xml) 为准。Java 8 不再适用于本分支。

### 数据库范围

- **MySQL**：项目内置 JDBC 驱动与专用元数据查询逻辑。
- **Oracle**：保留适配分支；需自行引入与 Java、Oracle 版本匹配的 JDBC 驱动并重新打包，在真实数据库验证后使用。
- **H2**：用于保存工具自身的配置，不代表页面已支持将 H2 作为生成目标。

MyBatis-Plus 上游支持的数据库，不等于本工具界面和元数据查询均已支持。其他数据库需要补充适配和测试。

## 快速开始

### 1. 获取并构建

```bash
git clone https://github.com/jackcooper2015/mybatisplus-generator-web.git
cd mybatisplus-generator-web
./mvnw clean verify
```

Windows 使用 `mvnw.cmd clean verify`；已安装 Maven 时也可以运行 `mvn clean verify`。首次使用 Wrapper 会下载 Maven。请从当前源码构建；`doc/code-generator-2.0.0.jar` 是历史版本，不包含本次升级。

### 2. 配置登录密码并启动

Linux / macOS（Bash）：

```bash
read -rsp '设置管理员密码: ' GENERATOR_ADMIN_PASSWORD; echo
export GENERATOR_ADMIN_PASSWORD
java -jar target/code-generator-3.0.1.jar
```

Windows PowerShell 7：

```powershell
$env:GENERATOR_ADMIN_PASSWORD = Read-Host '设置管理员密码' -MaskInput
java -jar target/code-generator-3.0.1.jar
```

打开 <http://127.0.0.1:8088>，使用用户名 `admin` 和刚设置的密码登录。未设置密码或密码为空时应用拒绝启动，旧版 `admin/admin` 不再是可用的默认凭据。环境变量应通过本机或部署平台的安全配置提供，不要提交到 Git。

### Docker（可选）

先完成上述 Maven 构建，再在仓库根目录运行：

```bash
docker build -t mybatisplus-generator-web:local .
docker run --rm --name mybatisplus-generator-web \
  -p 127.0.0.1:8088:8088 \
  -e GENERATOR_ADMIN_PASSWORD \
  -v mybatisplus-generator-data:/root/.h2 \
  mybatisplus-generator-web:local
```

示例从当前 shell 传入已设置的管理员密码，并用命名卷保存配置。容器内部监听 `0.0.0.0`，宿主机端口仍仅绑定本机。容器中的 `127.0.0.1` 指向容器自身，业务数据源地址需能从容器访问。

## 配置

默认配置见 [application.yml](src/main/resources/application.yml)。以下是**工具自身**的配置；需要生成代码的业务数据库应在登录后的数据源页面单独添加。

| 环境变量 | 默认值 / 用途 |
| --- | --- |
| `GENERATOR_ADMIN_USERNAME` | `admin`，网页登录用户名 |
| `GENERATOR_ADMIN_PASSWORD` | 无默认密码，必须设置非空值才能启动 |
| `GENERATOR_SERVER_ADDRESS` | `127.0.0.1`，默认仅本机访问 |
| `GENERATOR_SERVER_PORT` | `8088` |
| `GENERATOR_DB_URL` | H2 文件数据库，默认路径 `~/.h2/mybatisplus-gen-v4` |
| `GENERATOR_DB_USERNAME` | `sa`，H2 用户名 |
| `GENERATOR_DB_PASSWORD` | 默认空；与网页登录密码不同，部署时建议设置 |
| `GENERATOR_GOD_MODE_ENABLED` | `false`；仅在可信本地环境显式启用任意 Groovy 脚本执行 |
| `GENERATOR_CHAT_ENABLED` | `false`；旧 WebSocket 聊天没有独立鉴权，仅限可信本地环境 |

默认 H2 控制台禁用。改变服务监听地址前，请先阅读下文的安全部署要求。修改 H2 连接地址时应保留默认 URL 所需的兼容选项；不要将业务数据库 URL 填入 `GENERATOR_DB_URL`。

### 添加业务数据源

MySQL 示例：

```text
URL:      jdbc:mysql://127.0.0.1:3306/example?serverTimezone=UTC
Driver:   com.mysql.cj.jdbc.Driver
Schema:   example
Username: 专用数据库账号
Password: 对应密码
```

根据实际数据库配置 TLS、时区与账号权限，不要为了连接成功随意关闭安全选项。先点击“测试”，成功后保存。仅生成代码时优先使用满足元数据读取需求的最小权限账号。

## 生成流程

1. 登录后添加数据源，测试连接并保存。
2. 点击数据源的 **Tables**，查看表列表。
3. **单表**：进入“查看表字段”，填写包名、作者、表前缀、各层命名和模板组；先保存，再点击“生成代码”。
4. **批量**：在表列表勾选多个表，点击“批量生成代码”，填写本次共用策略并生成。
5. 下载并解压 `src.zip`，审查生成结果，再合入目标项目并运行该项目的构建与测试。

包名前缀由界面的 `com.` 与填写的模块名组合，例如填写 `example.order` 得到 `com.example.order`。类名模式可使用 `%s`，例如 `%sEntity`、`%sMapper`、`I%sService`。请使用合法的 Java 包名和类名；批量生成的非空类名模式必须包含且只包含一个 `%s`，避免输出重名。

单表“保存”用于持久化生成策略，不应把它当作修改业务数据库表结构或注释的迁移工具。

### 下载内容

Java 文件按所选包路径生成，Mapper XML 随生成结果输出；前端文件沿用以下目录约定：

```text
src.zip
├── com/...                         # Java 包路径
├── com/.../mapper/xml/*.xml         # Mapper XML（默认）
└── resource/
    ├── templates/views/*.html
    ├── templates/views/*.vue
    └── static/js/*.js
```

`resource` 为本工具的输出约定，并非标准 Maven 的 `src/main/resources`。按目标项目结构移动文件。生成结果是脚手架片段，不包含完整应用、依赖清单或前端工程；不能直接将 ZIP 当作可运行项目。

## 自定义模板

内置模板位于 [src/main/resources/templates/gen-template](src/main/resources/templates/gen-template)。可在模板管理页创建模板组、编辑内容，并在生成时选择该组。

支持的模板文件名：

```text
entity.java.vm       mapper.java.vm       mapper.xml.vm
service.java.vm      serviceImpl.java.vm  controller.java.vm
page.html.vm         page.vue.vm          page.js.vm
```

模板名用于匹配输出类型；不要把任意新文件名视为新增输出类型。修改内置资源模板后需重新构建；页面保存的模板组则存储在 H2 中。

模板使用 MyBatis-Plus 的模型（如 `table`、`entity`、`package`）及项目的 `cfg` 配置。升级后请逐一验证旧模板，不要假定 3.2 的模板模型与 3.5 完全兼容。

内置 Controller 已移除私有框架依赖，使用内嵌的 `Response`、`PageParam` 和 `PageResult`，保留 `code/msg/data` 响应结构。生成结果仍是脚手架：请配置目标项目的 MyBatis-Plus 分页拦截器并适配业务规则。Vue 模板仍引用目标项目的 `@/utils` 和 `@/api`；请适配接口与组件，不承诺 Vue 3 开箱即用兼容性。

## 旧版本升级与数据迁移

本次升级跨越 Spring Boot、Jakarta API、MyBatis-Plus Generator 与 H2 依赖。默认使用新的 `~/.h2/mybatisplus-gen-v4` 文件数据库，避免直接打开旧版 `~/.h2/mybatisplus-gen`。

1. 停止旧应用，备份旧 H2 文件、旧配置和自定义模板。保存一份只读原始备份。
2. 用**旧应用实际使用的 H2 版本**打开旧库副本，按 H2 的 `SCRIPT` 导出流程导出 SQL；导出文件可能含数据源密码，应按敏感文件保管。
3. 在隔离环境使用新 H2 版本和**新的数据库路径**，参考 [H2 升级文档](https://www.h2database.com/html/migration-to-v2.html)，检查 SQL、标识符和表结构兼容性后导入。不要直接覆盖已初始化的新库表。
4. 校验数据源、策略、模板和 ID 关联，再对测试表生成代码。只有验证通过后才切换配置；失败时保留旧版本与旧库回退。

新版实体将 `db_config.schema/catalog` 映射为 `schema_name/catalog_name`，将 `template_set.desc` 映射为 `description`；ID 使用 identity，自定义模板内容使用 CLOB。导入前必须按实际旧表结构映射列名、类型与 ID 关系，并校验自增序列；不要将导出的旧建表语句直接覆盖新版结构。旧 `sql/data.sql` 中的连接凭据样例已移除，应用不再自动执行它。

项目没有一键迁移脚本。不熟悉 H2 导出/导入时，可在新库中手动重建数据源与模板。切勿把旧数据库文件重命名为新路径后直接启动，也不要将新版应用直接指向生产业务库作为配置存储。

## 安全部署与限制

- 工具会保存目标数据库连接信息（包括密码），模板也会在服务端执行。仅向可信用户开放，限制数据目录、备份和日志的访问权限。
- 默认本机监听和禁用 H2 控制台只是基础防护，不等于完整安全加固。不要直接暴露到互联网；团队共享应放在私有网络，并使用 HTTPS、额外的认证与访问控制。
- 模板编辑权限应视同代码执行权限。不要运行来源不明的模板，也不要开放任意 JDBC 连接给不可信用户。
- 数据源页面及错误信息可能包含敏感信息，分享截图、日志或问题报告前先脱敏。
- 表名校验仅接受字母或下划线开头、后接字母/数字/下划线/美元符号的名称；包含空格、连字符或其他字符的表名目前不支持。
- 日期/时间字段沿用 `OffsetDateTime` 映射，请核对 JDBC 驱动、时区和目标项目的序列化配置。
- 不同数据库版本、字段类型、复合主键和旧模板需在目标项目验证。自动化测试不替代真实 MySQL / Oracle 环境的集成验证。
- 不要将生成结果未经审查直接覆盖已有业务代码。先保留备份或通过 Git 比较变更。

## 项目结构

```text
src/main/java/com/reapal/
├── controller/        # 页面和生成接口
├── service/           # 业务服务
├── dao/               # 配置持久化及数据库元数据访问
├── model/             # 数据源、表策略和模板模型
├── conf/              # 生成引擎与应用配置
└── interceptor/       # 登录检查等
src/main/resources/
├── application.yml
├── templates/views/   # 工具页面
├── templates/gen-template/  # 代码生成模板
├── static/            # 前端静态资源
└── sql/               # 数据库初始化资源
src/test/              # 自动化测试
```

## 界面预览

以下为历史界面截图，来自原 README 的外部图床；用于说明工作流程，布局和配置以当前版本为准。图片加载依赖外部服务。

**数据源列表**

![数据源管理界面](https://upload-images.jianshu.io/upload_images/3710706-faa8e82a48d8d21f.png)

**表与生成配置**

![表结构与生成配置界面](https://upload-images.jianshu.io/upload_images/3710706-3832aa826be420c6.png)

## 开发与贡献

```bash
./mvnw test
./mvnw clean verify
```

本次升级已通过 `mvn clean verify`：12 项测试全部通过，包括生成 Java 再编译、单表/批量 9/18 文件、HTTP ZIP 下载、自定义模板与回退、并发隔离、无效路径/缺表/命名冲突、登录/JSON/页面、新 H2 配置存储及多表策略保存。构建使用 JDK 21，按 Java 17 编译。另以 Temurin 17.0.20.1 实际启动打包 JAR，验证登录、JSON 保存配置、JAR 内模板生成 9 文件 ZIP，以及重启后的 H2 文件持久化。测试仅使用隔离的本地测试库；真实 MySQL/Oracle、旧 H2 数据迁移和 Docker 运行尚未验证。

提交问题时请提供 Java/数据库版本、脱敏的表结构、所用模板和复现步骤。提交 PR 时说明行为变化、补充相应测试；涉及模板或依赖升级时，同时验证默认模板、自定义模板、单表和批量生成，并同步中英文说明。请勿提交数据库文件、真实凭据或生成的业务代码。

## 许可证与作者

本项目采用 [GNU AGPL v3](LICENSE)。

- [GitHub · jackcooper2015](https://github.com/jackcooper2015)
- [作者的简书](https://www.jianshu.com/u/8523e601fa4e)
