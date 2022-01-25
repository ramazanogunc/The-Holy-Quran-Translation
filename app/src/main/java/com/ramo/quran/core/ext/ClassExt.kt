package com.ramo.quran.core.ext

import java.lang.reflect.ParameterizedType

/**
 * @param genericIndex Generic type index number. example: 0
 * @return Class<CLS>
 */
fun <CLS> Class<*>.findGenericWithType(genericIndex: Int): Class<CLS> {
    @Suppress("UNCHECKED_CAST")
    return (genericSuperclass as ParameterizedType).actualTypeArguments[genericIndex] as Class<CLS>
}