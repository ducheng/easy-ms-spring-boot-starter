package com.ducheng.easy.ms;

import com.ducheng.easy.ms.service.MeiliSearchRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class BookEntityRepository extends MeiliSearchRepository<BookEntity> {
}
