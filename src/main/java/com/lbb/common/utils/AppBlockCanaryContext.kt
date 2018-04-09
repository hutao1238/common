package com.lbb.common.utils

import com.github.moduth.blockcanary.BlockCanaryContext
import java.io.File
import java.util.*

/**
 * Created by 胡涛.
 */
class AppBlockCanaryContext: BlockCanaryContext() {
    /**
     * Implement in your project.
     *
     * @return Qualifier which can specify this installation, like version + flavor.
     */
    fun provideQualifier(): String = "unknown"

    /**
     * Implement in your project.
     *
     * @return user id
     */
    fun provideUid(): String = "uid"

    /**
     * Network type
     *
     * @return [String] like 2G, 3G, 4G, wifi, etc.
     */
    fun provideNetworkType(): String = "unknown"

    /**
     * Config monitor duration, after this time BlockCanary will stop, use
     * with `BlockCanary`'s isMonitorDurationEnd
     *
     * @return monitor last duration (in hour)
     */
    fun provideMonitorDuration(): Int = -1

    /**
     * Config block threshold (in millis), dispatch over this duration is regarded as a BLOCK. You may set it
     * from performance of device.
     *
     * @return threshold in mills
     */
    private fun provideBlockThreshold(): Int = 1000

    /**
     * Thread stack dump interval, use when block happens, BlockCanary will dump on main thread
     * stack according to current sample cycle.
     *
     *
     * Because the implementation mechanism of Looper, real dump interval would be longer than
     * the period specified here (especially when cpu is busier).
     *
     *
     * @return dump interval (in millis)
     */
    fun provideDumpInterval(): Int = provideBlockThreshold()

    /**
     * Path to save log, like "/blockcanary/", will save to sdcard if can.
     *
     * @return path of log files
     */
    fun providePath(): String = "/blockcanary/"

    /**
     * If need notification to notice block.
     *
     * @return true if need, else if not need.
     */
    fun displayNotification(): Boolean = true

    /**
     * Implement in your project, bundle files into a zip file.
     *
     * @param src  files before compress
     * @param dest files compressed
     * @return true if compression is successful
     */
    fun zip(src: Array<File>, dest: File): Boolean = false

    /**
     * Implement in your project, bundled log files.
     *
     * @param zippedFile zipped file
     */
    fun upload(zippedFile: File) {
        throw UnsupportedOperationException()
    }


    /**
     * Packages that developer concern, by default it uses process name,
     * put high priority one in pre-order.
     *
     * @return null if simply concern only package with process name.
     */
    fun concernPackages(): List<String>? = null

    /**
     * Filter stack without any in concern package, used with @{code concernPackages}.
     *
     * @return true if filter, false it not.
     */
    fun filterNonConcernStack(): Boolean = false

    /**
     * Provide white list, entry in white list will not be shown in ui list.
     *
     * @return return null if you don't need white-list filter.
     */
    fun provideWhiteList(): List<String> {
        val whiteList = LinkedList<String>()
        whiteList.add("org.chromium")
        return whiteList
    }

    /**
     * Whether to delete files whose stack is in white list, used with white-list.
     *
     * @return true if delete, false it not.
     */
    fun deleteFilesInWhiteList(): Boolean = true
}