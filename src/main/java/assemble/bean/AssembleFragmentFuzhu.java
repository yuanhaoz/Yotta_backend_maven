package assemble.bean;
/**  
 * 1. 分面名
 * 2. 分面对应的知识碎片内容（文本和图片）（html形式）
 * 3. 分面层次
 *  
 * @author 郑元浩 
 * @date 2016年11月29日
 */
public class AssembleFragmentFuzhu {
	
	public String facetName;
	public String facetContent;
	public int facetLayer;
	public String facetContentPureText;
	
	public String getFacetName() {
		return facetName;
	}
	public void setFacetName(String facetName) {
		this.facetName = facetName;
	}
	public String getFacetContent() {
		return facetContent;
	}
	public void setFacetContent(String facetContent) {
		this.facetContent = facetContent;
	}
	public int getFacetLayer() {
		return facetLayer;
	}
	public void setFacetLayer(int facetLayer) {
		this.facetLayer = facetLayer;
	}

	public String getFacetContentPureText() {
		return facetContentPureText;
	}

	public void setFacetContentPureText(String facetContentPureText) {
		this.facetContentPureText = facetContentPureText;
	}

	/**
	 * @param facetName
	 * @param facetContent
	 * @param facetLayer
	 */
	public AssembleFragmentFuzhu(String facetName, String facetContent, int facetLayer) {
		super();
		this.facetName = facetName;
		this.facetContent = facetContent;
		this.facetLayer = facetLayer;
	}

	public AssembleFragmentFuzhu(String facetName, String facetContent, int facetLayer, String facetContentPureText) {
		this.facetName = facetName;
		this.facetContent = facetContent;
		this.facetLayer = facetLayer;
		this.facetContentPureText = facetContentPureText;
	}

	public AssembleFragmentFuzhu() {
	}

	@Override
	public String toString() {
		return "AssembleFragmentFuzhu{" +
				"facetName='" + facetName + '\'' +
				", facetContent='" + facetContent + '\'' +
				", facetLayer=" + facetLayer +
				", facetContentPureText='" + facetContentPureText + '\'' +
				'}';
	}

}
