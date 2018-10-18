package bigdata.numbers;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

import static bigdata.numbers.NumberParam.*;

/**
 * @author yuqiang lin
 * @description 制造随机int型整数，并写入文件
 * @email 1098387108@qq.com
 * @date 2018/10/18 上午10:21
 */
public class NumberMaker {
    private NumberMaker() {
    }

    /**
     * 写数据
     *
     * @param fineName   文件名
     * @param lineNumber 文件行数
     */
    public static void writeLineNumbers(String fineName, long lineNumber) {
        File originalFile = new File(RESOURCE_PATH + File.separator + fineName + SUFFIX_NAME);

        try (FileWriter originalFileWriter = new FileWriter(originalFile)) {
            try (BufferedWriter originalBufferedWriter = new BufferedWriter(originalFileWriter)) {
                // 写1亿个数，写一个另起一行
                StringBuilder sb = new StringBuilder();
                boolean first = true;
                while (lineNumber > 0) {
                    int size = 1000000;
                    for (int j = 0; j < Math.min(size, lineNumber); j++) {
                        if (!first) {
                            sb.append("\n");
                        } else {
                            first = false;
                        }
                        sb.append(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
                    }
                    originalBufferedWriter.write(sb.toString());
                    sb.setLength(0);
                    lineNumber -= size;
                }
                // 关闭资源前将缓冲区数据全部写出
                originalBufferedWriter.flush();
            }
        } catch (IOException ignored) {
            // ignored
        }
    }

    /**
     * 获取文件行数
     *
     * @param fileName 文件名
     * @return 文件行数
     */
    public static long readLineNumbers(String fileName) {
        File originalFile = new File(RESOURCE_PATH + File.separator + fileName + SUFFIX_NAME);
        try (FileReader originalFileReader = new FileReader(originalFile)) {
            LineNumberReader originalFileLineNumberReader = new LineNumberReader(originalFileReader);
            // 跳过多少个字符
            originalFileLineNumberReader.skip(Long.MAX_VALUE);
            return originalFileLineNumberReader.getLineNumber() + 1;
        } catch (IOException ignored) {
            // ignored
        }
        return -1;
    }
}
