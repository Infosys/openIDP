/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class SSLUtilities {
	private SSLUtilities() {
		// TODO Auto-generated constructor stub
	}

	private static com.sun.net.ssl.HostnameVerifier __hostnameVerifier;
	private static com.sun.net.ssl.TrustManager[] __trustManagers;
	private static HostnameVerifier _hostnameVerifier;
	private static TrustManager[] _trustManagers;

	private static void trustAllHostnamess() {
		// Create a trust manager that does not validate certificate chains
		if (__hostnameVerifier == null) {
			__hostnameVerifier = new _FakeHostnameVerifier();
		} // if
			// Install the all-trusting host name verifier
		com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(__hostnameVerifier);
	} // __trustAllHttpsCertificates

	private static void trustAllHttpsCertificatess() {
		com.sun.net.ssl.SSLContext context;
		// Create a trust manager that does not validate certificate chains
		if (__trustManagers == null) {
			__trustManagers = new com.sun.net.ssl.TrustManager[] { new _FakeX509TrustManager() };
		} // if
			// Install the all-trusting trust manager
		try {
			context = com.sun.net.ssl.SSLContext.getInstance("TLSv1.2");
			context.init(null, __trustManagers, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		} // catch
		com.sun.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	} // __trustAllHttpsCertificates

	private static boolean isDeprecatedSSLProtocol() {
		return ("com.sun.net.ssl.internal.www.protocol".equals(System.getProperty("java.protocol.handler.pkgs")));
	} // isDeprecatedSSLProtocol

	private static void trustAllHostname() {
		// Create a trust manager that does not validate certificate chains
		if (_hostnameVerifier == null) {
			_hostnameVerifier = new FakeHostnameVerifier();
		} // if
			// Install the all-trusting host name verifier:
		HttpsURLConnection.setDefaultHostnameVerifier(_hostnameVerifier);
	} // _trustAllHttpsCertificates

	private static void trustAllHttpsCertificate() {
		SSLContext context;
		// Create a trust manager that does not validate certificate chains
		if (_trustManagers == null) {
			_trustManagers = new TrustManager[] { new FakeX509TrustManager() };
		} // if
			// Install the all-trusting trust manager:
		try {
			context = SSLContext.getInstance("TLSv1.2");
			context.init(null, _trustManagers, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		} // catch
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	} // _trustAllHttpsCertificates

	public static void trustAllHostnames() {
		// Is the deprecated protocol setted?
		if (isDeprecatedSSLProtocol()) {
			trustAllHostnamess();
		} else {
			trustAllHostname();
		} // else
	} // trustAllHostnames

	public static void trustAllHttpsCertificates() {
		// Is the deprecated protocol setted?
		if (isDeprecatedSSLProtocol()) {
			trustAllHttpsCertificatess();
		} else {
			trustAllHttpsCertificate();
		} // else
	} // trustAllHttpsCertificates

	public static class _FakeHostnameVerifier implements com.sun.net.ssl.HostnameVerifier {
		public boolean verify(String hostname, String session) {
			return (true);
		} // verify
	} // _FakeHostnameVerifier

	public static class _FakeX509TrustManager implements com.sun.net.ssl.X509TrustManager {
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

		public boolean isClientTrusted(X509Certificate[] chain) {
			return (true);
		} // checkClientTrusted

		public boolean isServerTrusted(X509Certificate[] chain) {
			return (true);
		} // checkServerTrusted

		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		} // getAcceptedIssuers
	} // _FakeX509TrustManager

	@SuppressWarnings({"java:S3510"})
	public static class FakeHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
			return (true);
		} // verify
	} // FakeHostnameVerifier

	public static class FakeX509TrustManager implements X509TrustManager {
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

		public void checkClientTrusted(X509Certificate[] chain, String authType) {
			//implements parent method
		} // checkClientTrusted

		public void checkServerTrusted(X509Certificate[] chain, String authType) {
			//implements parent method
		} // checkServerTrusted

		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		} // getAcceptedIssuers
	} // FakeX509TrustManager
} // SSLUtilities