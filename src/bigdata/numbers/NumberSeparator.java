package bigdata.numbers;

import java.io.*;
import java.util.*;

import static bigdata.numbers.NumberParam.*;

/**
 * @author yuqiang lin
 * @description 将大文件分成小文件
 * @email 1098387108@qq.com
 * @date 2018/10/18 上午11:30
 */
public class NumberSeparator {
    private NumberSeparator() {
    }

    public static void separateFile(String fileName, int separatorLineNumber) {
        File originalFile = new File(RESOURCE_PATH + File.separator + fileName + SUFFIX_NAME);

        try (FileReader originalFileReader = new FileReader(originalFile)) {
            try (LineNumberReader originalFileLineNumberReader = new LineNumberReader(originalFileReader)) {
                int lineNumber = (int) NumberMaker.readLineNumbers(fileName);
                List<Integer> sortedNumbers = new ArrayList<>(separatorLineNumber);
                for (int i = 1; i <= lineNumber / separatorLineNumber + 1; i++) {
                    sortedNumbers.clear();
                    File separatorFile = new File(RESOURCE_PATH + File.separator + fileName + '_' + i + SUFFIX_NAME);
                    try (FileWriter separatorFileWriter = new FileWriter(separatorFile)) {
                        try (BufferedWriter separatorBufferedWriter = new BufferedWriter(separatorFileWriter)) {
                            String numberStr;
                            // 读出一行
                            int count = 0;
                            while ((numberStr = originalFileLineNumberReader.readLine()) != null
                                    && count + 1 != separatorLineNumber) {
                                if ("".equals(numberStr)) {
                                    continue;
                                }
                                count++;
                                sortedNumbers.add(Integer.parseInt(numberStr));
                            }
                            if (numberStr != null && !"".equals(numberStr)) {
                                sortedNumbers.add(Integer.parseInt(numberStr));
                            }
                            if (!sortedNumbers.isEmpty()) {
                                count = 0;
                                sortedNumbers.sort((i1, i2) -> i1 - i2 < 0 ? -1 : 0);
                                for (Integer sortedNumber : sortedNumbers) {
                                    separatorBufferedWriter.write(sortedNumber.toString());
                                    if (++count != sortedNumbers.size()) {
                                        separatorBufferedWriter.newLine();
                                    }
                                }
                                separatorBufferedWriter.flush();
                            } else {
                                separatorFile.delete();
                            }
                        }
                    }
                }
            }
        } catch (IOException ignored) {
            // ignored
        }
    }
}
