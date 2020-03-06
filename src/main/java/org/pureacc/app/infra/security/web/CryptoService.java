package org.pureacc.app.infra.security.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.pureacc.app.vocabulary.exception.SystemException;
import org.springframework.stereotype.Component;

@Component
class CryptoService {
	private static final String CIPHER = "AES/GCM/PKCS5Padding";

	private final static SecureRandom SECURE_RANDOM = new SecureRandom();

	private SecretKeySpec aesKey;

	private byte[] aadData;

	CryptoService() throws IOException, NoSuchAlgorithmException {
		byte[] key;

		this.aadData = new byte[32];
		SECURE_RANDOM.nextBytes(this.aadData);

		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(128);
		key = keygen.generateKey()
				.getEncoded();

		byte[] keys = new byte[this.aadData.length + key.length];
		System.arraycopy(this.aadData, 0, keys, 0, this.aadData.length);
		System.arraycopy(key, 0, keys, this.aadData.length, key.length);
		Files.write(Paths.get("keys"), keys);

		this.aesKey = new SecretKeySpec(key, "AES");
	}

	String encrypt(String message) {
		try {
			byte[] iv = new byte[12];
			SECURE_RANDOM.nextBytes(iv);

			Cipher c = Cipher.getInstance(CIPHER);
			c.init(Cipher.ENCRYPT_MODE, this.aesKey, new GCMParameterSpec(128, iv));
			c.updateAAD(this.aadData);

			byte[] cipherMessage = c.doFinal(message.getBytes(StandardCharsets.UTF_8));

			byte[] result = new byte[iv.length + cipherMessage.length];
			System.arraycopy(iv, 0, result, 0, iv.length);
			System.arraycopy(cipherMessage, 0, result, iv.length, cipherMessage.length);

			return Base64.getEncoder()
					.encodeToString(result);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			throw new SystemException("Failed encrypting message", e);
		}
	}

	String decrypt(String encryptedMessage) {
		try {
			byte[] encryptedMessageBytes = Base64.getDecoder()
					.decode(encryptedMessage);

			byte[] iv = Arrays.copyOfRange(encryptedMessageBytes, 0, 12);
			byte[] cipherMessage = Arrays.copyOfRange(encryptedMessageBytes, 12, encryptedMessageBytes.length);

			Cipher c = Cipher.getInstance(CIPHER);
			c.init(Cipher.DECRYPT_MODE, this.aesKey, new GCMParameterSpec(128, iv));
			c.updateAAD(this.aadData);
			byte[] plainMessage = c.doFinal(cipherMessage);

			return new String(plainMessage, StandardCharsets.UTF_8);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | IllegalArgumentException e) {
			throw new SystemException("Failed decrypting message", e);
		}
	}
}
