# MyBatis-Plus Generator Web

[简体中文](README.md) | **English**

A browser-based code generator powered by MyBatis-Plus. Configure data sources, inspect tables, maintain generation strategies and Velocity templates, and download Java, XML, HTML, Vue, and JavaScript code.

Designed for individual developers and trusted internal teams. The application UI is primarily Chinese; bilingual documentation does not imply a fully localized interface.

## Features

- **Data source management**: add, test, and save database connections; inspect tables and columns.
- **Single-table and batch generation**: configure packages, authors, table prefixes, and naming patterns, then download `src.zip`.
- **Reusable configuration**: data sources, saved single-table strategies, and custom templates persist in a local H2 **file database** across restarts.
- **Custom template sets**: edit Velocity templates in the browser and select a set for each project.
- **Backend and frontend output**: Entity, Mapper, Mapper XML, Service, ServiceImpl, Controller, plus HTML, Vue, and JavaScript page templates.
- **Browser interface**: Thymeleaf, Vue, and Element UI, with frontend assets bundled in the application. No separate Node.js build is required.

## Requirements

| Component | Requirement or pinned version |
| --- | --- |
| Java | JDK 17 or newer |
| Maven | Repository Wrapper (3.9.11) recommended, or installed Maven 3.9.x |
| Spring Boot | 4.1.1 |
| MyBatis-Plus / Generator | 3.5.17 |
| Storage | Writable home and temporary directories for H2 data and generated files |
| Network | Maven dependency access for the first build; target database access at runtime |

See [pom.xml](pom.xml) for dependency versions. This branch no longer runs on Java 8.

### Database scope

- **MySQL**: JDBC driver and database-specific metadata queries are included.
- **Oracle**: adapter branches are retained. Add a JDBC driver compatible with your Java and Oracle versions, rebuild, and validate against a real database before use.
- **H2**: stores this tool's configuration. Its inclusion does not imply that the UI supports H2 as a generation target.

Upstream MyBatis-Plus database support does not automatically extend to this application's UI and metadata queries. Other databases require adaptation and tests.

## Quick start

### 1. Clone and build

```bash
git clone https://github.com/jackcooper2015/mybatisplus-generator-web.git
cd mybatisplus-generator-web
./mvnw clean verify
```

On Windows, use `mvnw.cmd clean verify`. With Maven installed, `mvn clean verify` also works. The Wrapper downloads Maven on first use. Build from current source: `doc/code-generator-2.0.0.jar` is a historical binary and does not include this upgrade.

### 2. Set a login password and start

Linux / macOS (Bash):

```bash
read -rsp 'Set an administrator password: ' GENERATOR_ADMIN_PASSWORD; echo
export GENERATOR_ADMIN_PASSWORD
java -jar target/code-generator-3.0.1.jar
```

Windows PowerShell 7:

```powershell
$env:GENERATOR_ADMIN_PASSWORD = Read-Host 'Set an administrator password' -MaskInput
java -jar target/code-generator-3.0.1.jar
```

Open <http://127.0.0.1:8088> and sign in as `admin` using the password you just set. The application refuses to start when the password is unset or blank; the historical `admin/admin` credentials are no longer an active default. Supply environment variables through your local or deployment platform's secure configuration and never commit secrets to Git.

### Docker (optional)

Complete the Maven build above, then run from the repository root:

```bash
docker build -t mybatisplus-generator-web:local .
docker run --rm --name mybatisplus-generator-web \
  -p 127.0.0.1:8088:8088 \
  -e GENERATOR_ADMIN_PASSWORD \
  -v mybatisplus-generator-data:/root/.h2 \
  mybatisplus-generator-web:local
```

This passes the administrator password already set in your shell and persists configuration in a named volume. The container listens on `0.0.0.0` internally, while the host port remains bound to loopback. Inside a container, `127.0.0.1` refers to the container itself; use a business database address reachable from the container.

## Configuration

Defaults are defined in [application.yml](src/main/resources/application.yml). These settings configure **the generator itself**. Add the business database you want to generate code for separately in the data source page after login.

| Environment variable | Default / purpose |
| --- | --- |
| `GENERATOR_ADMIN_USERNAME` | `admin`, web login username |
| `GENERATOR_ADMIN_PASSWORD` | No default; a nonblank value is required to start |
| `GENERATOR_SERVER_ADDRESS` | `127.0.0.1`, local access only by default |
| `GENERATOR_SERVER_PORT` | `8088` |
| `GENERATOR_DB_URL` | H2 file database, default path `~/.h2/mybatisplus-gen-v4` |
| `GENERATOR_DB_USERNAME` | `sa`, H2 username |
| `GENERATOR_DB_PASSWORD` | Empty by default; separate from the web login password; set one for deployments |
| `GENERATOR_GOD_MODE_ENABLED` | `false`; arbitrary Groovy execution, enable explicitly only in a trusted local environment |
| `GENERATOR_CHAT_ENABLED` | `false`; legacy WebSocket chat has no independent authentication, trusted local use only |

The H2 console is disabled by default. Read the security guidance below before changing the bind address. If you change the H2 URL, retain the compatibility options required by the default URL. Do not use your business database URL as `GENERATOR_DB_URL`.

### Add a business data source

Example MySQL configuration:

```text
URL:      jdbc:mysql://127.0.0.1:3306/example?serverTimezone=UTC
Driver:   com.mysql.cj.jdbc.Driver
Schema:   example
Username: a dedicated database account
Password: its password
```

Configure TLS, time zones, and permissions for your database rather than disabling security options to make a connection work. Click “测试” (Test), then save after the connection succeeds. For generation alone, use the least privileges needed to read metadata.

## Generation workflow

1. Sign in, add a data source, test its connection, and save it.
2. Click **Tables** for that data source.
3. **Single table**: open “查看表字段” (View columns), configure the package, author, table prefix, naming patterns, and template set. Save first, then select “生成代码” (Generate code).
4. **Batch**: select tables and click “批量生成代码” (Batch generate). Configure the shared strategy for this run and generate.
5. Download and extract `src.zip`, review the output, integrate it into your project, and run that project's build and tests.

The UI combines the `com.` prefix with your module name: `example.order` produces `com.example.order`. Class naming patterns can use `%s`, such as `%sEntity`, `%sMapper`, and `I%sService`. Use valid Java package and class names. For batch generation, every nonempty class-name pattern must contain exactly one `%s` to avoid output name collisions.

Saving a single-table configuration persists the generation strategy. Do not treat that action as a migration tool for changing business database schemas or comments.

### Download layout

Java files follow the selected package paths; Mapper XML files are included in the output. Frontend files use these conventions:

```text
src.zip
├── com/...                         # Java package paths
├── com/.../mapper/xml/*.xml         # Mapper XML (default)
└── resource/
    ├── templates/views/*.html
    ├── templates/views/*.vue
    └── static/js/*.js
```

`resource` is this generator's output convention, not Maven's standard `src/main/resources` directory. Move files to match your target project. The ZIP contains scaffolding, not a complete application, dependency manifest, or frontend project.

## Custom templates

Bundled templates live in [src/main/resources/templates/gen-template](src/main/resources/templates/gen-template). Create a template set in the template management page, edit its contents, and select it when generating code.

Recognized template filenames:

```text
entity.java.vm       mapper.java.vm       mapper.xml.vm
service.java.vm      serviceImpl.java.vm  controller.java.vm
page.html.vm         page.vue.vm          page.js.vm
```

Names select the output type; adding an arbitrary filename does not introduce a new output type. Changes to bundled resource templates require a rebuild. Template sets saved through the UI are stored in H2.

Templates use the MyBatis-Plus model, including `table`, `entity`, and `package`, plus this project's `cfg` configuration. Validate legacy templates individually after upgrading; do not assume the 3.2 and 3.5 template models are identical.

The bundled Controller no longer depends on a private framework. Nested `Response`, `PageParam`, and `PageResult` types retain the `code/msg/data` response shape. The output remains scaffolding: configure the target project's MyBatis-Plus pagination interceptor and adapt business rules. Vue templates still reference the target project's `@/utils` and `@/api`; adapt API paths and components. Out-of-the-box Vue 3 compatibility is not guaranteed.

## Upgrading and migrating existing data

This upgrade crosses Spring Boot, Jakarta API, MyBatis-Plus Generator, and H2 dependency generations. The default database uses the new path `~/.h2/mybatisplus-gen-v4` to avoid opening the legacy `~/.h2/mybatisplus-gen` database directly.

1. Stop the old application. Back up its H2 files, configuration, and custom templates, retaining an untouched original backup.
2. Open a copy using **the H2 version actually used by the old application**, and export SQL with H2's `SCRIPT` workflow. The export may contain data source passwords; protect it as sensitive data.
3. Use the new H2 version and a **new database path** in an isolated environment. Follow the [H2 migration documentation](https://www.h2database.com/html/migration-to-v2.html), review SQL, identifiers, and schema compatibility, then import. Do not blindly overwrite tables in an already initialized new database.
4. Verify data sources, strategies, templates, and their ID relationships. Generate code from test tables before switching configuration. Retain the old application and database for rollback.

The new entities map `db_config.schema/catalog` to `schema_name/catalog_name` and `template_set.desc` to `description`; IDs use identity columns and template contents use CLOB storage. Map column names, types, and ID relationships against the actual old schema before importing, and verify identity sequences. Do not overwrite the new schema with legacy CREATE statements. Connection credential examples have been removed from `sql/data.sql`, which is no longer executed automatically.

There is no one-click migration script. Recreating data sources and template sets in the new database is an alternative if you are unfamiliar with H2 export/import. Do not rename an old database file to the new path and start the application, or point configuration storage at your production business database.

## Security and limitations

- The application stores target database connection details, including passwords, and executes templates on the server. Limit access to trusted users and protect data directories, backups, and logs.
- Loopback binding and a disabled H2 console are baseline protections, not a complete security hardening. Do not expose the application directly to the internet. Team deployments should use private networking, HTTPS, and additional authentication and access controls.
- Treat template editing as code execution access. Do not run untrusted templates or allow untrusted users to submit arbitrary JDBC connections.
- Data source screens and error messages may contain sensitive information. Redact screenshots, logs, and issue reports before sharing them.
- Table name validation accepts a letter or underscore followed by letters, digits, underscores, or dollar signs. Names containing spaces, hyphens, or other characters are currently unsupported.
- Date/time columns retain the `OffsetDateTime` mapping. Check your JDBC driver, time zones, and target project serialization settings.
- Validate your database versions, column types, composite keys, and legacy templates in the target project. Automated tests do not replace integration validation against real MySQL / Oracle databases.
- Review generated code before replacing existing business code. Keep backups or compare changes through Git.

## Project structure

```text
src/main/java/com/reapal/
├── controller/        # Pages and generation endpoints
├── service/           # Application services
├── dao/               # Configuration persistence and database metadata
├── model/             # Data sources, table strategies, and templates
├── conf/              # Generator engine and application configuration
└── interceptor/       # Login checks and other interceptors
src/main/resources/
├── application.yml
├── templates/views/   # Application pages
├── templates/gen-template/  # Code generation templates
├── static/            # Frontend assets
└── sql/               # Database initialization resources
src/test/              # Automated tests
```

## Screenshots

These historical screenshots are retained from the original README's external image host to illustrate the workflow. Current layouts and settings may differ; image availability depends on the external service.

**Data source management**

![Data source management screen](https://upload-images.jianshu.io/upload_images/3710706-faa8e82a48d8d21f.png)

**Table and generation configuration**

![Table metadata and generation configuration screen](https://upload-images.jianshu.io/upload_images/3710706-3832aa826be420c6.png)

## Development and contributions

```bash
./mvnw test
./mvnw clean verify
```

This upgrade passed `mvn clean verify` with all 12 tests passing: generated Java recompilation, single/batch 9/18 artifacts, HTTP ZIP downloads, custom templates and fallback, concurrent isolation, invalid paths/missing tables/name collisions, login/JSON/HTML, new H2 configuration persistence, and multi-table strategy saves. The build used JDK 21 with Java 17 compilation. A packaged-JAR smoke test on Temurin 17.0.20.1 also verified login, JSON configuration creation, nine-file ZIP generation from packaged templates, and H2 file persistence after restart. Tests used isolated local fixtures only; live MySQL/Oracle, legacy H2 data migration, and Docker execution have not been verified.

For issues, include Java/database versions, a sanitized schema, the selected templates, and reproduction steps. For pull requests, describe behavior changes and add relevant tests. Template or dependency changes should cover bundled and custom templates, single-table and batch generation, and updates to both language versions of this README. Never commit database files, real credentials, or generated business code.

## License and author

Licensed under the [GNU AGPL v3](LICENSE).

- [GitHub · jackcooper2015](https://github.com/jackcooper2015)
- [Author's Jianshu](https://www.jianshu.com/u/8523e601fa4e)
