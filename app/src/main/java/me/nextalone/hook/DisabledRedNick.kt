/*
 * QNotified - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2021 dmca@ioctl.cc
 * https://github.com/ferredoxin/QNotified
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as published
 * by ferredoxin.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/ferredoxin/QNotified/blob/master/LICENSE.md>.
 */
package me.nextalone.hook

import android.widget.LinearLayout
import android.widget.RelativeLayout
import ltd.nextalone.util.*
import me.singleneuron.qn_kernel.data.requireMinQQVersion
import me.singleneuron.util.QQVersion
import nil.nadph.qnotified.base.annotation.FunctionEntry
import nil.nadph.qnotified.hook.CommonDelayableHook
import nil.nadph.qnotified.util.Utils

@FunctionEntry
object DisabledRedNick : CommonDelayableHook("na_disable_red_nick_kt") {

    override fun initOnce(): Boolean {
        return try {
            if (!isSimpleUi) {
                "Lcom/tencent/mobileqq/activity/aio/core/FriendChatPie;->aP()V".method.hookBefore(this) {
                    val navAIO = it.thisObject.get("a", "com.tencent.mobileqq.widget.navbar.NavBarAIO".clazz) as RelativeLayout
                    val linearLayout = navAIO.findHostViewById<LinearLayout>("e89")
                    linearLayout?.hide()
                    it.result = null
                }
            }
            true
        } catch (t: Throwable) {
            Utils.log(t)
            false
        }
    }

    override fun isValid() = requireMinQQVersion(QQVersion.QQ_8_5_5)
}
