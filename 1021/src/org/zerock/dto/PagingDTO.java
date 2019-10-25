package org.zerock.dto;

public class PagingDTO {

	private int page;
	private int amount;

	public PagingDTO() {

		this.page = 1;
		this.amount = 10;
	}

	public PagingDTO(String pageStr, String amountStr) {
		try {
			this.page = Integer.parseInt(pageStr);
		} catch (Exception e) {
			page = 1;
			
		}
		try {
			this.amount = Integer.parseInt(amountStr);
		} catch (Exception e) {
			amount = 10;
			
		}
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {

		if (page <= 0) {
			return;
		}
		this.page = page;
	}

	@Override
	public String toString() {
		return "PagingDTO [page=" + page + ", amount=" + amount + "]";
	}

}
