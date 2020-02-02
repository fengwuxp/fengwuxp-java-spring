package com.wuxp.security.example.services.simple.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Schema(description = "example对象")
@Data
@Accessors(chain = true)
@EqualsAndHashCode()
public class ExampleInfo implements Serializable {
    private static final long serialVersionUID = -3450789080833732451L;
}
