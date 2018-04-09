@file:Suppress("NAME_SHADOWING")

package com.lbb.common.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.lbb.common.R
import com.lbb.common.adapter.DialogMutiAdapter
import com.lbb.common.adapter.DialogSingleAdapter
import com.lbb.common.model.bean.DialogItem
import com.lbb.common.model.bean.Store
import com.zhl.cbdialog.CBDialogBuilder
import org.w3c.dom.Text
import java.lang.ref.WeakReference


/**
 * Created by 胡涛.
 */
object DialogUtil {

    var mDialog: Dialog? = null

    /**
     * 底部对话框(多选)
     */
    @SuppressLint("InflateParams")
    fun showBottomDialogMuti(context: Context , list: List<Store> , callback: (List<String>) -> Unit) {
        val mContext: WeakReference<Context> = WeakReference(context)
        if (mDialog == null)
            mDialog = Dialog(mContext.get() , R.style.my_dialog)
        val root = LayoutInflater.from(mContext.get()).inflate(R.layout.dialog_item , null) as LinearLayout
        val cancel = root.findViewById<TextView>(R.id.tv_dialog_cancel)
        val ok = root.findViewById<TextView>(R.id.tv_dialog_ok)
        val recyclerView = root.findViewById<RecyclerView>(R.id.rv_dialog_recycler)

        recyclerView.layoutManager = LinearLayoutManager(mContext.get())
        val adapter = DialogMutiAdapter(context , list)
        recyclerView.adapter = adapter

        cancel.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
        }

        ok.setOnClickListener {
            val data = adapter.getData()
            val list = arrayListOf<String>()
            if (data != null && data.isNotEmpty() && data != list) {
                data.forEach {
                    if (it.isCheck) {
                        list.add(it.id)
                    }
                }
                callback.invoke(list)
            }
            mDialog?.dismiss()
            mDialog = null
        }
        mDialog?.setContentView(root)
        val dialogWindow = mDialog?.window
        dialogWindow?.setGravity(Gravity.BOTTOM)
        val lp = dialogWindow?.attributes // 获取对话框当前的参数值
        lp?.x = 0 // 新位置X坐标
        lp?.y = -20 // 新位置Y坐标
        lp?.width = context.resources.displayMetrics.widthPixels // 宽度
        root.measure(0 , 0)
        val measuredHeight = root.measuredHeight
        val maxHeight = (context.resources.displayMetrics.heightPixels * 0.4).toInt()
        lp?.height = minOf(measuredHeight , maxHeight)
        lp?.alpha = 2f // 透明度
        dialogWindow?.attributes = lp
        mDialog?.show()
    }

    /**
     * 底部对话框(单选)
     */
    @SuppressLint("InflateParams")
    fun showBottomDialogSingle(context: Context , list: List<String> , callback: (String) -> Unit) {
        val mContext: WeakReference<Context> = WeakReference(context)
        if (mDialog == null)
            mDialog = Dialog(mContext.get() , R.style.my_dialog)
        val root = LayoutInflater.from(mContext.get()).inflate(R.layout.dialog_item , null) as LinearLayout
        val cancel = root.findViewById<TextView>(R.id.tv_dialog_cancel)
        val ok = root.findViewById<TextView>(R.id.tv_dialog_ok)
        val recyclerView = root.findViewById<RecyclerView>(R.id.rv_dialog_recycler)

        val mList = arrayListOf<DialogItem>()
        list.forEach {
            mList.add(DialogItem(false , it))
        }
        recyclerView.layoutManager = LinearLayoutManager(mContext.get())
        val adapter = DialogSingleAdapter(context , mList)
        recyclerView.adapter = adapter

        cancel.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
        }

        ok.setOnClickListener {
            val position = adapter.getPosition()
            if (position != null) {
                callback.invoke(mList[position].item)
            }
            mDialog?.dismiss()
            mDialog = null
        }
        mDialog?.setContentView(root)
        val dialogWindow = mDialog?.window
        dialogWindow?.setGravity(Gravity.BOTTOM)
        val lp = dialogWindow?.attributes // 获取对话框当前的参数值
        lp?.x = 0 // 新位置X坐标
        lp?.y = -20 // 新位置Y坐标
        lp?.width = context.resources.displayMetrics.widthPixels // 宽度
        root.measure(0 , 0)
        val measuredHeight = root.measuredHeight
        val maxHeight = (context.resources.displayMetrics.heightPixels * 0.3).toInt()
        lp?.height = minOf(measuredHeight , maxHeight)
        lp?.alpha = 2f // 透明度
        dialogWindow?.attributes = lp
        mDialog?.show()
    }

    private var dialog: Dialog? = null
    fun showLoading(context: Context , msg: String) {
        val mContext: WeakReference<Context> = WeakReference(context)
        dialog = CBDialogBuilder(mContext.get() , CBDialogBuilder.DIALOG_STYLE_PROGRESS , 0.4f)
                .showCancelButton(true)
                .setMessage(msg)
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_SLID_BOTTOM)
                .setOnProgressOutTimeListener(20000 / 5600 , { _ , textView ->
                    textView.text = "出错啦！"
                })
                .create()
        dialog?.show()
    }

    fun dismissDialog() {
        dialog?.let {
            if (it.isShowing) {
                dialog?.dismiss()
                dialog = null
            }
        }
    }

    fun showBottomDialog(context: Context , array: Array<String> , callback: (Int) -> Unit) {
        try {
            val mContext: WeakReference<Context> = WeakReference(context)
            val dialog = CBDialogBuilder(mContext.get())
                    .setTouchOutSideCancelable(true)
                    .setCustomIcon(-1)
                    .setTitle("")
                    .setTitleTextSize(0)
                    .showCancelButton(false)
                    .showConfirmButton(false)
                    .setItems(array , { dialogItemAdapter , context , cbDialogBuilder , dialog , i ->
                        callback.invoke(i)
                        dialog.dismiss()
                    })
                    .setDialoglocation(CBDialogBuilder.DIALOG_LOCATION_BOTTOM)
                    .create()
                    .show()
        } catch (e: Exception) {

        }
    }

    fun showCenterDialog(context: Context , array: Array<String> , callback: (Int) -> Unit) {
        try {
            val mContext: WeakReference<Context> = WeakReference(context)
            val dialog = CBDialogBuilder(mContext.get())
                    .setTouchOutSideCancelable(true)
                    .setCustomIcon(-1)
                    .setTitle("")
                    .setTitleTextSize(0)
                    .showCancelButton(false)
                    .showConfirmButton(false)
                    .setItems(array , { dialogItemAdapter , context , cbDialogBuilder , dialog , i ->
                        callback.invoke(i)
                        dialog.dismiss()
                    })
                    .setDialoglocation(CBDialogBuilder.MSG_LAYOUT_CENTER)
                    .create()
                    .show()
        } catch (e: Exception) {

        }
    }

    fun showNormalDialog(context: Context , title: String , msg: String , confirmButtonText: String , cancelButtonText: String , listener: CBDialogBuilder.onDialogbtnClickListener) {
        try {
            val mContext: WeakReference<Context> = WeakReference(context)
            val create = CBDialogBuilder(mContext.get())
                    .setTouchOutSideCancelable(true)
                    .setCustomIcon(-1)
                    .setTitle(title)
                    .setMessage(msg)
                    .setConfirmButtonText(confirmButtonText)
                    .setCancelButtonText(cancelButtonText)
                    .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_NORMAL)
                    .setDialoglocation(CBDialogBuilder.DIALOG_LOCATION_CENTER)
                    .setButtonClickListener(true , listener)

            if (cancelButtonText == "") {
                create.showCancelButton(false)
                        .setCancelable(false)
            } else {
                create.showCancelButton(true)
            }
            create.create()
                    .show()
        } catch (e: Exception) {

        }
    }

    fun showBottomDialog(context: Context , cameraCallback: () -> Unit , photoCallback: () -> Unit) {
        val mContext: WeakReference<Context> = WeakReference(context)
        if (mDialog == null)
            mDialog = Dialog(mContext.get() , R.style.DialogTheme)
        val root = LayoutInflater.from(mContext.get()).inflate(R.layout.dialog_bottom , null) as LinearLayout
        val camera = root.findViewById<TextView>(R.id.tv_dialog_camera)
        val photo = root.findViewById<TextView>(R.id.tv_dialog_photo)
        val cancel = root.findViewById<TextView>(R.id.tv_dialog_cancel)

        cancel.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
        }

        camera.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
            cameraCallback.invoke()
        }

        photo.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
            photoCallback.invoke()
        }

        mDialog?.setContentView(root)
        val dialogWindow = mDialog?.window
        dialogWindow?.setGravity(Gravity.BOTTOM)
        val lp = dialogWindow?.attributes                           // 获取对话框当前的参数值
        lp?.x = 0                                                   // 新位置X坐标
        lp?.y = -20                                                 // 新位置Y坐标
        root.measure(0 , 0)
        lp?.width = context.resources.displayMetrics.widthPixels - 40   // 宽度
        lp?.height = root.measuredHeight
        lp?.alpha = 2f                                              // 透明度
        dialogWindow?.attributes = lp
        mDialog?.show()
    }

    fun showPhoneDialog(context: Context , phone: String , dp: Int , callback: () -> Unit) {
        val mContext: WeakReference<Context> = WeakReference(context)
        if (mDialog == null)
            mDialog = Dialog(mContext.get() , R.style.DialogTheme)
        val root = LayoutInflater.from(mContext.get()).inflate(R.layout.dialog_phone , null) as LinearLayout
        val phoneView = root.findViewById<TextView>(R.id.tv_dialog_phone)
        val cancel = root.findViewById<TextView>(R.id.tv_dialog_cancel)
        val ok = root.findViewById<TextView>(R.id.tv_dialog_ok)
        phoneView.text = phone
        cancel.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
        }

        ok.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
            callback.invoke()
        }

        mDialog?.setContentView(root)
        val dialogWindow = mDialog?.window
        dialogWindow?.setGravity(Gravity.CENTER)
        val lp = dialogWindow?.attributes                                // 获取对话框当前的参数值
        lp?.x = 0                                                        // 新位置X坐标
        lp?.y = 0                                                        // 新位置Y坐标
        root.measure(0 , 0)
        lp?.width = context.resources.displayMetrics.widthPixels - 200   // 宽度
        lp?.height = root.measuredHeight + dp
        lp?.alpha = 2f                                                   // 透明度
        dialogWindow?.attributes = lp
        mDialog?.show()
    }


    fun showApplyDialog(context: Context , content: String) {
        val mContext: WeakReference<Context> = WeakReference(context)
        if (mDialog == null)
            mDialog = Dialog(mContext.get() , R.style.DialogTheme)
        val root = LayoutInflater.from(mContext.get()).inflate(R.layout.dialog_apply , null) as LinearLayout
        val contentView = root.findViewById<TextView>(R.id.tv_dialog_phone)
        val ok = root.findViewById<TextView>(R.id.tv_dialog_ok)
        contentView.text = content

        ok.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
        }

        mDialog?.setContentView(root)
        val dialogWindow = mDialog?.window
        dialogWindow?.setGravity(Gravity.CENTER)
        val lp = dialogWindow?.attributes                                // 获取对话框当前的参数值
        lp?.x = 0                                                        // 新位置X坐标
        lp?.y = 0                                                        // 新位置Y坐标
        root.measure(0 , 0)
        lp?.width = context.resources.displayMetrics.widthPixels - 200   // 宽度
        lp?.height = root.measuredHeight + 80
        lp?.alpha = 2f                                                   // 透明度
        dialogWindow?.attributes = lp
        mDialog?.show()
    }

    fun showCenterDialog(context: Context , title: String , phone: String , dp: Int , callback: () -> Unit) {
        val mContext: WeakReference<Context> = WeakReference(context)
        if (mDialog == null)
            mDialog = Dialog(mContext.get() , R.style.DialogTheme)
        val root = LayoutInflater.from(mContext.get()).inflate(R.layout.dialog_center , null) as LinearLayout
        val titleView = root.findViewById<TextView>(R.id.tv_dialog_camera)
        val phoneView = root.findViewById<TextView>(R.id.tv_dialog_phone)
        val cancel = root.findViewById<TextView>(R.id.tv_dialog_cancel)
        val ok = root.findViewById<TextView>(R.id.tv_dialog_ok)
        titleView.text = title
        phoneView.text = phone
        cancel.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
        }

        ok.setOnClickListener {
            mDialog?.dismiss()
            mDialog = null
            callback.invoke()
        }

        mDialog?.setContentView(root)
        val dialogWindow = mDialog?.window
        dialogWindow?.setGravity(Gravity.CENTER)
        val lp = dialogWindow?.attributes                                // 获取对话框当前的参数值
        lp?.x = 0                                                        // 新位置X坐标
        lp?.y = 0                                                        // 新位置Y坐标
        root.measure(0 , 0)
        lp?.width = context.resources.displayMetrics.widthPixels - 200   // 宽度
        lp?.height = root.measuredHeight + dp
        lp?.alpha = 2f                                                   // 透明度
        dialogWindow?.attributes = lp
        mDialog?.show()
    }
}