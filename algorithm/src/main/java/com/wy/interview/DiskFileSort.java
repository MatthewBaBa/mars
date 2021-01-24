package com.wy.interview;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import com.wy.interview.DiskFileSort.ReadSortWriteQueue.SortEntry;


/**
 * 大文件内容排序
 * 用有限内存（比如 256MB），处理一个大文件（比如 4GB），该文件每行都只有一个全局不重复的乱序数字（正整数），排序好输出到一个新的文件。
 * 为了节约时间，定义大文件是：100w 行，内存能处理 1w 行（排序+输出）
 * 第一步：按要求生成一个文件 input.txt
 * 第二步：按题目要求处理该文件，最终输出成 output.txt
 * 第三步：统计排序算法的耗时，通过并发编程，在确保结果正确的基础上，如何减少耗时
 *
 * @author matthew_wu
 * @since 2020/12/25 3:48 下午
 */
public class DiskFileSort {

    static String inputFile = "/Users/abc/codeInterview/input.txt";
    static String outputFile = "/Users/abc/codeInterview/output.txt";
    static String midFilePath = "/Users/abc/codeInterview/tmp/";
    static String midFilePrefix = "tmp-";
    static int totalCount = 1000000;
    static int splitCount = 10000;
    static int queueSize = 100;

    static long maxMem = 0;

    public static void main(String[] args) {

        checkMaxMem("begin");

        long g_s = System.nanoTime();
        BufferedReader bufferedReader = generateInputFile(inputFile, totalCount);
        long g_time = System.nanoTime() - g_s;
        System.out.printf("generate cost %d ns, %d ms \n", g_time, g_time / 1000000);

        checkMaxMem("after generate input");

        if (bufferedReader != null) {
            try {
                System.out.println("begin to doDiskFileSort..");
                long s_s = System.nanoTime();
                //doDiskFileSort(bufferedReader);
                //doDiskFileSortDivid(bufferedReader);
                doDiskFileSortDividConcurrent(bufferedReader);
                long s_time = System.nanoTime() - s_s;

                System.out.printf("sort cost %d ns, %d ms \n", s_time, s_time / 1000000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        checkMaxMem("end");
    }

    /**
     * 随机生成input.txt文件，如果文件已经存在，则复用
     * @param path  文件路径
     * @param count 行数
     * @return      input.txt 的BufferedReader 起点句柄
     */
    private static BufferedReader generateInputFile(String path, int count) {

        BufferedWriter bufferedWriter;
        try {
            File file = new File(path);
            if (file.isFile()) {
                return new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path)),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        if (bufferedWriter != null) {
            Random random = new Random(System.currentTimeMillis());
            BitSet bitSet = new BitSet(Integer.MAX_VALUE);
            Integer lineNum;

            for (int i = 0; i < count; i++) {
                do {
                    lineNum = Math.abs(random.nextInt());
                } while (bitSet.get(lineNum));
                bitSet.set(lineNum);

                try {
                    bufferedWriter.write(lineNum.toString());
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void checkMaxMem(String info) {
        long cur = Runtime.getRuntime().totalMemory()/1024/1024 - Runtime.getRuntime().freeMemory()/1024/1024;
        if (maxMem < cur) {
            maxMem = cur;
        }
        System.out.println("memUse:" + cur + "MB | maxUse: " + maxMem + "MB | " + info);
    }

    /**
     * 方案 A
     * 全部读到内存里，排序，输出
     * @param bufferedReader
     * @throws IOException
     */
    private static void doDiskFileSort(BufferedReader bufferedReader) throws IOException {
        if (bufferedReader == null) {
            return;
        }
        String line = null;
        List<Integer> sortList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            Integer lineNum = new Integer(line);
            if (lineNum != null) {
                sortList.add(lineNum);
            }
        }
        sortAndFlush(sortList, outputFile);

        bufferedReader.close();
    }

    private static void sortAndFlush(List<Integer> sortList, String filePath) throws IOException {
        if (!sortList.isEmpty()) {
            Collections.sort(sortList);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "utf-8"));
            for (Integer i : sortList) {
                bufferedWriter.write(i.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    /**
     * 方案 B
     * 分成n个小文件，分别排序，再归并输出
     * @param bufferedReader
     * @throws Exception
     */
    private static void doDiskFileSortDivid(BufferedReader bufferedReader) throws Exception {

        doSplitFileAndSort(bufferedReader);

        doMergeSortedFile();

    }

    /**
     * 方案 B - 第一步 拆分排序成小文件
     * @param bufferedReader
     * @throws Exception
     */
    private static void doSplitFileAndSort(BufferedReader bufferedReader)  throws Exception {
        if (bufferedReader == null) {
            return;
        }

        checkMaxMem("split begin");

        String line = null;
        List<Integer> sortList = new ArrayList<>();
        int count = 0;
        while ((line = bufferedReader.readLine()) != null) {
            count++;
            Integer lineNum = new Integer(line);
            if (lineNum != null) {
                sortList.add(lineNum);
            }
            if (count % splitCount == 0) {
                sortAndFlush(sortList, midFilePath + midFilePrefix +(count / splitCount)+".txt");
                sortList = new ArrayList<>();
            }
        }

        checkMaxMem("split end");

    }

    /**
     * 方案 B - 第二步 归并排序输出
     * @throws Exception
     */
    private static void doMergeSortedFile() throws Exception {

        checkMaxMem("merge begin");

        System.out.println("begin to merge sort...");

        // 打开中间文件所在的目录
        File file = new File(midFilePath);
        if (file.isDirectory()) {
            // 处理已经分隔好，并且排好序的文件
            // 把所有的文件句柄初始化成MidFile对象，存入一个Map里，value 是该文件里待输出的最小的数 (因为有序，所以通过readLine() 遍历即可）
            Map<MidFile, Integer> tmpFiles = new HashMap<>(totalCount / splitCount);
            for (File tmp : file.listFiles()) {
                // 过滤文件前缀
                if (!tmp.getName().startsWith(midFilePrefix)) {
                    continue;
                }

                MidFile midFile = new MidFile(tmp);
                tmpFiles.put(midFile, midFile.getNextNum());
            }

            // 这个Map里的Value集合就是 每个文件最小数
            // 将其中最小的一个输出到outputFile里，再从该MidFile里取下一个数字，重新排序后选择最小的输出。
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFile)), "utf-8"));

            // 实现一个Map-Value 排序器，new 一个实例，new 一个List容器
            DiskFileSort.ValueComparator valueComparator = new ValueComparator();
            List<Map.Entry<MidFile, Integer>> sortFiles = new ArrayList<>();
            sortFiles.addAll(tmpFiles.entrySet());

            do {
                // 排序List容器，升序
                Collections.sort(sortFiles, valueComparator);

                if (sortFiles.size() == 0) {
                    continue;
                }

                // 有序的List容器的第一个MidFile的值输出。
                String minNum = sortFiles.get(0).getValue().toString();
                bufferedWriter.write(minNum);
                bufferedWriter.newLine();

                // 有序的List容器的第一个MidFile补充数字
                Integer nextNum = sortFiles.get(0).getKey().getNextNum();

                // 如果该MidFile已经取空，则从 Map里移除，同时从排序List容器移除
                // 如果MidFile有值，则更新Map里该MidFile的 Value
                if (nextNum == null) {
                    tmpFiles.remove(sortFiles.get(0).getKey());
                    sortFiles.remove(0);
                } else {
                    tmpFiles.put(sortFiles.get(0).getKey(), nextNum);
                }
            } while (!tmpFiles.isEmpty());

            bufferedWriter.flush();
            bufferedWriter.close();
        }

        checkMaxMem("merge end");

    }

    /**
     * Map ValueSet 的排序比较器
     */
    static class ValueComparator implements Comparator<Entry<MidFile,Integer>> {
        @Override
        public int compare(Map.Entry<MidFile,Integer> m, Map.Entry<MidFile,Integer> n)
        {
            return m.getValue()-n.getValue();
        }
    }

    /**
     * 中间文件类，封装读文件
     */
    static class MidFile {

        protected BufferedReader bufferedReader;
        protected String fileName;

        MidFile(File file) {
            try {
                if (file != null) {
                    this.bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    fileName = file.getName();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        protected Integer getNextNum() {
            try {
                if (this.bufferedReader != null) {
                    String line;
                    do {
                        line = this.bufferedReader.readLine();
                        if (line == null) {
                            return null;
                        }
                    } while (StringUtils.isBlank(line));
                    return new Integer(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 中间文件，并发处理的封装
     */
    static class MidFileCur extends MidFile {

        /**
         * 中间文件读队列
         */
        ConcurrentLinkedQueue<Integer> readQueue;
        /**
         * 文件是否读完
         */
        volatile boolean isFileEOF = false;
        /**
         * 文件队列是否存满
         */
        volatile boolean isReadQueueFull = false;
        /**
         * 文件队列是否已经已经处理完成
         */
        volatile boolean isReadQueueFinished = false;

        MidFileCur(File file) {
            super(file);
            readQueue = new ConcurrentLinkedQueue();
        }

        /**
         * 从MidFile 顺序读一个数，加载到readQueue里
         * 一次性尽量读满文件队列
         */
        public void reloadQ() {
            while (!isReadQueueFinished && !isFileEOF && !isReadQueueFull) {
                Integer next = getNextNum();
                if (next != null) {
                    readQueue.add(next);
                } else {
                    isFileEOF = true;
                }
                if (readQueue.size() >= queueSize) {
                    isReadQueueFull = true;
                    break;
                }
            }
            checkFinished();
        }

        public Integer pollQ() {
            Integer min = readQueue.poll();
            isReadQueueFull = false;
            checkFinished();
            return min;
        }

        public void checkFinished() {
            if (isFileEOF && readQueue.isEmpty()) {
                isReadQueueFinished = true;
            }
        }

    }

    private static ExecutorService createFixedThreadPool(int corePoolSize, int maxPoolSize) {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, 60, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(600), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
    }

    /**
     * 方案 C
     * 在方案B的基础上 加入并发编程优化方案
     * @param bufferedReader
     * @throws Exception
     */
    private static void doDiskFileSortDividConcurrent(BufferedReader bufferedReader) throws Exception {

        ExecutorService ioTask = createFixedThreadPool(7, 200);
        ExecutorService cpuTask = createFixedThreadPool(1, 200);

        checkMaxMem("split begin");

        /**
         * 第一阶段并发：拆分 + 排序，用countDownLatch线程模型，从大文件里读一块数据，排序一块数据，输出到中间文件
         */
        final CountDownLatch latch = new CountDownLatch(totalCount / splitCount);

        ioTask.submit(new SplitReadTask("SplitFile", bufferedReader, cpuTask, ioTask, latch));

        // wait all split files have bean sorted
        latch.await();

        checkMaxMem("split end");

        final CountDownLatch latchRes  = new CountDownLatch(1);

        checkMaxMem("merge begin");

        /**
         * 第二阶段并发：
         *  为每个中间文件 建立一个 读Buffer，第一个读任务负责 当文件buffer有空余时，从文件里读取文件的最小数，并写入buffer
         *  第二个任务从每个文件buffer里读取第一个数字 形成SortList，并排序得到全局最小的数字，写入 WriteQueue
         *  第三个任务从WriteQueue里依次写入磁盘
         */
        doMergeSortedFileConcurrent(ioTask, cpuTask, latchRes);
        // 可以替换成单线程做测试 和 效果对比
        //doMergeSortedFile();

        latchRes.countDown();

        checkMaxMem("merge end");

        ioTask.shutdown();
        cpuTask.shutdown();
        try {
            ioTask.awaitTermination(0, TimeUnit.MILLISECONDS);
            cpuTask.awaitTermination(0, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void doMergeSortedFileConcurrent(ExecutorService ioTask, ExecutorService cpuTask,
                                                    CountDownLatch latchRes) throws Exception {

        System.out.println("begin to merge sort, concurrently ...");

        ioTask.submit(new MergeReadTask(ReadSortWriteQueue.getInstance()));
        cpuTask.submit(new MergeSortTask(ReadSortWriteQueue.getInstance()));
        ioTask.submit(new MergeWriteTask(ReadSortWriteQueue.getInstance(), latchRes));
    }

    /**
     * 第一阶段的 分治任务类
     */

    static class SplitReadTask implements Runnable {

        private String taskName;
        private BufferedReader bufferedReader;
        private ExecutorService cpuTask;
        private ExecutorService ioTask;
        private CountDownLatch latch;

        SplitReadTask(String taskName, BufferedReader bufferedReader, ExecutorService cpuTask, ExecutorService ioTask, CountDownLatch latch) {
            this.taskName = taskName;
            this.bufferedReader = bufferedReader;
            this.cpuTask = cpuTask;
            this.ioTask = ioTask;
            this.latch = latch;
        }

        @Override
        public void run() {
            if (bufferedReader == null) {
                return;
            }
            try {
                String line = null;
                List<Integer> sortList = new ArrayList<>();
                int count = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    count++;
                    Integer lineNum = new Integer(line);
                    if (lineNum != null) {
                        sortList.add(lineNum);
                    }
                    if (count % splitCount == 0) {
                        cpuTask.submit(new SplitSortTask(sortList, count / splitCount, ioTask, latch));
                        sortList = new ArrayList<>();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    static class SplitSortTask implements Runnable {

        private String taskName;
        private int tmpFileNum;
        private List<Integer> sortList;
        private ExecutorService ioTask;
        private CountDownLatch latch;

        SplitSortTask(List<Integer> sortList, int tmpFileNum, ExecutorService ioTask, CountDownLatch latch) {
            this.sortList = sortList;
            this.tmpFileNum = tmpFileNum;
            this.taskName = "sortfile-" + tmpFileNum;
            this.ioTask = ioTask;
            this.latch = latch;
        }

        @Override
        public void run() {
            if (!sortList.isEmpty()) {
                Collections.sort(sortList);
                ioTask.submit(new SplitWriteTask(sortList, midFilePath + midFilePrefix + tmpFileNum + ".txt" , latch));
            }
        }
    }

    static class SplitWriteTask implements Runnable {

        private List<Integer> sortList;
        private String tmpFilename;
        private CountDownLatch latch;

        SplitWriteTask(List<Integer> sortList, String tmpFilename, CountDownLatch latch) {
            this.sortList = sortList;
            this.tmpFilename = tmpFilename;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(new File(tmpFilename)), "utf-8"));
                for (Integer i : sortList) {
                    bufferedWriter.write(i.toString());
                    bufferedWriter.newLine();
                }

                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    /**
     * 第二阶段，多线程协同的 统一数据结构类，各自队列集合，单例类
     */
    static class ReadSortWriteQueue {

        private volatile static ReadSortWriteQueue instance;

        public static ReadSortWriteQueue getInstance() {
            if (instance == null) {
                synchronized (ReadSortWriteQueue.class) {
                    if (instance == null) {
                        instance = new ReadSortWriteQueue();
                        instance.init();
                    }
                }
            }
            return instance;
        }

        private void init() {
            midFileMap = new ConcurrentHashMap(totalCount / splitCount);
            sortList = new ArrayList<>(totalCount / splitCount);
            writeQueue = new ConcurrentLinkedQueue();
            valueComparator = new ValueComparator();
        }

        /**
         * 中间文件的 读取队列Map,
         * Key      是 中间文件对象
         * Value    是 sortList 里是否有该中间文件的最小值参与排序
         */
        private ConcurrentHashMap<MidFileCur, Boolean> midFileMap;

        /**
         * 归并排序集合类，每次只能排序一次输出一个数，所以单线程处理即可
         */
        private List<SortEntry> sortList;

        /**
         * 排序输出队列，往output.txt输出的缓存
         */
        private ConcurrentLinkedQueue<Integer> writeQueue;

        /**
         * 排序器
         */
        private ValueComparator valueComparator;


        public boolean addWQ(Integer e) {
            if (writeQueue != null) {
                writeQueue.add(e);
                return true;
            } else {    // 增加boolean型 返回结果，方便控制写入缓冲队列的大小
                return false;
            }
        }

        public Integer pollWQ() {
            if (writeQueue != null) {
                return writeQueue.poll();
            } else {
                return null;
            }
        }

        /**
         * readSortQueueMap 的keySet 是所有的中间文件
         *      初始化时，全部添加进来，
         *      处理完一个文件则移除一个key，全部移除后，则readTask停止。
         *
         * @return
         */
        public boolean isReadFinished() {
            return midFileMap.keySet().isEmpty();
        }

        private volatile boolean isSortFinished = false;

        public boolean isSortFinished() {
            return isSortFinished;
        }

        public boolean isWriteFinished() {
            return isSortFinished() & writeQueue.isEmpty();
        }

        /**
         * 中间文件 的最小值，是否参与排序
         * false    没参与（初始化时需设定为false）
         * true     参与  (null 的情况是排序完成)
         *
         * @param midFile
         * @return
         */
        private boolean isMidFileSort(MidFileCur midFile) {
            Boolean status = midFileMap.get(midFile);
            return status == null ? true : status;
        }

        private void setMidFileSortStatus(MidFileCur midFile, boolean sortStatus) {
            midFileMap.put(midFile, Boolean.valueOf(sortStatus));
        }

        public void initSortList() {
            for (MidFileCur loopFile : midFileMap.keySet()) {
                // 遍历中间文件，如果没参与，则从ReadQ里 poll 一个数进到 sortList
                // 如果poll的数为null，要么readQ还没准备好，要么该文件已经处理完成
                while (!isMidFileSort(loopFile)) {
                    if (!loopFile.isReadQueueFinished) {
                        Integer nextMin = loopFile.getNextNum();
                        if (nextMin != null) {
                            sortList.add(new SortEntry(loopFile, nextMin));
                            setMidFileSortStatus(loopFile, true);
                        } else {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        setMidFileSortStatus(loopFile, true);
                    }
                }
            }
        }

        /**
         * 归并排序
         * @return
         */
        public SortEntry sort() {
            if (sortList.isEmpty()) {
                return null;
            }
            Collections.sort(sortList, valueComparator);
            return sortList.get(0);
        }

        public void reloadSortList(MidFileCur midFile, Integer value) {
            if (sortList != null && !sortList.isEmpty()) {
                sortList.remove(0);
            }

            if (value != null && midFile != null) {
                sortList.add(new SortEntry(midFile, value));
            }
        }

        public void removeSortList(MidFileCur midFile) {
            Iterator<SortEntry> iterator = sortList.iterator();
            while (iterator.hasNext()) {
                SortEntry sortEntry = iterator.next();
                if (sortEntry != null && sortEntry.getKey() == midFile) {
                    iterator.remove();
                }
            }
        }

        public boolean isSortReady() {
            for (MidFileCur midFile : midFileMap.keySet()) {
                if (!isMidFileSort(midFile)) {
                    return false;
                }
            }
            return true;
        }

        public KeySetView<MidFileCur, Boolean> getReadLoopKeySet() {
            return midFileMap.keySet();
        }

        class SortEntry implements Entry{
            MidFileCur midFile;
            Integer value;

            SortEntry(MidFileCur midFile, Integer value) {
                this.midFile = midFile;
                this.value = value;
            }

            @Override
            public MidFileCur getKey() {
                return midFile;
            }

            @Override
            public Integer getValue() {
                return value;
            }

            @Override
            public Object setValue(Object value) {
                this.value = (Integer)value;
                return value;
            }
        }

        /**
         * 在统一数据结构内，封装一个排序对象的比较器
         */
        class ValueComparator implements Comparator<SortEntry> {

            @Override
            public int compare(SortEntry o1, SortEntry o2) {
                return o1.value - o2.value;
            }
        }
    }

    /**
     * 第二阶段的 归并任务类
     */

    static class MergeReadTask implements Runnable {

        private ReadSortWriteQueue readSortWriteQueue;

        public MergeReadTask(ReadSortWriteQueue readSortWriteQueue) {
            this.readSortWriteQueue = readSortWriteQueue;
            init();
            readSortWriteQueue.initSortList();
        }

        private void init() {

            // 打开中间文件所在的目录
            File file = new File(midFilePath);
            if (file.isDirectory()) {
                // 处理已经分隔好，并且排好序的文件
                // 把所有的文件句柄初始化成MidFile对象，为每个文件初始化一个队列
                for (File tmp : file.listFiles()) {
                    // 过滤文件前缀
                    if (!tmp.getName().startsWith(midFilePrefix)) {
                        continue;
                    }

                    MidFileCur midFile = new MidFileCur(tmp);
                    readSortWriteQueue.setMidFileSortStatus(midFile, false);
                }
            }
        }

        @Override
        public void run() {

            //System.out.println("MergeReadTask.run()");

            try {
                while (!readSortWriteQueue.isReadFinished()) {

                    for (MidFileCur loopFile : readSortWriteQueue.getReadLoopKeySet()) {
                        if (loopFile.isReadQueueFinished) {
                            readSortWriteQueue.midFileMap.remove(loopFile);
                            continue;
                        }
                        loopFile.reloadQ();
                    }

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            //System.out.println("MergeReadTask finished.");

        }
    }

    static class MergeSortTask implements Runnable {

        private ReadSortWriteQueue readSortWriteQueue;

        public MergeSortTask(ReadSortWriteQueue readSortWriteQueue) {
            this.readSortWriteQueue = readSortWriteQueue;
        }

        @Override
        public void run() {

            //System.out.println("MergeSortTask.run()");

            try {

                while (!readSortWriteQueue.isSortFinished()) {
                    if (readSortWriteQueue.isSortReady()) {
                        //System.out.println("MergeSortTask begin to sort");
                        SortEntry min = readSortWriteQueue.sort();
                        if (min != null) {
                            readSortWriteQueue.addWQ(min.getValue());
                            reloadMinValue(min.getKey());
                        } else {
                            readSortWriteQueue.isSortFinished = true;
                        }
                    } else {
                        try {
                            Thread.sleep(1);
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            //System.out.println("MergeSortTask finished.");
        }

        /**
         * 从SortList头部输出一个数，然后从对应的文件队列补充一个数 添加到 SortList
         * @param minFile
         */
        private void reloadMinValue(MidFileCur minFile) {
            if (minFile == null) {
                return;
            }

            // 阻塞排序任务，如果文件队列为空，则同步从文件句柄读
            while (!minFile.isReadQueueFinished) {
                Integer nextMin = minFile.pollQ();
                if (nextMin == null) {
                    minFile.reloadQ();
                } else {
                    readSortWriteQueue.reloadSortList(minFile, nextMin);
                    return;
                }
            }
            // 要么读到有效数、要么该文件处理完成，不再参与排序
            readSortWriteQueue.removeSortList(minFile);
        }

    }

    static class MergeWriteTask implements Runnable {

        private ReadSortWriteQueue readSortWriteQueue;
        private BufferedWriter bufferedWriter;
        private CountDownLatch latch;

        public MergeWriteTask(ReadSortWriteQueue readSortWriteQueue, CountDownLatch latchRes) {
            this.readSortWriteQueue = readSortWriteQueue;
            this.latch = latchRes;
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outputFile)), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            //System.out.println("MergeWriteTask.run()");

            try {
                while (!readSortWriteQueue.isWriteFinished()) {
                    Integer tmp = readSortWriteQueue.pollWQ();
                    if (tmp != null) {
                        try {
                            bufferedWriter.write(tmp.toString());
                            bufferedWriter.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Throwable e) {
                e.printStackTrace();
            }
            latch.countDown();
            //System.out.println("MergeWriteTask finished.");
        }
    }

}
