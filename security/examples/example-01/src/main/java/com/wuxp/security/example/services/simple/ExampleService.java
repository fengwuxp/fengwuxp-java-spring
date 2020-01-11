package com.wuxp.security.example.services.simple;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "例子服务")
public interface ExampleService {

    Long create();
}
