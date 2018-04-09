@file:Suppress("NAME_SHADOWING" , "DEPRECATED_IDENTITY_EQUALS")

package com.lbb.common.utils

import android.annotation.SuppressLint
import android.util.Log
import com.lbb.common.utils.ConstUtil.DAY
import com.lbb.common.utils.ConstUtil.HOUR
import com.lbb.common.utils.ConstUtil.MIN
import com.lbb.common.utils.ConstUtil.MSEC
import com.lbb.common.utils.ConstUtil.SEC
import com.lbb.common.utils.ConstUtil.TimeUnit
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by 胡涛.
 */
object TimeUtil {
    /**
     *
     * 在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.
     * 格式的意义如下： 日期和时间模式 <br></br>
     *
     * 日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
     * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
     * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
     * <pre>
     * HH:mm    15:44
     * h:mm a    3:44 下午
     * HH:mm z    15:44 CST
     * HH:mm Z    15:44 +0800
     * HH:mm zzzz    15:44 中国标准时间
     * HH:mm:ss    15:44:40
     * yyyy-MM-dd    2016-08-12
     * yyyy-MM-dd HH:mm    2016-08-12 15:44
     * yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     * yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     * EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     * yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     * K:mm a    3:44 下午
     * EEE, MMM d, ''yy    星期五, 八月 12, '16
     * hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     * yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     * EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     * yyMMddHHmmssZ    160812154440+0800
     * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
    </pre> *
     */
    val DEFAULT_SDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.getDefault())


    /**
     * 将时间戳转为时间字符串
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param milliseconds 毫秒时间戳
     * @return 时间字符串
     */
    fun milliseconds2String(milliseconds: Long): String {
        return milliseconds2String(milliseconds , DEFAULT_SDF)
    }

    /**
     * 将时间戳转为时间字符串
     *
     * 格式为用户自定义
     *
     * @param milliseconds 毫秒时间戳
     * @param format       时间格式
     * @return 时间字符串
     */
    fun milliseconds2String(milliseconds: Long , format: SimpleDateFormat): String {
        return format.format(Date(milliseconds))
    }

    /**
     * 将时间字符串转为时间戳
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    fun string2Milliseconds(time: String): Long {
        return string2Milliseconds(time , DEFAULT_SDF)
    }

    /**
     * 将时间字符串转为时间戳
     *
     * 格式为用户自定义
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    fun string2Milliseconds(time: String , format: SimpleDateFormat): Long {
        try {
            return format.parse(time).getTime()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return -1
    }

    /**
     * 将时间字符串转为Date类型
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @return Date类型
     */
    fun string2Date(time: String): Date {
        return string2Date(time , DEFAULT_SDF)
    }

    /**
     * 将时间字符串转为Date类型
     *
     * 格式为用户自定义
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date类型
     */
    fun string2Date(time: String , format: SimpleDateFormat): Date {
        return Date(string2Milliseconds(time , format))
    }

    /**
     * 将Date类型转为时间字符串
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time Date类型时间
     * @return 时间字符串
     */
    fun date2String(time: Date): String {
        return date2String(time , DEFAULT_SDF)
    }

    /**
     * 将Date类型转为时间字符串
     *
     * 格式为用户自定义
     *
     * @param time   Date类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    fun date2String(time: Date , format: SimpleDateFormat): String {
        return format.format(time)
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param time Date类型时间
     * @return 毫秒时间戳
     */
    fun date2Milliseconds(time: Date): Long {
        return time.getTime()
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param milliseconds 毫秒时间戳
     * @return Date类型时间
     */
    fun milliseconds2Date(milliseconds: Long): Date {
        return Date(milliseconds)
    }

    /**
     * 毫秒时间戳单位转换（单位：unit）
     *
     * @param milliseconds 毫秒时间戳
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @return unit时间戳
     */
    private fun milliseconds2Unit(milliseconds: Long , unit: TimeUnit): Long {
        return when (unit) {
            TimeUnit.MSEC -> milliseconds / MSEC
            TimeUnit.SEC -> milliseconds / SEC
            TimeUnit.MIN -> milliseconds / MIN
            TimeUnit.HOUR -> milliseconds / HOUR
            TimeUnit.DAY -> milliseconds / DAY
        }
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * time1和time2格式都为yyyy-MM-dd HH:mm:ss
     *
     * @param time0 时间字符串1
     * @param time1 时间字符串2
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @return unit时间戳
     */
    fun getIntervalTime(time0: String , time1: String , unit: TimeUnit): Long {
        return getIntervalTime(time0 , time1 , unit , DEFAULT_SDF)
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * time1和time2格式都为format
     *
     * @param time0  时间字符串1
     * @param time1  时间字符串2
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @param format 时间格式
     * @return unit时间戳
     */
    fun getIntervalTime(time0: String , time1: String , unit: TimeUnit , format: SimpleDateFormat): Long {
        return Math.abs(milliseconds2Unit(string2Milliseconds(time0 , format) - string2Milliseconds(time1 , format) , unit))
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * time1和time2都为Date类型
     *
     * @param time0 Date类型时间1
     * @param time1 Date类型时间2
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @return unit时间戳
     */
    fun getIntervalTime(time0: Date , time1: Date , unit: TimeUnit): Long {
        return Math.abs(milliseconds2Unit(date2Milliseconds(time1) - date2Milliseconds(time0) , unit))
    }

    /**
     * 获取当前时间
     *
     * @return 毫秒时间戳
     */
    fun getCurTimeMills(): Long {
        return System.currentTimeMillis()
    }

    /**
     * 获取当前时间
     *
     * 格式为yyyy-MM-dd HH:mm:ss
     *
     * @return 时间字符串
     */
    fun getCurTimeString(): String {
        return date2String(Date())
    }

    /**
     * 获取当前时间
     *
     * 格式为用户自定义
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    fun getCurTimeString(format: SimpleDateFormat): String {
        return date2String(Date() , format)
    }

    /**
     * 获取当前时间
     *
     * Date类型
     *
     * @return Date类型时间
     */
    fun getCurTimeDate(): Date {
        return Date()
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * time格式为yyyy-MM-dd HH:mm:ss
     *
     * @param time 时间字符串
     * @param unit
     *  * [TimeUnit.MSEC]:毫秒
     *  * [TimeUnit.SEC]:秒
     *  * [TimeUnit.MIN]:分
     *  * [TimeUnit.HOUR]:小时
     *  * [TimeUnit.DAY]:天
     *
     * @return unit时间戳
     */
    fun getIntervalByNow(time: String , unit: TimeUnit): Long {
        return getIntervalByNow(time , unit , DEFAULT_SDF)
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * time格式为format
     *
     * @param time   时间字符串
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @param format 时间格式
     * @return unit时间戳
     */
    fun getIntervalByNow(time: String , unit: TimeUnit , format: SimpleDateFormat): Long {
        return getIntervalTime(getCurTimeString() , time , unit , format)
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * time为Date类型
     *
     * @param time Date类型时间
     * @param unit
     *  * [TimeUnit.MSEC]: 毫秒
     *  * [TimeUnit.SEC]: 秒
     *  * [TimeUnit.MIN]: 分
     *  * [TimeUnit.HOUR]: 小时
     *  * [TimeUnit.DAY]: 天
     *
     * @return unit时间戳
     */
    fun getIntervalByNow(time: Date , unit: TimeUnit): Long {
        return getIntervalTime(getCurTimeDate() , time , unit)
    }

    /**
     * 判断闰年
     *
     * @param year 年份
     * @return `true`: 闰年<br></br>`false`: 平年
     */
    fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    @SuppressLint("SimpleDateFormat")
            /**
             * 将date转换成format格式的日期
             *
             * @param format 格式
             * @param date   日期
             * @return
             */
    fun simpleDateFormat(format: String , date: Date): String {
        var format = format
        if (format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss"
        return SimpleDateFormat(format).format(date)
    }

    //--------------------------------------------字符串转换成时间戳-----------------------------------

    /**
     * 将指定格式的日期转换成时间戳
     *
     * @param mDate
     * @return
     */
    fun Date2Timestamp(mDate: Date?): String {
        return mDate!!.time.toString().substring(0 , 10)
    }

    @SuppressLint("SimpleDateFormat")
            /**
             * 将日期字符串 按照 指定的格式 转换成 DATE
             * 转换失败时 return null;
             *
             * @param format
             * @param dates
             * @return
             */
    fun string2Date(format: String , dates: String): Date? {
        val sdr = SimpleDateFormat(format)
        var date: Date? = null
        try {
            date = sdr.parse(dates)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    /**
     * 将 yyyy年MM月dd日 转换成 时间戳
     *
     * @param format
     * @param datess
     * @return
     */
    fun string2Timestamp(format: String , datess: String): String {
        val date = string2Date(format , datess)
        return Date2Timestamp(date)
    }
    //===========================================字符串转换成时间戳====================================

    /**
     * 获取当前日期时间 / 得到今天的日期
     * str yyyyMMddhhMMss 之类的
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateTime(format: String): String {
        return simpleDateFormat(format , Date())
    }

    /**
     * 时间戳  转换成 指定格式的日期
     * 如果format为空，则默认格式为
     *
     * @param times  时间戳
     * @param format 日期格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun getDate(times: String , format: String): String {
        return simpleDateFormat(format , Date(times.toInt() * 1000L))
    }

    /**
     * 得到昨天的日期
     *
     * @param format 日期格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    fun getYestoryDate(format: String): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE , -1)
        return simpleDateFormat(format , calendar.getTime())
    }

    @SuppressLint("SimpleDateFormat")
            /**
             * 视频时间 转换成 "mm:ss"
             *
             * @param milliseconds
             * @return
             */
    fun formatTime(milliseconds: Long): String {
        val format = "mm:ss"
        val sdf = SimpleDateFormat(format)
        sdf.timeZone = TimeZone.getTimeZone("GMT+0")
        return sdf.format(milliseconds)
    }

    @SuppressLint("SimpleDateFormat")
            /**
             * "mm:ss" 转换成 视频时间
             *
             * @param time
             * @return
             */
    fun formatSeconds(time: String): Long {
        val format = "mm:ss"
        val sdf = SimpleDateFormat(format)
        sdf.timeZone = TimeZone.getTimeZone("GMT+0")
        val date: Date
        var times: Long = 0
        try {
            date = sdf.parse(time)
            val l = date.getTime()
            times = l
            Log.d("时间戳" , times.toString() + "")
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return times
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    fun getDaysByYearMonth(year: Int , month: Int): Int {
        val a = Calendar.getInstance()
        a.set(Calendar.YEAR , year)
        a.set(Calendar.MONTH , month - 1)
        a.set(Calendar.DATE , 1)
        a.roll(Calendar.DATE , -1)
        return a.get(Calendar.DATE)
    }

    /**
     * 判断当前日期是星期几
     *
     * @param strDate 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常<br></br>
     */
    @SuppressLint("SimpleDateFormat")
    @Throws(Exception::class)
    fun stringForWeek(strDate: String): Int {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        c.time = format.parse(strDate)
        return if (c.get(Calendar.DAY_OF_WEEK) === Calendar.SUNDAY) {
            7
        } else {
            c.get(Calendar.DAY_OF_WEEK) - 1
        }
    }

    /**
     * 判断当前日期是星期几
     *
     * @param strDate 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常<br></br>
     */
    @Throws(Exception::class)
    fun stringForWeek(strDate: String , simpleDateFormat: SimpleDateFormat): Int {
        val c = Calendar.getInstance()
        c.time = simpleDateFormat.parse(strDate)
        return if (c.get(Calendar.DAY_OF_WEEK) === Calendar.SUNDAY) {
            7
        } else {
            c.get(Calendar.DAY_OF_WEEK) - 1
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun compareTime(date1: String , date2: String): Boolean {
        val formatter = SimpleDateFormat("yyyy年MM月dd日")
        val data1 = formatter.parse(date1)
        val data2 = formatter.parse(date2)
        return data1.time <= data2.time
    }

    @SuppressLint("SimpleDateFormat")
    fun compareCurrentTime(date1: String): Boolean {
        return compareTime(date1 , dateToString(getCurTimeDate()))
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToString(date: Date): String {
        val formatter = SimpleDateFormat("yyyy年MM月dd日")
        return formatter.format(date)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateToString2(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(date)
    }

    fun getStartDate(): String {
        return dateToString2(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getEndDate(term: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.MONTH , term.toInt())
        val endTime = calendar.time
        return format.format(endTime)
    }

    @SuppressLint("SimpleDateFormat")
            /**
             * 将时间转换成刚刚、几秒钟前、几分钟前等样式
             *
             * @param string
             * 格式为yyyy-MM-dd HH:mm:ss
             * @return
             */
    fun parseTime(string: String): String {
        val curtime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val sCurTime = dateFormat.format(curtime)
        val ns = sCurTime.split(" ".toRegex())
        val nsNYR = ns[0].split("-".toRegex())
        val nsSFM = ns[1].split(":".toRegex())
        val s: Array<String>?
        val sNYR: Array<String>?
        val sSFM: Array<String>?
        try {
            if (string.contains("-")) {
                s = string.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                sNYR = s[0].split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                sSFM = s[1].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            } else {
                s = string.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                sNYR = s[0].split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                sSFM = s[1].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
        } catch (e: Exception) {
            return string
        }

        val N = nsNYR[0].toInt() - sNYR[0].toInt()
        val Y = nsNYR[1].toInt() - sNYR[1].toInt()
        val R = nsNYR[2].toInt() - sNYR[2].toInt()
        val S = nsSFM[0].toInt() - sSFM[0].toInt()
        val F = nsSFM[1].toInt() - sSFM[1].toInt()
        val M = nsSFM[2].toInt() - sSFM[2].toInt()
        return if (N > 0) {
//            s!![0]
            "${sNYR[0]}年${sNYR[1]}月${sNYR[2]}日"
        } else {
            if (Y > 0) {
//                s!![0]
                "${sNYR[0]}年${sNYR[1]}月${sNYR[2]}日"
            } else {
                if (R === 1) {
                    return "昨天${sSFM[0]}:${sSFM[1]}"
                } else if (R >= 2) {
                    "${sNYR[0]}年${sNYR[1]}月${sNYR[2]}日"
                } else {
                    s[1]
                }
            }
        }
    }
}