package com.exam.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class InventoryDTO {

	Integer inventoryId;
	Integer productId;
	Integer stockQuantity;
	LocalDateTime lastUpdated;
}