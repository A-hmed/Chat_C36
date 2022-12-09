package com.route.chat_c36.ui.register_activity

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<VM : ViewModel, DB : ViewDataBinding> : AppCompatActivity() {
    lateinit var viewModel: VM
    lateinit var viewDataBinding: DB
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayOutFile())
        viewModel = initViewModel()
        progressDialog = ProgressDialog(this)
    }

    abstract fun getLayOutFile(): Int
    abstract fun initViewModel(): VM
    fun showError(title: String = "", message: String = "") {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0!!.dismiss()
                }
            }).show()
    }
  fun showLoading(){
      progressDialog.setMessage("Loading")
      progressDialog.show()
  }
  fun hideLoading(){
      progressDialog.dismiss()
  }
}