package dev.bas.util

import com.intellij.openapi.progress.ProgressManager
import org.jetbrains.rpc.LOG

typealias Callback = () -> Unit

inline fun dontCare(fn: Callback) {
    try {
        fn()
    } catch (err: Exception) {
        // Don't care about the exception.
        LOG.error(err)
    }
}

inline fun processDontCare(fn: Callback) {
    ProgressManager.getInstance().run {
        dontCare(fn)
    }
}
