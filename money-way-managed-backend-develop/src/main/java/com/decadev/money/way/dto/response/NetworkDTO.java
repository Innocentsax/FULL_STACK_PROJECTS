package com.decadev.money.way.dto.response;

import com.decadev.money.way.model.Network;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NetworkDTO {
      private String status;
      private String message;
      private List<Network> data;
}
