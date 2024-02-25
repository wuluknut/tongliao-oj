<h1 align="center">Tongliao OJ</h1>

<p align="center">
  <a href="https://adoptium.net">
    <img src="https://img.shields.io/badge/OpenJDK-8+-green.svg?style=flat-square" alt="openjdk 8+" />
  </a>
  <a href="https://start.spring.io/">
    <img src="https://img.shields.io/badge/SpringBoot-2.x-green.svg?style=flat-square" alt="spring boot 2.x" />
  </a>
  <a href="https://github.com/wuluknut/tongliao-oj">
    <img src="https://img.shields.io/github/license/wuluknut/tongliao-oj?label=License&style=flat-square" alt="license" />
  </a>
  <a href="https://github.com/wuluknut/tongliao-oj">
    <img src="https://img.shields.io/github/v/tag/wuluknut/tongliao-oj?label=Version&style=flat-square" alt="version" />
  </a>
</p>

<p align="center">
  <a href="https://github.com/wuluknut/tongliao-oj">
    <img src="https://img.shields.io/github/watchers/wuluknut/tongliao-oj?style=social" alt="watchers" />
  </a>
  <a href="https://github.com/wuluknut/tongliao-oj">
    <img src="https://img.shields.io/github/stars/wuluknut/tongliao-oj?style=social" alt="stars" />
  </a>
  <a href="https://github.com/wuluknut/tongliao-oj">
    <img src="https://img.shields.io/github/forks/wuluknut/tongliao-oj?style=social" alt="forks" />
  </a>
</p>

<p align="center">
  <strong>基于 Java 的高校内网 Online Judge 系统设计与实现</strong>
</p>

## 项目简介

个人毕业设计项目，用于校内的练习测试平台。

## 使用方法

- 项目已进行 Docker 镜像构建，请使用项目内提供的 [compose.yaml](compose/compose.yaml) 文件进行生产部署。
    - 在部署过程中，请注意将代码执行系统 [judge0.conf](compose/judge0.conf) 配置文件放置在 `compose.yaml` 中所映射的位置。
        - 默认映射位置为 `compose.yaml` 文件同一路径。
        - 代码执行系统 `judge0.conf` 配置文件已经进行精简，完整配置请前往相关链接查看。

## 常见问题

- Judge0 Worker 报错 `No such file or directory @ rb_sysopen - /box/script.py`
    - 解决方法：https://github.com/judge0/judge0/issues/325#issuecomment-1429381789

## 相关链接

- 代码执行系统：[Judge0 CE](https://github.com/judge0/judge0)

- 后台管理框架：[Erupt Framework](https://github.com/erupts/erupt)

## 许可证

[AGPLv3](LICENSE) © Wulu Knut
