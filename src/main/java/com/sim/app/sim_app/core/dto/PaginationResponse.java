package com.sim.app.sim_app.core.dto;

import java.util.List;

import lombok.Data;

@Data
public class PaginationResponse<T> {
    private List<T> items;
    private PaginationMeta metaData;

    public static <T> PaginationResponse<T> of(List<T> items, long totalItems, long currentPage, long pageSize) {
        PaginationResponse<T> response = new PaginationResponse<>();

        PaginationMeta meta = new PaginationMeta(
            totalItems,
            currentPage,
            pageSize,
            (long) Math.ceil((double) totalItems / pageSize)
        );

        response.setItems(items);
        response.setMetaData(meta);
        return response;
    }
}
