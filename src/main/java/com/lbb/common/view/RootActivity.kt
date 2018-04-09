package com.lbb.common.view

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.gyf.barlibrary.ImmersionBar
import com.lbb.common.R
import com.lbb.common.app.App
import com.lbb.common.contract.BasePresenter
import com.lbb.common.utils.MsgUtil
import com.lbb.common.utils.PerfectClickListener
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject


/**
 * Created by 胡涛.
 */
abstract class RootActivity<T , M : BasePresenter<T>> : RxAppCompatActivity() , AnkoLogger {
    @Inject
    lateinit var mPresenter: M

    @Inject
    lateinit var application: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBar()
        initSoftInput()
        setContentView(initView())
        initInject()
        initTitle()
        titleTop.text = initIntentOrTitle()
        initListener()
        initData()
        getData()
    }

    abstract fun initColor(): Int

    abstract fun initInject()
    abstract fun initView(): Int
    abstract fun initIntentOrTitle(): String
    abstract fun initListener()
    abstract fun initData()
    abstract fun getData()

    private var llProgressBar: LinearLayout? = null
    private var error: LinearLayout? = null
    private var again: TextView? = null
    private var progressBar: ProgressBar? = null
    private lateinit var baseView: View
    private lateinit var actualView: View

    override fun setContentView(layoutResID: Int) {
        baseView = View.inflate(this , R.layout.activity_base , null)
        actualView = View.inflate(this , layoutResID , null)

        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT)
        actualView.layoutParams = params
        val mContainer = baseView.findViewById<FrameLayout>(R.id.fl_base_container)
        mContainer.addView(actualView)
        window.setContentView(baseView)
        llProgressBar = baseView.findViewById<LinearLayout>(R.id.ll_base_progress)
        error = baseView.findViewById<LinearLayout>(R.id.tv_base_error)
        again = baseView.findViewById<TextView>(R.id.tv_base_again)
        progressBar = baseView.findViewById<ProgressBar>(R.id.pb_base_progress)

        titleBar = baseView.findViewById<LinearLayout>(R.id.ll_title_container)
        again?.setOnClickListener(object : PerfectClickListener() {
            override fun onNoDoubleClick(v: View) {
                showPageLoading()
                onError()
            }
        })
        showPageLoading()
    }

    protected fun showLoading() {
        llProgressBar?.visibility = View.VISIBLE
    }

    protected fun hideLoading() {
        llProgressBar?.visibility = View.GONE
    }

    protected lateinit var titleBar: LinearLayout
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
        topBg.setBackgroundColor(ContextCompat.getColor(this , initColor()))
        rlBg.setBackgroundColor(ContextCompat.getColor(this , initColor()))
        backTop.setOnClickListener(object : PerfectClickListener() {
            override fun onNoDoubleClick(v: View) {
                finish()
            }
        })
        shareTop.visibility = View.GONE
        shareTopIv.visibility = View.GONE
    }

    protected fun showPageLoading() {
        if (llProgressBar?.visibility != View.VISIBLE) {
            llProgressBar?.visibility = View.VISIBLE
        }
        if (actualView.visibility != View.GONE) {
            actualView.visibility = View.GONE
        }
        if (error?.visibility != View.GONE) {
            error?.visibility = View.GONE
        }
    }

    protected fun showContentView() {
        if (llProgressBar?.visibility != View.GONE) {
            llProgressBar?.visibility = View.GONE
        }

        if (error?.visibility != View.GONE) {
            error?.visibility = View.GONE
        }
        if (actualView.visibility != View.VISIBLE) {
            actualView.visibility = View.VISIBLE
        }
    }

    protected fun showError() {
        if (llProgressBar?.visibility != View.GONE) {
            llProgressBar?.visibility = View.GONE
        }
        if (error?.visibility != View.VISIBLE) {
            error?.visibility = View.VISIBLE
        }
        if (actualView.visibility != View.GONE) {
            actualView.visibility = View.GONE
        }
    }

    /**
     * 失败后点击刷新
     */
    protected fun onError() {
        getData()
    }

    lateinit var mImmersionBar: ImmersionBar
    /**
     * 初始化沉浸式状态栏
     */
    private fun initBar() {
        mImmersionBar = ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .fixMarginAtBottom(true)
        mImmersionBar.init()
    }

    var manager: InputMethodManager? = null
    /**
     * 初始化键盘
     */
    private fun initSoftInput() {
        manager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    }

    /**
     * 点击空白处隐藏键盘
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if (currentFocus != null && currentFocus.windowToken != null) {
                manager?.hideSoftInputFromWindow(currentFocus.windowToken , InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        return super.onTouchEvent(event)
    }

    fun showMsg(msg: String) {
        MsgUtil.showMsg(application , msg)
    }

    override fun onDestroy() {
        mImmersionBar.destroy()
        super.onDestroy()
    }
}