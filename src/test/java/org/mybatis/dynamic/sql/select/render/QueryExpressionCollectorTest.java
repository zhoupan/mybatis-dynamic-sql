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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class QueryExpressionCollectorTest {

    @Test
    public void testQueryExpressionCollectorMerge() {
        List<RenderedQueryExpression> queryExpressions = new ArrayList<>();
        
        Map<String, Object> parms1 = new HashMap<>();
        parms1.put("p1", 1);
        RenderedQueryExpression fp1 = new RenderedQueryExpression.Builder()
                .withConnector(Optional.empty())
                .withColumnList("foo")
                .withTableName("bar")
                .withParameters(parms1)
                .build();
        queryExpressions.add(fp1);

        Map<String, Object> parms2 = new HashMap<>();
        parms2.put("p2", 2);
        RenderedQueryExpression fp2 = new RenderedQueryExpression.Builder()
                .withConnector(Optional.of("union"))
                .withColumnList("bar")
                .withTableName("foo")
                .withParameters(parms2)
                .build();
        queryExpressions.add(fp2);

        // parallelStream should trigger the merge
        QueryExpressionCollector collector = queryExpressions.parallelStream()
                .collect(QueryExpressionCollector.collect());

        assertThat(collector.queryExpression()).isEqualTo("select foo from bar union select bar from foo");
        assertThat(collector.parameters().size()).isEqualTo(2);
        assertThat(collector.parameters().get("p1")).isEqualTo(1);
        assertThat(collector.parameters().get("p2")).isEqualTo(2);
    }
}