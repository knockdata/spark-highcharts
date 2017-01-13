package com.knockdata.spark.utils

import org.junit.Test
import org.junit.Assert.assertEquals

class TestCipherUtil{
  @Test
  def testEncrypt(): Unit = {
    val encrypted = CipherUtil.encrypt("rockie", "password")
    println(encrypted)

    assertEquals("encrypted a sample password shall be ", "v/nMLHnQCeqDobO01j3syw==", encrypted)
  }

  @Test
  def testDecrypt(): Unit = {
    val decrypted = CipherUtil.decrypt("rockie", "v/nMLHnQCeqDobO01j3syw==")
    println(decrypted)

    assertEquals("encrypted a sample password shall be ", "password", decrypted)
  }

  @Test
  def testEncrypt2(): Unit = {
    val encrypted = CipherUtil.encrypt("yang", "password")
    println(encrypted)

    assertEquals("encrypted a sample password shall be ", "VUhy/KQ2nf7DO0NBBRWW8g==", encrypted)
  }

  @Test
  def testDecrypt2(): Unit = {
    val decrypted = CipherUtil.decrypt("yang", "VUhy/KQ2nf7DO0NBBRWW8g==")
    println(decrypted)

    assertEquals("encrypted a sample password shall be ", "password", decrypted)
  }

  @Test
  def testSymmetric(): Unit = {
    val encrypted = CipherUtil.encrypt("yang", "rockie")
    println(encrypted)

    val decrypted = CipherUtil.decrypt("yang", encrypted)
    assertEquals("encrypted a sample password shall be ", "rockie", decrypted)
  }
}
