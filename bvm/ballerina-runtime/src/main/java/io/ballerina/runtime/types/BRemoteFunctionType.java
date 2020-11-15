/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package io.ballerina.runtime.types;

import io.ballerina.runtime.api.types.RemoteFunctionType;
import io.ballerina.runtime.api.types.Type;

import java.util.StringJoiner;

/**
 * {@code RemoteFunctionType} represents a remote function in Ballerina.
 *
 * @since 2.0
 */
public class BRemoteFunctionType extends BMemberFunctionType implements RemoteFunctionType {

    public BRemoteFunctionType(String funcName, BObjectType parent, BFunctionType type, int flags) {
        super(funcName, parent, type, flags);
        this.funcName = funcName;
        this.type = type;
        this.flags = flags;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "remote function (", ") returns (" + type.retType + ")");
        for (Type type : type.paramTypes) {
            sj.add(type.getName());
        }
        return sj.toString();
    }
}
