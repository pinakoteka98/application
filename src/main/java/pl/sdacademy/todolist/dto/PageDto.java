package pl.sdacademy.todolist.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class PageDto {

    private String filter;

    @Min(0)
    private Integer pageIndex;

    @Min(1)
    @Max(20)
    private Integer pageSize;

    private String sortDirection;
    private String sortColumn;
}
