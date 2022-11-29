package com.permissionx.wanghongbodev

import androidx.fragment.app.FragmentActivity

/**
 * 库对外的接口类
 */
object PermissionX {
    private const val TAG = "InvisibleFragment"

    /**
     *
     * @param activity FragmentActivity FragmentActivity是AppCompatActivity的父类
     * @param permissions Array<out String> permission参数列表
     * @param callback Function2<Boolean, List<String>, Unit> 回调函数
     */
    fun request(activity: FragmentActivity, vararg permissions: String, callback: PermissionCallback){
        // 获取FragmentManager的实例
        val fragmentManager = activity.supportFragmentManager
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        // 判断传入的Activity参数中是否已经包含了指定TAG的Fragment
        // 如果已经包含就直接使用该Fragment，否则就创建一个新的InvisibleFragment实例，并将它添加到Activity中，同时指定一个TAG
        val fragment = if (existedFragment != null){
            existedFragment as InvisibleFragment
        } else {
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()
            invisibleFragment
        }
        // 调用自己写的requestNow()方法进行权限申请
        fragment.requestNow(callback, *permissions)
    }
}