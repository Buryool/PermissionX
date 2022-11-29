package com.permissionx.wanghongbodev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

typealias PermissionCallback = (Boolean, List<String>) -> Unit

/**
 * 库的具体实现类
 * @property callback Function2<Boolean, List<String>, Unit>?
 */
class InvisibleFragment: Fragment() {
    // 定义一个回调函数作为运行时权限申请结果的回调通知方式（函数式API类型）
    private var callback: PermissionCallback? = null

    /**
     *
     * @param cb Function2<Boolean, List<String>, Unit> 和callback回调函数变量类型相同的函数式API类型参数
     * @param permission Array<out String> 要申请的权限组成的列表
     */
    fun requestNow(cb: PermissionCallback, vararg permission: String) {
        // 将外面传入的函数式API赋值给callback
        callback = cb
        // 申请运行时权限
        requestPermissions(permission, 1)
    }

    // 授权完后回调到这里
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            // 所有被用户拒绝的权限
            val deniedList = ArrayList<String>()
            // 遍历返回的授权结果，将被拒绝的权限放入deniedList
            for ((index, result) in grantResults.withIndex()){
                if (result != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[index])
                }
            }
            // 标记是否所有请求的权限全部申请成功
            val allGranted = deniedList.isEmpty()
            callback?.let {
                // 回调callback，将申请结果作为参数一起返回
                it(allGranted, deniedList)
            }
        }
    }
}