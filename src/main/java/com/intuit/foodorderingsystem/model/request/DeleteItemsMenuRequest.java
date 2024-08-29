package com.intuit.foodorderingsystem.model.request;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeleteItemsMenuRequest {
    List<Long> itemListToDelete;
}
