package dev.bas.util

import com.intellij.openapi.application.ApplicationManager

fun invokeLater(fn: () -> Unit) = ApplicationManager.getApplication().invokeLater(fn)

fun invokeLater(timeout: Long, fn: () -> Unit) {
    val thread = Thread {
        Thread.sleep(timeout)

        fn()
    }
    thread.start();
}
