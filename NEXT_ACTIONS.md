# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 8/51 (15.7%)
- **Function parity:** 24/224 matched (target 53) — 10.7%
- **Class/type parity:** 5/53 matched (target 20) — 9.4%
- **Combined symbol parity:** 29/277 matched (target 73) — 10.5%
- **Average inline-code cosine:** 0.63 (function body across 8 matched files)
- **Average documentation cosine:** 0.50 (doc text across 8 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 3 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. visitor
- **Similarity:** 0.69 (needs 16% improvement)
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

- **Target:** `allocative.Visitor`
- **Similarity:** 0.69
- **Dependents:** 37
- **Priority Score:** 37012004.0
- **Functions:** 16/17 matched (target 16)
- **Missing functions:** `drop`
- **Types:** 3/3 matched
- **Missing types:** _none_

### 2. key

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

### 4. impls.hashbrown_util

- **Target:** `impls.HashbrownUtil`
- **Similarity:** 0.82
- **Dependents:** 1
- **Priority Score:** 1000101.8
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 0/0 matched
- **Missing types:** _none_

### 5. impls.indexmap

- **Target:** `impls.Indexmap`
- **Similarity:** 0.54
- **Dependents:** 0
- **Priority Score:** 204.6
- **Functions:** 2/2 matched (target 14)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

### 6. std.primitive

- **Target:** `std.Primitive [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 110.0
- **Functions:** 1/1 matched (target 11)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 11)
- **Missing types:** _none_

### 7. allocative_trait

- **Target:** `allocative.AllocativeTrait`
- **Similarity:** 1.00
- **Dependents:** 0
- **Priority Score:** 100.0
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

### 8. impls.common

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

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

