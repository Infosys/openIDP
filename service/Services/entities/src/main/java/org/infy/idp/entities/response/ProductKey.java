package org.infy.idp.entities.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author kruti.vyas
 *
 */

public class ProductKey {
	@SerializedName("ProdutRuleKey")
	@Expose
	private String ProdutRuleKey;

	public String getProdutRuleKey() {
		return ProdutRuleKey;
	}

	public void setProdutRuleKey(String produtRuleKey) {
		ProdutRuleKey = produtRuleKey;
	}
}
