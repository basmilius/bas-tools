package dev.bas.util

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.annotations.Nullable
import kotlin.reflect.KClass

fun <T : PsiElement> findChildOfType(element: PsiElement?, clazz: KClass<T>): T? {
    return PsiTreeUtil.findChildOfType(element, clazz.java)
}

fun <T : PsiElement> findChildrenOfType(element: PsiElement?, clazz: KClass<T>): Collection<T> {
    return PsiTreeUtil.findChildrenOfType(element, clazz.java)
}

fun <T : PsiElement> findElementOfClassAtOffset(file: PsiFile, offset: Int, clazz: KClass<T>, strictStart: Boolean): T? {
    return PsiTreeUtil.findElementOfClassAtOffset(file, offset, clazz.java, strictStart)
}

fun <T : PsiElement> getChildrenOfType(element: PsiElement?, clazz: KClass<T>): Array<out @Nullable T?>? {
    return PsiTreeUtil.getChildrenOfType(element, clazz.java)
}

fun <T : PsiElement> getContextOfType(element: PsiElement?, vararg classes: KClass<T>): T? {
    val javaClasses = classes
        .map { it.java }
        .toTypedArray()

    return PsiTreeUtil.getContextOfType(element, *javaClasses)
}

fun <T : PsiElement> getParentOfType(element: PsiElement?, clazz: KClass<T>): T? {
    return PsiTreeUtil.getParentOfType(element, clazz.java)
}

fun <T : PsiElement> getPrevSiblingOfType(element: PsiElement?, clazz: KClass<T>): T? {
    return PsiTreeUtil.getPrevSiblingOfType(element, clazz.java)
}
