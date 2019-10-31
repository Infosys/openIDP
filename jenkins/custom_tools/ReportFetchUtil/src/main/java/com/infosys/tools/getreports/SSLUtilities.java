/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class SSLUtilities {
	private static com.sun.net.ssl.HostnameVerifier hostnameVerifier;
	private static com.sun.net.ssl.TrustManager[] trustManagers;
	private static HostnameVerifier hostnameVerifiers;
	private static TrustManager[] trustManager;
	
	

	private SSLUtilities() {
		// TODO Auto-generated constructor stub
	}

	private static void trustAllHostname() {
		// Create a trust manager that does not validate certificate chains
		if (hostnameVerifier == null) {
			hostnameVerifier = new FakeHostnameVerifiers();
		} // if
			// Install the all-trusting host name verifier
		com.sun.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
	} // __trustAllHttpsCertificates

	private static void trustAllHttpsCertificate() {
		com.sun.net.ssl.SSLContext context;
		// Create a trust manager that does not validate certificate chains
		if (trustManagers == null) {
			trustManagers = new com.sun.net.ssl.TrustManager[] { new FakeX509TrustManagers() };
		} // if
			// Install the all-trusting trust manager
		try {
			context = com.sun.net.ssl.SSLContext.getInstance("SSL");
			context.init(null, trustManagers, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		} // catch
		com.sun.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	} // __trustAllHttpsCertificates

	private static boolean isDeprecatedSSLProtocol() {
		return ("com.sun.net.ssl.internal.www.protocol".equals(System.getProperty("java.protocol.handler.pkgs")));
	} // isDeprecatedSSLProtocol

	private static void trustAllHostnamess() {
		// Create a trust manager that does not validate certificate chains
		if (hostnameVerifiers == null) {
			hostnameVerifiers = new FakeHostnameVerifier();
		} // if
			// Install the all-trusting host name verifier:
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifiers);
	} // _trustAllHttpsCertificates

	private static void trustAllHttpsCertificatess() {
		SSLContext context;
		// Create a trust manager that does not validate certificate chains
		if (trustManager == null) {
			trustManager = new TrustManager[] { new FakeX509TrustManager() };
		} // if
			// Install the all-trusting trust manager:
		try {
			context = SSLContext.getInstance("SSL");
			context.init(null, trustManager, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		} // catch
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	} // _trustAllHttpsCertificates

	public static void trustAllHostnames() {
		// Is the deprecated protocol setted?
		if (isDeprecatedSSLProtocol()) {
			trustAllHostname();
		} else {
			trustAllHostnamess();
		} // else
	} // trustAllHostnames

	public static void trustAllHttpsCertificates() {
		// Is the deprecated protocol setted?
		if (isDeprecatedSSLProtocol()) {
			trustAllHttpsCertificate();
		} else {
			trustAllHttpsCertificatess();
		} // else
	} // trustAllHttpsCertificates

	public static class FakeHostnameVerifiers implements com.sun.net.ssl.HostnameVerifier {
		@Override
		public boolean verify(String hostname, String session) {
			return (true);
		} // verify
	} // _FakeHostnameVerifier

	public static class FakeX509TrustManagers implements com.sun.net.ssl.X509TrustManager {
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

		@Override
		public boolean isClientTrusted(X509Certificate[] chain) {
			return (true);
		} // checkClientTrusted

		@Override
		public boolean isServerTrusted(X509Certificate[] chain) {
			return (true);
		} // checkServerTrusted

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		} // getAcceptedIssuers
	} // _FakeX509TrustManager

	public static class FakeHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
			return (true);
		} // verify
	} // FakeHostnameVerifier

	public static class FakeX509TrustManager implements X509TrustManager {
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
			//implements parent method
		} // checkClientTrusted

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
			//implements parent method
		} // checkServerTrusted

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		} // getAcceptedIssuers
	} // FakeX509TrustManager
} // SSLUtilities