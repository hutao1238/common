package com.lbb.common.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.gyf.barlibrary.ImmersionBar
import com.lbb.common.R
import com.lbb.common.app.App
import com.lbb.common.contract.BasePresenter
import com.lbb.common.utils.MsgUtil
import com.lbb.common.utils.PerfectClickListener
import com.trello.rxlifecycle2.components.support.RxFragment
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by 胡涛.
 */
abstract class RootFragment<T, M : BasePresenter<T>> : RxFragment(), AnkoLogger {
    @Inject
    lateinit var mPresenter: M

    @Inject
    lateinit var application: App

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setContent()
        return baseView
    }

    lateinit var actualView: View
    private lateinit var baseView: View
    var llProgressBar_fragment: LinearLayout? = null
    private lateinit var error: View
    private lateinit var progressBar: ProgressBar
    protected lateinit var titleBar: LinearLayout

    private fun setContent() {
        actualView = View.inflate(activity, initView(), null)
        baseView = View.inflate(activity, R.layout.activity_base_fragment, null)

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        actualView.layoutParams = params
        val mContainer = baseView.findViewById<FrameLayout>(R.id.fl_base_container)
        mContainer.addView(actualView)
        llProgressBar_fragment = baseView.findViewById<LinearLayout>(R.id.ll_base_progress)
        error = baseView.findViewById<LinearLayout>(R.id.tv_base_error)
        progressBar = baseView.findViewById<ProgressBar>(R.id.pb_base_progress)

        titleBar = baseView.findViewById<LinearLayout>(R.id.ll_title_container)
        error.setOnClickListener(object : PerfectClickListener() {
            override fun onNoDoubleClick(v: View) {
                showPageLoading()
                onRefresh()
            }
        })
        showPageLoading()
    }

    protected var isPrepared: Boolean = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        initTitleOrIntent()
        initInject()
        initListener()
        initData()
        getData()
    }

    protected fun showLoading(){
        llProgressBar_fragment?.visibility = View.VISIBLE
    }

    protected fun hideLoading(){
        llProgressBar_fragment?.visibility = View.GONE
    }

    abstract fun initView(): Int
    abstract fun initInject()
    abstract fun initTitleOrIntent()
    abstract fun initListener()
    abstract fun initData()
    abstract fun getData()
    abstract fun initColor(): Int

    protected lateinit var shareTop: TextView
    protected lateinit var shareTopIv: ImageView
    protected lateinit var titleTop: TextView
    protected lateinit var backTop: ImageView
    protected lateinit var topBg: View
    protected lateinit var rlBg: RelativeLayout

    private fun initTitle() {
        shareTop = baseView.findViewById<TextView>(R.id.tv_title_share)
        shareTopIv = baseView.findViewById<ImageView>(R.id.iv_title_share)
        titleTop = baseView.findViewById<TextView>(R.id.tv_title_name)
        backTop = baseView.findViewById<ImageView>(R.id.iv_title_back)
        topBg = baseView.findViewById<View>(R.id.top_title_bg)
        rlBg = baseView.findViewById<RelativeLayout>(R.id.rl_title_bg)
        topBg.setBackgroundColor(ContextCompat.getColor(context,initColor()))
        rlBg.setBackgroundColor(ContextCompat.getColor(context,initColor()))
        titleBar.visibility = View.GONE
        shareTop.visibility = View.GONE
        shareTopIv.visibility = View.GONE
        backTop.visibility = View.GONE
    }
    protected fun showPageLoading() {
        if (llProgressBar_fragment?.visibility != View.VISIBLE) {
            llProgressBar_fragment?.visibility = View.VISIBLE
        }
        if (actualView.visibility != View.GONE) {
            actualView.visibility = View.GONE
        }
        if (error.visibility != View.GONE) {
            error.visibility = View.GONE
        }
    }

    protected fun showContentView() {
        if (llProgressBar_fragment?.visibility != View.GONE) {
            llProgressBar_fragment?.visibility = View.GONE
        }

        if (error.visibility != View.GONE) {
            error.visibility = View.GONE
        }
        if (actualView.visibility != View.VISIBLE) {
            actualView.visibility = View.VISIBLE
        }
    }

    protected fun showError() {
        if (llProgressBar_fragment?.visibility != View.GONE) {
            llProgressBar_fragment?.visibility = View.GONE
        }
        if (error.visibility != View.VISIBLE) {
            error.visibility = View.VISIBLE
        }
        if (actualView.visibility != View.GONE) {
            actualView.visibility = View.GONE
        }
    }

    fun showMsg(msg: String) {
        MsgUtil.showMsg(application, msg)
    }

    /**
     * 失败后点击刷新
     */
    protected fun onRefresh() {
        getData()
    }

}