package bigdata.numbers;

import java.io.*;
import java.util.*;

import static bigdata.numbers.NumberParam.*;

/**
 * @author yuqiang lin
 * @description 排序
 * @email 1098387108@qq.com
 * @date 2018/10/18 下午3:29
 */
public class NumberSorter {
    private NumberSorter() {
    }

    public static void SortNumbers(List<String> unsortedFileNames, String sortedFileName) {

        List<FileReader> fileReaders = new LinkedList<>();
        List<LineNumberReader> lineNumberReaders = new LinkedList<>();
        try {
            initResources(unsortedFileNames, fileReaders, lineNumberReaders);

            File sortedFile = new File(RESOURCE_PATH + File.separator + sortedFileName + SUFFIX_NAME);
            boolean first = true;
            try (FileWriter sortedFileWriter = new FileWriter(sortedFile)) {
                try (BufferedWriter sortedBufferedWriter = new BufferedWriter(sortedFileWriter)) {
                    LinkedList<String> minNumbers = new LinkedList<>();
                    // 初始化
                    initSortedNumbers(fileReaders, lineNumberReaders, minNumbers);
                    String readLine;
                    while (!minNumbers.isEmpty()) {
                        // 排序
                        int min = Integer.parseInt(minNumbers.getFirst());
                        int index = 0;
                        for (int i = 0; i < minNumbers.size(); i++) {
                            if (Integer.valueOf(minNumbers.get(i)) < min) {
                                min = Integer.valueOf(minNumbers.get(i));
                                index = i;
                            }
                        }
                        if (!first) {
                            sortedBufferedWriter.newLine();
                        }
                        if (first) {
                            first = false;
                        }
                        sortedBufferedWriter.write(String.valueOf(min));
                        readLine = lineNumberReaders.get(index).readLine();
                        if (readLine != null) {
                            minNumbers.set(index, readLine);
                        } else {
                            minNumbers.remove(index);
                            fileReaders.remove(index);
                            lineNumberReaders.remove(index);
                        }
                    }
                    sortedBufferedWriter.flush();
                }
            }
        } catch (IOException ignored) {
            // ignored
        } finally {
            releaseResources(fileReaders, lineNumberReaders);
        }
    }

    /**
     * 初始化资源
     *
     * @param unsortedFileNames unsortedFileNames
     * @param fileReaders       fileReaders
     * @param lineNumberReaders lineNumberReaders
     * @throws FileNotFoundException FileNotFoundException
     */
    private static void initResources(List<String> unsortedFileNames, List<FileReader> fileReaders, List<LineNumberReader> lineNumberReaders) throws FileNotFoundException {
        for (String unsortedFileName : unsortedFileNames) {
            File unsortedFile = new File(RESOURCE_PATH + File.separator + unsortedFileName + SUFFIX_NAME);
            FileReader fileReader = new FileReader(unsortedFile);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);
            fileReaders.add(fileReader);
            lineNumberReaders.add(lineNumberReader);
        }
    }

    /**
     * 初始化
     *
     * @param fileReaders       fileReaders
     * @param lineNumberReaders lineNumberReaders
     * @param sortedNumbers     sortedNumbers
     * @throws IOException IOException
     */
    private static void initSortedNumbers(List<FileReader> fileReaders, List<LineNumberReader> lineNumberReaders, List<String> sortedNumbers) throws IOException {
        String readLine;
        for (int i = 0; i < lineNumberReaders.size(); i++) {
            LineNumberReader lineNumberReader = lineNumberReaders.get(i);
            if ((readLine = lineNumberReader.readLine()) != null) {
                sortedNumbers.add(readLine);
            } else {
                releaseFileReader(fileReaders.get(i));
                releaseLineNumberReader(lineNumberReaders.get(i));
            }
        }
    }

    /**
     * 释放资源
     *
     * @param fileReaders       fileReaders
     * @param lineNumberReaders lineNumberReaders
     */
    private static void releaseResources(List<FileReader> fileReaders, List<LineNumberReader> lineNumberReaders) {
        for (FileReader fileReader : fileReaders) {
            releaseFileReader(fileReader);
        }
        for (LineNumberReader lineNumberReader : lineNumberReaders) {
            releaseLineNumberReader(lineNumberReader);
        }
    }

    /**
     * 释放资源
     *
     * @param lineNumberReader lineNumberReader
     */
    private static void releaseLineNumberReader(LineNumberReader lineNumberReader) {
        if (lineNumberReader != null) {
            try {
                lineNumberReader.close();
            } catch (IOException ignored) {
                // ignored
            }
        }
    }

    /**
     * 释放资源
     *
     * @param fileReader fileReader
     */
    private static void releaseFileReader(FileReader fileReader) {
        if (fileReader != null) {
            try {
                fileReader.close();
            } catch (IOException ignored) {
                // ignored
            }
        }
    }
}
