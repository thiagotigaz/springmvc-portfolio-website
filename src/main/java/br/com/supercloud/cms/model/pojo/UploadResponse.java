package br.com.supercloud.cms.model.pojo;

public class UploadResponse {

	private Integer portfolioId;
	private Integer fileId;

	public UploadResponse(Integer portfolioId, Integer fileId) {
		this.portfolioId = portfolioId;
		this.fileId = fileId;
	}

	public Integer getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Integer portfolioId) {
		this.portfolioId = portfolioId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

}
