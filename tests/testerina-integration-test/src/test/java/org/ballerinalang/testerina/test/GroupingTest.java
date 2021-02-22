/*
 *  Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.ballerinalang.testerina.test;

import org.ballerinalang.test.context.BMainInstance;
import org.ballerinalang.test.context.BallerinaTestException;
import org.ballerinalang.testerina.test.utils.AssertionUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * Test class containing tests related to test grouping.
 */
public class GroupingTest extends BaseTestCase {

    private BMainInstance balClient;
    private String projectPath;

    @BeforeClass
    public void setup() throws BallerinaTestException {
        balClient = new BMainInstance(balServer);
        projectPath = singleFileTestsPath.resolve("grouping").toString();
    }

    @Test
    public void testSingleGroupExecution() throws BallerinaTestException {
        String msg = "3 passing";
        String[] args = mergeCoverageArgs(new String[]{"--groups", "g1", "groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, false);
        if (!output.contains(msg)) {
            AssertionUtils.assertForTestFailures(output, "single group test execution failure");
        }
    }

    @Test
    public void testMultipleGroupExecution() throws BallerinaTestException {
        String msg = "3 passing";
        String[] args = mergeCoverageArgs(new String[]{"--groups", "g2,g4", "groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, false);
        if (!output.contains(msg)) {
            AssertionUtils.assertForTestFailures(output, "multiple group test execution failure");
        }
    }

    @Test
    public void testSingleGroupExclusion() throws BallerinaTestException {
        String msg1 = "4 passing";
        String msg2 = "1 failing";
        String[] args = mergeCoverageArgs(new String[]{"--disable-groups", "g5", "groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, false);
        if (!output.contains(msg1) || !output.contains(msg2)) {
            AssertionUtils.assertForTestFailures(output, "single group exclusion failure");
        }
    }

    @Test
    public void testMultipleGroupExclusion() throws BallerinaTestException {
        String msg = "1 passing";
        String[] args = mergeCoverageArgs(new String[]{"--disable-groups", "g1,g5,g6", "groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, false);
        if (!output.contains(msg)) {
            AssertionUtils.assertForTestFailures(output, "multiple group exclusion failure");
        }
    }

    @Test
    public void testNonExistingGroupInclusion() throws BallerinaTestException {
        String msg = "No tests found";
        String[] args = mergeCoverageArgs(new String[]{"--groups", "g10", "groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, false);
        if (!output.contains(msg)) {
            AssertionUtils.assertForTestFailures(output, "non existent group inclusion failure");
        }
    }

    @Test
    public void testNonExistingGroupExclusion() throws BallerinaTestException {
        String msg1 = "4 passing";
        String msg2 = "2 failing";
        String[] args = mergeCoverageArgs(new String[]{"--disable-groups", "g10", "groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, false);
        if (!output.contains(msg1) || !output.contains(msg2)) {
            AssertionUtils.assertForTestFailures(output, "non existent group exclusion failure");
        }
    }

    @Test
    public void testListingOfTestGroups() throws BallerinaTestException {
        String msg = "[g1, g2, g3, g4, g5, g6]";
        String[] args = mergeCoverageArgs(new String[]{"--list-groups", "groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, false);
        if (!output.contains(msg)) {
            AssertionUtils.assertForTestFailures(output, "listing test groups failure");
        }
    }

    @Test
    public void testListGroupsWithOtherFlags() throws BallerinaTestException {
        String msg = "Warning: Other flags are skipped when list-groups flag is provided.";
        String[] args = mergeCoverageArgs(new String[]{"--groups", "g1", "--list-groups", "groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, false);
        if (!output.contains(msg)) {
            AssertionUtils.assertForTestFailures(output, "listing test groups with other flags failure");
        }
    }

    @Test
    public void beforeGroupsAfterGroups1() throws BallerinaTestException {
        String[] args = mergeCoverageArgs(new String[]{"before-groups-after-groups-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, true);
        if (output.contains("[fail] afterSuiteFunc")) {
            throw new BallerinaTestException("Test failed due to assertion failure in after suite function");
        }
    }

    @Test
    public void beforeGroupsAfterGroups2() throws BallerinaTestException {
        String[] args = mergeCoverageArgs(new String[]{"before-groups-after-groups-test2.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, true);
        if (output.contains("[fail] afterSuiteFunc")) {
            throw new BallerinaTestException("Test failed due to assertion failure in after suite function");
        }
    }

    @Test
    public void afterGroupsWithDisabledTest() throws BallerinaTestException {
        String[] args = mergeCoverageArgs(new String[]{"--groups", "g1", "after-groups-with-disabled-test.bal"});
        String output = balClient.runMainAndReadStdOut("test", args,
                new HashMap<>(), projectPath, true);
        if (output.contains("[fail] afterSuiteFunc")) {
            throw new BallerinaTestException("Test failed due to assertion failure in after suite function");
        }
    }

}
