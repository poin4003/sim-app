package com.sim.app.sim_app.core.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaginationResponse<T> {
    private List<T> items;
    private Long totalItems;
    private Long currentPages;
    private Long pageSize;

    public static <T> PaginationResponse<T> of(List<T> items, long totalItems, long currentPages, long pageSize) {
        PaginationResponse<T> response = new PaginationResponse<>();
        response.setItems(items);
        response.setTotalItems(totalItems);
        response.setCurrentPages(currentPages);
        response.setPageSize(pageSize);

        return response;
    }
}
