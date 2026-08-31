# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 19/51 (37.3%)
- **Function parity:** 82/193 matched (target 177) — 42.5%
- **Class/type parity:** 16/53 matched (target 71) — 30.2%
- **Combined symbol parity:** 98/246 matched (target 248) — 39.8%
- **Average inline-code cosine:** 0.47 (function body across 19 matched files)
- **Average documentation cosine:** 0.34 (doc text across 19 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 12 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. visitor
- **Similarity:** 0.68 (needs 17% improvement)
- **Dependencies:** 37
- **Priority Score:** 37012004.0
- **Functions:** 16/17 matched (target 16)
- **Missing functions:** `drop`
- **Types:** 3/3 matched
- **Missing types:** _none_
- **Symbol Deficit:** 1 (functions: 1, types: 0)
- **Action:** Review and complete missing sections

### 2. key
- **Similarity:** 0.25 (needs 60% improvement)
- **Dependencies:** 19
- **Priority Score:** 19061008.0
- **Functions:** 3/7 matched (target 9)
- **Missing functions:** `partial_cmp`, `cmp`, `hash`, `deref`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `Target`, `AllocativeKeyForType`
- **Symbol Deficit:** 6 (functions: 4, types: 2)
- **Action:** Deep review - likely missing major functionality

### 3. std.mem
- **Similarity:** 0.70 (needs 15% improvement)
- **Dependencies:** 17
- **Priority Score:** 17000104.0
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Action:** Review and complete missing sections

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. visitor

- **Target:** `allocative.Visitor [PROVENANCE-FALLBACK]`
- **Similarity:** 0.68
- **Dependents:** 37
- **Priority Score:** 37012004.0
- **Functions:** 16/17 matched (target 16)
- **Missing functions:** `drop`
- **Types:** 3/3 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/visitor.rs` vs expected `visitor.rs`
- **Proposed provenance header:** `// port-lint: source visitor.rs` (current: `// port-lint: source allocative/src/visitor.rs`)
- **Lint issues:** 1

### 2. key

- **Target:** `key.Key [PROVENANCE-FALLBACK]`
- **Similarity:** 0.25
- **Dependents:** 19
- **Priority Score:** 19061008.0
- **Functions:** 3/7 matched (target 9)
- **Missing functions:** `partial_cmp`, `cmp`, `hash`, `deref`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `Target`, `AllocativeKeyForType`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/key.rs` vs expected `key.rs`
- **Proposed provenance header:** `// port-lint: source key.rs` (current: `// port-lint: source allocative/src/key.rs`)
- **Lint issues:** 1

### 3. std.mem

- **Target:** `std.Mem [PROVENANCE-FALLBACK]`
- **Similarity:** 0.70
- **Dependents:** 17
- **Priority Score:** 17000104.0
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/std/mem.rs` vs expected `impls/std/mem.rs`
- **Proposed provenance header:** `// port-lint: source impls/std/mem.rs` (current: `// port-lint: source allocative/src/impls/std/mem.rs`)
- **Lint issues:** 1

### 4. flamegraph

- **Target:** `allocative.FlameGraph [PROVENANCE-FALLBACK]`
- **Similarity:** 0.62
- **Dependents:** 1
- **Priority Score:** 1155503.8
- **Functions:** 33/43 matched (target 37)
- **Missing functions:** `flamegraph`, `warnings`, `as_ref`, `index`, `index_mut`, `current_data`, `_assert_flame_graph_builder_is_send`, `assert_send`, `default`, `current`
- **Types:** 7/12 matched (target 9)
- **Missing types:** `TreeId`, `Trees`, `Output`, `TreeStackRef`, `VisitedSharedPointer`
- **Tests:** 6/6 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/flamegraph.rs` vs expected `flamegraph.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/flamegraph.rs` vs expected `flamegraph.rs`
- **Proposed provenance header:** `// port-lint: source flamegraph.rs` (current: `// port-lint: source allocative/src/flamegraph.rs`)
- **Proposed provenance header:** `// port-lint: tests flamegraph.rs` (current: `// port-lint: tests allocative/src/flamegraph.rs`)
- **Lint issues:** 2

### 5. impls.hashbrown_util

- **Target:** `impls.HashbrownUtil [PROVENANCE-FALLBACK]`
- **Similarity:** 0.82
- **Dependents:** 1
- **Priority Score:** 1000101.8
- **Functions:** 1/1 matched (target 3)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/hashbrown_util.rs` vs expected `impls/hashbrown_util.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/impls/hashbrown_util.rs` vs expected `impls/hashbrown_util.rs`
- **Proposed provenance header:** `// port-lint: source impls/hashbrown_util.rs` (current: `// port-lint: source allocative/src/impls/hashbrown_util.rs`)
- **Proposed provenance header:** `// port-lint: tests impls/hashbrown_util.rs` (current: `// port-lint: tests allocative/src/impls/hashbrown_util.rs`)
- **Lint issues:** 2

### 6. rc_str

- **Target:** `allocative.RcStr [PROVENANCE-FALLBACK]`
- **Similarity:** 0.28
- **Dependents:** 0
- **Priority Score:** 40807.2
- **Functions:** 3/6 matched (target 10)
- **Missing functions:** `deref`, `borrow`, `hash`
- **Types:** 1/2 matched
- **Missing types:** `Target`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/rc_str.rs` vs expected `rc_str.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/rc_str.rs` vs expected `rc_str.rs`
- **Proposed provenance header:** `// port-lint: source rc_str.rs` (current: `// port-lint: source allocative/src/rc_str.rs`)
- **Proposed provenance header:** `// port-lint: tests rc_str.rs` (current: `// port-lint: tests allocative/src/rc_str.rs`)
- **Lint issues:** 2

### 7. std.sync

- **Target:** `std.Sync [PROVENANCE-FALLBACK]`
- **Similarity:** 0.15
- **Dependents:** 0
- **Priority Score:** 40508.5
- **Functions:** 1/3 matched (target 4)
- **Missing functions:** `layout`, `test_arc_align`
- **Types:** 0/2 matched (target 4)
- **Missing types:** `RcBox`, `CacheLine`
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/std/sync.rs` vs expected `impls/std/sync.rs`
- **Proposed provenance header:** `// port-lint: source impls/std/sync.rs` (current: `// port-lint: source allocative/src/impls/std/sync.rs`)
- **Lint issues:** 1

### 8. std.cell

- **Target:** `std.Cell [PROVENANCE-FALLBACK]`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 30407.5
- **Functions:** 1/4 matched (target 6)
- **Missing functions:** `test_default`, `test_borrowed`, `test_once_cell`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Tests:** 0/3 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/std/cell.rs` vs expected `impls/std/cell.rs`
- **Proposed provenance header:** `// port-lint: source impls/std/cell.rs` (current: `// port-lint: source allocative/src/impls/std/cell.rs`)
- **Lint issues:** 1

### 9. size_of

- **Target:** `allocative.SizeOf [PROVENANCE-FALLBACK]`
- **Similarity:** 0.67
- **Dependents:** 0
- **Priority Score:** 11503.3
- **Functions:** 12/12 matched (target 16)
- **Missing functions:** _none_
- **Types:** 2/3 matched (target 6)
- **Missing types:** `Boxed`
- **Tests:** 3/3 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/size_of.rs` vs expected `size_of.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/size_of.rs` vs expected `size_of.rs`
- **Proposed provenance header:** `// port-lint: source size_of.rs` (current: `// port-lint: source allocative/src/size_of.rs`)
- **Proposed provenance header:** `// port-lint: tests size_of.rs` (current: `// port-lint: tests allocative/src/size_of.rs`)
- **Lint issues:** 2

### 10. impls.either

- **Target:** `impls.Either [PROVENANCE-FALLBACK]`
- **Similarity:** 0.32
- **Dependents:** 0
- **Priority Score:** 10206.8
- **Functions:** 1/2 matched (target 3)
- **Missing functions:** `test_golden`
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_
- **Tests:** 0/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/either.rs` vs expected `impls/either.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/impls/either.rs` vs expected `impls/either.rs`
- **Proposed provenance header:** `// port-lint: source impls/either.rs` (current: `// port-lint: source allocative/src/impls/either.rs`)
- **Proposed provenance header:** `// port-lint: tests impls/either.rs` (current: `// port-lint: tests allocative/src/impls/either.rs`)
- **Lint issues:** 2

### 11. global_root

- **Target:** `allocative.GlobalRoot [PROVENANCE-FALLBACK]`
- **Similarity:** 0.55
- **Dependents:** 0
- **Priority Score:** 404.5
- **Functions:** 3/3 matched (target 4)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/global_root.rs` vs expected `global_root.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/global_root.rs` vs expected `global_root.rs`
- **Proposed provenance header:** `// port-lint: source global_root.rs` (current: `// port-lint: source allocative/src/global_root.rs`)
- **Proposed provenance header:** `// port-lint: tests global_root.rs` (current: `// port-lint: tests allocative/src/global_root.rs`)
- **Lint issues:** 2

### 12. impls.indexmap

- **Target:** `impls.Indexmap [PROVENANCE-FALLBACK]`
- **Similarity:** 0.54
- **Dependents:** 0
- **Priority Score:** 204.6
- **Functions:** 2/2 matched (target 14)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/indexmap.rs` vs expected `impls/indexmap.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/impls/indexmap.rs` vs expected `impls/indexmap.rs`
- **Proposed provenance header:** `// port-lint: source impls/indexmap.rs` (current: `// port-lint: source allocative/src/impls/indexmap.rs`)
- **Proposed provenance header:** `// port-lint: tests impls/indexmap.rs` (current: `// port-lint: tests allocative/src/impls/indexmap.rs`)
- **Lint issues:** 2

### 13. allocative_trait

- **Target:** `allocative.AllocativeTrait [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 110.0
- **Functions:** 0/0 matched (target 2)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/allocative_trait.rs` vs expected `allocative_trait.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/allocative_trait.rs` vs expected `allocative_trait.rs`
- **Proposed provenance header:** `// port-lint: source allocative_trait.rs` (current: `// port-lint: source allocative/src/allocative_trait.rs`)
- **Proposed provenance header:** `// port-lint: tests allocative_trait.rs` (current: `// port-lint: tests allocative/src/allocative_trait.rs`)
- **Lint issues:** 2

### 14. std.primitive

- **Target:** `std.Primitive [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 110.0
- **Functions:** 1/1 matched (target 31)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 13)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/std/primitive.rs` vs expected `impls/std/primitive.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:allocative/src/impls/std/primitive.rs` vs expected `impls/std/primitive.rs`
- **Proposed provenance header:** `// port-lint: source impls/std/primitive.rs` (current: `// port-lint: source allocative/src/impls/std/primitive.rs`)
- **Proposed provenance header:** `// port-lint: tests impls/std/primitive.rs` (current: `// port-lint: tests allocative/src/impls/std/primitive.rs`)
- **Lint issues:** 2

### 15. std.tuple

- **Target:** `std.Tuple [PROVENANCE-FALLBACK]`
- **Similarity:** 0.32
- **Dependents:** 0
- **Priority Score:** 106.8
- **Functions:** 1/1 matched (target 3)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/std/tuple.rs` vs expected `impls/std/tuple.rs`
- **Proposed provenance header:** `// port-lint: source impls/std/tuple.rs` (current: `// port-lint: source allocative/src/impls/std/tuple.rs`)
- **Lint issues:** 1

### 16. std.time

- **Target:** `std.Time [PROVENANCE-FALLBACK]`
- **Similarity:** 0.49
- **Dependents:** 0
- **Priority Score:** 105.1
- **Functions:** 1/1 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/std/time.rs` vs expected `impls/std/time.rs`
- **Proposed provenance header:** `// port-lint: source impls/std/time.rs` (current: `// port-lint: source allocative/src/impls/std/time.rs`)
- **Lint issues:** 1

### 17. std.collections

- **Target:** `std.Collections [PROVENANCE-FALLBACK]`
- **Similarity:** 0.52
- **Dependents:** 0
- **Priority Score:** 104.8
- **Functions:** 1/1 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/std/collections.rs` vs expected `impls/std/collections.rs`
- **Proposed provenance header:** `// port-lint: source impls/std/collections.rs` (current: `// port-lint: source allocative/src/impls/std/collections.rs`)
- **Lint issues:** 1

### 18. impls.serde_json

- **Target:** `impls.SerdeJson [PROVENANCE-FALLBACK]`
- **Similarity:** 0.76
- **Dependents:** 0
- **Priority Score:** 102.4
- **Functions:** 1/1 matched (target 8)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 9)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/serde_json.rs` vs expected `impls/serde_json.rs`
- **Proposed provenance header:** `// port-lint: source impls/serde_json.rs` (current: `// port-lint: source allocative/src/impls/serde_json.rs`)
- **Lint issues:** 1

### 19. impls.common

- **Target:** `impls.Common [PROVENANCE-FALLBACK]`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 0.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `allocative/src/impls/common.rs` vs expected `impls/common.rs`
- **Proposed provenance header:** `// port-lint: source impls/common.rs` (current: `// port-lint: source allocative/src/impls/common.rs`)
- **Lint issues:** 1

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

