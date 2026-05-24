// port-lint: source impls/common.rs
package io.github.kotlinmania.allocative.impls

import io.github.kotlinmania.allocative.key.Key

/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under both the MIT license found in the
 * LICENSE-MIT file in the root directory of this source tree and the Apache
 * License, Version 2.0 found in the LICENSE-APACHE file in the root directory
 * of this source tree.
 */

/** "Field" describing allocated but unused capacity, for example in a list. */
internal val UNUSED_CAPACITY_NAME: Key = Key.new("unused_capacity")

/** "Field" describing all capacity, for example in a list. */
internal val CAPACITY_NAME: Key = Key.new("capacity")

/** Generic field for a uniquely referenced value. */
internal val PTR_NAME: Key = Key.new("ptr")

/** Generic name for useful data, for example in a list. */
internal val DATA_NAME: Key = Key.new("data")

internal val KEY_NAME: Key = Key.new("key")
internal val VALUE_NAME: Key = Key.new("value")
