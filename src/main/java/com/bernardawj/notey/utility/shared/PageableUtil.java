package com.bernardawj.notey.utility.shared;

import com.bernardawj.notey.dto.shared.InputPageDTO;
import com.bernardawj.notey.dto.shared.SortDTO;
import com.bernardawj.notey.dto.shared.SortType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {

    public static Pageable populatePageable(SortDTO sort, InputPageDTO inputPage) {
        Pageable pageable;
        if (sort.getType() == SortType.ASCENDING)
            pageable = PageRequest.of(inputPage.getPageNo() - 1,
                    inputPage.getPageSize(),
                    Sort.by(sort.getBy()).ascending());
        else if (sort.getType() == SortType.DESCENDING)
            pageable = PageRequest.of(inputPage.getPageNo() - 1,
                    inputPage.getPageSize(),
                    Sort.by(sort.getBy()).descending());
        else
            pageable = PageRequest.of(inputPage.getPageNo() - 1,
                    inputPage.getPageSize());
        return pageable;
    }
}
