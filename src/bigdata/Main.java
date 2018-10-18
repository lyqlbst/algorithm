package bigdata;

import bigdata.numbers.NumberMaker;
import bigdata.numbers.NumberSeparator;
import bigdata.numbers.NumberSorter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuqiang lin
 * @description 主方法，限制内存 50m
 * @email 1098387108@qq.com
 * @date 2018/10/18 上午11:32
 */
public class Main {
    public static void main(String[] args) {
        // 生成随机数字，1亿个数，1亿行
        long startTime = System.currentTimeMillis();
        NumberMaker.writeLineNumbers("number", 400000000);
        long currentTime = System.currentTimeMillis();
        System.out.println("make time:" + (currentTime - startTime) / 1000 + "s");

        // 获取文件行数
        startTime = System.currentTimeMillis();
        long lineNumber = NumberMaker.readLineNumbers("number");
        System.out.println("original file lines:" + lineNumber);
        currentTime = System.currentTimeMillis();
        System.out.println("read line number time:" + (currentTime - startTime) / 1000 + "s");

        // 分为小文件
        startTime = System.currentTimeMillis();
        NumberSeparator.separateFile("number", 4000000);
        currentTime = System.currentTimeMillis();
        System.out.println("separate time:" + (currentTime - startTime) / 1000 + "s");

        // 最终排序
        int fileNum = 50;
        List<String> fileNames = new ArrayList<>(fileNum);
        for (int i = 1; i <= fileNum; i++) {
            fileNames.add("number_" + i);
        }
        startTime = System.currentTimeMillis();
        NumberSorter.SortNumbers(fileNames, "sortedNumber");
        currentTime = System.currentTimeMillis();
        System.out.println("sort and integrate time:" + (currentTime - startTime) / 1000 + "s");
    }
}