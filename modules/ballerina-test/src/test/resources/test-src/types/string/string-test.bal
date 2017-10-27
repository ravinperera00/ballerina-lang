import ballerina.lang.strings;

function contains(string source, string substring) (boolean) {
    return source.contains(substring);
}

function equalsIgnoreCase(string s1, string s2) (boolean) {
    return s1.equalsIgnoreCase(s2);
}

function hasPrefix(string s, string prefix) (boolean) {
    return s.hasPrefix(prefix);
}

function hasSuffix(string s, string suffix) (boolean) {
    return s.hasSuffix(suffix);
}

function indexOf(string s, string str) (int) {
    return s.indexOf(str);
}

function lastIndexOf(string s, string str) (int) {
    return s.lastIndexOf(str);
}

function replace(string s, string source, string target) (string) {
    return s.replace(source, target);
}

function replaceAll(string s, string source, string target) (string) {
    return s.replaceAll(source, target);
}

function replaceFirst(string s, string source, string target) (string) {
    return s.replaceFirst(source, target);
}

function subString(string s, int from, int to) (string) {
    return s.subString(from, to);
}

function toLowerCase(string s) (string) {
    return s.toLowerCase();
}

function toUpperCase(string s) (string) {
    return s.toUpperCase();
}

function trim(string s) (string) {
    return s.trim();
}

function intValueOf(int i) (string) {
    return strings:valueOf(i);
}

function floatValueOf(float f) (string) {
    return strings:valueOf(f);
}

function booleanValueOf(boolean b) (string) {
    return strings:valueOf(b);
}

function stringValueOf(string s) (string) {
    return strings:valueOf(s);
}

function xmlValueOf(xml x) (string) {
    return strings:valueOf(x);
}

function jsonValueOf(json j) (string) {
    return strings:valueOf(j);
}

function length(string j) (int) {
    return j.length();
}

function unescape(string j) (string) {
    return j.unescape();
}

function split(string j, string k) (string[]) {
    return j.split(k);
}

function toBlob(string l, string m) (blob) {
    return l.toBlob(m);
}
