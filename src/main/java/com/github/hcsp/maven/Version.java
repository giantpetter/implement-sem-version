package com.github.hcsp.maven;

import java.util.ArrayList;
import java.util.List;

public class Version {
    /**
     * 请根据语义化版本的要求 https://semver.org/lang/zh-CN/ ，比较两个"语义化版本"
     *
     * <p>传入两个形如x.y.z的字符串，比较两个语义化版本的大小。如果version1小于version2，返回-1；如果version1大于
     * version2，返回1。如果二者相等，返回0。
     *
     * <p>注意，如果传入的字符串形如x，则其等价于x.0.0。 如果传入的字符串形如x.y，则其等价于x.y.0。
     *
     * @param version1 传入的版本字符串1，支持x/x.y/x.y.z，你可以假定传入的字符串一定是合法的语义化版本
     * @param version2 传入的版本字符串2，支持x/x.y/x.y.z，你可以假定传入的字符串一定是合法的语义化版本
     * @return -1/0/1 当version1 小于/等于/大于 version2时
     */

    private static int calcWeight(String version) {
        // 对版本号进行加权，x y z 分别对应 百 十 个
        int weight = 0;
        String[] versionArr = version.split("\\."); // ["12", "0", "5"]
        for (int i = versionArr.length - 1; i >= 0; i--) {
            weight += Integer.valueOf(versionArr[i]) * (Math.pow(10, i));
        }
        return weight;
    }
//
    private static int compareHelper(List<String> vArr1, List<String> vArr2) {
        for (int i = 0; i < 3; i++) {
            int v1 = Integer.parseInt(vArr1.get(i));
            int v2 = Integer.parseInt(vArr2.get(i));
            if (v1 < v2) {
                return -1;
            } else if (v1 > v2) {
                return 1;
            } else if (i == 2) {
                return 0;
            }
        }
        return 0;
    }

    private static List<String> stylizeVersion(String version) {
        List<String> vList = new ArrayList<>();
        for (String v : version.split("\\.")) {
            vList.add(v);
        }
        if (vList.size() == 1) {
            vList.add("0");
            vList.add("0");
        } else if (vList.size() == 2) {
            vList.add("0");
        }
        return vList;
    }

    public static int compare(String version1, String version2) {
        return compareHelper(stylizeVersion(version1), stylizeVersion(version2));
    }

    public static void main(String[] args) {
        System.out.println(compare("1.2", "1.2.1"));
    }
}
