package com.knockdata.spark.utils

import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}

import sun.misc.{BASE64Decoder, BASE64Encoder}

object CipherUtil {
  val cipherName = "AES/CBC/PKCS5Padding"

  val ivspec = new IvParameterSpec(Array[Byte](0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))

  def getKey(key: String): Array[Byte] = {
    val raw = MessageDigest.getInstance("MD5").digest(key.getBytes)
    raw
  }

  def encrypt(key: String, password: String): String = {
    val spec = new SecretKeySpec(getKey(key), "AES")
    val cipher = Cipher.getInstance(cipherName)
    cipher.init(Cipher.ENCRYPT_MODE, spec, ivspec)

    val encrypted = cipher.doFinal(password.getBytes("UTF8"))

    new BASE64Encoder().encode(encrypted)
  }

  def decrypt(key: String, encryptedPassword: String): String = {
    val spec = new SecretKeySpec(getKey(key), "AES")
    val cipher = Cipher.getInstance(cipherName)
    cipher.init(Cipher.DECRYPT_MODE, spec, ivspec)

    val encrypted = new BASE64Decoder().decodeBuffer(encryptedPassword)

    val decrypted = cipher.doFinal(encrypted)
    new String(decrypted, "UTF8")
  }
}
