import axios from 'axios';
import { 
  Product, 
  PricingSuggestion, 
  ReorderSuggestion, 
  ProductStatus, 
  SuggestionStatus 
} from './types';

const API_BASE_URL = '/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Product APIs
export const getProducts = async (status?: ProductStatus, category?: string) => {
  const params = new URLSearchParams();
  if (status) params.append('status', status);
  if (category) params.append('category', category);
  
  const response = await api.get<Product[]>(`/products?${params.toString()}`);
  return response.data;
};

export const getProductById = async (id: string) => {
  const response = await api.get<Product>(`/products/${id}`);
  return response.data;
};

export const createProduct = async (product: Omit<Product, 'id' | 'createdAt' | 'updatedAt' | 'status'>) => {
  const response = await api.post<Product>('/products', product);
  return response.data;
};

export const updateStock = async (id: string, newStockLevel: number) => {
  const response = await api.patch<Product>(`/products/${id}/stock`, null, {
    params: { newStockLevel }
  });
  return response.data;
};

export const processOrder = async (id: string, quantity: number) => {
  const response = await api.post<Product>(`/products/${id}/orders`, null, {
    params: { quantity }
  });
  return response.data;
};

// Pricing Suggestion APIs
export const suggestPricing = async (productId: string) => {
  const response = await api.post<PricingSuggestion>(`/products/${productId}/suggest-pricing`);
  return response.data;
};

export const updatePricingSuggestion = async (id: number, status: SuggestionStatus) => {
  const response = await api.patch<PricingSuggestion>(`/pricing-suggestions/${id}`, null, {
    params: { status }
  });
  return response.data;
};

// Reorder Suggestion APIs
export const suggestReorder = async (productId: string) => {
  const response = await api.post<ReorderSuggestion>(`/products/${productId}/suggest-reorder`);
  return response.data;
};

export const updateReorderSuggestion = async (id: number, status: SuggestionStatus) => {
  const response = await api.patch<ReorderSuggestion>(`/reorder-suggestions/${id}`, null, {
    params: { status }
  });
  return response.data;
};