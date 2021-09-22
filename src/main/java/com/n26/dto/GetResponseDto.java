package com.n26.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GetResponseDto implements Serializable {
	private static final long serialVersionUID = 3159723978991365907L;
	private static final String ZERO = "0.00";

	private String sum = ZERO;
	private String avg = ZERO;
	private String max = ZERO;
	private String min = ZERO;
	private long count;

}