package com.winner.client.productservice.stock.presentation.dto;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.global.security.CustomUserPrincipal;
import com.winner.client.productservice.stock.application.dto.command.UpdateStockCommand;
import com.winner.client.productservice.stock.application.dto.result.StockResult;
import com.winner.client.productservice.stock.application.service.StockCommandService;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import com.winner.client.productservice.stock.presentation.dto.request.UpdateStockRequest;
import io.lettuce.core.AbstractRedisAsyncCommands;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/stocks")
public class StockController {

  private final StockCommandService stockCommandService;

  @PatchMapping("/products/{productId}")
  @PreAuthorize("hasRole('MASTER, HUB_MANAGER, COMPANY_MANAGER')")
  public ResponseEntity<ApiResponse<StockResult>> updateProductStock(
      @PathVariable UUID productId, @RequestBody UpdateStockRequest request,
      @AuthenticationPrincipal CustomUserPrincipal userPrincipal){
    log.info("userPrincipal: {}",userPrincipal);
    StockResult result = stockCommandService.updateStock(new UpdateStockCommand(request.amount(),
        ProductId.of(productId)), userPrincipal);

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,result));

  }

}
