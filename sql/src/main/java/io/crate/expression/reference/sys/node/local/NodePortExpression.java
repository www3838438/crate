/*
 * Licensed to Crate under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  Crate licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.  You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * However, if you have executed another commercial license agreement
 * with Crate these terms will supersede the license and you may use the
 * software solely pursuant to the terms of the relevant commercial
 * agreement.
 */

package io.crate.expression.reference.sys.node.local;

import io.crate.metadata.ReferenceImplementation;
import io.crate.expression.reference.NestedObjectExpression;
import org.elasticsearch.common.transport.TransportAddress;

import java.util.function.Supplier;

import static io.crate.expression.reference.sys.node.Ports.portFromAddress;

class NodePortExpression extends NestedObjectExpression {

    private static final String HTTP = "http";
    private static final String TRANSPORT = "transport";
    private final Supplier<TransportAddress> httpAddress;
    private final Supplier<TransportAddress> transportAddress;


    NodePortExpression(Supplier<TransportAddress> httpAddress,
                       Supplier<TransportAddress> transportAddress) {
        this.httpAddress = httpAddress;
        this.transportAddress = transportAddress;
        addChildImplementations();
    }

    private void addChildImplementations() {
        childImplementations.put(HTTP, new ReferenceImplementation<Integer>() {
            @Override
            public Integer value() {
                return portFromAddress(httpAddress.get());
            }
        });
        childImplementations.put(TRANSPORT, new ReferenceImplementation<Integer>() {
            @Override
            public Integer value() {
                return portFromAddress(transportAddress.get());
            }
        });
    }
}
