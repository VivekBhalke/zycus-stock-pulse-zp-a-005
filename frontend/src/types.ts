export interface Product {
  id: string;
  sku: string;
  name: string;
  category: string;
  currentPrice: number;
  stockLevel: number;
  reorderThreshold: number;
  demandVelocity: number;
  status: ProductStatus;
  createdAt: string;
  updatedAt: string;
}

export enum ProductStatus {
  ACTIVE = "ACTIVE",
  PRICE_REVIEW_PENDING = "PRICE_REVIEW_PENDING",
  INACTIVE = "INACTIVE",
  OUT_OF_STOCK = "OUT_OF_STOCK"
}

export interface PricingSuggestion {
  id: number;
  productId: string;
  suggestedPrice: number;
  confidenceScore: number;
  reasoning: string;
  triggerReason: TriggerReason;
  status: SuggestionStatus;
  createdAt: string;
  updatedAt: string;
}

export interface ReorderSuggestion {
  id: number;
  productId: string;
  suggestedQuantity: number;
  confidenceScore: number;
  reasoning: string;
  triggerReason: TriggerReason;
  status: SuggestionStatus;
  createdAt: string;
  updatedAt: string;
}

export enum SuggestionStatus {
  PENDING = "PENDING",
  APPROVED = "APPROVED",
  REJECTED = "REJECTED"
}

export enum TriggerReason {
  INVENTORY_LOW = "INVENTORY_LOW",
  DEMAND_SPIKE = "DEMAND_SPIKE",
  MANUAL = "MANUAL"
}