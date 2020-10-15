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
package org.wso2.ballerinalang.compiler.diagnostic;

import io.ballerina.tools.diagnostics.Location;
import io.ballerina.tools.text.LinePosition;
import io.ballerina.tools.text.LineRange;
import io.ballerina.tools.text.TextRange;
import org.ballerinalang.model.elements.PackageID;

/**
 * Represent the location of a diagnostic in a {@code TextDocument}.
 * <p>
 * It is a combination of source file path, start and end line numbers, and start and end column numbers.
 *
 * @since 2.0.0
 */
public class BLangDiagnosticLocation implements Location {

    private LineRange lineRange;
    private TextRange textRange;
    private PackageID packageID;

    public BLangDiagnosticLocation(String filePath, int startLine, int endLine, int startColumn, int endColumn) {
        this.lineRange = LineRange.from(filePath, LinePosition.from(startLine, startColumn),
                LinePosition.from(endLine, endColumn));
        this.textRange = TextRange.from(0, 0);
    }

    public BLangDiagnosticLocation(String filePath, PackageID pkgId, int startLine, int endLine,
                                   int startColumn, int endColumn) {
        this.packageID = pkgId;
        this.lineRange = LineRange.from(filePath, LinePosition.from(startLine, startColumn),
                LinePosition.from(endLine, endColumn));
        this.textRange = TextRange.from(0, 0);
    }

    @Override
    public LineRange lineRange() {
        return lineRange;
    }

    @Override
    public TextRange textRange() {
        return textRange;
    }

    public PackageID getPackageID() {
        return packageID;
    }

    public int getStartLine() {
        return lineRange.startLine().line();
    }

    public int getEndLine() {
        return lineRange.endLine().line();
    }

    public int getStartColumn() {
        return lineRange.startLine().offset();
    }

    public int getEndColumn() {
        return lineRange.endLine().offset();
    }

    public void setPackageID(PackageID packageID) {
        this.packageID = packageID;
    }

    @Override
    public String toString() {
        return lineRange.toString() + textRange.toString();
    }

    public int compareTo(BLangDiagnosticLocation diagnosticLocation) {

        // Compare the source first.
        String thisDiagnosticString = packageID.name.value + packageID.version.value + lineRange().filePath();
        String otherDiagnosticString = diagnosticLocation.getPackageID().name.value +
                diagnosticLocation.getPackageID().version.value +
                diagnosticLocation.lineRange().filePath();
        int value = thisDiagnosticString.compareTo(otherDiagnosticString);

        if (value != 0) {
            return value;
        }

        // If the sources are same, then compare the start line.
        if (getStartLine() < diagnosticLocation.getStartLine()) {
            return -1;
        } else if (getStartLine() > diagnosticLocation.getStartLine()) {
            return 1;
        }

        // If the start line is the same, then compare the start column.
        if (getStartColumn() < diagnosticLocation.getStartColumn()) {
            return -1;
        } else if (getStartColumn() > diagnosticLocation.getStartColumn()) {
            return 1;
        }

        return 0;
    }
}
