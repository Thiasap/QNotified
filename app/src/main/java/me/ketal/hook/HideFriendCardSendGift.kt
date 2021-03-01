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

package me.ketal.hook

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import ltd.nextalone.util.hookAfter
import ltd.nextalone.util.method
import me.singleneuron.qn_kernel.data.requireMinQQVersion
import me.singleneuron.util.QQVersion
import nil.nadph.qnotified.base.annotation.FunctionEntry
import nil.nadph.qnotified.hook.CommonDelayableHook
import nil.nadph.qnotified.util.Utils

@FunctionEntry
object HideFriendCardSendGift : CommonDelayableHook("ketal_HideFriendProfileCardSendGift") {

    override fun isValid() = requireMinQQVersion(QQVersion.QQ_8_0_0)

    override fun initOnce() = try {
        "Lcom/tencent/mobileqq/activity/FriendProfileCardActivity;->a(Landroid/widget/LinearLayout;)V"
            .method.hookAfter(this) {
                val rootView = it.args[0] as LinearLayout
                val view = rootView[2]
                val child = (view as LinearLayout)[0]
                if (child is TextView) {
                    child.doAfterTextChanged {
                        if (!isEnabled) return@doAfterTextChanged
                        if (it.toString() == "送礼物")
                            (child.parent as LinearLayout).isVisible = false
                    }
                }
            }
        true
    } catch (t: Throwable) {
        Utils.log(t)
        false
    }
}
