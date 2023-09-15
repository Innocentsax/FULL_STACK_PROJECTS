package com.example.cedarxpressliveprojectjava010.dto.request;

import lombok.*;

import javax.websocket.server.PathParam;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlterProductQuantityRequest {
    private Long cartItemId;
    private int quantity;
}
