package io.tiklab.kaelthas.common.util;

import io.tiklab.core.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 效验地址，文件操作
 */

public class ConversionDateUtil {

    /**
     * 返回系统时间
     *
     * @param type 时间类型 1.(yyyy-MM-dd HH:mm:ss) 2.(yyyy-MM-dd) 3.(HH:mm:ss) 4.([format]) 5.(HH:mm)
     * @return 时间
     */
    public static String date(int type) {
        switch (type) {
            case 2 -> {
                return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            }
            case 3 -> {
                return new SimpleDateFormat("HH:mm:ss").format(new Date());
            }
            case 4 -> {
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                return "[" + format + "]" + "  ";
            }
            case 5 -> {
                return new SimpleDateFormat("HH:mm").format(new Date());
            }
            case 6 -> {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
            }
            default -> {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            }
        }
    }

    /**
     * 获取当前时间的指定的时间 如：获取前一个月，一天，一年，或后一天，一年等
     *
     * @param field  Calendar.MONTH 月 ；Calendar.DATE 天；Calendar.YEAR 年，等
     * @param number 1 往后 ，-1 往前
     * @return 时间
     */
    public static Date findDate(int field, int number) {
        //获取当前日期
        Date date = new Date();
        //创建Calendar实例
        Calendar cal = Calendar.getInstance();
        //设置当前时间
        cal.setTime(date);
        //在当前时间基础上减一月
        // cal.add(Calendar.MONTH,-1);
        // 同理增加一天的方法：
        // cal.add(Calendar.DATE, 1);
        cal.add(field, number);
        return cal.getTime();
    }

    /**
     * 根据当前时间获取前几个小时,分钟或者后几个小时,分钟的时间
     *
     * @param direction 方向(0代表当前时间之前几个小时,1代表当前时间之后几个小时,2代表当前时间之前几分钟之后,3代表当前时间之后几分钟)
     * @param number    大小,代表是几个时间
     * @param date date为空的话默认为当前时间
     */
    public static String findLocalDateTime(int direction, int number, String date) {
        if (StringUtils.isBlank(date)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return switch (direction) {
                //向前几小时
                case 0 -> dateTimeFormatter.format(now.minusHours(number));
                //向后几小时
                case 1 -> dateTimeFormatter.format(now.plusHours(number));
                //几分钟之前
                case 2 -> dateTimeFormatter.format(now.minusMinutes(number));
                //几分钟之后
                case 3 -> dateTimeFormatter.format(now.plusMinutes(number));
                default -> dateTimeFormatter.format(now);
            };
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime parse = LocalDateTime.parse(date, dateTimeFormatter);
            return switch (direction) {
                //向前几小时
                case 0 -> dateTimeFormatter.format(parse.minusHours(number));
                //向后几小时
                case 1 -> dateTimeFormatter.format(parse.plusHours(number));
                //几分钟之前
                case 2 -> dateTimeFormatter.format(parse.minusMinutes(number));
                //几分钟之后
                case 3 -> dateTimeFormatter.format(parse.plusMinutes(number));
                default -> dateTimeFormatter.format(parse);
            };
        }
    }

    //判断是否是1分钟,5分钟,15分钟的整除
    public static boolean isDivisible(String dateTimeString, int minuteNum) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        int timeInSeconds = dateTime.getMinute() * 60 + dateTime.getSecond();
        return timeInSeconds % (minuteNum * 60) == 0;
    }

    //计算两个时间中间的时间点
    public static List<String> splitTime(String beginTime, String endTime,int type) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime beginLocalTime = LocalDateTime.parse(beginTime, dateTimeFormatter);
        LocalDateTime endLocalTime = LocalDateTime.parse(endTime, dateTimeFormatter);
        long minutes = Duration.between(beginLocalTime, endLocalTime).toMinutes();
        if (type == 1) {
            //多少个秒
            long seconds = Duration.between(beginLocalTime, endLocalTime).toSeconds();
            //多少个秒的段数
            int secondCount = (int) Math.ceil((double) seconds);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < secondCount; i++) {
                list.add(dateTimeFormatter.format(endLocalTime));
                endLocalTime = endLocalTime.minusSeconds(1);
            }
            Collections.reverse(list);
            return list;
        }  else if (type == 2) {
            //多少个秒
            long minutes1 = Duration.between(beginLocalTime, endLocalTime).toMinutes();
            //多少个秒的段数
            int secondCount = (int) Math.ceil((double) minutes1);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < secondCount; i++) {
                list.add(dateTimeFormatter.format(endLocalTime));
                endLocalTime = endLocalTime.minusMinutes(1);
            }
            list.add(beginTime);
            Collections.reverse(list);
            return list;
        }  else if (type == 3) {
            long minutesCount = Duration.between(beginLocalTime, endLocalTime).toMinutes();
            //多少个秒的段数
            int secondCount = (int) Math.ceil((double) minutesCount / 5);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < secondCount; i++) {
                list.add(dateTimeFormatter.format(endLocalTime));
                endLocalTime = endLocalTime.minusMinutes(5);
            }
            list.add(beginTime);
            Collections.reverse(list);
            return list;
        } else  {
            long minutesCount = Duration.between(beginLocalTime, endLocalTime).toMinutes();
            //多少个秒的段数
            int secondCount = (int) Math.ceil((double) minutesCount / 15);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < secondCount; i++) {
                list.add(dateTimeFormatter.format(endLocalTime));
                endLocalTime = endLocalTime.minusMinutes(15);
            }
            list.add(beginTime);
            Collections.reverse(list);
            return list;
        }
    }

    /**
     * 字符串转换成时间
     *
     * @param time 时间字符串
     * @return 时间
     */
    public static Date StringChengeDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date targetTime;
        try {
            targetTime = sdf.parse(time);
        } catch (ParseException e) {
            throw new ApplicationException("时间转换失败，不是yyyy-MM-dd HH:mm:ss格式:" + time);
        }
        return targetTime;
    }

    /**
     * 获取指定时间与现在时间是否相差在指定天数内
     *
     * @param targetTime 指定时间
     * @param dayNumber  天数
     * @return 不为空则代表在相差时间内
     */
    public static String findDateTime(Date targetTime, Integer dayNumber) {

        Date currentDate = new Date();

        // 将Date类型转换为Calendar类型
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(targetTime);

        // 计算时间差
        long diffMillis = currentDate.getTime() - targetCalendar.getTimeInMillis();
        long diffSeconds = diffMillis / 1000;
        long diffMinutes = diffSeconds / 60;
        long diffHours = diffMinutes / 60;
        long diffDays = diffHours / 24;

        int day = (int) diffDays;
        int hours = (int) diffHours % 24;
        int minutes = (int) diffMinutes % 60;
        int seconds = (int) diffSeconds % 60;

        String time = "";

        if (day > dayNumber && dayNumber != 0) {
            return null;
        }

        if (seconds != 0) {
            time = seconds + " 秒";
        }

        if (minutes != 0) {
            time = minutes + " 分 " + time;
        }

        if (hours != 0) {
            if (minutes != 0) {
                time = hours + " 小时 " + minutes + " 分";
            } else {
                time = hours + " 小时";
            }
        }

        if (day != 0) {
            if (day == 1 && hours == 0) {
                return (hours + 24) + " 小时 " + minutes + " 分前 ";
            }

            if (day > 365) {
                int year = day / 365;
                int i = day - year * 365;
                time = year + " 年 " + i + " 天 ";
            } else {
                time = day + " 天 ";
            }

            if (hours != 0) {
                time = time + hours + " 小时";
            }
        }

        if (time.isEmpty()) {
            return null;
        }
        return time + "前";

    }

    //时间转换成时分秒
    public static String formatDateTime(long time) {
        String DateTimes;
        long days = time / (60 * 60 * 24);
        long hours = (time % (60 * 60 * 24)) / (60 * 60);
        long minutes = (time % (60 * 60)) / 60;
        long seconds = time % 60;
        if (days > 0) {
            DateTimes = days + " 天" + hours + " 时" + minutes + " 分" + seconds + " 秒";
        } else if (hours > 0) {
            DateTimes = hours + " 时" + minutes + " 分" + seconds + " 秒";
        } else if (minutes > 0) {
            DateTimes = minutes + " 分" + seconds + " 秒";
        } else {
            DateTimes = seconds + " 秒";
        }
        return DateTimes;
    }

    //根据字符串时间计算出与当前时间相差的年月日时分秒
    public static String formatToDateTime(String time) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse(time, dateTimeFormatter);
        long epochMilli1 = parse.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();

        long now = System.currentTimeMillis();

        long second = (now - epochMilli1) / 1000;

        String DateTimes;
        long year = second / (60 * 60 * 24 * 365);
        long month = (second % (60 * 60 * 24 * 365)) / (60 * 60 * 24 * 30);
        long days = (second % (60 * 60 * 24 * 30)) / (60 * 60 * 24);
        long hours = (second % (60 * 60 * 24)) / (60 * 60);
        long minutes = (second % (60 * 60)) / 60;
        long seconds = second % 60;

        if (year > 0) {
            DateTimes = year + "年" + month + "月" + days + "日" + hours + "时" + minutes + "分" + seconds + "秒";
        } else if (month > 0) {
            DateTimes = month + "月" + days + "日" + hours + "时" + minutes + "分" + seconds + "秒";
        } else if (days > 0) {
            DateTimes = days + "天" + hours + "时" + minutes + "分" + seconds + "秒";
        } else if (hours > 0) {
            DateTimes = hours + "时" + minutes + "分" + seconds + "秒";
        } else if (minutes > 0) {
            DateTimes = minutes + "分" + seconds + "秒";
        } else {
            DateTimes = seconds + "秒";
        }
        return DateTimes;
    }

    /**
     * 返回今天星期几
     *
     * @return 1: 周一 7:周天
     */
    public static int week() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (i == 0) {
            return 7;
        }
        return i;
    }

    /**
     * 效验URl地址
     *
     * @param url url
     * @return 效验成功或失败
     */
    public static boolean validURL(String url) {
        String valid = "^(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?";
        return Pattern.matches(valid, url);
    }

    /**
     * 系统类型
     *
     * @return 1.windows 2.其他
     */
    public static int findSystemType() {
        String property = System.getProperty("os.name");
        if (property.contains("Windows")) {
            return 1;
        } else if (property.contains("mac")) {
            return 3;
        } else {
            return 2;
        }
    }


    /**
     * 执行cmd命令
     *
     * @param path  执行文件夹
     * @param order 执行命令
     * @return 执行信息
     * @throws IOException 调取命令行失败
     */
    public static Process process(String path, String order) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        String[] cmd;
        if (findSystemType() == 1) {
            if (!ConversionDateUtil.isNoNull(path)) {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", order);
                process = processBuilder.start();
                // cmd = new String[] { "cmd.exe", "/c", " " + order };
                // process = runtime.exec(cmd);
            } else {
                cmd = new String[]{"cmd.exe", "/c", " " + order};
                process = runtime.exec(cmd, null, new File(path));
            }
        } else if (findSystemType() == 2) {
            if (!ConversionDateUtil.isNoNull(path)) {
                cmd = new String[]{"/bin/sh", "-c", " source /etc/profile;" + order};
                process = runtime.exec(cmd);
            } else {
                cmd = new String[]{"/bin/sh", "-c", "cd " + path + ";" + " source /etc/profile;" + order};
                process = runtime.exec(cmd, null, new File(path));
            }
        } else {
            if (!ConversionDateUtil.isNoNull(path)) {
                cmd = new String[]{"/bin/zsh", "-c", order};
                process = runtime.exec(cmd);
            } else {
                cmd = new String[]{"/bin/zsh", "-c", "cd " + path + ";" + " source /etc/profile;" + order};
                process = runtime.exec(cmd, null, new File(path));
            }
        }
        return process;
    }

    /**
     * 返回命令集合
     *
     * @param order 命令
     * @return 命令集合
     */
    public static List<String> execOrder(String order) {
        if (!isNoNull(order)) {
            return Collections.emptyList();
        }
        String[] split = order.split("\n");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            if (!isNoNull(s)) {
                continue;
            }
            if (s.contains("#")) {
                int i = s.indexOf("#");
                if (i != -1 && i != 0) {
                    String[] strings = s.split("#");
                    String string = strings[0];
                    list.add(string);
                }
            } else {
                list.add(s);
            }
        }
        return list;
    }

    /**
     * 判断字符串是否为空
     *
     * @param s 字符串
     * @return true:不为空 false:空
     */
    public static boolean isNoNull(String s) {
        if (s == null) {
            return false;
        }
        if (s.equals(" ")) {
            return false;
        }
        if (s.equals("\n")) {
            return false;
        }
        if (s.equals("null")) {
            return false;
        }
        return !s.isEmpty();
    }

    /**
     * 格式化输出流
     *
     * @param inputStream 流
     * @param encode      GBK,US-ASCII,ISO-8859-1,ISO-8859-1,UTF-16BE ,UTF-16LE, UTF-16,UTF-8
     * @return 输出流
     */
    public static InputStreamReader encode(InputStream inputStream, String encode) {
        if (inputStream == null) {
            return null;
        }

        if (encode != null) {
            return new InputStreamReader(inputStream, Charset.forName(encode));
        }
        if (findSystemType() == 1) {
            return new InputStreamReader(inputStream, Charset.forName("GBK"));
        } else {
            return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }
    }

    /**
     * 效验地址是否存在配置文件
     *
     * @param fileAddress 文件地址
     * @param type        文件类型
     *                    // * @return 匹配状态  1.不是个目录或不存在这个文件夹  2. 空目录找不到可执行文件 0. 匹配成功
     */
    public static void validFile(String fileAddress, String type) throws ApplicationException {
        File file = new File(fileAddress);

        //不存在这个目录
        if (!file.exists()) {
            throw new ApplicationException("git可执行程序地址错误，找不到 " + fileAddress + " 这个目录。");
        }
        //不是个目录
        if (!file.isDirectory()) {
            throw new ApplicationException(fileAddress + "不是个目录。");
        }
        //不存在可执行文件
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            throw new ApplicationException("在" + fileAddress + "找不到可执行文件。");
        }

        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            if (listFile.isDirectory()) {
                continue;
            }
            String name = listFile.getName();
            switch (type) {
                case "git", "gitee", "github", "gitlab", "xcode" -> {
                    if ("git".equals(name) || "git.exe".equals(name)) {
                        return;
                    }
                }
                case "svn" -> {
                    if ("svn".equals(name) || "svn.exe".equals(name)) {
                        return;
                    }
                }
                case "maven" -> {
                    if ("mvn".equals(name)) {
                        return;
                    }
                }
                case "nodejs" -> {
                    if ("npm".equals(name)) {
                        return;
                    }
                }
            }
        }
    }


    /**
     * 生成随机字符串（1~33位）
     *
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomString(Integer length) {
        String chars = "abcdefghijklmnopqrstuvwxyz123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}

