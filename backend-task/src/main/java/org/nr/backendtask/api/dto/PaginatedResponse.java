package org.nr.backendtask.api.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class PaginatedResponse<T> {

    private long count;

    private int page;

    private int perPage;

    private List<T> results;

    public <DATA> PaginatedResponse(Page<DATA> pageResults, Function<DATA, T> function) {

        this.count = pageResults.getTotalElements();
        this.page = pageResults.getNumber();
        this.perPage = pageResults.getSize();
        this.results = pageResults.getContent().stream().map(function).collect(Collectors.toList());

    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
