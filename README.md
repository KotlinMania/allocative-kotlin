# allocative-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fallocative--kotlin-blue.svg)](https://github.com/KotlinMania/allocative-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/allocative-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/allocative-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/allocative-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/allocative-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`facebookexperimental/allocative`](https://github.com/facebookexperimental/allocative).

**Original Project:** This port is based on [`facebookexperimental/allocative`](https://github.com/facebookexperimental/allocative). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `facebookexperimental/allocative`

> The text below is reproduced and lightly edited from [`https://github.com/facebookexperimental/allocative`](https://github.com/facebookexperimental/allocative). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## Allocative: memory profiler for Rust

This crate implements a lightweight memory profiler which allows object
traversal and memory size introspection.

## Usage

`Allocative` trait (typically implemented with proc-macro) is introspectable:
`Allocative` values can be traversed and their size and sizes of referenced
objects can be collected.

This crate provides a few utilities to work with such objects, the main of such
utilities is flame graph builder which produces flame graph (see the crate
documentation) like this:

![sample-flamegraph.png](https://raw.githubusercontent.com/facebookexperimental/allocative/HEAD/sample-flamegraph.png)

## How it is different from other call-stack malloc profilers like jemalloc heap profiler

Allocative is not a substitute for call stack malloc profiler, it provides a
different view of memory usage.

Here are some differences between allocative and call-stack malloc profiler:

- Allocative requires implementation of `Allocative` trait for each type which
  needs to be measured, and some setup in the program to enable it is needed
- Allocative flamegraph shows object by object tree, not by call stack
- Allocative shows gaps in allocated memory, e.g. spare capacity of collections
  or too large padding in structs or enums
- Allocative allows profiling of non-malloc allocations (for example,
  allocations within [bumpalo](https://github.com/fitzgen/bumpalo) bumps)
- Allocative allows profiling of memory for subset of the process data (for
  example, measure the size of RPC response before serialization)

## Runtime overhead

When allocative is used, binary size is slightly increased due to
implementations of [`Allocative`] trait, but it has no runtime/memory overhead
when it is enabled but not used.

## Source code

Note there are several copies of this project on GitHub due to how Meta monorepo
is synchronized to GitHub. The main copy is
[facebookexperimental/allocative](https://github.com/facebookexperimental/allocative).

## License

Allocative is both MIT and Apache License, Version 2.0 licensed, as found in the
[LICENSE-MIT](https://github.com/facebookexperimental/allocative/blob/HEAD/LICENSE-MIT) and [LICENSE-APACHE](https://github.com/facebookexperimental/allocative/blob/HEAD/LICENSE-APACHE) files.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:allocative-kotlin:0.1.0")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`facebookexperimental/allocative`](https://github.com/facebookexperimental/allocative). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the allocative authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`facebookexperimental/allocative`](https://github.com/facebookexperimental/allocative) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
