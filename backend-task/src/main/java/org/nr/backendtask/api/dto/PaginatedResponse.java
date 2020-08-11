package org.nr.backendtask.api.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class PaginatedResponse<T> {

    private long totalElements;

    private int page;

    private int size;

    private int totalPages;

    private List<T> results;


    public <DATA> PaginatedResponse(Page<DATA> pageResults, Function<DATA, T> function) {

        this.totalElements = pageResults.getTotalElements();
        this.page = pageResults.getNumber();
        this.size = pageResults.getSize();
        this.totalPages = pageResults.getTotalPages();
        this.results = pageResults.getContent().stream().map(function).collect(Collectors.toList());

    }

    public PaginatedResponse() {
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
