package com.winner.client.productservice.stock.presentation.dto;

import com.winner.client.global.response.ApiResponse;
import com.winner.client.global.response.CommonSuccessCode;
import com.winner.client.productservice.stock.application.service.StockCommandService;
import com.winner.client.productservice.stock.application.service.dto.command.UpdateStockCommand;
import com.winner.client.productservice.stock.application.service.dto.result.StockResult;
import com.winner.client.productservice.stock.domain.vo.ProductId;
import com.winner.client.productservice.stock.presentation.dto.request.UpdateStockRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StockController {

  private final StockCommandService stockCommandService;

  @PatchMapping("/products/{productId}")
  public ResponseEntity<ApiResponse<StockResult>> updateProductStock(
      @PathVariable UUID productId, @RequestBody UpdateStockRequest request){

    StockResult result = stockCommandService.updateStock(new UpdateStockCommand(request.amount(), ProductId.of(productId)));

    return ResponseEntity.ok(ApiResponse.success(CommonSuccessCode.OK,result));

  }

}
