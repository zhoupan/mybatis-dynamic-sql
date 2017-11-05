/**
 *    Copyright 2016-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.dynamic.sql.select.render;

import java.util.Map;
import java.util.Optional;

import org.mybatis.dynamic.sql.SqlTable;

/**
 * Returns the alias for a table if specified, or the table name itself.
 * This is useful for join rendering when we always want to have an alias for the table.
 * 
 * @author Jeff Butler
 * 
 */
public class GuaranteedAliasMap extends AliasMap {

    private GuaranteedAliasMap(Map<SqlTable, String> aliases) {
        super(aliases);
    }

    @Override
    public Optional<String> aliasFor(SqlTable table) {
        return super.aliasFor(table)
                .map(Optional::of)
                .orElse(Optional.of(table.name()));
    }
    
    public static GuaranteedAliasMap of(Map<SqlTable, String> aliases) {
        return new GuaranteedAliasMap(aliases);
    }
}