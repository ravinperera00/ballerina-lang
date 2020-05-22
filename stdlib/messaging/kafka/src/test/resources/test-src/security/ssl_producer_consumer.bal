// Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/filepath;
import ballerina/kafka;

string topic = "test-topic-ssl";

kafka:ProducerConfiguration producerConfigs = {
    bootstrapServers: "localhost:14122",
    clientId:"ssl-producer",
    acks: kafka:ACKS_ALL,
    valueSerializerType: kafka:SER_STRING,
    retryCount:3,
    secureSocket: {
        keyStore:{
            location:"<FILE_PATH>" + filepath:getPathSeparator() + "kafka.client.keystore.jks",
            password:"test1234"
        },
        trustStore: {
            location:"<FILE_PATH>" + filepath:getPathSeparator() + "kafka.client.truststore.jks",
            password:"test1234"
        },
        protocol: {
            sslProtocol:"TLS",
            sslProtocolVersions:"TLSv1.2,TLSv1.1,TLSv1",
            securityProtocol:"SSL"
        },
        sslKeyPassword:"test1234"
    }
};

kafka:Producer kafkaProducer = new (producerConfigs);

kafka:ProducerConfiguration producerNegativeConfigs = {
    bootstrapServers: "localhost:14122",
    clientId:"ssl-producer-negative",
    acks: kafka:ACKS_ALL,
    maxBlock: 1000,
    valueSerializerType: kafka:SER_STRING,
    retryCount:3
};

kafka:Producer negativeProducer = new (producerNegativeConfigs);

kafka:ConsumerConfiguration consumerConfig = {
    bootstrapServers:"localhost:14122",
    groupId:"test-group",
    clientId: "ssl-consumer",
    offsetReset:"earliest",
    valueDeserializerType: kafka:DES_STRING,
    topics:["test-topic-ssl"],
    secureSocket: {
        keyStore:{
            location:"<FILE_PATH>" + filepath:getPathSeparator() + "kafka.client.keystore.jks",
            password:"test1234"
        },
        trustStore: {
            location:"<FILE_PATH>" + filepath:getPathSeparator() + "kafka.client.truststore.jks",
            password:"test1234"
        },
        protocol: {
            sslProtocol:"TLS",
            sslProtocolVersions:"TLSv1.2,TLSv1.1,TLSv1",
            securityProtocol:"SSL"
        },
        sslKeyPassword:"test1234"
    }
};

kafka:Consumer consumer = new (consumerConfig);

function testProducerWithSsl(string message) returns boolean|error {
    var result = kafkaProducer->send(message, topic);
    if (result is error) {
        return result;
    } else {
        return true;
    }
}

function testPollWithSsl() returns string|error {
    var results = consumer->poll(1000);
    if (results is error) {
        return results;
    } else {
        if (results.length() == 1) {
            var kafkaRecord = results[0];
            var message = kafkaRecord.value;
            if (message is string) {
                return message;
            } else {
                panic error("Incorrect type");
            }
        } else if (results.length() > 1) {
            return "More than one message received";
        } else {
            return "";
        }
    }
}

function testSslConnectNegative() returns int|error {
    string message = "Hello World SSL Negative Test";
    var result = negativeProducer->send(message, topic);
    if (result is error) {
        return result;
    } else {
        return 1;
    }
}
