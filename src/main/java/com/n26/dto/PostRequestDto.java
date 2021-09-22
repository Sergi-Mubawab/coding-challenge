package com.n26.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PostRequestDto implements Serializable {
	private static final long serialVersionUID = 3159723978991365907L;

	private String amount;
	private String timestamp;

}