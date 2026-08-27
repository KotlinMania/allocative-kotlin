# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 19/52 (36.5%)
- **Function parity:** 82/195 matched (target 177) — 42.1%
- **Class/type parity:** 16/53 matched (target 71) — 30.2%
- **Combined symbol parity:** 98/248 matched (target 248) — 39.5%
- **Average inline-code cosine:** 0.47 (function body across 19 matched files)
- **Average documentation cosine:** 0.34 (doc text across 19 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 12 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. allocative.visitor
- **Similarity:** 0.68 (needs 17% improvement)
- **Dependencies:** 37
- **Priority Score:** 37012004.0
- **Functions:** 16/17 matched (target 16)
- **Missing functions:** `drop`
- **Types:** 3/3 matched
- **Missing types:** _none_
- **Symbol Deficit:** 1 (functions: 1, types: 0)
- **Action:** Review and complete missing sections

### 2. allocative.key
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

### 1. allocative.visitor

- **Target:** `allocative.Visitor`
- **Similarity:** 0.68
- **Dependents:** 37
- **Priority Score:** 37012004.0
- **Functions:** 16/17 matched (target 16)
- **Missing functions:** `drop`
- **Types:** 3/3 matched
- **Missing types:** _none_

### 2. allocative.key

- **Target:** `key.Key`
- **Similarity:** 0.25
- **Dependents:** 19
- **Priority Score:** 19061008.0
- **Functions:** 3/7 matched (target 9)
- **Missing functions:** `partial_cmp`, `cmp`, `hash`, `deref`
- **Types:** 1/3 matched (target 1)
- **Missing types:** `Target`, `AllocativeKeyForType`

### 3. std.mem

- **Target:** `std.Mem`
- **Similarity:** 0.70
- **Dependents:** 17
- **Priority Score:** 17000104.0
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 4. allocative.flamegraph

- **Target:** `allocative.FlameGraph`
- **Similarity:** 0.62
- **Dependents:** 1
- **Priority Score:** 1155503.8
- **Functions:** 33/43 matched (target 37)
- **Missing functions:** `flamegraph`, `warnings`, `as_ref`, `index`, `index_mut`, `current_data`, `_assert_flame_graph_builder_is_send`, `assert_send`, `default`, `current`
- **Types:** 7/12 matched (target 9)
- **Missing types:** `TreeId`, `Trees`, `Output`, `TreeStackRef`, `VisitedSharedPointer`
- **Tests:** 6/6 matched

### 5. impls.hashbrown_util

- **Target:** `impls.HashbrownUtil`
- **Similarity:** 0.82
- **Dependents:** 1
- **Priority Score:** 1000101.8
- **Functions:** 1/1 matched (target 3)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 6. allocative.rc_str

- **Target:** `allocative.RcStr`
- **Similarity:** 0.28
- **Dependents:** 0
- **Priority Score:** 40807.2
- **Functions:** 3/6 matched (target 10)
- **Missing functions:** `deref`, `borrow`, `hash`
- **Types:** 1/2 matched
- **Missing types:** `Target`

### 7. std.sync

- **Target:** `std.Sync`
- **Similarity:** 0.15
- **Dependents:** 0
- **Priority Score:** 40508.5
- **Functions:** 1/3 matched (target 4)
- **Missing functions:** `layout`, `test_arc_align`
- **Types:** 0/2 matched (target 4)
- **Missing types:** `RcBox`, `CacheLine`
- **Tests:** 0/1 matched

### 8. std.cell

- **Target:** `std.Cell`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 30407.5
- **Functions:** 1/4 matched (target 6)
- **Missing functions:** `test_default`, `test_borrowed`, `test_once_cell`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Tests:** 0/3 matched

### 9. allocative.size_of

- **Target:** `allocative.SizeOf`
- **Similarity:** 0.67
- **Dependents:** 0
- **Priority Score:** 11503.3
- **Functions:** 12/12 matched (target 16)
- **Missing functions:** _none_
- **Types:** 2/3 matched (target 6)
- **Missing types:** `Boxed`
- **Tests:** 3/3 matched

### 10. impls.either

- **Target:** `impls.Either`
- **Similarity:** 0.32
- **Dependents:** 0
- **Priority Score:** 10206.8
- **Functions:** 1/2 matched (target 3)
- **Missing functions:** `test_golden`
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 11. allocative.global_root

- **Target:** `allocative.GlobalRoot`
- **Similarity:** 0.55
- **Dependents:** 0
- **Priority Score:** 404.5
- **Functions:** 3/3 matched (target 4)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 12. impls.indexmap

- **Target:** `impls.Indexmap`
- **Similarity:** 0.54
- **Dependents:** 0
- **Priority Score:** 204.6
- **Functions:** 2/2 matched (target 14)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

### 13. allocative.allocative_trait

- **Target:** `allocative.AllocativeTrait [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 110.0
- **Functions:** 0/0 matched (target 2)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_

### 14. std.primitive

- **Target:** `std.Primitive [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 110.0
- **Functions:** 1/1 matched (target 31)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 13)
- **Missing types:** _none_

### 15. std.tuple

- **Target:** `std.Tuple`
- **Similarity:** 0.32
- **Dependents:** 0
- **Priority Score:** 106.8
- **Functions:** 1/1 matched (target 3)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

### 16. std.time

- **Target:** `std.Time`
- **Similarity:** 0.49
- **Dependents:** 0
- **Priority Score:** 105.1
- **Functions:** 1/1 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 17. std.collections

- **Target:** `std.Collections`
- **Similarity:** 0.52
- **Dependents:** 0
- **Priority Score:** 104.8
- **Functions:** 1/1 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

### 18. impls.serde_json

- **Target:** `impls.SerdeJson`
- **Similarity:** 0.76
- **Dependents:** 0
- **Priority Score:** 102.4
- **Functions:** 1/1 matched (target 8)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 9)
- **Missing types:** _none_

### 19. impls.common

- **Target:** `impls.Common`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 0.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

