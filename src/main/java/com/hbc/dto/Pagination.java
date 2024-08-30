package com.hbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

	private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
}
