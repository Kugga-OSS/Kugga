@file:JvmName("EnbededKotlin")

package com.ayang818.kugga.starter

import org.slf4j.LoggerFactory

open class EnbededKotlin {

    var logger = LoggerFactory.getLogger("EnbededKotlin");

    fun main() {
        logger.info("kotlin enbededSuccess!")
    }
}