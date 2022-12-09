package com.route.chat_c36.ui.register_activity


import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.route.chat_c36.R
import com.route.chat_c36.databinding.ActivityRegisterBinding
import com.route.chat_c36.ui.home_activity.HomeActivity

class RegisterActivity : BaseActivity<RegisterViewModel,ActivityRegisterBinding>(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        viewModel.navigator = this
        subscribeToLiveData()

    }
     fun subscribeToLiveData(){
         viewModel.isLoadingLiveData.observe(this) {
           if(it){
            showLoading()
           }else {
             hideLoading()
           }
         }
         viewModel.messageLiveData.observe(this){
            val alertDialogBuilder = AlertDialog.Builder(this)
             alertDialogBuilder.setTitle("Error")
                 .setMessage(it)
                 .setPositiveButton("Ok",object :DialogInterface.OnClickListener{
                     override fun onClick(p0: DialogInterface?, p1: Int) {
                         p0!!.dismiss()
                     }
                 }).show()
         }
     }
    override fun getLayOutFile(): Int {
     return R.layout.activity_register
    }

    override fun initViewModel(): RegisterViewModel {
        return ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    override fun navigateToHome() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }
}