package org.infosys.idp.web.uiconfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="url")
public class UIProperty {

    private String idpresturl;
	private String idpsubscriptionurl;
    private String idpdashboardurl;
    private String idplink;
    private String ge;
    private static boolean geFlag;
    private String profile;
	private String tfsServerUrl;
	private String uName;
	private String pass;
	private String authmode;
	private String tenate;
	private String clientId;
	private String postLogoutRedirectUri;
	private String endpoints;
	private String keycloakUrl;
    private String keycloakRealm;
    private String clouddeployurl;
    private String keycloakClientId;

   // private String password;
   
    public String getKeycloakUrl() {
		return keycloakUrl;
	}
	public void setkeycloakUrl(String keycloakUrl) {
		this.keycloakUrl = keycloakUrl;
	}
	 public String getClouddeployurl() {
		return clouddeployurl;
	}
	public void setClouddeployurl(String clouddeployurl) {
		this.clouddeployurl = clouddeployurl;
	}
	 public String getKeycloakRealm() {
		return keycloakRealm;
	}
	public void setKeycloakRealm(String keycloakRealm) {
		this.keycloakRealm = keycloakRealm;
	}
	 public String getKeycloakClientId() {
		return keycloakClientId;
	}
	public void setKeycloakClientId(String keycloakClientId) {
		this.keycloakClientId = keycloakClientId;
	}
	
   public String getTfsServerUrl() {
		return tfsServerUrl;
	}
	public void setTfsServerUrl(String tfsServerUrl) {
		this.tfsServerUrl = tfsServerUrl;
	}
	
	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
   
   
	public String getIdpresturl() {
		return idpresturl;
	}
	public void setIdpresturl(String idpresturl) {
		this.idpresturl = idpresturl;
	}
	
	public String getIdpsubscriptionurl() {
		return idpsubscriptionurl;
	}
	public void setIdpsubscriptionurl(String idpsubscriptionurl) {
		this.idpsubscriptionurl = idpsubscriptionurl;
	}
	public String getIdpdashboardurl() {
		return idpdashboardurl;
	}
	public void setIdpdashboardurl(String idpdashboardurl) {
		this.idpdashboardurl = idpdashboardurl;
	}
	public String getIdplink() {
		return idplink;
	}
	public void setIdplink(String idplink) {
		this.idplink = idplink;
	}
	public String getGe() {
		return ge;
	}
	public void setGe(String ge) {
		this.ge = ge;
	}
	
	public boolean getGeFlag() {
		return geFlag;
	}
	
	public void setGeFlag(boolean geFlag) {
		this.geFlag = geFlag;
	}

	public static boolean getGeFlagForJava(){
		return geFlag;
	}

  public String getProfile(){
    return profile;
  }

  public void setProfile(String profile){
      this.profile = profile;
  }
  public String getAuthmode() {
		return authmode;
	}
	public void setAuthmode(String authmode) {
		this.authmode = authmode;
	}
	public String getTenate() {
		return tenate;
	}
	public void setTenate(String tenate) {
		this.tenate = tenate;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPostLogoutRedirectUri() {
		return postLogoutRedirectUri;
	}
	public void setPostLogoutRedirectUri(String postLogoutRedirectUri) {
		this.postLogoutRedirectUri = postLogoutRedirectUri;
	}
	public String getEndpoints() {
		return endpoints;
	}
	public void setEndpoints(String endpoints) {
		this.endpoints = endpoints;
	}



}